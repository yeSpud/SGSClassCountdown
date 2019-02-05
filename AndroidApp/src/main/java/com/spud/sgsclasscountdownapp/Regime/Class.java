package com.spud.sgsclasscountdownapp.Regime;

/**
 * Created by Stephen Ogden on 2/4/19.
 * FRC 1595
 */
public class Class {

    private String name, customName = "";

    private long startTime, endTime;

    /**
     * (Non native java) Class constructor.
     *
     * @param name      The official name of the class.
     * @param startTime When the class starts (as a long).
     * @param endTime   When the class ends (as a long).
     */
    public Class(String name, long startTime, long endTime) {
        this.setName(name);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
    }

    /**
     * Get the time (as a long) of when the class starts.
     *
     * @return When the class.
     */
    public long getStartTime() {
        return this.startTime;
    }

    /**
     * Sets the time (as a long) of when the class starts.
     *
     * @param startTime The time when the class starts.
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Get the time (as a long) of when the class ends.
     *
     * @return The time when the class ends.
     */
    public long getEndTime() {
        return this.endTime;
    }

    /**
     * Set the time (as a long) of when the class ends.
     *
     * @param endTime The time when the class ends.
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * Get the name name of the class. If the boolean provided is true, the  it will return the custom name for the class rather than the official one.
     *
     * @param bool Whether or not to return the custom name for the class.
     * @return The class name.
     */
    public String getName(boolean bool) {
        return bool ? this.getCustomName() : this.name;
    }

    /**
     * Returns the custom name for the class. This may be empty an string!
     *
     * @return The custom class name.
     */
    private String getCustomName() {
        return this.customName;
    }

    /**
     * Sets the custom name for the class.
     *
     * @param customName The custom name.
     */
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    /**
     * Sets the official name for the class.
     *
     * @param name The official class name.
     */
    public void setName(String name) {
        this.name = name;
    }

}
