/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesmanagement;

import AttendanceManagement.GoTo;
import database.Connect;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import main.Keyboard;
import notifications.Message;
import notifications.WarningMessage;
import studentmanagement.PaymentEdior;

/**
 *
 * @author Nadun
 */
public class PaymentManager extends javax.swing.JFrame {

    /**
     * Creates new form PaymentManager
     */
    private DefaultTableModel dtm;
    public static final int NEXT = 1, PREVIOUS = 2, ANYWHERE = 3;
    private int currentYear, currentMonth, labelYear, labelMonth;
    private String editing_student_id,courseName,instituteName,cityName,payment_date,paid_amount,card_type,classCalendarID;
    
    private static PaymentManager instance = new PaymentManager();
    private int editingRow, paymentInteger;
    
    public PaymentManager() {
        initComponents();
        
        dtm = (DefaultTableModel) paymentTabel.getModel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
    }
    
    public static PaymentManager getInstance(){
        return instance;   
    }
    
    public void showInterface(){
        
        getCurrentMonth();
        clearText();
        setMonth();

        retrieveData();
        instance.setVisible(true);
        
        
    }
    
    private void getCurrentMonth(){
         
        Calendar c = new GregorianCalendar();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH)+1;
        labelYear = currentYear;
        labelMonth = currentMonth;
       
     }
   
    private String getSelectedMonth(){
        
        String year = Integer.toString(labelYear);
        String month=Integer.toString(labelMonth);
        
        if(month.length()==1){
            month="0"+month;
            
        }
        
        return (year+"-"+month);
        
    }
    
    
    private void setMonth(){
        
        String monthName = null;
        
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
   
    
    
    private String getSelectedMonthName(){
        
        String year = yearLabel.getText();
        String month= monthLabel.getText();
        
        return (year+" "+month);
        
    }
    
    
    public void addDataToTabel(int Student_id,String barcode,String stuName,
            String cardType,String institute,String amount,String city,String payment,
            String paymentDate,String course){
            
            String [] row = {Integer.toString(Student_id),stuName,barcode,course,institute,city,cardType,getSelectedMonthName(),payment,amount,paymentDate};
        
             if(!(!payment.equals("Completed") && hasDuplicateRow(row))){
            
                dtm.addRow(new Object[]{Student_id,stuName,barcode,course,institute,city,cardType,getSelectedMonthName(),payment,amount,paymentDate});
                System.out.println("Data Adding Success");
                
        }
    
    }
    
    
    private String formatValue(double d){
            DecimalFormat df=new DecimalFormat("#0.00");
        return df.format(d);   
    }
   
    private void cleanTabel(){
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
       
    }
   
    private boolean hasDuplicateRow(String[] stringArray){
        
        boolean IsDuplicate = false;
        int rowCount = dtm.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            
            String id = dtm.getValueAt(i, 0).toString();
            String name = dtm.getValueAt(i, 1).toString();
            String barcode = dtm.getValueAt(i, 2).toString();
            String course = dtm.getValueAt(i, 3).toString();
            String institute = dtm.getValueAt(i, 4).toString();
            String city = dtm.getValueAt(i, 5).toString();
            String cardType = dtm.getValueAt(i, 6).toString();
            String month = dtm.getValueAt(i, 7).toString();
            String payment = dtm.getValueAt(i, 8).toString();
            String amount = dtm.getValueAt(i, 9).toString();
            String paymentDate = dtm.getValueAt(i, 10).toString();
            
            if(id.equals(stringArray[0]) && name.equals(stringArray[1]) && barcode.equals(stringArray[2]) && course.equals(stringArray[3]) &&
                    institute.equals(stringArray[4]) && city.equals(stringArray[5]) && cardType.equals(stringArray[6]) && month.equals(stringArray[7]) &&
                    payment.equals(stringArray[8]) && amount.equals(stringArray[9]) && paymentDate.equals(stringArray[10])){
                IsDuplicate = true;
                i=rowCount-1;
            }
            
        }
        
        
        return IsDuplicate;
    }
    
    private void removeOptionalRow(){
        
        ArrayList<String> al = new ArrayList<String>();
                int rowCount = dtm.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    String payment = dtm.getValueAt(i, 8).toString();
                    String stuID = dtm.getValueAt(i, 0).toString();
                    if(payment.equals("Completed")){
                        al.add(stuID);
            }
            }
                for(String id : al){
                
                    for (int j = 0; j < dtm.getRowCount(); j++) {
                     String payment = dtm.getValueAt(j, 8).toString();
                     String stuID = dtm.getValueAt(j, 0).toString();
                     if(!payment.equals("Completed") && stuID.equals(id)){
                         dtm.removeRow(j);
                         j=dtm.getRowCount()-1;
                     }
                    }
                }
             
    }

    private void retrieveData(){
        cleanTabel();
        notificationLabel.setText("Waiting.....");
        Connect c=new Connect();
        String query="SELECT pay.amount,cal.date,stu.register_date,stu.id,stu.barcode,"
                + "stu.name_with_initials,shc.*,cour.name,cour.id,ins.*,ct.name FROM"
                + " ((((student stu LEFT JOIN card_type ct ON stu.card_type_id="
                + "ct.id) INNER JOIN student_has_course shc ON stu.id=shc.student_id) "
                + "INNER JOIN course cour ON cour.id=shc.course_id) INNER JOIN institution ins ON cour.institution_id=ins.id)"
                + "INNER JOIN class_calendar cal ON cour.id=cal.course_id AND STRCMP(cal.date,stu.register_date)>=0 "
                + "LEFT JOIN payment pay ON pay.class_calendar_id=cal.id AND pay.student_id=stu.id WHERE cal.date LIKE '"+getSelectedMonth()
                + "%' AND stu.delete_status=0 AND cour.delete_status=0 AND ins.delete_status=0";
        
      ResultSet r=c.getQuery(query);
      try {
      while(r.next()){
          
           
            String barcode=r.getString("stu.barcode");
            String studentName=r.getString("stu.name_with_initials");
            int stu_id=r.getInt("stu.id");
            String cardType=r.getString("ct.name");
            String course=r.getString("cour.name");
            String institute=r.getString("ins.name");
            String city=r.getString("ins.city");
            String dateOfPaid=r.getString("cal.date");
            String amount=r.getString("pay.amount");
            double paidAmount=0;
            try {
              paidAmount=Double.parseDouble(amount);
          } catch (Exception e) {
          }
            
            String payment="X";
            String amountString="-";
            String paymentDate="-";
            
            
            if(amount!=null){
            
                amountString=formatValue(paidAmount);
                paymentDate=dateOfPaid;
                payment="Completed";
            }
            
            
            if(cardType.equals("Free Card") && payment.equals("X")){
                payment="-";
                paymentDate="-";
            }
            
            
            addDataToTabel(stu_id, barcode, studentName,cardType, institute,amountString, city,payment, paymentDate, course);
            
            
      }
            removeOptionalRow();
            
        } catch (SQLException ex) {
            Logger.getLogger(PaymentManager.class.getName()).log(Level.SEVERE, null, ex);
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
        cleanTabel();
        Connect c=new Connect();
       
      ResultSet r=c.getQuery(query);
      try {
      while(r.next()){
          
           String barcode=r.getString("stu.barcode");
            String studentName=r.getString("stu.name_with_initials");
            int stu_id=r.getInt("stu.id");
            String cardType=r.getString("ct.name");
            String course=r.getString("cour.name");
            String institute=r.getString("ins.name");
            String city=r.getString("ins.city");
            String dateOfPaid=r.getString("cal.date");
            String amount=r.getString("pay.amount");
            double paidAmount=0;
            try {
              paidAmount=Double.parseDouble(amount);
          } catch (Exception e) {
          }
            
            String payment="X";
            String amountString="-";
            String paymentDate="-";
            
            
            if(amount!=null){
            
                amountString=formatValue(paidAmount);
                paymentDate=dateOfPaid;
                payment="Completed";
            }
            
            
            if(cardType.equals("Free Card") && payment.equals("X")){
                payment="-";
                paymentDate="-";
            }
            
            
                
            addDataToTabel(stu_id, barcode, studentName,cardType, institute,amountString, city,payment, paymentDate, course);
          
            
          }
            removeOptionalRow();
            
        } catch (SQLException ex) {
            Logger.getLogger(PaymentManager.class.getName()).log(Level.SEVERE, null, ex);
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
        stuNameTxt.setText("");
        barcodetxt.setText("");
        courseTxt.setText("");
        instituteTxt.setText("");
        cityTxt.setText("");
        paymentDateTxt.setText("");
        
        
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
    
    
    
    
    private void search(){
        
        String name=stuNameTxt.getText();
        String barcode = barcodetxt.getText();
        String institute =instituteTxt.getText();
        String city=cityTxt.getText();
        String course = courseTxt.getText();
        String paymentDate=paymentDateTxt.getText();
        
       

        if(paymentDate.isEmpty()){
        
        String query="SELECT cal.date,pay.amount,stu.id,stu.barcode,stu.name_with_initials,shc.*,cour.*,ins.*,ct.name FROM"
                + " ((((student stu LEFT JOIN card_type ct ON stu.card_type_id="
                + "ct.id) INNER JOIN student_has_course shc ON stu.id=shc.student_id) "
              + "INNER JOIN course cour ON cour.id=shc.course_id) INNER JOIN institution ins ON cour.institution_id=ins.id) "
                + "INNER JOIN class_calendar cal ON cour.id=cal.course_id AND STRCMP(cal.date,stu.register_date)>=0 "
                + "LEFT JOIN payment pay ON pay.class_calendar_id=cal.id AND pay.student_id=stu.id "
                + "WHERE (stu.name_with_initials LIKE '%"+name+"%' OR stu.fullname LIKE '%"+name+"%') AND cal.date LIKE '"+getSelectedMonth()+"%'"
                   + " AND stu.barcode LIKE '"+barcode+"%' AND cour.name LIKE '%"+course+"%' AND ins.name LIKE '%"+institute+"%'"
                + " AND ins.city LIKE '"+city+"%' AND stu.delete_status=0 AND cour.delete_status=0 AND ins.delete_status=0";
        
               retrieveData(query);
        }else{
       
        if(paymentDate.length()==1){
            paymentDate="0"+paymentDate;
        }
        
        String query1="SELECT cal.date,pay.amount,stu.id,stu.barcode,stu.name_with_initials,shc.*,cour.*,ins.*,ct.name FROM"
                + " ((((student stu LEFT JOIN card_type ct ON stu.card_type_id="
                + "ct.id) INNER JOIN student_has_course shc ON stu.id=shc.student_id) "
              + "INNER JOIN course cour ON cour.id=shc.course_id) INNER JOIN institution ins ON cour.institution_id=ins.id) "
                + "LEFT JOIN payment pay ON pay.student_id=stu.id INNER JOIN class_calendar cal ON cour.id=cal.course_id "
                + "AND STRCMP(cal.date,stu.register_date)>=0 AND cal.id=pay.class_calendar_id"
                + " WHERE (stu.name_with_initials LIKE '%"+name+"%' OR stu.fullname LIKE '%"+name+"%') AND cal.date LIKE '"+getSelectedMonth()+"%'"
                   + " AND stu.barcode LIKE '"+barcode+"%' AND cour.name LIKE '%"+course+"%' AND ins.name LIKE '%"+institute+"%' AND cal.date LIKE '%"+paymentDate+"'"
                + " AND ins.city LIKE '"+city+"%' AND stu.delete_status=0 AND cour.delete_status=0 AND ins.delete_status=0";
        
        
        retrieveData(query1);
        }
    }
    
    private void getClassCalendarDetails(){
        
        String query="SELECT cal.id,cour.course_fee,cal.date FROM class_calendar cal "
                       + "INNER JOIN course cour ON cour.id=cal.course_id INNER JOIN institution inst ON inst.id=cour.institution_id"
                       + " WHERE inst.delete_status=0 AND cour.delete_status=0 AND cour.name='"+courseName+"' AND "
                       + "inst.name='"+instituteName+"' AND inst.city='"+cityName+"' ORDER BY cal.date DESC LIMIT 1";
        
        Connect con = new Connect();
        ResultSet re=con.getQuery(query);
        try {
            while (re.next()) {            
            
                classCalendarID=re.getString("cal.id");
                double course_fee=re.getDouble("cour.course_fee");
                payment_date= re.getString("cal.date");
                
                if(card_type.equals("Full Card")){
                   paid_amount=formatValue(course_fee);
                }else if(card_type.equals("Half Card")){
                   paid_amount=formatValue(course_fee/2);
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void changePayment(int newPayment){
        
        Connect con = new Connect();
        if(newPayment!=paymentInteger){
            
            if(newPayment==0){
               dtm.setValueAt("X", editingRow, 8);
               dtm.setValueAt("-", editingRow, 10);
               dtm.setValueAt("-", editingRow, 9);
               
               String query="DELETE FROM payment WHERE class_calendar_id IN (SELECT cal.id FROM class_calendar cal "
                       + "INNER JOIN course cour ON cour.id=cal.course_id INNER JOIN institution inst ON inst.id=cour.institution_id"
                       + " WHERE inst.delete_status=0 AND cour.delete_status=0 AND cour.name='"+courseName+"' AND "
                       + "inst.name='"+instituteName+"' AND inst.city='"+cityName+"' AND cal.date='"+payment_date+"') AND student_id="
                       + editing_student_id;
               
               
               con.setQuery(query, null);
            }
            else if(newPayment==1){
                
                getClassCalendarDetails();
                dtm.setValueAt("Completed", editingRow, 8);
                dtm.setValueAt(payment_date, editingRow, 10);
                dtm.setValueAt(paid_amount, editingRow, 9);
               
                String query="INSERT INTO payment(amount,student_id,class_calendar_id) VALUES("
                +paid_amount+","
                +editing_student_id+","
                +classCalendarID
                + ")";
        
               con.setQuery(query, null);
            }
          
        }
    }
    
    private void changeActivity(){
        
        int selectedRowCount = paymentTabel.getSelectedRowCount();
        if(selectedRowCount==1){
            int selectedRow = paymentTabel.getSelectedRow();
            String valueAt = dtm.getValueAt(selectedRow, 6).toString();
            if(valueAt.equals("Free Card")){
              paymentChangebtn.setEnabled(false);
            }else{
              paymentChangebtn.setEnabled(true);  
            }
       
        }else if(selectedRowCount>1){
           paymentChangebtn.setEnabled(false); 
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
        jPanel5 = new javax.swing.JPanel();
        homeBtn = new javax.swing.JButton();
        keyBoardBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        paymentTabel = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        stuNameTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        barcodetxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        searchBtn = new javax.swing.JButton();
        removeSearchBtn = new javax.swing.JButton();
        paymentChangebtn = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        notificationLabel = new javax.swing.JLabel();
        paymentDateTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        courseTxt = new javax.swing.JTextField();
        cityTxt = new javax.swing.JTextField();
        instituteTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        jLabel10.setText("Payment Details");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/payment-icon.png"))); // NOI18N

        yearLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 25)); // NOI18N
        yearLabel.setForeground(new java.awt.Color(153, 153, 153));
        yearLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        monthLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 25)); // NOI18N
        monthLabel.setForeground(new java.awt.Color(153, 153, 153));

        jPanel3.setBackground(new java.awt.Color(24, 25, 54));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        goToPreviousBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/goToPrevious.png"))); // NOI18N
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
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(yearLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(monthLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(24, 25, 54));

        jPanel5.setBackground(new java.awt.Color(24, 25, 54));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        homeBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        homeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        homeBtn.setDisabledIcon(null);
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
        keyBoardBtn.setDisabledIcon(null);
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
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane1.setBackground(new java.awt.Color(7, 1, 36));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));

        paymentTabel.setBackground(new java.awt.Color(7, 1, 36));
        paymentTabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        paymentTabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        paymentTabel.setForeground(new java.awt.Color(102, 102, 102));
        paymentTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Barcode", "Course Name", "Institution", "City", "Card Type", "Month", "Payment Status", "Amount", "Date of Paid"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        paymentTabel.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        paymentTabel.setFocusable(false);
        paymentTabel.setGridColor(new java.awt.Color(24, 25, 54));
        paymentTabel.setRowHeight(30);
        paymentTabel.setSelectionBackground(new java.awt.Color(24, 25, 54));
        paymentTabel.setSelectionForeground(new java.awt.Color(153, 153, 153));
        paymentTabel.getTableHeader().setReorderingAllowed(false);
        paymentTabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                paymentTabelMouseDragged(evt);
            }
        });
        paymentTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paymentTabelMouseClicked(evt);
            }
        });
        paymentTabel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                paymentTabelPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(paymentTabel);
        if (paymentTabel.getColumnModel().getColumnCount() > 0) {
            paymentTabel.getColumnModel().getColumn(0).setResizable(false);
            paymentTabel.getColumnModel().getColumn(0).setPreferredWidth(150);
            paymentTabel.getColumnModel().getColumn(1).setResizable(false);
            paymentTabel.getColumnModel().getColumn(1).setPreferredWidth(350);
            paymentTabel.getColumnModel().getColumn(2).setResizable(false);
            paymentTabel.getColumnModel().getColumn(2).setPreferredWidth(200);
            paymentTabel.getColumnModel().getColumn(3).setResizable(false);
            paymentTabel.getColumnModel().getColumn(3).setPreferredWidth(300);
            paymentTabel.getColumnModel().getColumn(4).setResizable(false);
            paymentTabel.getColumnModel().getColumn(4).setPreferredWidth(200);
            paymentTabel.getColumnModel().getColumn(5).setResizable(false);
            paymentTabel.getColumnModel().getColumn(5).setPreferredWidth(200);
            paymentTabel.getColumnModel().getColumn(6).setResizable(false);
            paymentTabel.getColumnModel().getColumn(6).setPreferredWidth(100);
            paymentTabel.getColumnModel().getColumn(7).setResizable(false);
            paymentTabel.getColumnModel().getColumn(7).setPreferredWidth(220);
            paymentTabel.getColumnModel().getColumn(8).setResizable(false);
            paymentTabel.getColumnModel().getColumn(8).setPreferredWidth(200);
            paymentTabel.getColumnModel().getColumn(9).setResizable(false);
            paymentTabel.getColumnModel().getColumn(9).setPreferredWidth(200);
            paymentTabel.getColumnModel().getColumn(10).setResizable(false);
            paymentTabel.getColumnModel().getColumn(10).setPreferredWidth(200);
        }

        jLabel5.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Search");

        jLabel6.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Student Name");

        stuNameTxt.setBackground(new java.awt.Color(7, 1, 36));
        stuNameTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        stuNameTxt.setForeground(new java.awt.Color(102, 102, 102));
        stuNameTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        stuNameTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        stuNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stuNameTxtActionPerformed(evt);
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

        jLabel8.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Course");

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

        paymentChangebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/changePayment.png"))); // NOI18N
        paymentChangebtn.setToolTipText("edit payments");
        paymentChangebtn.setBorder(null);
        paymentChangebtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/changePayment-disable.png"))); // NOI18N
        paymentChangebtn.setFocusPainted(false);
        paymentChangebtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/changePayment-selected.png"))); // NOI18N
        paymentChangebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentChangebtnActionPerformed(evt);
            }
        });
        jPanel6.add(paymentChangebtn);

        jLabel13.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Payment Date");

        notificationLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 23)); // NOI18N
        notificationLabel.setForeground(new java.awt.Color(102, 102, 102));

        paymentDateTxt.setBackground(new java.awt.Color(7, 1, 36));
        paymentDateTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        paymentDateTxt.setForeground(new java.awt.Color(102, 102, 102));
        paymentDateTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        paymentDateTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        jLabel9.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Institute");

        jLabel11.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("City / Town");

        courseTxt.setBackground(new java.awt.Color(7, 1, 36));
        courseTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        courseTxt.setForeground(new java.awt.Color(102, 102, 102));
        courseTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        courseTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        cityTxt.setBackground(new java.awt.Color(7, 1, 36));
        cityTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        cityTxt.setForeground(new java.awt.Color(102, 102, 102));
        cityTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        cityTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        instituteTxt.setBackground(new java.awt.Color(7, 1, 36));
        instituteTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        instituteTxt.setForeground(new java.awt.Color(102, 102, 102));
        instituteTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        instituteTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(barcodetxt, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(stuNameTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(courseTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(instituteTxt)
                                .addComponent(cityTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(paymentDateTxt)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(notificationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(123, 123, 123)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(16, 16, 16)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(stuNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(barcodetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(courseTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(paymentDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(124, 124, 124))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(notificationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void homeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeBtnActionPerformed
        
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        GoTo.getInstance().dispose();
        PaymentEdior.getInstance().dispose();
        this.dispose();
    }//GEN-LAST:event_homeBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
       
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        GoTo.getInstance().dispose();
        PaymentEdior.getInstance().dispose();
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        
        notificationLabel.setText("Searching.....");
        search();


    }//GEN-LAST:event_searchBtnActionPerformed

    private void removeSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSearchBtnActionPerformed
        clearText();
        retrieveData();
    }//GEN-LAST:event_removeSearchBtnActionPerformed

    private void stuNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stuNameTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stuNameTxtActionPerformed

    private void keyBoardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyBoardBtnActionPerformed

        if(Keyboard.getInstance().isVisible()){
            Keyboard.getInstance().hideInterface();
        }
        else{
            Keyboard.getInstance().showInterface();

        }
    }//GEN-LAST:event_keyBoardBtnActionPerformed

    private void goToPreviousBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToPreviousBtnActionPerformed

        notificationLabel.setText("Waiting.....");
        goToNextBtn.setEnabled(true);
        goTo(PaymentManager.PREVIOUS,null,null);
        search();
    }//GEN-LAST:event_goToPreviousBtnActionPerformed

    private void goToBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToBtnActionPerformed

        GoTo.getInstance().showInterface(GoTo.PAYMENT_MANAGER);

    }//GEN-LAST:event_goToBtnActionPerformed

    private void goToNextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToNextBtnActionPerformed

        notificationLabel.setText("Waiting.....");
        goToPreviousBtn.setEnabled(true);
        goTo(PaymentManager.NEXT,null,null);
        search();
    }//GEN-LAST:event_goToNextBtnActionPerformed

    private void paymentChangebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentChangebtnActionPerformed
        int rowCount = dtm.getRowCount();
        int selectedRowCount = paymentTabel.getSelectedRowCount();
        int selectedRow = paymentTabel.getSelectedRow();
        String cardType = null;
        if(selectedRowCount==1){
            String valueAt = dtm.getValueAt(selectedRow, 6).toString();
            cardType=valueAt;
        }
        
        if(rowCount==0){
            
        }else if(selectedRowCount==0){
            Message.getInstance().showInterface("Select one student to","change payment status");
            
        }else if(selectedRowCount>1){
            
        }else if(selectedRowCount==1 && "Free Card".equals(cardType)){
            
        }else{
            String stuID = dtm.getValueAt(selectedRow, 0).toString();
            String name = dtm.getValueAt(selectedRow, 1).toString();
            String barcode = dtm.getValueAt(selectedRow, 2).toString();
            String course = dtm.getValueAt(selectedRow, 3).toString();
            String inst = dtm.getValueAt(selectedRow, 4).toString();
            String city = dtm.getValueAt(selectedRow, 5).toString();
            String payment = dtm.getValueAt(selectedRow, 8).toString();
            String date= dtm.getValueAt(selectedRow, 10).toString();
           
            if(payment.equals("Completed")){
               paymentInteger=1 ;
            }else if(payment.equals("X")){
               paymentInteger=0 ;
            }
           
            editing_student_id=stuID;
            courseName=course;
            instituteName=inst;
            cityName=city;
            payment_date=date;
            editingRow=selectedRow;
            card_type=cardType;
            
            PaymentEdior.getInstance().showInterface(name, barcode, courseName, instituteName, cityName, paymentInteger);
            
        }
    }//GEN-LAST:event_paymentChangebtnActionPerformed

    private void paymentTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentTabelMouseClicked
        
        changeActivity();
    }//GEN-LAST:event_paymentTabelMouseClicked

    private void paymentTabelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_paymentTabelPropertyChange
        
    }//GEN-LAST:event_paymentTabelPropertyChange

    private void paymentTabelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentTabelMouseDragged
       changeActivity();
    }//GEN-LAST:event_paymentTabelMouseDragged

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        Message.getInstance().disposeMessage();
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
            java.util.logging.Logger.getLogger(PaymentManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaymentManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaymentManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaymentManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaymentManager().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField barcodetxt;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField cityTxt;
    private javax.swing.JTextField courseTxt;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton keyBoardBtn;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JLabel notificationLabel;
    private javax.swing.JButton paymentChangebtn;
    private javax.swing.JTextField paymentDateTxt;
    private javax.swing.JTable paymentTabel;
    private javax.swing.JButton removeSearchBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField stuNameTxt;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables
}
