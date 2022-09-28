package Paket15_Gui.com.com_patika.view;

import Paket15_Gui.com.com_patika.Helper.*;
import Paket15_Gui.com.com_patika.Model.*;

import javax.swing.*;

public class UpdateCourseGUI extends JFrame {
    private JPanel panel1;
    private JTextField fld_course_name;
    private JTextField fld_course_language;
    private JButton btn_course_update;
    private JComboBox cmb_patika_name;
    private JComboBox cmd_educator_name;
    private JPanel wrapper;
    private Course course;

    public UpdateCourseGUI(Course course){
        this.course=course;
        add(panel1);
        setSize(300,300);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize())); // Ekranı masa üstünün orta noktasına atadık
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_course_name.setText(course.getName());
        fld_course_language.setText(course.getLanguage());

        loadPatikaCombo();
        loadUserCombo();

        btn_course_update.addActionListener(e -> {
            Item patikaItem= (Item) cmb_patika_name.getSelectedItem(); // patika combobox ın içerisinde seçilen değeri aldık
            Item educatorItem= (Item) cmd_educator_name.getSelectedItem();
            if (Helper.isFieldEmpty(fld_course_name)||Helper.isFieldEmpty(fld_course_language)|| educatorItem.getKey()==0||patikaItem.getKey()==0){
                Helper.showMsg("fill");
            }else{
                if (Course.updete(course.getId(),educatorItem.getKey(),patikaItem.getKey(),fld_course_name.getText(),fld_course_language.getText())){
                    Helper.showMsg("done");
                }else {
                    Helper.showMsg("error");
                }
                dispose();// ekranı kapatmak için
            }

        });
    }

    public void loadPatikaCombo(){
        cmb_patika_name.removeAllItems();// içerisindeki tüm verileri siliyorsun
        cmb_patika_name.addItem(new Item(0,""));
        for (Patika obj: Patika.getList()){
            cmb_patika_name.addItem(new Item(obj.getId(),obj.getName()));
        }
    }

    public void loadUserCombo(){
        cmd_educator_name.removeAllItems();
        cmd_educator_name.addItem(new Item(0,""));
        for (User obj: User.getList()){
            if (obj.getType().equals("educator")){
                cmd_educator_name.addItem(new Item(obj.getId(),obj.getName()));
            }
        }
    }
}
