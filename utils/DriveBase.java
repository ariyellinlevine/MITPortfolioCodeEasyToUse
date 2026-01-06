package org.firstinspires.ftc.teamcode.utils;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.controller.PIDFController;

import com.pedropathing.follower.Follower;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.BezierPoint;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;

import java.util.Locale;

/**
 *<h2>Class to control robots driving movement and controls robot drive style state</h2>
 * @author Ari Yellin-Levine
 */
public class DriveBase implements Subsystem {

    private enum State {MOVE_TO_PEDRO, HOLD, TELEOP, PEDRO_TURN }
    private State state = State.HOLD;
    public double sensitivity = 1;
    public final Follower follower;
    public PIDFController headingPID = new PIDFController(0.7, 0.01, -0.02, 0.01);
    private final Gpad gpad, gpad2;
    private final Robot r;
    public double precisionPos = 0.7;
    private Pose startPose, targetPose;
    public boolean fieldCentric = true;
    public boolean holdEnd;
    public double targetAngle;
    public boolean inTeleOp;
    double fieldCentricOffset = 0;
    public double xDis;

    Telemetry t;
    public DriveBase(Robot r, Gpad gpad, Gpad gpad2, Telemetry t, Pose startPose) {
        this.r = r;

        this.t = t;
        this.gpad = gpad;
        this.gpad2 = gpad2;

        this.follower = Constants.createFollower(r.hardwareMap);
        this.follower.setStartingPose(startPose);
        this.follower.update();
        targetPose = startPose;

        xDis = Math.hypot(getPos().getX()/39.37, getPos().getY()/39.37);

        inTeleOp = r.opmodetype == Robot.OpModeType.TELEOP;
    }

    public DriveBase(Robot r, Gpad gpad, Gpad gpad2, Telemetry t, Pose startPose, double offset) {
        this.r = r;

        this.t = t;
        this.gpad = gpad;
        this.gpad2 = gpad2;

        this.follower = Constants.createFollower(r.hardwareMap);
        this.follower.setStartingPose(startPose);
        this.follower.update();

        if (r.opmodetype == Robot.OpModeType.TELEOP) {
            inTeleOp = true;
        } else {
            inTeleOp = false;
        }

        fieldCentricOffset = offset;
    }
    public DriveBase(Robot r, Gpad gpad, Telemetry t) {
        this(r, gpad, null, t, new Pose());
    }

    @Override
    public void zero() {}

    /**
     * Updates the drivebase based off of the state they are in.
     * @throws IllegalStateException if the state isn't possible.
     */
    public void update() {
        xDis = Math.hypot(getPos().getX()/39.37, getPos().getY()/39.37);

        double lX = Math.abs(Math.pow(gpad.stickLX, sensitivity)) * Math.signum(gpad.stickLX);
        double lY = Math.abs(Math.pow(gpad.stickLY, sensitivity)) * Math.signum(gpad.stickLY);
        double rX = Math.abs(Math.pow(gpad.stickRX, sensitivity)) * Math.signum(gpad.stickRX);

        switch (state) {
            case MOVE_TO_PEDRO:
                follower.update();
                if (!follower.isBusy()) {
                    if (inTeleOp) {
                        teleop();
                    } else {
                        hold();
                    }
                }
                break;
            case TELEOP:
                follower.update();
                follower.setTeleOpDrive(-lY, -lX, -rX, !fieldCentric, r.getAlliance() == Robot.Alliance.RED ? 0 : 135);
                break;
            case HOLD:
                follower.holdPoint(targetPose);
                follower.update();
                break;

            case PEDRO_TURN:
                follower.update();
                if (!follower.isBusy()) {
                    if (Math.abs(Math.toDegrees(follower.getHeading()) - targetAngle) >= 5) {
                        this.turn(targetAngle);
                    } else {
                        if (inTeleOp) {
                            teleop();
                        } else {
                            hold();
                        }
                    }
                }
                break;
            default:
                throw new IllegalStateException("Illegal state in drivebase " + state);
        }
    }

    /**
     * Sets the robot to follow a pedro path and starts it
     * @param path path for the robot to follow
     */
    public void movePedro(Path path, boolean holdPos) {
        startPose = follower.getPose();
        targetPose = path.endPose();
        holdEnd = holdPos;
        follower.followPath(path);
        setState(State.MOVE_TO_PEDRO);
    }

    public void movePedro(PathChain path) {
        startPose = follower.getPose();
        targetPose = path.endPose();

        holdEnd = false;
        follower.followPath(path);
        setState(State.MOVE_TO_PEDRO);
    }

    /**
     * Gets current position from follower
     * @return current Pose of robot
     */

    public Pose getPos() {
        return follower.getPose();
    }

    public void setStartPose(Pose start){
        this.follower.setStartingPose(start);
    }

    public void inTeleOp() {
        inTeleOp = true;
    }

    /**
     * Sets the position of the follower in the robot
     * @param pos Pose object to set current position to
     */
    public void setPos(Pose pos) {
        follower.setPose(pos);

        if (state == State.HOLD) {
            targetPose = pos;
        }
    }

    /**
     * Creates a path between the robots current position and a pose object for pedro to follow
     * @param pose end pose of the path
     */
    public void movePedro(Pose pose, boolean holdPos) {
        startPose = follower.getPose();
        targetPose = pose;
        Path path = new Path(new BezierLine(follower.getPose(), pose));
        holdEnd = holdPos;
        //might want more than just linear sometimes
        path.setLinearHeadingInterpolation(startPose.getHeading(), targetPose.getHeading());
        follower.followPath(path);
        setState(State.MOVE_TO_PEDRO);
    }

    public void teleop() {
        follower.startTeleopDrive();
        setState(State.TELEOP);
    }

    /**
     * Turns the robot the specified number of degrees
     * @param degrees degrees to turn
     */
    public void turn(double degrees) {
        targetAngle = degrees;
        follower.followPath(
                follower.pathBuilder()
                .addPath(
                        new BezierPoint(follower.getPose())
                )
                .setConstantHeadingInterpolation(Math.toRadians(targetAngle))
                .build());
        setState(State.PEDRO_TURN);

    }

    public void setFieldCentric(boolean f) {
        fieldCentric = f;
    }
    /**
     * Sets the target to the robot's current position and starts keeping it there
     */
    public void hold() {
        this.targetPose = follower.getPose();
        setState(State.HOLD);
    }



    /**
     * Sets the Drivebase's state
     * @param state state to set the drivebase to
     */
    private void setState(State state){this.state = state;}

    /**
     * @return Whether drivebase is in a moving state
     */
    public boolean isMoving(){
        return (state == State.MOVE_TO_PEDRO);
    }

    /**
     * Calculates the percentage of an automove the robot has completed <br>
     * Not implemented-> start and end positons, than trig?
     * @return Percent Complete of the autoMove
     */

    @Override
    public double getCompletion() {
        Pose currentPose = follower.getPose();

        switch (state) {
            case MOVE_TO_PEDRO:
            case HOLD:
            case TELEOP:
            default:
        }
        if (isMoving()) {
            return 0;
        }
        return 1;
    }


    @NonNull
    @Override
    public String toString() {
        String s = String.format(Locale.US, "Drive Base; State %s; Completion: %f; Path Completion: %f; Dist: %f",
                state, getCompletion(), follower.getPathCompletion(), xDis);
        if (follower.getCurrentPath() != null) {
            s += String.format("Last control point: %s",follower.getCurrentPath().getLastControlPoint());
        }
        return s;
    }
}
