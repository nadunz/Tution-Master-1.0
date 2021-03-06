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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
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

public class Institute extends javax.swing.JFrame {

    /**
     * Creates new form Institute
     */
    private int selectedRowCount;
    private int[] selectedRows;
    private DefaultTableModel dtm;
    private static Institute instance=new Institute();
    
    public Institute() {
        initComponents();
//         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        this.setSize(screenSize);
        dtm=(DefaultTableModel) instituteDetailsTable.getModel();
        DragJFrame df=new DragJFrame(this, jPanel2);
    }
    
     public static Institute getInstance(){
        return instance;
    }
    
    public void showInterface(){
        instance.setVisible(true);
        retrieveData();
    }
    
   
    public JButton getSaveBtn() {
        return saveBtn;
    }
    
    private void cleanTable(){
      dtm.getDataVector().removeAllElements();
      dtm.fireTableDataChanged();
    }
    
    private void addInstitute(int id,String institute,String city){
        String serialNo=Integer.toString(dtm.getRowCount()+1);
        dtm.addRow(new Object[]{serialNo,id,institute,city});
    }
    
    private void resetSerialNumbers(){
           int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               dtm.setValueAt(i+1, i, 0);
               
           }   
    }
    
    public void deleteInstitute(){
        Connect con=new Connect();
        for (int i = 0; i < selectedRowCount; i++) {
            int row=selectedRows[i];
            String id=dtm.getValueAt(row, 1).toString();
           
            String query="UPDATE institution SET delete_status=1 WHERE id="+id;
            String query1="UPDATE course SET delete_status=1 WHERE institution_id="+id;
            con.setQuery(query, null);
            con.setQuery(query1, null);
            
        }
         String query2="UPDATE student SET delete_status=1 WHERE id NOT IN (SELECT shc.student_id FROM student_has_course shc INNER "
                    + "JOIN course cour ON shc.course_id=cour.id INNER JOIN institution inst ON cour.institution_id=inst.id"
                    +" WHERE cour.delete_status=0 AND inst.delete_status=0)";
             con.setQuery(query2, null);
        
        if(selectedRowCount==1){
                
              WarningMessage.getInstance().showInterface("1 institute deleted");
                
            }else{
              
              WarningMessage.getInstance().showInterface(selectedRowCount+" institutes deleted");  
            }
        retrieveData();
        resetSerialNumbers();

    }
    
    public boolean isEarlyAddedInstitute(String institute,String city){
          boolean wheatherAdd = false;
          int rCount=dtm.getRowCount();
           for (int i = 0; i < rCount; i++) {
               String tableInstitute=dtm.getValueAt(i, 2).toString();
               String tableCity=dtm.getValueAt(i, 3).toString();
               if(tableInstitute.equalsIgnoreCase(institute) && tableCity.equalsIgnoreCase(city)){
                   wheatherAdd=true;
                   i=rCount-1;
               } 
           }
           return wheatherAdd;  
    }
    private void clearText(){
        instituteTxt.setText("");
        cityTxt.setText("");
        
    }
    
    public void retrieveData(){
      cleanTable();
      String query="SELECT * FROM institution WHERE delete_status=0";
      Connect c=new Connect();
      
        ResultSet r=c.getQuery(query);
        try {
            while(r.next()){
            int ID=r.getInt("id");
            String institute=r.getString("name");
            String city=r.getString("city");
            addInstitute(ID, institute, city);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Institute.class.getName()).log(Level.SEVERE, null, ex);
           
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
        jPanel5 = new javax.swing.JPanel();
        mainBtn = new javax.swing.JButton();
        keyBoardBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        instituteDetailsTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        selectAllBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        label1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        instituteTxt = new javax.swing.JTextField();
        cityTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(7, 1, 36));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(24, 25, 54));

        interfaceHead.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 35)); // NOI18N
        interfaceHead.setForeground(new java.awt.Color(153, 153, 153));
        interfaceHead.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        interfaceHead.setText("Institute");

        interfaceIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/institute-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(interfaceIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(interfaceHead, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(interfaceHead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(interfaceIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(24, 25, 54));

        jPanel5.setBackground(new java.awt.Color(24, 25, 54));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        mainBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mainBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        mainBtn.setFocusPainted(false);
        mainBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home-selected.png"))); // NOI18N
        mainBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainBtnActionPerformed(evt);
            }
        });
        jPanel5.add(mainBtn);

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
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane2.setBackground(new java.awt.Color(7, 1, 36));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(25, 24, 54)));

        instituteDetailsTable.setBackground(new java.awt.Color(7, 1, 36));
        instituteDetailsTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(24, 25, 54)));
        instituteDetailsTable.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        instituteDetailsTable.setForeground(new java.awt.Color(102, 102, 102));
        instituteDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Serial Number", "Institute ID", "Institute Name", "City/Town"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        instituteDetailsTable.setFocusable(false);
        instituteDetailsTable.setGridColor(new java.awt.Color(24, 25, 54));
        instituteDetailsTable.setRowHeight(30);
        instituteDetailsTable.setSelectionBackground(new java.awt.Color(24, 25, 54));
        instituteDetailsTable.setSelectionForeground(new java.awt.Color(153, 153, 153));
        instituteDetailsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(instituteDetailsTable);
        if (instituteDetailsTable.getColumnModel().getColumnCount() > 0) {
            instituteDetailsTable.getColumnModel().getColumn(0).setResizable(false);
            instituteDetailsTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            instituteDetailsTable.getColumnModel().getColumn(1).setResizable(false);
            instituteDetailsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            instituteDetailsTable.getColumnModel().getColumn(2).setPreferredWidth(200);
            instituteDetailsTable.getColumnModel().getColumn(3).setResizable(false);
            instituteDetailsTable.getColumnModel().getColumn(3).setPreferredWidth(250);
        }

        jPanel6.setBackground(new java.awt.Color(7, 1, 36));
        jPanel6.setLayout(new java.awt.GridLayout(0, 1, 0, 3));

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

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        saveBtn.setToolTipText("save");
        saveBtn.setBorder(null);
        saveBtn.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save-selected.png"))); // NOI18N
        saveBtn.setFocusPainted(false);
        saveBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save-selected.png"))); // NOI18N
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        jPanel6.add(saveBtn);

        label1.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 24)); // NOI18N
        label1.setForeground(new java.awt.Color(153, 153, 153));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label1.setText("New Institute");

        jLabel11.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Institute Name");

        jLabel13.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("City / Town");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(instituteTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
       
        int rowCount = dtm.getRowCount();
        int selectedRCount = instituteDetailsTable.getSelectedRowCount();
        
        if(rowCount==0){
            
            
        }else if(selectedRCount==0){
            WarningMessage.getInstance().showInterface("Select to edit");
            
        }else if(selectedRCount>1){
            WarningMessage.getInstance().showInterface("Select only one to edit");
            
        }else{
            int row=instituteDetailsTable.getSelectedRow();
            String ins_id=dtm.getValueAt(row, 1).toString();
            String ins_name=dtm.getValueAt(row, 2).toString();
            String city=dtm.getValueAt(row, 3).toString();
            
            InstituteEditor.getInstance().showInterface(ins_id, ins_name, city);
            
        }
        
    }//GEN-LAST:event_editBtnActionPerformed

    private void instituteTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instituteTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_instituteTxtActionPerformed

    private void cityTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cityTxtActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        String institute=instituteTxt.getText();
        String city=cityTxt.getText();
        
        if(institute.isEmpty() && city.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Nothing to save");
            
        }else if(institute.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Give the institute");
           
        }else if(city.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Please enter the city");
           
        }else if(isEarlyAddedInstitute(institute, city)){
            
            Message.getInstance().showInterface(institute+" "+city,"already exists");
            
        }else{
            String query="INSERT INTO institution(city,name) VALUES("
                    +"'"+city+"'"+","
                    +"'"+institute+"'"
                    +")";
            
            Connect con=new Connect();
            con.setQuery(query, null);
            
            SavedStatusNotice.getInstance().showInterface();
            retrieveData();
            clearText();
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
            
            selectedRowCount = instituteDetailsTable.getSelectedRowCount();
            selectedRows=new int[selectedRowCount];
            selectedRows=instituteDetailsTable.getSelectedRows();
        
            if(dtm.getRowCount()==0){
                
             WarningMessage.getInstance().showInterface("Nothing to delete");   
                
            }else if(selectedRowCount==0){
               
            WarningMessage.getInstance().showInterface("Select to delete");
             
            }else{
                
                if(selectedRowCount==1){
                   
                ConfirmDialog.getInstance().showInterface("Are you sure you want to delete this", "selected institute ?", ConfirmDialog.INSTITUTE_DELETE);
              
                }else{
                ConfirmDialog.getInstance().showInterface("Are you sure you want to delete these", selectedRowCount+" institutes ?", ConfirmDialog.INSTITUTE_DELETE);   
                   
               }
           
              
            }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void mainBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainBtnActionPerformed
        
        SavedStatusNotice.getInstance().disposeMessage();
        WarningMessage.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        
        Keyboard.getInstance().hideInterface();
        InstituteEditor.getInstance().dispose();
        
        this.dispose();
    }//GEN-LAST:event_mainBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        
        WarningMessage.getInstance().disposeMessage();
        SavedStatusNotice.getInstance().disposeMessage();
        Message.getInstance().disposeMessage();
        
        InstituteEditor.getInstance().dispose();
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

    private void selectAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllBtnActionPerformed
        
        int rowCount = dtm.getRowCount();
        int selectedRowC = instituteDetailsTable.getSelectedRowCount();
        if(rowCount>0){
        if(rowCount==selectedRowC){
           instituteDetailsTable.setRowSelectionInterval(0, 0);
        }else{
          instituteDetailsTable.selectAll();  
        }
        }
    }//GEN-LAST:event_selectAllBtnActionPerformed

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
            java.util.logging.Logger.getLogger(Institute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Institute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Institute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Institute.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Institute().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField cityTxt;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTable instituteDetailsTable;
    private javax.swing.JTextField instituteTxt;
    private javax.swing.JLabel interfaceHead;
    private javax.swing.JLabel interfaceIcon;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton keyBoardBtn;
    private javax.swing.JLabel label1;
    private javax.swing.JButton mainBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton selectAllBtn;
    // End of variables declaration//GEN-END:variables
}
