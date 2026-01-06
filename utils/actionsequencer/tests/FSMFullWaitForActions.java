package org.firstinspires.ftc.teamcode.utils.actionsequencer.tests;

import com.actionsequencer.Action;
import com.actionsequencer.ActionSequencer;

/**
 * Full mock test of the action sequencer. Three mock named subsystems, named actions, testing full range of motion
 * of the action sequencer
 *
 * @author Ari Yellin-Levine
 */
//Problem with waiting till completion of 1, gets reset before it checks. might be a problem in real life, might not. (subsystems will probably wait at 1, no?)
//sometimes waits a loop (continue is .4, runs .5 before new action. probably not problem because fast loops and robot different)
public class FSMFullWaitForActions {

    static ActionSequencer actionSequencer;
    static ActionSequencerTestSubsystem slides = new ActionSequencerTestSubsystem("Slides");
    static ActionSequencerTestSubsystem claw = new ActionSequencerTestSubsystem("Claw");
    static ActionSequencerTestSubsystem drive = new ActionSequencerTestSubsystem("Drive");


    public static void main(String[] args) {
        actionSequencer = new ActionSequencer();

        Action intake = () -> drive.action(10, "Drive Intake");
        Action slidesUp = () -> slides.action(10, "Slides Up");
        Action open = () -> claw.action(2, "Open");
        Action close = () -> claw.action(3, "Close");
        Action slidesDown = () -> slides.action(10, "Slides Down");
        Action score = () -> drive.action(10, "Drive Score");
        Action slidesScore = () -> slides.action(5, "Slides Score");
        Action park = () ->  drive.action(10, "Drive Park");
        Action teleop = () -> slides.action(10, "Slides Tele");

        actionSequencer
                .addAction(drive, intake, 0.4)
                .addAction(slides, slidesUp, .3)
                .addAction(claw, open)
                .addAction(claw, close, 1, true)
                .addAction(slides, slidesDown, .6)
                .addAction(drive, score, .1)
                .addAction(slides, slidesScore, 0.7)
                .addAction(claw, open, 1, true)
                .addAction(drive, park, 0.7)
                .addAction(slides, teleop);


        actionSequencer.start();

        while (actionSequencer.update()) {
            slides.update();
            claw.update();
            drive.update();
        }
    }
}
