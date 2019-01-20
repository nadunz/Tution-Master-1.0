/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement;

import database.Connect;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import main.Keyboard;
import notifications.ConfirmDialog;
import notifications.Message;
import notifications.SavedStatusNotice;
import notifications.WarningMessage;

/**
 *
 * @author Nadun
 */
public class StudentRegistry extends javax.swing.JFrame {

    /**
     * Creates new form StudentRegistry
     */
    
    private DefaultTableModel dtm;
    private int selectedStudentCount;
    private int[] selectedStudent;
    private static StudentRegistry instance=new StudentRegistry();
    private int editingRow;
    
    public StudentRegistry() {
        initComponents();
        dtm=(DefaultTableModel) studentTabel.getModel();
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
    }
    
    public static StudentRegistry getInstance(){
        return instance;
    }
    
    public void showInterface(){
     retrieveData();
     instance.setVisible(true);
     
    }
    
    
    
    private void cleanTable(){
      dtm.getDataVector().removeAllElements();
      dtm.fireTableDataChanged();
    }
    
    private void addStudent(int id,String name,String barcode,String nic,String dob,String cardType,String address,String mobile,
            String telephone,String email,String guardianName,String guardianPhoneNo,String fullName,String registerDate){
        String serialNo=Integer.toString(dtm.getRowCount()+1);
        dtm.addRow(new Object[]{serialNo,id,name,barcode,fullName,nic,registerDate,cardType,dob,address,mobile,telephone,email,guardianName,guardianPhoneNo});
    }
    
    private void resetSerialNumbers(){
           int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               dtm.setValueAt(i+1, i, 0);
               
           }   
    }
   
    public void editTabel(int studentID){
        
    String query="SELECT stu.*,cardtyp.*,gu.*,pn.* FROM ((student stu LEFT JOIN card_type cardtyp ON stu.card_type_id=cardtyp.id)"
              + " LEFT JOIN guardian gu ON gu.student_id=stu.id) LEFT JOIN phone_number pn ON pn.student_id=stu.id "
              + "WHERE stu.delete_status=0 AND stu.id="+studentID;
              
      
      Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            
            String barcode=r.getString("stu.barcode");
            String name=r.getString("stu.name_with_initials");
            String fullname=r.getString("stu.fullname");
            String address=r.getString("stu.address");
            String dob=r.getString("stu.dob");
            String email=r.getString("stu.email");
            String nic=r.getString("stu.nic");
            String cardType=r.getString("cardtyp.name");
            String registerDate=r.getString("stu.register_date");
   
            String guardianName=r.getString("gu.name");
            String guardianPhoneNo=r.getString("gu.number");
            if(guardianPhoneNo.length()==9){
                guardianPhoneNo="0"+guardianPhoneNo;
            }
            String  mobile=r.getString("pn.mobile_number");
            if(mobile.length()==9){
                mobile="0"+mobile;
            }
            String tele=r.getString("pn.telephone_number");
            
            if(tele.length()==9){
                tele="0"+tele;
            }
            
            if(mobile.isEmpty()){
               mobile="-";
            }
            if(tele.isEmpty()){
               tele="-" ;
            }
            if(guardianPhoneNo.isEmpty()){
                guardianPhoneNo="-" ;
            }
            if(email.isEmpty()){
                email="-" ;
            }
            dtm.getDataVector().removeElementAt(editingRow);
            String serialNo=Integer.toString(editingRow+1);
            dtm.insertRow(editingRow, new Object[]{serialNo,studentID,name,barcode,fullname,nic,registerDate,cardType,
                dob,address,mobile,tele,email,guardianName,guardianPhoneNo});
            dtm.fireTableDataChanged();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    private void retrieveData(){
      cleanTable();
      notificationLabel.setText("Waiting....");
      String query="SELECT stu.*,cardtyp.*,gu.*,pn.* FROM ((student stu LEFT JOIN card_type cardtyp ON stu.card_type_id=cardtyp.id)"
              + " LEFT JOIN guardian gu ON gu.student_id=stu.id) LEFT JOIN phone_number pn ON pn.student_id=stu.id "
              + "WHERE stu.delete_status=0 ORDER BY stu.id ASC";
              
      
      Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            int ID=r.getInt("stu.id");
            String barcode=r.getString("stu.barcode");
            String nameWithInitials=r.getString("stu.name_with_initials");
            String fullname=r.getString("stu.fullname");
            String address=r.getString("stu.address");
            String dob=r.getString("stu.dob");
            String email=r.getString("stu.email");
            String nic=r.getString("stu.nic");
            String cardType=r.getString("cardtyp.name");
            String registerDate=r.getString("stu.register_date");
   
            String guardianName=r.getString("gu.name");
            String guardianPhoneNo=r.getString("gu.number");
            if(guardianPhoneNo.length()==9){
                guardianPhoneNo="0"+guardianPhoneNo;
            }
            String  mobile=r.getString("pn.mobile_number");
            if(mobile.length()==9){
                mobile="0"+mobile;
            }
            String tele=r.getString("pn.telephone_number");
            
            if(tele.length()==9){
                tele="0"+tele;
            }
            
            if(mobile.isEmpty()){
               mobile="-";
            }
            if(tele.isEmpty()){
               tele="-" ;
            }
            if(guardianPhoneNo.isEmpty()){
                guardianPhoneNo="-" ;
            }
            if(email.isEmpty()){
                email="-" ;
            }
            
            addStudent(ID, nameWithInitials, barcode, nic, dob, cardType, address, mobile, tele, email, guardianName, guardianPhoneNo,fullname,registerDate);
                
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            int rowCount = dtm.getRowCount();
        if(rowCount==0){
         notificationLabel.setText("No Results Found");
        }else if(rowCount==1){
         notificationLabel.setText("1 Result Found");   
        }else{
         notificationLabel.setText(rowCount+" Results Found");     
        }
        
        
    }
    
private void retrieveData(String query){
        
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            int ID=r.getInt("stu.id");
            String barcode=r.getString("stu.barcode");
            String nameWithInitials=r.getString("stu.name_with_initials");
            String fullname=r.getString("stu.fullname");
            String address=r.getString("stu.address");
            String dob=r.getString("stu.dob");
            String email=r.getString("stu.email");
            String nic=r.getString("stu.nic");
            String cardType=r.getString("cardtyp.name");
            String registerDate=r.getString("stu.register_date");
            
            String guardianName=r.getString("gu.name");
            String guardianPhoneNo=r.getString("gu.number");
            if(guardianPhoneNo.length()==9){
                guardianPhoneNo="0"+guardianPhoneNo;
            }
            
           String  mobile=r.getString("pn.mobile_number");
            if(mobile.length()==9){
                mobile="0"+mobile;
            }
            String tele=r.getString("pn.telephone_number");
            
            if(tele.length()==9){
                tele="0"+tele;
            }
            
            if(mobile.isEmpty()){
               mobile="-";
            }
            if(tele.isEmpty()){
               tele="-" ;
            }
            if(guardianPhoneNo.isEmpty()){
                guardianPhoneNo="-" ;
            }
            if(email.isEmpty()){
                email="-" ;
            }
            
            addStudent(ID, nameWithInitials, barcode, nic, dob, cardType, address, mobile, tele, email, guardianName, guardianPhoneNo,fullname,registerDate);
                
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void deleteStudent(){
       
        for (int i = 0; i < selectedStudentCount; i++) {
            int row=selectedStudent[i];
            String id=dtm.getValueAt(row, 1).toString();
            
            String query="UPDATE student SET delete_status=1 WHERE id="+id;
            Connect con=new Connect();
            con.setQuery(query, null);
            
            dtm.getDataVector().removeElementAt(row);
            dtm.fireTableDataChanged();
           
        }
        
            resetSerialNumbers();
        if(selectedStudentCount==1){
                
              WarningMessage.getInstance().showInterface("1 student deleted");
                
            }else{
              
              WarningMessage.getInstance().showInterface(selectedStudentCount+" students deleted");  
            }
        
            int rowCount = dtm.getRowCount();
        if(rowCount==0){
         notificationLabel.setText("No Results Found");
        }else if(rowCount==1){
         notificationLabel.setText("1 Result Found");   
        }else{
         notificationLabel.setText(rowCount+" Results Found");     
        }
       

    }
    
    private void clearText(){
        nameTxt.setText("");
        courseTxt.setText("");
        barcodetxt.setText("");
        nicTxt.setText("");
        cardTypeTxt.setText("");
        addressTxt.setText("");
        instituteTxt.setText("");
    }
    
    
   
    
    public void searchStudent(){
        
        String name=nameTxt.getText().trim();
        String barcode = barcodetxt.getText().trim();
        String nic = nicTxt.getText().trim();
        String cardType=cardTypeTxt.getText().trim();
        String address =addressTxt.getText().trim();
        String course=courseTxt.getText().trim();
        String institute = instituteTxt.getText().trim();
        
           
           notificationLabel.setText("Searching......");
           Connect c=new Connect();            
           cleanTable();

           String query="SELECT DISTINCT stu.*,cardtyp.*,gu.*,pn.* FROM (((student stu LEFT JOIN card_type cardtyp ON stu.card_type_id=cardtyp.id)"
              + " LEFT JOIN guardian gu ON gu.student_id=stu.id) LEFT JOIN phone_number pn ON pn.student_id=stu.id)"
                   + " INNER JOIN student_has_course shc ON shc.student_id=stu.id INNER JOIN course cour ON cour.id=shc.course_id"
                   + " INNER JOIN institution inst ON inst.id=cour.institution_id WHERE inst.name LIKE '"+institute+"%' AND cour.name LIKE "
                   + "'%"+course+"%' AND (stu.name_with_initials LIKE '%"+name+"%' OR stu.fullname LIKE"
                   + " '%"+name+"%') AND stu.barcode LIKE '"+barcode+"%' AND stu.nic LIKE '"+nic+"%' AND stu.address LIKE '%"+address+"%' "
                        + "AND cardtyp.name LIKE '%"+cardType+"%' AND stu.delete_status=0 AND inst.delete_status=0 AND cour.delete_status=0 ORDER BY stu.id ASC";
          
           
           retrieveData(query);
           
           resetSerialNumbers();
        
            int rowCount = dtm.getRowCount();
        if(rowCount==0){
         notificationLabel.setText("No Results Found");
        }else if(rowCount==1){
         notificationLabel.setText("1 Result Found");   
        }else{
         notificationLabel.setText(rowCount+" Results Found");     
        }
       
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        studentTabel = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        homeBtn = new javax.swing.JButton();
        keyBoardBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        searchBtn = new javax.swing.JButton();
        removeSearchBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        selectAllBtn = new javax.swing.JButton();
        viewBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        barcodetxt = new javax.swing.JTextField();
        nicTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cardTypeTxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        addressTxt = new javax.swing.JTextField();
        courseTxt = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        instituteTxt = new javax.swing.JTextField();
        notificationLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(7, 1, 36));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(7, 1, 36));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));

        studentTabel.setBackground(new java.awt.Color(7, 1, 36));
        studentTabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        studentTabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        studentTabel.setForeground(new java.awt.Color(102, 102, 102));
        studentTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Serial No", "Student ID", "Student Name", "Barcode", "Full Name", "NIC Number", "Registered date", "Card Type", "Date Of Birth", "Address", "Mobile", "Telephone", "Email", "Guardian Name", "Guardian's Phone no"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        studentTabel.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        studentTabel.setFocusable(false);
        studentTabel.setGridColor(new java.awt.Color(24, 25, 54));
        studentTabel.setRowHeight(30);
        studentTabel.setSelectionBackground(new java.awt.Color(24, 25, 54));
        studentTabel.setSelectionForeground(new java.awt.Color(153, 153, 153));
        studentTabel.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(studentTabel);
        if (studentTabel.getColumnModel().getColumnCount() > 0) {
            studentTabel.getColumnModel().getColumn(0).setResizable(false);
            studentTabel.getColumnModel().getColumn(0).setPreferredWidth(100);
            studentTabel.getColumnModel().getColumn(1).setResizable(false);
            studentTabel.getColumnModel().getColumn(1).setPreferredWidth(150);
            studentTabel.getColumnModel().getColumn(2).setPreferredWidth(350);
            studentTabel.getColumnModel().getColumn(3).setPreferredWidth(200);
            studentTabel.getColumnModel().getColumn(4).setPreferredWidth(500);
            studentTabel.getColumnModel().getColumn(5).setResizable(false);
            studentTabel.getColumnModel().getColumn(5).setPreferredWidth(150);
            studentTabel.getColumnModel().getColumn(6).setResizable(false);
            studentTabel.getColumnModel().getColumn(6).setPreferredWidth(150);
            studentTabel.getColumnModel().getColumn(7).setResizable(false);
            studentTabel.getColumnModel().getColumn(7).setPreferredWidth(150);
            studentTabel.getColumnModel().getColumn(8).setResizable(false);
            studentTabel.getColumnModel().getColumn(8).setPreferredWidth(150);
            studentTabel.getColumnModel().getColumn(9).setResizable(false);
            studentTabel.getColumnModel().getColumn(9).setPreferredWidth(650);
            studentTabel.getColumnModel().getColumn(10).setResizable(false);
            studentTabel.getColumnModel().getColumn(10).setPreferredWidth(180);
            studentTabel.getColumnModel().getColumn(11).setResizable(false);
            studentTabel.getColumnModel().getColumn(11).setPreferredWidth(180);
            studentTabel.getColumnModel().getColumn(12).setResizable(false);
            studentTabel.getColumnModel().getColumn(12).setPreferredWidth(300);
            studentTabel.getColumnModel().getColumn(13).setPreferredWidth(300);
            studentTabel.getColumnModel().getColumn(14).setResizable(false);
            studentTabel.getColumnModel().getColumn(14).setPreferredWidth(180);
        }

        jPanel3.setBackground(new java.awt.Color(24, 25, 54));

        jLabel12.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 33)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Student Registry");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/studentRegistry-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(960, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(24, 25, 54));

        jPanel5.setBackground(new java.awt.Color(24, 25, 54));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        homeBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        homeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        homeBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        homeBtn.setFocusPainted(false);
        homeBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home-selected.png"))); // NOI18N
        homeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeBtnActionPerformed(evt);
            }
        });
        jPanel5.add(homeBtn);

        keyBoardBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        keyBoardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Keyboard.png"))); // NOI18N
        keyBoardBtn.setBorder(null);
        keyBoardBtn.setBorderPainted(false);
        keyBoardBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Keyboard.png"))); // NOI18N
        keyBoardBtn.setFocusPainted(false);
        keyBoardBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Keyboard-selected.png"))); // NOI18N
        keyBoardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyBoardBtnActionPerformed(evt);
            }
        });
        jPanel5.add(keyBoardBtn);

        jPanel7.setBackground(new java.awt.Color(7, 1, 36));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close.png"))); // NOI18N
        cancelBtn.setBorder(null);
        cancelBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close.png"))); // NOI18N
        cancelBtn.setFocusPainted(false);
        cancelBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close-selected.png"))); // NOI18N
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        jPanel7.add(cancelBtn);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(7, 1, 36));
        jPanel6.setLayout(new java.awt.GridLayout(0, 1, 0, 3));

        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        searchBtn.setBorder(null);
        searchBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        searchBtn.setFocusPainted(false);
        searchBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search-selected.png"))); // NOI18N
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });
        jPanel6.add(searchBtn);

        removeSearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/removeSearch.png"))); // NOI18N
        removeSearchBtn.setToolTipText("remove search");
        removeSearchBtn.setBorder(null);
        removeSearchBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/removeSearch.png"))); // NOI18N
        removeSearchBtn.setFocusPainted(false);
        removeSearchBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/removeSearch-selected.png"))); // NOI18N
        removeSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSearchBtnActionPerformed(evt);
            }
        });
        jPanel6.add(removeSearchBtn);

        editBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/edit.png"))); // NOI18N
        editBtn.setToolTipText("edit");
        editBtn.setBorder(null);
        editBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/edit.png"))); // NOI18N
        editBtn.setFocusPainted(false);
        editBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/edit-selected.png"))); // NOI18N
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });
        jPanel6.add(editBtn);

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        deleteBtn.setToolTipText("delete");
        deleteBtn.setBorder(null);
        deleteBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        deleteBtn.setFocusPainted(false);
        deleteBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete-selected.png"))); // NOI18N
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        jPanel6.add(deleteBtn);

        selectAllBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/selectAll.png"))); // NOI18N
        selectAllBtn.setToolTipText("select all");
        selectAllBtn.setBorder(null);
        selectAllBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/selectAll.png"))); // NOI18N
        selectAllBtn.setFocusPainted(false);
        selectAllBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/selectAll-selected.png"))); // NOI18N
        selectAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllBtnActionPerformed(evt);
            }
        });
        jPanel6.add(selectAllBtn);

        viewBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/View.png"))); // NOI18N
        viewBtn.setToolTipText("view details");
        viewBtn.setBorder(null);
        viewBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/View.png"))); // NOI18N
        viewBtn.setFocusPainted(false);
        viewBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/view-selected.png"))); // NOI18N
        viewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBtnActionPerformed(evt);
            }
        });
        jPanel6.add(viewBtn);

        jLabel5.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Search");

        jLabel6.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Student Name");

        nameTxt.setBackground(new java.awt.Color(7, 1, 36));
        nameTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        nameTxt.setForeground(new java.awt.Color(102, 102, 102));
        nameTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        nameTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        nameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameTxtKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Barcode");

        barcodetxt.setBackground(new java.awt.Color(7, 1, 36));
        barcodetxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        barcodetxt.setForeground(new java.awt.Color(102, 102, 102));
        barcodetxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        barcodetxt.setCaretColor(new java.awt.Color(153, 153, 153));

        nicTxt.setBackground(new java.awt.Color(7, 1, 36));
        nicTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        nicTxt.setForeground(new java.awt.Color(102, 102, 102));
        nicTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        nicTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        jLabel8.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("NIC Number");

        jLabel9.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Card Type");

        cardTypeTxt.setBackground(new java.awt.Color(7, 1, 36));
        cardTypeTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        cardTypeTxt.setForeground(new java.awt.Color(102, 102, 102));
        cardTypeTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        cardTypeTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        cardTypeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardTypeTxtActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Address");

        addressTxt.setBackground(new java.awt.Color(7, 1, 36));
        addressTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        addressTxt.setForeground(new java.awt.Color(102, 102, 102));
        addressTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        addressTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        courseTxt.setBackground(new java.awt.Color(7, 1, 36));
        courseTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        courseTxt.setForeground(new java.awt.Color(102, 102, 102));
        courseTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        courseTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        jLabel15.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Course Name");

        jLabel16.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Institute");

        instituteTxt.setBackground(new java.awt.Color(7, 1, 36));
        instituteTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        instituteTxt.setForeground(new java.awt.Color(102, 102, 102));
        instituteTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        instituteTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        notificationLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 23)); // NOI18N
        notificationLabel.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 987, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(instituteTxt)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(nameTxt)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(barcodetxt)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cardTypeTxt)
                            .addComponent(addressTxt)
                            .addComponent(courseTxt)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nicTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(notificationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(barcodetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nicTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cardTypeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(courseTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addComponent(notificationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeBtnActionPerformed
        
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        
        this.dispose();
    }//GEN-LAST:event_homeBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        
        selectedStudentCount=studentTabel.getSelectedRowCount();
        selectedStudent=new int[selectedStudentCount];
        selectedStudent=studentTabel.getSelectedRows();
        
            if(dtm.getRowCount()==0){
                
            }else if(selectedStudentCount==0){
              
              WarningMessage.getInstance().showInterface("Select to delete");
            
            }else{
      
                if(selectedStudentCount==1){
                ConfirmDialog.getInstance().showInterface("Are you sure you want to delete this", "selected student ?", ConfirmDialog.STUDENT_DELETE);
              
                }else{
                ConfirmDialog.getInstance().showInterface("Are you sure you want to delete these", selectedStudentCount+" students ?", ConfirmDialog.STUDENT_DELETE);   
                }
            }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
            searchStudent();
    }//GEN-LAST:event_searchBtnActionPerformed

    private void cardTypeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardTypeTxtActionPerformed
       
    }//GEN-LAST:event_cardTypeTxtActionPerformed

    private void removeSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSearchBtnActionPerformed
        clearText();
        retrieveData();
    }//GEN-LAST:event_removeSearchBtnActionPerformed

    private void selectAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllBtnActionPerformed
        int rowCount = dtm.getRowCount();
        int selectedRowCount = studentTabel.getSelectedRowCount();
        
        if(rowCount>0){
        if(rowCount==selectedRowCount){
           studentTabel.setRowSelectionInterval(0, 0);
        }else{
          studentTabel.selectAll();  
        }
        }
    }//GEN-LAST:event_selectAllBtnActionPerformed

    private void keyBoardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyBoardBtnActionPerformed

        if(Keyboard.getInstance().isVisible()){
            Keyboard.getInstance().hideInterface();
        }
        else{
            Keyboard.getInstance().showInterface();

        }
    }//GEN-LAST:event_keyBoardBtnActionPerformed

    private void viewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewBtnActionPerformed
        int rowCount = dtm.getRowCount();
        int selectedRowCount = studentTabel.getSelectedRowCount();
        
        if(rowCount==0){
            WarningMessage.getInstance().showInterface("Nothing to view");
            
        }else if(selectedRowCount==0){
            WarningMessage.getInstance().showInterface("Select to view details");
            
        }else if(selectedRowCount!=1){
            WarningMessage.getInstance().showInterface("You can view only one");
            
        }else{
            int selectedRow = studentTabel.getSelectedRow();
            
            String stu_id = dtm.getValueAt(selectedRow, 1).toString();
            String name = dtm.getValueAt(selectedRow, 2).toString();
            String barcode = dtm.getValueAt(selectedRow, 3).toString();
            String fullName = dtm.getValueAt(selectedRow, 4).toString();
            String nic = dtm.getValueAt(selectedRow, 5).toString();
            String registerDate= dtm.getValueAt(selectedRow, 6).toString();
            String cardType = dtm.getValueAt(selectedRow, 7).toString();
            String dob = dtm.getValueAt(selectedRow, 8).toString();
            String address = dtm.getValueAt(selectedRow, 9).toString();
            String mobile = dtm.getValueAt(selectedRow, 10).toString();
            String tele = dtm.getValueAt(selectedRow, 11).toString();
            String email = dtm.getValueAt(selectedRow, 12).toString();
            String guardian = dtm.getValueAt(selectedRow, 13).toString();
            String guardianPhone = dtm.getValueAt(selectedRow, 14).toString();
            
            ApplicationView.getInstance().showInterface(stu_id, name, fullName, barcode, address, dob, nic, cardType, mobile, tele, email, guardian, guardianPhone,registerDate);
            
        }
    }//GEN-LAST:event_viewBtnActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        int rowCount = dtm.getRowCount();
        int selectedRowCount = studentTabel.getSelectedRowCount();
        
        if(rowCount==0){
           
            
        }else if(selectedRowCount==0){
            WarningMessage.getInstance().showInterface("Select to edit");
            
        }else if(selectedRowCount!=1){
            Message.getInstance().showInterface("Select only one student","to edit");
            
        }else{
            int selectedRow = studentTabel.getSelectedRow();
            editingRow=selectedRow;
            
            String stu_id = dtm.getValueAt(selectedRow, 1).toString();
            String name = dtm.getValueAt(selectedRow, 2).toString();
            String barcode = dtm.getValueAt(selectedRow, 3).toString();
            String fullName = dtm.getValueAt(selectedRow, 4).toString();
            String nic = dtm.getValueAt(selectedRow, 5).toString();
            String dob = dtm.getValueAt(selectedRow, 8).toString();
            String cardType = dtm.getValueAt(selectedRow, 7).toString();
            String address = dtm.getValueAt(selectedRow, 9).toString();
            String mobile = dtm.getValueAt(selectedRow, 10).toString();
            String tele = dtm.getValueAt(selectedRow, 11).toString();
            String email = dtm.getValueAt(selectedRow, 12).toString();
            String guardian = dtm.getValueAt(selectedRow, 13).toString();
            String guardianPhone = dtm.getValueAt(selectedRow, 14).toString();
            
            NewApplication.getInstance().showInterface(Integer.parseInt(stu_id), name, fullName, barcode, address,
                    dob, nic, cardType, mobile, tele, email, guardian, guardianPhone);
        }
    }//GEN-LAST:event_editBtnActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
       SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void nameTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameTxtKeyReleased
        
    }//GEN-LAST:event_nameTxtKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentRegistry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentRegistry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentRegistry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentRegistry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentRegistry().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressTxt;
    private javax.swing.JTextField barcodetxt;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField cardTypeTxt;
    private javax.swing.JTextField courseTxt;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JTextField instituteTxt;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton keyBoardBtn;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JTextField nicTxt;
    private javax.swing.JLabel notificationLabel;
    private javax.swing.JButton removeSearchBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JButton selectAllBtn;
    private javax.swing.JTable studentTabel;
    private javax.swing.JButton viewBtn;
    // End of variables declaration//GEN-END:variables
}
