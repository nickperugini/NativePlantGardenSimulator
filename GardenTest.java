package mainPkg;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class GardenTest {
	/* 
	 * @author Ben Salis
	 *  tests will cover all methods/cases in the Garden class
	 *  and check for inconsistencies
	 *  
	 */
	Garden greenhouse = new Garden();

	@Before
	public void setup() {
		greenhouse.setProtPlants();
		greenhouse.setMoistureType("Dry"); 
		greenhouse.setSoilType("Loam");
		greenhouse.setLightType("Half");
		greenhouse.setBudget(1000);
		greenhouse.setCurrentSpendings(530);
		greenhouse.launchFilters();
	}
	


	@Test 
	public void testLaunchFiltersSoil() { //also indirectly tests each filter
		greenhouse.getFinalFilteredPlants().forEach(item -> {
			assertEquals(item.getSoil(), Soil.LOAM) ;

		});
	}

	
	@Test
	public void testCalculateBudget() {
		greenhouse.setCurrentBudget(greenhouse.calculateCurrentBudget());
		assertEquals(greenhouse.getCurrentBudget(),470);
		
	}
	
/*
	@Test
	public void testMoistureFilter() { 
		greenhouse.moistureFilter();
		greenhouse.getMoistureFilteredPlants().forEach(item -> {
			assert item.getAtmosphericHumidity() >= 4 && item.getAtmosphericHumidity() <= 6;
			if(item.getSciName() == "Rosa rubiginosa") {// looks for rosa rubiginosa, which has a medium moisture type
				System.out.println(item +" needs medium moisture, and was caught by the filter");
			}
				});	
		//System.out.println(greenhouse.getMoistureFilteredPlants());
	}
*/
	
}
