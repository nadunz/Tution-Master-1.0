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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import main.Keyboard;
import notifications.Message;
import notifications.SavedStatusNotice;
import notifications.WarningMessage;

/**
 *
 * @author Nadun
 */
public class ClassCalendar extends javax.swing.JFrame {

    /**
     * Creates new form ClassCalendar
     */
    private DefaultComboBoxModel dcm;
    private DefaultTableModel dtm;
    private static ClassCalendar instance=new ClassCalendar();
    public ClassCalendar() {
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        dtm=(DefaultTableModel) classCalendarTbl.getModel();
        dcm=(DefaultComboBoxModel) yearCombo.getModel();
    }
    
    public static ClassCalendar getInstance(){
        return instance;  
    }
    
    public void showInterface(){
        
        instance.setVisible(true);
        clearText();
        setRecentYears();
        yearCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        retrieveData();
    }
   
    private void cleanTable(){
      dtm.getDataVector().removeAllElements();
      dtm.fireTableDataChanged();
    }
    
    private void addDataToTabel(String course,String institute,String city,String date,int attendedCount){
        
        String serialNo=Integer.toString(dtm.getRowCount()+1);
        dtm.addRow(new Object[]{serialNo,course,institute,city,date,attendedCount});
    }
    
    private void resetSerialNumbers(){
           int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               dtm.setValueAt(i+1, i, 0);
               
           }   
    }
  
     private void setRecentYears(){
        dcm.removeAllElements();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
        String year=sdf.format(new Date());
        int yearInt=Integer.parseInt(year);
        for (int i = 0; i < 7; i++) {
            yearCombo.addItem(yearInt-i);
        }
     }
   
    
    private void clearText(){
        instituteTxt.setText("");
        cityTxt.setText("");
        courseTxt.setText("");
        dateTxt.setText("");
       
        
        
    }
   
    private int getAttendedCount(int classCalendarID){
        int studentCount=0;
        String query="SELECT COUNT(class_calendar_id) FROM student_has_class_calendar WHERE class_calendar_id="+classCalendarID;
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        
        try {
            while (r.next()) {
               studentCount=r.getInt("COUNT(class_calendar_id)");
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassCalendar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentCount;
        
    }
    
    private void retrieveData(){
      cleanTable();
      String query="SELECT ins.*,cour.name,cal.date,cal.id FROM ((class_calendar cal INNER JOIN course cour ON cal.course_id= cour.id) INNER JOIN institution ins"
              + " ON cour.institution_id=ins.id) WHERE cour.delete_status=0 AND ins.delete_status=0 AND cal.date LIKE '"
              +yearCombo.getSelectedItem().toString()+"%'";
      Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            int calendarID=r.getInt("cal.id");
            String date=r.getString("cal.date");
            String courseName=r.getString("cour.name");
            String institute=r.getString("ins.name");
            String city=r.getString("ins.city");
            
            
            addDataToTabel(courseName, institute, city, date,getAttendedCount(calendarID));
           
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassCalendar.class.getName()).log(Level.SEVERE, null, ex);
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
            int calendarID=r.getInt("cal.id");
            String date=r.getString("cal.date");
            String courseName=r.getString("cour.name");
            String institute=r.getString("ins.name");
            String city=r.getString("ins.city");
            
            
            addDataToTabel(courseName, institute, city, date,getAttendedCount(calendarID));
           
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassCalendar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    
    private void search(){
        
        String institute =instituteTxt.getText();
        String city=cityTxt.getText();
        String course = courseTxt.getText();
        String day=dateTxt.getText();
        
        if(day.length()==1){
           day="0"+day;
        }
        
        int monthNu=monthCombo.getSelectedIndex();
        String monthNumberString=Integer.toString(monthNu);
        
        if(monthNumberString.length()==1 && !monthNumberString.equals("0")){
           monthNumberString="0"+monthNumberString; 
        }
        
        String classCalendarDate=yearCombo.getSelectedItem().toString()+"-"+monthNumberString+"-"+day;
        
        if(monthNumberString.equals("0")){
            classCalendarDate=yearCombo.getSelectedItem().toString()+"-__-"+day;
        }
        
        String query="SELECT ins.*,cour.name,cal.date,cal.id FROM ((class_calendar cal INNER JOIN course cour ON cal.course_id= cour.id) INNER JOIN institution ins"
              + " ON cour.institution_id=ins.id) WHERE cour.name LIKE '%"+course+"%' AND ins.name LIKE '%"+institute+"%'"
                + " AND ins.city LIKE '"+city+"%' AND cal.date LIKE '"+classCalendarDate+"%' AND cour.delete_status=0 AND ins.delete_status=0";
        
                retrieveData(query);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        yearCombo = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        keyBoardBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        classCalendarTbl = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        searchBtn = new javax.swing.JButton();
        removeSearchBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        courseTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        instituteTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cityTxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        notificationLabel = new javax.swing.JLabel();
        monthCombo = new javax.swing.JComboBox();
        dateTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(7, 1, 36));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));

        jPanel2.setBackground(new java.awt.Color(24, 25, 54));

        jLabel10.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 33)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Class Calendar");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/classCalendar-icon.png"))); // NOI18N

        yearCombo.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        yearCombo.setForeground(new java.awt.Color(102, 102, 102));
        yearCombo.setBorder(null);
        yearCombo.setFocusable(false);
        yearCombo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                yearComboMouseClicked(evt);
            }
        });
        yearCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                yearComboItemStateChanged(evt);
            }
        });
        yearCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(yearCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yearCombo)
                .addContainerGap())
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
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(7, 1, 36));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));

        classCalendarTbl.setBackground(new java.awt.Color(7, 1, 36));
        classCalendarTbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        classCalendarTbl.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        classCalendarTbl.setForeground(new java.awt.Color(102, 102, 102));
        classCalendarTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Serial No", "Course Name", "Institution", "City", "Date", "Attended Count"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        classCalendarTbl.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        classCalendarTbl.setFocusable(false);
        classCalendarTbl.setGridColor(new java.awt.Color(24, 25, 54));
        classCalendarTbl.setRowHeight(30);
        classCalendarTbl.setSelectionBackground(new java.awt.Color(24, 25, 54));
        classCalendarTbl.setSelectionForeground(new java.awt.Color(153, 153, 153));
        classCalendarTbl.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(classCalendarTbl);
        if (classCalendarTbl.getColumnModel().getColumnCount() > 0) {
            classCalendarTbl.getColumnModel().getColumn(0).setResizable(false);
            classCalendarTbl.getColumnModel().getColumn(0).setPreferredWidth(100);
            classCalendarTbl.getColumnModel().getColumn(1).setResizable(false);
            classCalendarTbl.getColumnModel().getColumn(1).setPreferredWidth(350);
            classCalendarTbl.getColumnModel().getColumn(2).setResizable(false);
            classCalendarTbl.getColumnModel().getColumn(2).setPreferredWidth(230);
            classCalendarTbl.getColumnModel().getColumn(3).setResizable(false);
            classCalendarTbl.getColumnModel().getColumn(3).setPreferredWidth(200);
            classCalendarTbl.getColumnModel().getColumn(4).setResizable(false);
            classCalendarTbl.getColumnModel().getColumn(4).setPreferredWidth(150);
            classCalendarTbl.getColumnModel().getColumn(5).setResizable(false);
            classCalendarTbl.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

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

        jLabel5.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Search");

        jLabel8.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Course Name");

        courseTxt.setBackground(new java.awt.Color(7, 1, 36));
        courseTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        courseTxt.setForeground(new java.awt.Color(102, 102, 102));
        courseTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        courseTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        jLabel9.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Institute");

        instituteTxt.setBackground(new java.awt.Color(7, 1, 36));
        instituteTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        instituteTxt.setForeground(new java.awt.Color(102, 102, 102));
        instituteTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        instituteTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        jLabel11.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("City / Town");

        cityTxt.setBackground(new java.awt.Color(7, 1, 36));
        cityTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        cityTxt.setForeground(new java.awt.Color(102, 102, 102));
        cityTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        cityTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        jLabel13.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Date");

        notificationLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 23)); // NOI18N
        notificationLabel.setForeground(new java.awt.Color(102, 102, 102));

        monthCombo.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        monthCombo.setForeground(new java.awt.Color(102, 102, 102));
        monthCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(None)", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        monthCombo.setBorder(null);
        monthCombo.setFocusable(false);
        monthCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                monthComboItemStateChanged(evt);
            }
        });
        monthCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthComboActionPerformed(evt);
            }
        });

        dateTxt.setBackground(new java.awt.Color(7, 1, 36));
        dateTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        dateTxt.setForeground(new java.awt.Color(102, 102, 102));
        dateTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        dateTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 966, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(notificationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(monthCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(courseTxt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(instituteTxt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cityTxt, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(courseTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monthCombo, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(238, 238, 238)
                        .addComponent(notificationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
         search();
    }//GEN-LAST:event_searchBtnActionPerformed

    private void removeSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSearchBtnActionPerformed
        clearText();
        monthCombo.setSelectedIndex(0);
        retrieveData();
    }//GEN-LAST:event_removeSearchBtnActionPerformed

    private void keyBoardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyBoardBtnActionPerformed
        
        if(Keyboard.getInstance().isVisible()){
            Keyboard.getInstance().hideInterface();
        }
        else{
            Keyboard.getInstance().showInterface();

        }

    }//GEN-LAST:event_keyBoardBtnActionPerformed

    private void yearComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_yearComboItemStateChanged
       try{
        monthCombo.setSelectedIndex(0);
        search();
       
       }catch(Exception e){
           
       }
       
    }//GEN-LAST:event_yearComboItemStateChanged

    private void yearComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearComboActionPerformed

    private void monthComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_monthComboItemStateChanged
       
    }//GEN-LAST:event_monthComboItemStateChanged

    private void monthComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthComboActionPerformed

    private void yearComboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yearComboMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_yearComboMouseClicked

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
            java.util.logging.Logger.getLogger(ClassCalendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClassCalendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClassCalendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClassCalendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClassCalendar().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField cityTxt;
    private javax.swing.JTable classCalendarTbl;
    private javax.swing.JTextField courseTxt;
    private javax.swing.JTextField dateTxt;
    private javax.swing.JTextField instituteTxt;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton keyBoardBtn;
    private javax.swing.JComboBox monthCombo;
    private javax.swing.JLabel notificationLabel;
    private javax.swing.JButton removeSearchBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JComboBox yearCombo;
    // End of variables declaration//GEN-END:variables
}
