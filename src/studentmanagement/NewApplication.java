/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement;

import coursesmanagement.CoursesSelector;
import database.Connect;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import main.Keyboard;
import notifications.Message;
import notifications.SavedStatusNotice;
import notifications.WarningMessage;

/**
 *
 * @author Nadun
 */
public class NewApplication extends javax.swing.JFrame {

    /**
     * Creates new form NewCourse
     */
    private int courseTabelRow;
    private DefaultTableModel dtm;
    private static NewApplication instance=new NewApplication();
    
    private int studentID;
    public NewApplication() {
        initComponents();
        dtm=(DefaultTableModel) courseTabel.getModel();
        
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
    }
    
    public static NewApplication getInstance(){
        return instance;
        
    }
    
    public void showInterface(){
        cleanApplication();
        makeChangesAsDefaultInterface();
        instance.setVisible(true);
        
    }
    
    private Date parseDate(String stringDate){
        Date date=null;
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            date = df.parse(stringDate);
        } catch (ParseException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
        
    }
    
    private String getCurrentDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        
        return sdf.format(date.getTime());
        
    }
    
     public void showInterface(int stu_id,String name,String fullName,String barcode,String address,String dob,String nic
            ,String cardT,String mobile,String tele,String email,String guardianName,String guardianPhone){
       
       studentID=stu_id;
       instance.setVisible(true);
       cleanApplication();
       makeChangesAsEditor();
       
       if(mobile.equals("-")){
          mobile=""; 
       }
       if(tele.equals("-")){
          tele=""; 
       }
       if(email.equals("-")){
          email=""; 
       }
       if(guardianPhone.equals("-")){
          guardianPhone=""; 
       }
       
       
       nameWithInitialsTxt.setText(name);
       fullNameTxt.setText(fullName);
       barcodeTxt.setText(barcode);
       AddressTxt.setText(address);
       dobTxt.setDate(parseDate(dob));
       nicTxt.setText(nic);
       
       if(cardT.equals("Full Card")){
           fullCardBtn.setSelected(true);
           
       }else if(cardT.equals("Half Card")){
           halfCardBtn.setSelected(true);
           
       }else if(cardT.equals("Free Card")){
           freeCardBtn.setSelected(true);
           
       }
       
       mobileTxt.setText(mobile);
       telephoneTxt.setText(tele);
       emailTxt.setText(email);
       guardianTxt.setText(guardianName);
       guardianPhoneTxt.setText(guardianPhone);
       retrieveCourseDetails(studentID);
        
    }
    
    public void makeChangesAsEditor(){
        interfaceHeadLine.setText("Student Details Editor");
        interfaceIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/EditorIcon.png")));
        backBtn.setEnabled(true);
    }
    
    private void makeChangesAsDefaultInterface(){
        
        interfaceHeadLine.setText("Student Application");
        interfaceIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/newStudent-icon.png")));
        backBtn.setEnabled(false);
        
    }
    
    public void retrieveCourseDetails(int stuID){
      cleanTabel();
      String query="SELECT inst.*,cour.*,shc.* FROM (student_has_course shc INNER JOIN course cour ON cour.id="
              + "shc.course_id) INNER JOIN institution inst ON inst.id="
                + "cour.institution_id WHERE shc.student_id="+stuID+" AND cour.delete_status=0 AND inst.delete_status=0";
      Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            String courseName=r.getString("cour.name");
           
            String institute= r.getString("inst.name");
            String city=r.getString("inst.city");
                addCourse(courseName, institute, city);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    private void cleanTabel(){
        dtm.getDataVector().removeAllElements();
        dtm.fireTableDataChanged();
    }
    
    public void addCourse(String course,String institute,String city){
        String serialNo=Integer.toString(dtm.getRowCount()+1);
        dtm.addRow(new Object[]{serialNo,course,institute,city});
    }
    
     private void resetSerialNumbers(){
           int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               dtm.setValueAt(i+1, i, 0);
               
           }   
    }

    public int getCourseTabelRow() {
        return courseTabelRow;
    }
    
    private void cleanApplication(){
        nameWithInitialsTxt.setText("");
        fullNameTxt.setText("");
        barcodeTxt.setText("");
        AddressTxt.setText("");
        dobTxt.setDate(new Date());
        nicTxt.setText("");
        mobileTxt.setText("");
        telephoneTxt.setText("");
        emailTxt.setText("");
        guardianTxt.setText("");
        guardianPhoneTxt.setText("");
        fullCardBtn.setSelected(true);
        cleanTabel();
        
    }
   
     public boolean isEarlyAddedCourse(String course,String institute,String city){
          boolean wheatherAdd = false;
          int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               String tableCourseName=dtm.getValueAt(i, 1).toString();
               String tableInstitute=dtm.getValueAt(i, 2).toString();
               String tableCity=dtm.getValueAt(i, 3).toString();
               if(tableInstitute.equalsIgnoreCase(institute) && tableCity.equalsIgnoreCase(city)
                   && tableCourseName.equalsIgnoreCase(course)){
                   courseTabelRow=i;
                   wheatherAdd=true;
                   i=rCount-1;
               } 
           }
           return wheatherAdd;  
    }
     
     
     
     public int getCourseID(String courseName,String institute,String city){
         int courseID=0;
         String query="SELECT cour.id FROM course cour INNER JOIN institution ins ON ins.id=cour.institution_id WHERE "
                 + "cour.name='"+courseName+"' AND ins.name='"+institute+"' AND ins.city='"+city+"' AND cour.delete_status=0 AND ins.delete_status=0";
         Connect con=new Connect();
         ResultSet r=con.getQuery(query);
        try {
            while(r.next()){
                courseID=r.getInt("id");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return courseID;
         
     }
     private boolean isInvalidNIC(String nic){
         boolean invalidity=false;
         
        
         if(nic.length()!=10){
            invalidity=true; 
         }else if(!(nic.endsWith("V") || nic.endsWith("v"))){
            invalidity=true; 
         }else{
             String subString=nic.substring(0,9);
             try{
             long subNIC=Long.parseLong(subString);
             }catch(NumberFormatException e){
                 invalidity=true;
             }
         }
        return invalidity;
         
     }
     
     
     private boolean isInvalidPhoneNo(String number){
         boolean invalidity=false;
         
         if(!(number.length()==10 && number.startsWith("0")) && !(number.length()==9 && !number.startsWith("0"))){
            invalidity=true; 

         }else{
             
             try{
             long nmb=Long.parseLong(number);
             }catch(NumberFormatException e){
                 invalidity=true;
                 
             }
         }
        return invalidity;
         
     }
     
     private boolean isInvalidEmail(String email){
         boolean invalidity=false;
         
       if(email.length()<=4 || (email.length()>4 && !email.endsWith(".com") )){
        invalidity=true;
       }
        return invalidity;
         
     }
      private boolean isInvalidDateOfBirth(Date dob){
         boolean invalidity=false;

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate=new Date();
        String currentDateString=sdf.format(currentDate);
        String dobString=sdf.format(dob);
        
        int compareTo=dob.compareTo(currentDate);
        if(compareTo > 0 || currentDateString.equals(dobString)){
            invalidity=true;
        }

        return invalidity;

     }

      private boolean isDuplicateBarcode(String barcode){
          boolean duplicate=false; 
          
           String query="SELECT id FROM student WHERE barcode='"+barcode+"'";
           Connect c=new Connect();
           ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            duplicate=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return duplicate;
      }
      
      
      private boolean hasSameBarcode(String barcode){
          boolean have=false; 
          
           String query="SELECT id FROM student WHERE barcode='"+barcode+"'";
           Connect c=new Connect();
           ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
             
                int stu_id=r.getInt("id");
                if(stu_id==studentID){
                   continue;
                }else{
                have=true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return have;
      }
      
      
      private boolean isAlreadyGivenNIC(String nic){
          boolean isAlreadyGiven=false;
          String query="SELECT id FROM student WHERE nic='"+nic+"' AND delete_status=0";
           Connect c=new Connect();
           ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            isAlreadyGiven=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isAlreadyGiven; 
      }
      
      
      private boolean hasSameNIC(String nic){
          boolean WheatherHave=false;
          String query="SELECT id FROM student WHERE nic='"+nic+"' AND delete_status=0";
           Connect c=new Connect();
           ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
                
                int stu_id=r.getInt("id");
                if(stu_id==studentID){
                   continue;
                }else{
            WheatherHave=true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return WheatherHave; 
      }
     
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        interfaceHeadLine = new javax.swing.JLabel();
        interfaceIcon = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        keyBoardBtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        backBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        barcodeTxt = new javax.swing.JTextField();
        fullNameTxt = new javax.swing.JTextField();
        nameWithInitialsTxt = new javax.swing.JTextField();
        telephoneTxt = new javax.swing.JTextField();
        mobileTxt = new javax.swing.JTextField();
        nicTxt = new javax.swing.JTextField();
        emailTxt = new javax.swing.JTextField();
        guardianTxt = new javax.swing.JTextField();
        guardianPhoneTxt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        fullCardBtn = new javax.swing.JToggleButton();
        halfCardBtn = new javax.swing.JToggleButton();
        freeCardBtn = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        clearTxtBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        AddressTxt = new javax.swing.JTextField();
        coursesSelectorBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        courseTabel = new javax.swing.JTable();
        removeCourseBtn = new javax.swing.JButton();
        dobTxt = new de.wannawork.jcalendar.JCalendarComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Application Form");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(7, 1, 36));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Name with initials");

        jLabel6.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Full Name");

        jLabel7.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Barcode");

        jLabel8.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Address");

        jLabel9.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Date of Birth");

        jLabel11.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("NIC Number");

        jLabel13.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Mobile Number");

        jLabel14.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Telephone Number");

        jLabel15.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Email Address");

        jLabel19.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Guardian Name");

        jLabel20.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Guardian Phone No");

        jPanel2.setBackground(new java.awt.Color(24, 25, 54));

        interfaceHeadLine.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 32)); // NOI18N
        interfaceHeadLine.setForeground(new java.awt.Color(153, 153, 153));
        interfaceHeadLine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        interfaceHeadLine.setText("Student Application");

        interfaceIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/newStudent-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(interfaceIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(interfaceHeadLine, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(interfaceHeadLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        keyBoardBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/disableIcon.png"))); // NOI18N
        keyBoardBtn.setFocusPainted(false);
        keyBoardBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Keyboard-selected.png"))); // NOI18N
        keyBoardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyBoardBtnActionPerformed(evt);
            }
        });
        jPanel5.add(keyBoardBtn);

        jPanel6.setBackground(new java.awt.Color(7, 1, 36));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

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
        jPanel6.add(backBtn);

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close.png"))); // NOI18N
        cancelBtn.setBorder(null);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close-selected.png"))); // NOI18N
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        jPanel6.add(cancelBtn);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        barcodeTxt.setBackground(new java.awt.Color(7, 1, 36));
        barcodeTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        barcodeTxt.setForeground(new java.awt.Color(102, 102, 102));
        barcodeTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        barcodeTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        barcodeTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        barcodeTxt.setNextFocusableComponent(AddressTxt);
        barcodeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barcodeTxtActionPerformed(evt);
            }
        });

        fullNameTxt.setBackground(new java.awt.Color(7, 1, 36));
        fullNameTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        fullNameTxt.setForeground(new java.awt.Color(102, 102, 102));
        fullNameTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        fullNameTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        fullNameTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        fullNameTxt.setNextFocusableComponent(barcodeTxt);
        fullNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullNameTxtActionPerformed(evt);
            }
        });

        nameWithInitialsTxt.setBackground(new java.awt.Color(7, 1, 36));
        nameWithInitialsTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        nameWithInitialsTxt.setForeground(new java.awt.Color(102, 102, 102));
        nameWithInitialsTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        nameWithInitialsTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        nameWithInitialsTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        nameWithInitialsTxt.setNextFocusableComponent(fullNameTxt);
        nameWithInitialsTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameWithInitialsTxtActionPerformed(evt);
            }
        });

        telephoneTxt.setBackground(new java.awt.Color(7, 1, 36));
        telephoneTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        telephoneTxt.setForeground(new java.awt.Color(102, 102, 102));
        telephoneTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        telephoneTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        telephoneTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        telephoneTxt.setNextFocusableComponent(courseTabel);
        telephoneTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telephoneTxtActionPerformed(evt);
            }
        });

        mobileTxt.setBackground(new java.awt.Color(7, 1, 36));
        mobileTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        mobileTxt.setForeground(new java.awt.Color(102, 102, 102));
        mobileTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        mobileTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        mobileTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        mobileTxt.setNextFocusableComponent(telephoneTxt);
        mobileTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobileTxtActionPerformed(evt);
            }
        });

        nicTxt.setBackground(new java.awt.Color(7, 1, 36));
        nicTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        nicTxt.setForeground(new java.awt.Color(102, 102, 102));
        nicTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        nicTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        nicTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        nicTxt.setNextFocusableComponent(mobileTxt);
        nicTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nicTxtActionPerformed(evt);
            }
        });

        emailTxt.setBackground(new java.awt.Color(7, 1, 36));
        emailTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        emailTxt.setForeground(new java.awt.Color(102, 102, 102));
        emailTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        emailTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        emailTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        emailTxt.setNextFocusableComponent(guardianTxt);
        emailTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTxtActionPerformed(evt);
            }
        });

        guardianTxt.setBackground(new java.awt.Color(7, 1, 36));
        guardianTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        guardianTxt.setForeground(new java.awt.Color(102, 102, 102));
        guardianTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        guardianTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        guardianTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        guardianTxt.setNextFocusableComponent(guardianPhoneTxt);
        guardianTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardianTxtActionPerformed(evt);
            }
        });

        guardianPhoneTxt.setBackground(new java.awt.Color(7, 1, 36));
        guardianPhoneTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        guardianPhoneTxt.setForeground(new java.awt.Color(102, 102, 102));
        guardianPhoneTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        guardianPhoneTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        guardianPhoneTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        guardianPhoneTxt.setNextFocusableComponent(saveBtn);
        guardianPhoneTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardianPhoneTxtActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Card Type");

        jLabel17.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Courses");

        buttonGroup2.add(fullCardBtn);
        fullCardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fullCardBtnIcon.png"))); // NOI18N
        fullCardBtn.setSelected(true);
        fullCardBtn.setBorder(null);
        fullCardBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fullCardBtnIcon.png"))); // NOI18N
        fullCardBtn.setFocusPainted(false);
        fullCardBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fullCardBtnIcon-selected.png"))); // NOI18N

        buttonGroup2.add(halfCardBtn);
        halfCardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/HalfCardBtnIcon.png"))); // NOI18N
        halfCardBtn.setBorder(null);
        halfCardBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/HalfCardBtnIcon.png"))); // NOI18N
        halfCardBtn.setFocusPainted(false);
        halfCardBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/HalfCardBtnIcon-selected.png"))); // NOI18N

        buttonGroup2.add(freeCardBtn);
        freeCardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/freeCardBtnIcon.png"))); // NOI18N
        freeCardBtn.setBorder(null);
        freeCardBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/freeCardBtnIcon.png"))); // NOI18N
        freeCardBtn.setFocusPainted(false);
        freeCardBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/freeCardBtnIcon-selected.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(7, 1, 36));
        jPanel3.setLayout(new java.awt.GridLayout(0, 1, 0, 2));

        clearTxtBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/clearAll.png"))); // NOI18N
        clearTxtBtn.setToolTipText("clean application");
        clearTxtBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/disableIcon-2.png"))); // NOI18N
        clearTxtBtn.setFocusPainted(false);
        clearTxtBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/clearAll-selected.png"))); // NOI18N
        clearTxtBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearTxtBtnActionPerformed(evt);
            }
        });
        jPanel3.add(clearTxtBtn);

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        saveBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/disableIcon-2.png"))); // NOI18N
        saveBtn.setFocusPainted(false);
        saveBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save-selected.png"))); // NOI18N
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        jPanel3.add(saveBtn);

        AddressTxt.setBackground(new java.awt.Color(7, 1, 36));
        AddressTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        AddressTxt.setForeground(new java.awt.Color(102, 102, 102));
        AddressTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        AddressTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        AddressTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        AddressTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddressTxtActionPerformed(evt);
            }
        });

        coursesSelectorBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        coursesSelectorBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/addCourses.png"))); // NOI18N
        coursesSelectorBtn.setBorder(null);
        coursesSelectorBtn.setFocusPainted(false);
        coursesSelectorBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/addCourses-selected.png"))); // NOI18N
        coursesSelectorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursesSelectorBtnActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(7, 1, 36));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        courseTabel.setBackground(new java.awt.Color(7, 1, 36));
        courseTabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        courseTabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        courseTabel.setForeground(new java.awt.Color(102, 102, 102));
        courseTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Serial no", "Course Name", "Institute", "City / Town"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        courseTabel.setFocusable(false);
        courseTabel.setGridColor(new java.awt.Color(24, 25, 54));
        courseTabel.setNextFocusableComponent(emailTxt);
        courseTabel.setRowHeight(30);
        courseTabel.setSelectionBackground(new java.awt.Color(24, 25, 54));
        courseTabel.setSelectionForeground(new java.awt.Color(204, 204, 204));
        courseTabel.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(courseTabel);
        if (courseTabel.getColumnModel().getColumnCount() > 0) {
            courseTabel.getColumnModel().getColumn(0).setResizable(false);
            courseTabel.getColumnModel().getColumn(0).setPreferredWidth(50);
            courseTabel.getColumnModel().getColumn(1).setPreferredWidth(300);
            courseTabel.getColumnModel().getColumn(2).setResizable(false);
            courseTabel.getColumnModel().getColumn(2).setPreferredWidth(150);
            courseTabel.getColumnModel().getColumn(3).setResizable(false);
            courseTabel.getColumnModel().getColumn(3).setPreferredWidth(150);
        }

        removeCourseBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        removeCourseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/removeCourse.png"))); // NOI18N
        removeCourseBtn.setBorder(null);
        removeCourseBtn.setFocusPainted(false);
        removeCourseBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/removeCourse-selected.png"))); // NOI18N
        removeCourseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCourseBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17)
                    .addComponent(jLabel11)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel19)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(guardianPhoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telephoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(guardianTxt, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
                                    .addComponent(emailTxt)
                                    .addComponent(AddressTxt, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(coursesSelectorBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(removeCourseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                        .addContainerGap(310, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(barcodeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(mobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(227, 227, 227)
                                    .addComponent(freeCardBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(nicTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                                        .addComponent(dobTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(halfCardBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(fullCardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(nameWithInitialsTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
                                .addComponent(fullNameTxt, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 282, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameWithInitialsTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fullNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(barcodeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fullCardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(dobTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(halfCardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nicTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(mobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(freeCardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(telephoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(coursesSelectorBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(4, 4, 4)
                        .addComponent(removeCourseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(guardianTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(guardianPhoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void barcodeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barcodeTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_barcodeTxtActionPerformed

    private void fullNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullNameTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullNameTxtActionPerformed

    private void nameWithInitialsTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameWithInitialsTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameWithInitialsTxtActionPerformed

    private void telephoneTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telephoneTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telephoneTxtActionPerformed

    private void mobileTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobileTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobileTxtActionPerformed

    private void nicTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nicTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nicTxtActionPerformed

    private void emailTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTxtActionPerformed

    private void guardianTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardianTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guardianTxtActionPerformed

    private void guardianPhoneTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardianPhoneTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guardianPhoneTxtActionPerformed

    private void AddressTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressTxtActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
            SavedStatusNotice.getInstance().disposeMessage();
            WarningMessage.getInstance().disposeMessage();
            Message.getInstance().disposeMessage();
        
        String nameWithInitials=nameWithInitialsTxt.getText();
        String fullName = fullNameTxt.getText();
        String barcode = barcodeTxt.getText();
        String address = AddressTxt.getText();
        Date dob = dobTxt.getDate();
        String nic = nicTxt.getText();
        String mobile = mobileTxt.getText();
        String telephone = telephoneTxt.getText();
        String email = emailTxt.getText();
        String guardianName = guardianTxt.getText();
        String guardianPhoneNo = guardianPhoneTxt.getText();
        Connect con=new Connect();
        String interfaceHead=interfaceHeadLine.getText();
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dobString=sdf.format(dob.getTime());
        String registerDate=getCurrentDate();
        
        int rowCount = dtm.getRowCount();
        
        int cardType=1;
        if(fullCardBtn.isSelected()){
            cardType=1;
        }else if(halfCardBtn.isSelected()){
            cardType=2;
        }else if(freeCardBtn.isSelected()){
            cardType=3;
        }
        
        boolean a=nameWithInitials.isEmpty();
        boolean b=fullName.isEmpty();
        boolean c=barcode.isEmpty();
        boolean d=address.isEmpty();
        boolean e=nic.isEmpty();
        boolean f=guardianName.isEmpty();
        boolean g=mobile.isEmpty();
        boolean h=telephone.isEmpty();
        boolean i=(rowCount==0);
       
        
        
        if(isInvalidNIC(nic) && !e){
            
            WarningMessage.getInstance().showInterface("Invalid NIC number");
            
        }else if(isInvalidPhoneNo(mobile) && !g){
            
            WarningMessage.getInstance().showInterface("Invalid mobile number");
            
            
        }else if(isInvalidPhoneNo(telephone) && !h){
           
            WarningMessage.getInstance().showInterface("Invalid telephone number");
           
        }else if(isInvalidPhoneNo(guardianPhoneNo) && !guardianPhoneNo.isEmpty()){
            
            Message.getInstance().showInterface("Guardian phone number is","invalid");
            
        }else if(!email.isEmpty() && isInvalidEmail(email)){
           
            WarningMessage.getInstance().showInterface("Give valid email address");
            
            
        }else if(a && !b && !c && !d && !e && !f && (!g || !h) && !i){
            
            Message.getInstance().showInterface("Give the student name","with initials");
            
            
        }else if(!a && b && !c && !d && !e && !f && (!g || !h) && !i){
            
            WarningMessage.getInstance().showInterface("Give the fullname");
           
        }else if(a && b && c && d && e && f && g && h && i && email.isEmpty() && guardianPhoneNo.isEmpty()){
            
            Message.getInstance().showInterface("Firstly enter the information","of the student");
           
            
        }else if(!a && !b && c && !d && !e && !f && (!g || !h) && !i){
            
            WarningMessage.getInstance().showInterface("Give the barcode");
           
            
        }else if(!a && !b && !c && d && !e && !f && (!g || !h) && !i){
            
            WarningMessage.getInstance().showInterface("Give the address");
          
        }else if(!a && !b && !c && !d && e && !f && (!g || !h) && !i){
            
            WarningMessage.getInstance().showInterface("Give the NIC number");
           
        }else if(!a && !b && !c && !d && !e && f && (!g || !h) && !i){
           
            WarningMessage.getInstance().showInterface("Give the guardian name");
            
           
            
        }else if(!a && !b && !c && !d && !e && !f && g && h && !i){
           
            Message.getInstance().showInterface("At least give either mobile", "or telephone number");
            
       
        }else if(!a && !b && !c && !d && !e && !f && (!g || !h) && i){
             
            WarningMessage.getInstance().showInterface("Add the courses of study");
           
        }else if(a || b || c || d || e || f || (g && h) || i){
            
            WarningMessage.getInstance().showInterface("Complete the information");
            
           
        }else if(isInvalidDateOfBirth(dob)){
           
            WarningMessage.getInstance().showInterface("Reset the Date of birth");
            
            
        }else if(isDuplicateBarcode(barcode)&& interfaceHead.equals("Student Application")){
            
            
            Message.getInstance().showInterface("Barcode "+barcode, "is already issued");
           
        }else if(isAlreadyGivenNIC(nic)&& interfaceHead.equals("Student Application")){
           
            Message.getInstance().showInterface("NIC "+nic, "already exists");
           
            
        }else{
            if(interfaceHead.equals("Student Application")){
            String query="INSERT INTO student(barcode,name_with_initials,fullname,address,dob,email,nic,register_date,card_type_id) VALUES("
                    +"'"+barcode+"'"+","
                    +"'"+nameWithInitials+"'"+","
                    +"'"+fullName+"'"+","
                    +"'"+address+"'"+","
                    +"'"+dobString+"'"+","
                    +"'"+email+"'"+","
                    +"'"+nic+"'"+","
                    +"'"+registerDate+"'"+","
                    +cardType
                    +")";
            
            
            int genID=con.setQuery(query, null);
            
            for (int j = 0; j < rowCount; j++) {
                
               String query1="INSERT INTO student_has_course(student_id,course_id) VALUES("
                    +genID+","
                    +getCourseID(dtm.getValueAt(j, 1).toString(), dtm.getValueAt(j, 2).toString(), dtm.getValueAt(j, 3).toString())
                    +")"; 
                
                con.setQuery(query1, null);
            }
            
            String query2="INSERT INTO guardian(name,number,student_id) VALUES("
                    +"'"+guardianName+"'"+","
                    +"'"+guardianPhoneNo+"'"+","
                    +genID
                    +")"; 
            
                con.setQuery(query2, null);
                
                
               String query3="INSERT INTO phone_number(student_id,telephone_number,mobile_number) VALUES("
                    +genID+","
                    +"'"+telephone+"',"
                    +"'"+mobile+"'"
                    +")"; 
               
                con.setQuery(query3, null);
          
            
        SavedStatusNotice.getInstance().showInterface();
        
        
        }else{
                if(hasSameBarcode(barcode)){
                     Message.getInstance().showInterface("Barcode "+barcode, "is already issued");
                    
                }else if(hasSameNIC(nic)){
                     Message.getInstance().showInterface("NIC "+nic, "already exists");
                    
                }else{
                String query=" UPDATE student SET barcode='"+barcode+"',name_with_initials='"+nameWithInitials+"',"
                        + "fullname='"+fullName+"',address='"+address+"',dob='"+dobString+"',email='"+email+"',nic='"+nic+"',"
                        + "card_type_id="+cardType+" WHERE id="+studentID+"";
            
                con.setQuery(query, null);
                
                String deleteQuery="DELETE FROM student_has_course WHERE student_id="+studentID;
                con.setQuery(deleteQuery, null);
                
                
                
            for (int j = 0; j < rowCount; j++) {
                
               String query1="INSERT INTO student_has_course(student_id,course_id) VALUES("
                    +studentID+","
                    +getCourseID(dtm.getValueAt(j, 1).toString(), dtm.getValueAt(j, 2).toString(), dtm.getValueAt(j, 3).toString())
                    +")"; 
               
                con.setQuery(query1, null);
            }
            
            
            
            String query2="UPDATE guardian SET name='"+guardianName+"',number='"+guardianPhoneNo+"',student_id="+studentID
                    + " WHERE student_id="+studentID;
                 
                con.setQuery(query2, null);
                
                 
               String query3="UPDATE phone_number SET telephone_number='"+telephone+"',mobile_number='"+mobile
                       +"' WHERE student_id="+studentID;
                    
                con.setQuery(query3, null);
                
                SavedStatusNotice.getInstance().showInterface();
            }
            
                
            }
        }
        
    }//GEN-LAST:event_saveBtnActionPerformed

    private void coursesSelectorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursesSelectorBtnActionPerformed
        CoursesSelector.getInstance().showInterface();
        
    }//GEN-LAST:event_coursesSelectorBtnActionPerformed

    private void clearTxtBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearTxtBtnActionPerformed
        cleanApplication();
    }//GEN-LAST:event_clearTxtBtnActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        CoursesSelector cs = CoursesSelector.getInstance();
        if(cs.isVisible()){
           cs.dispose();
        }
        StudentRegistry sr=StudentRegistry.getInstance();
        if(sr.isVisible()){
           sr.dispose();
        }
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        CoursesSelector cs = CoursesSelector.getInstance();
        if(cs.isVisible()){
           cs.dispose();
        }
        Keyboard.getInstance().hideInterface();
         
        StudentRegistry.getInstance().editTabel(studentID);
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

    private void removeCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCourseBtnActionPerformed
        int rowCount = dtm.getRowCount();
        int selectedRowCount = courseTabel.getSelectedRowCount();
        if(rowCount==0){
            
        }else if(selectedRowCount==0){
            
            WarningMessage.getInstance().showInterface("Select to remove");
        }else{
            int selectedRows[]=new int[selectedRowCount];
        selectedRows = courseTabel.getSelectedRows();
        for (int i = selectedRowCount; i > 0; i--) {
            int row=selectedRows[i-1];
            dtm.removeRow(row);
            dtm.getDataVector().removeElement(row);
            dtm.fireTableDataChanged();
        }
        resetSerialNumbers();
        }
    }//GEN-LAST:event_removeCourseBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        CoursesSelector cs = CoursesSelector.getInstance();
        
        StudentRegistry.getInstance().editTabel(studentID);
        
        if(cs.isVisible()){
           cs.dispose();
        }
        this.dispose();

    }//GEN-LAST:event_backBtnActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
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
            java.util.logging.Logger.getLogger(NewApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewApplication().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddressTxt;
    private javax.swing.JButton backBtn;
    private javax.swing.JTextField barcodeTxt;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton clearTxtBtn;
    private javax.swing.JTable courseTabel;
    private javax.swing.JButton coursesSelectorBtn;
    private de.wannawork.jcalendar.JCalendarComboBox dobTxt;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JToggleButton freeCardBtn;
    private javax.swing.JToggleButton fullCardBtn;
    private javax.swing.JTextField fullNameTxt;
    private javax.swing.JTextField guardianPhoneTxt;
    private javax.swing.JTextField guardianTxt;
    private javax.swing.JToggleButton halfCardBtn;
    private javax.swing.JLabel interfaceHeadLine;
    private javax.swing.JLabel interfaceIcon;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton keyBoardBtn;
    private javax.swing.JTextField mobileTxt;
    private javax.swing.JTextField nameWithInitialsTxt;
    private javax.swing.JTextField nicTxt;
    private javax.swing.JButton removeCourseBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTextField telephoneTxt;
    // End of variables declaration//GEN-END:variables
}
