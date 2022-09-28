package Paket15_Gui.com.com_patika.view;

import Paket15_Gui.com.com_patika.Helper.*;
import Paket15_Gui.com.com_patika.Model.Educator;
import Paket15_Gui.com.com_patika.Model.Student;
import Paket15_Gui.com.com_patika.Model.User;
import Paket15_Gui.com.com_patika.Model.operator;

import javax.swing.*;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_uname;
    private JPasswordField fld_user_password;
    private JButton btn_login;
    private JButton btn_add_student;

    public LoginGUI(){
        add(wrapper);
        setSize(500,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        btn_login.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_password)|| Helper.isFieldEmpty(fld_user_uname)){
                Helper.showMsg("fill");
            }else{
                User u=User.getFetch(fld_user_uname.getText(),fld_user_password.getText());
                if (u==null){
                    Helper.showMsg("Kullanıcı bulunamadı! ");
                }else {
                    switch (u.getType()){
                        case"operator" :
                            OperatorGUI opGUI=new OperatorGUI((operator) u);
                            break;
                        case "educator":

                            EducatorGUI edGUI=new EducatorGUI(new Educator(u.getId(),u.getName(),u.getUserName(),u.getPassword()));
                            break;
                        case "student":
                            Helper.setLayout();
                            StudentGUI stGUI=new StudentGUI(new Student(u.getId(),u.getName(),u.getUserName(), u.getPassword()));
                            break;
                    }
                    dispose();
                }
            }
        });
        btn_add_student.addActionListener(e -> {//Kayıt ol
            RegisterGUI rg=new RegisterGUI();
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI=new LoginGUI();
    }
}
