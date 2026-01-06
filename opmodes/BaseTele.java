package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Statics;


/**
 * Base Teleop to extend our final teleops from, contains some start logic every teleop will need
 *
 * @author Ari Yellin-Levine
 */
public abstract class BaseTele extends BaseOp {

    Servo localizationIndicator;
    public Pose startPose() {
        return Statics.startPosition;
    }
    public Robot.OpModeType opType() {
        return Robot.OpModeType.TELEOP;
    }


    public void holdForStart() {
        localizationIndicator = robot.ledM;
        localizationIndicator.setPosition(Statics.warningLED);
        telemetry.update();
        while (!limelight.getLocalized() && opModeInInit()) {
            limelight.updatePosWithAprilTags();
            telemetry.update();
        }

        localizationIndicator.setPosition(Statics.goodLED);
        waitForStart();
        zero();
        drive.inTeleOp();
        drive.teleop();

    }

}
