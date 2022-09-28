package Paket15_Gui.com.com_patika.view;

import Paket15_Gui.com.com_patika.Helper.*;
import Paket15_Gui.com.com_patika.Model.*;

import javax.swing.*;

public class UpdatePatikaGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_parika_name;
    private JButton btn_update;
    private Patika patika;

    public UpdatePatikaGUI(Patika patika){
        this.patika=patika;
        add(wrapper);
        setSize(300,150);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize())); // Ekranı masa üstünün orta noktasına atadık
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_parika_name.setText(this.patika.getName());

        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_parika_name)){
                Helper.showMsg("fill");
            }else {
                if (Patika.update(patika.getId(),fld_parika_name.getText())){
                    Helper.showMsg("done");
                }else{
                    Helper.showMsg("error");
                }
            }
            dispose(); //Ekranı kapatması için
        });
    }
}
