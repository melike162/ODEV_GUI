package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String language;
    private Patika patika;
    private User user;

    public Course(int id, int user_id, int patika_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.language = language;
        this.patika=Patika.getFetch(patika_id);// getFetch() id den patikayı verir
        this.user=User.getFetch(user_id);
    }
    public Course(){}

    public static ArrayList<Course> getList(){
        ArrayList<Course> courseList=new ArrayList<>();
        Course obj;

        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery("select * from course");
            while (rs.next()){
                int id=rs.getInt("id");
                int user_id=rs.getInt("user_id");
                int patika_id=rs.getInt("patika_id");
                String name=rs.getString("name");
                String language=rs.getString("language");
                obj=new Course(id,user_id,patika_id,name,language);
                courseList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }

    public static ArrayList<Course> getList(int patika_id){
        ArrayList<Course> courseList =new ArrayList<>();
        Course obj;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement("select * from course where patika_id=?");
            pr.setInt(1,patika_id);
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                obj=new Course();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setLanguage(rs.getString("language"));
                obj.setUser_id(rs.getInt("user_id"));
                obj.setPatika_id(rs.getInt("patika_id"));
                courseList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }

    public static ArrayList<Course> getListID(int id){
        ArrayList<Course> courseList =new ArrayList<>();
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement("select * from course where user_id=?");
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            while(rs.next()){
                courseList.add(new Course(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),rs.getString("name"),rs.getString("language")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }

    public static boolean getFetch2(int id){

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement("select * from course where user_id=?");
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    public static boolean addCourse(int patika_id, int user_id, String language,String name){
        String query="insert into  course (user_id,patika_id,name,language) values (?,?,?,?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);
            pr.setString(3,name);
            pr.setString(4,language);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static ArrayList<Course> getListByUser(int user_id){
        ArrayList<Course> courseList=new ArrayList<>();
        Course obj;

        try {
            Statement st= DBConnector.getInstance().createStatement();
            ResultSet rs=st.executeQuery("select * from course where user_id="+user_id);
            while (rs.next()){
                int id=rs.getInt("id");
                int userID=rs.getInt("user_id");
                int patika_id=rs.getInt("patika_id");
                String name=rs.getString("name");
                String language=rs.getString("language");
                obj=new Course(id,userID,patika_id,name,language);
                courseList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }

    public static boolean delete(int id){
        String query="delete from course where id=?";
        for (Content obj: Content.getList(id)){
            Content.delete(obj.getId());
        }
        studentInfoCourse.deleteCourse(id);
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean userDelete(int user_id){
        String query="delete from course where user_id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean updete(int id,int user_id,int patika_id,String name,String language){
        String query="update  course set name=?, language=?,user_id=?,patika_id=? where id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,language);
            pr.setInt(3,user_id);
            pr.setInt(4,patika_id);
            pr.setInt(5,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static Course getFetch(int id){
        String query="select * from course where id=?";
        Course obj=null;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();

            if (rs.next()){
                obj=new Course(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),rs.getString("name"),rs.getString("language"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static Course getFetch(String name){
        String query="select * from course where name=?";
        Course obj=null;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            ResultSet rs=pr.executeQuery();

            if (rs.next()){
                obj=new Course(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),rs.getString("name"),rs.getString("language"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
