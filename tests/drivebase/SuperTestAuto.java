package org.firstinspires.ftc.teamcode.tests.drivebase;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.BaseAuto;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.actionsequencer.Action;

@Autonomous(name = "Red Pedro Super Test")
public class SuperTestAuto extends BaseAuto {
        public Pose startPose() {return new Pose(89.5, 6.75, Math.toRadians(90));}
        public Robot.Alliance alliance() {return Robot.Alliance.RED;}
        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;
        public PathChain Path5;
        public PathChain Path6;
        public PathChain Path7;
        public PathChain Path8;
        public PathChain Path9;
        public PathChain Path10;
        public PathChain Path11;
        public PathChain Path13;
        public PathChain Path12;
        public PathChain Path14;
        public PathChain Path15;
        public PathChain Path16;
        public PathChain Path17;
        public PathChain Path18;
        public PathChain Path19;
        public PathChain Path20;
        public PathChain Path21;
        public PathChain Path22;
        public PathChain Path23;
        public PathChain Path24;
        public PathChain Path25;
        public PathChain Path26;
        public PathChain Path27;
        public PathChain Path28;
        public PathChain Path29;
        public PathChain Path30;
        public PathChain Path31;
        public PathChain Path32;
        public PathChain Path33;
        public PathChain Path34;
        public PathChain Path35;
        public PathChain Path36;

        public void buildPaths() {
            Path1 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(88.500, 9.000), new Pose(79.500, 15.000))
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path2 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(79.500, 15.000), new Pose(79.500, 81.000))
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path3 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(79.500, 81.000), new Pose(112.500, 81.000))
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path4 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 81.000), new Pose(112.500, 15.000))
                    )
                    .setTangentHeadingInterpolation()
                    .setReversed()
                    .build();

            Path5 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 15.000), new Pose(103.500, 33.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path6 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(103.500, 33.000), new Pose(112.500, 39.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path7 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 39.000), new Pose(103.500, 57.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path8 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(103.500, 57.000), new Pose(112.500, 63.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path9 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 63.000), new Pose(103.500, 81.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path10 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(103.500, 81.000), new Pose(112.500, 87.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path11 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 87.000), new Pose(103.500, 105.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path12 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(103.500, 105.000), new Pose(112.500, 111.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path13 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 111.000), new Pose(112.500, 87.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path14 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(112.500, 87.000),
                                    new Pose(72.000, 153.500),
                                    new Pose(31.500, 87.000)
                            )
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path15 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(31.500, 87.000), new Pose(31.500, 111.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path16 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(31.500, 111.000), new Pose(40.500, 105.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path17 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(40.500, 105.000), new Pose(55.500, 111.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path18 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(55.500, 111.000), new Pose(64.500, 105.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path19 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(64.500, 105.000), new Pose(79.500, 111.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path20 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(79.500, 111.000), new Pose(88.500, 105.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path21 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(88.500, 105.000), new Pose(103.500, 111.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path22 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(103.500, 111.000), new Pose(112.500, 105.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path23 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 105.000), new Pose(112.500, 111.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path24 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 111.000), new Pose(31.500, 33.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path25 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(31.500, 33.000), new Pose(112.500, 111.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path26 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 111.000), new Pose(72.000, 72.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path27 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(72.000, 72.000), new Pose(31.500, 111.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path28 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(31.500, 111.000), new Pose(112.500, 33.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path29 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 33.000), new Pose(31.500, 111.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path30 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(31.500, 111.000), new Pose(72.000, 72.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path31 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(72.000, 72.000), new Pose(79.500, 33.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path32 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(79.500, 33.000), new Pose(31.500, 33.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path33 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(31.500, 33.000),
                                    new Pose(72.000, 111.500),
                                    new Pose(112.500, 33.000)
                            )
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path34 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(112.500, 33.000), new Pose(112.500, 81.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path35 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(112.500, 81.000),
                                    new Pose(70.681, 15.500),
                                    new Pose(31.500, 81.000)
                            )
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();

            Path36 = drive.follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(31.500, 81.000), new Pose(40.500, 33.000))
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(90))
                    .build();
        }

    public void initActionSequencer() {
        Action goPath1 = basicFollow(Path1);
        Action goPath2 = basicFollow(Path2);
        Action goPath3 = basicFollow(Path3);
        Action goPath4 = basicFollow(Path4);
        Action goPath5 = basicFollow(Path5);
        Action goPath6 = basicFollow(Path6);
        Action goPath7 = basicFollow(Path7);
        Action goPath8 = basicFollow(Path8);
        Action goPath9 = basicFollow(Path9);
        Action goPath10 = basicFollow(Path10);
        Action goPath11 = basicFollow(Path11);
        Action goPath12 = basicFollow(Path12);
        Action goPath13 = basicFollow(Path13);
        Action goPath14 = basicFollow(Path14);
        Action goPath15 = basicFollow(Path15);
        Action goPath16 = basicFollow(Path16);
        Action goPath17 = basicFollow(Path17);
        Action goPath18 = basicFollow(Path18);
        Action goPath19 = basicFollow(Path19);
        Action goPath20 = basicFollow(Path20);
        Action goPath21 = basicFollow(Path21);
        Action goPath22 = basicFollow(Path22);
        Action goPath23 = basicFollow(Path23);
        Action goPath24 = basicFollow(Path24);
        Action goPath25 = basicFollow(Path25);
        Action goPath26 = basicFollow(Path26);
        Action goPath27 = basicFollow(Path27);
        Action goPath28 = basicFollow(Path28);
        Action goPath29 = basicFollow(Path29);
        Action goPath30 = basicFollow(Path30);
        Action goPath31 = basicFollow(Path31);
        Action goPath32 = basicFollow(Path32);
        Action goPath33 = basicFollow(Path33);
        Action goPath34 = basicFollow(Path34);
        Action goPath35 = basicFollow(Path35);
        Action goPath36 = basicFollow(Path36);

        sequencer
                .addAction(drive, goPath1)
                .addAction(drive, goPath2)
                .addAction(drive, goPath3)
                .addAction(drive, goPath4)
                .addAction(drive, goPath5)
                .addAction(drive, goPath6)
                .addAction(drive, goPath7)
                .addAction(drive, goPath8)
                .addAction(drive, goPath9)
                .addAction(drive, goPath10)
                .addAction(drive, goPath11)
                .addAction(drive, goPath12)
                .addAction(drive, goPath13)
                .addAction(drive, goPath14)
                .addAction(drive, goPath15)
                .addAction(drive, goPath16)
                .addAction(drive, goPath17)
                .addAction(drive, goPath18)
                .addAction(drive, goPath19)
                .addAction(drive, goPath20)
                .addAction(drive, goPath21)
                .addAction(drive, goPath22)
                .addAction(drive, goPath23)
                .addAction(drive, goPath24)
                .addAction(drive, goPath25)
                .addAction(drive, goPath26)
                .addAction(drive, goPath27)
                .addAction(drive, goPath28)
                .addAction(drive, goPath29)
                .addAction(drive, goPath30)
                .addAction(drive, goPath31)
                .addAction(drive, goPath32)
                .addAction(drive, goPath33)
                .addAction(drive, goPath34)
                .addAction(drive, goPath35)
                .addAction(drive, goPath36);
    }
    }
