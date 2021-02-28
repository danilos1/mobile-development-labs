package ua.kpi.comsys.io8324.tabfragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import info.hoang8f.android.segmented.SegmentedGroup;
import ua.kpi.comsys.io8324.R;

public class DrawingFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    private SegmentedGroup drawingSegmented;
    private LineChart lineChart;
    private PieChart pieChart;
    public static final int N = 100;
    public static final float DELTA = 0.5f;
    public static final float[] PIE_DATA = {10, 20, 25, 5, 40};
    public static final List<Integer> PIE_COLORS = new ArrayList<>(Arrays.asList(
            Color.YELLOW, Color.GREEN, Color.parseColor("#000080"), Color.RED, Color.BLUE
    ));

    public DrawingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawing, container, false);


        drawingSegmented = view.findViewById(R.id.segmented);
        drawingSegmented.setOnCheckedChangeListener(this);

        lineChart = view.findViewById(R.id.line_chart);
        lineChart.setNoDataText("Please select the corresponding option on switch button");

        pieChart = view.findViewById(R.id.pie_chart);
        pieChart.setNoDataText("Please select the corresponding option on switch button");

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Description chartDescription = new Description();
        chartDescription.setText("");

        switch (checkedId) {
            case R.id.lineChartButton:
                pieChart.setVisibility(View.GONE);
                lineChart.setVisibility(View.VISIBLE);
                LineDataSet lineDataSet = new LineDataSet(loadLineData(), "Data set");
                lineDataSet.setDrawCircles(false);
                lineDataSet.setColor(Color.MAGENTA);
                lineDataSet.setLineWidth(1.5f);
                LineData lineData = new LineData(lineDataSet);

                lineChart.setDescription(chartDescription);
                lineChart.getLegend().setEnabled(false);
                lineChart.setData(lineData);
                lineChart.invalidate();

                break;
            case R.id.pieChartButton:
                lineChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                PieDataSet pieDataSet = new PieDataSet(loadPieData(PIE_DATA), "");
                pieDataSet.setColors(PIE_COLORS);
                PieData pieData = new PieData(pieDataSet);
                pieData.setDrawValues(false);
                pieChart.setDescription(chartDescription);
                pieChart.setData(pieData);
                pieChart.getLegend().setEnabled(false);
                pieChart.invalidate();

                break;
            default:

        }
    }

    private ArrayList<Entry> loadLineData() {
        ArrayList<Entry> dataSet = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            float x = i + DELTA;
            dataSet.add(new Entry(x, (float) Math.log(x)));
        }

        return dataSet;
    }

    private ArrayList<PieEntry> loadPieData(float ... percents) {
        ArrayList<PieEntry> pieDataSet = new ArrayList<>();
        for (int i = 0; i < percents.length; i++) {
            pieDataSet.add(new PieEntry(percents[i], percents[i]  + " %"));
        }

        return pieDataSet;
    }
}