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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import main.DragJFrame;
import main.Keyboard;
import notifications.Message;
import notifications.SavedStatusNotice;
import notifications.WarningMessage;

/**
 *
 * @author Nadun
 */
public class NewCourse extends javax.swing.JFrame {

    /**
     * Creates new form NewCourse
     */
    private DefaultTableModel dtm;
    private static NewCourse instance=new NewCourse();
    
    public NewCourse() {
        initComponents();
        
        dtm=(DefaultTableModel) courseTable.getModel();
//         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        this.setSize(screenSize);
            DragJFrame df=new DragJFrame(this, jPanel2);
        
    }
     public static NewCourse getInstance(){
        return instance;  
        
    }
     
     
     private void clearText(){
         courseNameTxt.setText("");
         instituteTxt.setText("");
         cityTxt.setText("");
         startDateCombobox.setDate(new Date());
         courseFeeTxt.setText("");
         
     }
    
    public void showInterface(){
        instance.setVisible(true);
        makeChangesAsDefaultInterface();
        clearText();
        retrieveData();
    }
    public void showInterface(String courseID){
        
        instance.setVisible(true);
        clearText();
        retrieveData();
        makeChangesAsEditor();
        selectRow(courseID);
        editText();
        
    }
    
    private void makeChangesAsEditor(){
        
        interfaceHead.setText("Courses Editor");
        interfaceIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/EditorIcon.png")));
        newCourseLabel.setText("Edit Course");
        backBtn.setEnabled(true);
        
    }
    
    private void makeChangesAsDefaultInterface(){
        
        interfaceHead.setText("New Course");
        interfaceIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/newCourse-icon.png")));
        newCourseLabel.setText("New Course");
        backBtn.setEnabled(false);
        
    }
    private void selectRow(String courseID){
        int row=0;
        int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               String course_id=dtm.getValueAt(i, 1).toString();
               if(course_id.equals(courseID)){
                  row=i; 
                  i=rCount-1; 
               }
                   
           }
           courseTable.setRowSelectionInterval(row, row);
        
    }
    
    private void editText(){
        int selectedRow = courseTable.getSelectedRow();
        String courseName=dtm.getValueAt(selectedRow, 2).toString();
        String institute=dtm.getValueAt(selectedRow, 3).toString();
        String city=dtm.getValueAt(selectedRow, 4).toString();
        String courseFee=dtm.getValueAt(selectedRow, 6).toString();
        String startingDate=dtm.getValueAt(selectedRow, 5).toString();
        
        courseNameTxt.setText(courseName);
        instituteTxt.setText(institute);
        cityTxt.setText(city);
        courseFeeTxt.setText(courseFee);
       
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=df.parse(startingDate);
            startDateCombobox.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(NewCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
            
       
        
    }

    public JTextField getCourseNameTxt() {
        return courseNameTxt;
    }

    public JTextField getCityTxt() {
        return cityTxt;
    }

    public JTextField getInstituteTxt() {
        return instituteTxt;
    }
    private String formatValue(double d){
            DecimalFormat df=new DecimalFormat("#0.00");
        return df.format(d);   
    }
    
    private String dateToString(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        
        return sdf.format(date);
        
    }
    
    private boolean isInvalidCourseFee(String feeString){
       boolean validity=false;
       if(!feeString.isEmpty()){
           try{
       double feeDouble=Double.parseDouble(feeString);
           }catch(NumberFormatException e){
               validity=true;
           }
       }
       return validity;
    }
    
    private int getInstituteID(String institute,String city){
        int ID=0;
        String query="SELECT id FROM institution WHERE city='"+city+"' AND name='"+institute+"' AND delete_status=0";
        Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            ID=r.getInt("id");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ID;
        
    }
    
    private void cleanTable(){
      dtm.getDataVector().removeAllElements();
      dtm.fireTableDataChanged();
    }
    
    private void addCourse(int id,String courseName,String institute,String city,String startingDate,String fee){
        String serialNo=Integer.toString(dtm.getRowCount()+1);
        dtm.addRow(new Object[]{serialNo,id,courseName,institute,city,startingDate,fee});
    }
    
    private void resetSerialNumbers(){
           int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               dtm.setValueAt(i+1, i, 0);
               
           }   
    }
    
   
    public boolean isEarlyAddedCourse(String course,String institute,String city){
          boolean wheatherAdd = false;
          int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               String tableCourseName=dtm.getValueAt(i, 2).toString();
               String tableInstitute=dtm.getValueAt(i, 3).toString();
               String tableCity=dtm.getValueAt(i, 4).toString();
               if(tableInstitute.equalsIgnoreCase(institute) && tableCity.equalsIgnoreCase(city)
                   && tableCourseName.equalsIgnoreCase(course)){
                   wheatherAdd=true;
                   i=rCount-1;
               } 
           }
           return wheatherAdd;  
    }
    
    public boolean hasSameCourse(int selectedRow,String course,String institute,String city){
          boolean wheatherHave = false;
          int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               if(i==selectedRow){
                   continue;
               }else{
               String tableCourseName=dtm.getValueAt(i, 2).toString();
               String tableInstitute=dtm.getValueAt(i, 3).toString();
               String tableCity=dtm.getValueAt(i, 4).toString();
               
               if(tableInstitute.equalsIgnoreCase(institute) && tableCity.equalsIgnoreCase(city)
                   && tableCourseName.equalsIgnoreCase(course)){
                   wheatherHave=true;
                   i=rCount-1;
               } 
               }
           }
           return wheatherHave;  
    }
    
     public boolean isModified(String course,String institute,String city,String fee,String startDate){
          boolean wheatherModified = true;
          int rCount=dtm.getRowCount();
          String formatValue_of_fee=formatValue(Double.parseDouble(fee));
           for (int i = 0; i < rCount; i++) {
               String tableCourseName=dtm.getValueAt(i, 2).toString();
               String tableInstitute=dtm.getValueAt(i, 3).toString();
               String tableCity=dtm.getValueAt(i, 4).toString();
               String tableFee=dtm.getValueAt(i, 6).toString();
               String date=dtm.getValueAt(i, 5).toString();
               
               
               
               if(tableInstitute.equals(institute) && tableCity.equals(city)
                   && tableCourseName.equals(course) && tableFee.equals(formatValue_of_fee)
                       && date.equals(startDate)){
                   wheatherModified=false;
                   i=rCount-1;
               } 
           }
           return wheatherModified;  
    }
    
    
    private void retrieveData(){
      cleanTable();
      String query="SELECT inst.*,cour.* FROM institution inst INNER JOIN course cour ON inst.id="
                + "cour.institution_id WHERE inst.delete_status=0 AND cour.delete_status=0";
      Connect c=new Connect();
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            int ID=r.getInt("cour.id");
            String courseName=r.getString("cour.name");
            String startingDate=r.getString("cour.start_date");
            Double courseFee=r.getDouble("cour.course_fee");
            String institute= r.getString("inst.name");
            String city= r.getString("inst.city");
            addCourse(ID, courseName, institute, city, startingDate, formatValue(courseFee));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewCourse.class.getName()).log(Level.SEVERE, null, ex);
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
        interfaceHead = new javax.swing.JLabel();
        interfaceIcon = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        keyBoardBtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        backBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        courseNameTxt = new javax.swing.JTextField();
        instituteTxt = new javax.swing.JTextField();
        courseFeeTxt = new javax.swing.JTextField();
        instituteSelecterBtn = new javax.swing.JButton();
        templateBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        clearTextBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        courseTable = new javax.swing.JTable();
        cityTxt = new javax.swing.JTextField();
        startDateCombobox = new de.wannawork.jcalendar.JCalendarComboBox();
        newCourseLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Course");
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(7, 1, 36));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(24, 25, 54));

        interfaceHead.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 34)); // NOI18N
        interfaceHead.setForeground(new java.awt.Color(153, 153, 153));
        interfaceHead.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        interfaceHead.setText("New Course");

        interfaceIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/newCourse-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(interfaceIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(interfaceHead, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(interfaceHead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(interfaceIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(24, 25, 54));

        jPanel3.setBackground(new java.awt.Color(24, 25, 54));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        jButton5.setFocusPainted(false);
        jButton5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home-selected.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5);

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
        jPanel3.add(keyBoardBtn);

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
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Starting Date");

        jLabel8.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Institute");

        jLabel9.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Course Fee");

        jLabel12.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Course Name");

        courseNameTxt.setBackground(new java.awt.Color(7, 1, 36));
        courseNameTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        courseNameTxt.setForeground(new java.awt.Color(102, 102, 102));
        courseNameTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        courseNameTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        courseNameTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        courseNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseNameTxtActionPerformed(evt);
            }
        });

        instituteTxt.setEditable(false);
        instituteTxt.setBackground(new java.awt.Color(7, 1, 36));
        instituteTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        instituteTxt.setForeground(new java.awt.Color(102, 102, 102));
        instituteTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        instituteTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        instituteTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        instituteTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instituteTxtActionPerformed(evt);
            }
        });

        courseFeeTxt.setBackground(new java.awt.Color(7, 1, 36));
        courseFeeTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        courseFeeTxt.setForeground(new java.awt.Color(102, 102, 102));
        courseFeeTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        courseFeeTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        courseFeeTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        courseFeeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseFeeTxtActionPerformed(evt);
            }
        });

        instituteSelecterBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/addCourses.png"))); // NOI18N
        instituteSelecterBtn.setBorder(null);
        instituteSelecterBtn.setFocusPainted(false);
        instituteSelecterBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/addCourses-selected.png"))); // NOI18N
        instituteSelecterBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instituteSelecterBtnActionPerformed(evt);
            }
        });

        templateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Help.png"))); // NOI18N
        templateBtn.setBorder(null);
        templateBtn.setFocusPainted(false);
        templateBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Help-selected.png"))); // NOI18N
        templateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                templateBtnActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(7, 1, 36));
        jPanel5.setLayout(new java.awt.GridLayout(0, 1, 0, 2));

        clearTextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/clearAll.png"))); // NOI18N
        clearTextBtn.setToolTipText("clean texts");
        clearTextBtn.setFocusPainted(false);
        clearTextBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/clearAll-selected.png"))); // NOI18N
        clearTextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearTextBtnActionPerformed(evt);
            }
        });
        jPanel5.add(clearTextBtn);

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        saveBtn.setToolTipText("save");
        saveBtn.setFocusPainted(false);
        saveBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save-selected.png"))); // NOI18N
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        jPanel5.add(saveBtn);

        jScrollPane1.setBackground(new java.awt.Color(7, 1, 36));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));

        courseTable.setBackground(new java.awt.Color(7, 1, 36));
        courseTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        courseTable.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        courseTable.setForeground(new java.awt.Color(102, 102, 102));
        courseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Serial No", "Course ID", "Course Name", "Institute", "City / Town", "Starting Date", "Course Fee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        courseTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        courseTable.getTableHeader().setReorderingAllowed(false);
        courseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                courseTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(courseTable);
        if (courseTable.getColumnModel().getColumnCount() > 0) {
            courseTable.getColumnModel().getColumn(0).setResizable(false);
            courseTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            courseTable.getColumnModel().getColumn(1).setResizable(false);
            courseTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            courseTable.getColumnModel().getColumn(2).setResizable(false);
            courseTable.getColumnModel().getColumn(2).setPreferredWidth(350);
            courseTable.getColumnModel().getColumn(3).setResizable(false);
            courseTable.getColumnModel().getColumn(3).setPreferredWidth(200);
            courseTable.getColumnModel().getColumn(4).setResizable(false);
            courseTable.getColumnModel().getColumn(4).setPreferredWidth(200);
            courseTable.getColumnModel().getColumn(5).setResizable(false);
            courseTable.getColumnModel().getColumn(5).setPreferredWidth(150);
            courseTable.getColumnModel().getColumn(6).setResizable(false);
            courseTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        }

        cityTxt.setEditable(false);
        cityTxt.setBackground(new java.awt.Color(7, 1, 36));
        cityTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        cityTxt.setForeground(new java.awt.Color(102, 102, 102));
        cityTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        cityTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        cityTxt.setCaretColor(new java.awt.Color(153, 153, 153));
        cityTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityTxtActionPerformed(evt);
            }
        });

        startDateCombobox.setBackground(new java.awt.Color(7, 1, 36));
        startDateCombobox.setForeground(new java.awt.Color(102, 102, 102));
        startDateCombobox.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 16)); // NOI18N

        newCourseLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 22)); // NOI18N
        newCourseLabel.setForeground(new java.awt.Color(153, 153, 153));
        newCourseLabel.setText("New Course");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(courseFeeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startDateCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(instituteSelecterBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(newCourseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(courseNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(templateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)))
                .addGap(20, 20, 20)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(newCourseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(courseNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startDateCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(courseFeeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(templateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(instituteSelecterBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(154, 154, 154)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        CourseNameTemplate.getInstance().dispose();
        CourseManager cm=CourseManager.getInstance();
        
        if(cm.isVisible()){
           cm.dispose();
        }
        InstituteSelector ins = InstituteSelector.getInstance();
        CourseNameTemplate temp = CourseNameTemplate.getInstance();
        if(ins.isVisible()){
           ins.dispose();
        }
       
        if(temp.isVisible()){
           temp.dispose();
        }
        this.dispose();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void courseNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseNameTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseNameTxtActionPerformed

    private void instituteTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instituteTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_instituteTxtActionPerformed

    private void courseFeeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseFeeTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseFeeTxtActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        String course = courseNameTxt.getText();
        String institute = instituteTxt.getText();
        String city=cityTxt.getText();
        String fee=courseFeeTxt.getText();
        String date=dateToString(startDateCombobox.getDate());
        if(!course.isEmpty() && !institute.isEmpty() && fee.isEmpty()){
            
            Message.getInstance().showInterface("Course fee cannot be","empty");
            
            
        }else if(!course.isEmpty() && institute.isEmpty() && fee.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Give the institute & fee");
            
        }else if(course.isEmpty() && !institute.isEmpty() && fee.isEmpty()){
          
            WarningMessage.getInstance().showInterface("Give the course & fee");
            
            
        }else if(course.isEmpty() && institute.isEmpty() && fee.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Nothing to save");
            
        }else if(isInvalidCourseFee(fee) || Double.parseDouble(fee)<0){
            
            WarningMessage.getInstance().showInterface("Give valid course fee");
            courseFeeTxt.setText("");
            
        }else if(!course.isEmpty() && institute.isEmpty() && !fee.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Give the institute");
            
        }else if(course.isEmpty() && !institute.isEmpty() && !fee.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Give the course name");
            
        }else if(course.isEmpty() && institute.isEmpty() && !fee.isEmpty()){
            
            Message.getInstance().showInterface("Give the course & ","institute");
            
            
        }else if(Double.parseDouble(fee)==0){
            
            Message.getInstance().showInterface("Course fee cannot be","zero");
            
        }else if(course.isEmpty() || institute.isEmpty() || fee.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Nothing to save");
            
        }else if(isEarlyAddedCourse(course, institute, city)&& interfaceHead.getText().equalsIgnoreCase("New Course")){
           
            WarningMessage.getInstance().showInterface("This course already exists");
            
           
        }else{
            Connect con=new Connect();
            if(interfaceHead.getText().equalsIgnoreCase("New Course")){
            
            String query="INSERT INTO course(name,start_date,course_fee,institution_id) VALUES("
                    +"'"+course+"'"+","
                    +"'"+date+"'"+","
                    +"'"+fee+"'"+","
                    +"'"+getInstituteID(institute, city)+"'"
                    +")";
            
            
            con.setQuery(query, null);
            retrieveData();
            SavedStatusNotice.getInstance().showInterface();
            resetSerialNumbers();
            }else{
                    
                if(hasSameCourse(courseTable.getSelectedRow(),course, institute, city)){
                    Message.getInstance().showInterface("There is already a course", "with the same name");
                    
                }else if(!isModified(course, institute, city, fee, date)){
                    Message.getInstance().showInterface("The course has not", "been modified");
                    
                }else{
                int selectedRow = courseTable.getSelectedRow();
                String courseID=dtm.getValueAt(selectedRow, 1).toString();
                
            String query="UPDATE course SET name='"+course+"',start_date='"+date+"',course_fee='"+fee+"',institution_id="+getInstituteID(institute, city)
                    + " WHERE id="+courseID;
            con.setQuery(query, null);
            SavedStatusNotice.getInstance().showInterface();
            retrieveData();
             clearText();
                }
            }
            
            
        
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void instituteSelecterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instituteSelecterBtnActionPerformed
        InstituteSelector.getInstance().showInterface();
    }//GEN-LAST:event_instituteSelecterBtnActionPerformed

    private void templateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_templateBtnActionPerformed
       CourseNameTemplate.getInstance().showInterface();
    }//GEN-LAST:event_templateBtnActionPerformed

    private void cityTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cityTxtActionPerformed

    private void clearTextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearTextBtnActionPerformed
        clearText();
    }//GEN-LAST:event_clearTextBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        
        InstituteSelector ins = InstituteSelector.getInstance();
        CourseNameTemplate temp = CourseNameTemplate.getInstance();
        if(ins.isVisible()){
           ins.dispose();
        }
       
        if(temp.isVisible()){
           temp.dispose();
        }
        CourseManager.getInstance().searchCourse();
       
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

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        Keyboard.getInstance().hideInterface();
        CourseManager.getInstance().searchCourse();
        CourseNameTemplate.getInstance().dispose();
        
        this.dispose();
       

    }//GEN-LAST:event_backBtnActionPerformed

    private void courseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courseTableMouseClicked
        editText();
    }//GEN-LAST:event_courseTableMouseClicked

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
            java.util.logging.Logger.getLogger(NewCourse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewCourse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewCourse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewCourse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewCourse().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField cityTxt;
    private javax.swing.JButton clearTextBtn;
    private javax.swing.JTextField courseFeeTxt;
    private javax.swing.JTextField courseNameTxt;
    private javax.swing.JTable courseTable;
    private javax.swing.JButton instituteSelecterBtn;
    private javax.swing.JTextField instituteTxt;
    private javax.swing.JLabel interfaceHead;
    private javax.swing.JLabel interfaceIcon;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel newCourseLabel;
    private javax.swing.JButton saveBtn;
    private de.wannawork.jcalendar.JCalendarComboBox startDateCombobox;
    private javax.swing.JButton templateBtn;
    // End of variables declaration//GEN-END:variables
}
