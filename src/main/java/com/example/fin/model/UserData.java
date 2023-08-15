package com.example.fin.model;

import java.util.Objects;

/**
 * User data
 */
public class UserData {
    private Cushion cushion;
    private double percent;
    private int minVal;
    private double salary;

    public Cushion getCushion() {
        return cushion;
    }

    public void setCushion(Cushion cushion) {
        this.cushion = cushion;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getMinVal() {
        return minVal;
    }

    public void setMinVal(int minVal) {
        this.minVal = minVal;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public UserData(Cushion cushion, double percent, int minVal, double salary) {
        this.cushion = cushion;
        this.percent = percent / 100;
        this.minVal = minVal;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "cushion=" + cushion +
                ", percent=" + percent +
                ", minVal=" + minVal +
                ", salary=" + salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserData userData = (UserData) o;

        if (Double.compare(percent, userData.percent) != 0) return false;
        if (minVal != userData.minVal) return false;
        if (Double.compare(salary, userData.salary) != 0) return false;
        return Objects.equals(cushion, userData.cushion);
    }
}
