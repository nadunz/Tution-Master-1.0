/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notifications;

import AttendanceManagement.Attendance;
import coursesmanagement.CourseManager;
import coursesmanagement.Institute;
import java.awt.Dimension;
import java.awt.Toolkit;
import studentmanagement.StudentRegistry;

/**
 *
 * @author Nadun
 */
public class ConfirmDialog extends javax.swing.JFrame {

    /**
     * Creates new form ConfirmDialog
     */
    private static ConfirmDialog instance=new ConfirmDialog();
    private int messageType;
    public static final int INSTITUTE_DELETE=1 , COURSE_DELETE=2 , STUDENT_DELETE=3 ,TUTE_ISSUES=4;
    public int issuedStat; 
    
    public ConfirmDialog() {
        initComponents();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width=(int) dimension.getWidth();
        int height=(int) dimension.getHeight();
        this.setLocation((width/2)-250,(height/2)-114);
    }
    
    public static ConfirmDialog getInstance(){
        return instance;
    }
    
    public void showInterface(String messageLine1,String messageLine2,int type){
        this.messageType=type;
        
        instance.messageLabel.setText(messageLine1);
        messageLbl2.setText(messageLine2);
        setTopic(type);
        instance.setVisible(true);
    }
    public void showInterface(String messageLine1,String messageLine2,int type,int issuedSt){
        this.messageType=type;
        this.issuedStat = issuedSt;
        instance.messageLabel.setText(messageLine1);
        messageLbl2.setText(messageLine2);
        setTopic(type);
        instance.setVisible(true);
    }
    
    private void setTopic(int messageType){
        
        if(messageType==1){
          topic.setText("Delete Institute");
        }else if(messageType==2){
          topic.setText("Delete Course"); 
        }else if(messageType==3){
          topic.setText("Delete Student"); 
        }else if(messageType==4){
          topic.setText("Edit");   
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
        jSeparator1 = new javax.swing.JSeparator();
        topic = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel16 = new javax.swing.JPanel();
        yesBtn = new javax.swing.JButton();
        noBtn = new javax.swing.JButton();
        messageLbl2 = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(21, 21, 41));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 350));

        jSeparator1.setBackground(new java.awt.Color(36, 36, 64));
        jSeparator1.setForeground(new java.awt.Color(36, 36, 64));

        topic.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 24)); // NOI18N
        topic.setForeground(new java.awt.Color(153, 153, 153));
        topic.setText("Message");

        jSeparator2.setBackground(new java.awt.Color(36, 36, 64));
        jSeparator2.setForeground(new java.awt.Color(36, 36, 64));

        jPanel16.setBackground(new java.awt.Color(36, 36, 64));
        jPanel16.setLayout(new java.awt.GridLayout(1, 0, 2, 0));

        yesBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/yesBtnpng.png"))); // NOI18N
        yesBtn.setBorder(null);
        yesBtn.setFocusPainted(false);
        yesBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/yesBtnpng-selected.png"))); // NOI18N
        yesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yesBtnActionPerformed(evt);
            }
        });
        jPanel16.add(yesBtn);

        noBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/noBtn.png"))); // NOI18N
        noBtn.setBorder(null);
        noBtn.setFocusPainted(false);
        noBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/noBtn-selected.png"))); // NOI18N
        noBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noBtnActionPerformed(evt);
            }
        });
        jPanel16.add(noBtn);

        messageLbl2.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 16)); // NOI18N
        messageLbl2.setForeground(new java.awt.Color(153, 153, 153));

        messageLabel.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 16)); // NOI18N
        messageLabel.setForeground(new java.awt.Color(153, 153, 153));

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/CloseCircle.png"))); // NOI18N
        cancelBtn.setBorder(null);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/CloseCircle-selected.png"))); // NOI18N
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topic, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addComponent(messageLbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(topic)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messageLbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void noBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noBtnActionPerformed
       
        this.dispose();
    }//GEN-LAST:event_noBtnActionPerformed

    private void yesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesBtnActionPerformed
        this.dispose();
        switch(messageType){
            case 1:
                
                Institute.getInstance().deleteInstitute();
            break;  
                
            case 2:
                
                CourseManager .getInstance().deleteCourse();
            break;
                
            case 3:
                
                StudentRegistry.getInstance().deleteStudent();
            break; 
                
            case 4:
               
                Attendance.getInstance().updateTuteIssuedStatus(issuedStat);
                Attendance.getInstance().retrieveData();
            break;
                
            default:
                
            System.out.println("Invalid message type");
        }
        
    }//GEN-LAST:event_yesBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConfirmDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfirmDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfirmDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfirmDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfirmDialog().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel messageLbl2;
    private javax.swing.JButton noBtn;
    private javax.swing.JLabel topic;
    private javax.swing.JButton yesBtn;
    // End of variables declaration//GEN-END:variables
}
