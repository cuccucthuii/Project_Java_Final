package dao;

import entity.Admin;
import util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class AdminDAO {
    public boolean loginAdmin(Admin admin) {
        Connection conn = null;
        CallableStatement stmt = null;
        try{
            conn = ConnectionDB.openConnection();
            stmt = conn.prepareCall("{call log_in_with_admin_account(?,?)}");
            stmt.setString(1,admin.getAdminUser());
            stmt.setString(2,admin.getAdminPassword());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getBoolean(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            ConnectionDB.closeConnection(conn, stmt);
        }
        return false;
    }
}
