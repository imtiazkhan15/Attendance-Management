package attendance_management;

import java.io.*;
import java.util.*;


public class Teacher extends User {
	private static final String COURSES_FILE = "./data/courses.csv";
    public Teacher(String name, String username, String password) {
        super(name, username, password, "Teacher");
    }

    @Override
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            ArrayList<Course> Courses = new ArrayList<Course>(); // reset each time
            System.out.println("\n--- Section Menu ---");
            System.out.println("Choice\tCourse Code\tCourse Name\tSection");
            
            List<String[]> courses = CSVUtils.readCSV(COURSES_FILE);
            int index = 1;
            for (String[] c : courses) {
                if (c[3].equals(username)) {
                    Course course = new Course(c[0], c[1], c[2], c[3]);
                    Courses.add(course);
                    System.out.println(index + ".\t" + c[0] + "\t" + c[1] + "\t" + c[2]);
                    index++;
                }
            }

            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) return;

            if (choice < 1 || choice > Courses.size()) {
                System.out.println("Invalid choice!");
                continue;
            }

            Course selectedCourse = Courses.get(choice - 1); // get chosen course

            System.out.println("\n--- Course Actions ---");
            System.out.println("1. Take Attendance");
            System.out.println("2. View Attendance History");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");
            int subChoice = sc.nextInt();
            sc.nextLine();

            switch (subChoice) {
                case 1:
                    AttendanceService.takeAttendance(sc, this.username, selectedCourse);
                    break;
                case 2:
                    AttendanceService.viewAttendanceHistory(sc, this.username, selectedCourse);
                    break;
                case 3:
                    // back to course menu
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    
    
}
