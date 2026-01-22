package business.impl;

import business.CourseBusiness;
import business.CurrentStudent;
import dao.CourseDAO;
import dao.impl.CourseDaoImpl;
import entity.Course;

import java.util.List;
import java.util.Scanner;

public class CourseBusinessImpl implements CourseBusiness {

    private final CourseDAO courseDAO;

    // Dependency Injection
    public CourseBusinessImpl() {
        this.courseDAO = new CourseDaoImpl();
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = courseDAO.findAllCourses();
        if (courses == null || courses.isEmpty()) {
            System.err.println("Chưa có dữ liệu khoá học!");
        } else {
            System.out.println("Thông tin khoá học:");
            courses.forEach(System.out::println);
        }
        return courses;
    }

    @Override
    public boolean createCourse(Scanner scanner) {
        Course course = new Course();
        System.out.println("Nhập thông tin khoá học");
        course.inputCourceData(scanner);

        boolean result = courseDAO.addCourse(course);
        System.out.println(
                result ? "Thêm khoá học thành công!" : "Thêm khoá học không thành công!"
        );
        return result;
    }

    @Override
    public void updateCourse(Scanner scanner) {
        System.out.print("Nhập ID khoá học: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("ID không hợp lệ!");
            return;
        }

        Course course = courseDAO.searchCourseById(id);
        if (course == null) {
            System.err.println("Không tìm thấy khoá học!");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("1. Cập nhật tên");
            System.out.println("2. Cập nhật thời lượng");
            System.out.println("3. Cập nhật giảng viên");
            System.out.println("4. Lưu & thoát");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Sai định dạng!");
                continue;
            }

            switch (choice) {
                case 1 -> course.nameCourceData(scanner);
                case 2 -> course.durationCourceData(scanner);
                case 3 -> course.instructorCourceData(scanner);
                case 4 -> running = false;
                default -> System.err.println("Chọn từ 1–4");
            }
        }

        if (courseDAO.updateCourse(course)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.err.println("Cập nhật thất bại!");
        }
    }

    @Override
    public void deleteCourseById(Scanner scanner) {
        System.out.print("Nhập ID cần xoá: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("ID không hợp lệ!");
            return;
        }

        Course course = courseDAO.searchCourseById(id);
        if (course == null) {
            System.err.println("Không tìm thấy khoá học!");
            return;
        }

        System.out.print("Xác nhận xoá (Y/N): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            courseDAO.deleteCourseById(id);
            System.out.println("Xoá thành công!");
        } else {
            System.out.println("Huỷ thao tác!");
        }
    }

    @Override
    public List<Course> searchCoursesByName(Scanner scanner) {
        System.out.print("Nhập tên khoá học: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.err.println("Tên không được để trống!");
            return null;
        }

        List<Course> courses = courseDAO.searchCourseByName(name);
        if (courses == null || courses.isEmpty()) {
            System.err.println("Không tìm thấy khoá học!");
        } else {
            courses.forEach(System.out::println);
        }
        return courses;
    }

    @Override
    public List<Course> sortCoursesByIdDesc() {
        List<Course> courses = courseDAO.sortCourseByIdDesc();
        if (courses == null || courses.isEmpty()) {
            System.err.println("Chưa có dữ liệu!");
        } else {
            courses.forEach(System.out::println);
        }
        return courses;
    }

    @Override
    public List<Course> getCourseForStudent() {
        List<Course> courses = courseDAO.getCourseForStudent();
        if (courses == null || courses.isEmpty()) {
            System.err.println("Chưa có dữ liệu hệ thống!");
            return  null;
        } else {
            courses.forEach(System.out::println);
        }
        return courses;
    }
//    @Override
//    public List<Course> getCourseByDateDESC() {
//        List<Course> list = courseDAO.sortCourseByDateDesc();
//        if (list == null || list.isEmpty()) {
//            System.err.println("Chưa có dữ liệu!");
//            return List.of();
//        }else {
//            list.forEach(System.out::println);
//        }
//        return list;
//    }
}
