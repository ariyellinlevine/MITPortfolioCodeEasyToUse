package org.firstinspires.ftc.teamcode.tests.drivebase;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Gpad;
import org.firstinspires.ftc.teamcode.utils.DriveBase;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Telem;

/**
 * This is a simple teleop routine for testing localization. Drive the robot around like a normal
 * teleop routine and make sure the robot's estimated pose matches the robot's actual pose (slight
 * errors are not out of the ordinary, especially with sudden drive motions). The goal of this
 * exercise is to ascertain whether the localizer has been configured properly (note: the pure
 * encoder localizer heading may be significantly off if the track width has not been tuned).
 */


@TeleOp(name = "LocalizationTest", group = "tests drive")
public class LocalizationTest extends LinearOpMode {
    Robot robot;
    DriveBase driveBase;
    Telem t;
    Gpad gpad1;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        robot = new Robot(hardwareMap, telemetry);
        gpad1 = new Gpad(gamepad1);

        driveBase = new DriveBase(robot, gpad1, telemetry);
        driveBase.inTeleOp();

        t = new Telem(this, robot, driveBase, telemetry);
        t.print();
        driveBase.teleop();

        telemetry.update();
        waitForStart();

        while (!isStopRequested()) {
            driveBase.update();
            gpad1.update();

            telemetry.addData("Path", driveBase.follower.getCurrentPath());
            t.print();

            telemetry.update();
        }
    }
}
