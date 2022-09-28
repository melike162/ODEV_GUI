package Paket15_Gui.com.com_patika.Model;

import Paket15_Gui.com.com_patika.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Question {
    private int id;
    private String question;
    private int content_id;

    public Question(int id, String question, int content_id) {
        this.id = id;
        this.question = question;
        this.content_id = content_id;
    }

    public static ArrayList<Question> getList(){
        String query="select * from questions";
        ArrayList<Question> questions=new ArrayList<>();
        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                questions.add(new Question(rs.getInt("id"),rs.getString("question"),rs.getInt("courseContent_id")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return questions;
    }

    public static ArrayList<Question> getList(int content_id){
        String query="select * from questions where courseContent_id=?";
        ArrayList<Question> questions=new ArrayList<>();
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,content_id);
            ResultSet rs=pr.executeQuery();
            while (rs.next()){
                questions.add(new Question(rs.getInt("id"),rs.getString("question"),rs.getInt("courseContent_id")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return questions;
    }

    public static boolean deleteContentID(int id){
        String query="delete from questions where courseContent_id=?";
        questionInfo.deleteQuestion(id);
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, int content_id,String question){
        String query="update questions set courseContent_id=?,question=? where id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,content_id);
            pr.setString(2,question);
            pr.setInt(3,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean add(int content_id,String question){
        String query="insert into questions (courseContent_id, question) values (?,?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,content_id);
            pr.setString(2,question);
            return  pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id){
        String query="delete from questions where id=?";
        questionInfo.deleteQuestion(id);
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }
}
