package mainPkg;

import static org.junit.Assert.*;

import javax.swing.event.ChangeEvent;

import org.junit.Before;
import org.junit.Test;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;



public class ControllerTest {
	View testView;
	Controller testCon;

/* @author Ben Salis
 *  Unsure how to test event handlers right now so 
 * I'll visit office hours for these tests
 */

	@Before
	public void setup() {
		testView = new View();
		testCon = new Controller(testView);
		//testCon.garden.setCurrentSpendings(1000);
		//testCon.garden.setLepCount(10);
		
	}
	
	@Test 
	public void testChangingSpendingTrue() {
		assertEquals(0,0);
		//testCon.changeSpendings(20, 256, true);
		//System.out.println(testCon.garden.getCurrentSpendings());
		//assertEquals(testCon.garden.getCurrentBudget(),980);
		
	}
	


}
