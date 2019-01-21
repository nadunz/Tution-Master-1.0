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
public class CoursesSelectorTest {
    
    public CoursesSelectorTest() {
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
     * Test of showInterface method, of class CoursesSelector.
     */
    @Test
    public void testShowInterface() {
        System.out.println("showInterface");
        CoursesSelector instance = new CoursesSelector();
        instance.showInterface();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retrieveData method, of class CoursesSelector.
     */
    @Test
    public void testRetrieveData() {
        System.out.println("retrieveData");
        CoursesSelector instance = new CoursesSelector();
        instance.retrieveData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class CoursesSelector.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        CoursesSelector.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
