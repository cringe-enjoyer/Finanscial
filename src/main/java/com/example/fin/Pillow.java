package com.example.fin;

import com.example.fin.utils.DateUtils;

import java.text.ParseException;
import java.util.Calendar;

/**
 * User's financial pillow
 */
public class Pillow {
    private double sum;
    /**
     * Date of next salary
     */
    private Calendar updateDay;
    private double percent;
    private int minVal;
    private double salary;

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Calendar getUpdateDay() {
        return updateDay;
    }

    public void setUpdateDay(Calendar updateDay) {
        this.updateDay = updateDay;
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

    public Pillow(double sum, Calendar updateDay, double percent, int minVal, double salary) {
        this.sum = sum;
        this.updateDay = updateDay;
        this.percent = percent;
        this.minVal = minVal;
        this.salary = salary;
    }

    public Pillow(double sum, String updateDay, double percent, int minVal, double salary) {
        try {
            this.sum = sum;
            this.updateDay = DateUtils.stringToDate(updateDay);
            this.percent = percent;
            this.minVal = minVal;
            this.salary = salary;
        } catch (Exception ex) {

        }
    }

    @Override
    public String toString() {
        return "sum=" + sum +
                ", updateDay=" + updateDay +
                ", percent=" + percent +
                ", minVal=" + minVal +
                ", salary=" + salary;
    }
}
