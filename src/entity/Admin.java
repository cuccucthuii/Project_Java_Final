package entity;

import java.util.Scanner;

public class Admin {
    private int adminId;
    private String adminUser;
    private String adminPassword;

    public Admin() {
    }

    public Admin(int adminId, String adminUser, String adminPassword) {
        this.adminId = adminId;
        this.adminUser = adminUser;
        this.adminPassword = adminPassword;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void inputDataAdmin(Scanner scanner) {
        inputUserAdmin(scanner);
        inputPasswordAdmin(scanner);
    }

    public void inputUserAdmin(Scanner scanner) {
        System.out.println("Tên đăng nhập: ");
        do {
            String user = scanner.nextLine().trim();
            if (user.isEmpty()) {
                System.err.println("Tên đăng nhập không được để trống!");
            } else {
                this.adminUser = user;
                break;
            }
        } while (true);
    }

    public void inputPasswordAdmin(Scanner scanner) {
        System.out.println("Mật khẩu: ");
        do {
            String password = scanner.nextLine().trim();
            if (password.isEmpty()) {
                System.err.println("Mật khẩu không được để trống!");
            } else {
                this.adminPassword = password;
                break;
            }
        } while (true);
    }


    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminUser='" + adminUser + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }
}
