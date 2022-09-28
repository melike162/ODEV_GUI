package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Content {
    private int id;
    private String title;
    private String discription;
    private String discription_link;
    private int course_id;

    public Content(int id, String title, String discription, String discription_link, int course_id) {
        this.id = id;
        this.title = title;
        this.discription = discription;
        this.discription_link = discription_link;
        this.course_id = course_id;
    }

    public static ArrayList<Content> getList(){
        String query="select * from coursecontent";
        ArrayList<Content> contents=new ArrayList<>();
        Content content;
        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                content=new Content(rs.getInt("id"),rs.getString("title"),rs.getString("discription"),rs.getString("discription_Link"),rs.getInt("course_id"));
                contents.add(content);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contents;
    }

    public static ArrayList<Content> getList(int course_id){
        String query="select * from coursecontent where course_id=?";
        ArrayList<Content> contents=new ArrayList<>();
        Content content;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,course_id);
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                content=new Content(rs.getInt("id"),rs.getString("title"),rs.getString("discription"),rs.getString("discription_Link"),rs.getInt("course_id"));
                contents.add(content);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contents;
    }

    public static boolean update(int id, String title,String discription,String discription_link,String course_name){
        String query="update coursecontent set title=?,discription=?,discription_Link=?,course_id=? where id=?";
        int course_id;
        if (Course.getFetch(course_name)==null){
            return false;
        }else {
            course_id=Course.getFetch(course_name).getId();
        }
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,title);
            pr.setString(2,discription);
            pr.setString(3,discription_link);
            pr.setInt(4,course_id);
            pr.setInt(5,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean add(String title,String discription,String discription_link,int courseid){
        String query="insert into coursecontent (title,discription,discription_Link,course_id) values (?,?,?,?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,title);
            pr.setString(2,discription);
            pr.setString(3,discription_link);
            pr.setInt(4,courseid);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id){
        String query="delete from coursecontent where id=?";
        Question.deleteContentID(id);
        contentİnfo.deleteContent(id);
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean deleteCourse(int course_id){
        String query="delete from coursecontent where course_id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,course_id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static Content getFetch(String title){
        String query="select * from coursecontent where title=?";
        Content obj=null;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,title);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj=new Content(rs.getInt("id"),rs.getString("title"),rs.getString("discription"),rs.getString("discription_Link"),rs.getInt("course_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }
    public static Content getFetch(int id){
        String query="select * from coursecontent where id=?";
        Content obj=null;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj=new Content(rs.getInt("id"),rs.getString("title"),rs.getString("discription"),rs.getString("discription_Link"),rs.getInt("course_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getDiscription_link() {
        return discription_link;
    }

    public void setDiscription_link(String discription_link) {
        this.discription_link = discription_link;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
