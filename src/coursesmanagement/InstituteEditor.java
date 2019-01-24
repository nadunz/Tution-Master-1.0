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
import java.util.logging.Level;
import java.util.logging.Logger;
import main.DragJFrame;
import notifications.Message;
import notifications.WarningMessage;

/**
 *
 * @author Nadun
 */
public class InstituteEditor extends javax.swing.JFrame {

    /**
     * Creates new form InstituteEditor
     */
    private static InstituteEditor instance=new InstituteEditor();
    private String instituteID;
    private String institute;
    private String city;
    
    public InstituteEditor() {
        initComponents();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width=(int) dimension.getWidth();
        int height=(int) dimension.getHeight();
        this.setLocation((width/2)-247,(height/2)-121);
        DragJFrame df=new DragJFrame(this, jPanel1);
    }
    
    public static InstituteEditor getInstance(){
        return instance;
    }
    
    public void showInterface(String institute_id,String name,String cityOrTown){
        clearText();
        this.instituteID=institute_id;
        institute=name;
        city=cityOrTown;
        
        instance.setVisible(true);
        instituteTxt.setText(name);
        cityTxt.setText(city);
        
    }
    
    public boolean hasSameInstitute(String institute,String city){
          boolean have = false;
          String query="SELECT id FROM institution WHERE name='"+institute+"' AND city='"+city+"'";
          Connect con=new Connect();
          ResultSet r=con.getQuery(query);
        try {
            while(r.next()){
                String id=r.getInt("id")+"";
               if(id.equals(instituteID)){
                   continue;
               }else{
                 have=true;  
               }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstituteEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
           
           return have;  
    }
    
    
    private void clearText(){
        instituteTxt.setText("");
        cityTxt.setText("");
    }
    
    
     public boolean isModified(String insti,String cityOrTown){
         
          boolean modified= true;
          
               if(institute.equals(insti) && city.equals(cityOrTown)){
                   modified=false;
                   
               } 
          
           return modified;  
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
        saveBtn = new javax.swing.JButton();
        cancelBtn1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        instituteTxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cityTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        jLabel1.setText("Institute Editor");

        jPanel2.setBackground(new java.awt.Color(21, 21, 41));
        jPanel2.setLayout(new java.awt.GridLayout());

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/okCircle.png"))); // NOI18N
        saveBtn.setBorder(null);
        saveBtn.setBorderPainted(false);
        saveBtn.setFocusPainted(false);
        saveBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/okCircleSelected.png"))); // NOI18N
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        jPanel2.add(saveBtn);

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
        jLabel11.setText("Institute Name");

        instituteTxt.setBackground(new java.awt.Color(21, 21, 41));
        instituteTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        instituteTxt.setForeground(new java.awt.Color(153, 153, 153));
        instituteTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        instituteTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(36, 36, 64)));
        instituteTxt.setCaretColor(new java.awt.Color(153, 153, 153));

        jLabel13.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 19)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("City / Town");

        cityTxt.setBackground(new java.awt.Color(21, 21, 41));
        cityTxt.setFont(new java.awt.Font("Swis721 LtEx BT", 0, 17)); // NOI18N
        cityTxt.setForeground(new java.awt.Color(153, 153, 153));
        cityTxt.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        cityTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(36, 36, 64)));
        cityTxt.setCaretColor(new java.awt.Color(153, 153, 153));

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 25, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35))))
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
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(instituteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed

        String institution=instituteTxt.getText();
        String cityName=cityTxt.getText();
        
        if(institute.isEmpty() && city.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Nothing to save");
            
        }else if(institute.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Give the institute");
           
        }else if(city.isEmpty()){
            
            WarningMessage.getInstance().showInterface("Please enter the city");
           
        }else if(hasSameInstitute(institution, cityName)){
            
            Message.getInstance().showInterface("There is already an institute","with the same name & city");
            
        }else if(!isModified(institution, cityName)){
            
            Message.getInstance().showInterface("The institute has not", "been modified");
            
        }else{
            
            String query="UPDATE institution SET city='"+cityName+"',name='"+institution+"' WHERE id="+instituteID;
 
            Connect con=new Connect();
            con.setQuery(query, null);
            
            Institute.getInstance().retrieveData();
            this.dispose();
        }
        

    }//GEN-LAST:event_saveBtnActionPerformed

    private void cancelBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtn1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtn1ActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InstituteEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InstituteEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InstituteEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InstituteEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InstituteEditor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn1;
    private javax.swing.JTextField cityTxt;
    private javax.swing.JTextField instituteTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables
}
