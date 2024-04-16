package com.example.binbolehxfirebase;

public class MonthData {
    private String name; // The name of the month, e.g., "January"
    private float totalPercentage; // The percentage value for the month

    public MonthData() {
        // Default constructor required for calls to DataSnapshot.getValue(MonthData.class)
    }

    public MonthData(String name, float totalPercentage) {
        this.name = name;
        this.totalPercentage = totalPercentage;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTotalPercentage() {
        return totalPercentage;
    }

    public void setTotalPercentage(float totalPercentage) {
        this.totalPercentage = totalPercentage;
    }
}

