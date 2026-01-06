package org.firstinspires.ftc.teamcode.utils;

import androidx.annotation.NonNull;

import com.pedropathing.geometry.Pose;


/**
 * Custom Pose Object (Rabbi pOSE)
 *
 * @author Ari Yellin Levine (Gann '26)
 */

public class Rose {
    public double x;
    public double y;
    public double h;

    /**
     * Creates a new Rose object
     * @param x Robot's X coord
     * @param y Robot's Y coord
     * @param h Robot's heading
     */
    public Rose(double x, double y, double h) {
        this.x = x;
        this.y = y;
        this.h = h;
    }
    /**
     * Creates a new Rose object with 0 heading
     * @param x Robot's X coord
     * @param y Robot's Y coord
     */
    public Rose(double x, double y) {
        this(x, y, 0);
    }

    /**
     * Creates a new Rose object at (0,0) with heading 0
     */
    public Rose() {
        this(0, 0);
    }

    /**
     * Converts coords from FTC standard system to pedro pathing system
     * @return Pedro Pose object with the same position in the Pedro coord system.
     */
    public Pose toPedro() {
        return new Pose(x+72, y+72, h);
    }
    
    @NonNull
    public String toString() {
        return "Rose: X: " + x + " Y: " + y + " H: " + h;
    }
}

