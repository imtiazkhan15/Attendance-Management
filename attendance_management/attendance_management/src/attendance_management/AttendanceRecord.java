package attendance_management;

public class AttendanceRecord {
    private String courseId;
    private String section;
    private String studentId;
    private String date;
    private String status;

    public AttendanceRecord(String courseId, String section, String studentId, String date, String status) {
        this.courseId = courseId;
        this.section = section;
        this.studentId = studentId;
        this.date = date;
        this.status = status;
    }

    public String getCourseId() { return courseId; }
    public String getSection() { return section; }
    public String getStudentId() { return studentId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
}
