package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class contentİnfo {
    private int id;
    private int user_id;
    private int content_id;
    private String comment;
    private int point;

    public contentİnfo(int id, int user_id, int content_id, String comment, int point) {
        this.id = id;
        this.user_id = user_id;
        this.content_id = content_id;
        this.comment = comment;
        this.point = point;
    }

    public static boolean add(int user_id, int content_id, String comment, int point){
        String query="insert into contenti̇nfo (user_id,content_id,comment,point) values (?,?,?,?)";
        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,content_id);
            pr.setString(3,comment);
            pr.setInt(4,point);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean deleteContent(int content_id){
        String query="delete from contenti̇nfo where content_id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,content_id);
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
