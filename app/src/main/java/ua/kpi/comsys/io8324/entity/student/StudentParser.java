package ua.kpi.comsys.io8324.entity.student;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class StudentParser {
    private static final Collator collator = Collator.getInstance(new Locale("uk", "UA"));
    private final String studentsRow;
    private final List<Student> students;
    private final Map<Student, String> studentsWithGroups;

    public StudentParser(String studentsRow) {
        this.studentsRow = studentsRow;
        this.studentsWithGroups = parse(studentsRow);
        this.students = new ArrayList<>(studentsWithGroups.keySet());
    }

    public List<Student> getStudents() {
        return students;
    }

    public Map<String, List<Student>> collectToGroups() {
        Set<String> groups = new HashSet<>(this.getGroups());
        Map<String, List<Student>> groupMap = new HashMap<>();

        for (String group: groups) {
            List<Student> s = studentsWithGroups.entrySet().stream()
                    .filter(student -> student.getValue().equals(group))
                    .map(Map.Entry::getKey).sorted((s1, s2) -> collator.compare(s1.getName(), s2.getName()))
                    .collect(Collectors.toList());

            groupMap.put(group, s);
        }

        return groupMap;
    }

    public Map<String, Double> collectToGroupAverageMarks(Map<String, Map<Student, Integer>> studentsWithFinalMarks) {
        Map<String, Double> groupAvgMarks = new HashMap<>();
        for (Map.Entry<String, Map<Student, Integer>> studentEntrySetWithMarks: studentsWithFinalMarks.entrySet()) {
            Map<Student, Integer> studentSet = studentEntrySetWithMarks.getValue();
            Double avg = studentSet.values().stream().mapToDouble(Integer::doubleValue).average().getAsDouble();
            groupAvgMarks.put(studentEntrySetWithMarks.getKey(), avg);
        }
        return groupAvgMarks;
    }

    public Map<String, Map<Student, Integer>> collectToGroupWithFinalMarks(Map<String, Map<Student,
            List<Integer>>> studentsWithPointsByGroups) {
        Map<String, Map<Student, Integer>> studentsWithMarksByGroup = new HashMap<>();

        for (Map.Entry<String, Map<Student, List<Integer>>> studentsWithPoints: studentsWithPointsByGroups.entrySet()) {
            Map<Student, Integer> studentsWithSumMarks = new HashMap<>();
            for (Map.Entry<Student, List<Integer>> studentIntegerMap: studentsWithPoints.getValue().entrySet()) {
                studentsWithSumMarks.put(
                        studentIntegerMap.getKey(),
                        studentIntegerMap.getValue().stream()
                                .mapToInt(Integer::intValue)
                                .sum()
                );
            }
            studentsWithMarksByGroup.put(studentsWithPoints.getKey(), studentsWithSumMarks);
        }

        return studentsWithMarksByGroup;
    }

    public Map<String, List<Student>> filterStudents(Map<String, Map<Student, Integer>> studentsWithFinalMarks,
                                                     int floor) {
        Map<String, List<Student>> filteredStudentMap = new HashMap<>();
        for (Map.Entry<String, Map<Student, Integer>> studentSetWithMarks: studentsWithFinalMarks.entrySet()) {
            List<Student> students = studentSetWithMarks.getValue().entrySet().stream()
                    .filter(x -> x.getValue() >= floor)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            filteredStudentMap.put(studentSetWithMarks.getKey(), students);
        }

        return filteredStudentMap;
    }

    public Map<String, Map<Student, List<Integer>>> collectToGroupWithMarks(Integer[] maxPoints) {
        Map<String, Map<Student, List<Integer>>> studentsWithMarksByGroups = new HashMap<>();
        Map<String, List<Student>> groupMap = this.collectToGroups();

        for (Map.Entry<String, List<Student>> gm: groupMap.entrySet()) {
            Map<Student, List<Integer>> studentsWithMarks = gm.getValue().stream()
                    .collect(Collectors.toMap(
                            student -> student, student -> Students.getRandomPoints(maxPoints)
                    )
            );
            studentsWithMarksByGroups.put(gm.getKey(), studentsWithMarks);
        }

        return studentsWithMarksByGroups;
    }

    private Map<Student, String> parse(String studentsRow) {
        if (studentsRow == null) {
            return null;
        }

        String[] s = studentsRow.split(";");
        return Arrays.stream(s)
                .map(student -> student.split("-"))
                .collect(Collectors.toMap(strings -> new Student(strings[0].trim()), strings -> strings[1].trim()));
    }

    public Collection<String> getGroups() {
        return this.studentsWithGroups.values();
    }

    public String getInputRow() {
        return studentsRow;
    }

    public Map<Student, String> getStudentsWithGroups() {
        return studentsWithGroups;
    }

}
