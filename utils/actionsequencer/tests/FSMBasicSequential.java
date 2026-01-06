package org.firstinspires.ftc.teamcode.utils.actionsequencer.tests;

import com.actionsequencer.Action;
import com.actionsequencer.ActionSequencer;


/**
*<h1>Finite State Machine basic example running through 3 actions</h1>
 */
public class FSMBasicSequential {

    static ActionSequencer actionSequencer;
    static ActionSequencerTestSubsystem sub1 = new ActionSequencerTestSubsystem( "Subsystem One");
    static ActionSequencerTestSubsystem sub2 = new ActionSequencerTestSubsystem( "Subsystem Two");
    static ActionSequencerTestSubsystem sub3 = new ActionSequencerTestSubsystem("Subsystem Three");

    public static void main(String[] args) {
        actionSequencer = new ActionSequencer();

        sub3.completion = 1;


        Action act1 = () -> sub1.action("Action 1");
        Action act2 = () -> sub2.action("Action 2");
        Action done = () -> System.out.println("Done");

        actionSequencer
                .addAction(sub1, act1, 0.4)
                .addAction(sub2, act2, 0.7)
                .addAction(sub3, done, 1);


        actionSequencer.start();


        while (actionSequencer.update()) {
                sub1.update();
                sub2.update();
                sub3.update();
        }
    }

}
