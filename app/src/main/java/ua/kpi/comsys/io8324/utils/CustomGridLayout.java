package ua.kpi.comsys.io8324.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import ua.kpi.comsys.io8324.R;

public class CustomGridLayout {
    private Activity activity;
    private View view;
    private ImageView[] imageGrid;
    private LinearLayout[] linearLayoutGrid;
    private int[] heightDimension;
    private int[] widthDimension;

    public ImageView[] getImageGrid() {
        return imageGrid;
    }

    public LinearLayout[] getLinearLayoutGrid() {
        return linearLayoutGrid;
    }

    public int[] getHeightDimension() {
        return heightDimension;
    }

    public int[] getWidthDimension() {
        return widthDimension;
    }

    public class DimensionBuilder {
        private int start;

        DimensionBuilder(int start) {
            this.start = start;
        }

        private int[] buildWidths() {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int x = dm.widthPixels;

            int bigChunk = (int)(x - x/2.477);
            int midChunk = x - bigChunk;
            int smallChunk = bigChunk/3;

            return new int[]{bigChunk, midChunk, midChunk, smallChunk, smallChunk, smallChunk};
        }

        private int[] buildHeights() {
            if (ActivityHelper.isPortraitOrientation(activity)) {
                return getHeightDimension(start, 185);
            }

            return getHeightDimension(start + 200, 225);
        }
        private int[] getHeightDimension(int start, int delta) {
            int[] dimensionalRow = new int[6];
            for (int i = 0, s = 0; i < dimensionalRow.length; i++, s++) {
                for (int j = i; j <= i; j++) {
                    for (int k = 0; k <= j; k++, i++) {
                        dimensionalRow[i] = start - j * delta;
                    }

                    if (i == dimensionalRow.length) {
                        break;
                    }
                }
            }

            return dimensionalRow;
        }
    }

    public CustomGridLayout(int gridResolution, Activity activity, View view) {
        this.activity = activity;
        this.view = view;
        this.imageGrid = new ImageView[gridResolution];
        this.linearLayoutGrid = new LinearLayout[gridResolution >> 1];
        gridInit();
    }

    private void gridInit() {
        DimensionBuilder dimensionBuilder = new DimensionBuilder(580);
        this.heightDimension = dimensionBuilder.buildHeights();
        this.widthDimension = dimensionBuilder.buildWidths();

        for (int i = 0; i < 3; i++) {
            linearLayoutGrid[i] = view.findViewById(
                ActivityHelper.getResourceByString(String.format("linear_layout%d", i + 1))
            );
        }

        for (int i = 0; i < 6; i++) {
            imageGrid[i] = view.findViewById(
                ActivityHelper.getResourceByString(String.format("grid_image%d", i + 1))
            );
        }
    }
}
