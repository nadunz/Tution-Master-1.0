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
public class CourseManagerTest {
    
    public CourseManagerTest() {
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
     * Test of retrieveData method, of class CourseManager.
     */
    @Test
    public void testRetrieveData() {
        System.out.println("retrieveData");
        CourseManager instance = new CourseManager();
        instance.retrieveData();
       
    }

    /**
     * Test of searchCourse method, of class CourseManager.
     */
    @Test
    public void testSearchCourse() {
        System.out.println("searchCourse");
        CourseManager instance = new CourseManager();
        instance.searchCourse();
        
    }

    
}
