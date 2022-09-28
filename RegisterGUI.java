package Paket15_Gui.com.com_patika.view;

import Paket15_Gui.com.com_patika.Helper.*;
import Paket15_Gui.com.com_patika.Model.Student;
import Paket15_Gui.com.com_patika.Model.User;

import javax.swing.*;

public class RegisterGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_register_name;
    private JTextField fld_register_uname;
    private JButton kayıtOlButton;
    private JTextField fld_register_password;

    public RegisterGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        kayıtOlButton.addActionListener(e -> {

            if (Helper.isFieldEmpty(fld_register_name) || Helper.isFieldEmpty(fld_register_uname)|| Helper.isFieldEmpty(fld_register_password)){
                Helper.showMsg("fill");
            }else{
                if (Student.getFech(fld_register_uname.getText())){
                    Helper.showMsg("Bu kullanıcı adı kullanılmaktadır. Lütfen Başka bir Kullanıcı adı giriniz!");
                }else{
                    if (User.add(fld_register_name.getText(),fld_register_uname.getText(),fld_register_password.getText(),"student")){
                        Helper.showMsg("done");
                        User user=User.getFetch(fld_register_uname.getText());
                    }else {
                        Helper.showMsg("error");
                    }
                    dispose();
                }
            }

        });
    }
}
