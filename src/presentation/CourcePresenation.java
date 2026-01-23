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
            System.out.println();
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║        📚 QUẢN LÝ KHOÁ HỌC           ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. 📋 Hiển thị danh sách khoá học    ║");
            System.out.println("║ 2. ➕ Thêm mới khoá học              ║");
            System.out.println("║ 3. ✏️  Chỉnh sửa thông tin khoá học  ║");
            System.out.println("║ 4. 🗑️  Xoá khoá học                  ║");
            System.out.println("║ 5. 🔍 Tìm kiếm theo tên              ║");
            System.out.println("║ 6. ↕️  Sắp xếp theo ID (Giảm dần)    ║");
            System.out.println("║ 7. 🔙 Quay về menu chính             ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("👉 Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("✗ Vui lòng nhập số từ 1 đến 7!");
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
                    System.out.println("🔙 Quay về menu chính...");
                    return;
                default:
                    System.err.println("✗ Lựa chọn không hợp lệ! Chỉ chọn từ 1–7.");
            }
        } while (true);
    }

}
