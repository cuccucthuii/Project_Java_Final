package presentation;

import business.impl.CourseBusinessImpl;

import java.util.Scanner;

public class CourcePresenation {
    private static CourcePresenation cp = new CourcePresenation();
    private CourseBusinessImpl cb = new CourseBusinessImpl();
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        cp.CourseManagement(scanner);
//    }

    public void CourseManagement(Scanner scanner) {
        int choice = 0;
        do {
            System.out.println("========== QUẢN LÝ KHOÁ HỌC ==========");
            System.out.println("1. Hiển thị danh sách khoá học");
            System.out.println("2. Thêm mới khoá học");
            System.out.println("3. Chỉnh sửa thông tin khoá học");
            System.out.println("4. Xoá khoá học");
            System.out.println("5. Tìm kiếm theo tên");
            System.out.println("6. Sắp xếp theo ID");
            System.out.println("7. Quay về menu chính");
            System.out.println("======================================");
            System.out.println("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập đúng định dạng 1-7 trên MENU");
            }
            switch (choice) {
                case 1:
                    cb.getAllCourses();
                    break;
                case 2:
                    cb.createCourse(scanner);
                    break;
                case 3:
                    cb.updateCourse(scanner);
                    break;
                case 4:
                    cb.deleteCourseById(scanner);
                    break;
                case 5:
                    cb.searchCoursesByName(scanner);
                    break;
                case 6:
                    cb.sortCoursesByIdDesc();
                    break;
                case 7:
                    return;
                default:
                    System.err.println("Vui lòng chọn từ 1-7");
            }
        } while (true);
    }

}
