package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Tatec
{
    private static final int CORRECT_TOTAL_TOKEN_PER_STUDENT = 100;
    private static final String OUT_TATEC_UNHAPPY = "io/output/unhappyOutTATEC.txt";
    private static final String OUT_TATEC_ADMISSION = "io/output/admissionOutTATEC.txt";
    private static final String OUT_RAND_UNHAPPY = "io/output/unhappyOutRANDOM.txt";
    private static final String OUT_RAND_ADMISSION = "io/output/admissionOutRANDOM.txt";

    private static final ArrayList<Student> students = new ArrayList<>();
    private static final ArrayList<Course> courses = new ArrayList<>();

    private static void parse(String courseFilePath, String studentIdFilePath, String tokenFilePath) {
        try (Stream<String> lines = Files.lines(Paths.get(courseFilePath))) {
            var ref = new Object() {
                int i = 0;
            };
            lines.forEach(line -> {
                String[] tokens = line.split(",");
                courses.add(new Course(tokens[0], Integer.parseInt(tokens[1]), ref.i++));
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

        try{
            students.stream().forEach(student -> student.isTokenDistributionValid(CORRECT_TOTAL_TOKEN_PER_STUDENT));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }

        Student.setAllCourses(courses);
        Course.setAllStudents(students);
    }

    public static void registerStudentsByToken(){
        courses.stream()
                .forEach(Course::registerStudents);
        Course.writeRegistrationToFile(OUT_TATEC_ADMISSION, false);
        Course.writeUnhappinessToFile(OUT_TATEC_UNHAPPY, false);
    }

    public static void registerStudentsRandomly(){
        courses.stream()
                .forEach(Course::registerStudentsRandomly);
        Course.writeRegistrationToFile(OUT_RAND_ADMISSION, true);
        Course.writeUnhappinessToFile(OUT_RAND_UNHAPPY, true);
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
        Course.setCoefficient(h);

        registerStudentsByToken();
        registerStudentsRandomly();
    }
}
