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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.DragJFrame;
import main.Keyboard;
import notifications.Message;
import notifications.SavedStatusNotice;
import notifications.WarningMessage;

/**
 *
 * @author Nadun
 */
public class CardMarker extends javax.swing.JFrame {

    /**
     * Creates new form CardMarker
     */
    
    private String currentCourseID;
    private String paymentDate;
    private String studentName, currentBarcode, fullname, address, dob, email, nic, cardType, registerDate, guardianPhoneNo, guardianName, mobile, tele;
    private boolean payment;
    private int currentStudentCount, nowWhere;
    private static final int NEXT=1,PREVIOUS=2,DEFAULT_DATA=1,NEW_DATA=2;
    private static CardMarker instance=new CardMarker();
    private int stu_ID ,classCalendarID;
       
    public CardMarker() {
        initComponents();
//         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        this.setSize(screenSize);
        DragJFrame df=new DragJFrame(this, jPanel2);
    }
    
    public static CardMarker getInstance(){
        return instance;  
    }
    
    public void showInterface(String courseID,String name,String institute,String city){
        this.currentCourseID=courseID;
        courseLabel.setText(name);
        instituteLabel.setText(institute);
        cityLabel.setText(city);
        if(!hasClassCalendar(getCurrentDate(), courseID)){
        
        this.classCalendarID=markCourseOnClassCalendar(courseID);
        }
        retrieveData(CardMarker.DEFAULT_DATA);
        updateCurrentStudentAttendance();
        
        if(currentStudentCount==0){
           previousStudentBtn.setEnabled(false);
        }else{
           previousStudentBtn.setEnabled(true);
        }
        instance.setVisible(true);
        barcodeTxt.requestFocus();
        
    }
    
    private String formatValue(double d){
            DecimalFormat df=new DecimalFormat("#0.00");
        return df.format(d);   
    }
    
    private boolean isAnyCardMarked(){
        boolean wheatherMarked=false;
        String query="SELECT student_id FROM student_has_class_calendar WHERE class_calendar_id="+classCalendarID;
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
                wheatherMarked=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return wheatherMarked;
        
    }
    
    private double getCourseFee(String courseID){
        double fee=0;
        String query="SELECT course_fee FROM course WHERE id="+courseID;
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
                fee= r.getDouble("course_fee");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fee;
        
    }
    
    private boolean isIssuedBarcode(){
        boolean wheatherIssued=false;
        String query="SELECT id FROM student WHERE barcode='"+currentBarcode+"' AND delete_status=0";
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
                int id = r.getInt("id");
                stu_ID=id;
                wheatherIssued=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return wheatherIssued;
        
    }
    
   
    private boolean isRegisteredForCurrentCourse(){
        boolean wheatherRegistered=false;
        String query="SELECT course_id FROM student_has_course WHERE student_id="+stu_ID+" AND course_id="+currentCourseID;
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            
                wheatherRegistered=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return wheatherRegistered;
        
    }
    
    private boolean isPreviouslyCheacked(){
        boolean wheatherCheaked=false;
        String query="SELECT student_id FROM student_has_class_calendar WHERE class_calendar_id="+classCalendarID+" AND "
                + "student_id="+stu_ID;
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            
                wheatherCheaked=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return wheatherCheaked;
        
    }
    
    private boolean isPaidForThisMonth(){
        boolean wheatherPaid=false;
        String query="SELECT pay.id,cal.id,cal.date FROM payment pay INNER JOIN class_calendar cal"
                + " ON cal.id=pay.class_calendar_id WHERE pay.student_id="+stu_ID
                + " AND cal.date LIKE '"+getCurrentMonth()+"%' AND cal.course_id="+currentCourseID;
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
                wheatherPaid=true;
                paymentDate=r.getString("cal.date");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return wheatherPaid;
    }
    
    private int getLastDayAttendance(){
        int attendance=2; //if today is the firstday of the class
        String query="SELECT id FROM class_calendar WHERE course_id="+currentCourseID
                + " AND date NOT LIKE '"+getCurrentDate()+"' ORDER BY date DESC LIMIT 1";
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
               int lastDayClassCalendar_id=r.getInt("id");
        String query1="SELECT student_id FROM student_has_class_calendar WHERE class_calendar_id="
                +lastDayClassCalendar_id+" AND student_id="+stu_ID;
                attendance=0;
        Connect con=new Connect();
        ResultSet reset=con.getQuery(query1);
        try {
            while(reset.next()){
                attendance=1;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return attendance;
    }
    
    
    private void addData(String name,String address,String nic,int cardType,int attendance,boolean payment){
        String card_Type=null;
        String cardTypeMessage=null;
        String fee=null;
        
        if(cardType==1){
            card_Type="Full Card";
            cardTypeMessage="Full Card Student";
            fee=formatValue(getCourseFee(currentCourseID));
            
        }else if(cardType==2){
            card_Type="Half Card";
            cardTypeMessage="Half Card Student";
            fee=formatValue(getCourseFee(currentCourseID)/2);
            
        }else if(cardType==3){
            card_Type="Free Card";
            cardTypeMessage="Free Card Student";
        }
        
        nameLabel.setText(name);
        addressLabel.setText(address);
        nicLabel.setText(nic);
        cardTypeLabel.setText(card_Type);
        cardTypeNotice.setText(cardTypeMessage);
        barcodeTxt.setText(currentBarcode);
        tuteIssueBtn.setEnabled(true);
       
        
        boolean tute_issued=false;
        String query1="SELECT tutes_issued FROM student_has_class_calendar WHERE class_calendar_id="+classCalendarID
                + " AND student_id="+stu_ID+" AND tutes_issued=1";
        Connect c=new Connect();
        ResultSet r=c.getQuery(query1);
        try {
            while(r.next()){
                tute_issued=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarkingStartupScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(tute_issued){
        tuteIssueBtn.setSelected(true);
        }else{
        tuteIssueBtn.setSelected(false);
        }
        courseFeeNotice.setText(fee);
        
        
        if(attendance==0){
            attendanceLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/false.png")));
        }else if(attendance==1){
            attendanceLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/true.png")));
        }else if(attendance==2){
            attendanceLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/noIcon.png")));
        }
        
        if(payment){
            paymentMarkBtn.setSelected(true);
            int compareTo = paymentDate.compareTo(getCurrentDate());
            if(compareTo==0){
            paymentMarkBtn.setEnabled(true);
            }else{
            paymentMarkBtn.setEnabled(false);    
            }
        }else{
            if(cardType==3){
               paymentMarkBtn.setSelected(false);
               paymentMarkBtn.setEnabled(false); 
               
            }else{
               
               paymentMarkBtn.setSelected(false);
               paymentMarkBtn.setEnabled(true); 
               
            }
            
        }
        
    }
    
    public void retrieveData(int what){
        
        switch(what){
        
        case 1:
            nameLabel.setText("");
            addressLabel.setText("");
            cardTypeLabel.setText("");
            nicLabel.setText("");
            barcodeTxt.setText("");
            barcodeTxt.setEditable(true);
            barcodeTxt.setFocusable(true);
            cardTypeNotice.setText("");
            courseFeeNotice.setText("");
            clearBtn.setEnabled(true);
            
            paymentMarkBtn.setSelected(false);
            paymentMarkBtn.setEnabled(false);
            attendanceLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/noIcon.png")));
           
            tuteIssueBtn.setEnabled(false);
        
        break;
            
         case 2:
            
            
            String query="SELECT stu.*,cardtyp.*,gu.*,pn.* FROM ((student stu LEFT JOIN card_type cardtyp ON stu.card_type_id=cardtyp.id)"
                  + " LEFT JOIN guardian gu ON gu.student_id=stu.id) LEFT JOIN phone_number pn ON pn.student_id=stu.id"
                           + " WHERE stu.delete_status=0 AND stu.barcode='"+currentBarcode+"'";

            Connect c=new Connect();
            ResultSet r=c.getQuery(query);
            try {
                while(r.next()){
                stu_ID=r.getInt("stu.id");
                studentName=r.getString("stu.name_with_initials");
                fullname=r.getString("stu.fullname");
                address=r.getString("stu.address");
                dob=r.getString("stu.dob");
                email=r.getString("stu.email");
                nic=r.getString("stu.nic");
                cardType=r.getString("cardtyp.name");
                registerDate=r.getString("stu.register_date");
                guardianName=r.getString("gu.name");
                guardianPhoneNo=r.getString("gu.number");

                int cardTypeID=r.getInt("card_type_id");

                if(guardianPhoneNo.length()==9){
                    guardianPhoneNo="0"+guardianPhoneNo;
                }
                mobile=r.getString("pn.mobile_number");
                if(mobile.length()==9){
                    mobile="0"+mobile;
                }
                tele=r.getString("pn.telephone_number");

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

                    payment=isPaidForThisMonth();
                    int attendance=getLastDayAttendance();

                    addData(studentName, address, nic, cardTypeID, attendance, payment);
                    barcodeTxt.setEditable(false);
                    clearBtn.setEnabled(false);

                }
            } catch (SQLException ex) {
                Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        }
        
    }
   
    private int markCourseOnClassCalendar(String courseID){
        String query="INSERT INTO class_calendar(date,course_id) VALUES("
                +"'"+getCurrentDate()+"'"+","
                +courseID
                + ")";
        Connect c=new Connect();
        int genID=c.setQuery(query,null);
        
        return genID;
    }
    
    
    private String getCurrentDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        
        return sdf.format(date.getTime());
        
    }
    
    private String getCurrentMonth(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        Date date=new Date();
        
        return sdf.format(date.getTime());
        
    }
    
    private void insertData(){
       
        
        int tutes_issued=0;
        if(tuteIssueBtn.isSelected()){
            tutes_issued=1;
        }else{
            tutes_issued=0;
        }
        
        String query="INSERT INTO student_has_class_calendar(tutes_issued,class_calendar_id,student_id,marking_time) VALUES("
                + tutes_issued+","
                +classCalendarID+","
                + stu_ID+","
                + "CURRENT_TIME)";
        Connect c=new Connect();
        c.setQuery(query, null);

    }
    
    private void makePayment(){
        
        if(!cardTypeLabel.getText().equals("Free card") && paymentMarkBtn.isEnabled() && paymentMarkBtn.isSelected()){
       
        String query="INSERT INTO payment(amount,student_id,class_calendar_id) VALUES("
                +courseFeeNotice.getText()+","
                +stu_ID+","
                +classCalendarID
                + ")";
        Connect c=new Connect();
        c.setQuery(query, null);
        }
    
    }
    
    private void cancelPayment(){
        
        String query="DELETE FROM payment WHERE class_calendar_id="+classCalendarID+" AND student_id="+stu_ID;
        Connect c=new Connect();
        c.setQuery(query, null);
        
    
    }
    
    private void update(){
        
         Connect c=new Connect();
         int tutes_issued=0;
        if(tuteIssueBtn.isSelected()){
            tutes_issued=1;
        }else{
            tutes_issued=0;
        }
        
        String query="UPDATE student_has_class_calendar SET tutes_issued='"+tutes_issued+"' WHERE class_calendar_id="+classCalendarID
                +" AND student_id="+stu_ID;
                
        c.setQuery(query, null);
        
        
        if(!payment){
            
            makePayment();
        }else{
            if(!paymentMarkBtn.isSelected()){
               cancelPayment();
            }
            
        }
        
      
    }
    
    private boolean hasClassCalendar(String date,String courseID){
        boolean wheatherHas=false;
        String query="SELECT id FROM class_calendar WHERE date='"+date+"' AND course_id="+courseID;
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        
        try {
            while (r.next()) {
                wheatherHas=true;
                this.classCalendarID=r.getInt("id");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wheatherHas;
        
    }
    private void updateCurrentStudentAttendance(){
        int studentCount=0;
        String query="SELECT student_id FROM student_has_class_calendar WHERE class_calendar_id="+classCalendarID;
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        
        try {
            while (r.next()) {
               studentCount++;
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentStudentCount=studentCount;
        nowWhere=studentCount;
        currentStuCount.setText(""+studentCount);
        
    }
    
    private void removeOptionalClassCalendar(){
        if(!isAnyCardMarked()){
            
            String query="DELETE FROM class_calendar WHERE id="+classCalendarID;
            Connect c=new Connect();
            c.setQuery(query, null);
           
        }
    }
    
    private void goTo(int where){
        
        switch(where){
            case 1:
              nowWhere=++nowWhere;
                if(nowWhere==1){
                  previousStudentBtn.setEnabled(true);
                }
                break;
            case 2:
              nowWhere=--nowWhere;
                if(nowWhere==0){
                  previousStudentBtn.setEnabled(false);
                }
        
        }
        
        String query="SELECT shcc.student_id,stu.barcode,shcc.marking_time FROM student_has_class_calendar shcc INNER"
                + " JOIN student stu ON stu.id=shcc.student_id WHERE shcc.class_calendar_id="+classCalendarID+" ORDER "
                + "BY marking_time ASC LIMIT "+nowWhere+",1";
        Connect con=new Connect();
        ResultSet r=con.getQuery(query);
        try {
            while(r.next()){
            
                stu_ID=r.getInt("shcc.student_id");
                currentBarcode=r.getString("stu.barcode");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CardMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        retrieveData(CardMarker.NEW_DATA);
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
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        keyBoardBtn = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        backBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        courseLabel = new javax.swing.JLabel();
        instituteLabel = new javax.swing.JLabel();
        cityLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        nextStudentBtn = new javax.swing.JButton();
        previousStudentBtn = new javax.swing.JButton();
        viewDetailsBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        currentStuCount = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        paymentMarkBtn = new javax.swing.JToggleButton();
        tuteIssueBtn = new javax.swing.JToggleButton();
        attendanceLabel = new javax.swing.JLabel();
        cardTypeNotice = new javax.swing.JLabel();
        courseFeeNotice = new javax.swing.JLabel();
        courseFeeNotice1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        nicLabel = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        clearBtn = new javax.swing.JButton();
        barcodeTxt = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        cardTypeLabel = new javax.swing.JLabel();
        goBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(7, 1, 36));
        jPanel1.setMinimumSize(new java.awt.Dimension(1366, 748));
        jPanel1.setNextFocusableComponent(barcodeTxt);
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(24, 25, 54));
        jPanel4.setPreferredSize(new java.awt.Dimension(216, 46));

        jPanel8.setBackground(new java.awt.Color(24, 25, 54));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        jButton5.setFocusPainted(false);
        jButton5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home-selected.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton5);

        jPanel10.setBackground(new java.awt.Color(7, 1, 36));
        jPanel10.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

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
        jPanel10.add(keyBoardBtn);

        jPanel8.add(jPanel10);

        jPanel9.setBackground(new java.awt.Color(7, 1, 36));
        jPanel9.setPreferredSize(new java.awt.Dimension(140, 46));
        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        backBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/back.png"))); // NOI18N
        backBtn.setBorder(null);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/back-selected.png"))); // NOI18N
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        jPanel9.add(backBtn);

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close.png"))); // NOI18N
        cancelBtn.setBorder(null);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close-selected.png"))); // NOI18N
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        jPanel9.add(cancelBtn);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(24, 25, 54));

        jLabel10.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 33)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Card Marking");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cardmarker-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        courseLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        courseLabel.setForeground(new java.awt.Color(102, 102, 102));
        courseLabel.setText("Course");
        courseLabel.setNextFocusableComponent(barcodeTxt);

        instituteLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        instituteLabel.setForeground(new java.awt.Color(102, 102, 102));
        instituteLabel.setText("Institute");
        instituteLabel.setNextFocusableComponent(barcodeTxt);

        cityLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        cityLabel.setForeground(new java.awt.Color(102, 102, 102));
        cityLabel.setText("City / Town");
        cityLabel.setNextFocusableComponent(barcodeTxt);

        jPanel6.setBackground(new java.awt.Color(7, 1, 36));
        jPanel6.setLayout(new java.awt.GridLayout(0, 1, 0, 3));

        nextStudentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/nextStudent.png"))); // NOI18N
        nextStudentBtn.setToolTipText("next student");
        nextStudentBtn.setBorder(null);
        nextStudentBtn.setFocusPainted(false);
        nextStudentBtn.setNextFocusableComponent(barcodeTxt);
        nextStudentBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/nextStudent-selected.png"))); // NOI18N
        nextStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextStudentBtnActionPerformed(evt);
            }
        });
        jPanel6.add(nextStudentBtn);

        previousStudentBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/previousStudent.png"))); // NOI18N
        previousStudentBtn.setToolTipText("previous student");
        previousStudentBtn.setBorder(null);
        previousStudentBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/previousStudent-disable.png"))); // NOI18N
        previousStudentBtn.setEnabled(false);
        previousStudentBtn.setFocusPainted(false);
        previousStudentBtn.setNextFocusableComponent(barcodeTxt);
        previousStudentBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/previousStudent-selected.png"))); // NOI18N
        previousStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousStudentBtnActionPerformed(evt);
            }
        });
        jPanel6.add(previousStudentBtn);

        viewDetailsBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/View.png"))); // NOI18N
        viewDetailsBtn.setToolTipText("view Details");
        viewDetailsBtn.setBorder(null);
        viewDetailsBtn.setFocusPainted(false);
        viewDetailsBtn.setNextFocusableComponent(barcodeTxt);
        viewDetailsBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/view-selected.png"))); // NOI18N
        viewDetailsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDetailsBtnActionPerformed(evt);
            }
        });
        jPanel6.add(viewDetailsBtn);

        jSeparator1.setBackground(new java.awt.Color(24, 25, 54));
        jSeparator1.setForeground(new java.awt.Color(24, 25, 54));

        jLabel9.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("<html><body align='left'>Current Students<br>Attendance</body><html>");

        currentStuCount.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 23)); // NOI18N
        currentStuCount.setForeground(new java.awt.Color(102, 102, 102));
        currentStuCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        currentStuCount.setText("0");
        currentStuCount.setNextFocusableComponent(barcodeTxt);

        jPanel5.setBackground(new java.awt.Color(7, 1, 36));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(24, 25, 54), 2, true));

        jLabel6.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 33)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Tutes");

        jLabel7.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 30)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("<html><body align='left'>Last Day<br>Attendance</body></html>");

        jLabel5.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 33)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Payment");

        paymentMarkBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/false.png"))); // NOI18N
        paymentMarkBtn.setBorder(null);
        paymentMarkBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/noIcon.png"))); // NOI18N
        paymentMarkBtn.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/true.png"))); // NOI18N
        paymentMarkBtn.setEnabled(false);
        paymentMarkBtn.setFocusPainted(false);
        paymentMarkBtn.setNextFocusableComponent(barcodeTxt);
        paymentMarkBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/true.png"))); // NOI18N
        paymentMarkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentMarkBtnActionPerformed(evt);
            }
        });

        tuteIssueBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/false.png"))); // NOI18N
        tuteIssueBtn.setBorder(null);
        tuteIssueBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/noIcon.png"))); // NOI18N
        tuteIssueBtn.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/noIcon.png"))); // NOI18N
        tuteIssueBtn.setEnabled(false);
        tuteIssueBtn.setFocusPainted(false);
        tuteIssueBtn.setNextFocusableComponent(barcodeTxt);
        tuteIssueBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/true.png"))); // NOI18N
        tuteIssueBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tuteIssueBtnActionPerformed(evt);
            }
        });

        attendanceLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/noIcon.png"))); // NOI18N
        attendanceLabel.setNextFocusableComponent(barcodeTxt);

        cardTypeNotice.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 32)); // NOI18N
        cardTypeNotice.setForeground(new java.awt.Color(102, 102, 102));
        cardTypeNotice.setNextFocusableComponent(barcodeTxt);

        courseFeeNotice.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 18)); // NOI18N
        courseFeeNotice.setForeground(new java.awt.Color(102, 102, 102));
        courseFeeNotice.setNextFocusableComponent(barcodeTxt);

        courseFeeNotice1.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 20)); // NOI18N
        courseFeeNotice1.setForeground(new java.awt.Color(102, 102, 102));
        courseFeeNotice1.setText("Course Fee");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(attendanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(courseFeeNotice1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(61, 61, 61))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(courseFeeNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tuteIssueBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(paymentMarkBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(cardTypeNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cardTypeNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(courseFeeNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(courseFeeNotice1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentMarkBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tuteIssueBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attendanceLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(32, 32, 32))
        );

        jPanel7.setBackground(new java.awt.Color(7, 1, 36));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(24, 25, 54), 2, true));

        nameLabel.setBackground(new java.awt.Color(24, 25, 54));
        nameLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 20)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(102, 102, 102));
        nameLabel.setNextFocusableComponent(barcodeTxt);

        jLabel17.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 34)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Address");

        jLabel3.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 34)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Name");

        jLabel8.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 34)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Card Type");

        nicLabel.setBackground(new java.awt.Color(24, 25, 54));
        nicLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 20)); // NOI18N
        nicLabel.setForeground(new java.awt.Color(102, 102, 102));
        nicLabel.setNextFocusableComponent(barcodeTxt);

        jLabel18.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 34)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("NIC");

        addressLabel.setBackground(new java.awt.Color(24, 25, 54));
        addressLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 20)); // NOI18N
        addressLabel.setForeground(new java.awt.Color(102, 102, 102));
        addressLabel.setNextFocusableComponent(barcodeTxt);

        jLabel1.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Barcode");

        clearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/barcodeClean.png"))); // NOI18N
        clearBtn.setToolTipText("clean barcode");
        clearBtn.setBorder(null);
        clearBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/barcodeClean.png"))); // NOI18N
        clearBtn.setFocusPainted(false);
        clearBtn.setNextFocusableComponent(barcodeTxt);
        clearBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/barcodeClean-selected.png"))); // NOI18N
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        barcodeTxt.setBackground(new java.awt.Color(7, 1, 36));
        barcodeTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 20)); // NOI18N
        barcodeTxt.setForeground(new java.awt.Color(102, 102, 102));
        barcodeTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        barcodeTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        barcodeTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        barcodeTxt.setNextFocusableComponent(barcodeTxt);
        barcodeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barcodeTxtActionPerformed(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(24, 25, 54));
        jSeparator2.setForeground(new java.awt.Color(24, 25, 54));

        cardTypeLabel.setBackground(new java.awt.Color(24, 25, 54));
        cardTypeLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 20)); // NOI18N
        cardTypeLabel.setForeground(new java.awt.Color(102, 102, 102));
        cardTypeLabel.setNextFocusableComponent(barcodeTxt);

        goBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/go.png"))); // NOI18N
        goBtn.setToolTipText("enter the barcode and click");
        goBtn.setBorder(null);
        goBtn.setFocusPainted(false);
        goBtn.setNextFocusableComponent(barcodeTxt);
        goBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/go-selected.png"))); // NOI18N
        goBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel3)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(7, 7, 7)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(barcodeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(goBtn)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cardTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 322, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())))))
            .addComponent(jSeparator2)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(barcodeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(goBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(cardTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(76, 76, 76))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1366, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(courseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(instituteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(currentStuCount, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(instituteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(currentStuCount)
                            .addComponent(courseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 244, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void barcodeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barcodeTxtActionPerformed
        
    }//GEN-LAST:event_barcodeTxtActionPerformed

    private void tuteIssueBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tuteIssueBtnActionPerformed
       
    }//GEN-LAST:event_tuteIssueBtnActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        removeOptionalClassCalendar();
        Keyboard.getInstance().hideInterface();
        
        CardMarkingStartupScreen.getInstance().dispose();
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        this.dispose();
        removeOptionalClassCalendar();
        
    }//GEN-LAST:event_backBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        removeOptionalClassCalendar();
        Keyboard.getInstance().hideInterface();
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void nextStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextStudentBtnActionPerformed
       String barcode=barcodeTxt.getText();
       barcodeTxt.requestFocus();
       
       if(barcode.isEmpty()){
      
           WarningMessage.getInstance().showInterface("Enter the barcode");
       
       }else{
           if(!isPreviouslyCheacked()){
               
           previousStudentBtn.setEnabled(true);
           insertData();
           makePayment();
           retrieveData(CardMarker.DEFAULT_DATA);
           updateCurrentStudentAttendance();
           }else{
               
            if(nowWhere==currentStudentCount-1){
            
                update();
                retrieveData(CardMarker.DEFAULT_DATA);
                previousStudentBtn.setEnabled(true);
                nowWhere=currentStudentCount;
            }else{
                update();
                goTo(CardMarker.NEXT);
                
            }
           }
           
       }
       
     
    }//GEN-LAST:event_nextStudentBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        barcodeTxt.requestFocus();
        if(barcodeTxt.isEditable()){
            
        barcodeTxt.setText("");
        }
    }//GEN-LAST:event_clearBtnActionPerformed

    private void goBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBtnActionPerformed
        barcodeTxt.requestFocus();
        currentBarcode=barcodeTxt.getText();
        
         
            if(!isIssuedBarcode()){
                
                WarningMessage.getInstance().showInterface("This barcode is not valid");
               
            }else if(!isRegisteredForCurrentCourse()){
                
                Message.getInstance().showInterface("Not registered for this","course");
                
            }else if(isPreviouslyCheacked()){
               
                WarningMessage.getInstance().showInterface("Previously marked");
            }else{  
        
                retrieveData(CardMarker.NEW_DATA);
            }
    }//GEN-LAST:event_goBtnActionPerformed

    private void previousStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousStudentBtnActionPerformed
       barcodeTxt.requestFocus();
    
       if(nowWhere!=currentStudentCount){
       update();
       
       }
       goTo(CardMarker.PREVIOUS);
       
        
    }//GEN-LAST:event_previousStudentBtnActionPerformed

    private void viewDetailsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDetailsBtnActionPerformed
        barcodeTxt.requestFocus();
        String barcode=barcodeTxt.getText();
        String name=nameLabel.getText();
       if(name.isEmpty()){
         WarningMessage.getInstance().showInterface("No student to view details");
       
       }else{
           ApplicationView.getInstance().showInterface(stu_ID+"", studentName, fullname,barcode, address, dob, nic, cardType, 
                   mobile, tele, email, guardianName, guardianPhoneNo, registerDate);
       }
       
    }//GEN-LAST:event_viewDetailsBtnActionPerformed

    private void keyBoardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyBoardBtnActionPerformed
        barcodeTxt.requestFocus();
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        
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

    private void paymentMarkBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentMarkBtnActionPerformed
       
    }//GEN-LAST:event_paymentMarkBtnActionPerformed

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
            java.util.logging.Logger.getLogger(CardMarker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CardMarker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CardMarker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CardMarker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CardMarker().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLabel;
    private javax.swing.JLabel attendanceLabel;
    private javax.swing.JButton backBtn;
    private javax.swing.JTextField barcodeTxt;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel cardTypeLabel;
    private javax.swing.JLabel cardTypeNotice;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JButton clearBtn;
    private javax.swing.JLabel courseFeeNotice;
    private javax.swing.JLabel courseFeeNotice1;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JLabel currentStuCount;
    private javax.swing.JButton goBtn;
    private javax.swing.JLabel instituteLabel;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton keyBoardBtn;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton nextStudentBtn;
    private javax.swing.JLabel nicLabel;
    private javax.swing.JToggleButton paymentMarkBtn;
    private javax.swing.JButton previousStudentBtn;
    private javax.swing.JToggleButton tuteIssueBtn;
    private javax.swing.JButton viewDetailsBtn;
    // End of variables declaration//GEN-END:variables
}
