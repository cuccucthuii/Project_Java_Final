package dao.impl;

import dao.CourseDAO;
import entity.Course;
import util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDAO {
    public List<Course> findAllCourses() {
        Connection conn = null;
        CallableStatement stmt = null;
        List<Course> courses = null;
        try {
            conn = ConnectionDB.openConnection();
            stmt = conn.prepareCall("{ Call func_get_all_course()}");
            boolean hasData = stmt.execute();
            if (hasData) {
                courses = new ArrayList<>();
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setCourseDuration(rs.getInt("course_duration"));
                    course.setCourseInstructor(rs.getString("course_instructor"));
                    course.setCourseCreateAt(LocalDate.parse(rs.getString("course_create_at")));
                    courses.add(course);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, stmt);
        }
        return courses;
    }

    public boolean addCourse(Course course) {
        Connection conn = null;
        CallableStatement stmt = null;
        try {
            conn = ConnectionDB.openConnection();
            stmt = conn.prepareCall("call proc_create_course(?,?,?)");
            stmt.setString(1, course.getCourseName());
            stmt.setInt(2, course.getCourseDuration());
            stmt.setString(3, course.getCourseInstructor());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, stmt);
        }
        return false;
    }

    public boolean updateCourse(Course course) {
        Connection conn = null;
        CallableStatement stmt = null;
        try {
            conn = ConnectionDB.openConnection();
            stmt = conn.prepareCall("call proc_update_course(?,?,?,?)");
            stmt.setInt(1, course.getCourseId());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCourseDuration());
            stmt.setString(4, course.getCourseInstructor());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, stmt);
        }
        return false;
    }

    public Course searchCourseById(int courseId) {
        Connection conn = null;
        CallableStatement stmt = null;
        Course course = null;
        try {
            conn = ConnectionDB.openConnection();
            stmt = conn.prepareCall("{call func_search_course_by_id(?)}");
            stmt.setInt(1, courseId);
            boolean hasData = stmt.execute();
            if (hasData) {
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setCourseDuration(rs.getInt("course_duration"));
                    course.setCourseInstructor(rs.getString("course_instructor"));
                    course.setCourseCreateAt(LocalDate.parse(rs.getString("course_create_at")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, stmt);
        }
        return course;
    }

    public boolean deleteCourseById(int courseId) {
        Connection conn = null;
        CallableStatement stmt = null;
        try {
            conn = ConnectionDB.openConnection();
            stmt = conn.prepareCall("call proc_delete_course(?)");
            stmt.setInt(1, courseId);
            boolean hasData = stmt.execute();
            if (hasData) {
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, stmt);
        }
        return false;
    }

    public List<Course> searchCourseByName(String courseName) {
        Connection conn = null;
        CallableStatement stmt = null;
        List<Course> courses = null;
        try {
            conn = ConnectionDB.openConnection();
            stmt = conn.prepareCall("{call func_search_course_by_name(?)}");
            stmt.setString(1, courseName);
            boolean hasData = stmt.execute();
            if (hasData) {
                courses = new ArrayList<>();
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setCourseDuration(rs.getInt("course_duration"));
                    course.setCourseInstructor(rs.getString("course_instructor"));
                    course.setCourseCreateAt(LocalDate.parse(rs.getString("course_create_at")));
                    courses.add(course);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, stmt);
        }
        return courses;
    }

    public List<Course> sortCourseByIdDesc(){
        Connection conn = null;
        CallableStatement stmt = null;
        List<Course> courses = null;
        try {
            conn = ConnectionDB.openConnection();
            stmt = conn.prepareCall("{call func_soft_sourse_by_id_desc()}");
            boolean hasData = stmt.execute();
            if (hasData) {
                courses = new ArrayList<>();
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setCourseDuration(rs.getInt("course_duration"));
                    course.setCourseInstructor(rs.getString("course_instructor"));
                    course.setCourseCreateAt(LocalDate.parse(rs.getString("course_create_at")));
                    courses.add(course);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, stmt);
        }
        return courses;
    }

    // API COURSE FOR STUDENT
    @Override
    public List<Course> getCourseForStudent() {
        Connection conn = null;
        CallableStatement stmt = null;
        List<Course> courses = null;
        try{
            conn = ConnectionDB.openConnection();
            stmt = conn.prepareCall("{call func_get_course()}");
            boolean hasData = stmt.execute();
            if (hasData) {
                courses = new ArrayList<>();
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setCourseDuration(rs.getInt("course_duration"));
                    course.setCourseInstructor(rs.getString("course_instructor"));
                    course.setCourseCreateAt(LocalDate.parse(rs.getString("course_create_at")));
                    courses.add(course);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, stmt);
        }
        return courses;
    }


}
