package com.example.fin.model;

import com.example.fin.database.Sqlite;
import com.example.fin.utils.DateUtils;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

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
            for (Cushion cushion : cushions) {
                XYChart.Data node = new XYChart.Data(DateUtils.dateToString(cushion.getUpdateDate()), cushion.getSum());
                node.setNode(new HoveredThresholdNode(cushion.getSum()));
                data.getData().add(node);
            }
        }
    }

    //https://gist.github.com/jewelsea/4681797
    class HoveredThresholdNode extends StackPane {
        HoveredThresholdNode(double value) {
            setPrefSize(10, 10);

            final Label label = createDataThresholdLabel(value);

            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getChildren().setAll(label);
                    setCursor(Cursor.NONE);
                    toFront();
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getChildren().clear();
                    setCursor(Cursor.CROSSHAIR);
                }
            });
        }

        private Label createDataThresholdLabel(double value) {
            final Label label = new Label(value + "");
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
            label.setTextFill(Color.BLACK);
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }
    }
}
