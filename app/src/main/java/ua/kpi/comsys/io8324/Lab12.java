package ua.kpi.comsys.io8324;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ua.kpi.comsys.io8324.entity.clock.TimeDS;
import ua.kpi.comsys.io8324.entity.student.Student;
import ua.kpi.comsys.io8324.entity.student.StudentParser;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Lab12 {
    public static void main(String[] args) {
        Integer[] points = {13, 12, 12, 12, 13, 12, 16};
        String studentsRow = "Дмитренко Олександр - ІП-84; Матвійчук Андрій - ІВ-83; Лесик Сергій - ІО-82; Ткаченко Ярослав - ІВ-83; Аверкова Анастасія - ІО-83; Соловйов Даніїл - ІО-83; Рахуба Вероніка - ІО-81; Кочерук Давид - ІВ-83; Лихацька Юлія- ІВ-82; Головенець Руслан - ІВ-83; Ющенко Андрій - ІО-82; Мінченко Володимир - ІП-83; Мартинюк Назар - ІО-82; Базова Лідія - ІВ-81; Снігурець Олег - ІВ-81; Роман Олександр - ІО-82; Дудка Максим - ІО-81; Кулініч Віталій - ІВ-81; Жуков Михайло - ІП-83; Грабко Михайло - ІВ-81; Мітячкін Денис Євгенійович - ІО-83; Іванов Володимир - ІО-81; Востриков Нікіта - ІО-82; Бондаренко Максим - ІВ-83; Скрипченко Володимир - ІВ-82; Кобук Назар - ІО-81; Дровнін Павло - ІВ-83; Тарасенко Юлія - ІО-82; Дрозд Світлана - ІВ-81; Фещенко Кирил - ІО-82; Крамар Віктор - ІО-83; Іванов Дмитро - ІВ-82";

        StudentParser studentParser = new StudentParser(studentsRow);

        System.out.println("Input string: "+studentsRow);

        System.out.println("---------------------------- Part 1 ----------------------------\n");
        // task 1
        System.out.println("============== Task 1 ==============");
        Map<String, List<Student>> groupMap = studentParser.collectToGroups();
        System.out.println(groupMap);

        // task 2
        System.out.println("\n============== Task 2 ==============");
        Map<String, Map<Student, List<Integer>>> studentsWithMarksByGroup = studentParser.collectToGroupWithMarks(points);
        System.out.println(studentsWithMarksByGroup);

        // task 3
        System.out.println("\n============== Task 3 ==============");
        Map<String, Map<Student, Integer>> studentsWithFinalMarks = studentParser.collectToGroupWithFinalMarks(
                studentsWithMarksByGroup
        );
        System.out.println(studentsWithFinalMarks);

        // task 4
        System.out.println("\n============== Task 4 ==============");
        Map<String, Double> groupAvgMarks = studentParser.collectToGroupAverageMarks(studentsWithFinalMarks);
        System.out.println(groupAvgMarks);

        // task 5
        System.out.println("\n============== Task 5 ==============");
        int floor = 85;
        Map<String, List<Student>>  successfulStudents = studentParser.filterStudents(studentsWithFinalMarks, floor);
        System.out.println(successfulStudents);

        System.out.println("\n---------------------------- Part 2 ----------------------------\n");

        TimeDS[] times = {
                new TimeDS(23, 59, 59),
                new TimeDS(12, 0, 1),
                new TimeDS(13, 27, 11),
                new TimeDS(1, 15, 29),
                new TimeDS(new Date()),
                new TimeDS(),
                new TimeDS(0, 0, 1)

        };
        for (int i = 0; i < times.length; i++) {
            System.out.printf("t%d: %s%n",i+1, times[i]);
        }

        System.out.println("\nVarious operation with TimeDS objects: ");
        System.out.println("t1 + t2: " + times[0].addTime(times[1]));
        System.out.println("t3 + t5: " + times[2].addTime(times[4]));
        System.out.println("t5 + t4: " + TimeDS.add(times[4], times[3]));
        System.out.println("t6 - t7: " + times[5].subtractTime(times[6]));
        System.out.println("t5 - t3: " + TimeDS.sub(times[4], times[2]));

        System.out.println("\nCreating TimeDS object with invalid data: ");
        TimeDS invalidTimeDSObject = new TimeDS(26, 19, 111);
    }
}
