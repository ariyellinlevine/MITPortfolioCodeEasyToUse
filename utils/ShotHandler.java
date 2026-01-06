package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.geometry.FuturePose;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Locale;

public class ShotHandler implements Subsystem{


    Outtake outtake;
    Hood hood;
    DriveBase drive;
    Turret turret;
    Telemetry telem;
    double[] solution = new double[3];

    KinematicEquations kins;
    ManualShooting man;
    double targetVel = 0;
    Servo led;

    public ShotHandler(Outtake s, Hood h, DriveBase d, Telemetry t, Turret turret, KinematicEquations kins, ManualShooting man, Robot r) {
        this.outtake = s;
        this.hood = h;
        this.drive = d;
        this.telem = t;
        this.kins = kins;
        this.turret = turret;
        this.man = man;
        this.led = r.ledR;
    }

    public void zero() {}

    @Override
    public void update() {
        telem.addData("Commpletion", getCompletion());
    }


    double hoodAngle = 0;
    public void prepShot(FuturePose robotPose) {

        Pose pose = robotPose.getPose();
        solution = kins.velocitySolve(pose.getX()/39.37, pose.getY()/39.37, 52.166);

        try {
            targetVel = man.getVelocity(pose);
        } catch (Exception e) {
            targetVel = 1250;
            led.setPosition(Statics.errorLED);
        }
        outtake.setPIDF(targetVel);

        drive.turn(solution[0]);

    }
    public void shootwithoutange(FuturePose robotPose) {

        Pose pose = robotPose.getPose();
        solution = kins.velocitySolve(pose.getX()/39.37, pose.getY()/39.37, 52.166);

        try {
            targetVel = man.getVelocity(pose);
        } catch (Exception e) {
            targetVel = 1250;
            led.setPosition(Statics.errorLED);
        }
        outtake.setPIDF(solution[1]);


    }


    public double getCompletion() {
        if ((drive.getCompletion() != 1) || (hood.getCompletion() != 1) || (Math.abs(kins.outtakeVel - outtake.getVelocity()) <= 1)) {
            return 0;
        }
        return 1;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Shot Handler; Completion: %f; Target X: %f; Target Y: %f; Face Angle: %f; Launch Velocity: %f; Dist: %f; Target Vel: %f", this.getCompletion(), kins.goalx * 39.37, kins.goaly * 39.37,  solution[0], solution[1], kins.xdis, targetVel);
    }
}
