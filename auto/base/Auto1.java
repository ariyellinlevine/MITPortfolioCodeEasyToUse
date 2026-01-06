package org.firstinspires.ftc.teamcode.auto.base;

import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.opmodes.BaseAuto;
import org.firstinspires.ftc.teamcode.utils.actionsequencer.Action;


/**
 * "Auto Solo Start Far Shoot Far No Gate"
 */
public abstract class Auto1 extends BaseAuto {

    public Pose startPose = gen.newPose(89.5, 6.75, Math.toRadians(90));
    protected Pose startPose() {
        return startPose;
    }

    public Pose shootFar = gen.newPose(83.000, 11.000, 0);
    public Pose intake1startPose = gen.newPose(100, 34.000, Math.toRadians(0));
    public Pose intake1end = gen.newPose(125.000, 34.000, Math.toRadians(0));
    public Pose intake2startPose = gen.newPose(105.000, 58.000, Math.toRadians(0));
    public Pose intake2end = gen.newPose(125.000, 58.000, Math.toRadians(0));
    public Pose intake3startPose = gen.newPose(105.000, 82.000, Math.toRadians(0));
    public Pose intake3endPose = gen.newPose(125.000, 82.000, Math.toRadians(0));
    public Pose parkPose = gen.newPose(105, 11.000, Math.toRadians(45));

    public PathChain intake1start, intake1done, intake1tofar, intake2start, intake2done, intake2tofar, intake3start, intake3done, intake3tofar, park;

    public void initActionSequencer() {
        Action goToIntake1 =  basicFollow(intake1start);
        Action intake1 = basicFollow(intake1done);
        Action goToShoot1 = basicFollow(intake1tofar);

        Action goToIntake2 =  basicFollow(intake2start);
        Action intake2 = basicFollow(intake2done);
        Action goToShoot2 = basicFollow(intake2tofar);

        Action goToIntake3 = basicFollow(intake3start);
        Action intake3 = basicFollow(intake3done);
        Action goToShoot3 = basicFollow(intake3tofar);
        //send straight to the pose? I'll test both
        Action goPark = basicFollow(park);

        Action disableIntake = () -> intake.disable();
        Action enableIntake = () -> intake.enable();


        sequencer
                .addAction(shotHandler, prepShoot)
                .addAction(outtake, shoot)

                .addAction(drive, goToIntake1)
                .addAction(drive, intake1)
                .addAction(drive, goToShoot1)

                .addAction(shotHandler, prepShoot)
                .addAction(outtake, shoot)

                .addAction(drive, goToIntake2)
                .addAction(drive, intake2)
                .addAction(drive, goToShoot2)

                .addAction(shotHandler, prepShoot)
                .addAction(outtake, shoot)

                .addAction(drive, goToIntake3)
                .addAction(drive, intake3)
                .addAction(drive, goToShoot3)

                .addAction(shotHandler, prepShoot)
                .addAction(outtake, shoot)

                .addAction(drive, goPark)
        ;
    }

    public void buildPaths() {

        intake1start = buildBasicLine(startPose, intake1startPose);


        intake1done = buildBasicLine(intake1startPose, intake1end);


        intake1tofar = buildBasicLine(intake1end, shootFar);


        intake2start = buildBasicLine(shootFar, intake2startPose);


        intake2done = buildBasicLine(intake2startPose, intake2end);


        intake2tofar = buildBasicLine(intake2end, shootFar);


        intake3start = buildBasicLine(shootFar, intake3startPose);


        intake3done = buildBasicLine(intake3startPose, intake3endPose);


        intake3tofar = buildBasicLine(intake3endPose, shootFar);


        park = buildBasicLine(shootFar, parkPose);

    }
}
