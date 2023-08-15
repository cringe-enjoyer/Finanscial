package com.example.fin.model;

import com.example.fin.database.Sqlite;
import com.example.fin.utils.DateUtils;
import javafx.scene.chart.*;

import java.util.List;

/**
 * Class for displaying old financial cushions to the user
 */
public class CushionChart {
    private XYChart.Series<String, Number> data;
    private LineChart<String, Number> chart;

    public CushionChart(LineChart<String, Number> chart) {
        this.chart = chart;
        data = new XYChart.Series<>();
        data.setName("Финансовая подушка");
        chart.getData().clear();
        chart.getData().add(data);
    }

    public void updateData() {
        data.getData().clear();
        List<Cushion> cushions = Sqlite.getOldCushions();

        if (cushions != null) {
            for (Cushion cushion : cushions)
                data.getData().add(new XYChart.Data<>(DateUtils.dateToString(cushion.getUpdateDate()), cushion.getSum()));
        }
    }
}
