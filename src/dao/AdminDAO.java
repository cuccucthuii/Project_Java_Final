package dao;

import entity.Admin;
import util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public interface AdminDAO {
    public boolean loginAdmin(Admin admin);
}
