package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class studentInfoCourse {
    private int id;
    private int user_id;
    private int course_id;

    public studentInfoCourse(int id, int user_id, int course_id) {
        this.id = id;
        this.user_id = user_id;
        this.course_id = course_id;
    }
    public static studentInfoCourse getFetch(int user_id){
        String query="select * from studenti̇nfoC where user_id=?";
        studentInfoCourse obj=null;
        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj=new studentInfoCourse(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("course_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static boolean add(int user_id,int course_id){
        String query="insert into studenti̇nfoC (user_id,course_id) values (?,?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,course_id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int user_id){
        String query="delete from studenti̇nfoC where user_id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean deleteCourse(int course_id){
        String query="delete from studenti̇nfoC where course_id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,course_id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    public static boolean update(int user_id,int course_id){
        String query="update studenti̇nfoC set course_id=? where user_id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,course_id);
            pr.setInt(2,user_id);
            return  pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
