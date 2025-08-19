package attendance_management;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class AttendanceService {
    private static final String ATTENDANCE_FILE = "./data/attendance.csv";
    private static final String SCO_FILE = "./data/"; // each course-section file holds students
    
    private static void checkConsecutiveAbsents(Course selectedCourse) {
        List<String[]> records = CSVUtils.readCSV(ATTENDANCE_FILE);

        // Filter for this course-section
        List<String[]> filtered = new ArrayList<>();
        for (String[] r : records) {
            if (r[0].equals(selectedCourse.getCourseId()) && r[1].equals(selectedCourse.getsec())) {
                filtered.add(r);
            }
        }

        // Sort first by studentId, then by date
        filtered.sort(Comparator.<String[], String>comparing(r -> r[2])   // studentId
                                .thenComparing(r -> r[3]));               // date

        String currentStudent = "";
        int absentCount = 0;

        for (String[] r : filtered) {
            String studentId = r[2];
            String status = r[4];

            if (!studentId.equals(currentStudent)) {
                // new student -> reset counter
                currentStudent = studentId;
                absentCount = 0;
            }

            if (status.equals("A")) {
                absentCount++;
            } else {
                absentCount = 0; // reset if not absent
            }

            if (absentCount == 3) {
                System.out.println(" Student " + studentId +
                                   " has been ABSENT for 3 consecutive classes in " +
                                   selectedCourse.getCourseId() + " - " + selectedCourse.getsec());
            }
        }
    }

    // Take attendance for a selected course
    public static void takeAttendance(Scanner sc, String teacherId, Course selectedCourse) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // file name e.g. CSE101_A.csv containing studentId,name
        String courseFile = SCO_FILE + selectedCourse.getCourseId() + "_" + selectedCourse.getsec() + ".csv";
        List<String[]> students = CSVUtils.readCSV(courseFile);

        if (students.isEmpty()) {
            System.out.println("No students found in this section!");
            return;
        }

        List<String[]> records = CSVUtils.readCSV(ATTENDANCE_FILE);

        //check if already attendance taken
        List<String[]> filtered = new ArrayList<>();
        for (String[] r : records) {
            if (r[0].equals(selectedCourse.getCourseId()) && r[1].equals(selectedCourse.getsec()) && r[3].equals(date)) {
                filtered.add(r);
                System.out.println("Attendance already taken today");
                return;
            }
        }
        
        for (String[] s : students) {
            String sid = s[0]; // assume first col = studentId
            String sname = (s.length > 1) ? s[1] : "";
            System.out.print("Mark attendance for " + sid + " " + sname + " (P/A/L): ");
            String status = sc.nextLine().trim().toUpperCase();

            // default to Absent if invalid input
            if (!(status.equals("P") || status.equals("A") || status.equals("L"))) {
                status = "A";
            }

            CSVUtils.appendCSV(ATTENDANCE_FILE, new String[]{
                selectedCourse.getCourseId(),
                selectedCourse.getsec(),
                sid,
                date,
                status
            });
        }
        checkConsecutiveAbsents(selectedCourse);
        System.out.println("Attendance recorded!");
    }

    // View attendance for a course-section, sorted by date
    public static void viewAttendanceHistory(Scanner sc, String teacherId, Course selectedCourse) {
        List<String[]> records = CSVUtils.readCSV(ATTENDANCE_FILE);

        // Filter for this course-section
        List<String[]> filtered = new ArrayList<>();
        for (String[] r : records) {
            if (r[0].equals(selectedCourse.getCourseId()) && r[1].equals(selectedCourse.getsec())) {
                filtered.add(r);
            }
        }

        // Sort by date (col index 3)
        filtered.sort(Comparator.comparing(r -> r[3]));

        System.out.println("\nAttendance History for " + selectedCourse.getCourseId() + " - " + selectedCourse.getsec());
        for (String[] r : filtered) {
            System.out.println("Date: " + r[3] + " | Student: " + r[2] + " | Status: " + r[4]);
        }
    }

    // View all attendance records of a student, sorted by date
    public static void viewStudentAttendance(Scanner sc, String studentId) {
        List<String[]> records = CSVUtils.readCSV(ATTENDANCE_FILE);

        List<String[]> filtered = new ArrayList<>();
        for (String[] r : records) {
            if (r[2].equals(studentId)) {
                filtered.add(r);
            }
        }

        // Sort by date
        filtered.sort(Comparator.comparing(r -> r[3]));

        System.out.println("\nAttendance for Student " + studentId);
        for (String[] r : filtered) {
            System.out.println("Course: " + r[0] + " | Section: " + r[1] +
                               " | Date: " + r[3] + " | Status: " + r[4]);
        }
    }
}
