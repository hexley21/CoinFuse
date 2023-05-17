package com.hxl.coinfuse.ui.fragments.coins.details.price.graph;

import androidx.annotation.ColorInt;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class LineChartUtil {

    private final LineChart lineChart;
    @ColorInt
    private final int chartColor;
    @ColorInt
    private final int textColor;

    public LineChartUtil(LineChart lineChart, int chartColor, int textColor) {
        this.lineChart = lineChart;
        this.chartColor = chartColor;
        this.textColor = textColor;
    }

    public void drawLineGraph() {
        Description chartDescription = new Description();
        chartDescription.setText("");
        lineChart.setDescription(chartDescription);
        lineChart.setDrawGridBackground(false);
        lineChart.setMaxHighlightDistance(300);

        // Disable any kind of interaction with graph
        lineChart.setDragEnabled(true);
        lineChart.setScaleXEnabled(true);
        lineChart.setScaleYEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDoubleTapToZoomEnabled(false);

        // Config X (horizontal) axis
        XAxis x = lineChart.getXAxis();
        x.setLabelCount(6);
        x.setTextColor(textColor);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(textColor);

        // Config Y (vertical) axis
        YAxis y = lineChart.getAxisLeft();
        y.setLabelCount(4, false);
        y.setTextColor(textColor);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(textColor);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
    }

    public void setData(ArrayList<Entry> entries) {
        LineDataSet lineDataSet= new LineDataSet(entries, "");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);

        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawCircles(false);

        lineDataSet.setLineWidth(1.8f);
        lineDataSet.setCircleRadius(4f);

        lineDataSet.setColor(chartColor);

        lineDataSet.setFillColor(chartColor);
        lineDataSet.setFillAlpha(100);

        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setFillFormatter((dataSet, dataProvider) -> lineChart.getAxisLeft().getAxisMinimum());

        lineChart.setData(data);
        lineChart.invalidate();
    }

    public void setValueFormatter(ValueFormatter valueFormatter) {
        lineChart.getXAxis().setValueFormatter(valueFormatter);
    }
}