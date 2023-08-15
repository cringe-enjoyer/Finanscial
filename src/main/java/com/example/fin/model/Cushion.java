package com.example.fin.model;

import java.util.Calendar;

/**
 * User's financial cushion data
 */
public class Cushion {
    private double sum;
    /**
     * Date of next salary
     */
    private Calendar updateDate;

    public Cushion(double sum, Calendar updateDate) {
        this.sum = sum;
        this.updateDate = updateDate;
    }

    public Cushion(Calendar updateDate) {
        this.updateDate = updateDate;
    }

    public Cushion(double sum, int updateDay) {
        this.sum = sum;
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, updateDay);
        updateDate = date;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "sum=" + sum +
                ", updateDate=" + updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cushion cushion = (Cushion) o;

        if (Double.compare(sum, cushion.sum) != 0) return false;
        if (updateDate.get(Calendar.DAY_OF_MONTH) != cushion.updateDate.get(Calendar.DAY_OF_MONTH)) return false;
        if (updateDate.get(Calendar.MONTH) != cushion.updateDate.get(Calendar.MONTH)) return false;
        return updateDate.get(Calendar.YEAR) == cushion.updateDate.get(Calendar.YEAR);
    }
}
