package ua.kpi.comsys.io8324.entity.student;

import java.util.List;

public class Student {
    private final String name;
    private List<Integer> points;

    public Student(String name) {
        this.name = name;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public void setPoints(List<Integer> points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
