package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class questionInfo {
    private int id;
    private int user_id;
    private int patika_id;
    private int course_id;
    private int question_id;
    private String solution;

    public questionInfo(int id, int user_id, int patika_id, int course_id, int question_id, String solution) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.course_id = course_id;
        this.question_id = question_id;
        this.solution = solution;
    }

    public static boolean add(int user_id, int patika_id, int course_id, int question_id, String solution){
        String query="insert into questioni̇nfo ( user_id,patika_id,course_id,question_id,solution) values (?,?,?,?,?)";
        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);
            pr.setInt(3,course_id);
            pr.setInt(4,question_id);
            pr.setString(5,solution);
            return  pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static questionInfo getFetch(int question_id,int user_id){
        String query="select * from questioni̇nfo where question_id=? and user_id=?";
        questionInfo obj=null;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,question_id);
            pr.setInt(2,user_id);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj=new questionInfo(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),rs.getInt("course_id"),rs.getInt("question_id"),rs.getString("solution"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }
    public static boolean deleteQuestion(int question_id){
        String query="delete from questioni̇nfo where question_id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,question_id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
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

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
