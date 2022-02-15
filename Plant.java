package mainPkg;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

@SuppressWarnings("serial")
public class Plant extends TilePane implements java.io.Serializable{

	private String comName;
	private String sciName;
	//private String URL;
	private String plantType;
	private long spread;
	//private long phMinimum;
	private long phMax;
	private long light;
	private String soilType;
	private long atmosphericHumidity;
	private long lepCount;
	private int price;
	//private double avgHeight;
	private boolean isWoody;
	transient private ImageView img;
	private int frequency;
	private final int plantHeight = 50;
	private final int plantWidth = 50;
	private double x;
	private double y;
	private double startX = 10;
	private double startY = 10;
	private double plantGardenWidth;
	private double plantGardenHeight;
	private Moisture moisture;
	private Sunlight sunlight;
	private Soil soil;

	/**
	 * Class constructor.
	 * <p>
	 * uses data collected from parser stored in plantData.json to
	 * 
	 * @author Nick Perugini
	 * @author Michal Bargenda
	 * @param sciName
	 * @param isWoody
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	/*
	 * public Plant(String sciName) throws FileNotFoundException, IOException,
	 * ParseException {
	 * 
	 * JSONParser parser = new JSONParser(); try { Object obj = parser.parse(new
	 * FileReader("plantData.json")); this.sciName = sciName; JSONObject jo =
	 * (JSONObject) obj; this.URL = (String) jo.get(sciName + " URL"); this.spread =
	 * 0; // Setting spread to 0 until we figure out what to do with none argument
	 * this.phMinimum = (long) jo.get(sciName + " Minimum ph"); this.phMax = (long)
	 * jo.get(sciName + " Maximum ph"); this.light = (long) jo.get(sciName +
	 * " Light"); this.sunlight=setSunlight(light); //this.soilType = (long)
	 * jo.get(sciName + " Soil"); //this.soil=setSoil(soilType);
	 * this.atmosphericHumidity = (long) jo.get(sciName + " Moisture");
	 * this.moisture=setMoisture(atmosphericHumidity); this.avgHeight = (long)
	 * jo.get(sciName + " Average Height"); String path = (String) jo.get(sciName +
	 * " URL"); this.img = loadImageToPlant(path); this.comName = (String)
	 * jo.get(sciName + " Common Name"); String a[] = sciName.split(" ");
	 * this.lepCount = lepCountLoader(a[0]); this.isWoody = typeLoader(a[0]); if
	 * (isWoody) { this.price = 20; this.plantType = "Woody"; this.plantGardenHeight
	 * = 50; this.plantGardenWidth = this.plantGardenHeight * 0.75; } else {
	 * this.price = 6; this.plantType = "Herbacious"; this.plantGardenHeight = 20;
	 * this.plantGardenWidth = this.plantGardenHeight * 0.75;
	 * 
	 * } } catch (FileNotFoundException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); } catch (ParseException e) {
	 * e.printStackTrace(); } }
	 */


	/**
	 * Class Constructor
	 * <p>
	 * Takes in an Integer and casts it as a string for parsing 
	 * the restrictedData json. Will assign data from json to attributes
	 * 
	 * @author Ben Salis
	 * @param plantID from plantID array
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws NullPointerException
	 */
	public Plant(Integer plantID) throws FileNotFoundException, IOException, ParseException, NullPointerException{
		String id = Integer.toString(plantID);
		JSONParser jspar = new JSONParser();
		
		try {
			Object obj = jspar.parse(new FileReader("restrictedData.json"));
			JSONObject plantInfo = (JSONObject) obj;
			this.sciName = (String) plantInfo.get(id + " scientific_name");
			this.comName = (String) plantInfo.get(id + " common_name");
			this.spread = 10; //we can change this for plant type
			double a;
			a = (double) plantInfo.get(id + " light");
			this.light = (long) a;
			a = (double) plantInfo.get(id + " atmospheric_humidity");
			this.atmosphericHumidity = (long) a;
			this.soilType = readSoil(sciName);
			this.moisture = setMoisture(atmosphericHumidity);
			this.sunlight = setSunlight(light);
			this.soil = setSoil(soilType);
			String type = (String) plantInfo.get(id + " herb_or_woody");
			if(type.equals("WOOD")) {
				this.isWoody = true;
				this.plantType = "Woody";
				this.price = 20;
				this.plantGardenHeight = 50;
				this.plantGardenWidth = this.plantGardenHeight * 0.75;
			}
			
			else if(type.equals("HERB")) {
				this.isWoody = false;
				this.plantType = "Herbaceous";
				this.price = 6;
				this.plantGardenHeight = 20;
				this.plantGardenWidth = this.plantGardenHeight * 0.75;
				
			}
			
			this.lepCount = (long) plantInfo.get(id + " lep_count");
			try {
			this.img = loadImageToPlant(this.sciName + ".png");
			}
			catch(NullPointerException e) {
				System.out.println(this.sciName);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(id);
		}
	}
	

	/**
	 * Depending on the values from the JSON file, sets up the enum value for the plant
	 * @param atmosphericHumidity
	 * @return the enum determining what moisture level the plant needs.
	 */
	public Moisture setMoisture(long atmosphericHumidity) {
		if ((atmosphericHumidity >= 0) && (atmosphericHumidity <= 3)) {
			return Moisture.DRY;
		} else if ((atmosphericHumidity >= 4) && (atmosphericHumidity <= 6)) {
			return Moisture.MEDIUM;
		}
		else if (atmosphericHumidity ==-1) {
			return Moisture.ALL;
		}else {
			return Moisture.WET;
		}
	}
	
	
	/**
	 * Based off the plants soil values in the JSON, this determines which enum value the plant falls under.
	 * @param soilType
	 * @return the enum determining what soil type the plant requires.
	 * @author Jack Sabagh
	 */
	
	public Soil setSoil(String soilType) {
		if(soilType.equals("Clay")){
			return Soil.CLAY;
		}
		else if(soilType.equals("Loam")) {
			return Soil.LOAM;
		}
		else if(soilType.equals("Sandy")) {
			return Soil.SANDY;
		}
		else {
			return Soil.ALL;
		}
	}
	
	
	/**
	 * Based off the plants sunlight values in the JSON, this determines which enum value the plant falls under.
	 * @param sunlightType
	 * @return the enum determining how much sunlight the plant requires.
	 * @author Jack Sabagh
	 */
	
	public Sunlight setSunlight(long sunlightType) {
		if((sunlightType >= 0) && (sunlightType < 4)){
			return Sunlight.SHADE;
		}
		else if((sunlightType >= 4) && (sunlightType <7)) {
			return Sunlight.HALF;
		}
		else if(sunlightType == -1) {
			return Sunlight.ALL;
		}
		else {
			return Sunlight.FULL;
		}
	}

	/**
	 * Returns the image view with the image of the plant.
	 * <p>
	 * The correct name of the file should be genera_specie.png, for example
	 * "Amorpha_californica.png", Method will retrive the picure from img folder.
	 * 
	 * @author Michal Bargenda
	 * @param path
	 * @return the ImageView of the plant
	 */
	public ImageView loadImageToPlant(String path) {
		Image image = new Image(getClass().getResourceAsStream("/img/" + path));
		ImageView imv = new ImageView(image);
		imv.setPreserveRatio(true);
		imv.setFitHeight(plantHeight);
		return imv;
	}

	/**
	 * Finds the number of leps from the xlsx file.
	 * <p>
	 * Using apache poi it checks all genera names to find the plant and eventually
	 * return the number of leps it supports.
	 * 
	 * @author Michal Bargenda
	 * @param sciName
	 * @return the number of leps supported by the plant
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public int lepCountLoader(String sciName) throws EncryptedDocumentException, IOException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("All_plants.xlsx");
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell nameCell = row.getCell(1);
			String name = nameCell.getStringCellValue();
			if (name.equals(sciName)) {
				Cell lepCell = row.getCell(4);
				return (int) lepCell.getNumericCellValue();
			}
		}

		return 0;
	}

	/**
	 * Finds if plant is woody or not from the xlsx file.
	 * <p>
	 * Using apache poi it checks all genera names to find the plant and eventually
	 * return the boolean which determines if plant is woody or not.
	 * 
	 * @author Michal Bargenda
	 * @param sciName
	 * @return wheter the plant is woody or not.
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public boolean typeLoader(String sciName) throws EncryptedDocumentException, IOException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("All_plants.xlsx");
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell nameCell = row.getCell(1);
			String name = nameCell.getStringCellValue();
			if (name.equals(sciName)) {
				Cell lepCell = row.getCell(3);
				String type = lepCell.getStringCellValue();
				switch (type) {
				case "h":
					return false;
				case "w":
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * 
	 * Called in the constructor to read the Soil data json
	 * <p>
	 * Once given a scientific name it will return the soil type
	 * for that plant
	 * 
	 * @author Ben Salis
	 * @param  The scientific name of the plant
	 * @return string of soil type
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws NullPointerException
	 */
	public String readSoil(String sci) throws FileNotFoundException, IOException, ParseException, NullPointerException{
		JSONParser par = new JSONParser();
		String dirt = "soil";
		try {
			Object obj = par.parse(new FileReader("soilData.json"));
			JSONObject plantInfo = (JSONObject) obj;
			dirt = (String) plantInfo.get(sci);
			return dirt;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return dirt;
	}
	/**
	 * Sets the x location of the plant.
	 * <p>
	 * Limits the plant location to be inside the garden.
	 * 
	 * @author Michal Bargenda
	 * @param x    x coordinate of the plant
	 * @param pane
	 */
	public void setX(double x, Pane pane, Plant p, Garden g) {
		if (x > 0 && x < pane.getWidth() - (g.getPlantGardenWidth()))
			this.x = x;
	}
	
	/**
	 * Sets the y location of the plant.
	 * <p>
	 * Limits the plant location to be inside the garden.
	 * 
	 * @author Michal Bargenda
	 * @param y    y coordinate of the plant
	 * @param pane
	 */
	public void setY(double y, Pane pane, Plant p, Garden g) {
		if (y > 0 && y < pane.getHeight() - (g.getPlantGardenHeight()))
			this.y = y;
	}


	public void setImg(Plant p) {
		this.img = loadImageToPlant(p.getSciName()+".png");
	}
	
	@Override
	public String toString() {
		return this.comName;
	}

	public String getComName() {
		return comName;
	}
	public String getSciName() {
		return sciName;
	}
	public long getLepCount() {
		return lepCount;
	}

	public int getPrice() {
		return price;
	}
	
	public ImageView getImg() {
		return img;
	}


	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public double getX() {
		return x;
	}

	
	public double getY() {
		return y;
	}

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

	public double getPlantGardenHeight() {
		return plantGardenHeight;
	}


	public Moisture getMoisture() {
		return moisture;
	}
	
	public Soil getSoil() {
		return soil;
	}

	public Sunlight getSunlight() {
		return sunlight;
	}
	
	public long getAtmosphericHumidity() {
		return atmosphericHumidity;
	}


	public String getSoilType() {
		return soilType;
	}

	public long getLight() {
		return light;
	}
}
