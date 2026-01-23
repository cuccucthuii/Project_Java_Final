package entity;

import java.time.LocalDate;
import java.util.Scanner;

public class Course {
    private int courseId;
    private String courseName;
    private int courseDuration;
    private String courseInstructor;
    private LocalDate courseCreateAt;

    public Course() {
    }

    public Course(int courseId, String courseName, int courseDuration, String courseInstructor, LocalDate courseCreateAt) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.courseInstructor = courseInstructor;
        this.courseCreateAt = courseCreateAt;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public LocalDate getCourseCreateAt() {
        return courseCreateAt;
    }

    public void setCourseCreateAt(LocalDate courseCreateAt) {
        this.courseCreateAt = courseCreateAt;
    }

    public void inputCourceData(Scanner scanner) {
        nameCourceData(scanner);
        durationCourceData(scanner);
        instructorCourceData(scanner);
        this.courseCreateAt = LocalDate.now();
    }

    public void nameCourceData(Scanner scanner) {
        System.out.print("Nhập vào tên khoá học: ");
        do {
                String nameCource = scanner.nextLine().trim();
                if (nameCource.isEmpty()) {
                    System.err.println("Tên khoá học không được để trống!");
                } else if (nameCource.length() > 100) {
                    System.err.println("Tên khoá học tối đa 100 ký tự!");
                } else {
                    this.courseName = nameCource;
                    break;
                }
        } while (true);
    }

    public void durationCourceData(Scanner scanner) {
        System.out.print("Nhập vào thời lượng (giờ): ");
        do {
                String durationCource = scanner.nextLine().trim();
                if (durationCource.isEmpty()) {
                    System.err.println("Thời lương không được để trống!");
                }
            try {
                int checkInput = Integer.parseInt(durationCource);
                if (checkInput < 0){
                    System.err.println("Thời lượng phải > 0 Phút");
                }else {
                    this.courseDuration = checkInput;
                    break;
                }
            }catch (Exception e) {
                System.err.println("Thời lượng phải là số!");
            }
        }while (true);
    }

    public void instructorCourceData(Scanner scanner) {
        System.out.print("Nhập vào giảng viên phụ trách: ");
        do {
                String instructorCource = scanner.nextLine().trim();
                if (instructorCource.isEmpty()) {
                    System.err.println("Tên giảng viên không được để trống!");
                }else if (instructorCource.length() > 100) {
                    System.err.println("Tên giảng viên không quá 100 ký tự!");
                }else{
                    this.courseInstructor = instructorCource;
                    break;
                }
        }while (true);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDuration=" + courseDuration +
                ", courseInstructor='" + courseInstructor + '\'' +
                ", courseCreateAt=" + courseCreateAt +
                '}';
    }
}
