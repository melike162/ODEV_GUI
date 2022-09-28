package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Educator  {
    private int id;
    private String name;
    private String userName;
    private String password;

    public Educator(int id, String name, String userName, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;

    }

    public Educator(){}

    public static ArrayList<Educator> getList(){
        ArrayList<Educator> educators=new ArrayList<>();
        String query="select * from user where type=educator";
        Educator obj;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);
            while (rs.next()){
                obj=new Educator();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("uname"));
                obj.setPassword(rs.getString("password"));
                educators.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return educators;
    }
    public static Educator getFetch(String userName){
        String query="select * from user where uname=?";
        Educator obj=null;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,userName);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("uname"));
                obj.setPassword(rs.getString("password"));
                obj.setId(rs.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static boolean update( int id,String name, String userName, String password){
        String query="update educator set name=?, uname=?, password=? where id=?";

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,userName);
            pr.setString(3,password);
            pr.setInt(4,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;

    }

    public static boolean add(int id,String name,String userName,String password){
        String query="insert into educator (id,name,uname,password) values (?,?,?,?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            pr.setString(2,name);
            pr.setString(3,userName);
            pr.setString(4,password);

            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id){
        String query="delete from educator where id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate()!=-1;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
