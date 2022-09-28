package Paket15_Gui.com.com_patika.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static int screenCenterPoint(String eksen, Dimension size){
        int point;
        switch (eksen){
            case "x": point=(Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
                break;
            case "y": point=(Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;
                break;
            default: point=0;
        }
        return point;
    }

    public static void setLayout(){
        for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
            System.out.println(info.getName()+" => "+info.getClassName());
            if (info.getName().equals("Nimbus")){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty(); // trim boşluk varsa siler
    }

    public static void showMsg(String str){
        optionPageTR();// Hata ekranındaki ok buttonunu tamama çevirdi
        String msg;
        String title;
        switch (str){
            case "fill":
                msg="Lütfen tüm alanları doldurunuz! ";
                title="Hata";
                break;
            case "done":
                msg="İşlem başarılı! ";
                title="Sonuç";
                break;
            case "error":
                msg="Bir hata oluştu";
                title="Hata";
            default:
                msg=str;
                title="Mesaj";
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean Confirm(String str){
        optionPageTR();// Hata ekranındaki yes no yu değiştirdi
        String msg;
        switch (str){
            case "sure":
                msg="Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
                break;
            default:
                msg=str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Son kararın mı?",JOptionPane.YES_NO_OPTION)==0;
    }

    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");
    }
}
