package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Locale;



//led colors, red: set outside range; yellow: in wrong pos/moving; green: not moving/ready to shoot
public class Turret implements Subsystem {

    private enum State {SHOTREADY, POSOUTOFRANGE, OTHER}
    private State state = State.OTHER;

    Servo left, right;
    Telemetry telem;
    DriveBase drive;
    Servo indcatorLight;


    double shooterSetAngle = 0;
    double angleToField = 0.0;
    double turretAngle = 355d / 2;

    double driveHeading = 0, setPos = 0.5;
    KinematicEquations kin;

    double minServoPos = 0;
    double maxServoPos = 1;
    double minAngleDeg = -2.5;
    double maxAngleDeg = 182.5;
    double servoHardMax = 1;
    double servoHardMin = 0;


    public Limelight3A limelight3A;
    private boolean limelightInited;

    public Turret(Robot r, DriveBase drive, KinematicEquations kin) {
        telem = r.t;
        left = r.turretServoL;
        right = r.turretServoR;


        this.drive = drive;
        this.kin = kin;
        driveHeading = drive.getPos().getHeading();

        try {
            limelight3A = r.limelight;
            limelightInited = true;
        } catch (Exception e) {
            telem.addLine("Turret Limelight didn't work");
            limelightInited = false;
        }
        indcatorLight = r.ledL;
    }

    public void zero() {
        turretAngle = 90;
        left.setPosition(0.5);
        right.setPosition(0.5);
    }

    public void faceGoal() {
        Pose pos = drive.getPos();
        double angle = kin.kinematicSolve(pos.getX(), pos.getY())[0];
        setAngleToField(angle, Math.toDegrees(pos.getHeading()));
    }


    public void setAngleToField(double angToField, double fieldAngle) {
        setTurretAngle(angToField - (fieldAngle - turretAngle));
    }

    public void setTurretAngle(double turretAngle) {
        this.turretAngle = turretAngle;
        if (turretAngle > 357.5 || turretAngle < 2.5) {
            state = State.POSOUTOFRANGE;
            return;
        }
        setPos(map_value(turretAngle, minAngleDeg, maxAngleDeg, minServoPos, maxServoPos));
    }

    private double map_value(double value, double in_min, double in_max, double out_min, double out_max){
        return (value - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public void setPos(double pos) {
        if (pos > servoHardMax || pos < servoHardMin) {
            state = State.POSOUTOFRANGE;
            return;
        }

        setPos = pos;
        state = State.SHOTREADY;

        left.setPosition(pos);
        right.setPosition(pos);
    }


    @Override
    public void update() {

        driveHeading = Math.toDegrees(drive.getPos().getHeading());

        angleToField = addAngles(driveHeading, turretAngle);

        if (limelightInited) {
            limelight3A.updateRobotOrientation(angleToField -90);
        }

        switch (state) {
            case POSOUTOFRANGE:
                indcatorLight.setPosition(Statics.errorLED);
                setAngleToField(shooterSetAngle, driveHeading);
                break;
            case OTHER:
                indcatorLight.setPosition(Statics.warningLED);
            case SHOTREADY:
                indcatorLight.setPosition(0);
        }
    }

    public double addAngles(double angle1, double angle2) {
        double a = angle1 + angle2;

        while (a > 360) {
            a -= 360;
        }
        while (a < 0) {
            a += 360;
        }
        return a;
    }

    public double getPos() {
        return left.getPosition();
    }

    @Override
    public double getCompletion() {
        return 1;
    }

    public String toString() {
        return String.format(Locale.US, "Turret; Shooter Angle: %f; Set Pos: %f; Servo Pos: %f; Turret Angle: %f", angleToField, setPos, getPos(), turretAngle);
    }
}
