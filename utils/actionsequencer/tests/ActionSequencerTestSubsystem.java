package org.firstinspires.ftc.teamcode.utils.actionsequencer.tests;

import com.actionsequencer.ActionSequencerSubsystem;

/**
 * Fake subsystem to test the Action Sequencer
 * Can name it names of real subsystem
 * Has "actions" that print out complete number from 0 to preset number
 * Name the actions to make it easier to follow
 */
public class ActionSequencerTestSubsystem implements ActionSequencerSubsystem {
    double counter = 0;
    boolean running = false;
    double completion = 0;
    public String name;
    int stop = 10;
    String actionName;

    public ActionSequencerTestSubsystem(String name) {
        this.name = name;
    }
    public ActionSequencerTestSubsystem() {
        super();
    }

    public void update() {

        if (running) {
            counter++;

            completion = counter / stop;

            //NOT DEBUGGING; IS THE ACTION

            System.out.println(name + ": " + actionName + ": " + counter + " Completion: " + completion);
            if (counter >= stop) {
                running = false;
                counter = 0;
            }
        }

    }

    @Override
    public double getCompletion() {
        return completion;
    }


    public void action(String name) {
        action(10, name);
    }
    public void action(int stop) {
        action(stop, "");
    }
    public void action(int stop, String name) {
        running = true;
        this.stop = stop;
        actionName = name;
        completion = 0;
        counter = 0;
    }
    public void action() {
        action(10, "Counter");
    }

    @Override
    public String toString() {
        return name + ": " + actionName + "; Completion Percent: " + completion;
    }

}
