/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceManagement;

import coursesmanagement.PaymentManager;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Nadun
 */
public class GoTo extends javax.swing.JFrame {

    /**
     * Creates new form StudentMoreInfo
     */
    
    public static final int ATTENDANCE=1,PAYMENT_MANAGER=2;
    private int type, currentYear, currentMonth;
    private DefaultComboBoxModel dcm1,dcm2;
    private static GoTo instance=new GoTo();
    
    public GoTo() {
        
        initComponents();
        
        dcm1=(DefaultComboBoxModel) yearCombo.getModel();
        dcm2=(DefaultComboBoxModel) monthCombo.getModel();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width=(int) dimension.getWidth();
        int height=(int) dimension.getHeight();
        this.setLocation((width/2)-174,(height/2)-99);
        
        
    }
    
    
     public static GoTo getInstance(){
        return instance;  
    }
    
     
     
    public void showInterface(int detailsType){
        
        this.type=detailsType;
        getCurrentMonth();
        instance.setVisible(true);
        setRecentYears();
        addMonthToComboBox(currentYear);
        setDefaultComboBox();
    }
    
     private void setDefaultComboBox(){
         
       yearCombo.setSelectedItem(currentYear);
       monthCombo.setSelectedIndex(currentMonth-1);
       
    }
     private void setRecentYears(){
         
        dcm1.removeAllElements();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
        String year=sdf.format(new Date());
        int yearInt=Integer.parseInt(year);
        for (int i = 0; i < 6; i++) {
            yearCombo.addItem(yearInt-i);
        }
     }
     
     private void getCurrentMonth(){
         
         Calendar c=new GregorianCalendar();
         
         currentYear=c.get(Calendar.YEAR);
         currentMonth=(c.get(Calendar.MONTH)+1);
     }
     
     private void addMonthToComboBox(int selectedYear){
        
         dcm2.removeAllElements();
         
         if(selectedYear==currentYear){
             
             switch(1){
                 
                   case 1: 
                       
                    dcm2.addElement("January");
                       if(currentMonth==1)
                           break;
                   case 2:
                       
                    dcm2.addElement("February"); 
                       if(currentMonth==2)
                           break;
                   case 3:
          
                    dcm2.addElement("March");  
                       if(currentMonth==3)
                           break;
                   case 4:
           
                    dcm2.addElement("April"); 
                       if(currentMonth==4)
                           break;
                   case 5:
           
                    dcm2.addElement("May");
                       if(currentMonth==5)
                           break;
                   case 6:
            
                    dcm2.addElement("June");
                       if(currentMonth==6)
                           break;
                   case 7:
           
                    dcm2.addElement("July"); 
                       if(currentMonth==7)
                           break;
                   case 8:
          
                    dcm2.addElement("August");
                       if(currentMonth==8)
                           break;
                   case 9:
          
                    dcm2.addElement("September");
                       if(currentMonth==9)
                           break;
                   case 10:
           
                    dcm2.addElement("October");
                       if(currentMonth==10)
                           break;
                   case 11:
         
                    dcm2.addElement("November");  
                       if(currentMonth==11)
                           break;
                   case 12 :
                  
                    dcm2.addElement("December");    
             }
             
         }else{
           dcm2.addElement("January");
           dcm2.addElement("February");
           dcm2.addElement("March");
           dcm2.addElement("April");
           dcm2.addElement("May");
           dcm2.addElement("June");
           dcm2.addElement("July");
           dcm2.addElement("August");
           dcm2.addElement("September");
           dcm2.addElement("October");
           dcm2.addElement("November");
           dcm2.addElement("December");
           
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        okBtn = new javax.swing.JButton();
        cancelBtn1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        yearCombo = new javax.swing.JComboBox();
        monthCombo = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Student Attendance");
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(21, 21, 41));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 350));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("Go To");

        jPanel2.setBackground(new java.awt.Color(21, 21, 41));
        jPanel2.setLayout(new java.awt.GridLayout());

        okBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/okCircle.png"))); // NOI18N
        okBtn.setBorder(null);
        okBtn.setBorderPainted(false);
        okBtn.setFocusPainted(false);
        okBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/okCircleSelected.png"))); // NOI18N
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });
        jPanel2.add(okBtn);

        cancelBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/CloseCircle.png"))); // NOI18N
        cancelBtn1.setBorder(null);
        cancelBtn1.setBorderPainted(false);
        cancelBtn1.setFocusPainted(false);
        cancelBtn1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/CloseCircle-selected.png"))); // NOI18N
        cancelBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtn1ActionPerformed(evt);
            }
        });
        jPanel2.add(cancelBtn1);

        jSeparator1.setBackground(new java.awt.Color(36, 36, 64));
        jSeparator1.setForeground(new java.awt.Color(36, 36, 64));

        jLabel11.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 19)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Year");

        jLabel13.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 19)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Month");

        yearCombo.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        yearCombo.setForeground(new java.awt.Color(102, 102, 102));
        yearCombo.setBorder(null);
        yearCombo.setFocusable(false);
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

        monthCombo.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        monthCombo.setForeground(new java.awt.Color(102, 102, 102));
        monthCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", " " }));
        monthCombo.setBorder(null);
        monthCombo.setFocusable(false);
        monthCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel11))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yearCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 75, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearCombo))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthCombo))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed

        String year=yearCombo.getSelectedItem().toString();
        String month=Integer.toString(monthCombo.getSelectedIndex()+1);
        
        switch(type){
            case 1:
                
                Attendance.getInstance().goTo(Attendance.ANYWHERE, year, month);
                this.dispose();
                break;
            case 2:
                
                PaymentManager.getInstance().goTo(PaymentManager.ANYWHERE, year, month);
                this.dispose();
                
                
        }
        

    }//GEN-LAST:event_okBtnActionPerformed

    private void cancelBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtn1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtn1ActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked

    }//GEN-LAST:event_jPanel1MouseClicked

    private void yearComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearComboActionPerformed

    private void monthComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthComboActionPerformed

    private void yearComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_yearComboItemStateChanged
       
        try {
          int selectedYear=Integer.parseInt(dcm1.getSelectedItem().toString());
          addMonthToComboBox(selectedYear);
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_yearComboItemStateChanged

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
            java.util.logging.Logger.getLogger(GoTo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GoTo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GoTo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GoTo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GoTo().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox monthCombo;
    private javax.swing.JButton okBtn;
    private javax.swing.JComboBox yearCombo;
    // End of variables declaration//GEN-END:variables
}
