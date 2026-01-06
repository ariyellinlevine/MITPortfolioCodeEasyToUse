package org.firstinspires.ftc.teamcode.tests.turret;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.BaseOp;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Turret;

@TeleOp (name = "Turret Face Test")
public class TurretFaceTest extends BaseOp {
    @Override
    protected Robot.Alliance alliance() {
        return Robot.Alliance.RED;
    }

    public Pose startPose() {
        return gen.newPose(89.5, 6.75, Math.toRadians(90));
    }

    public Robot.OpModeType opType() {
        return Robot.OpModeType.TEST;
    }

    boolean shooting = true;
    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        drive.inTeleOp();
        drive.teleop();
        turret.zero();
        while(opModeIsActive()) {

            turret.faceGoal();
        }
    }
}
