import java.util.ArrayList;

public class Student {
    private final String studentId;
    private final ArrayList<Integer> tokens = new ArrayList<>();
    private int totalTokens;
    private final ArrayList<Course> enrolledCourses = new ArrayList<>();
    private final ArrayList<Course> randomlyEnrolledCourses = new ArrayList<>();

    public Student(String studentId) {
        this.studentId = studentId;
        totalTokens = 0;
    }

    public Student(String studentId, ArrayList<Integer> tokens) {
        this.studentId = studentId;
        this.tokens.addAll(tokens);
        totalTokens = tokens.stream().reduce(0, Integer::sum);
    }

    public String toString() {
        return studentId + " " + tokens + " " + totalTokens + " ";
    }

    public String getStudentId() {
        return studentId;
    }

    public void addToken(int token) {
        tokens.add(token);
        totalTokens += token;
    }

    public ArrayList<Integer> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Integer> tokens) {
        this.tokens.clear();
        this.tokens.addAll(tokens);
        totalTokens = tokens.stream().reduce(0, Integer::sum);
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public void enroll(Course course, boolean isRandom) {
        if (isRandom){
            randomlyEnrolledCourses.add(course);
        } else {
            enrolledCourses.add(course);
        }
    }

    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public int getTokenOfCourse(int order) {
        return tokens.get(order);
    }

    public void isTokenDistributionValid(int totalTokens) {
        if (this.totalTokens != totalTokens){
            throw new IllegalArgumentException("Token distribution is not valid for student " + studentId);
        }
    }
}
