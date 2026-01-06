package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.LimeLight;
import org.firstinspires.ftc.teamcode.utils.DriveBase;
import org.firstinspires.ftc.teamcode.utils.Gpad;
import org.firstinspires.ftc.teamcode.utils.Hood;
import org.firstinspires.ftc.teamcode.utils.Intake;
import org.firstinspires.ftc.teamcode.utils.KinematicEquations;
import org.firstinspires.ftc.teamcode.utils.ManualShooting;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Outtake;
import org.firstinspires.ftc.teamcode.utils.ShotHandler;
import org.firstinspires.ftc.teamcode.utils.Subsystem;
import org.firstinspires.ftc.teamcode.utils.Telem;
import org.firstinspires.ftc.teamcode.utils.Turret;

import java.util.ArrayList;

/**
 * Class to extend all opmodes from, contains all the subsystems and methods to initalize, zero, and update them together
 * ensure nothing is forgotten and things only need to be changed in one place.
 *
 * @author Ari Yellin-Levine '26
 */
public abstract class BaseOp extends LinearOpMode {
    protected Robot robot;
    protected DriveBase drive;
    protected Outtake outtake;
    protected Intake intake;
    protected Hood hood;
    protected ArrayList<Subsystem> subsystems = new ArrayList<>();
    protected Gpad gpad1, gpad2;
    protected Telem telem;
    protected ShotHandler shotHandler;
    protected Turret turret;

    protected LimeLight limelight;
    protected PoseGenerator gen = new PoseGenerator(alliance());

    protected abstract Pose startPose();
    protected abstract Robot.Alliance alliance();
    protected abstract Robot.OpModeType opType();
    protected double[] returnAngles = new double[3];
    protected static final double vInitial = 1200;
    protected static KinematicEquations kins;
    protected ManualShooting man;
    public abstract void runOpMode();



    //https://www.desmos.com/calculator/icqwbumn4d
    //multiple solutions?
    //this doesn't take the robots size into account, I need to add that

    /**
     * Checks if the robot is in a legal zone to launch
     * @param pose robot's current pose
     * @return True if legal to launch
     */
    public static boolean checkPos(Pose pose) {

        double y = pose.getY();
        double x = pose.getX();
        if (y >= 72 && y <= 144) {
            if (y <= (-x + 144) && y <= x) {
                return true;
            }
        }
        if (y >= 0 && y <= 24) {
            if (y >= x - 48 && y >= (-x + 96)) {
                return true;
            }
        }
        return false;
    }


    public void initialize() {

        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);
        robot = new Robot(hardwareMap, alliance(), opType(), telemetry);
        gpad1 = new Gpad(gamepad1);
        gpad2 = new Gpad(gamepad2);

        kins = new KinematicEquations(1.143,0.4064,vInitial,Robot.TARGETGOAL.getX()/39.37,Robot.TARGETGOAL.getY()/39.37);

        drive = new DriveBase(robot, gpad1, gpad2, telemetry, startPose());
        subsystems.add(drive);

        intake = new Intake(robot, telemetry);
        subsystems.add(intake);

        outtake = new Outtake(robot, intake, telemetry);
        subsystems.add(outtake);

        hood = new Hood(robot);
        subsystems.add(hood);

        telem = new Telem(this, robot, drive, telemetry, subsystems);

        turret = new Turret(robot, drive, kins);
        subsystems.add(turret);

        man = new ManualShooting(kins);

        shotHandler = new ShotHandler(outtake, hood, drive, telemetry, turret, kins, man, robot);
        subsystems.add(shotHandler);

        try {
            limelight = new LimeLight(robot, drive, turret);
        } catch (Exception e) {
            telemetry.addLine("April Tag Local failed to initalize");
            robot.ledR.setPosition(0);
        }
        telemetry.addLine("BaseOp inited");
        telemetry.update();

    }

    public void waitForStart() {
        super.waitForStart();
        //no clue why this is here
        robot.ledR.setPosition(0.27);
    }

    public void zero() {
        for (Subsystem s : subsystems) {
            s.zero();
        }

        robot.ledR.setPosition(0);
        robot.ledM.setPosition(0);
        robot.ledL.setPosition(0);

    }



    double lastTime = 0;
    public void update() {
        telemetry.addData("Loop Time", (System.currentTimeMillis() - lastTime) * 1000);
        lastTime = System.currentTimeMillis();
        gpad1.update();
        gpad2.update();

        telemetry.addData("Can Shoot", checkPos(drive.getPos()));
        for (Subsystem s : subsystems) {
            s.update();
        }

        limelight.updatePosWithAprilTags();
        telemetry.addLine(limelight.toString());

        Drawing.drawRobot(drive.getPos());
        telem.print();
        telemetry.update();
    }
}
