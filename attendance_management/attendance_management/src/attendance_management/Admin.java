package attendance_management;

import java.util.Scanner;

public class Admin extends User {
    public Admin(String name, String username, String password) {
        super(name, username, password, "Admin");
    }

    @Override
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Create Course");
            System.out.println("2. Assign Student to Course");
            System.out.println("3. Remove Student from Course");
            System.out.println("4. Add new user to system");
            System.out.println("5. Search Student");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: CourseService.createCourse(sc); break;
                case 2: CourseService.assignStudent(sc); break;
                case 3: CourseService.removeStudent(sc); break;
                case 4: CourseService.addUser(sc);break;
                case 5: CourseService.searchStudents(sc);break;
                case 6: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
}
