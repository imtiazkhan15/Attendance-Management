package attendance_management;

public class Course {
    private String courseId;
    private String courseName;
    private String sec;
    private String teacherId;

    public Course(String courseId, String courseName, String sec, String teacherId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.sec = sec;
        this.teacherId = teacherId;
    }

    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getsec() { return sec; }
    public String getTeacherId() { return teacherId; }
}
