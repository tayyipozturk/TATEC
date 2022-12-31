import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class Student {
    private String studentId;
    private ArrayList<Integer> tokens;
    private int totalTokens;

    public Student(String studentId) {
        this.studentId = studentId;
        tokens = new ArrayList<>();
        totalTokens = 0;
    }

    public Student(String studentId, ArrayList<Integer> tokens) {
        this.studentId = studentId;
        this.tokens = tokens;
        totalTokens = tokens.stream().reduce(0, Integer::sum);
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
        this.tokens = tokens;
        totalTokens = tokens.stream().reduce(0, Integer::sum);
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public String toString() {
        return studentId + " " + tokens + " " + totalTokens;
    }
}
