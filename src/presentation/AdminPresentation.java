package presentation;

import java.util.Scanner;

public class AdminPresentation {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        AdminPresentation ap = new AdminPresentation();
//        ap.adminManagement(sc);
//    }

    public void adminManagement(Scanner scanner) {
        CourcePresenation cp = new CourcePresenation();
        ManagementStudentPresentation msp = new ManagementStudentPresentation();
        RegisteredCourse rc = new RegisteredCourse();
        int choice = 0;
        do {
            System.out.println("=========== MENU ADMIN ===========");
            System.out.println("1. Quản lý khoá học");
            System.out.println("2. Quản lý học viên");
            System.out.println("3. Quản lý đăng ký học");
            System.out.println("4. Thống kê học viên theo khoá học");
            System.out.println("5. Đăng xuất");
            System.out.println("==================================");
            System.out.println("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập đúng định dạng!");
            }
            switch (choice) {
                case 1:
                    cp.CourseManagement(scanner);
                    break;
                case 2:
                    msp.ManagementStudentPresentation(scanner);
                    break;
                case 3:
                    rc.RegisterCourse(scanner);
                    break;
                case 4:
                    break;
                case 5:
                    return;
                default:
                    System.err.println("Vui lòng nhập từ 1-5");
            }
        } while (true);
    }
}
