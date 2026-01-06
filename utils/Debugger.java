package org.firstinspires.ftc.teamcode.utils;


import java.util.Locale;

/**
 * Utility tool that acts like a subsystem that can be set to either wait for a second or until a function is called
 * Is able to halt autos
 */
public class Debugger implements Subsystem {


    public enum Config {PAUSEFORTIME, PAUSEUNTILPRESS}
    Config config;
    public boolean active = false;
    long startTime;
    double completion = 1;


    public Debugger(int config) {
        switch (config) {
            case 0:
                this.config = Config.PAUSEFORTIME;
                break;
            case 1:
                this.config = Config.PAUSEUNTILPRESS;
                break;
        }
    }
    public Debugger(Config c) {
        this.config = c;
    }
    public Debugger() {
        this(Config.PAUSEFORTIME);
    }

    /**
     * If in waiting for time mode, checks to see if the time has elapsed, then sets completion to 0
     */
    public void update() {
        if (config == Config.PAUSEFORTIME && completion == 0) {
            if (System.currentTimeMillis() - startTime >= 1000) {
                completion = 1;
            }
        }
    }

    @Override
    public void zero() {

    }

    public double getCompletion() {
        return completion;
    }

    /**
     * If debugger is active, sets completion to 0 and if waiting for time, starts the timer
     */
    public void pause() {
        if (active) {
            completion = 0;
            if (config == Config.PAUSEFORTIME) {
                startTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * Sets the completion to 1, allows the auto to continue
     */
    public void reset() {
        completion = 1;
    }

    /**
     * Toggles whether the debugger is stopping the auto or not, default is true
     */
    public void toggleActivity() {
        this.active = !active;
    }

    @Override
    public String toString() {
        String output = String.format(Locale.US, "Debugger; Config: %s; Is Active: %b; Completion: %f;", config, active, completion);
        if (completion == 0 && config == Config.PAUSEFORTIME) {
            output += " Time Left: " + (System.currentTimeMillis() - startTime);
        }
        return output;
    }

}
