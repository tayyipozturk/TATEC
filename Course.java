import java.io.File;
import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Course {
    private static final ArrayList<Course> allCourses = new ArrayList<>();
    private static final ArrayList<Student> allStudents = new ArrayList<>();
    private static double coefficient;
    private final String courseName;
    private final int capacity;
    private final int order;
    private final ArrayList<Student> registeredStudents = new ArrayList<>();
    private final ArrayList<Student> randomlyRegisteredStudents = new ArrayList<>();

    public Course(String courseName, int capacity, int order) {
        this.courseName = courseName;
        this.capacity = capacity;
        this.order = order;
        allCourses.add(this);
    }

    public Course(Course other){
        this.courseName = other.courseName;
        this.capacity = other.capacity;
        this.order = other.order;
    }

    public String toString() {
        return courseName + ", " + registeredStudents.stream().map(Student::getStudentId).collect(Collectors.joining(", "));
    }

    public String randomToString() {
        return courseName + ", " + randomlyRegisteredStudents.stream().map(Student::getStudentId).collect(Collectors.joining(", "));
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public static void setAllStudents(ArrayList<Student> allStudents) {
        Course.allStudents.clear();
        Course.allStudents.addAll(allStudents);
    }

    public static ArrayList<Course> getAllCourses() {
        return allCourses;
    }

    public void registerStudent(Student student, boolean isRandom) {
        if (isRandom){
            randomlyRegisteredStudents.add(student);
            student.enroll(this, isRandom);
        } else {
            registeredStudents.add(student);
            student.enroll(this, isRandom);
        }
    }

    public ArrayList<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public ArrayList<Student> getRandomlyRegisteredStudents() {
        return randomlyRegisteredStudents;
    }

    public int getNumStudents() {
        return registeredStudents.size();
    }

    public int getOrder() {
        return order;
    }

    public static void setCoefficient(double coefficient) {
        Course.coefficient = coefficient;
    }

    public static double getCoefficient() {
        return coefficient;
    }

    public void registerStudents() {
        allStudents.stream()
                .sorted(Comparator.comparing(student -> student.getTokenOfCourse(order), Comparator.reverseOrder()))
                .filter(student -> student.getTokenOfCourse(order) > 0)
                .limit(capacity)
                .forEach(student -> registerStudent(student, false));

        allStudents.stream()
                .sorted(Comparator.comparing(student -> student.getTokenOfCourse(order), Comparator.reverseOrder()))
                .skip(registeredStudents.size())
                .filter(student -> student.getTokenOfCourse(order) == registeredStudents.get(registeredStudents.size() - 1).getTokenOfCourse(order))
                .forEach(student -> registerStudent(student, true));

    }

    public void registerStudentsRandomly() {
        Collections.shuffle(allStudents);
        allStudents.stream()
                .filter(student -> student.getTokenOfCourse(order) > 0 && !randomlyRegisteredStudents.contains(student))
                .limit(capacity)
                .forEach(student -> registerStudent(student, true));
    }

    public static double calculateUnhappiness(boolean isRandom) {
        return allStudents.stream()
                .mapToDouble(student -> student.calculateUnhappiness(coefficient, isRandom))
                .sum() / allStudents.size();
    }

    public static void writeRegistration(String tatecFile, String randomFile) {
        try {
            Files.write(
                    new File(tatecFile).toPath(),
                    allCourses.stream()
                            .flatMap(course -> Stream.of(course.toString()))
                            .collect(Collectors.toList())
            );

            Files.write(
                    new File(randomFile).toPath(),
                    allCourses.stream()
                            .flatMap(course -> Stream.of(course.randomToString()))
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeUnhappiness(String tatecFile, String randomFile){
        try {
            Files.write(
                    new File(tatecFile).toPath(),
                    Stream.of(String.valueOf(calculateUnhappiness(false)))
                            .collect(Collectors.toList()));

            Files.write(
                    new File(randomFile).toPath(),
                    Stream.of(String.valueOf(calculateUnhappiness(true)))
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
