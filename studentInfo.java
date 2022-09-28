package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class studentInfo {
    private int id;
    private int user_id;
    private int patika_id;

    public studentInfo(int id, int user_id, int patika_id) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
    }
    public static studentInfo getFetch(int user_id){
        String query="select * from studenti̇nfo where user_id=?";
        studentInfo obj=null;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj=new studentInfo(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }
    public static boolean deletePatika(int patika_id){
        String query="delete from studenti̇nfo where patika_id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,patika_id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean add(int user_id,int patika_id){
        String query="insert into studenti̇nfo (user_id,patika_id) values (?,?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean update(int user_id,int patika_id){
        String query="update studenti̇nfo set patika_id=? where user_id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,patika_id);
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

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }
}
