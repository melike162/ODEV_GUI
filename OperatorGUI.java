package Paket15_Gui.com.com_patika.view;

import Paket15_Gui.com.com_patika.Helper.*;
import Paket15_Gui.com.com_patika.Model.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane top_operator;
    private JLabel lbl_welcome;
    private JPanel jpl_top;
    private JButton btn_logout;
    private JPanel panel_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_userName;
    private JTextField fld_user_password;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_srch_name;
    private JTextField fld_srch_userName;
    private JComboBox cmb_srch_type;
    private JButton btn_srch;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_course_list;
    private JPanel panel_patika_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_language;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private JTextField fld_course_id;
    private JComboBox cmb_course_educator;
    private JButton btn_course_src_educator;
    private JPanel pnl_content_list;
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
    private final operator operator;
    private DefaultTableModel mdl_user_list;// guıdeki tabloya veri aktarmak için
    private Object[] row_user_list;// guıdeki tabloya veri aktarmak için
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;// patika tablosundaki veriye sağ tıklandığında bir ekran çıkması için
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private JPopupMenu courseMenu;// course tablosundaki veriye sağ tıklandığında bir ekran çıkması için
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;
    private DefaultTableModel mdl_question_list;
    private Object[] row_question_list;

    public OperatorGUI(operator operator){
        this.operator=operator;
        add(wrapper);
        setSize(1000,500);
        int x= Helper.screenCenterPoint("x",getSize());
        int y= Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldiniz "+operator.getName());

        // guıdeki kullanıcı tablosuna veri aktarmak için:
        mdl_user_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. sütunun değerlerinin değişmesini engelledik
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list={"ID","AD Soyad","Kullanıcı adı","Şifre","Üyelik Tipi"}; // tablonun sütun başlıkları
        mdl_user_list.setColumnIdentifiers(col_user_list);

        //Object[] firstRow={"1","Melike İşeri","melike","123","Operator"};
        //mdl_user_list.addRow(firstRow);//tabloya veri aktarmayı sağlar
        row_user_list=new Object[col_user_list.length];
        loadUserModel();//Tabloya verileri yazdık
        tbl_user_list.setModel(mdl_user_list); // üstte yapılanları tabloya atmış olduk

        tbl_user_list.getTableHeader().setReorderingAllowed(false);// sütun başlıklarını fareyle değiştirmeyi kapattık
        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {// tabloda seçilen şeyin id yerinde yazmasını sağlar
            try{
                String selected_user_id=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString();
                // üstteki satır tablodaki seçilen satır 0. sütundaki değeri verir
                // getValueAt(x,y)-> x. satır y. sütundaki değeri verir
                // tbl_user_list.getSelectedRow()-> tablodaki seçilen değeri verir

                fld_user_id.setText(selected_user_id);// değiştirilcek id yi JTextField a atamış olduk
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> { // tablodaki değişiklikleri dinlemek
            if (e.getType()== TableModelEvent.UPDATE){ // type da yaptığın işlem bir update mi
                // Seçilen satırdaki değerleri aldık(alttaki 5 satır):
                int user_id=Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                String user_name=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                String user_userName=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                String user_password=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                String user_type=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString();
                if (User.upDate(user_id,user_name,user_userName,user_password,user_type)){

                    Helper.showMsg("done");
                }
                loadUserModel();
                loadUserCombo();
                loadCourseModel();

            }
        });
        //#####
        // guıdeki patika tablosuna veri aktarmak için:

        patikaMenu=new JPopupMenu();
        JMenuItem updateMenu= new JMenuItem("Güncelle");// sağ tıkladığında güncelle adında bir satır oluşacak
        JMenuItem deleteMenu=new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);
        tbl_patika_list.setComponentPopupMenu(patikaMenu); // oluşan bu satırları tabloya atadık

        //updateMenu yü dinleyerek işlem yapmak için: updateMenu.addActionListener(new ActionListener()) sonra alttaki olucak
        updateMenu.addActionListener(e -> {  //patikaMenu.add(updateMenu); yi dinledik
            int selectedRowID=Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            //tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString(); -> tablodaki seçilen satır 0. sütundaki değeri verir
            // getValueAt(x,y)-> x. satır y. sütundaki değeri verir
            // tbl_user_list.getSelectedRow()-> tablodaki seçilen değeri verir
            UpdatePatikaGUI updatePatikaGUI=new UpdatePatikaGUI(Patika.getFetch(selectedRowID));
            updatePatikaGUI.addWindowListener(new WindowAdapter() { // updatePatikaGUI kapanpıktan sonra yarılması gerekenler
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();

                }
            });
        });

        deleteMenu.addActionListener(e -> { //patikaMenu.add(deleteMenu); yi dinledik
            if(Helper.Confirm("sure")){
                int selectedRowID=Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());// üstte var
                if (Patika.delete(selectedRowID)){
                    Helper.showMsg("done");
                    loadCourseCombo();
                    loadContentModel();
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                    loadQuestionModel();
                }else{
                    Helper.showMsg("error");

                }
            }
        });

        tbl_patika_list.addMouseListener(new MouseInputAdapter() { //java method find another line
            @Override
            public void mousePressed(MouseEvent e) {

                //System.out.println(e.getPoint().toString()); // farenin tablodaki kordinatını verir
                Point point=new Point(e.getPoint()); // farenin tablodaki x ve y kordinatları point veri tipindedir ve biz onu böyle tutabiliriz
                
                int selectedRow1=tbl_patika_list.rowAtPoint(point);// bu kordinatların tabloda nereye denk geldiğini söyler
                                                                   // seçilen rowun id sini verir
                tbl_patika_list.setRowSelectionInterval(selectedRow1,selectedRow1);// patika tablosunda üstüne sağ tıkladığın satırı mavi yapar
            }
        });




        mdl_patika_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. sütunun değerlerinin değişmesini engelledik
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] colmn_patika_list={"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(colmn_patika_list); //başlıkları atadık
        row_patika_list=new Object[colmn_patika_list.length];

        loadPatikaModel();


        tbl_patika_list.setModel(mdl_patika_list);// patika tablosuna verileri attık

        tbl_patika_list.getTableHeader().setReorderingAllowed(false);// sütun başlıklarını fareyle değiştirmeyi kapattık
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75); // patika tablosunun 0. sütununun max genişliği 75 olsun
        //#####
        //guıdeki dersler tablosuna veri aktarmak için

        courseMenu=new JPopupMenu();
        JMenuItem updateMenuC=new JMenuItem("Güncelleme");
        JMenuItem deleteMenuC=new JMenuItem("Sil");
        courseMenu.add(updateMenuC);
        courseMenu.add(deleteMenuC);
        tbl_course_list.setComponentPopupMenu(courseMenu); // oluşan bu satırları tabloya atadık

        updateMenuC.addActionListener(e -> {
            int selectedRowID= Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString());//-> tablodaki seçilen satır 0. sütundaki değeri verir
            UpdateCourseGUI updateCourseGUI=new UpdateCourseGUI(Course.getFetch(selectedRowID));
            updateCourseGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    loadCourseModel();
                    loadContentModel();
                }
            });
        });


        deleteMenuC.addActionListener(e -> {
            if (Helper.Confirm("sure")){
                int selectedRowID= Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString());
                if (Course.delete(selectedRowID)){
                    Helper.showMsg("done");
                    loadContentModel();
                    loadQuestionModel();
                    loadCourseCombo();
                    loadCourseModel();


                }else {
                    Helper.showMsg("error");
                }
            }
        });


        mdl_course_list=new DefaultTableModel();
        Object[] col_course_list={"ID","Ders Adı","Programlama Dili","Patika Adı","Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();

        /*cmb_course_patika.addItem(new Item(1,"1.eleman")); // patikanın combobox ının içerisine eleman atadık
        cmb_course_patika.addItem(new Item(2,"2.eleman"));
        cmb_course_patika.addItem(new Item(3,"3.eleman"));*/
        loadPatikaCombo();// patika combobox ın içerisine verileri atadık
        loadUserCombo();// user combobox ın içerisine verileri atadık
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75); // 0. column un genişliği 75 olsun
        tbl_course_list.getTableHeader().setReorderingAllowed(false);// column ların yer değişimi engellendi

        //#####
        //Content list

        mdl_content_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) { // 0. sütunun değerlerinin değişmesini engelledik
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] colmn_content_list={"ID","title","Açıklama","Link","Ders"};
        mdl_content_list.setColumnIdentifiers(colmn_content_list);
        row_content_list=new Object[colmn_content_list.length];
        loadContentModel();
        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);

        loadCourseCombo();



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
                loadContentModel();
                loadQuestionModel();
                loadContentCombo();
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
        //Question list
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
        loadQuestionModel();
        tbl_question_list.setModel(mdl_question_list);
        tbl_question_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_question_list.getTableHeader().setReorderingAllowed(false);

        loadContentCombo();

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

              loadQuestionModel();
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



        btn_user_add.addActionListener(e -> { // button listener with lamb
            if (Helper.isFieldEmpty(fld_user_name)||Helper.isFieldEmpty(fld_user_userName)|| Helper.isFieldEmpty(fld_user_password)){
                Helper.showMsg("fill"); // eğer boş bir yer bırakırsa bir hata ekranı gösterir
            }else {
                String name=fld_user_name.getText();
                String userName=fld_user_userName.getText();
                String password=fld_user_password.getText();
                String type=cmb_user_type.getSelectedItem().toString();// JComboBox da seçilen değeri verir
                if (User.add(name,userName,password,type)){
                    Helper.showMsg("done"); // yeni  bir veri ekler ama eklaneni tabloda göstermez
                    loadUserModel();//tabloya verileri tekrar yazdık
                    loadUserCombo();
                    fld_user_name.setText(null);
                    fld_user_password.setText(null);
                    fld_user_userName.setText(null);
                }
            }
        });

        btn_user_delete.addActionListener(e -> { //guı deki enable ın tikini kaldırınca bir değer girilmez
            if (Helper.isFieldEmpty(fld_user_id)){
                Helper.showMsg("fill");
            }else {
                if (Helper.Confirm("sure")){ //İncele
                    int id=Integer.parseInt(fld_user_id.getText());
                    boolean t=User.getFetch(Integer.parseInt(fld_user_id.getText())).getType().equals("educator");
                    if (User.delete(id)){
                        if (t){
                            Course.userDelete(id);
                        }
                        Helper.showMsg("done");
                        loadUserModel();
                        loadUserCombo();
                        loadCourseModel();
                        loadCourseCombo();
                        loadContentModel();
                        loadCourseCombo();
                        loadQuestionModel();
                        fld_user_id.setText(null); // field ın içindeki sayıyı siliyorsun
                    }else {
                        Helper.showMsg("error");
                    }
                }

            }
        });
        btn_srch.addActionListener(e -> {
            String name=fld_srch_name.getText();
            String userName=fld_srch_userName.getText();
            String type=cmb_srch_type.getSelectedItem().toString();// JComboBox da seçilen değeri verir

            ArrayList<User> searcingUser=User.searchUserList(User.searchQuery(name,userName,type));
            loadUserModel(searcingUser);// ekrana bulduklarımızı bastırdık
        });
        btn_logout.addActionListener(e -> {
            dispose(); // çağrıldığı fireymi kapatır
            LoginGUI login=new LoginGUI();
        });
        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)){ // Senin Yazdığın metotla boş mu diye bakıyorsun
                Helper.showMsg("fill");
            }else {
                if (Patika.add(fld_patika_name.getText())){
                    Helper.showMsg("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    fld_patika_name.setText(null);
                }else {
                    Helper.showMsg("error");
                }
            }
        });
        btn_course_add.addActionListener(e -> {
            Item patikaItem= (Item) cmb_course_patika.getSelectedItem(); // patika combobox ın içerisinde seçilen değeri aldık
            Item userItem= (Item) cmb_course_user.getSelectedItem();// user combobox ın içerisinde seçilen değeri aldık
            if (Helper.isFieldEmpty(fld_course_language)|| Helper.isFieldEmpty(fld_course_name)){
                Helper.showMsg("fill");
            }else{
                if (Course.addCourse(patikaItem.getKey(),userItem.getKey(),fld_course_language.getText(),fld_course_name.getText())){
                    Helper.showMsg("done");
                    loadCourseModel();
                    loadCourseCombo();
                    fld_course_name.setText(null);
                    fld_course_language.setText(null);
                }else{
                    Helper.showMsg("error");
                }
            }

        });

        tbl_course_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_course_id=tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString();
                fld_course_id.setText(selected_course_id);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }

        });


        btn_course_src_educator.addActionListener(e -> {
            Item userItem= (Item) cmb_course_educator.getSelectedItem();
            ArrayList<Course> educatorSrch=Course.getListByUser(userItem.getKey());
            loadCourseModel(educatorSrch);
        });
        btn_content_add.addActionListener(e -> {
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

                loadContentModel();
                loadContentCombo();
            }
        });
        btn_content_delete.addActionListener(e -> {
            int id= Integer.parseInt(fld_content_id.getText());
            if (Content.delete(id)){
                Helper.showMsg("done");
                loadContentModel();
                Question.deleteContentID(id);
                fld_content_id.setText(null);
            }else {
                Helper.showMsg("error");
            }
            loadContentCombo();
            loadQuestionModel();
        });

        btn_question_add.addActionListener(e -> {
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
            loadQuestionModel();
        });

        btn_question_delete.addActionListener(e -> {
            int id= Integer.parseInt(fld_question_id.getText());
            if (Question.delete(id)){
                Helper.showMsg("done");
                fld_question_id.setText(null);
            }else {
                Helper.showMsg("error");
            }
            loadQuestionModel();
        });
    }





    private void loadContentModel() {
        DefaultTableModel clearModel= (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Content obj: Content.getList()){
            i=0;
            row_content_list[i++]=obj.getId();
            row_content_list[i++]=obj.getTitle();
            row_content_list[i++]=obj.getDiscription();
            row_content_list[i++]=obj.getDiscription_link();
            row_content_list[i++]=Course.getFetch(obj.getCourse_id()).getName();
            mdl_content_list.addRow(row_content_list);

        }
    }

    private void loadQuestionModel(){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_question_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Question obj: Question.getList()){
            i=0;
            row_question_list[i++]=obj.getId();
            row_question_list[i++]=Content.getFetch(obj.getContent_id()).getTitle();
            row_question_list[i++]=obj.getQuestion();
            mdl_question_list.addRow(row_question_list);
        }
    }

    private void loadCourseModel() {
    DefaultTableModel clearModel= (DefaultTableModel) tbl_course_list.getModel();
    clearModel.setRowCount(0);// son iki satırda tablodaki tüm değerleri sildik
        int i=0;
        for (Course obj: Course.getList()){
            i=0;
            row_course_list[i++]=obj.getId();
            row_course_list[i++]=obj.getName();
            row_course_list[i++]=obj.getLanguage();
            row_course_list[i++]=obj.getPatika().getName();
            row_course_list[i++]=obj.getUser().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    public void loadCourseModel(ArrayList<Course> list){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Course obj: list){
            i=0;
            row_course_list[i++]=obj.getId();
            row_course_list[i++]=obj.getName();
            row_course_list[i++]=obj.getLanguage();
            row_course_list[i++]=obj.getPatika().getName();
            row_course_list[i++]=obj.getUser().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    public void loadUserModel(){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);// son iki satırda tablodaki tüm değerleri sildik
        for (User obj: User.getList()){ //tabloya verileri yazdık
            row_user_list[0]=obj.getId();
            row_user_list[1]=obj.getName();
            row_user_list[2]=obj.getUserName();
            row_user_list[3]=obj.getPassword();
            row_user_list[4]=obj.getType();
            mdl_user_list.addRow(row_user_list);//tabloya veri aktarmayı sağlar

        }
    }

    //overloading->aşırı yüklenme
    public void loadUserModel(ArrayList<User> list){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);// son iki satırda tablodaki tüm değerleri sildik
        for (User obj: list){ //tabloya verileri yazdık
            row_user_list[0]=obj.getId();
            row_user_list[1]=obj.getName();
            row_user_list[2]=obj.getUserName();
            row_user_list[3]=obj.getPassword();
            row_user_list[4]=obj.getType();
            mdl_user_list.addRow(row_user_list);//tabloya veri aktarmayı sağlar

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

    private void loadContentCombo() {
        cmb_question_content.removeAllItems();
        for (Content obj: Content.getList()){
            cmb_question_content.addItem(new Item(obj.getId(),obj.getTitle()));
        }
    }

    public void loadPatikaCombo(){
        cmb_course_patika.removeAllItems();// içerisindeki tüm verileri siliyorsun
        for (Patika obj: Patika.getList()){
            cmb_course_patika.addItem(new Item(obj.getId(),obj.getName()));
        }
    }

    public void loadUserCombo(){
        cmb_course_educator.removeAllItems();
        cmb_course_user.removeAllItems();
        for (User obj: User.getList()){
            if (obj.getType().equals("educator")){
                cmb_course_user.addItem(new Item(obj.getId(),obj.getName()));
                cmb_course_educator.addItem(new Item(obj.getId(),obj.getName()));
            }
        }
    }

    public void loadCourseCombo(){
        cmb_content_cours.removeAllItems();
        for (Course obj: Course.getList()){
            cmb_content_cours.addItem(new Item(obj.getId(),obj.getName()));
        }
    }

    //public static void main(String[] args) {
    //        Helper.setLayout();
    //        operator op=new operator();
    //        op.setId(1);
    //        op.setName("Melike İşeri");
    //        op.setUserName("melike");
    //        op.setType("Operator");
    //        op.setPassword("123");
    //        OperatorGUI gu= new OperatorGUI(op);
    //    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
