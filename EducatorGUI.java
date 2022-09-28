package Paket15_Gui.com.com_patika.view;

import Paket15_Gui.com.com_patika.Helper.*;
import Paket15_Gui.com.com_patika.Model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EducatorGUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JTable tbl_course_list;
    private JTable tbl_content_list;
    private JTextField fld_content_title;
    private JTextField fld_content_discription;
    private JTextField fld_content_discription_link;
    private JComboBox cmb_content_cours;
    private JButton btn_content_add;
    private JTextField fld_content_id;
    private JButton btn_content_delete;
    private JTable tbl_question_list;
    private JTextField fld_question_question;
    private JComboBox cmb_question_content;
    private JButton btn_question_add;
    private JTextField fld_question_id;
    private JButton btn_question_delete;
    private JLabel lbl_welcome;
    private JComboBox cmb_content_srch;
    private JButton btn_content_srch;
    private JComboBox cmb_question_srch;
    private JButton btn_question_srch2;
    private JButton btn_longout;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;
    private DefaultTableModel mdl_question_list;
    private Object[] row_question_list;

    public EducatorGUI(Educator educator){
        add(wrapper);
        setSize(600,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldiniz "+educator.getName());

        mdl_course_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. sütunun değerlerinin değişmesini engelledik
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] colmn_course_list={"ID","Ders Adı","Programlama Dili","Patika Adı"};
        mdl_course_list.setColumnIdentifiers(colmn_course_list);
        row_course_list = new Object[colmn_course_list.length];

        loadCourseModel(courseArry(educator));
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75); // 0. column un genişliği 75 olsun
        tbl_course_list.getTableHeader().setReorderingAllowed(false);// column ların yer değişimi engellendi



        tbl_course_list.getModel().addTableModelListener(e -> {
           if (e.getType()==TableModelEvent.UPDATE){
               int course_id= Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString());
               String course_name=tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),1).toString();
               String course_language=tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),2).toString();
               String course_patikaName=tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),3).toString();
               if (Patika.getFetch(course_patikaName)==null){
                   String str="Geçerli bir patika giriniz. Geçerli patikalar:";
                   for (Patika obj: Patika.getList()){
                       str=str+" "+obj.getName()+",";
                   }
                   Helper.showMsg(str);
               }else{
                   if (Course.updete(course_id,educator.getId(), Patika.getFetch(course_patikaName).getId(),course_name,course_language)){
                       Helper.showMsg("done");
                       loadContentModel(contentArr(educator));
                   }else {
                       Helper.showMsg("error");
                   }
               }
               loadCourseModel(courseArry(educator));
           }
        });

        //###
        // Content List
        mdl_content_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. sütunun değerlerinin değişmesini engelledik
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] colmn_content_list={"ID","Başlık","Açıklama","Link","Ders"};
        mdl_content_list.setColumnIdentifiers(colmn_content_list);
        row_content_list=new Object[colmn_content_list.length];

        loadContentModel(contentArr(educator));

        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);
        loadCourseCombo(courseCmb(educator));
        tbl_content_list.getModel().addTableModelListener(e -> {
            if (e.getType()==TableModelEvent.UPDATE){
                int id= Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),0).toString());
                String title =tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),1).toString();
                String discription=tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),2).toString();
                String discription_link=tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),3).toString();
                String course_name= tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),4).toString();
                if (Content.update(id,title,discription,discription_link,course_name)){
                    Helper.showMsg("done");
                }else {
                    Helper.showMsg("Geçerli bir ders giriniz!");
                }

                loadContentModel(contentArr(educator));
                loadQuestionModel(questionArr(educator));
                loadContentCombo(contentCmb(educator));

            }
        });

        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {// tabloda seçilen şeyin id yerinde yazmasını sağlar
            try{
                String selected_content_id=tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),0).toString();
                fld_content_id.setText(selected_content_id);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        //###
        //Question Lİst
        mdl_question_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. sütunun değerlerinin değişmesini engelledik
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        Object[] colmn_question_list={"ID","İçerik Başlığı","Soru"};
        mdl_question_list.setColumnIdentifiers(colmn_question_list);
        row_question_list=new Object[colmn_question_list.length];

        loadQuestionModel(questionArr(educator));
        tbl_question_list.setModel(mdl_question_list);
        tbl_question_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_question_list.getTableHeader().setReorderingAllowed(false);


        loadContentCombo(contentCmb(educator));

        tbl_question_list.getModel().addTableModelListener(e -> {
            if (e.getType()== TableModelEvent.UPDATE){
                int id= Integer.parseInt(tbl_question_list.getValueAt(tbl_question_list.getSelectedRow(),0).toString());
                String title=tbl_question_list.getValueAt(tbl_question_list.getSelectedRow(),1).toString();
                String question=tbl_question_list.getValueAt(tbl_question_list.getSelectedRow(),2).toString();
                Content content=Content.getFetch(title);
                if (content==null){
                    Helper.showMsg("Geçerli bir içerik başlığı giriniz!");
                }else{
                    if (Question.update(id,content.getId(),question)){
                        Helper.showMsg("done");
                    } else{
                        Helper.showMsg("error");
                    }
                }


                loadQuestionModel(questionArr(educator));
            }
        });

        tbl_question_list.getSelectionModel().addListSelectionListener(e -> {// tabloda seçilen şeyin id yerinde yazmasını sağlar
            try{
                String selected_question_id=tbl_question_list.getValueAt(tbl_question_list.getSelectedRow(),0).toString();
                fld_question_id.setText(selected_question_id);
            }catch (Exception ex){

            }
        });


        //###

        btn_content_add.addActionListener(e -> {
            if (Course.getFetch2(educator.getId())){
                Helper.showMsg("Kayıtlı olduğunuz bir ders yoktur. İçerik ekleyemezsiniz!");
            }else{
                if (Helper.isFieldEmpty(fld_content_discription) || Helper.isFieldEmpty(fld_content_discription_link) || Helper.isFieldEmpty(fld_content_title)){
                    Helper.showMsg("fill");
                }else{
                    Item courseItem= (Item) cmb_content_cours.getSelectedItem();
                    if (Content.getFetch(fld_content_title.getText())== null){
                        if (Content.add(fld_content_title.getText(),fld_content_discription.getText(),fld_content_discription_link.getText(),courseItem.getKey())){
                            Helper.showMsg("done");
                            fld_content_discription_link.setText(null);
                            fld_content_discription.setText(null);
                            fld_content_title.setText(null);
                        }else{
                            Helper.showMsg("error");
                        }
                    }else{
                        Helper.showMsg("Bu başlıkta bir içerik bulunmaktadır. Başka bir başlık giriniz!");
                    }

                }
            }
            loadContentModel(contentArr(educator));
            loadContentCombo(contentCmb(educator));

        });
        btn_content_delete.addActionListener(e -> {
            if (Course.getFetch2(educator.getId())){
                Helper.showMsg("Kayıtlı olduğunuz bir ders yoktur. İçerik silemezsiniz!");
            }else {
                if (Helper.isFieldEmpty(fld_content_id)){
                    Helper.showMsg("fill");
                }else{
                    int id= Integer.parseInt(fld_content_id.getText());
                    if (Content.delete(id)){
                        Helper.showMsg("done");

                        Question.deleteContentID(id);
                        fld_content_id.setText(null);
                    }else {
                        Helper.showMsg("error");
                    }
                }

            }
            loadContentModel(contentArr(educator));
            loadContentCombo(contentCmb(educator));
            loadQuestionModel(questionArr(educator));
        });

        btn_question_add.addActionListener(e -> {
            if (Course.getFetch2(educator.getId())){
                Helper.showMsg("Kayıtlı olduğunuz bir ders yoktur. İçerik ekleyemezsiniz!");
            }else{
                if (Helper.isFieldEmpty(fld_question_question)){
                    Helper.showMsg("fill");
                }else{
                    Item contentItem= (Item) cmb_question_content.getSelectedItem();
                    if (Question.add(contentItem.getKey(),fld_question_question.getText())){
                        Helper.showMsg("done");
                        fld_question_question.setText(null);
                    }else {
                        Helper.showMsg("error");
                    }
                }
            }

            loadQuestionModel(questionArr(educator));
        });
        btn_question_delete.addActionListener(e -> {
            if (Course.getFetch2(educator.getId())){
                Helper.showMsg("Kayıtlı olduğunuz bir ders yoktur. İçerik silemezsiniz!");
            }else{
                if (Helper.isFieldEmpty(fld_question_id)){
                    Helper.showMsg("fill");
                }else{
                    int id= Integer.parseInt(fld_question_id.getText());
                    if (Question.delete(id)){
                        Helper.showMsg("done");
                        fld_question_id.setText(null);
                    }else {
                        Helper.showMsg("error");
                    }
                }

            }

            loadQuestionModel(questionArr(educator));
        });
        btn_content_srch.addActionListener(e -> {
            Item courseItem= (Item) cmb_content_srch.getSelectedItem();
            ArrayList<Content > contentSrch=Content.getList(courseItem.getKey());
            loadContentModel(contentSrch);
        });
        btn_question_srch2.addActionListener(e -> {
            Item contentItem= (Item) cmb_question_srch.getSelectedItem();
            ArrayList<Question> questionSrch=Question.getList(contentItem.getKey());
            loadQuestionModel(questionSrch);
        });
        btn_longout.addActionListener(e -> {
            dispose();
            LoginGUI log=new LoginGUI();
        });
    }

    private void loadCourseModel(ArrayList<Course> courses) {
        DefaultTableModel clearModel= (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Course obj: courses){
            if (obj.getPatika()!=null){
                i=0;
                row_course_list[i++]=obj.getId();
                row_course_list[i++]=obj.getName();
                row_course_list[i++]=obj.getLanguage();
                row_course_list[i++]=obj.getPatika().getName();
                mdl_course_list.addRow(row_course_list);
            }

        }
    }

    private void loadContentModel(ArrayList<Content> contents) {
        DefaultTableModel clearModel= (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Content obj: contents){
            i=0;
            row_content_list[i++]=obj.getId();
            row_content_list[i++]=obj.getTitle();
            row_content_list[i++]=obj.getDiscription();
            row_content_list[i++]=obj.getDiscription_link();
            row_content_list[i++]=Course.getFetch(obj.getCourse_id()).getName();
            mdl_content_list.addRow(row_content_list);

        }
    }

    private void loadQuestionModel(ArrayList<Question> questions){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_question_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Question obj: questions){
            i=0;
            row_question_list[i++]=obj.getId();
            row_question_list[i++]=Content.getFetch(obj.getContent_id()).getTitle();
            row_question_list[i++]=obj.getQuestion();
            mdl_question_list.addRow(row_question_list);
        }
    }


    public void loadCourseCombo(ArrayList<Course> courses){
        cmb_content_cours.removeAllItems();
        cmb_content_srch.removeAllItems();
        for (Course obj: courses){
            cmb_content_cours.addItem(new Item(obj.getId(),obj.getName()));
            cmb_content_srch.addItem(new Item(obj.getId(),obj.getName()));
        }
    }

    private void loadContentCombo(ArrayList<Content> contents) {
        cmb_question_content.removeAllItems();
        cmb_question_srch.removeAllItems();
        for (Content obj: contents){
            cmb_question_content.addItem(new Item(obj.getId(),obj.getTitle()));
            cmb_question_srch.addItem(new Item(obj.getId(),obj.getTitle()));
        }
    }
    ArrayList<Question> questionCmb(Educator educator){
        ArrayList<Question> questions=new ArrayList<>();
        for (Content obj: contentCmb(educator)){
            for (Question obj2: Question.getList(obj.getId())){
                questions.add(obj2);
            }
        }
        return questions;
    }
    ArrayList<Content> contentCmb(Educator educator){
        ArrayList<Content> contents=new ArrayList<>();
        for (Course obj: courseCmb(educator)){
            for (Content obj2: Content.getList(obj.getId())){
                contents.add(obj2);
            }
        }
        return contents;
    }
    ArrayList<Course> courseCmb(Educator educator){
        return Course.getListID(educator.getId());
    }

    ArrayList<Question> questionArr(Educator educator){
        ArrayList<Question> questions=new ArrayList<>();
        for (Content obj: contentArr(educator)){
            for (Question obj2: Question.getList(obj.getId())){
                questions.add(obj2);
            }
        }
        return questions;
    }
    ArrayList<Content> contentArr(Educator educator){
        ArrayList<Content> contents=new ArrayList<>();
        for (Course obj: courseArry(educator)){
            for (Content obj2: Content.getList(obj.getId())){
                contents.add(obj2);
            }
        }
        return contents;
    }
    ArrayList<Course> courseArry(Educator educator){
        return Course.getListID(educator.getId());
    }
}
