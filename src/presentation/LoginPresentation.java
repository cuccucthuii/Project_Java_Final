package presentation;

import business.AdminBusiness;
import entity.Admin;

import java.util.Scanner;

public class LoginPresentation {
    private static AdminBusiness adminBusiness = new AdminBusiness();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("========== HỆ THỐNG QUẢN LÝ ĐÀO TẠO ==========");
            System.out.println("1. Đăng nhập với tư cách Quản trị viên");
            System.out.println("2. Đăng nhập với tư cách học viên");
            System.out.println("3. Thoát");
            System.out.println("===============================================");
            System.out.println("Nhập lựa chọn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập vào ký tự số theo MENU!");
            }
            switch (choice) {
                case 1:
                    loginAdmin(scanner);
                    break;
                case 2:
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1 - 3");
            }
        } while (true);
    }

    public static void loginAdmin(Scanner scanner) {
        Admin admin = new Admin();
        System.out.println("Nhập thông tin tài khoản");
        admin.inputDataAdmin(scanner);

        boolean result = adminBusiness.loginAdmin(admin);
        if (result) {
            System.out.println("Đăng nhập thành công!");
            AdminPresentation ad = new AdminPresentation();
            ad.adminManagement(scanner);
        }else{
            System.err.println("Thông tin tài khoản không chính xác!");
            return;
        }

    }
}
