/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceManagement;

import database.Connect;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import main.Keyboard;
import notifications.ConfirmDialog;
import notifications.Message;
import notifications.SavedStatusNotice;
import notifications.WarningMessage;
import studentmanagement.NewApplication;

/**
 *
 * @author Nadun
 */
public class Attendance extends javax.swing.JFrame {

    /**
     * Creates new form Attendance
     */

    public static final int NEXT=1, PREVIOUS=2, ANYWHERE=3;
    public static final int TUTE_NOT_ISSUED=0, TUTE_ISSUED=1; 
    private int currentYear, currentMonth, labelYear, labelMonth;
    private DefaultTableModel dtm;
    private static Attendance instance=new Attendance();
    private int selectedRowCount;
    private int[] selectedRows;
    
    public Attendance() {
        
        initComponents();
  
        dtm=(DefaultTableModel) attendanceTbl.getModel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
       
    }
     public static Attendance getInstance(){
        return instance;  
    }
    
    public void showInterface(){
       
       getCurrentMonth();
       clearText();
       setMonth();
       retrieveData();
       instance.setVisible(true);
       
    }
    
    public void goTo(int where, String year, String month){
       
        switch(where){
           
            case 1:
                
                    if(labelMonth==12){
                       labelYear=++labelYear;
                       labelMonth=1;
                    }else{
                    labelMonth=++labelMonth;
                    }
                    setMonth();
                if(currentYear==labelYear && currentMonth==labelMonth){
                   goToNextBtn.setEnabled(false);
                   goToNextBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToNext-disable.png")));
                }
              
                break;
            case 2:
                
                if(labelMonth==1){
                    labelYear=--labelYear;
                    labelMonth=12;
                }else{
                labelMonth=--labelMonth;
                }
                setMonth();
                
                if(labelYear==(currentYear-5) && labelMonth==1){
                  goToPreviousBtn.setEnabled(false);
                  goToPreviousBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToPrevious-disable.png")));
                }                
                break;
            case 3:
                
                labelYear=Integer.parseInt(year);
                labelMonth=Integer.parseInt(month);
                
                setMonth();
                goToNextBtn.setEnabled(true);
                goToPreviousBtn.setEnabled(true);
                if(labelYear==(currentYear-5) && labelMonth==1){
                  goToPreviousBtn.setEnabled(false);
                  goToPreviousBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToPrevious-disable.png")));
                }    
                
                if(currentYear==labelYear && currentMonth==labelMonth){
                   goToNextBtn.setEnabled(false);
                   goToNextBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToNext-disable.png")));
                }
                notificationLabel.setText("Waiting.....");
                search();
        }
    }
    
    
    private void setMonth(){
        
        String monthName=null;
        
        switch(labelMonth){
            case 1:
                monthName="January";
               break; 
            case 2:
                monthName="February";
               break;
            case 3:
                monthName="March";
               break; 
            case 4:
                monthName="April";
               break;
            case 5:
                monthName="May";
               break; 
            case 6:
                monthName="June";
               break;
            case 7:
                monthName="July";
               break; 
            case 8:
                monthName="August";
               break;
            case 9:
                monthName="September";
               break; 
            case 10:
                monthName="October";
               break; 
            case 11:
                monthName="November";
               break;
            case 12:
                monthName="December";
               break;
            
        }
        
        yearLabel.setText(labelYear+"");
        monthLabel.setText(monthName);
        
        
    }
     private void getCurrentMonth(){
         
        Calendar c=new GregorianCalendar();
        currentYear=c.get(Calendar.YEAR);
        currentMonth=c.get(Calendar.MONTH)+1;
        labelYear=currentYear;
        labelMonth=currentMonth;
       
     }
   
    
    private void cleanTable(){
      dtm.getDataVector().removeAllElements();
      dtm.fireTableDataChanged();
    }
    
    
    private void addDataToTabel(String barcode,String stu_Name,String course,String institute,String city,
            String calendarDate,String attendance,String tutes_issues){
       
        dtm.addRow(new Object[]{barcode,stu_Name,course,institute,city,calendarDate,attendance,tutes_issues});
    }
    
   
    
    private void clearText(){
        nameTxt.setText("");
        barcodeTxt.setText("");
        courseTxt.setText("");
        instituteTxt.setText("");
        cityTxt.setText("");
        dateTxt.setText("");
    }
        
    
    private String getSelectedMonth(){
        
        String year = Integer.toString(labelYear);
        String month=Integer.toString(labelMonth);
        
        if(month.length()==1){
            month="0"+month;
            
        }
        
        return (year+"-"+month);
        
    }
    

   public void retrieveData(){
     
      notificationLabel.setText("Waiting.....");
      cleanTable();
      
      Connect c=new Connect();
      String query="SELECT shcc.*,stu.barcode,stu.name_with_initials,shc.*,cour.name,ins.*,cal.* FROM ((((student stu INNER JOIN "
              + "student_has_course shc ON stu.id=shc.student_id) "
              + "INNER JOIN course cour ON cour.id=shc.course_id) INNER JOIN institution ins ON cour.institution_id=ins.id)"
              + " INNER JOIN class_calendar cal ON cal.course_id=cour.id AND STRCMP(cal.date,stu.register_date)>=0) "
              + " LEFT JOIN student_has_class_calendar shcc ON cal.id=shcc.class_calendar_id AND "
              + "stu.id=shcc.student_id WHERE cal.date LIKE '"+getSelectedMonth()+"%'"
              + " AND stu.delete_status=0 AND cour.delete_status=0 AND ins.delete_status=0 ORDER BY cal.date DESC";
      ResultSet r=c.getQuery(query);
      try {
          
      while(r.next()){
          
            
            String barcode=r.getString("stu.barcode");
            String studentName=r.getString("stu.name_with_initials");
            String classCalendarDate=r.getString("cal.date");
            String courseName=r.getString("cour.name");
            String institute=r.getString("ins.name");
            String city=r.getString("ins.city");

            String attendanceString="X";
            String tute_issuesString="X";
           
                String tute_issues=r.getString("shcc.tutes_issued");
                
              if(tute_issues!=null){
                  
                if(tute_issues.equals("1")){
                tute_issuesString="Issued";
                attendanceString="Attended";

                }else if(tute_issues.equals("0")){
                    tute_issuesString="X";
                    attendanceString="Attended";
                }
            
            }
            
           
            addDataToTabel(barcode,studentName,courseName,institute,city,classCalendarDate,attendanceString,tute_issuesString);
           
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
            Logger.getLogger(Attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
private void retrieveData(String query){
      
      cleanTable();
      
      Connect c=new Connect();
    
      ResultSet r=c.getQuery(query);
      try {
      while(r.next()){
          
           
            String barcode=r.getString("stu.barcode");
            String studentName=r.getString("stu.name_with_initials");
            String classCalendarDate=r.getString("cal.date");
            String courseName=r.getString("cour.name");
            String institute=r.getString("ins.name");
            String city=r.getString("ins.city");
            
            String attendanceString="X";
            String tute_issuesString="X";
           String tute_issues=r.getString("shcc.tutes_issued");
                
              if(tute_issues!=null){
                  
                if(tute_issues.equals("1")){
                tute_issuesString="Issued";
                attendanceString="Attended";

                }else if(tute_issues.equals("0")){
                    tute_issuesString="X";
                    attendanceString="Attended";
                }
            
            }
           
            addDataToTabel(barcode,studentName,courseName,institute,city,classCalendarDate,attendanceString,tute_issuesString);
            
             
           
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
            Logger.getLogger(Attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }


    private void search(){
        
        String name=nameTxt.getText();
        String barcode = barcodeTxt.getText();
        String institute =instituteTxt.getText();
        String city=cityTxt.getText();
        String course = courseTxt.getText();
        String classCalendarDate=dateTxt.getText();
        
        if(classCalendarDate.length()==1){
            classCalendarDate="0"+classCalendarDate;
            
        }
        
                      
           cleanTable();
       final String query="SELECT shcc.*,stu.barcode,stu.name_with_initials,shc.*,cour.name,ins.*,cal.* FROM ((((student stu INNER JOIN "
              + "student_has_course shc ON stu.id=shc.student_id) "
              + "INNER JOIN course cour ON cour.id=shc.course_id) INNER JOIN institution ins ON cour.institution_id=ins.id)"
              + " INNER JOIN class_calendar cal ON cal.course_id=cour.id AND STRCMP(cal.date,stu.register_date)>=0) "
               + "LEFT JOIN student_has_class_calendar shcc ON cal.id=shcc.class_calendar_id AND stu.id=shcc.student_id "
               + "WHERE (stu.name_with_initials LIKE '%"+name+"%' OR stu.fullname LIKE '%"+name+"%') "
                   + "AND stu.barcode LIKE '"+barcode+"%' AND cour.name LIKE '%"+course+"%' AND ins.name LIKE '%"+institute+"%'"
                + " AND ins.city LIKE '"+city+"%' AND cal.date LIKE '%"+classCalendarDate+"' AND date LIKE '"+getSelectedMonth()+"%'"
                + " AND stu.delete_status=0 AND cour.delete_status=0 AND ins.delete_status=0 ORDER BY cal.date DESC";
        
        
            retrieveData(query);
          
}

    private void changeButtonActivity(){
        
         markTutesOkBtn.setEnabled(true);
         markTutesNotIssuedBtn.setEnabled(true);
         boolean disableBothButtons=false;
         boolean tutesIssued=false;
         boolean tuteNotIssued=false;
         
        int selectedRowC = attendanceTbl.getSelectedRowCount();
        int[] selected_Rows = attendanceTbl.getSelectedRows();
        
        for (int i = 0; i < selectedRowC; i++) {
            String attendance = dtm.getValueAt(selected_Rows[i], 6).toString();
            String tutes_issues = dtm.getValueAt(selected_Rows[i], 7).toString();
            
           
            if(attendance.equals("Attended")&&tutes_issues.equals("Issued")){
               tutesIssued=true;
                
            }
             if(attendance.equals("Attended")&&tutes_issues.equals("X")){
               tuteNotIssued=true;
            }
            
             if(attendance.equals("X")){
                markTutesOkBtn.setEnabled(false);
                markTutesNotIssuedBtn.setEnabled(false);
                disableBothButtons=true;
                i=selectedRowC-1;
            }
            
        }
        if(!disableBothButtons){
            if(!tutesIssued && tuteNotIssued){
                markTutesOkBtn.setEnabled(true);
                markTutesNotIssuedBtn.setEnabled(false);
                
            }else if(tutesIssued && !tuteNotIssued){
                markTutesOkBtn.setEnabled(false);
                markTutesNotIssuedBtn.setEnabled(true);
                
            }
        }
       
}
    
    public void updateTuteIssuedStatus(int issuedStatus){
        
        
        for(int i=0;i<selectedRowCount;i++){
            
            Connect con=new Connect();
            
            String courseN=dtm.getValueAt(selectedRows[i], 2).toString();
            String institute=dtm.getValueAt(selectedRows[i], 3).toString();
            String city=dtm.getValueAt(selectedRows[i], 4).toString();
            String barcode =dtm.getValueAt(selectedRows[i], 0).toString();
            String date =dtm.getValueAt(selectedRows[i], 5).toString();
            int courseID = NewApplication.getInstance().getCourseID(courseN, institute, city);
            int stuID=0,ccID=0;
            
            String query="SELECT shcc.student_id,shcc.class_calendar_id FROM (class_calendar cc INNER JOIN "
                    + "student_has_class_calendar shcc ON cc.id=shcc."
                    + "class_calendar_id INNER JOIN student stu ON shcc.student_id=stu.id ) WHERE stu.barcode='"+barcode
                    + "' AND cc.date='"+date+"' AND cc.course_id="+courseID+"";
        
        
            ResultSet r=con.getQuery(query);
        
            try {
                while(r.next()){
                    
                    stuID=r.getInt("shcc.student_id");
                    ccID=r.getInt("shcc.class_calendar_id");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Attendance.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connect conn=new Connect();
            
            String updateQuery="UPDATE student_has_class_calendar SET tutes_issued="+issuedStatus+" WHERE student_id"
                    + "="+stuID+" AND class_calendar_id="+ccID;
            
            conn.setQuery(updateQuery);
                    
        }
        
        
         
    }
    
   
    
    private boolean allAreAttenders(){
        
        boolean attendance=true;
        for (int i = 0; i < selectedRowCount; i++) {
            String valueAt = dtm.getValueAt(selectedRows[i], 6).toString();
            
            if(valueAt.equals("X")){
                attendance=false;
                i=selectedRowCount-1;
            }
        }
            
        return attendance;
        
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
        yearLabel = new javax.swing.JLabel();
        monthLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        goToPreviousBtn = new javax.swing.JButton();
        goToBtn = new javax.swing.JButton();
        goToNextBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        homeBtn = new javax.swing.JButton();
        keyBoardBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        backBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        attendanceTbl = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        searchBtn = new javax.swing.JButton();
        removeSearchBtn = new javax.swing.JButton();
        markTutesNotIssuedBtn = new javax.swing.JButton();
        markTutesOkBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        barcodeTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        courseTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        instituteTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cityTxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        notificationLabel = new javax.swing.JLabel();
        dateTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(7, 1, 36));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(24, 25, 54));

        jLabel10.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 34)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Attendance");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/attendance-icon.png"))); // NOI18N

        yearLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 25)); // NOI18N
        yearLabel.setForeground(new java.awt.Color(153, 153, 153));
        yearLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        monthLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 25)); // NOI18N
        monthLabel.setForeground(new java.awt.Color(153, 153, 153));

        jPanel3.setBackground(new java.awt.Color(24, 25, 54));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        goToPreviousBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToPrevious.png"))); // NOI18N
        goToPreviousBtn.setToolTipText("goto previous month");
        goToPreviousBtn.setBorder(null);
        goToPreviousBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToPrevious-disable.png"))); // NOI18N
        goToPreviousBtn.setFocusPainted(false);
        goToPreviousBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToPrevious-selected.png"))); // NOI18N
        goToPreviousBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToPreviousBtnActionPerformed(evt);
            }
        });
        jPanel3.add(goToPreviousBtn);

        goToBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goto.png"))); // NOI18N
        goToBtn.setToolTipText("click goto any month");
        goToBtn.setBorder(null);
        goToBtn.setDisabledIcon(null);
        goToBtn.setFocusPainted(false);
        goToBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goto-selected.png"))); // NOI18N
        goToBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToBtnActionPerformed(evt);
            }
        });
        jPanel3.add(goToBtn);

        goToNextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToNext.png"))); // NOI18N
        goToNextBtn.setToolTipText("goto next month");
        goToNextBtn.setBorder(null);
        goToNextBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToNext-disable.png"))); // NOI18N
        goToNextBtn.setEnabled(false);
        goToNextBtn.setFocusPainted(false);
        goToNextBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToNext-selected.png"))); // NOI18N
        goToNextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToNextBtnActionPerformed(evt);
            }
        });
        jPanel3.add(goToNextBtn);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(yearLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(monthLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(24, 25, 54));

        jPanel8.setBackground(new java.awt.Color(24, 25, 54));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        homeBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        homeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        homeBtn.setBorder(null);
        homeBtn.setDisabledIcon(null);
        homeBtn.setFocusPainted(false);
        homeBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home-selected.png"))); // NOI18N
        homeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeBtnActionPerformed(evt);
            }
        });
        jPanel8.add(homeBtn);

        keyBoardBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        keyBoardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Keyboard.png"))); // NOI18N
        keyBoardBtn.setBorder(null);
        keyBoardBtn.setBorderPainted(false);
        keyBoardBtn.setDisabledIcon(null);
        keyBoardBtn.setFocusPainted(false);
        keyBoardBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Keyboard-selected.png"))); // NOI18N
        keyBoardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyBoardBtnActionPerformed(evt);
            }
        });
        jPanel8.add(keyBoardBtn);

        jPanel7.setBackground(new java.awt.Color(7, 1, 36));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        backBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/back.png"))); // NOI18N
        backBtn.setBorder(null);
        backBtn.setBorderPainted(false);
        backBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/disableIcon.png"))); // NOI18N
        backBtn.setEnabled(false);
        backBtn.setFocusPainted(false);
        backBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/back-selected.png"))); // NOI18N
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        jPanel7.add(backBtn);

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close.png"))); // NOI18N
        cancelBtn.setBorder(null);
        cancelBtn.setDisabledIcon(null);
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
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(7, 1, 36));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));

        attendanceTbl.setBackground(new java.awt.Color(7, 1, 36));
        attendanceTbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        attendanceTbl.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        attendanceTbl.setForeground(new java.awt.Color(102, 102, 102));
        attendanceTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Barcode", "Student Name", "Course Name", "Institution", "City", "Date", "Attendance", "Tute Issues"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        attendanceTbl.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        attendanceTbl.setFocusable(false);
        attendanceTbl.setGridColor(new java.awt.Color(24, 25, 54));
        attendanceTbl.setRowHeight(30);
        attendanceTbl.setSelectionBackground(new java.awt.Color(24, 25, 54));
        attendanceTbl.setSelectionForeground(new java.awt.Color(153, 153, 153));
        attendanceTbl.getTableHeader().setReorderingAllowed(false);
        attendanceTbl.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                attendanceTblMouseDragged(evt);
            }
        });
        attendanceTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attendanceTblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(attendanceTbl);
        if (attendanceTbl.getColumnModel().getColumnCount() > 0) {
            attendanceTbl.getColumnModel().getColumn(0).setResizable(false);
            attendanceTbl.getColumnModel().getColumn(0).setPreferredWidth(250);
            attendanceTbl.getColumnModel().getColumn(1).setResizable(false);
            attendanceTbl.getColumnModel().getColumn(1).setPreferredWidth(450);
            attendanceTbl.getColumnModel().getColumn(2).setResizable(false);
            attendanceTbl.getColumnModel().getColumn(2).setPreferredWidth(300);
            attendanceTbl.getColumnModel().getColumn(3).setResizable(false);
            attendanceTbl.getColumnModel().getColumn(3).setPreferredWidth(200);
            attendanceTbl.getColumnModel().getColumn(4).setResizable(false);
            attendanceTbl.getColumnModel().getColumn(4).setPreferredWidth(200);
            attendanceTbl.getColumnModel().getColumn(5).setResizable(false);
            attendanceTbl.getColumnModel().getColumn(5).setPreferredWidth(150);
            attendanceTbl.getColumnModel().getColumn(6).setResizable(false);
            attendanceTbl.getColumnModel().getColumn(6).setPreferredWidth(100);
            attendanceTbl.getColumnModel().getColumn(7).setResizable(false);
            attendanceTbl.getColumnModel().getColumn(7).setPreferredWidth(100);
        }

        jPanel6.setBackground(new java.awt.Color(7, 1, 36));
        jPanel6.setLayout(new java.awt.GridLayout(0, 1, 0, 3));

        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        searchBtn.setToolTipText("search");
        searchBtn.setBorder(null);
        searchBtn.setDisabledIcon(null);
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
        removeSearchBtn.setDisabledIcon(null);
        removeSearchBtn.setFocusPainted(false);
        removeSearchBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/removeSearch-selected.png"))); // NOI18N
        removeSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSearchBtnActionPerformed(evt);
            }
        });
        jPanel6.add(removeSearchBtn);

        markTutesNotIssuedBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/markTutesNotIssued.png"))); // NOI18N
        markTutesNotIssuedBtn.setBorder(null);
        markTutesNotIssuedBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/AttendanceManagement/markTutesNotIssued-disable.png"))); // NOI18N
        markTutesNotIssuedBtn.setFocusPainted(false);
        markTutesNotIssuedBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/markTutesNotIssued-selected.png"))); // NOI18N
        markTutesNotIssuedBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markTutesNotIssuedBtnActionPerformed(evt);
            }
        });
        jPanel6.add(markTutesNotIssuedBtn);

        markTutesOkBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/markTutesOk.png"))); // NOI18N
        markTutesOkBtn.setBorder(null);
        markTutesOkBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/markTutesOk-disable.png"))); // NOI18N
        markTutesOkBtn.setFocusPainted(false);
        markTutesOkBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/markTutesOk-selected.png"))); // NOI18N
        markTutesOkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markTutesOkBtnActionPerformed(evt);
            }
        });
        jPanel6.add(markTutesOkBtn);

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

        jLabel7.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Barcode");

        barcodeTxt.setBackground(new java.awt.Color(7, 1, 36));
        barcodeTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        barcodeTxt.setForeground(new java.awt.Color(102, 102, 102));
        barcodeTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        barcodeTxt.setCaretColor(new java.awt.Color(153, 153, 153));

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

        dateTxt.setBackground(new java.awt.Color(7, 1, 36));
        dateTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        dateTxt.setForeground(new java.awt.Color(102, 102, 102));
        dateTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        dateTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 946, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameTxt)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(barcodeTxt)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(courseTxt)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(instituteTxt)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cityTxt)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(dateTxt))
                        .addGap(0, 100, Short.MAX_VALUE))
                    .addComponent(notificationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 1276, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(barcodeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(courseTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(notificationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(59, 59, 59)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(433, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeBtnActionPerformed
       
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        GoTo.getInstance().dispose();
        ConfirmDialog.getInstance().dispose();
        this.dispose();
    }//GEN-LAST:event_homeBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        GoTo.getInstance().dispose();
        ConfirmDialog.getInstance().dispose();
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
       
        notificationLabel.setText("Searching.....");
        search();
    }//GEN-LAST:event_searchBtnActionPerformed

    private void removeSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSearchBtnActionPerformed
        clearText();
        search();
    }//GEN-LAST:event_removeSearchBtnActionPerformed

    private void keyBoardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyBoardBtnActionPerformed
        if(Keyboard.getInstance().isVisible()){
            Keyboard.getInstance().hideInterface();
        }
        else{
            Keyboard.getInstance().showInterface();

        }

    }//GEN-LAST:event_keyBoardBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed

        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        GoTo.getInstance().dispose();
        ConfirmDialog.getInstance().dispose();
        this.dispose();
    }//GEN-LAST:event_backBtnActionPerformed

    private void goToNextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToNextBtnActionPerformed
        
        notificationLabel.setText("Waiting.....");
        goToPreviousBtn.setEnabled(true);
        goTo(Attendance.NEXT,null,null);
        search();
    }//GEN-LAST:event_goToNextBtnActionPerformed

    private void goToPreviousBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToPreviousBtnActionPerformed
        
        notificationLabel.setText("Waiting.....");
        goToNextBtn.setEnabled(true);
        goTo(Attendance.PREVIOUS,null,null);
        search();
        
    }//GEN-LAST:event_goToPreviousBtnActionPerformed

    private void goToBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToBtnActionPerformed
        
        
        GoTo.getInstance().showInterface(GoTo.ATTENDANCE);
        
    }//GEN-LAST:event_goToBtnActionPerformed

    private void markTutesOkBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markTutesOkBtnActionPerformed
        
        selectedRowCount = attendanceTbl.getSelectedRowCount();
        selectedRows = attendanceTbl.getSelectedRows();
        int rowCount = dtm.getRowCount();
        
        if(rowCount==0){
            
        }else if(selectedRowCount==0){
            Message.getInstance().showInterface("Select to change tutes ","were issued");
            
        }else if(!allAreAttenders()){
            WarningMessage.getInstance().showInterface("you can edit only attenders");
        }else{
            
        
            if(selectedRowCount==1){
              ConfirmDialog.getInstance().showInterface("Are you sure you want to change the selected student", "was issued the tutes ?", ConfirmDialog.TUTE_ISSUES,Attendance.TUTE_ISSUED);  
            }else if(selectedRowCount>1){
              ConfirmDialog.getInstance().showInterface("Are you sure you want to change the "+selectedRowCount+" students", "you selected were issued the tutes ?", ConfirmDialog.TUTE_ISSUES,Attendance.TUTE_ISSUED);   
            }
        
               
        }
        
    }//GEN-LAST:event_markTutesOkBtnActionPerformed

    private void markTutesNotIssuedBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markTutesNotIssuedBtnActionPerformed
        
        selectedRowCount = attendanceTbl.getSelectedRowCount();
        selectedRows = attendanceTbl.getSelectedRows();
        int rowCount = dtm.getRowCount();
        
        if(rowCount==0){
            
        }else if(selectedRowCount==0){
            Message.getInstance().showInterface("Select to change tutes","were not issued");
            
        }else if(!allAreAttenders()){
            WarningMessage.getInstance().showInterface("you can edit only attenders");
        }else{
            
        
            if(selectedRowCount==1){
              ConfirmDialog.getInstance().showInterface("Are you sure you want to change the selected student", "was not issued the tutes ?", ConfirmDialog.TUTE_ISSUES,Attendance.TUTE_NOT_ISSUED);  
            }else if(selectedRowCount>1){
              ConfirmDialog.getInstance().showInterface("Are you sure you want to change the "+selectedRowCount+" students", "you selected were not issued the tutes ?", ConfirmDialog.TUTE_ISSUES,Attendance.TUTE_NOT_ISSUED);   
            }
        
        }
    }//GEN-LAST:event_markTutesNotIssuedBtnActionPerformed

    private void attendanceTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendanceTblMouseClicked
       changeButtonActivity();
    }//GEN-LAST:event_attendanceTblMouseClicked

    private void attendanceTblMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendanceTblMouseDragged
        changeButtonActivity();
    }//GEN-LAST:event_attendanceTblMouseDragged

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        
    }//GEN-LAST:event_jPanel1MouseClicked

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
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Attendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Attendance().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable attendanceTbl;
    private javax.swing.JButton backBtn;
    private javax.swing.JTextField barcodeTxt;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField cityTxt;
    private javax.swing.JTextField courseTxt;
    private javax.swing.JTextField dateTxt;
    private javax.swing.JButton goToBtn;
    private javax.swing.JButton goToNextBtn;
    private javax.swing.JButton goToPreviousBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JTextField instituteTxt;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton keyBoardBtn;
    private javax.swing.JButton markTutesNotIssuedBtn;
    private javax.swing.JButton markTutesOkBtn;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JLabel notificationLabel;
    private javax.swing.JButton removeSearchBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables
}
