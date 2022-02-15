package mainPkg;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class PlantTest {

	public Plant p1;
	public Plant p2;
	public Plant p3;
	public Plant p4;
	public Garden field;
	public Pane pane;
	
	
	@Before
	public void setup() {
		try {
			p1 = new Plant(105968);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		try {
			p2 = new Plant(131508);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		p1.setStartX(10); p1.setStartY(10);
		field = new Garden();
		pane = new Pane(); pane.setPrefSize(5, 5); 
		field.setPlantGardenHeight(20); field.setPlantGardenWidth();
	}

	@Test
	public void testImageLoader() { //this feels like a setter
		ImageView iv = p1.loadImageToPlant(p1.getSciName() + ".png");
		assertNotNull(iv);
	}

	@Test
	public void testToString() {
		System.out.println(p1);
	}
	

	
	@Test
	public void testSetX() { 
		p1.setX(3.2, pane, p1, field);
		//assertEquals(maple.getX(),3.2);  //this test isn't working. Will keep working on it.
		System.out.println(p1.getX());
	}
	@Test 
	public void testSetXViolation() {
		p1.setX(21.1, pane, p1, field);
		assert p1.getX() != 21.1;
	}
	
	@Test 
	public void testSetY() {
		
	}
}