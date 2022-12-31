import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tatec
{
    private static final int CORRECT_TOTAL_TOKEN_PER_STUDENT = 100;
    private static final String OUT_TATEC_UNHAPPY = "unhappyOutTATEC.txt";
    private static final String OUT_TATEC_ADMISSION = "admissionOutTATEC.txt";
    private static final String OUT_RAND_UNHAPPY = "unhappyOutRANDOM.txt";
    private static final String OUT_RAND_ADMISSION = "admissionOutRANDOM.txt";

    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Course> courses = new ArrayList<>();

    private static void parse(String courseFilePath, String studentIdFilePath, String tokenFilePath) {
        try (Stream<String> lines = Files.lines(Paths.get(courseFilePath))) {
            lines.forEach(line -> {
                String[] parts = line.split("\\s+");
                String courseName = parts[0];
                int capacity = Integer.parseInt(parts[1]);
                Course course = new Course(courseName, capacity);
                courses.add(course);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<String> lines = Files.lines(Paths.get(studentIdFilePath))) {
            lines.forEach(line -> {
                Student student = new Student(line);
                students.add(student);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<ArrayList<Integer>> tokens = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(tokenFilePath))){
            lines.forEach(line -> {
                String[] parts = line.split(",");
                ArrayList<Integer> token = new ArrayList<>();
                for (String part : parts) {
                    token.add(Integer.parseInt(part));
                }
                tokens.add(token);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < students.size(); i++) {
            students.get(i).setTokens(tokens.get(i));
        }

        for (Student student : students) {
            System.out.println(student.toString());
        }
    }

    public static void main(String args[])
    {
        if(args.length < 4)
        {
            System.err.println("Not enough arguments!");
            return;
        }

        // File Paths
        String courseFilePath = args[0];
        String studentIdFilePath = args[1];
        String tokenFilePath = args[2];
        double h;

        try {
            h = Double.parseDouble(args[3]);
        }
        catch (NumberFormatException ex)
        {
            System.err.println("4th argument is not a double!");
            return;
        }

        parse(courseFilePath, studentIdFilePath, tokenFilePath);

    }

}
