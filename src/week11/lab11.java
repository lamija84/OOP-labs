package week11;
import java.util.NoSuchElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class lab11 {

    class EmptyStudentListException extends RuntimeException {
        public EmptyStudentListException(String message) {
            super(message);
        }
    }
    class Student {
        private int id;
        private String name;
        private String university;
        private String department;
        private double gpa;

        public Student(int id, String name, String university, String department, double gpa ) {
            this.id = id;
            this.name = name;
            this.university = university;
            this.department = department;
            this.gpa = gpa;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }
        public String getUniversity() {
            return this.university;
        }
        public String getDepartment() {
            return this.department;
        }
        public double getGPA() {
            return this.gpa;
        }

        //    @Override
//    public String toString() {
//        return "Student{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", GPA=" + this.gpa +
//                '}';
//    }
        @Override
        public String toString() {
            return String.format("%s", name);
        }
    }


    public class StudentNotFoundException extends Exception {
        public StudentNotFoundException(String message) {
            super(message);
        }
    }
    public class StudentSystem {
        List<Student> students;
        public StudentSystem(String filename) throws IOException {
            students = readStudents(filename);
        }

        public static void main(String[] args) {
            try {
                StudentSystem system = new StudentSystem("students.csv");

                for (Student student : system.students) {
                    System.out.println(student);
                }

                try {
                    Optional<Student> student = system.getStudentById(10);
                    System.out.println(student.orElseThrow(() -> new StudentNotFoundException("Student not found")));
                } catch (StudentNotFoundException e) {
                    System.out.println(e.getMessage());
                }

                Student highestGPAStudent = system.getHighestGPAStudent();
                System.out.println("\nStudent with the highest GPA is " + highestGPAStudent.getName() + " with the GPA " + highestGPAStudent.getGPA() + " from the " + highestGPAStudent.getUniversity() + " studies " + highestGPAStudent.getDepartment());

                Student longestNameStudent = system.getLongestNameStudent();
                System.out.println("\nStudent with the longest name: " + longestNameStudent.getName());
            } catch (IOException e) {
                System.out.println("Unable to read students: " + e.getMessage());
            }
        }
        public static List<Student> readStudents(String filename) throws IOException {
            List<Student> students = new ArrayList<>();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
                List<String> lines = bufferedReader.lines().collect(Collectors.toList());
                for (String line : lines) {
                    String[] studentParts = line.split(",");
                    Student s = new Student(
                            Integer.parseInt(studentParts[0]),
                            studentParts[1],
                            studentParts[2],
                            studentParts[3],
                            Double.parseDouble(studentParts[4])
                    );
                    students.add(s);
                }
            }
            return students;
        }

        public Optional<Student> getStudentById(int id) {
            for (Student s : students) {
                if (s.getId() == id)
                    return Optional.of(s);
            }
            return Optional.empty();
        }

        public Student getHighestGPAStudent() {
            if (students.isEmpty())
                throw new EmptyStudentListException("List of students is empty");

            Student highestGPAStudent = students.get(0);
            for (Student student : students) {
                if (student.getGPA() > highestGPAStudent.getGPA())
                    highestGPAStudent = student;
            }
            return highestGPAStudent;
        }

        public Student getLongestNameStudent() {
            if (students.isEmpty())
                throw new EmptyStudentListException("List of students is empty");

            Student longestNameStudent = students.get(0);
            for (Student student : students) {
                if (student.getName().length() > longestNameStudent.getName().length())
                    longestNameStudent = student;
            }
            return longestNameStudent;
        }

    }
}
