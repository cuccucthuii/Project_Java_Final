package presentation;

import business.AdminBusiness;
import business.StudentBusiness;
import business.impl.StudentBusinessImpl;
import entity.Admin;

import java.util.Scanner;

public class LoginPresentation {
    private static AdminBusiness adminBusiness = new AdminBusiness();
    private static StudentBusiness studentBusiness = new StudentBusinessImpl();

    public static void login(Scanner scanner) {
        int choice = 0;
        do {
            System.out.println();
            System.out.println("╔════════════ HỆ THỐNG QUẢN LÝ ĐÀO TẠO ════════════╗");
            System.out.println("║                                                  ║");
            System.out.println("║   1. 🔐 Đăng nhập với tư cách Quản trị viên      ║");
            System.out.println("║   2. 👨‍🎓 Đăng nhập với tư cách Học viên           ║");
            System.out.println("║   3. 🚪 Thoát hệ thống                           ║");
            System.out.println("║                                                  ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            System.out.print("👉 Nhập lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("✖ Vui lòng nhập số theo menu!");
            }
            switch (choice) {
                case 1:
                    loginAdmin(scanner);
                    break;
                case 2:
                    studentBusiness.studentLogin(scanner);
                    break;
                case 3:
                    System.out.println("ℹ Đang thoát hệ thống...");
                    System.exit(0);
                    break;
                default:
                    System.err.println("✖ Lựa chọn không hợp lệ! Vui lòng chọn từ 1 - 3.");
            }
        } while (true);
    }

    public static void loginAdmin(Scanner scanner) {
        Admin admin = new Admin();
        System.out.println();
        System.out.println("╔════════════ ĐĂNG NHẬP QUẢN TRỊ VIÊN ════════════╗");
        System.out.println("║                                                 ║");
        System.out.println("║   👉 Vui lòng nhập thông tin tài khoản          ║");
        System.out.println("║                                                 ║");
        System.out.println("╚═════════════════════════════════════════════════╝");
        admin.inputDataAdmin(scanner);

        boolean result = adminBusiness.loginAdmin(admin);
        if (result) {
            System.out.println();
            System.out.println("✔ Đăng nhập thành công!");
            System.out.println("ℹ Đang chuyển đến màn hình quản lý...");
            AdminPresentation ad = new AdminPresentation();
            ad.adminManagement(scanner);
        }else{
            System.err.println();
            System.err.println("✖ Thông tin tài khoản không chính xác!");
            System.err.println("ℹ Vui lòng kiểm tra lại tên đăng nhập hoặc mật khẩu.");
            return;
        }

    }
}
