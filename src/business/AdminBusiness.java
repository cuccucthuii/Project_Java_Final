package business;

import dao.AdminDAO;
import dao.impl.AdminDaoImpl;
import entity.Admin;

public class AdminBusiness {
    private AdminDAO adminDAO = new AdminDaoImpl();

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
