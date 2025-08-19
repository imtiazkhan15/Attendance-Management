package attendance_management;

import java.util.*;

public class CourseService {
    private static final String COURSES_FILE = "./data/courses.csv";
    private static final String SCO_FILE = "./data/";
    private static final String USERS_FILE = "./data/users.csv";
    
    public static void searchStudents(Scanner sc) {
        System.out.println("\n--- Student Search ---");
        System.out.println("1. Search by ID (username)");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Course");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter Student ID (username): ");
                String id = sc.nextLine();
                searchById(id);
                break;
            case 2:
                System.out.print("Enter Student Name: ");
                String name = sc.nextLine();
                searchByName(name);
                break;
            case 3:
                System.out.print("Enter Course Code: ");
                String courseId = sc.nextLine();
                System.out.print("Enter Section: ");
                String section = sc.nextLine();
                searchByCourse(courseId, section);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // 🔹 Search by ID (username)
    private static void searchById(String id) {
        List<String[]> users = CSVUtils.readCSV(USERS_FILE);
        for (String[] row : users) {
            if (row.length >= 2 && row[1].equalsIgnoreCase(id) && row[3].trim().equalsIgnoreCase("Student")) {
                System.out.println("Found Student: Name=" + row[0] + ", Username=" + row[1]);
                return;
            }
        }
        System.out.println(" Student not found with ID: " + id);
    }

    // 🔹 Search by Name
    private static void searchByName(String name) {
        List<String[]> users = CSVUtils.readCSV(USERS_FILE);
        boolean found = false;
        for (String[] row : users) {
            if (row.length >= 1 && row[0].equalsIgnoreCase(name) && row[3].trim().equalsIgnoreCase("Student")) {
                System.out.println(" Found Student: Name=" + row[0] + ", Username=" + row[1]);
                found = true;
            }
        }
        if (!found) System.out.println(" No student found with Name: " + name);
    }

    // 🔹 Search by Course (reads from course-specific file)
    private static void searchByCourse(String courseId, String section) {
        String filePath = SCO_FILE + courseId + "_" + section + ".csv";
        List<String[]> students = CSVUtils.readCSV(filePath);

        if (students.isEmpty()) {
            System.out.println(" No students found in " + courseId + " (Sec " + section + ")");
            return;
        }

        System.out.println(" Students enrolled in " + courseId + " (Sec " + section + "):");
        for (String[] row : students) {
            if (row.length > 0) {
                System.out.println("- " + row[0]);
            }
        }
    }

    public static void createCourse(Scanner sc) {
        System.out.print("Enter Course Code: ");
        String id = sc.nextLine();
        System.out.print("Enter Course Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Section No: ");
        String sec = sc.nextLine();
        System.out.print("Enter Teacher username: ");
        String tuname = sc.nextLine();
        CSVUtils.appendCSV(COURSES_FILE, new String[]{id, name, sec, tuname});
        CSVUtils.writeCSV(SCO_FILE + id +"_"+ sec + ".csv",  new ArrayList<>());
        System.out.println("Course created!");
    }


    public static void assignStudent(Scanner sc) {
    	System.out.print("Enter Course Code: ");
        String id = sc.nextLine();
        System.out.print("Enter Section: ");
        String sec = sc.nextLine();
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        String filePath = SCO_FILE + id + "_" + sec + ".csv";
        List<String[]> students = CSVUtils.readCSV(filePath);

        // ✅ Check if student already exists
        boolean exists = students.stream()
                .anyMatch(row -> row.length > 0 && row[0].equalsIgnoreCase(name));

        if (exists) {
            System.out.println("Student already exists in this course section!");
        } else {
        	CSVUtils.appendCSV(SCO_FILE + id +"_"+ sec + ".csv", new String[]{name});
        	System.out.println("Student created!");
        }
    }
    public static void removeStudent(Scanner sc) {
    	System.out.print("Enter Course Code: ");
        String id = sc.nextLine();
        System.out.print("Enter Section: ");
        String sec = sc.nextLine();
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        
        String filePath = SCO_FILE + id + "_" + sec + ".csv";
        List<String[]> students = CSVUtils.readCSV(filePath);

        // ✅ Check if student exists before removing
        boolean exists = students.stream()
                .anyMatch(row -> row.length > 0 && row[0].equalsIgnoreCase(name));
        if(exists) {
        	CSVUtils.removeCSV(SCO_FILE + id +"_"+ sec + ".csv", name);
        	System.out.println("Student removed!");
        }else {
        	System.out.println("Student not found in this course section!");
        }
    }
        
    public static void addUser(Scanner sc) {
    	System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter username: ");
        String uname = sc.nextLine();
        String role = "Student";
        boolean choi = true;
        while(choi) {
        	System.out.println("Choose ROLE: ");
        	System.out.println("1. Student ");
        	System.out.println("2. Teacher ");
        	
        	int choice = 0;
        	choice = sc.nextInt();
        	switch(choice) {
        	case 1: role = "Student\n";break;
        	case 2: role = "Teacher\n";break;
        	default: System.out.println("Invalid Choice");
        	}
        	//break;
        	choi=false;
        }
        CSVUtils.appendCSV(USERS_FILE, new String[] {name,uname,uname+"123", role});
        System.out.println("User Added");
    }
    
    
}
