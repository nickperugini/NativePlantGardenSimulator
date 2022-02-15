package mainPkg;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GardenCompareTest {
	Plant p1;
	Plant p2;
	GardenCompare gc = new GardenCompare();

	@Before
	public void setUp() throws Exception {
		p1 = new Plant(131531);
		p2 = new Plant(106001);
	}

	/**
	 * 
	 * tests compare mechanism. 
	 * @author Ben Salis
	 */
	@Test
	public void testCompare() {
		int compVal1 = gc.compare(p2,p1);
		int compVal2 = gc.compare(p1,p2);
		assert compVal1 < 0 && compVal2 > 0;
	}

}
