package Paket15_Gui.com.com_patika.view;

import Paket15_Gui.com.com_patika.Helper.Helper;
import Paket15_Gui.com.com_patika.Model.Content;
import Paket15_Gui.com.com_patika.Model.Question;
import Paket15_Gui.com.com_patika.Model.questionInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QuestionGUI extends JFrame{

    private JPanel wrapper;
    private JTable tbl_question_list;
    private JTextField fld_question_id;
    private JTextField fld_question_answer;
    private JButton btn_question_save;
    private JButton btn_question_longOut;
    private DefaultTableModel mdl_question_list;
    private Object[] row_question_list;

    public QuestionGUI(int user_id, int patika_id, int course_id, int content_id) {
        System.out.println(user_id);
        System.out.println(patika_id);
        System.out.println(course_id);
        System.out.println(content_id);

        add(wrapper);
        setSize(450,450);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        mdl_question_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. sütunun değerlerinin değişmesini engelledik
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        Object[] colmn_question_list={"ID","İçerik","Soru"};
        mdl_question_list.setColumnIdentifiers(colmn_question_list);
        row_question_list=new Object[colmn_question_list.length];
        loadQuestionModel(content_id);
        tbl_question_list.setModel(mdl_question_list);
        tbl_question_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_question_list.getTableHeader().setReorderingAllowed(false);

        tbl_question_list.getSelectionModel().addListSelectionListener(e -> {// tabloda seçilen şeyin id yerinde yazmasını sağlar
            try{
                String selected_question_id=tbl_question_list.getValueAt(tbl_question_list.getSelectedRow(),0).toString();
                fld_question_id.setText(selected_question_id);
            }catch (Exception ex){

            }
        });


        btn_question_longOut.addActionListener(e -> dispose());
        btn_question_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_question_answer) || Helper.isFieldEmpty(fld_question_id)){
                Helper.showMsg("fill");
            }else{
                if (questionInfo.getFetch(Integer.parseInt(fld_question_id.getText()),user_id)!=null){
                    Helper.showMsg("Bu soruyu daha önce cevapladınız!");
                }else {
                    if (questionInfo.add(user_id,patika_id,course_id,Integer.parseInt(fld_question_id.getText()),fld_question_answer.getText())){
                        Helper.showMsg("done");
                        fld_question_answer.setText(null);
                        fld_question_id.setText(null);
                    }else{
                        Helper.showMsg("error");
                    }
                }
            }
        });
    }

    private void loadQuestionModel(int content_id){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_question_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Question obj: Question.getList(content_id)){
            i=0;
            row_question_list[i++]=obj.getId();
            row_question_list[i++]= Content.getFetch(obj.getContent_id()).getTitle();
            row_question_list[i++]=obj.getQuestion();
            mdl_question_list.addRow(row_question_list);
        }
    }
}
