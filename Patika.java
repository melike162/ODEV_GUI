package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ArrayList<Patika> getList(){ //veri tabanındaki patika tablosundaki bütün verirleri arraylist olarak verir
        ArrayList<Patika> patikaList=new ArrayList<>();
        Patika obj;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery("select * from patika");
            while (rs.next()){
                obj=new Patika(rs.getInt("id"),rs.getString("name"));
                patikaList.add(obj);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return patikaList;
    }

    public static boolean add(String name){
        String query="insert into patika (name) values (?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id,String name){
        String query="update patika set name=? where id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setInt(2,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static Patika getFetch(int id){ //veri tabanından 1 değer çekmeye fetch denir
        Patika obj=null;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement("select * from patika where id=?");
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();

            if (rs.next()){
                obj=new Patika(rs.getInt("id"),rs.getString("name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static Patika getFetch(String name){
        Patika obj=null;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement("select * from patika where name=?");
            pr.setString(1,name);
            ResultSet rs=pr.executeQuery();

            if (rs.next()){
                obj=new Patika(rs.getInt("id"),rs.getString("name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }




    public static boolean delete(int id){
        String query="delete from patika where id=?";
        studentInfo.deletePatika(id);
        for (Course obj:Course.getList()){
            if (obj.getPatika().getId()==id){
                Course.delete(obj.getId());
            }
        }
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
}
