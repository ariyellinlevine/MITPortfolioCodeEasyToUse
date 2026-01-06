package org.firstinspires.ftc.teamcode.utils.actionsequencer;

/**
 * Functional interface representing an action to be executed.
 * <p>
 * Designed to be used with lambda expressions or method references.
 * </p>
 * Example:
 * <pre>{@code
        Action followPath1 = () -> driveBase.followPath(path1)
 *  }</pre>
 * @author Ari Yellin-Levine
 */
@FunctionalInterface
public interface Action {
    /**
     * Executes the action logic.
     */
    void run();
}
