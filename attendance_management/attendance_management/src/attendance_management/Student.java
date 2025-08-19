package attendance_management;

import java.util.Scanner;

public class Student extends User {
    public Student(String name, String username, String password) {
        super(name, username, password, "Student");
    }

    @Override
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View My Attendance");
            System.out.println("2. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: AttendanceService.viewStudentAttendance(sc, this.username); break;
                case 2: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
}
