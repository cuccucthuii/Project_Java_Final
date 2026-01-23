package business;

import dao.AdminDAO;
import dao.impl.AdminDaoImpl;
import entity.Admin;

public class AdminBusiness {
    private AdminDAO adminDAO = new AdminDaoImpl();

    public boolean loginAdmin(Admin admin) {
        boolean result = adminDAO.loginAdmin(admin);
        return result;
    }
}
