package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private int id;
    private String name;
    private String uname;
    private String password;

    public Student(int id,String name, String uname, String password) {
        this.name = name;
        this.uname = uname;
        this.id = id;
        this.password=password;
    }

    public static boolean add(int id,String name, String uname,String password){
        String query="insert into user (id,name,uname,password) values (?,?,?,?,?,?)";
        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            pr.setString(2,name);
            pr.setString(3,uname);
            pr.setString(4,password);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean getFech(String uname){
        String query="select * from user where userName=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,uname);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                //obj=new Student(rs.getString("name"),rs.getString("uname"),rs.getInt("id"),rs.getInt("myCourse_id"),rs.getInt("myPatika_id"));
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
