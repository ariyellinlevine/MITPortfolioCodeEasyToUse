package org.firstinspires.ftc.teamcode.utils.actionsequencer;

import java.util.Locale;

/**
 * Represents an action associated with a subsystem, along with the completion
 * criteria for progressing to the next action in an {@link ActionSequencer}.
 *
 * <p>
 * Each {@code SequencedAction} is linked to a {@link ActionSequencerSubsystem}, and it defines:
 * <ul>
 *   <li>The completion percentage (from 0.0 to 1.0) required to proceed to the next action.</li>
 *   <li>Whether it should wait for all previously running actions to finish before executing.</li>
 *   <li>A {@link #run()} method that defines the action behavior, which is executed once.</li>
 * </ul>
 * </p>
 *
 * <p><b>Note:</b> You usually do not need to subclass {@code SequencedAction} directly.
 * {@link ActionSequencer} handles subclassing internally and supplies the {@link #run()} implementation
 * using a lambda-style {@link Action} interface. This class is used as a container to manage
 * sequencing metadata.</p>
 * @author Ari Yellin-Levine
 */

public abstract class SequencedAction {
    protected double requiredCompletionPercent = -1;
    protected ActionSequencerSubsystem subsystem;
    private boolean waitForPrevious = false;

    private boolean hasRun = false;

    /**
     * Gets the completion percentage required to proceed to the next action.
     *
     * @return the completion percentage (0.0 to 1.0)
     */
    public double getRequiredCompletionPercent() {
        return requiredCompletionPercent;
    }

    /**
     * Sets the subsystem this action operates on.
     *
     * @param subsystem the subsystem to associate with this action
     */
    public void setSubsystem(ActionSequencerSubsystem subsystem) {
        this.subsystem = subsystem;
    }

    /**
     * Gets the subsystem associated with this action.
     *
     * @return the subsystem associated with this action
     */

    public ActionSequencerSubsystem getSubsystem() {
        return subsystem;
    }

    /**
     * Sets the required completion percentage threshold to continue.
     *
     * @param value the completion percentage (0.0 to 1.0)
     */
    public void setRequiredCompletionPercent(double value) {
        this.requiredCompletionPercent = value;
    }

    /**
     * Sets whether this action should wait for previous actions to finish before starting.
     *
     * @param value true to wait for previous actions; false otherwise
     */
    public void setWaitForPrevious(boolean value) {
        waitForPrevious = value;
    }

    /**
     * Returns whether this action waits for previous actions before starting.
     *
     * @return true if waiting for previous actions; false otherwise
     */
    public boolean getWaitForPrevious() {
        return waitForPrevious;
    }


    /**
     * Checks if the action has reached its completion threshold.
     *
     * @return true if the associated subsystem's completion is greater than or equal
     *         to the required threshold; false otherwise
     */
    public boolean isComplete() {
        return subsystem.getCompletion() >= requiredCompletionPercent;
    }


    /**
     * Executes this action once if it hasn't been run yet.
     */
    public void runOnce() {
        if (!hasRun) {
            hasRun = true;
            run();
        }
    }

    /**
     * Defines the behavior of this action.
     * <p>
     * This method is implemented by {@link ActionSequencer} using the {@link Action} interface,
     * and should not be called manually.
     * </p>
     */
    public abstract void run();

    /**
     * Returns a string representation useful for debugging.
     *
     * @return string describing the subsystem, completion threshold, and wait flag
     */

    @Override
    public String toString() {
        return String.format(Locale.US, "Subsystem: %s Continue Percent: %f, Wait: %b", subsystem, requiredCompletionPercent, waitForPrevious);
    }
}
