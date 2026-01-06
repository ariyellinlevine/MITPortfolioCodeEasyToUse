package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Statics;
import org.firstinspires.ftc.teamcode.utils.actionsequencer.Action;
import org.firstinspires.ftc.teamcode.utils.actionsequencer.ActionSequencer;

public abstract class BaseAuto extends BaseOp {

    protected ActionSequencer sequencer = new ActionSequencer();
    public abstract void buildPaths();
    public abstract void initActionSequencer();
    protected Robot.OpModeType opType() {
        return Robot.OpModeType.AUTONOMOUS;
    }


    @Override
    public void initialize() {
        super.initialize();

        buildPaths();
        initActionSequencer();

        telem.print();
        telemetry.update();
        zero();
    }

    public void holdForStart() {
        waitForStart();

        intake.enable();

        outtake.setPIDFTan(vInitial);

        sequencer.start();
    }

    @Override
    public void runOpMode() {
        initialize();
        //hiiiii

        holdForStart();

        while (sequencer.update() && opModeIsActive()) {
            update();
        }

        while (opModeIsActive()) {}
        Statics.startPosition = drive.getPos();
    }

    /**
     * Usage path = buildBasicLine(pose1, pose2);
     * @param p1 Start Pose of the path
     * @param p2 End Pose of the path
     * @return the path which can then be used by pedro
     */
    public PathChain buildBasicLine(Pose p1, Pose p2) {
        return drive.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(p1, p2)
                )
                .setLinearHeadingInterpolation((p1.getHeading()), (p2.getHeading()))
                .build();
    }

    public Action basicFollow(PathChain p) {
        return () -> drive.movePedro(p);
    }
    //wait these might null pointer, even if they don't i think there will always be a lingering threat of one, but its repeated code
    //It might be fine because of memory references, I'll have to test it.
    protected Action shoot = () -> outtake.shoot();
    protected Action prepShoot = () -> shotHandler.prepShot(drive.follower::getPose);

}