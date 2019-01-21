/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesmanagement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mahendra
 */
public class PaymentManagerTest {
    
    public PaymentManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of goTo method, of class PaymentManager.
     */
    @Test
    public void testaddDataToTabel() {
        System.out.println("addDataToTabel");
        int Student_id = 5;
        String barcode = "100";
        String stuName = "Tharuka";
        String cardType = "Full";
        String institute = "YMBA";
        String amount = "2500";
        String city = "";
        String payment = "";
        String paymentDate = "";
        String course = "";
        PaymentManager instance = new PaymentManager();
        instance.addDataToTabel(Student_id,barcode,stuName,
            cardType,institute,amount,city,payment,
            paymentDate,course);
    }




    
}
