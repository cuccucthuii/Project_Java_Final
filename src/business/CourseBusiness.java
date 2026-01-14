package business;

import dao.CourseDAO;
import entity.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseBusiness {
    private CourseDAO courseDAO = new CourseDAO();

    public List<Course> getAllCourses() {
        List<Course> courses = courseDAO.findAllCourses();
        if (courses == null || courses.isEmpty()) {
            System.err.println("Chưa có dữ liệu khoá học!");
        } else {
            System.out.println("Thông tin khoá học!");
            courses.forEach(System.out::println);
        }
        return courses;
    }

    public boolean createCourse(Scanner scanner) {
        Course course = new Course();
        System.out.println("Nhập thông tin khoá học");
        course.inputCourceData(scanner);

        boolean addCourse = courseDAO.addCourse(course);
        if (addCourse) {
            System.out.println("Thêm khoá học thành công!");
        } else {
            System.err.println("Thêm khoá học không thành công!");
        }
        return addCourse;
    }

    public void updateCourse(Scanner scanner) {
        System.out.println("Nhập vào ID khoá học: ");
        int id = 0;
        boolean running = true;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Lỗi nhập ID");
            return;
        }

        Course course = courseDAO.searchCourceById(id);
        if (course == null) {
            System.err.println("Không tìm thấy khoá học!");
            return;
        } else {
            System.out.println("Chọn thông tin cần cập nhật cho khoá học ID = " + id);
            do {
                System.out.println("1. Cập nhật tên khoá học");
                System.out.println("2. Cập nhật thời lượng");
                System.out.println("3. Cập nhật giảng viên");
                System.out.println("4. Thoát");
                System.out.println("Lựa chọn của bạn: ");
                int choice = 0;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi nhập liệu!");
                }
                switch (choice) {
                    case 1:
                        course.nameCourceData(scanner);
                        break;
                    case 2:
                        course.durationCourceData(scanner);
                        break;
                    case 3:
                        course.instructorCourceData(scanner);
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.err.println("Vui lòng chọn từ 1 - 4");
                }
            } while (running);

            boolean updateCourse = courseDAO.updateCourse(course);
            if (updateCourse) {
                System.out.println("Cập nhật khoá học thành công!");
            }else{
                System.err.println("Lỗi cập nhật khoá học!");
            }
        }


    }

    public void deleteCourseById(Scanner scanner) {
        System.out.println("Nhập vào ID cần xoá: ");
        int id = 0;
        String confirmDelete;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Lỗi nhập ID");
            return;
        }

        Course course = courseDAO.searchCourceById(id);
        if (course == null) {
            System.err.println("Không tìm thấy ID khoá học!");
            return;
        }else{
            System.out.println("Bạn có xác nhận muốn xoá Khoá học ?");
            System.out.println("(Y/N): ");
            do {
                confirmDelete = scanner.nextLine();
                if (confirmDelete.equalsIgnoreCase("Y")) {
                    courseDAO.deleteCourseById(id);
                    System.out.println("Xoá thành công!");
                    break;
                }else if (confirmDelete.equalsIgnoreCase("N")) {
                    System.out.println("Huỷ thành công!");
                    break;
                }else{
                    System.err.println("Nhập không đúng định dạng! Vui lòng thử lại.");
                }
            }while (true);
        }
    }

    public List<Course> searchCoursesByName(Scanner scanner) {
        System.out.println("Nhập vào tên khoá học: ");
        String name = scanner.nextLine().trim();
        if (name.equals("") || name.isEmpty()) {
            System.err.println("Tên không được để trống!");
        }
        List<Course> courses = courseDAO.searchCourseByName(name);
        if (courses == null || courses.isEmpty()) {
            System.err.println("Không tìm thấy dữ liệu khoá học!");
        }else{
            System.out.println("Tìm thấy thông tin khoá học liên quan: ");
            courses.forEach(System.out::println);
        }
        return courses;
    }

    public List<Course> sortCoursesByIdDesc() {
        List<Course> courses = courseDAO.sortCourseByIdDesc();
        if (courses == null || courses.isEmpty()) {
            System.err.println("Chưa có dữ liệu khoá học!");
        }else{
            System.out.println("Thông tin khoá học được sắp xếp DESC theo ID");
            courses.forEach(System.out::println);
        }
        return courses;
    }
}
