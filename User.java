package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.*;

import java.sql.*;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String userName;
    private String type;
    private String password;

    public User(int id, String name, String userName, String type, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.type = type;
        this.password = password;
    }
    public User(){}

    public static ArrayList<User> getList(){ //veri tabanındaki user tablosundaki bütün verirleri arraylist olarak verir
        ArrayList<User> userList=new ArrayList<>();
        String sql="select * from user";
        User obj;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("userName"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
            st.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public static boolean add(String name,String userName,String pass,String type){
        String query="INSERT INTO user (name,userName,password,type) values (?,?,?,?)";
        User findUser=getFetch(userName);
        if (findUser!=null){
            Helper.showMsg("Bu kullanıcı adı kullanılmaktadır. Başka bir kullanıcı adı giriniz! ");
            return false;
        }
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,userName);
            pr.setString(3,pass);
            pr.setString(4,type);
            int responce=pr.executeUpdate();
            if (responce==-1){
                Helper.showMsg("error");
            }
            return responce!=-1;
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return true;
    }

    public static User getFetch(String userName){ // veri tabanından 1 değer çekme işlemine fetch denir
        User obj=null;
        String query="select * from user where username=?";
        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,userName);
            ResultSet rs=pr.executeQuery();
            if(rs.next()){
                obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("userName"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    // overloading-> aşırı yüklenme
    public static User getFetch(int id){ // veri tabanından 1 değer çekme işlemine fetch denir
        User obj=null;
        String query="select * from user where id=?";
        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            if(rs.next()){
                obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("userName"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    public static User getFetch(String userName,String password){ // veri tabanından 1 değer çekme işlemine fetch denir
        User obj=null;
        String query="select * from user where userName=? and password=?";
        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,userName);
            pr.setString(2,password);
            ResultSet rs=pr.executeQuery();
            if(rs.next()){
                switch (rs.getString("type")){
                    case "operator":
                        obj=new operator();
                        break;
                    default:
                        obj=new User();
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("userName"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    public static boolean delete(int id){
        String query="delete from user where id=?";
        ArrayList<Course> courseList=Course.getListByUser(id);
        for (Course obj: courseList){
            Course.delete(obj.getId());
        }
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public static boolean upDate(int id,String name,String userName,String pass,String type){
        String query="update user set name=?,userName=?,password=?,type=? where id=?";
        User findUser=getFetch(userName);
        if (findUser!=null && findUser.getId()!=id){
            Helper.showMsg("Bu kullanıcı adı kullanılmaktadır. Başka bir kullanıcı adı giriniz! ");
            return false;
        }
        String[] list={"educator","student","operator"};
        int b=0;
        for (String a:list){
            if (a.equals(type)){
                b++;
            }
        }
        if (b==0){
            Helper.showMsg("Bu üye tipi sistemimizde bulunmamaktadır. Lütfen geçerli bir üye tipi giriniz. Geçerli üye tipleri: educator,student,operator");
            return false;
        }

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,userName);
            pr.setString(3,pass);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userList=new ArrayList<>();
        User obj;
        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery(query);
            while (rs.next()){
                obj=new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUserName(rs.getString("userName"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
            st.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public static String searchQuery(String name,String userName,String type){
        String query="select * from user where userName like '%{{userName}}%' and name like '%{{name}}%'";
        query=query.replace("{{userName}}",userName);
        query=query.replace("{{name}}",name);
        if (!type.isEmpty()){
            query+=" and type like '{{}}'";
            query=query.replace("{{}}",type);
        }

        return query;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
