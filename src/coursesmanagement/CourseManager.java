/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesmanagement;

import database.Connect;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import main.DragJFrame;
import main.Keyboard;
import notifications.ConfirmDialog;
import notifications.Message;
import notifications.SavedStatusNotice;
import notifications.WarningMessage;

/**
 *
 * @author Nadun
 */
public class CourseManager extends javax.swing.JFrame {

    /**
     * Creates new form CourseManager
     */
    private DefaultTableModel dtm;
    private static CourseManager instance=new CourseManager();
    private int selectedCoursecount;
    private int[] selectedCourses;
            
    public CourseManager() {
        initComponents();
        dtm=(DefaultTableModel) courseTable.getModel();
//         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        this.setSize(screenSize);
        DragJFrame df=new DragJFrame(this, jPanel2);
        
    }
    
    public static CourseManager getInstance(){
        return instance;   
    }
    
    public void showInterface(){
        retrieveData();
        instance.setVisible(true);
        
    }
    
    
    
    private String formatValue(double d){
            DecimalFormat df=new DecimalFormat("#0.00");
        return df.format(d);   
    }
    
   
    private void cleanTable(){
      dtm.getDataVector().removeAllElements();
      dtm.fireTableDataChanged();
    }
    
    
    
    private int getStudentCount(int courseID){
        
        int studentCount=0;
        String query="SELECT COUNT(stu.id) FROM student_has_course shc INNER JOIN student stu ON stu.id=shc.student_id WHERE "
                + "shc.course_id="+courseID+" AND stu.delete_status=0";
        Connect con=new Connect();
        ResultSet r=con.getQuery(query);
        try {
            while(r.next()){
                studentCount=r.getInt("COUNT(stu.id)");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentCount;
    }
    
    
    
    private void addCourse(int id,String courseName,String institute,String city,String startingDate,String fee){
        String serialNo=Integer.toString(dtm.getRowCount()+1);
        dtm.addRow(new Object[]{serialNo,id,courseName,institute,city,startingDate,fee,getStudentCount(id)});
    }
    
    
    
    private void resetSerialNumbers(){
           int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               dtm.setValueAt(i+1, i, 0);
               
           }   
    }
    
    
    
    
    public void deleteCourse(){
        Connect con=new Connect();   
        int r=selectedCoursecount;
        int selectedRows[]=selectedCourses;
        selectedRows = courseTable.getSelectedRows();
        for (int i = 0; i < r; i++) {
            int row=selectedRows[i];
            String id=dtm.getValueAt(row, 1).toString();
            
            String query="UPDATE course SET delete_status=1 WHERE id="+id;
            con.setQuery(query, null);
           
        }
         
            String query1="UPDATE student SET delete_status=1 WHERE id NOT IN (SELECT shc.student_id FROM student_has_course shc INNER "
                    + "JOIN course cour ON shc.course_id=cour.id INNER JOIN institution inst ON cour.institution_id=inst.id"
                    +" WHERE cour.delete_status=0 AND inst.delete_status=0)";
             con.setQuery(query1, null);
            
            searchCourse();
            resetSerialNumbers();
            
         if(r==1){
              
              WarningMessage.getInstance().showInterface("1 course deleted");   
            
            }else{
               
               WarningMessage.getInstance().showInterface(r+" courses deleted");
              
            }
    }
    
    
    public void retrieveData(){
      cleanTable();
      String query="SELECT inst.*,cour.* FROM institution inst INNER JOIN course "
              + "cour ON inst.id=cour.institution_id WHERE cour.delete_status=0";
      
      Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            int ID=r.getInt("cour.id");
            String courseName=r.getString("cour.name");
            String startingDate=r.getString("cour.start_date");
            Double courseFee=r.getDouble("cour.course_fee");
            String institute= r.getString("inst.name");
            String city=r.getString("inst.city");
                addCourse(ID, courseName, institute, city, startingDate, formatValue(courseFee));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseManager.class.getName()).log(Level.SEVERE, null, ex);
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
      cleanTable();
      
      Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            int ID=r.getInt("cour.id");
            String courseName=r.getString("cour.name");
            String startingDate=r.getString("cour.start_date");
            Double courseFee=r.getDouble("cour.course_fee");
            String institute=r.getString("inst.name");
            String city=r.getString("inst.city");
            
                
                addCourse(ID, courseName, institute, city, startingDate, formatValue(courseFee));
                
            }
            int rowCount = dtm.getRowCount();
        if(rowCount==0){
         notificationLabel.setText("No Results Found");
        }else if(rowCount==1){
         notificationLabel.setText("1 Result Found");   
        }else{
         notificationLabel.setText(rowCount+" Results Found");     
        }
        } catch (SQLException ex) {
            Logger.getLogger(CourseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void searchCourse(){
        
        String course=courseTxt.getText();
        String institute = instituteTxt.getText();
        String city = cityTxt.getText();
       
        String query="SELECT inst.*,cour.* FROM institution inst INNER JOIN course cour ON inst.id="
                + "cour.institution_id WHERE inst.city LIKE '"+city+"%' AND inst.name LIKE '"+institute+"%'"
                + " AND cour.name LIKE '%"+course+"%' AND inst.delete_status=0 AND cour.delete_status=0";
       retrieveData(query);
        
        resetSerialNumbers();
        
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
        courseTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        interfaceHead = new javax.swing.JLabel();
        interfaceIcon = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        keyBoardBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        searchBtn = new javax.swing.JButton();
        removeSearchBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        selectAllBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        courseTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        instituteTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cityTxt = new javax.swing.JTextField();
        notificationLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(7, 1, 36));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(7, 1, 36));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));

        courseTable.setBackground(new java.awt.Color(7, 1, 36));
        courseTable.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        courseTable.setForeground(new java.awt.Color(102, 102, 102));
        courseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Serial No", "Course ID", "Course Name", "Institute", "City / Town", "Starting Date", "Course Fee", "Registered student"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        courseTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        courseTable.setFocusable(false);
        courseTable.setGridColor(new java.awt.Color(24, 25, 54));
        courseTable.setRowHeight(30);
        courseTable.setSelectionBackground(new java.awt.Color(24, 25, 54));
        courseTable.setSelectionForeground(new java.awt.Color(153, 153, 153));
        courseTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(courseTable);
        if (courseTable.getColumnModel().getColumnCount() > 0) {
            courseTable.getColumnModel().getColumn(0).setResizable(false);
            courseTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            courseTable.getColumnModel().getColumn(1).setResizable(false);
            courseTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            courseTable.getColumnModel().getColumn(2).setResizable(false);
            courseTable.getColumnModel().getColumn(2).setPreferredWidth(350);
            courseTable.getColumnModel().getColumn(3).setResizable(false);
            courseTable.getColumnModel().getColumn(3).setPreferredWidth(250);
            courseTable.getColumnModel().getColumn(4).setResizable(false);
            courseTable.getColumnModel().getColumn(4).setPreferredWidth(150);
            courseTable.getColumnModel().getColumn(5).setResizable(false);
            courseTable.getColumnModel().getColumn(5).setPreferredWidth(150);
            courseTable.getColumnModel().getColumn(6).setResizable(false);
            courseTable.getColumnModel().getColumn(6).setPreferredWidth(150);
            courseTable.getColumnModel().getColumn(7).setResizable(false);
            courseTable.getColumnModel().getColumn(7).setPreferredWidth(200);
        }

        jPanel2.setBackground(new java.awt.Color(24, 25, 54));

        interfaceHead.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 33)); // NOI18N
        interfaceHead.setForeground(new java.awt.Color(153, 153, 153));
        interfaceHead.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        interfaceHead.setText("Courses List");

        interfaceIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/coursesList-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(interfaceIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(interfaceHead, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(880, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(interfaceHead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(interfaceIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(24, 25, 54));

        jPanel5.setBackground(new java.awt.Color(24, 25, 54));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        jButton5.setFocusPainted(false);
        jButton5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home-selected.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton5);

        keyBoardBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        keyBoardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Keyboard.png"))); // NOI18N
        keyBoardBtn.setBorder(null);
        keyBoardBtn.setBorderPainted(false);
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
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(7, 1, 36));
        jPanel6.setLayout(new java.awt.GridLayout(0, 1, 0, 3));

        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        searchBtn.setToolTipText("search");
        searchBtn.setBorder(null);
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
        selectAllBtn.setFocusPainted(false);
        selectAllBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/selectAll-selected.png"))); // NOI18N
        selectAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllBtnActionPerformed(evt);
            }
        });
        jPanel6.add(selectAllBtn);

        jLabel5.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Search");

        jLabel6.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Course Name");

        courseTxt.setBackground(new java.awt.Color(7, 1, 36));
        courseTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        courseTxt.setForeground(new java.awt.Color(102, 102, 102));
        courseTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        courseTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        jLabel7.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Institute");

        instituteTxt.setBackground(new java.awt.Color(7, 1, 36));
        instituteTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        instituteTxt.setForeground(new java.awt.Color(102, 102, 102));
        instituteTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        instituteTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        jLabel8.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("City / Town");

        cityTxt.setBackground(new java.awt.Color(7, 1, 36));
        cityTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        cityTxt.setForeground(new java.awt.Color(102, 102, 102));
        cityTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        cityTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        notificationLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 23)); // NOI18N
        notificationLabel.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 952, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(courseTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(notificationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 1277, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(courseTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(notificationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(250, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
            selectedCoursecount=courseTable.getSelectedRowCount();
            selectedCourses=new int[selectedCoursecount];
            selectedCourses = courseTable.getSelectedRows();
            
            if(dtm.getRowCount()==0){
              
            }else if(selectedCoursecount==0){
              
              WarningMessage.getInstance().showInterface("Select to delete");
             
            }else{
               if(selectedCoursecount==1){
                   
                ConfirmDialog.getInstance().showInterface("Are you sure you want to delete this", "selected course ?", ConfirmDialog.COURSE_DELETE);
              
                }else{
                ConfirmDialog.getInstance().showInterface("Are you sure you want to delete these", selectedCoursecount+" courses ?", ConfirmDialog.COURSE_DELETE);   
                   
               }
               
            }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void selectAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllBtnActionPerformed
        
        int rowCount = dtm.getRowCount();
        int selectedRowCount = courseTable.getSelectedRowCount();
        if(rowCount>0){
        if(rowCount==selectedRowCount){
           courseTable.setRowSelectionInterval(0, 0);
        }else{
          courseTable.selectAll();  
        }
        }
        
        
    }//GEN-LAST:event_selectAllBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed

        notificationLabel.setText("Searching.....");
        searchCourse();
        
    }//GEN-LAST:event_searchBtnActionPerformed

    private void removeSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSearchBtnActionPerformed
       courseTxt.setText("");
       instituteTxt.setText("");
       cityTxt.setText("");
        retrieveData();
        
    }//GEN-LAST:event_removeSearchBtnActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
       
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void keyBoardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyBoardBtnActionPerformed
        if(Keyboard.getInstance().isVisible()){
            Keyboard.getInstance().hideInterface();
        }
        else{
            Keyboard.getInstance().showInterface();

        }

    }//GEN-LAST:event_keyBoardBtnActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
       
        int rowCount = dtm.getRowCount();
        int selectedRowCount = courseTable.getSelectedRowCount();
        
        if(rowCount==0){
            
            
        }else if(selectedRowCount==0){
            WarningMessage.getInstance().showInterface("Select to edit");
            
        }else if(selectedRowCount>1){
            WarningMessage.getInstance().showInterface("Select only one to edit");
            
        }else{
            int row=courseTable.getSelectedRow();
            String selectedCourseID=dtm.getValueAt(row, 1).toString();
            NewCourse.getInstance().showInterface(selectedCourseID);
            
        }
        
        
    }//GEN-LAST:event_editBtnActionPerformed

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
            java.util.logging.Logger.getLogger(CourseManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CourseManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CourseManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CourseManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CourseManager().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField cityTxt;
    private javax.swing.JTable courseTable;
    private javax.swing.JTextField courseTxt;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField instituteTxt;
    private javax.swing.JLabel interfaceHead;
    private javax.swing.JLabel interfaceIcon;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton keyBoardBtn;
    private javax.swing.JLabel notificationLabel;
    private javax.swing.JButton removeSearchBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JButton selectAllBtn;
    // End of variables declaration//GEN-END:variables
}
