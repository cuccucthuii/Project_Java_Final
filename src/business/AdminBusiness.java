package business;

import dao.AdminDAO;
import entity.Admin;

public class AdminBusiness {
    private AdminDAO adminDAO = new AdminDAO();

    public boolean loginAdmin(Admin admin) {
        boolean result = adminDAO.loginAdmin(admin);
        if (result) {
            System.out.println("Thông báo!");
        }else{
            System.err.println("Thông báo!");
        }
        return result;
    }
}
