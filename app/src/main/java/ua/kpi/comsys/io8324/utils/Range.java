package ua.kpi.comsys.io8324.utils;

import android.annotation.SuppressLint;

public class Range {
    private int start;
    private int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("[%d, %d]", start, end);
    }
}
