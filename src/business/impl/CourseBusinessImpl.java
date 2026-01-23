package business.impl;

import business.CourseBusiness;
import business.CurrentStudent;
import dao.CourseDAO;
import dao.impl.CourseDaoImpl;
import entity.Course;
import formater.CourseFormater;

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
            System.err.println("✗ Chưa có dữ liệu khóa học!");
        } else {
            CourseFormater.headerCourse();
            courses.forEach(CourseFormater::printRow);
            CourseFormater.printFooter(courses.size());
        }
        return courses;
    }

    @Override
    public boolean createCourse(Scanner scanner) {
        Course course = new Course();
        System.out.println("╔════════════════════════╗");
        System.out.println("║  📘 THÊM KHOÁ HỌC MỚI  ║");
        System.out.println("╚════════════════════════╝");

        course.inputCourceData(scanner);

        boolean result = courseDAO.addCourse(course);

        System.out.println("---------------------------------");
        if (result) {
            System.out.println("✔ Thêm khoá học thành công!");
        } else {
            System.out.println("✖ Thêm khoá học KHÔNG thành công!");
        }
        System.out.println("---------------------------------");
        return result;
    }

    @Override
    public void updateCourse(Scanner scanner) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║      ✏ CẬP NHẬT KHOÁ HỌC         ║");
        System.out.println("╚══════════════════════════════════╝");

        System.out.print("👉 Nhập ID khoá học: ");
        int id = 0;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("✘ ID không hợp lệ! Vui lòng nhập số.");
            return;
        }

        Course course = courseDAO.searchCourseById(id);
        if (course == null) {
            System.err.println("✘ Không tìm thấy khoá học với ID: " + id);
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║     🔧 MENU CẬP NHẬT THÔNG TIN     ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. ✎ Cập nhật tên khoá học         ║");
            System.out.println("║ 2. ⏱ Cập nhật thời lượng           ║");
            System.out.println("║ 3. 👨‍🏫 Cập nhật giảng viên          ║");
            System.out.println("║ 4. ✔ Lưu & thoát                   ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("👉 Lựa chọn của bạn: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("✘ Vui lòng nhập số từ 1 đến 4!");
                continue;
            }

            switch (choice) {
                case 1:
                    course.nameCourceData(scanner);
                    System.out.println("✔ Đã cập nhật tên khoá học");
                    break;
                case 2:
                    course.durationCourceData(scanner);
                    System.out.println("✔ Đã cập nhật thời lượng");
                    break;
                case 3:
                    course.instructorCourceData(scanner);
                    System.out.println("✔ Đã cập nhật giảng viên");
                    break;
                case 4:
                    running = false;
                    System.out.println("✔ Đang lưu dữ liệu...");
                    break;
                default:
                    System.err.println("✘ Vui lòng chọn từ 1 đến 4!");
            }
        }

        if (courseDAO.updateCourse(course)) {
            System.out.println("✔ Cập nhật khoá học thành công!");
        } else {
            System.err.println("✘ Cập nhật khoá học thất bại!");
        }
    }

    @Override
    public void deleteCourseById(Scanner scanner) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║        🗑 XOÁ KHOÁ HỌC           ║");
        System.out.println("╚══════════════════════════════════╝");

        System.out.print("👉 Nhập ID khoá học cần xoá: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("✘ ID không hợp lệ! Vui lòng nhập số.");
            return;
        }

        Course course = courseDAO.searchCourseById(id);
        if (course == null) {
            System.err.println("✘ Không tìm thấy khoá học với ID: " + id);
            return;
        }

        CourseFormater.onlyHeaderCourse();
        CourseFormater.printRow(course);
        CourseFormater.onlyPrintFooter();

        boolean already = courseDAO.getCourseAlreadySub(course);
        if (already) {
            System.err.println("Khoá học đã được học không thể xoá!");
            System.err.flush();
            return;
        }

        System.out.print("⚠ Xác nhận xoá khoá học này? (Y/N): ");

        do {
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("Y")) {
                courseDAO.deleteCourseById(id);
                System.out.println("✔ Xoá khoá học thành công!");
                break;
            } else if (confirm.equalsIgnoreCase("N")) {
                System.out.println("✘ Đã huỷ thao tác xoá.");
                break;
            }else{
                System.err.println("! Vui lòng xác nhận ( Y - XOÁ | N - HUỶ )");
            }
        }while (true);
    }

    @Override
    public List<Course> searchCoursesByName(Scanner scanner) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║      🔍 TÌM KIẾM KHOÁ HỌC        ║");
        System.out.println("╚══════════════════════════════════╝");

        System.out.print("👉 Nhập tên khoá học cần tìm: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.err.println("✘ Tên khoá học không được để trống!");
            return null;
        }

        List<Course> courses = courseDAO.searchCourseByName(name);
        if (courses == null || courses.isEmpty()) {
            System.err.println("✘ Không tìm thấy khoá học phù hợp với từ khoá: " + name);
        } else {
            CourseFormater.headerCourse();
            courses.forEach(CourseFormater::printRow);
            CourseFormater.printFooter(courses.size());
        }
        return courses;
    }

    @Override
    public List<Course> sortCoursesByIdDesc() {
        List<Course> courses = courseDAO.sortCourseByIdDesc();
        if (courses == null || courses.isEmpty()) {
            System.err.println("Chưa có dữ liệu!");
        } else {
            CourseFormater.headerCourse();
            courses.forEach(CourseFormater::printRow);
            CourseFormater.printFooter(courses.size());
        }
        return courses;
    }

    @Override
    public List<Course> getCourseForStudent() {
        List<Course> courses = courseDAO.getCourseForStudent();
        if (courses == null || courses.isEmpty()) {
            System.err.println("Chưa có dữ liệu hệ thống!");
            return null;
        } else {
            CourseFormater.headerCourse();
            courses.forEach(CourseFormater::printRow);
            CourseFormater.printFooter(courses.size());
        }
        return courses;
    }

    // In thông tin theo dạng bảng
    // Tạo 1 class Format lại dữ liệu / thay cho toString()

}
