package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Locale;

public class Intake implements Subsystem {

    private enum State { RUNNING, STOPPED }
    private State state = State.STOPPED;
    public DcMotorEx motor;

    private Telemetry t;

    private double power = 1;

    public Intake(Robot r) {
        motor = r.intake;
    }
    public Intake(Robot r, Telemetry t) {
        motor = r.intake;
        this.t = t;
    }

    public void enable() {
        setState(State.RUNNING);
    }
    public void disable() {
        setState(State.STOPPED);
    }

    public void setPower(double p) {
        power = p;
    }

    @Override
    public void update() {

        switch (state) {
            case RUNNING:
                motor.setPower(power);
                break;
            case STOPPED:
                motor.setPower(0);
                break;

        }
    }

    public void zero() {
        disable();
    }

    @Override
    public double getCompletion() {
        return 0;
    }
    private void setState(State s) {
        state = s;
    }
    public void change() {
        if (state == State.RUNNING) {
            this.disable();
        } else if (state == State.STOPPED) {
            this.enable();
        }
    }

    public void reverse() {
        power *= -1;
    }

    public boolean isRunning() {
        return state == State.RUNNING;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Intake; State: %s; Power: %f; Velocity: %f", this.state, this.power, this.motor.getVelocity(AngleUnit.RADIANS));
    }
}
