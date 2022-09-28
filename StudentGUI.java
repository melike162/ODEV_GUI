package Paket15_Gui.com.com_patika.view;

import Paket15_Gui.com.com_patika.Helper.Config;
import Paket15_Gui.com.com_patika.Helper.Helper;
import Paket15_Gui.com.com_patika.Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class StudentGUI extends JFrame {

    private JPanel panel1;
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JTable tbl_patika_list;
    private JTextField fld_patika_registor;
    private JButton btn_patika_registor;
    private JTable tbl_course_list;
    private JTextField fld_course_registor;
    private JButton btn_course_registor;
    private JTable tbl_content_list;
    private JTextField fld_content_id2;
    private JTextField fld_content_comment;
    private JComboBox cmb_content_point;
    private JButton btn_content_comment;
    private JTextField fld_content_id;
    private JButton btn_content_goQuiz;
    private JButton btn_student_longOut;
    private JLabel lbl_welcome;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;

    public StudentGUI(Student student) {
        add(wrapper);
        setSize(600,600);
        int x= Helper.screenCenterPoint("x",getSize());
        int y= Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldiniz "+student.getName());


        mdl_patika_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. sütunun değerlerinin değişmesini engelledik

                return false;
            }
        };
        Object[] colmn_patika_list={"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(colmn_patika_list); //başlıkları atadık
        row_patika_list=new Object[colmn_patika_list.length];

        loadPatikaModel();
        tbl_patika_list.setModel(mdl_patika_list);// patika tablosuna verileri attık
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);// sütun başlıklarını fareyle değiştirmeyi kapattık
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75); // patika tablosunun 0. sütununun max genişliği 75 olsun

        tbl_patika_list.getSelectionModel().addListSelectionListener(e -> {// tabloda seçilen şeyin id yerinde yazmasını sağlar
            try{
                String selectedID=tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString();
                fld_patika_registor.setText(selectedID);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });


        //#####
        //Course List
        mdl_course_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. sütunun değerlerinin değişmesini engelledik

                return false;
            }
        };
        Object[] col_course_list={"ID","Ders Adı","Programlama Dili"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        if (studentInfo.getFetch(student.getId())==null){
            Helper.showMsg("Lütfen bir patikaya kayıt olunuz!");
        }else{
            loadCourseModel(studentInfo.getFetch(student.getId()).getPatika_id());
        }

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75); // 0. column un genişliği 75 olsun
        tbl_course_list.getTableHeader().setReorderingAllowed(false);// column ların yer değişimi engellendi

        tbl_course_list.getSelectionModel().addListSelectionListener(e -> {// tabloda seçilen şeyin id yerinde yazmasını sağlar
            try{
                String selectedID=tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString();
                fld_course_registor.setText(selectedID);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        //#####
        //Content List
        mdl_content_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. sütunun değerlerinin değişmesini engelledik

                return false;
            }
        };
        Object[] colmn_content_list={"ID","title","Açıklama","Link","Ders"};
        mdl_content_list.setColumnIdentifiers(colmn_content_list);
        row_content_list=new Object[colmn_content_list.length];
        if (studentInfoCourse.getFetch(student.getId())==null){
            Helper.showMsg("Lütfen bir ders seçiniz!");
        }else{
            loadContentModel(studentInfoCourse.getFetch(student.getId()).getCourse_id());
        }

        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);

        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {// tabloda seçilen şeyin id yerinde yazmasını sağlar
            try{
                String selected_content_id=tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),0).toString();
                fld_content_id.setText(selected_content_id);
                fld_content_id2.setText(selected_content_id);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        //###
        btn_patika_registor.addActionListener(e -> {
            studentInfo st= studentInfo.getFetch(student.getId());
            if (fld_patika_registor.getText().equals("")){
                Helper.showMsg("fill");
            }else{
                int patikaID= Integer.parseInt(fld_patika_registor.getText());
                if (st==null){
                    if (studentInfo.add(student.getId(),patikaID)){
                        Helper.showMsg("done");
                        fld_patika_registor.setText(null);
                    }else {
                        Helper.showMsg("error");
                    }
                }else if (Integer.parseInt(fld_patika_registor.getText())!= studentInfo.getFetch(student.getId()).getPatika_id()){
                    if (studentInfo.update(student.getId(), patikaID)){
                        Helper.showMsg("done");
                        fld_patika_registor.setText(null);
                    }else {
                        Helper.showMsg("error");
                    }
                    studentInfoCourse.delete(student.getId());
                    Helper.showMsg("Lütfen bir ders seçiniz!");
                    DefaultTableModel clearModel= (DefaultTableModel) tbl_content_list.getModel();
                    clearModel.setRowCount(0);
                }else{
                    Helper.showMsg("Zaten bu patikadasınız!");
                }
            }
            loadCourseModel(studentInfo.getFetch(student.getId()).getPatika_id());

        });

        btn_course_registor.addActionListener(e -> {
            studentInfoCourse st= studentInfoCourse.getFetch(student.getId());
            int courseID= Integer.parseInt(fld_course_registor.getText());
            if (st==null){
                if (studentInfoCourse.add(student.getId(),courseID)){
                    Helper.showMsg("done");
                    fld_course_registor.setText(null);
                }else{
                    Helper.showMsg("error");
                }
            }else{
                if (studentInfoCourse.update(student.getId(),courseID)){
                    Helper.showMsg("done");
                    fld_course_registor.setText(null);
                }else{
                    Helper.showMsg("error");
                }
            }
            loadContentModel(studentInfoCourse.getFetch(student.getId()).getCourse_id());
        });
        btn_content_comment.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id2) || Helper.isFieldEmpty(fld_content_comment)){
                Helper.showMsg("fill");
            }else{
                int point= Integer.parseInt(cmb_content_point.getSelectedItem().toString());
                if (contentİnfo.add(student.getId(),Integer.parseInt(fld_content_id2.getText()),fld_content_comment.getText(),point)){
                    Helper.showMsg("Yorum kaydedilmiştir!");
                    fld_content_id2.setText(null);
                    fld_content_comment.setText(null);
                }else{
                    Helper.showMsg("error");
                }
            }
        });
        btn_content_goQuiz.addActionListener(e -> {
            QuestionGUI questionGUI=new QuestionGUI(student.getId(),studentInfo.getFetch(student.getId()).getPatika_id(),studentInfoCourse.getFetch(student.getId()).getCourse_id(),Integer.parseInt(fld_content_id.getText()));
        });
        btn_student_longOut.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI=new LoginGUI();
        });
    }


    private void loadContentModel(int course_id) {
        DefaultTableModel clearModel= (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Content obj: Content.getList(course_id)){
            i=0;
            row_content_list[i++]=obj.getId();
            row_content_list[i++]=obj.getTitle();
            row_content_list[i++]=obj.getDiscription();
            row_content_list[i++]=obj.getDiscription_link();
            row_content_list[i++]= Course.getFetch(obj.getCourse_id()).getName();
            mdl_content_list.addRow(row_content_list);

        }
    }

    private void loadCourseModel(int patika_id) {
        DefaultTableModel clearModel= (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);// son iki satırda tablodaki tüm değerleri sildik
        int i=0;
        for (Course obj: Course.getList(patika_id)){
            i=0;
            row_course_list[i++]=obj.getId();
            row_course_list[i++]=obj.getName();
            row_course_list[i++]=obj.getLanguage();
            mdl_course_list.addRow(row_course_list);
        }
    }

    public void loadPatikaModel(){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);// son iki satırda tablodaki tüm verileri tamizledik
        int i=0;
        for (Patika obj: Patika.getList()){
            i=0;
            row_patika_list[i++]=obj.getId();
            row_patika_list[i++]=obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }
    //public static void main(String[] args) {
    //        Helper.setLayout();
    //        StudentGUI studentGUI2=new StudentGUI(new Student(33,"Melike İşeri","mlk","456"));
    //    }
}
