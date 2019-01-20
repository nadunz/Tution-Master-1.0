/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nadun
 */
public class Keyboard extends javax.swing.JFrame {

    /**
     * Creates new form Keyboard
     */
    private Robot robot;
    private boolean shiftSelected;
    private boolean capsLockSelected;
    private boolean ctrlSelected;
    private boolean altSelected;
    private boolean fnSelected;
    private static Keyboard instance=new Keyboard();
    public Keyboard() {
        initComponents();
        
        DragJFrame df=new DragJFrame(this, jPanel1);
        try {
            robot=new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(Keyboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setButtonEvent();
        
    }
   
    
    public static Keyboard getInstance(){
        return instance;
        
    }
    public void showInterface(){
        setButtonEvent();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(screenSize.getWidth()/2)-483,(int)screenSize.getHeight()-46-304);
        
        instance.setVisible(true);
    }
    public void hideInterface(){
        setDefaultkeyboard();
        instance.dispose();
        
    }
    
    

    
    private void setButtonEvent(){  
      capsLockSelected= Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
      
        if(capsLockSelected){
            capsLockBtn.setSelected(true);
            setCapitalLetters();
            setCapitalLettersPressedIcon();
        }  
    }
    
    private void setDefaultkeyboard(){
       if(shiftSelected){
          
          robot.keyRelease(KeyEvent.VK_SHIFT);
          shiftBtnRight.setSelected(false);
          shiftBtnleft.setSelected(false);
          shiftSelected=false;
         
       }
       if(capsLockSelected){
           
          robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
          capsLockBtn.setSelected(false);
      }
      if(ctrlSelected){
          robot.keyRelease(KeyEvent.VK_CONTROL);
          ctrlLeftBtn.setSelected(false);
          ctrlRightBtn.setSelected(false);
          
      }
      
      if(altSelected){
          robot.keyRelease(KeyEvent.VK_ALT);
          altLeftBtn.setSelected(false);
          altRightBtn.setSelected(false);
         
      }
      if(fnSelected){
          fnBtn.setSelected(false);
          
      } 
      
      setSimpalLetters();
      setSimpalLettersPressedIcon();
      setNumbers();
      setNumbersPressedIcon();
      
    }
    
    private void setFn(){
       btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f1.png")));
      btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f2.png")));
      btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f3.png")));
      btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f4.png")));
      btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f5.png")));
      btn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f6.png")));
      btn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f7.png")));
      btn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f8.png")));
      btn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f9.png")));
      btn0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f10.png")));
      btn12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f11.png")));
      btn13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f12.png"))); 
       
    }
    private void setFnPressedIcon(){
        
       btn1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f1-selected.png")));
      btn2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f2-selected.png")));
      btn3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f3-selected.png")));
      btn4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f4-selected.png")));
      btn5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f5-selected.png")));
      btn6.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f6-selected.png")));
      btn7.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f7-selected.png")));
      btn8.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f8-selected.png")));
      btn9.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f9-selected.png")));
      btn0.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f10-selected.png")));
      btn12.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f11-selected.png")));
      btn13.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/f12-selected.png"))); 
       
    }
    
     private void setCapitalLetters(){
       aBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Aaaa.png")));
       dBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/DDddd.png")));
       fBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/FFF.png")));
       gBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/GGG.png")));
       hBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/HH.png")));
       jBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/JJJ.png")));
       kBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/KKK.png")));
       lBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LLL.png")));
       zBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Zzz.png")));
       xBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Xxx.png")));
       cBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/CCcccc.png")));
       vBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/VVvv.png")));
       bBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Bbbb.png")));
       nBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/NNN.png")));
       mBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Mm.png")));
       qBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/QQ.png")));
       wBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/WW.png")));
       eBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Eeeee.png")));
       rBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Rr.png")));
       tBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Tt.png")));
       yBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/YY.png")));
       uBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Uuu.png")));
       iBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/IIIII.png")));
       oBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/OOoo.png")));
       pBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/PP.png")));
       sBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Ssss.png")));
    }
     
     private void setCapitalLettersPressedIcon(){
         
       aBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Aaaa-selected.png")));
       dBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/DDddd-selected.png")));
       fBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/FFF-selected.png")));
       gBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/GGG-selected.png")));
       hBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/HH-selected.png")));
       jBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/JJJ-selected.png")));
       kBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/KKK-selected.png")));
       lBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/LLL-selected.png")));
       zBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Zzz-selected.png")));
       xBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Xxx-selected.png")));
       cBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/CCcccc-selected.png")));
       vBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/VVvv-selected.png")));
       bBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Bbbb-selected.png")));
       nBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/NNN-selected.png")));
       mBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Mm-selected.png")));
       qBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/QQ-selected.png")));
       wBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/WW-selected.png")));
       eBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Eeeee-selected.png")));
       rBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Rr-selected.png")));
       tBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Tt-selected.png")));
       yBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/YY-selected.png")));
       uBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Uuu-selected.png")));
       iBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/IIIII-selected.png")));
       oBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/OOoo-selected.png")));
       pBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/PP-selected.png")));
       sBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Ssss-selected.png")));
    }
    private void setSimpalLetters(){
       aBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/aaa.png")));
       dBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ddd.png")));
       fBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ff.png")));
       gBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/g.png")));
       hBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/hhhh.png")));
       jBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/jjjjj.png")));
       kBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/kk.png")));
       lBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ll.png")));
       zBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z.png")));
       xBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/x.png")));
       cBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ccc.png")));
       vBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/vv.png")));
       bBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bbb.png")));
       nBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/nn.png")));
       mBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/mmmmm.png")));
       qBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/qqq.png")));
       wBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/www.png")));
       eBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/eee.png")));
       rBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/r.png")));
       tBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ttttt.png")));
       yBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/y.png")));
       uBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/uu.png")));
       iBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iii.png")));
       oBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/o.png")));
       pBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ppp.png")));
       sBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ss.png")));
    
    }
    
    private void setSimpalLettersPressedIcon(){
        
       aBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/aaa-selected.png")));
       dBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dd-selected.png")));
       fBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ff-selected.png")));
       gBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/g-selected.png")));
       hBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/hhhh-selected.png")));
       jBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/jjjjj-selected.png")));
       kBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/kk-selected.png")));
       lBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ll-selected.png")));
       zBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z-selected.png")));
       xBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/x-selected.png")));
       cBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ccc-selected.png")));
       vBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/vv-selected.png")));
       bBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bbb-selected.png")));
       nBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/nn-selected.png")));
       mBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/mmmmm-selected.png")));
       qBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/qqq-selected.png")));
       wBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/www-selected.png")));
       eBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/eee-selected.png")));
       rBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/r-selected.png")));
       tBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ttttt-selected.png")));
       yBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/y-selected.png")));
       uBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/uu-selected.png")));
       iBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iii-selected.png")));
       oBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/o-selected.png")));
       pBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ppp-selected.png")));
       sBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ss-selected.png")));
    
    }
    private void actionKeys(){
      if(shiftSelected){
          
          robot.keyRelease(KeyEvent.VK_SHIFT);
          shiftBtnRight.setSelected(false);
          shiftBtnleft.setSelected(false);
          shiftSelected=false;
          setNumbers();
          setNumbersPressedIcon();
          if(capsLockSelected){
            setCapitalLetters();
            setCapitalLettersPressedIcon();
          }else{
              
            setSimpalLetters();
            setSimpalLettersPressedIcon();
          }   
      }
      if(ctrlSelected){
          robot.keyRelease(KeyEvent.VK_CONTROL);
          ctrlLeftBtn.setSelected(false);
          ctrlRightBtn.setSelected(false);
          ctrlSelected=false;
      }
      
      if(altSelected){
          robot.keyRelease(KeyEvent.VK_ALT);
          altLeftBtn.setSelected(false);
          altRightBtn.setSelected(false);
          altSelected=false;
      }
      if(fnSelected){
          fnBtn.setSelected(false);
          fnSelected=false;
          setNormalNu();
          setNormalNuPressedIcon();
      } 
    }
    private void setNormalNu(){
        btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/1.png")));
      btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/2.png")));
      btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/3.png")));
      btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/4.png")));
      btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/5.png")));
      btn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/6.png")));
      btn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/7.png")));
      btn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/8.png")));
      btn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/9.png")));
      btn0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/0.png")));
      btn12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/minus.png")));
      btn13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/equal.png")));
    }
    
    
    private void setNormalNuPressedIcon(){
        
        btn1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/1-selected.png")));
      btn2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/2-selected.png")));
      btn3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/3-selected.png")));
      btn4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/4-selected.png")));
      btn5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/5-selected.png")));
      btn6.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/6-selected.png")));
      btn7.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/7-selected.png")));
      btn8.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/8-selected.png")));
      btn9.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/9-selected.png")));
      btn0.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/0-selected.png")));
      btn12.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/minus-selected.png")));
      btn13.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/equal-selected.png")));
    }
    
    
      
    private void setSymbol(){
      btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+1.png")));
      btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+2.png")));
      btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+3.png")));
      btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+4.png")));
      btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+5.png")));
      btn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+6.png")));
      btn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+7.png")));
      btn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+8.png")));
      btn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+9.png")));
      btn0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+0.png")));
      btn12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/plus.png")));
      btn13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/underscore.png")));
      openBracketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+openBracket.png")));
      closeBracketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+closeBracket.png")));
      slashBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+slash.png")));
      backSlashBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+backSlash.png")));
      dotBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+dot.png")));
      comaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+Comma.png")));
      semiColonBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/colon.png")));
      quoteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/doubleQuote.png")));
       
    }
    
     private void setSymbolPressedIcon(){
         
      btn1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+1-selected.png")));
      btn2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+2-selected.png")));
      btn3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+3-selected.png")));
      btn4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+4-selected.png")));
      btn5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+5-selected.png")));
      btn6.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+6-selected.png")));
      btn7.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+7-selected.png")));
      btn8.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+8-selected.png")));
      btn9.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+9-selected.png")));
      btn0.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+0-selected.png")));
      btn12.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/plus-selected.png")));
      btn13.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/underscore-selected.png")));
      openBracketBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+openBracket-selected.png")));
      closeBracketBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+closeBracket-selected.png")));
      slashBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+slash-selected.png")));
      backSlashBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+backSlash-selected.png")));
      dotBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+dot-selected.png")));
      comaBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift+Comma-selected.png")));
      semiColonBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/colon-selected.png")));
      quoteBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/doubleQuote-selected.png")));
       
    }
    private void setNumbers(){
     btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/1.png")));
      btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/2.png")));
      btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/3.png")));
      btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/4.png")));
      btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/5.png")));
      btn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/6.png")));
      btn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/7.png")));
      btn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/8.png")));
      btn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/9.png")));
      btn0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/0.png")));
      btn12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/minus.png")));
      btn13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/equal.png")));
      openBracketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/openBracket.png")));
      closeBracketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/closeBracket.png")));
      slashBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/slash.png")));
      backSlashBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/backSlash.png")));
      dotBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dot.png")));
      comaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/comma.png")));
      semiColonBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/semicolon.png")));
      quoteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/quote.png")));
    }
    
    private void setNumbersPressedIcon(){
        
     btn1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/1-selected.png")));
      btn2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/2-selected.png")));
      btn3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/3-selected.png")));
      btn4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/4-selected.png")));
      btn5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/5-selected.png")));
      btn6.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/6-selected.png")));
      btn7.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/7-selected.png")));
      btn8.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/8-selected.png")));
      btn9.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/9-selected.png")));
      btn0.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/0-selected.png")));
      btn12.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/minus-selected.png")));
      btn13.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/equal-selected.png")));
      openBracketBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/openBracket-selected.png")));
      closeBracketBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/closeBracket-selected.png")));
      slashBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/slash-selected.png")));
      backSlashBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/backSlash-selected.png")));
      dotBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dot-selected.png")));
      comaBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/comma-selected.png")));
      semiColonBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/semicolon-selected.png")));
      quoteBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/quote-selected.png")));
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
        jPanel11 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        escBtn = new javax.swing.JButton();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btn0 = new javax.swing.JButton();
        btn12 = new javax.swing.JButton();
        btn13 = new javax.swing.JButton();
        printScreenBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        qBtn = new javax.swing.JButton();
        wBtn = new javax.swing.JButton();
        eBtn = new javax.swing.JButton();
        rBtn = new javax.swing.JButton();
        tBtn = new javax.swing.JButton();
        yBtn = new javax.swing.JButton();
        uBtn = new javax.swing.JButton();
        iBtn = new javax.swing.JButton();
        oBtn = new javax.swing.JButton();
        pBtn = new javax.swing.JButton();
        openBracketBtn = new javax.swing.JButton();
        closeBracketBtn = new javax.swing.JButton();
        backSlashBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        tabBtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        aBtn = new javax.swing.JButton();
        sBtn = new javax.swing.JButton();
        dBtn = new javax.swing.JButton();
        fBtn = new javax.swing.JButton();
        gBtn = new javax.swing.JButton();
        hBtn = new javax.swing.JButton();
        jBtn = new javax.swing.JButton();
        kBtn = new javax.swing.JButton();
        lBtn = new javax.swing.JButton();
        semiColonBtn = new javax.swing.JButton();
        quoteBtn = new javax.swing.JButton();
        enterBtn = new javax.swing.JButton();
        capsLockBtn = new javax.swing.JToggleButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        zBtn = new javax.swing.JButton();
        xBtn = new javax.swing.JButton();
        cBtn = new javax.swing.JButton();
        vBtn = new javax.swing.JButton();
        bBtn = new javax.swing.JButton();
        nBtn = new javax.swing.JButton();
        mBtn = new javax.swing.JButton();
        comaBtn = new javax.swing.JButton();
        dotBtn = new javax.swing.JButton();
        slashBtn = new javax.swing.JButton();
        moveUpBtn = new javax.swing.JButton();
        shiftBtnleft = new javax.swing.JToggleButton();
        shiftBtnRight = new javax.swing.JToggleButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        fnBtn = new javax.swing.JToggleButton();
        officeBtn = new javax.swing.JButton();
        ctrlLeftBtn = new javax.swing.JToggleButton();
        altLeftBtn = new javax.swing.JToggleButton();
        spaceBtn = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        altRightBtn = new javax.swing.JToggleButton();
        ctrlRightBtn = new javax.swing.JToggleButton();
        moveLeftBtn = new javax.swing.JButton();
        moveDownBtn = new javax.swing.JButton();
        moveRightBtn = new javax.swing.JButton();
        btn11 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(1, 5, 28));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(1, 5, 28), 7));

        jPanel11.setBackground(new java.awt.Color(1, 5, 28));
        jPanel11.setLayout(new java.awt.GridLayout(5, 1, 0, 5));

        jPanel4.setBackground(new java.awt.Color(1, 5, 28));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(1, 5, 28));
        jPanel14.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        escBtn.setBackground(new java.awt.Color(21, 21, 41));
        escBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        escBtn.setForeground(new java.awt.Color(153, 153, 153));
        escBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/esc.png"))); // NOI18N
        escBtn.setBorder(null);
        escBtn.setBorderPainted(false);
        escBtn.setFocusPainted(false);
        escBtn.setPreferredSize(new java.awt.Dimension(50, 0));
        escBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/esc-selected.png"))); // NOI18N
        escBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                escBtnActionPerformed(evt);
            }
        });
        jPanel14.add(escBtn);

        btn1.setBackground(new java.awt.Color(21, 21, 41));
        btn1.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 16)); // NOI18N
        btn1.setForeground(new java.awt.Color(153, 153, 153));
        btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/1.png"))); // NOI18N
        btn1.setBorder(null);
        btn1.setBorderPainted(false);
        btn1.setFocusPainted(false);
        btn1.setPreferredSize(new java.awt.Dimension(50, 0));
        btn1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/1-selected.png"))); // NOI18N
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });
        jPanel14.add(btn1);

        btn2.setBackground(new java.awt.Color(21, 21, 41));
        btn2.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 16)); // NOI18N
        btn2.setForeground(new java.awt.Color(153, 153, 153));
        btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/2.png"))); // NOI18N
        btn2.setBorder(null);
        btn2.setBorderPainted(false);
        btn2.setFocusPainted(false);
        btn2.setPreferredSize(new java.awt.Dimension(50, 0));
        btn2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/2-selected.png"))); // NOI18N
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });
        jPanel14.add(btn2);

        btn3.setBackground(new java.awt.Color(21, 21, 41));
        btn3.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 16)); // NOI18N
        btn3.setForeground(new java.awt.Color(153, 153, 153));
        btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/3.png"))); // NOI18N
        btn3.setBorder(null);
        btn3.setBorderPainted(false);
        btn3.setFocusPainted(false);
        btn3.setPreferredSize(new java.awt.Dimension(50, 0));
        btn3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/3-selected.png"))); // NOI18N
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });
        jPanel14.add(btn3);

        btn4.setBackground(new java.awt.Color(21, 21, 41));
        btn4.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 16)); // NOI18N
        btn4.setForeground(new java.awt.Color(153, 153, 153));
        btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/4.png"))); // NOI18N
        btn4.setBorder(null);
        btn4.setBorderPainted(false);
        btn4.setFocusPainted(false);
        btn4.setPreferredSize(new java.awt.Dimension(50, 0));
        btn4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/4-selected.png"))); // NOI18N
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });
        jPanel14.add(btn4);

        btn5.setBackground(new java.awt.Color(21, 21, 41));
        btn5.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 16)); // NOI18N
        btn5.setForeground(new java.awt.Color(153, 153, 153));
        btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/5.png"))); // NOI18N
        btn5.setBorder(null);
        btn5.setBorderPainted(false);
        btn5.setFocusPainted(false);
        btn5.setPreferredSize(new java.awt.Dimension(50, 0));
        btn5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/5-selected.png"))); // NOI18N
        btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });
        jPanel14.add(btn5);

        btn6.setBackground(new java.awt.Color(21, 21, 41));
        btn6.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 16)); // NOI18N
        btn6.setForeground(new java.awt.Color(153, 153, 153));
        btn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/6.png"))); // NOI18N
        btn6.setBorder(null);
        btn6.setBorderPainted(false);
        btn6.setFocusPainted(false);
        btn6.setPreferredSize(new java.awt.Dimension(50, 0));
        btn6.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/6-selected.png"))); // NOI18N
        btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6ActionPerformed(evt);
            }
        });
        jPanel14.add(btn6);

        btn7.setBackground(new java.awt.Color(21, 21, 41));
        btn7.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 16)); // NOI18N
        btn7.setForeground(new java.awt.Color(153, 153, 153));
        btn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/7.png"))); // NOI18N
        btn7.setBorder(null);
        btn7.setBorderPainted(false);
        btn7.setFocusPainted(false);
        btn7.setPreferredSize(new java.awt.Dimension(50, 0));
        btn7.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/7-selected.png"))); // NOI18N
        btn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7ActionPerformed(evt);
            }
        });
        jPanel14.add(btn7);

        btn8.setBackground(new java.awt.Color(21, 21, 41));
        btn8.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 16)); // NOI18N
        btn8.setForeground(new java.awt.Color(153, 153, 153));
        btn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/8.png"))); // NOI18N
        btn8.setBorder(null);
        btn8.setBorderPainted(false);
        btn8.setFocusPainted(false);
        btn8.setPreferredSize(new java.awt.Dimension(50, 0));
        btn8.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/8-selected.png"))); // NOI18N
        btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn8ActionPerformed(evt);
            }
        });
        jPanel14.add(btn8);

        btn9.setBackground(new java.awt.Color(21, 21, 41));
        btn9.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 16)); // NOI18N
        btn9.setForeground(new java.awt.Color(153, 153, 153));
        btn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/9.png"))); // NOI18N
        btn9.setBorder(null);
        btn9.setBorderPainted(false);
        btn9.setFocusPainted(false);
        btn9.setPreferredSize(new java.awt.Dimension(50, 0));
        btn9.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/9-selected.png"))); // NOI18N
        btn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn9ActionPerformed(evt);
            }
        });
        jPanel14.add(btn9);

        btn0.setBackground(new java.awt.Color(21, 21, 41));
        btn0.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 16)); // NOI18N
        btn0.setForeground(new java.awt.Color(153, 153, 153));
        btn0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/0.png"))); // NOI18N
        btn0.setBorder(null);
        btn0.setBorderPainted(false);
        btn0.setFocusPainted(false);
        btn0.setPreferredSize(new java.awt.Dimension(50, 0));
        btn0.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/0-selected.png"))); // NOI18N
        btn0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn0ActionPerformed(evt);
            }
        });
        jPanel14.add(btn0);

        btn12.setBackground(new java.awt.Color(21, 21, 41));
        btn12.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        btn12.setForeground(new java.awt.Color(153, 153, 153));
        btn12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/minus.png"))); // NOI18N
        btn12.setBorder(null);
        btn12.setBorderPainted(false);
        btn12.setFocusPainted(false);
        btn12.setPreferredSize(new java.awt.Dimension(50, 0));
        btn12.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/minus-selected.png"))); // NOI18N
        btn12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn12ActionPerformed(evt);
            }
        });
        jPanel14.add(btn12);

        btn13.setBackground(new java.awt.Color(21, 21, 41));
        btn13.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        btn13.setForeground(new java.awt.Color(153, 153, 153));
        btn13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/equal.png"))); // NOI18N
        btn13.setBorder(null);
        btn13.setBorderPainted(false);
        btn13.setFocusPainted(false);
        btn13.setPreferredSize(new java.awt.Dimension(50, 0));
        btn13.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/equal-selected.png"))); // NOI18N
        btn13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn13ActionPerformed(evt);
            }
        });
        jPanel14.add(btn13);

        printScreenBtn.setBackground(new java.awt.Color(21, 21, 41));
        printScreenBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 13)); // NOI18N
        printScreenBtn.setForeground(new java.awt.Color(153, 153, 153));
        printScreenBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/prtScrn.png"))); // NOI18N
        printScreenBtn.setBorder(null);
        printScreenBtn.setBorderPainted(false);
        printScreenBtn.setFocusPainted(false);
        printScreenBtn.setPreferredSize(new java.awt.Dimension(50, 0));
        printScreenBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/prtScrn-selected.png"))); // NOI18N
        printScreenBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printScreenBtnActionPerformed(evt);
            }
        });
        jPanel14.add(printScreenBtn);

        jPanel4.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 864, 47));

        backBtn.setBackground(new java.awt.Color(21, 21, 41));
        backBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 14)); // NOI18N
        backBtn.setForeground(new java.awt.Color(153, 153, 153));
        backBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/backspace.png"))); // NOI18N
        backBtn.setBorder(null);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/backspaceSelected.png"))); // NOI18N
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        jPanel4.add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(869, 0, 90, 47));

        jPanel11.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(1, 5, 28));

        jPanel2.setBackground(new java.awt.Color(1, 5, 28));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        qBtn.setBackground(new java.awt.Color(21, 21, 41));
        qBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        qBtn.setForeground(new java.awt.Color(153, 153, 153));
        qBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/qqq.png"))); // NOI18N
        qBtn.setBorder(null);
        qBtn.setBorderPainted(false);
        qBtn.setFocusPainted(false);
        qBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/qqq-selected.png"))); // NOI18N
        qBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qBtnActionPerformed(evt);
            }
        });
        jPanel2.add(qBtn);

        wBtn.setBackground(new java.awt.Color(21, 21, 41));
        wBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        wBtn.setForeground(new java.awt.Color(153, 153, 153));
        wBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/www.png"))); // NOI18N
        wBtn.setBorder(null);
        wBtn.setBorderPainted(false);
        wBtn.setFocusPainted(false);
        wBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/www-selected.png"))); // NOI18N
        wBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wBtnActionPerformed(evt);
            }
        });
        jPanel2.add(wBtn);

        eBtn.setBackground(new java.awt.Color(21, 21, 41));
        eBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        eBtn.setForeground(new java.awt.Color(153, 153, 153));
        eBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/eee.png"))); // NOI18N
        eBtn.setBorder(null);
        eBtn.setBorderPainted(false);
        eBtn.setFocusPainted(false);
        eBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/eee-selected.png"))); // NOI18N
        eBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eBtnActionPerformed(evt);
            }
        });
        jPanel2.add(eBtn);

        rBtn.setBackground(new java.awt.Color(21, 21, 41));
        rBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        rBtn.setForeground(new java.awt.Color(153, 153, 153));
        rBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/r.png"))); // NOI18N
        rBtn.setBorder(null);
        rBtn.setBorderPainted(false);
        rBtn.setFocusPainted(false);
        rBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/r-selected.png"))); // NOI18N
        rBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rBtnActionPerformed(evt);
            }
        });
        jPanel2.add(rBtn);

        tBtn.setBackground(new java.awt.Color(21, 21, 41));
        tBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        tBtn.setForeground(new java.awt.Color(153, 153, 153));
        tBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ttttt.png"))); // NOI18N
        tBtn.setBorder(null);
        tBtn.setBorderPainted(false);
        tBtn.setFocusPainted(false);
        tBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ttttt-selected.png"))); // NOI18N
        tBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tBtnActionPerformed(evt);
            }
        });
        jPanel2.add(tBtn);

        yBtn.setBackground(new java.awt.Color(21, 21, 41));
        yBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        yBtn.setForeground(new java.awt.Color(153, 153, 153));
        yBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/y.png"))); // NOI18N
        yBtn.setBorder(null);
        yBtn.setBorderPainted(false);
        yBtn.setFocusPainted(false);
        yBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/y-selected.png"))); // NOI18N
        yBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yBtnActionPerformed(evt);
            }
        });
        jPanel2.add(yBtn);

        uBtn.setBackground(new java.awt.Color(21, 21, 41));
        uBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        uBtn.setForeground(new java.awt.Color(153, 153, 153));
        uBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/uu.png"))); // NOI18N
        uBtn.setBorder(null);
        uBtn.setBorderPainted(false);
        uBtn.setFocusPainted(false);
        uBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/uu-selected.png"))); // NOI18N
        uBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uBtnActionPerformed(evt);
            }
        });
        jPanel2.add(uBtn);

        iBtn.setBackground(new java.awt.Color(21, 21, 41));
        iBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        iBtn.setForeground(new java.awt.Color(153, 153, 153));
        iBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iii.png"))); // NOI18N
        iBtn.setBorder(null);
        iBtn.setBorderPainted(false);
        iBtn.setFocusPainted(false);
        iBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/iii-selected.png"))); // NOI18N
        iBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iBtnActionPerformed(evt);
            }
        });
        jPanel2.add(iBtn);

        oBtn.setBackground(new java.awt.Color(21, 21, 41));
        oBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        oBtn.setForeground(new java.awt.Color(153, 153, 153));
        oBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/o.png"))); // NOI18N
        oBtn.setBorder(null);
        oBtn.setBorderPainted(false);
        oBtn.setFocusPainted(false);
        oBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/o-selected.png"))); // NOI18N
        oBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oBtnActionPerformed(evt);
            }
        });
        jPanel2.add(oBtn);

        pBtn.setBackground(new java.awt.Color(21, 21, 41));
        pBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        pBtn.setForeground(new java.awt.Color(153, 153, 153));
        pBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ppp.png"))); // NOI18N
        pBtn.setBorder(null);
        pBtn.setBorderPainted(false);
        pBtn.setFocusPainted(false);
        pBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ppp-selected.png"))); // NOI18N
        pBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pBtnActionPerformed(evt);
            }
        });
        jPanel2.add(pBtn);

        openBracketBtn.setBackground(new java.awt.Color(21, 21, 41));
        openBracketBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        openBracketBtn.setForeground(new java.awt.Color(153, 153, 153));
        openBracketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/openBracket.png"))); // NOI18N
        openBracketBtn.setBorder(null);
        openBracketBtn.setBorderPainted(false);
        openBracketBtn.setFocusPainted(false);
        openBracketBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/openBracket-selected.png"))); // NOI18N
        openBracketBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openBracketBtnActionPerformed(evt);
            }
        });
        jPanel2.add(openBracketBtn);

        closeBracketBtn.setBackground(new java.awt.Color(21, 21, 41));
        closeBracketBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        closeBracketBtn.setForeground(new java.awt.Color(153, 153, 153));
        closeBracketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/closeBracket.png"))); // NOI18N
        closeBracketBtn.setBorder(null);
        closeBracketBtn.setBorderPainted(false);
        closeBracketBtn.setFocusPainted(false);
        closeBracketBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/closeBracket-selected.png"))); // NOI18N
        closeBracketBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBracketBtnActionPerformed(evt);
            }
        });
        jPanel2.add(closeBracketBtn);

        backSlashBtn.setBackground(new java.awt.Color(21, 21, 41));
        backSlashBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        backSlashBtn.setForeground(new java.awt.Color(153, 153, 153));
        backSlashBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/backSlash.png"))); // NOI18N
        backSlashBtn.setBorder(null);
        backSlashBtn.setBorderPainted(false);
        backSlashBtn.setFocusPainted(false);
        backSlashBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/backSlash-selected.png"))); // NOI18N
        backSlashBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backSlashBtnActionPerformed(evt);
            }
        });
        jPanel2.add(backSlashBtn);

        deleteBtn.setBackground(new java.awt.Color(21, 21, 41));
        deleteBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        deleteBtn.setForeground(new java.awt.Color(153, 153, 153));
        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dele.png"))); // NOI18N
        deleteBtn.setBorder(null);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dele-selected.png"))); // NOI18N
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        jPanel2.add(deleteBtn);

        tabBtn.setBackground(new java.awt.Color(21, 21, 41));
        tabBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        tabBtn.setForeground(new java.awt.Color(153, 153, 153));
        tabBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/tab.png"))); // NOI18N
        tabBtn.setBorder(null);
        tabBtn.setBorderPainted(false);
        tabBtn.setFocusPainted(false);
        tabBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/tab-selected.png"))); // NOI18N
        tabBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(tabBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 863, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel11.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(1, 5, 28));

        jPanel7.setBackground(new java.awt.Color(1, 5, 28));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        aBtn.setBackground(new java.awt.Color(21, 21, 41));
        aBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        aBtn.setForeground(new java.awt.Color(153, 153, 153));
        aBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/aaa.png"))); // NOI18N
        aBtn.setBorder(null);
        aBtn.setBorderPainted(false);
        aBtn.setFocusPainted(false);
        aBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/aaa-selected.png"))); // NOI18N
        aBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aBtnActionPerformed(evt);
            }
        });
        aBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                aBtnKeyPressed(evt);
            }
        });
        jPanel7.add(aBtn);

        sBtn.setBackground(new java.awt.Color(21, 21, 41));
        sBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        sBtn.setForeground(new java.awt.Color(153, 153, 153));
        sBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ss.png"))); // NOI18N
        sBtn.setBorder(null);
        sBtn.setBorderPainted(false);
        sBtn.setFocusPainted(false);
        sBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ss-selected.png"))); // NOI18N
        sBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sBtnActionPerformed(evt);
            }
        });
        jPanel7.add(sBtn);

        dBtn.setBackground(new java.awt.Color(21, 21, 41));
        dBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        dBtn.setForeground(new java.awt.Color(153, 153, 153));
        dBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ddd.png"))); // NOI18N
        dBtn.setBorder(null);
        dBtn.setBorderPainted(false);
        dBtn.setFocusPainted(false);
        dBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dd-selected.png"))); // NOI18N
        dBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dBtnActionPerformed(evt);
            }
        });
        jPanel7.add(dBtn);

        fBtn.setBackground(new java.awt.Color(21, 21, 41));
        fBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        fBtn.setForeground(new java.awt.Color(153, 153, 153));
        fBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ff.png"))); // NOI18N
        fBtn.setBorder(null);
        fBtn.setBorderPainted(false);
        fBtn.setFocusPainted(false);
        fBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ff-selected.png"))); // NOI18N
        fBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fBtnActionPerformed(evt);
            }
        });
        jPanel7.add(fBtn);

        gBtn.setBackground(new java.awt.Color(21, 21, 41));
        gBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        gBtn.setForeground(new java.awt.Color(153, 153, 153));
        gBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/g.png"))); // NOI18N
        gBtn.setBorder(null);
        gBtn.setBorderPainted(false);
        gBtn.setFocusPainted(false);
        gBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/g-selected.png"))); // NOI18N
        gBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gBtnActionPerformed(evt);
            }
        });
        jPanel7.add(gBtn);

        hBtn.setBackground(new java.awt.Color(21, 21, 41));
        hBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        hBtn.setForeground(new java.awt.Color(153, 153, 153));
        hBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/hhhh.png"))); // NOI18N
        hBtn.setBorder(null);
        hBtn.setBorderPainted(false);
        hBtn.setFocusPainted(false);
        hBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/hhhh-selected.png"))); // NOI18N
        hBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hBtnActionPerformed(evt);
            }
        });
        jPanel7.add(hBtn);

        jBtn.setBackground(new java.awt.Color(21, 21, 41));
        jBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        jBtn.setForeground(new java.awt.Color(153, 153, 153));
        jBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/jjjjj.png"))); // NOI18N
        jBtn.setBorder(null);
        jBtn.setBorderPainted(false);
        jBtn.setFocusPainted(false);
        jBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/jjjjj-selected.png"))); // NOI18N
        jBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActionPerformed(evt);
            }
        });
        jPanel7.add(jBtn);

        kBtn.setBackground(new java.awt.Color(21, 21, 41));
        kBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        kBtn.setForeground(new java.awt.Color(153, 153, 153));
        kBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/kk.png"))); // NOI18N
        kBtn.setBorder(null);
        kBtn.setBorderPainted(false);
        kBtn.setFocusPainted(false);
        kBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/kk-selected.png"))); // NOI18N
        kBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kBtnActionPerformed(evt);
            }
        });
        jPanel7.add(kBtn);

        lBtn.setBackground(new java.awt.Color(21, 21, 41));
        lBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        lBtn.setForeground(new java.awt.Color(153, 153, 153));
        lBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ll.png"))); // NOI18N
        lBtn.setBorder(null);
        lBtn.setBorderPainted(false);
        lBtn.setFocusPainted(false);
        lBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ll-selected.png"))); // NOI18N
        lBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lBtnActionPerformed(evt);
            }
        });
        jPanel7.add(lBtn);

        semiColonBtn.setBackground(new java.awt.Color(21, 21, 41));
        semiColonBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        semiColonBtn.setForeground(new java.awt.Color(153, 153, 153));
        semiColonBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/semicolon.png"))); // NOI18N
        semiColonBtn.setBorder(null);
        semiColonBtn.setBorderPainted(false);
        semiColonBtn.setFocusPainted(false);
        semiColonBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/semicolon-selected.png"))); // NOI18N
        semiColonBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                semiColonBtnActionPerformed(evt);
            }
        });
        jPanel7.add(semiColonBtn);

        quoteBtn.setBackground(new java.awt.Color(21, 21, 41));
        quoteBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        quoteBtn.setForeground(new java.awt.Color(153, 153, 153));
        quoteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/quote.png"))); // NOI18N
        quoteBtn.setBorder(null);
        quoteBtn.setBorderPainted(false);
        quoteBtn.setFocusPainted(false);
        quoteBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/quote-selected.png"))); // NOI18N
        quoteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quoteBtnActionPerformed(evt);
            }
        });
        jPanel7.add(quoteBtn);

        enterBtn.setBackground(new java.awt.Color(21, 21, 41));
        enterBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        enterBtn.setForeground(new java.awt.Color(153, 153, 153));
        enterBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/enter.png"))); // NOI18N
        enterBtn.setBorder(null);
        enterBtn.setBorderPainted(false);
        enterBtn.setFocusPainted(false);
        enterBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/enter-selected.png"))); // NOI18N
        enterBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterBtnActionPerformed(evt);
            }
        });

        capsLockBtn.setBackground(new java.awt.Color(21, 21, 41));
        capsLockBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        capsLockBtn.setForeground(new java.awt.Color(153, 153, 153));
        capsLockBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/capsLock.png"))); // NOI18N
        capsLockBtn.setBorder(null);
        capsLockBtn.setBorderPainted(false);
        capsLockBtn.setFocusPainted(false);
        capsLockBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/capsLock-selected.png"))); // NOI18N
        capsLockBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capsLockBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(capsLockBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(enterBtn)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capsLockBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(enterBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel11.add(jPanel6);

        jPanel12.setBackground(new java.awt.Color(1, 5, 28));

        jPanel8.setBackground(new java.awt.Color(1, 5, 28));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        zBtn.setBackground(new java.awt.Color(21, 21, 41));
        zBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        zBtn.setForeground(new java.awt.Color(153, 153, 153));
        zBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z.png"))); // NOI18N
        zBtn.setBorder(null);
        zBtn.setBorderPainted(false);
        zBtn.setFocusPainted(false);
        zBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z-selected.png"))); // NOI18N
        zBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zBtnActionPerformed(evt);
            }
        });
        jPanel8.add(zBtn);

        xBtn.setBackground(new java.awt.Color(21, 21, 41));
        xBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        xBtn.setForeground(new java.awt.Color(153, 153, 153));
        xBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/x.png"))); // NOI18N
        xBtn.setBorder(null);
        xBtn.setBorderPainted(false);
        xBtn.setFocusPainted(false);
        xBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/x-selected.png"))); // NOI18N
        xBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xBtnActionPerformed(evt);
            }
        });
        jPanel8.add(xBtn);

        cBtn.setBackground(new java.awt.Color(21, 21, 41));
        cBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        cBtn.setForeground(new java.awt.Color(153, 153, 153));
        cBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ccc.png"))); // NOI18N
        cBtn.setBorder(null);
        cBtn.setBorderPainted(false);
        cBtn.setFocusPainted(false);
        cBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ccc-selected.png"))); // NOI18N
        cBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cBtnActionPerformed(evt);
            }
        });
        jPanel8.add(cBtn);

        vBtn.setBackground(new java.awt.Color(21, 21, 41));
        vBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        vBtn.setForeground(new java.awt.Color(153, 153, 153));
        vBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/vv.png"))); // NOI18N
        vBtn.setBorder(null);
        vBtn.setBorderPainted(false);
        vBtn.setFocusPainted(false);
        vBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/vv-selected.png"))); // NOI18N
        vBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vBtnActionPerformed(evt);
            }
        });
        jPanel8.add(vBtn);

        bBtn.setBackground(new java.awt.Color(21, 21, 41));
        bBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        bBtn.setForeground(new java.awt.Color(153, 153, 153));
        bBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bbb.png"))); // NOI18N
        bBtn.setBorder(null);
        bBtn.setBorderPainted(false);
        bBtn.setFocusPainted(false);
        bBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bbb-selected.png"))); // NOI18N
        bBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBtnActionPerformed(evt);
            }
        });
        jPanel8.add(bBtn);

        nBtn.setBackground(new java.awt.Color(21, 21, 41));
        nBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        nBtn.setForeground(new java.awt.Color(153, 153, 153));
        nBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/nn.png"))); // NOI18N
        nBtn.setBorder(null);
        nBtn.setBorderPainted(false);
        nBtn.setFocusPainted(false);
        nBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/nn-selected.png"))); // NOI18N
        nBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nBtnActionPerformed(evt);
            }
        });
        jPanel8.add(nBtn);

        mBtn.setBackground(new java.awt.Color(21, 21, 41));
        mBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        mBtn.setForeground(new java.awt.Color(153, 153, 153));
        mBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/mmmmm.png"))); // NOI18N
        mBtn.setBorder(null);
        mBtn.setBorderPainted(false);
        mBtn.setFocusPainted(false);
        mBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/mmmmm-selected.png"))); // NOI18N
        mBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBtnActionPerformed(evt);
            }
        });
        jPanel8.add(mBtn);

        comaBtn.setBackground(new java.awt.Color(21, 21, 41));
        comaBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        comaBtn.setForeground(new java.awt.Color(153, 153, 153));
        comaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/comma.png"))); // NOI18N
        comaBtn.setBorder(null);
        comaBtn.setBorderPainted(false);
        comaBtn.setFocusPainted(false);
        comaBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/comma-selected.png"))); // NOI18N
        comaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comaBtnActionPerformed(evt);
            }
        });
        jPanel8.add(comaBtn);

        dotBtn.setBackground(new java.awt.Color(21, 21, 41));
        dotBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        dotBtn.setForeground(new java.awt.Color(153, 153, 153));
        dotBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dot.png"))); // NOI18N
        dotBtn.setBorder(null);
        dotBtn.setBorderPainted(false);
        dotBtn.setFocusPainted(false);
        dotBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dot-selected.png"))); // NOI18N
        dotBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dotBtnActionPerformed(evt);
            }
        });
        jPanel8.add(dotBtn);

        slashBtn.setBackground(new java.awt.Color(21, 21, 41));
        slashBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        slashBtn.setForeground(new java.awt.Color(153, 153, 153));
        slashBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/slash.png"))); // NOI18N
        slashBtn.setBorder(null);
        slashBtn.setBorderPainted(false);
        slashBtn.setFocusPainted(false);
        slashBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/slash-selected.png"))); // NOI18N
        slashBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slashBtnActionPerformed(evt);
            }
        });
        jPanel8.add(slashBtn);

        moveUpBtn.setBackground(new java.awt.Color(21, 21, 41));
        moveUpBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        moveUpBtn.setForeground(new java.awt.Color(153, 153, 153));
        moveUpBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Arrow up.png"))); // NOI18N
        moveUpBtn.setBorder(null);
        moveUpBtn.setBorderPainted(false);
        moveUpBtn.setFocusPainted(false);
        moveUpBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Arrow up-selected.png"))); // NOI18N
        moveUpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUpBtnActionPerformed(evt);
            }
        });
        jPanel8.add(moveUpBtn);

        shiftBtnleft.setBackground(new java.awt.Color(21, 21, 41));
        shiftBtnleft.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        shiftBtnleft.setForeground(new java.awt.Color(153, 153, 153));
        shiftBtnleft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift.png"))); // NOI18N
        shiftBtnleft.setBorder(null);
        shiftBtnleft.setBorderPainted(false);
        shiftBtnleft.setFocusPainted(false);
        shiftBtnleft.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift-selected.png"))); // NOI18N
        shiftBtnleft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shiftBtnleftActionPerformed(evt);
            }
        });

        shiftBtnRight.setBackground(new java.awt.Color(21, 21, 41));
        shiftBtnRight.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        shiftBtnRight.setForeground(new java.awt.Color(153, 153, 153));
        shiftBtnRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift.png"))); // NOI18N
        shiftBtnRight.setBorder(null);
        shiftBtnRight.setBorderPainted(false);
        shiftBtnRight.setFocusPainted(false);
        shiftBtnRight.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/shift-selected.png"))); // NOI18N
        shiftBtnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shiftBtnRightActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(shiftBtnleft, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(shiftBtnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(shiftBtnleft, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(shiftBtnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel11.add(jPanel12);

        jPanel13.setBackground(new java.awt.Color(1, 5, 28));

        jPanel9.setBackground(new java.awt.Color(1, 5, 28));
        jPanel9.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        fnBtn.setBackground(new java.awt.Color(21, 21, 41));
        fnBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        fnBtn.setForeground(new java.awt.Color(153, 153, 153));
        fnBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fn.png"))); // NOI18N
        fnBtn.setBorder(null);
        fnBtn.setBorderPainted(false);
        fnBtn.setFocusPainted(false);
        fnBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fn-selected.png"))); // NOI18N
        fnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnBtnActionPerformed(evt);
            }
        });
        jPanel9.add(fnBtn);

        officeBtn.setBackground(new java.awt.Color(21, 21, 41));
        officeBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        officeBtn.setForeground(new java.awt.Color(153, 153, 153));
        officeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Windows 8.png"))); // NOI18N
        officeBtn.setBorder(null);
        officeBtn.setBorderPainted(false);
        officeBtn.setFocusPainted(false);
        officeBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Windows-selected.png"))); // NOI18N
        officeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                officeBtnActionPerformed(evt);
            }
        });
        jPanel9.add(officeBtn);

        ctrlLeftBtn.setBackground(new java.awt.Color(21, 21, 41));
        ctrlLeftBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        ctrlLeftBtn.setForeground(new java.awt.Color(153, 153, 153));
        ctrlLeftBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ctrl.png"))); // NOI18N
        ctrlLeftBtn.setBorder(null);
        ctrlLeftBtn.setBorderPainted(false);
        ctrlLeftBtn.setFocusPainted(false);
        ctrlLeftBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ctrl-selected.png"))); // NOI18N
        ctrlLeftBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ctrlLeftBtnActionPerformed(evt);
            }
        });
        jPanel9.add(ctrlLeftBtn);

        altLeftBtn.setBackground(new java.awt.Color(21, 21, 41));
        altLeftBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        altLeftBtn.setForeground(new java.awt.Color(153, 153, 153));
        altLeftBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/alt.png"))); // NOI18N
        altLeftBtn.setBorder(null);
        altLeftBtn.setBorderPainted(false);
        altLeftBtn.setFocusPainted(false);
        altLeftBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/alt-selected.png"))); // NOI18N
        altLeftBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altLeftBtnActionPerformed(evt);
            }
        });
        jPanel9.add(altLeftBtn);

        spaceBtn.setBackground(new java.awt.Color(21, 21, 41));
        spaceBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        spaceBtn.setForeground(new java.awt.Color(153, 153, 153));
        spaceBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/space.png"))); // NOI18N
        spaceBtn.setBorder(null);
        spaceBtn.setBorderPainted(false);
        spaceBtn.setFocusPainted(false);
        spaceBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/space-selected.png"))); // NOI18N
        spaceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spaceBtnActionPerformed(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(1, 5, 28));
        jPanel10.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        altRightBtn.setBackground(new java.awt.Color(21, 21, 41));
        altRightBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        altRightBtn.setForeground(new java.awt.Color(153, 153, 153));
        altRightBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/alt.png"))); // NOI18N
        altRightBtn.setBorder(null);
        altRightBtn.setBorderPainted(false);
        altRightBtn.setFocusPainted(false);
        altRightBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/alt-selected.png"))); // NOI18N
        altRightBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altRightBtnActionPerformed(evt);
            }
        });
        jPanel10.add(altRightBtn);

        ctrlRightBtn.setBackground(new java.awt.Color(21, 21, 41));
        ctrlRightBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        ctrlRightBtn.setForeground(new java.awt.Color(153, 153, 153));
        ctrlRightBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ctrl.png"))); // NOI18N
        ctrlRightBtn.setBorder(null);
        ctrlRightBtn.setBorderPainted(false);
        ctrlRightBtn.setFocusPainted(false);
        ctrlRightBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ctrl-selected.png"))); // NOI18N
        ctrlRightBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ctrlRightBtnActionPerformed(evt);
            }
        });
        jPanel10.add(ctrlRightBtn);

        moveLeftBtn.setBackground(new java.awt.Color(21, 21, 41));
        moveLeftBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        moveLeftBtn.setForeground(new java.awt.Color(153, 153, 153));
        moveLeftBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Arrow left.png"))); // NOI18N
        moveLeftBtn.setBorder(null);
        moveLeftBtn.setBorderPainted(false);
        moveLeftBtn.setFocusPainted(false);
        moveLeftBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Arrow left-selected.png"))); // NOI18N
        moveLeftBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveLeftBtnActionPerformed(evt);
            }
        });
        jPanel10.add(moveLeftBtn);

        moveDownBtn.setBackground(new java.awt.Color(21, 21, 41));
        moveDownBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        moveDownBtn.setForeground(new java.awt.Color(153, 153, 153));
        moveDownBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Arrow down.png"))); // NOI18N
        moveDownBtn.setBorder(null);
        moveDownBtn.setBorderPainted(false);
        moveDownBtn.setFocusPainted(false);
        moveDownBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Arrow down-selected.png"))); // NOI18N
        moveDownBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDownBtnActionPerformed(evt);
            }
        });
        jPanel10.add(moveDownBtn);

        moveRightBtn.setBackground(new java.awt.Color(21, 21, 41));
        moveRightBtn.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        moveRightBtn.setForeground(new java.awt.Color(153, 153, 153));
        moveRightBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Arrow right.png"))); // NOI18N
        moveRightBtn.setBorder(null);
        moveRightBtn.setBorderPainted(false);
        moveRightBtn.setFocusPainted(false);
        moveRightBtn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Arrow right-selected.png"))); // NOI18N
        moveRightBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveRightBtnActionPerformed(evt);
            }
        });
        jPanel10.add(moveRightBtn);

        btn11.setBackground(new java.awt.Color(21, 21, 41));
        btn11.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 17)); // NOI18N
        btn11.setForeground(new java.awt.Color(153, 153, 153));
        btn11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/insert.png"))); // NOI18N
        btn11.setBorder(null);
        btn11.setBorderPainted(false);
        btn11.setFocusPainted(false);
        btn11.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/image/insert-selected.png"))); // NOI18N
        btn11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn11ActionPerformed(evt);
            }
        });
        jPanel10.add(btn11);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spaceBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 371, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spaceBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(2, 2, 2))
        );

        jPanel11.add(jPanel13);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 952, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void escBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_escBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        actionKeys();
    }//GEN-LAST:event_escBtnActionPerformed

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F1);
            robot.keyRelease(KeyEvent.VK_F1);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_1);
            robot.keyRelease(KeyEvent.VK_1);
            actionKeys();
        }
    }//GEN-LAST:event_btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F2);
            robot.keyRelease(KeyEvent.VK_F2);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_2);
            robot.keyRelease(KeyEvent.VK_2);
            actionKeys();
        }
    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F3);
            robot.keyRelease(KeyEvent.VK_F3);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_3);
            robot.keyRelease(KeyEvent.VK_3);
            actionKeys();
        }
    }//GEN-LAST:event_btn3ActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F4);
            robot.keyRelease(KeyEvent.VK_F4);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_4);
            robot.keyRelease(KeyEvent.VK_4);
            actionKeys();
        }
    }//GEN-LAST:event_btn4ActionPerformed

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F5);
            robot.keyRelease(KeyEvent.VK_F5);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_5);
            robot.keyRelease(KeyEvent.VK_5);
            actionKeys();
        }
    }//GEN-LAST:event_btn5ActionPerformed

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F6);
            robot.keyRelease(KeyEvent.VK_F6);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_6);
            robot.keyRelease(KeyEvent.VK_6);
            actionKeys();

        }
    }//GEN-LAST:event_btn6ActionPerformed

    private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F7);
            robot.keyRelease(KeyEvent.VK_F7);
            actionKeys();

        }else{
            robot.keyPress(KeyEvent.VK_7);
            robot.keyRelease(KeyEvent.VK_7);
            actionKeys();
        }
    }//GEN-LAST:event_btn7ActionPerformed

    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn8ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F8);
            robot.keyRelease(KeyEvent.VK_F8);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_8);
            robot.keyRelease(KeyEvent.VK_8);
            actionKeys();
        }
    }//GEN-LAST:event_btn8ActionPerformed

    private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn9ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F9);
            robot.keyRelease(KeyEvent.VK_F9);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_9);
            robot.keyRelease(KeyEvent.VK_9);
            actionKeys();
        }
    }//GEN-LAST:event_btn9ActionPerformed

    private void btn0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn0ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F10);
            robot.keyRelease(KeyEvent.VK_F10);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_0);
            robot.keyRelease(KeyEvent.VK_0);
            actionKeys();
        }
    }//GEN-LAST:event_btn0ActionPerformed

    private void btn12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn12ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F11);
            robot.keyRelease(KeyEvent.VK_F11);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_MINUS);
            robot.keyRelease(KeyEvent.VK_MINUS);
            actionKeys();
        }
    }//GEN-LAST:event_btn12ActionPerformed

    private void btn13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn13ActionPerformed
        // TODO add your handling code here:
        if(fnSelected){
            robot.keyPress(KeyEvent.VK_F12);
            robot.keyRelease(KeyEvent.VK_F12);
            actionKeys();
        }else{
            robot.keyPress(KeyEvent.VK_EQUALS);
            robot.keyRelease(KeyEvent.VK_EQUALS);
            actionKeys();
        }
    }//GEN-LAST:event_btn13ActionPerformed

    private void printScreenBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printScreenBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_PRINTSCREEN);
        robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
        actionKeys();
    }//GEN-LAST:event_printScreenBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        actionKeys();
    }//GEN-LAST:event_backBtnActionPerformed

    private void qBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_Q);
        robot.keyRelease(KeyEvent.VK_Q);
        actionKeys();
    }//GEN-LAST:event_qBtnActionPerformed

    private void wBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);
        actionKeys();
    }//GEN-LAST:event_wBtnActionPerformed

    private void eBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_E);
        actionKeys();
    }//GEN-LAST:event_eBtnActionPerformed

    private void rBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_R);
        robot.keyRelease(KeyEvent.VK_R);
        actionKeys();
    }//GEN-LAST:event_rBtnActionPerformed

    private void tBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
        actionKeys();
    }//GEN-LAST:event_tBtnActionPerformed

    private void yBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_Y);
        robot.keyRelease(KeyEvent.VK_Y);
        actionKeys();
    }//GEN-LAST:event_yBtnActionPerformed

    private void uBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_U);
        robot.keyRelease(KeyEvent.VK_U);
        actionKeys();
    }//GEN-LAST:event_uBtnActionPerformed

    private void iBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_I);
        robot.keyRelease(KeyEvent.VK_I);
        actionKeys();
    }//GEN-LAST:event_iBtnActionPerformed

    private void oBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_O);
        robot.keyRelease(KeyEvent.VK_O);
        actionKeys();
    }//GEN-LAST:event_oBtnActionPerformed

    private void pBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_P);
        robot.keyRelease(KeyEvent.VK_P);
        actionKeys();
    }//GEN-LAST:event_pBtnActionPerformed

    private void openBracketBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openBracketBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
        robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
        actionKeys();
    }//GEN-LAST:event_openBracketBtnActionPerformed

    private void closeBracketBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBracketBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
        robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
        actionKeys();
    }//GEN-LAST:event_closeBracketBtnActionPerformed

    private void backSlashBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backSlashBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_BACK_SLASH);
        robot.keyRelease(KeyEvent.VK_BACK_SLASH);
        actionKeys();
    }//GEN-LAST:event_backSlashBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_DELETE);
        actionKeys();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void tabBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        actionKeys();
    }//GEN-LAST:event_tabBtnActionPerformed

    private void aBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aBtnActionPerformed
        // TODO add your handling code here:

        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        actionKeys();
    }//GEN-LAST:event_aBtnActionPerformed

    private void sBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        actionKeys();
    }//GEN-LAST:event_sBtnActionPerformed

    private void dBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dBtnActionPerformed
        // TODO add your handling code here:
        dBtn.setBackground(new Color(36, 36, 36));
        robot.keyPress(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_D);
        actionKeys();
    }//GEN-LAST:event_dBtnActionPerformed

    private void fBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_F);
        robot.keyRelease(KeyEvent.VK_F);
        actionKeys();
    }//GEN-LAST:event_fBtnActionPerformed

    private void gBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_G);
        robot.keyRelease(KeyEvent.VK_G);
        actionKeys();
    }//GEN-LAST:event_gBtnActionPerformed

    private void hBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_H);
        robot.keyRelease(KeyEvent.VK_H);
        actionKeys();
    }//GEN-LAST:event_hBtnActionPerformed

    private void jBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_J);
        actionKeys();
    }//GEN-LAST:event_jBtnActionPerformed

    private void kBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_K);
        robot.keyRelease(KeyEvent.VK_K);
        actionKeys();
    }//GEN-LAST:event_kBtnActionPerformed

    private void lBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_L);
        actionKeys();
    }//GEN-LAST:event_lBtnActionPerformed

    private void semiColonBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semiColonBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_SEMICOLON);
        robot.keyRelease(KeyEvent.VK_SEMICOLON);
        actionKeys();
    }//GEN-LAST:event_semiColonBtnActionPerformed

    private void quoteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quoteBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_QUOTE);
        robot.keyRelease(KeyEvent.VK_QUOTE);
        actionKeys();
    }//GEN-LAST:event_quoteBtnActionPerformed

    private void enterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        actionKeys();
    }//GEN-LAST:event_enterBtnActionPerformed

    private void capsLockBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capsLockBtnActionPerformed
        // TODO add your handling code here:

        if(capsLockBtn.isSelected()){
            robot.keyPress(KeyEvent.VK_CAPS_LOCK);
            robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
            if(shiftSelected){
                setSimpalLetters();
                setSimpalLettersPressedIcon();
            }else{
                setCapitalLetters();
                setCapitalLettersPressedIcon();
            }
            capsLockSelected=true;

        }else{
            robot.keyPress(KeyEvent.VK_CAPS_LOCK);
            robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
            if(shiftSelected){
                setCapitalLetters();
                setCapitalLettersPressedIcon();
            }else{
                setSimpalLetters();
                setSimpalLettersPressedIcon();
            }
            capsLockSelected=false;
        }
    }//GEN-LAST:event_capsLockBtnActionPerformed

    private void zBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_Z);
        robot.keyRelease(KeyEvent.VK_Z);
        actionKeys();

    }//GEN-LAST:event_zBtnActionPerformed

    private void xBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_X);
        robot.keyRelease(KeyEvent.VK_X);
        actionKeys();
    }//GEN-LAST:event_xBtnActionPerformed

    private void cBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);
        actionKeys();
    }//GEN-LAST:event_cBtnActionPerformed

    private void vBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        actionKeys();
    }//GEN-LAST:event_vBtnActionPerformed

    private void bBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_B);
        robot.keyRelease(KeyEvent.VK_B);
        actionKeys();
    }//GEN-LAST:event_bBtnActionPerformed

    private void nBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_N);
        actionKeys();
    }//GEN-LAST:event_nBtnActionPerformed

    private void mBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_M);
        robot.keyRelease(KeyEvent.VK_M);
        actionKeys();
    }//GEN-LAST:event_mBtnActionPerformed

    private void comaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comaBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_COMMA);
        robot.keyRelease(KeyEvent.VK_COMMA);
        actionKeys();
    }//GEN-LAST:event_comaBtnActionPerformed

    private void dotBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dotBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_PERIOD);
        robot.keyRelease(KeyEvent.VK_PERIOD);
        actionKeys();

    }//GEN-LAST:event_dotBtnActionPerformed

    private void slashBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slashBtnActionPerformed
        // TODO add your handling code here
        robot.keyPress(KeyEvent.VK_SLASH);
        robot.keyRelease(KeyEvent.VK_SLASH);
        actionKeys();
    }//GEN-LAST:event_slashBtnActionPerformed

    private void moveUpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUpBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        actionKeys();
    }//GEN-LAST:event_moveUpBtnActionPerformed

    private void shiftBtnleftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shiftBtnleftActionPerformed
        // TODO add your handling code here:

        if(shiftBtnleft.isSelected()){
            robot.keyPress(KeyEvent.VK_SHIFT);
            shiftSelected=true;
            if(fnSelected){
                setSymbol();
                setSymbolPressedIcon();
                setFn();
                setFnPressedIcon();
            }else{
                setSymbol();
                setSymbolPressedIcon();
            }
            if(capsLockSelected){
                setSimpalLetters();
                setSimpalLettersPressedIcon();
            }else{
                setCapitalLetters();
                setCapitalLettersPressedIcon();
            }
            shiftBtnRight.setSelected(true);

        }else{
            robot.keyRelease(KeyEvent.VK_SHIFT);
            shiftSelected=false;
            if(fnSelected){
                setNumbers();
                setNumbersPressedIcon();
                setFn();
                setFnPressedIcon();
            }else{
                setNumbers();
                setNumbersPressedIcon();
            }

            shiftBtnRight.setSelected(false);
            if(capsLockSelected){
                setCapitalLetters();
                setCapitalLettersPressedIcon();
            }else{
                setSimpalLetters();
                setSimpalLettersPressedIcon();
            }
        }
    }//GEN-LAST:event_shiftBtnleftActionPerformed

    private void shiftBtnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shiftBtnRightActionPerformed
        // TODO add your handling code here:
        if(shiftBtnRight.isSelected()){
            robot.keyPress(KeyEvent.VK_SHIFT);
            shiftSelected=true;
            if(fnSelected){
                setSymbol();
                setSymbolPressedIcon();
                setFn();
                setFnPressedIcon();
            }else{
                setSymbol();
                setSymbolPressedIcon();
            }
            if(capsLockSelected){
                setSimpalLetters();
                setSimpalLettersPressedIcon();
            }else{
                setCapitalLetters();
                setCapitalLettersPressedIcon();
            }
            shiftBtnleft.setSelected(true);

        }else{
            robot.keyRelease(KeyEvent.VK_SHIFT);
            shiftSelected=false;
            if(fnSelected){
                setNumbers();
                setNumbersPressedIcon();
                setFn();
                setFnPressedIcon();
            }else{
                setNumbers();
                setNumbersPressedIcon();
            }
            shiftBtnleft.setSelected(false);
            if(capsLockSelected){
                setCapitalLetters();
                setCapitalLettersPressedIcon();
            }else{
                setSimpalLetters();
                setSimpalLettersPressedIcon();
            }
        }
    }//GEN-LAST:event_shiftBtnRightActionPerformed

    private void fnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fnBtnActionPerformed
        // TODO add your handling code here:
        if(fnBtn.isSelected()){
            setFn();
            setFnPressedIcon();
            fnSelected=true;
        }else{
            setNormalNu();
            setNormalNuPressedIcon();
            fnSelected=false;
        }
    }//GEN-LAST:event_fnBtnActionPerformed

    private void officeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_officeBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        actionKeys();
    }//GEN-LAST:event_officeBtnActionPerformed

    private void ctrlLeftBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ctrlLeftBtnActionPerformed
        // TODO add your handling code here:
        if(ctrlLeftBtn.isSelected()){
            robot.keyPress(KeyEvent.VK_CONTROL);
            ctrlRightBtn.setSelected(true);
            ctrlSelected=true;

        }else{
            robot.keyRelease(KeyEvent.VK_CONTROL);
            ctrlRightBtn.setSelected(false);
            ctrlSelected=false;
        }
    }//GEN-LAST:event_ctrlLeftBtnActionPerformed

    private void altLeftBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altLeftBtnActionPerformed
        // TODO add your handling code here:
        if(altLeftBtn.isSelected()){
            robot.keyPress(KeyEvent.VK_CONTROL);
            altRightBtn.setSelected(true);
            altSelected=true;

        }else{
            robot.keyRelease(KeyEvent.VK_CONTROL);
            altRightBtn.setSelected(false);
            altSelected=false;
        }
    }//GEN-LAST:event_altLeftBtnActionPerformed

    private void spaceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spaceBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
        actionKeys();
    }//GEN-LAST:event_spaceBtnActionPerformed

    private void altRightBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altRightBtnActionPerformed
        // TODO add your handling code here:
        if(altRightBtn.isSelected()){
            robot.keyPress(KeyEvent.VK_CONTROL);
            altLeftBtn.setSelected(true);
            altSelected=true;

        }else{
            robot.keyRelease(KeyEvent.VK_CONTROL);
            altLeftBtn.setSelected(false);
            altSelected=false;
        }
    }//GEN-LAST:event_altRightBtnActionPerformed

    private void ctrlRightBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ctrlRightBtnActionPerformed
        // TODO add your handling code here:
        if(ctrlRightBtn.isSelected()){
            robot.keyPress(KeyEvent.VK_CONTROL);
            ctrlLeftBtn.setSelected(true);
            ctrlSelected=true;

        }else{
            robot.keyRelease(KeyEvent.VK_CONTROL);
            ctrlLeftBtn.setSelected(false);
            ctrlSelected=false;
        }
    }//GEN-LAST:event_ctrlRightBtnActionPerformed

    private void moveLeftBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveLeftBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        actionKeys();
    }//GEN-LAST:event_moveLeftBtnActionPerformed

    private void moveDownBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDownBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        actionKeys();
    }//GEN-LAST:event_moveDownBtnActionPerformed

    private void moveRightBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveRightBtnActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        actionKeys();
    }//GEN-LAST:event_moveRightBtnActionPerformed

    private void btn11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn11ActionPerformed
        // TODO add your handling code here:
        robot.keyPress(KeyEvent.VK_INSERT);
        robot.keyRelease(KeyEvent.VK_INSERT);
        actionKeys();
    }//GEN-LAST:event_btn11ActionPerformed

    private void aBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_aBtnKeyPressed
        
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        actionKeys();
    }//GEN-LAST:event_aBtnKeyPressed

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
            java.util.logging.Logger.getLogger(Keyboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Keyboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Keyboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Keyboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Keyboard().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aBtn;
    private javax.swing.JToggleButton altLeftBtn;
    private javax.swing.JToggleButton altRightBtn;
    private javax.swing.JButton bBtn;
    private javax.swing.JButton backBtn;
    private javax.swing.JButton backSlashBtn;
    private javax.swing.JButton btn0;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn11;
    private javax.swing.JButton btn12;
    private javax.swing.JButton btn13;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JButton cBtn;
    private javax.swing.JToggleButton capsLockBtn;
    private javax.swing.JButton closeBracketBtn;
    private javax.swing.JButton comaBtn;
    private javax.swing.JToggleButton ctrlLeftBtn;
    private javax.swing.JToggleButton ctrlRightBtn;
    private javax.swing.JButton dBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton dotBtn;
    private javax.swing.JButton eBtn;
    private javax.swing.JButton enterBtn;
    private javax.swing.JButton escBtn;
    private javax.swing.JButton fBtn;
    private javax.swing.JToggleButton fnBtn;
    private javax.swing.JButton gBtn;
    private javax.swing.JButton hBtn;
    private javax.swing.JButton iBtn;
    private javax.swing.JButton jBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JButton kBtn;
    private javax.swing.JButton lBtn;
    private javax.swing.JButton mBtn;
    private javax.swing.JButton moveDownBtn;
    private javax.swing.JButton moveLeftBtn;
    private javax.swing.JButton moveRightBtn;
    private javax.swing.JButton moveUpBtn;
    private javax.swing.JButton nBtn;
    private javax.swing.JButton oBtn;
    private javax.swing.JButton officeBtn;
    private javax.swing.JButton openBracketBtn;
    private javax.swing.JButton pBtn;
    private javax.swing.JButton printScreenBtn;
    private javax.swing.JButton qBtn;
    private javax.swing.JButton quoteBtn;
    private javax.swing.JButton rBtn;
    private javax.swing.JButton sBtn;
    private javax.swing.JButton semiColonBtn;
    private javax.swing.JToggleButton shiftBtnRight;
    private javax.swing.JToggleButton shiftBtnleft;
    private javax.swing.JButton slashBtn;
    private javax.swing.JButton spaceBtn;
    private javax.swing.JButton tBtn;
    private javax.swing.JButton tabBtn;
    private javax.swing.JButton uBtn;
    private javax.swing.JButton vBtn;
    private javax.swing.JButton wBtn;
    private javax.swing.JButton xBtn;
    private javax.swing.JButton yBtn;
    private javax.swing.JButton zBtn;
    // End of variables declaration//GEN-END:variables
}
