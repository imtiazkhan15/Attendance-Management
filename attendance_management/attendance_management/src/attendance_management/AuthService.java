package attendance_management;

import java.util.*;

public class AuthService {
    private final String USERS_FILE = "./data/users.csv";
    public User login(Scanner sc) {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        List<String[]> users = CSVUtils.readCSV(USERS_FILE);
        for (String[] u : users) {
            if (u[1].equals(username) && u[2].equals(password)) {
                String role = u[3];
                if (role.equals("Admin")) return new Admin(u[0], u[1], u[2]);
                if (role.equals("Teacher")) return new Teacher(u[0], u[1], u[2]);
                if (role.equals("Student")) return new Student(u[0], u[1], u[2]);
            }
        }
        System.out.println("Invalid login credentials!");
        return null;
    }
}
