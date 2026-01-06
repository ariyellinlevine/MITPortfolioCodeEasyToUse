package org.firstinspires.ftc.teamcode.utils.actionsequencer;

import java.util.ArrayList;

/**
 * A state machine that executes a sequence of actions sequentially.
 * <p>
 * Each action is associated with a subsystem and a completion threshold.
 * The sequencer advances to the next action when the current action's subsystem
 * reaches the required completion percentage.
 * </p>
 * <p>
 * Supports waiting for previous actions to finish before starting the next,
 * and running multiple actions concurrently.
 * </p>
 * <p>
 * Usage:
 * <pre>{@code
 * ActionSequencer sequencer = new ActionSequencer()
 *     .addAction(driveSubsystem, driveForwardAction, 0.9)
 *     .addAction(armSubsystem, raiseArmAction, 1.0, true);
 * sequencer.start();
 * while(sequencer.update()) {
 *   // update robot state, telemetry, etc.
 * }
 * }</pre>
 * </p>
 * <p>
 * Note: The completion percentage is expected to be in the range [0.0, 1.0].
 * </p>
 * @author Ari Yellin-Levine
 */
// TODO fix JavaDoc for ActionSequencer

// TODO think about whether i want to change percentages to absolute positons (3
// in away, 6 in away)
// TODO Need to allow while time allows statements (while you have enough time,
// get a sample, otherwise park)



public class ActionSequencer {

    ArrayList<SequencedAction> actions = new ArrayList<>();
    ArrayList<SequencedAction> activeActions = new ArrayList<>();
    int actionPointer = 0;

    public ActionSequencer() {
    }



    /**
     * Adds an action with an option to wait for previous actions to finish before starting.
     *
     * @param system          the subsystem performing the action
     * @param action          the action to execute
     * @param completion      the required completion percentage (0.0 to 1.0)
     *                        of the subsystem's task before moving to the next action
     * @param waitForPrevious if true, this action will not start until all previously
     *                        started actions have completed
     * @return this ActionSequencer instance for method chaining
     */

    public ActionSequencer addAction(ActionSequencerSubsystem system, Action action, double completion, boolean waitForPrevious) {
        SequencedAction sequencedAction = new SequencedAction() {
            @Override
            public void run() {
                action.run();
                activeActions.add(this);
            }
        };

        sequencedAction.setRequiredCompletionPercent(completion);
        sequencedAction.setSubsystem(system);
        sequencedAction.setWaitForPrevious(waitForPrevious);

        actions.add(sequencedAction);
        return this;
    }

    /**

     * Adds an action to the sequencer to be executed on the given subsystem.
     *
     * @param system     the subsystem performing the action
     * @param action     the action to execute
     * @param completion the required completion percentage (0.0 to 1.0)
     *                   of the subsystem's task before moving to the next action
     *
     * @return this ActionSequencer instance for method chaining
     */
    public ActionSequencer addAction(ActionSequencerSubsystem system, Action action, double completion) {
        return addAction(system, action, completion, false);
    }

    /**

     * Adds an action to the sequencer to be executed on the given subsystem with a default completion value of 1
     *
     * @param system     the subsystem performing the action
     * @param action     the action to execute
     *
     * @return this ActionSequencer instance for method chaining
     */
    public ActionSequencer addAction(ActionSequencerSubsystem system, Action action) {
        return addAction(system, action, 1, false);
    }

    /**
     * Starts the sequencer by running the first action.
     * <p>
     * Should be called once before entering the main loop that repeatedly calls {@link #update()}.
     * </p>
     */
    public void start() {
        if (!actions.isEmpty()) actions.get(actionPointer).runOnce();
    }

    /**
     * Updates the sequencer state, checking if the current action(s) have reached
     * their completion threshold and advancing the sequence accordingly.
     * <p>
     * Should be called repeatedly, typically in the main control loop.
     * </p>
     *
     * @return true if there are still actions to be completed; false if all actions
     *         have finished
     */

    // NOTE return true strat not great, while (update()) is blocking and you have to
    // copy your update code and stuff.
    // NOTE Although maybe we don't need to ever call anything after the sequencer is done, so we
    // can get away with it?
    public boolean update() {
        // NOTE Would skip an action if something is removed, might not be a problem due
        // to fast loop times

        activeActions.removeIf(a -> a.getSubsystem().getCompletion() >= 1);


        SequencedAction sequencedAction = actions.get(actionPointer);


        if (sequencedAction.getWaitForPrevious()) {
            if (!activeActions.isEmpty()) {
                return true;
            }

            sequencedAction.runOnce();
            sequencedAction.setWaitForPrevious(false); //kinda not what the values supposed to do, but it works

        }

        if (actionPointer >= actions.size() - 1) {
            return finish();
        }


        if (sequencedAction.isComplete()) {
            actionPointer++;
           //checks if the next action has to wait, runs it doesn't
            if (!actions.get(actionPointer).getWaitForPrevious()) actions.get(actionPointer).runOnce();
        }

        return true;
    }

    /**
     * Checks if all active actions have completed.
     *
     * @return true if any active actions are still running, false otherwise
     */
    private boolean finish() {
        return !activeActions.isEmpty();
    }
}
