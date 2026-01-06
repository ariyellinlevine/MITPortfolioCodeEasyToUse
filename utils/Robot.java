package org.firstinspires.ftc.teamcode.utils;


import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

@Config
/**
 * Simple container class used to organize the robot's hardware devices.
 * <p>
 * This object is created once at initialization time and then shared with
 * the various subsystems so they can obtain references to the configured
 * motors and sensors.
 *
 * @Author Ben Levine and Ari Yellin-Levine
 */

public class Robot {

    public static double teleopLength = 120.0;
    public static double autonomousLength = 30.0;
    public enum Alliance {BLUE, RED, TEST}
    public enum OpModeType { TELEOP, AUTONOMOUS, TEST }
    public enum Motif {GPP, PGP, PPG}
    private Alliance alliance;
    public OpModeType opmodetype;
    private Motif motif;
    private Timer gameTime;
    public Limelight3A limelight;

    /** Left and right linear slide motors. */
    public DcMotorEx outtakeL, intake, outtakeR;
    public Servo pitchServo, stopperServo, turretServoL, turretServoR, AHHHH;
    //supposed to be a different line
    public Servo ledL, ledM, ledR;

    //have to decide which pose works best
    public static final Pose BLUEGOAL = new Pose(0,144);
    public static final Pose REDGOAL = new Pose(144,144);
    public static Pose TARGETGOAL = REDGOAL;

    /** The {@link HardwareMap} supplied by the running OpMode. */
    public HardwareMap hardwareMap;
    public Telemetry t;

    public IMU imu;

    public VoltageSensor volts;
    private boolean initedProperly = true;

    /**
     * Creates a new {@code Robot} instance and retrieves all required hardware
     * from the provided {@link HardwareMap}.
     *
     * @param map the hardware map used to look up devices
     */
    public Robot(HardwareMap map, Alliance a, OpModeType o, Telemetry t) {

        this.hardwareMap = map;
        this.t = t;

        this.alliance = a;

        if (alliance == Alliance.BLUE) {
            TARGETGOAL = BLUEGOAL;
        } else if (alliance == Alliance.RED) {
            TARGETGOAL = REDGOAL;
        } else {
            TARGETGOAL = null;
        }
        this.opmodetype = o;

        this.gameTime = new Timer();

        this.volts = hardwareMap.voltageSensor.iterator().next();

        try {
            this.outtakeL = hardwareMap.get(DcMotorEx.class, "outtakeL");
            outtakeL.setDirection(DcMotorSimple.Direction.REVERSE);
            outtakeL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } catch (Exception e) {
            t.addLine("Outtake L not initalized");
            initedProperly = false;
        }

        try {
            this.AHHHH = hardwareMap.get(Servo.class, "help");

        } catch (Exception e) {
            t.addLine("Help servo not initalized");
            initedProperly = false;
        }

        try {

            this.outtakeR = hardwareMap.get(DcMotorEx.class, "outtakeR");
            outtakeR.setDirection(DcMotorSimple.Direction.FORWARD);
            outtakeR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        } catch (Exception e) {
            t.addLine("Outtake R not initalized");
            initedProperly = false;
        }

        try {
            this.intake = hardwareMap.get(DcMotorEx.class, "intake");
            intake.setDirection(DcMotorSimple.Direction.FORWARD);
            intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        } catch (Exception e) {
            t.addLine("Intake Motor not initalized");
            initedProperly = false;
        }

        try {
            this.pitchServo = hardwareMap.get(Servo.class, "pitchServo");
            pitchServo.setDirection(Servo.Direction.REVERSE);

        } catch (Exception e) {
            t.addLine("Pitch Servo not initalized");
            initedProperly = false;
        }

        try {
            this.limelight = map.get(Limelight3A.class, "limelight");

            limelight.pipelineSwitch(9);

            limelight.start();
        } catch (Exception e) {
            t.addLine("Limelight not initialized");
            initedProperly = false;
        }

        try {
            this.stopperServo = hardwareMap.get(Servo.class, "stopperServo");
            stopperServo.setDirection(Servo.Direction.REVERSE);

        } catch (Exception e) {
            t.addLine("Stopper Servo not initalized");
            initedProperly = false;
        }
        try {
            this.turretServoL = hardwareMap.get(Servo.class, "turretServoL");
            turretServoL.setDirection(Servo.Direction.REVERSE);

        } catch (Exception e) {
            t.addLine("Left Turret Servo not initalized");
            initedProperly = false;

        }
        try {
            this.turretServoR = hardwareMap.get(Servo.class, "turretServoR");
            turretServoR.setDirection(Servo.Direction.REVERSE);

        } catch (Exception e) {
            t.addLine("Right Turret Servo not initalized");
            initedProperly = false;
        }

        try {
            ledL = hardwareMap.get(Servo.class, "ledL");
        } catch (Exception e) {
            t.addLine("Left LED not initalized");
            initedProperly = false;
        }

        try {
            ledM = hardwareMap.get(Servo.class, "ledM");
        } catch (Exception e) {
            t.addLine("Middle LED not initalized");
            initedProperly = false;
        }

        try {
            ledR = hardwareMap.get(Servo.class, "ledR");
            if (initedProperly) {
                ledR.setPosition(Statics.goodLED);
            } else {
                ledR.setPosition(Statics.errorLED);
            }
        } catch (Exception e) {
            t.addLine("Right LED not initalized");
            initedProperly = false;
        }
    }

    public Robot(HardwareMap map, Telemetry t) {
        this(map, Alliance.TEST, OpModeType.TEST, t);
    }

    public void initialize(){
        gameTime.resetTimer();
    }

    public double elapsedTimeSeconds(){
        return gameTime.getElapsedTimeSeconds();
    }

    public double elapsedTime(){
        return gameTime.getElapsedTime();
    }

    public double timeRemainingSeconds(){
        switch (opmodetype) {
            case TELEOP:
                return teleopLength - gameTime.getElapsedTimeSeconds();
            case AUTONOMOUS:
                return autonomousLength - gameTime.getElapsedTimeSeconds();
            case TEST:
                return 1.0;
            default:
                return 0.0;
        }
    }

    public boolean findPattern() {
        LLResult result = limelight.getLatestResult();

        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

        for (LLResultTypes.FiducialResult r : fiducials) {

            int id = r.getFiducialId();

            if (id == 20 || id == 24) {
                continue;
            }
            switch (id) {
                case 23:
                    motif = Motif.PPG;
                    t.addLine("Pattern: PPG");
                    return false;
                case 22:
                    motif = Motif.PGP;
                    t.addLine("Pattern: PGP");
                    return false;
                case 21:
                    motif = Motif.GPP;
                    t.addLine("Pattern: GPP");
                    return false;
            }
        }
        t.addLine("No pattern found");
        return true;

    }
    public double timeRemaining(){
        switch (opmodetype) {
            case TELEOP:
                return teleopLength - gameTime.getElapsedTime();
            case AUTONOMOUS:
                return autonomousLength - gameTime.getElapsedTime();
            case TEST:
                return 1.0;
            default:
                return 0.0;
        }
    }
    public Alliance getAlliance() {
        return alliance;
    }
    public OpModeType getOpmodetype() {
        return opmodetype;
    }
    public Motif getPattern() { return motif; }

}