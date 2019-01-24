/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import AttendanceManagement.Attendance;
import coursesmanagement.ClassCalendar;
import coursesmanagement.CourseManager;
import coursesmanagement.Institute;
import coursesmanagement.NewCourse;
import coursesmanagement.PaymentManager;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import studentmanagement.CardMarkingStartupScreen;
import studentmanagement.NewApplication;
import studentmanagement.StudentRegistry;
import usermanagement.Verification;

/**
 *
 * @author Nadun
 */
public class DashBord extends javax.swing.JFrame {

   
   
    private static DashBord instance=new DashBord();
    private String userName;
    
    public DashBord() {
        initComponents();
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        this.setSize(screenSize);

        runDateAndTime();
        setLoginTime();
        new DragJFrame(this, jPanel9);
    }
    
    private void runDateAndTime(){
        new Thread(){
        @Override
        public void run(){
            while(true){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            String currentDate=sdf.format(date.getTime());
            
            Calendar cal=new GregorianCalendar();    
                
                int hour=cal.get(Calendar.HOUR);
                int m=cal.get(Calendar.MINUTE);
                int AM_PM=cal.get(Calendar.AM_PM);
                String stringM=Integer.toString(m);
                int day=cal.get(Calendar.DAY_OF_WEEK);
                String stringDay=null;
                String am_pm=null;
                
                if(AM_PM==1){
                  am_pm="PM"; 
                }else{
                  am_pm="AM";
                }
                if(m<10){
                  stringM="0"+m;  
                }
                
                if(day==1){
                   stringDay=" Sunday"; 
                }else if(day==2){
                   stringDay=" Monday";  
                }else if(day==3){
                   stringDay=" Tuesday";  
                }else if(day==4){
                   stringDay=" Wednesday";  
                }else if(day==5){
                   stringDay=" Thursday";  
                }else if(day==6){
                    stringDay=" Friday"; 
                }else if(day==7){
                    stringDay=" Saturday"; 
                }
                
                if(hour==0){
                   hour=12;
                }
                
                clock.setText(hour+" : "+stringM+"  "+am_pm);
                dateLabel1.setText(currentDate);
                dayLabel.setText(stringDay);
            }
        }
        }.start();
    }
    
    
    public static DashBord getInstance(){
        return instance;
        
    }
    
    public void showInterface(){
        instance.setVisible(true);
    }
    
    private void setLoginTime(){
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            String currentDate=sdf.format(date.getTime());
            
            Calendar cal=new GregorianCalendar();    
                
                int hour=cal.get(Calendar.HOUR);
                int m=cal.get(Calendar.MINUTE);
                int AM_PM=cal.get(Calendar.AM_PM);
                String stringM=Integer.toString(m);


                String am_pm=null;
                
                if(AM_PM==1){
                  am_pm="PM"; 
                }else{
                  am_pm="AM";
                }
                if(m<10){
                  stringM="0"+m;  
                }
                
                
                if(hour==0){
                   hour=12;
                }
                
        loginTimeTxt.setText("at  "+hour+" : "+stringM+" "+am_pm+"  "+currentDate);
        
    }
    
    public void showInterface(String userN){
        userName=userN;
        userTxt.setText(userName);
        instance.setVisible(true);
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
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        newStudentBtn = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        courseManagerBtn = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        studentRegistryBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        cardMarkerBtn = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        AttendanceBtn = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        newCourseBtn = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        clock = new javax.swing.JLabel();
        dateLabel1 = new javax.swing.JLabel();
        dayLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        loginTimeTxt = new javax.swing.JLabel();
        label4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        userTxt = new javax.swing.JLabel();
        changeUserBtn = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        minimizeBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(7, 1, 36));
        jPanel1.setMinimumSize(new java.awt.Dimension(1348, 689));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));

        jPanel2.setBackground(new java.awt.Color(7, 1, 36));
        jPanel2.setLayout(new java.awt.GridLayout(6, 0));

        jPanel19.setBackground(new java.awt.Color(7, 1, 36));
        jPanel19.setLayout(new java.awt.GridLayout(0, 3, 80, 0));

        jPanel20.setBackground(new java.awt.Color(7, 1, 36));
        jPanel20.setOpaque(false);
        jPanel20.setLayout(new java.awt.GridLayout(1, 0, 12, 0));

        newStudentBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        newStudentBtn.setForeground(new java.awt.Color(255, 255, 255));
        newStudentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/newStudent.png"))); // NOI18N
        newStudentBtn.setBorder(null);
        newStudentBtn.setFocusPainted(false);
        newStudentBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/newStudent-selected.png"))); // NOI18N
        newStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newStudentBtnActionPerformed(evt);
            }
        });
        jPanel20.add(newStudentBtn);

        jPanel19.add(jPanel20);

        jPanel21.setBackground(new java.awt.Color(7, 1, 36));
        jPanel21.setOpaque(false);
        jPanel21.setLayout(new java.awt.GridLayout(1, 0, 12, 0));

        courseManagerBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        courseManagerBtn.setForeground(new java.awt.Color(255, 255, 255));
        courseManagerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/coursesList.png"))); // NOI18N
        courseManagerBtn.setBorder(null);
        courseManagerBtn.setFocusPainted(false);
        courseManagerBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/coursesList-selecetd.png"))); // NOI18N
        courseManagerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseManagerBtnActionPerformed(evt);
            }
        });
        jPanel21.add(courseManagerBtn);

        jPanel19.add(jPanel21);

        jPanel22.setBackground(new java.awt.Color(7, 1, 36));
        jPanel22.setOpaque(false);
        jPanel22.setLayout(new java.awt.GridLayout(1, 0, 12, 0));

        studentRegistryBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        studentRegistryBtn.setForeground(new java.awt.Color(255, 255, 255));
        studentRegistryBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/studentRegistry.png"))); // NOI18N
        studentRegistryBtn.setBorder(null);
        studentRegistryBtn.setFocusPainted(false);
        studentRegistryBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/studentRegistry-selected.png"))); // NOI18N
        studentRegistryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentRegistryBtnActionPerformed(evt);
            }
        });
        jPanel22.add(studentRegistryBtn);

        jPanel19.add(jPanel22);

        jPanel2.add(jPanel19);

        jPanel5.setBackground(new java.awt.Color(7, 1, 36));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0, 80, 0));

        jLabel2.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("New Student");
        jPanel5.add(jLabel2);

        jLabel8.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Courses List");
        jPanel5.add(jLabel8);

        jLabel9.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Student Registry");
        jPanel5.add(jLabel9);

        jPanel2.add(jPanel5);

        jPanel3.setBackground(new java.awt.Color(7, 1, 36));
        jPanel3.setLayout(new java.awt.GridLayout(0, 3, 80, 0));

        jPanel8.setBackground(new java.awt.Color(7, 1, 36));
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridLayout(1, 0, 12, 0));

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/paymentDetails.png"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.setFocusPainted(false);
        jButton4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/paymentDetails-selected.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton4);

        jPanel3.add(jPanel8);

        jPanel13.setBackground(new java.awt.Color(7, 1, 36));
        jPanel13.setOpaque(false);
        jPanel13.setLayout(new java.awt.GridLayout(1, 0, 12, 0));

        cardMarkerBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cardMarkerBtn.setForeground(new java.awt.Color(255, 255, 255));
        cardMarkerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cardmarker.png"))); // NOI18N
        cardMarkerBtn.setBorder(null);
        cardMarkerBtn.setFocusPainted(false);
        cardMarkerBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cardmarker-selected.png"))); // NOI18N
        cardMarkerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardMarkerBtnActionPerformed(evt);
            }
        });
        jPanel13.add(cardMarkerBtn);

        jPanel3.add(jPanel13);

        jPanel14.setBackground(new java.awt.Color(7, 1, 36));
        jPanel14.setOpaque(false);
        jPanel14.setLayout(new java.awt.GridLayout(1, 0, 12, 0));

        AttendanceBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        AttendanceBtn.setForeground(new java.awt.Color(255, 255, 255));
        AttendanceBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/attendance.png"))); // NOI18N
        AttendanceBtn.setBorder(null);
        AttendanceBtn.setFocusPainted(false);
        AttendanceBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/attendance-selected.png"))); // NOI18N
        AttendanceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttendanceBtnActionPerformed(evt);
            }
        });
        jPanel14.add(AttendanceBtn);

        jPanel3.add(jPanel14);

        jPanel2.add(jPanel3);

        jPanel23.setBackground(new java.awt.Color(7, 1, 36));
        jPanel23.setLayout(new java.awt.GridLayout(1, 0, 80, 0));

        jLabel1.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Payment Details");
        jPanel23.add(jLabel1);

        jLabel7.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Card Marker");
        jPanel23.add(jLabel7);

        jLabel3.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Attendance");
        jPanel23.add(jLabel3);

        jPanel2.add(jPanel23);

        jPanel15.setBackground(new java.awt.Color(7, 1, 36));
        jPanel15.setLayout(new java.awt.GridLayout(0, 3, 80, 0));

        jPanel17.setBackground(new java.awt.Color(7, 1, 36));
        jPanel17.setOpaque(false);
        jPanel17.setLayout(new java.awt.GridLayout(1, 0, 12, 0));

        newCourseBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        newCourseBtn.setForeground(new java.awt.Color(255, 255, 255));
        newCourseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/newCourse.png"))); // NOI18N
        newCourseBtn.setBorder(null);
        newCourseBtn.setFocusPainted(false);
        newCourseBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/newCourse-selected.png"))); // NOI18N
        newCourseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCourseBtnActionPerformed(evt);
            }
        });
        jPanel17.add(newCourseBtn);

        jPanel15.add(jPanel17);

        jPanel18.setBackground(new java.awt.Color(7, 1, 36));
        jPanel18.setOpaque(false);
        jPanel18.setLayout(new java.awt.GridLayout(1, 0, 12, 0));

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/institute.png"))); // NOI18N
        jButton9.setBorder(null);
        jButton9.setFocusPainted(false);
        jButton9.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/institute-selected.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel18.add(jButton9);

        jPanel15.add(jPanel18);

        jPanel24.setBackground(new java.awt.Color(7, 1, 36));
        jPanel24.setOpaque(false);
        jPanel24.setLayout(new java.awt.GridLayout(1, 0, 12, 0));

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/classCalendar.png"))); // NOI18N
        jButton8.setBorder(null);
        jButton8.setFocusPainted(false);
        jButton8.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/classCalendar-selected.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton8);

        jPanel15.add(jPanel24);

        jPanel2.add(jPanel15);

        jPanel4.setBackground(new java.awt.Color(7, 1, 36));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 80, 0));

        jLabel5.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("New Course");
        jPanel4.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Institute");
        jPanel4.add(jLabel6);

        jLabel4.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Class Calendar ");
        jPanel4.add(jLabel4);

        jPanel2.add(jPanel4);

        jDesktopPane1.setOpaque(false);

        jPanel6.setBackground(new java.awt.Color(24, 25, 54));

        jLabel11.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 50)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(67, 60, 99));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("TUTION");

        clock.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 43)); // NOI18N
        clock.setForeground(new java.awt.Color(67, 60, 99));
        clock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(67, 60, 99)));

        dateLabel1.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 28)); // NOI18N
        dateLabel1.setForeground(new java.awt.Color(67, 60, 99));
        dateLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        dayLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 28)); // NOI18N
        dayLabel.setForeground(new java.awt.Color(67, 60, 99));
        dayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel15.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 50)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(67, 60, 99));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("MASTER");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clock, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(dayLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                                .addComponent(dateLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(clock, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jDesktopPane1.add(jPanel6);
        jPanel6.setBounds(50, 50, 350, 450);

        loginTimeTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 20)); // NOI18N
        loginTimeTxt.setForeground(new java.awt.Color(153, 153, 153));
        loginTimeTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jDesktopPane1.add(loginTimeTxt);
        loginTimeTxt.setBounds(40, 570, 350, 30);

        label4.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 26)); // NOI18N
        label4.setForeground(new java.awt.Color(153, 153, 153));
        label4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label4.setText("Login :");
        jDesktopPane1.add(label4);
        label4.setBounds(60, 520, 140, 50);

        jLabel12.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setText("Change User");
        jDesktopPane1.add(jLabel12);
        jLabel12.setBounds(100, 660, 160, 19);

        userTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 20)); // NOI18N
        userTxt.setForeground(new java.awt.Color(153, 153, 153));
        userTxt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jDesktopPane1.add(userTxt);
        userTxt.setBounds(210, 530, 180, 30);

        changeUserBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 14)); // NOI18N
        changeUserBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/changePassword.png"))); // NOI18N
        changeUserBtn.setToolTipText("click to change user");
        changeUserBtn.setBorder(null);
        changeUserBtn.setFocusPainted(false);
        changeUserBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/changePassword-selected.png"))); // NOI18N
        changeUserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeUserBtnActionPerformed(evt);
            }
        });
        jDesktopPane1.add(changeUserBtn);
        changeUserBtn.setBounds(40, 637, 50, 50);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Bookmark.png"))); // NOI18N
        jLabel14.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel14.setOpaque(true);
        jDesktopPane1.add(jLabel14);
        jLabel14.setBounds(10, 0, 510, 700);

        jPanel9.setBackground(new java.awt.Color(24, 25, 54));

        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        minimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Minimize.png"))); // NOI18N
        minimizeBtn.setBorder(null);
        minimizeBtn.setFocusPainted(false);
        minimizeBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Minimize-selected.png"))); // NOI18N
        minimizeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minimizeBtnActionPerformed(evt);
            }
        });
        jPanel7.add(minimizeBtn);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Exit.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setFocusPainted(false);
        jButton1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Exit-selected.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1);

        jLabel13.setFont(new java.awt.Font("Segoe Print", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 113));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("TUTION MANAGEMENT SYSTEM");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void minimizeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizeBtnActionPerformed
        
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizeBtnActionPerformed

    private void AttendanceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttendanceBtnActionPerformed

        Attendance.getInstance().showInterface();
    }//GEN-LAST:event_AttendanceBtnActionPerformed

    private void cardMarkerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardMarkerBtnActionPerformed

        CardMarkingStartupScreen.getInstance().showInterface();
    }//GEN-LAST:event_cardMarkerBtnActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        PaymentManager.getInstance().showInterface();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void studentRegistryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentRegistryBtnActionPerformed
        StudentRegistry.getInstance().showInterface();
    }//GEN-LAST:event_studentRegistryBtnActionPerformed

    private void courseManagerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseManagerBtnActionPerformed
        CourseManager.getInstance().showInterface();
    }//GEN-LAST:event_courseManagerBtnActionPerformed

    private void newStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newStudentBtnActionPerformed

        NewApplication.getInstance().showInterface();
    }//GEN-LAST:event_newStudentBtnActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        Institute.getInstance().showInterface();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void newCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCourseBtnActionPerformed
        NewCourse.getInstance().showInterface();
    }//GEN-LAST:event_newCourseBtnActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        ClassCalendar.getInstance().showInterface();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void changeUserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeUserBtnActionPerformed
        Verification.getInstance().showInterface(Verification.USER_CHANGE);
    }//GEN-LAST:event_changeUserBtnActionPerformed

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
            java.util.logging.Logger.getLogger(DashBord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashBord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashBord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashBord.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DashBord().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AttendanceBtn;
    private javax.swing.JButton cardMarkerBtn;
    private javax.swing.JButton changeUserBtn;
    private javax.swing.JLabel clock;
    private javax.swing.JButton courseManagerBtn;
    private javax.swing.JLabel dateLabel1;
    private javax.swing.JLabel dayLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel loginTimeTxt;
    private javax.swing.JButton minimizeBtn;
    private javax.swing.JButton newCourseBtn;
    private javax.swing.JButton newStudentBtn;
    private javax.swing.JButton studentRegistryBtn;
    private javax.swing.JLabel userTxt;
    // End of variables declaration//GEN-END:variables
}
