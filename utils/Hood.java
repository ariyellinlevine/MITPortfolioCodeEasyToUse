package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.Servo;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Hood implements Subsystem {

    private enum State {MOVING, STOPPED}
    private State state = State.STOPPED;

    public final Servo servo;

    private final Timer timer = new Timer();

    private final double startAngle = 29.297;
    private final double endAngle = 56.297;
    private final int moveTime = 1000;
    private final double setPos;


    public Hood(Robot r) {
        servo = r.pitchServo;
        servo.setDirection(Servo.Direction.FORWARD);
        setPos = servo.getPosition();
    }


    public void update() {
        servo.setPosition(0.8);
    }



    double mappedAngle= 0;
    public void setAngle(double angle) {
        mappedAngle = (angle - startAngle)/360;

        servo.setPosition(mappedAngle);
        stateForChangingPositon();
    }

    public double getAngle() {
        return (mappedAngle *360) + startAngle;
    }

    public void incrementBy(double pos) {
        double lastpos = servo.getPosition();
        state = State.MOVING;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = State.STOPPED;
            }
        }, 250);

    }

    public double checkAngle(double angle1, double angle2) {
        double largeAngle = Math.max(angle1, angle2);
        double smallAngle = Math.min(angle1, angle2);

        if (largeAngle >= startAngle && largeAngle <= endAngle) {
            return largeAngle;
        } else {
            return smallAngle;
        }
    }

    private void stateForChangingPositon() {
        state = State.MOVING;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = State.STOPPED;

            }
        }, moveTime);
    }
    @Override
    public double getCompletion() {
        if (state == State.MOVING) {
            return 0;
        } else {
            return 1;
        }
    }

    public void zero() {
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Hood; State: %s; Position: %f; Mapped Angle: %f; Angle: %f", this.state, this.servo.getPosition(), mappedAngle, getAngle());
    }
}
