package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.opmodes.BaseTele;

public abstract class Teleop1 extends BaseTele {


    //uncomment if not running an auto first, alternatively just run on the red alliance, that's statics default
//    public Pose startPose() {
//        return gen.newPose(89.5, 6.75, Math.toRadians(90));
//    }


    double velocity = 100;
    boolean shooting = true;
    @Override
    public void runOpMode() {
        initialize();

        holdForStart();

        while (opModeIsActive()) {
            if (gpad1.a) {
                outtake.shoot();
            }


            if (gpad1.bR) {
                intake.disable();
                outtake.stop();
            }

            if (gpad1.b) {
                intake.change();
            }
            if (gpad1.back) {
                intake.reverse();
            }

            if (gpad2.dU) {
                velocity += 50;
            }

            if (gpad2.dD) {
                velocity -= 50;
            }

            if (gpad1.bL) {
                shotHandler.prepShot(drive.getPos());
                if (!checkPos(drive.getPos())) {
                    gpad1.rumble(1d, 1000);
                    gpad2.rumble(1d, 1000);
                }
            }

            if (gpad1.x) {
                if (shooting) {
                    shooting = false;
                    outtake.setPIDF(velocity);
                } else {
                    shooting = true;
                    outtake.stop();
                }
            }

            telemetry.addData("Vel", velocity);
            update();
        }

    }
}
