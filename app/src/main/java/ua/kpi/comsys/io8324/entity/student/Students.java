package ua.kpi.comsys.io8324.entity.student;

import java.util.*;

public class Students {
    public static List<Integer> getRandomPoints(Integer[] maxPoints) {
        List<Integer> points = new ArrayList<>();
        for (Integer maxPoint : maxPoints) {
            points.add(randomValue(maxPoint));
        }

        return points;
    }

    public static int randomValue(int maxValue) {
        Random random = new Random();
        switch(random.nextInt(6) + 1) {
            case 1:
                return (int) Math.ceil(maxValue * 0.7);
            case 2:
                return (int) Math.ceil(maxValue * 0.9);
            case 3:
            case 4:
            case 5:
                return maxValue;
            default:
                return 0;
        }
    }
}
