package org.firstinspires.ftc.teamcode.utils;

import static java.lang.Math.PI;
import static java.lang.Math.signum;

import com.acmerobotics.dashboard.config.Config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

@Config
public class Outtake implements Subsystem {
    private Intake intake;

    private enum PowerState {POWER, PIDF, OFF}
    private enum StopperState {OPEN, CLOSED}
    private StopperState stopperState = StopperState.CLOSED;

    private final double stopperOpen = 0.5;
    private final double stopperClose = 0.0;


    /** State of shooter run mode*/
    private PowerState powerState = PowerState.OFF;
    private DcMotorEx left = null, right = null;

    /** Power to set the motors to if using a power run mode */
    private double power = 0;
    /** Velocity to set the motors to if using a velocity run mode*/
    private double targetVelocity = 1;
    /** Radius of the wheel making up the shooter*/
    private static final double radius = 0.048;

    /** Ratio between the motor <b>output shaft</b> and axel the wheel is mounted on
     * motorRotations/wheelRotations
     * */
    private static final double gearRatio = 1;

    private final Timer timer = new Timer();

    /** Amount of time it takes to shoot all three balls*/
    private static final int shootTimeMs = 1500;

    /** Amount of time to reenable intake transfer to shoot ball*/
    private static final int waitToOutakeTimeMs = 400;

    //https://docs.wpilib.org/en/stable/docs/software/advanced-controls/introduction/tuning-flywheel.html
    public static double kP = 0.005, kV = 0.00072, kS = 0;
    /**
     * Pulses per revolution (Ticks per revolution)
     */
    private final double PPR = 28;
    private Servo stopperServo;
    private VoltageSensor voltageSensor;
    Telemetry telem;
    double shotTime = 0;

    boolean shooting = false;


public Outtake(Robot r, Intake i, Telemetry t) {
        intake = i;
        telem = t;

        try {
            this.left = r.outtakeL;
            this.right = r.outtakeR;
            voltageSensor = r.volts;

            left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            stopperServo = r.stopperServo;

        } catch (Exception e) {
            telem.addData("Shooter not initialized properly", e);
        }
    }

    public Outtake(Robot r, Intake i) {
        this(r, i, null);
    }

    /**
     * Updates the outtake based off of the state it's in
     * @throws IllegalStateException if the state isn't possible.
     */

    public void update() {

        if (shooting) {
            if (System.currentTimeMillis() - shotTime >= 850) {
                shooting = false;
            }
        }
        switch (powerState){
            case POWER:
                left.setPower(power);
                right.setPower(power);
                break;
            case PIDF:
                double volts = voltageSensor.getVoltage();
                double rightPower = (kP * (targetVelocity - right.getVelocity()) + kV * targetVelocity ) * 12/volts;
                double leftPower = (kP * (targetVelocity - left.getVelocity()) + kV * targetVelocity) * 12/volts;

                left.setPower(leftPower);
                right.setPower(rightPower);
                break;
            case OFF:
                left.setPower(0);
                right.setPower(0);
                break;
            default:
                throw new IllegalStateException("Unexpected State in Outtake");
        }
    }


    /**
     * Gets the completion of the shooter task, 0 if shooting, 1 if done.
     * @return Binary 0 or 1 depending on state
     */
    @Override
    public double getCompletion() {
        switch (stopperState) {
            case OPEN:
                return 0;
            case CLOSED:
                return 1;
        }
        throw new IllegalStateException("Illegal outtake state");
    }
    //

    /**
     * @return The velocity of the left motor in radians per second (I think)
     */
    public double getVelocity() {
        return left.getVelocity();
    }


    public double getPower() {return left.getPower();}


    /**
     * Gets the velocity of the outer edge of the wheel that makes contact with the artifacts using
     * the left motor's angular velocity
     * @return The velocity in meters per second
     */
    public double getVelocityTan() {
        double motorRotationsPerSecond = left.getVelocity()/PPR;
        double wheelRotationsPerSecond = motorRotationsPerSecond/gearRatio;
        double radiansPerSecond = wheelRotationsPerSecond * 2 * PI;
        return radiansPerSecond * radius;
    }

    public void swap() {
        if (powerState == PowerState.POWER || powerState == PowerState.PIDF) {
            powerState = PowerState.OFF;
        } else {
            powerState = PowerState.PIDF;
        }
    }

    /**
     * Starts the shooter running at a set velocity using a custom (PedroPathing's) PIDF controller
     * @param v Target velocity the shooter should run at
     */
    public void setPIDF(double v) {
        targetVelocity = v;
        powerState = PowerState.PIDF;
    }

    public void setPIDFTan(double v) {

        double radiansPerSecond = v/radius;
        double wheelRotationsPerSecond = radiansPerSecond/ (2* PI);
        double motorRotationsPerSecond = wheelRotationsPerSecond * gearRatio;

        targetVelocity = motorRotationsPerSecond * PPR;
        powerState = PowerState.PIDF;

    }

    /**
     * Sets the shooter to run at a certain power
     * @param p Power to set the shooter's motors to
     */
    public void setPower(double p) {
        power = p;
        powerState = PowerState.POWER;
    }


    public void zero() {

        stopperServo.setPosition(stopperClose);
        stopperState = StopperState.CLOSED;

    }

    public void shoot() {

        intake.disable();
        stopperServo.setPosition(stopperOpen);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                intake.enable();
            }
        }, waitToOutakeTimeMs);

        stopperState = StopperState.OPEN;

        shotTime = System.currentTimeMillis();
        shooting = true;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopperServo.setPosition(stopperClose);
                stopperState = StopperState.CLOSED;
                if (!intake.isRunning()) {
                    intake.disable();
                }
            }
        }, shootTimeMs);

    }


    public void stop() {
        this.powerState = PowerState.OFF;
    }

    @Override
    public String toString() {
        telem.addData("Right Velocity", this.right.getVelocity());
        return String.format(Locale.US, "Outtake; Power State: %s; Velocity Setpoint: %f; Power: %f; Velocity: %f RPS %n " +
                " %n Tan Velocity: %f m/s; Velocity: %f TPS; Velocity: %f RPM",
                this.powerState, this.targetVelocity, this.left.getPower(), this.left.getVelocity()/PPR, this.getVelocityTan(), this.left.getVelocity(), (this.left.getVelocity()/PPR)*60);
    }

}
