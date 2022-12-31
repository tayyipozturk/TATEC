import java.util.*;

public class Course {
    private static ArrayList<Course> allCourses = new ArrayList<>();
    private String courseName;
    private int capacity;
    private ArrayList<Student> students;

    public Course(String courseName, int capacity) {
        this.courseName = courseName;
        this.capacity = capacity;
        allCourses.add(this);
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public static ArrayList<Course> getAllCourses() {
        return allCourses;
    }

    public String toString() {
        return courseName + " " + capacity + " " + students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public int getNumStudents() {
        return students.size();
    }
}
