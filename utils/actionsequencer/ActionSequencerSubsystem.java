package org.firstinspires.ftc.teamcode.utils.actionsequencer;


/**
 * Represents a subsystem that can report its progress toward completing an action.
 * <p>
 * Used by the {@link ActionSequencer} to determine when an action is complete
 * and whether it can move on to the next step in the sequence.
 * </p>
 * <p>
 * Implementations should return a value between 0.0 (not started) and 1.0 (fully complete).
 * </p>
 * @author Ari Yellin-Levine
 */

public interface ActionSequencerSubsystem {
    double getCompletion();
}
