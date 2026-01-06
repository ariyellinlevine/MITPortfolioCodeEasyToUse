package org.firstinspires.ftc.teamcode.utils;


import org.firstinspires.ftc.teamcode.utils.actionsequencer.ActionSequencerSubsystem;

/**
 * Skeleton of all subsystems, helps with development, as well as guaranteeing that methods exist.
 */
public interface Subsystem extends ActionSequencerSubsystem {
    /**
     * Updates the subsystem
     */
    void update();

    void zero();
}
