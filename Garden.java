package mainPkg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.simple.parser.ParseException;

@SuppressWarnings("serial")
public class Garden implements java.io.Serializable{
	
	private String soil;
	private String sunlight;
	private String moisture;
	private double gardenWidth;
	private double gardenHeight;
	private double plantGardenHeight;
	private double plantGardenWidth;
	private Moisture selectedMoisture;
	private Soil selectedSoil;
	private Sunlight selectedSunlight;
	private int budget;

	private int currentBudget=budget;
	private int currentSpendings=0;
	private long lepCount=0;
	

	private GardenCompare compare = new GardenCompare();
	private ArrayList<Plant> allPlants = new ArrayList<Plant>();
	private ArrayList<Plant> moistureFilteredPlants = new ArrayList<Plant>();
	private ArrayList<Plant> soilFilteredPlants = new ArrayList<Plant>();
	private ArrayList<Plant> finalFilteredPlants = new ArrayList<Plant>();
	private ArrayList<Plant> gardenPlants = new ArrayList<Plant>();


	/*
	 * private ArrayList<String> plantNames = new ArrayList<String>(Arrays.asList(
	 * "Acer negundo", "Lyonia mariana", "Achillea millefolium",
	 * "Menispermum canadense", "Allium sativum", "Mentha spicata",
	 * "Alnus glutinosa", "Monarda didyma", "Amelanchier arborea", "Morus alba",
	 * "Amorpha californica", "Myrica pensylvanica", "Aquilegia canadensis",
	 * "Nymphaea odorata", "Asclepias syriaca", "Oenothera biennis",
	 * "Baptisia australis", "Ostrya virginiana", "Betula pendula",
	 * "Persicaria amphibia", "Campanula bononiensis", "Persicaria minor",
	 * "Phoradendron leucarpum", "Carpinus caroliniana", "Pinus strobus",
	 * "Carya glabra", "Platanus occidentalis", "Celastrus scandens", "Poa infirma",
	 * "Celtis occidentalis", "Poa nemoralis", "Chamaedaphne calyculata",
	 * "Poa pratensis", "Chenopodium album", "Poa trivialis",
	 * "Chionanthus virginicus", "Polygonum arenastrum", "Cirsium vulgare",
	 * "Populus nigra", "Clethra alnifolia", "Prunus avium", "Crataegus monogyna",
	 * "Prunus dulcis", "Crocanthemum scoparium", "Prunus persica",
	 * "Decodon verticillatus", "Quercus suber", "Diervilla lonicera",
	 * "Rhododendron tomentosum", "Diospyros virginiana", "Rhus typhina",
	 * "Dirca palustris", "Ribes americanum", "Eubotrys racemosus",
	 * "Rosa rubiginosa", "Fagus sylvatica", "Rubus idaeus",
	 * "Fallopia baldschuanica", "Rudbeckia hirta", "Fallopia convolvulus",
	 * "Ruellia simplex", "Fraxinus excelsior", "Rumex crispus",
	 * "Gaylussacia brachycera", "Saccharum officinarum", "Geranium cinereum",
	 * "Sambucus canadensis", "Geranium endressii", "Sassafras albidum",
	 * "Gleditsia triacanthos", "Solanum tuberosum", "Gymnocladus dioicus",
	 * "Spiraea alba", "Hamamelis virginiana", "Helianthus annuus",
	 * "Staphylea trifolia", "Hibiscus coccineus", "Symphoricarpos albus",
	 * "Hibiscus moscheutos", "Symphyotrichum laeve", "Hordeum vulgare",
	 * "Taxodium distichum", "Hydrangea arborescens", "Teucrium fruticans",
	 * "Hydrangea macrophylla", "Tilia cordata", "Hypericum perforatum",
	 * "Tsuga canadensis", "Impatiens balsamina", "Typha latifolia",
	 * "Juglans cinerea", "Ulmus minor", "Lilium bulbiferum",
	 * "Vaccinium corymbosum", "Liquidambar styraciflua", "Verbena officinalis",
	 * "Lupinus subcarnosus", "Viburnum dentatum", "Lupinus texensis",
	 * "Vitis labrusca", "Lyonia ligustrina", "Zanthoxylum americanum" ));
	 */

	private ArrayList<Integer> plantIDs = new ArrayList<Integer>(Arrays.asList(102772, 103137, 103180, 103457, 103872, 103942, 103971, 104396, 104804, 
			105558, 105968, 106001, 106014, 106050, 108254, 108447, 110497, 111355, 111387, 111819, 129449, 129630, 129684, 129876, 129887, 130377, 130378, 130382, 
			131455, 131508, 131531, 131536, 131616, 131686, 131760, 131769, 132692, 132909, 132923, 132969, 132976, 132989, 133000, 133418, 133450, 136414, 136452, 
			136768, 136924, 137025, 137810, 137842, 137845, 137854, 138707, 138947, 138953, 139010, 139024, 139091, 139201, 139206, 139218, 139236, 139255, 139256, 
			139259, 140901, 141384, 141504, 141537, 141559, 141569, 141571, 141641, 142849, 142893, 143233, 143249, 143261, 143424, 143482, 143716, 143741, 146152, 
			146203, 146208, 146278, 146335, 146350, 147135, 147148, 147165, 147167, 147170, 147364, 148141, 148142, 148154, 149946, 149950, 150411, 167048));
	
	
	/**
	 * Sorts plants in an alphabetical order
	 * @author Michal Bargenda
	 */
	public void sortPlants() {
		Collections.sort(allPlants, compare);
	}
	
	
	

	/**
	 * Launches the filtration methods
	 * @author Michal Bargenda
	 */
	public void launchFilters() {
		moistureFilter();
		soilFilter();
		sunlightFilter();
	}

	/**
	 * Filters all plant
	 * @author Michal Bargenda
	 */
	public void moistureFilter() {

		setSelectedMoisture();
		Iterator<Plant> itr = allPlants.iterator();
		Plant p;
		while(itr.hasNext()) {
			p=itr.next();
			if(p.getMoisture()==selectedMoisture ||p.getMoisture()==Moisture.ALL) {
				moistureFilteredPlants.add(p);
			}
		}
	}
	
	
	/**
	 * Filters all plants for correct soil type of soil
	 * @author Jack Sabagh
	 */
	
	public void soilFilter() {
		setSelectedSoil();
		Iterator<Plant> itr = moistureFilteredPlants.iterator();
		Plant p;
		while(itr.hasNext()) {
			p=itr.next();
			if(p.getSoil()==selectedSoil || p.getSoil()== Soil.ALL) {
				soilFilteredPlants.add(p);
			}
		}
		moistureFilteredPlants.clear();
	}
	
	
	/**
	 * Filters all plants for correct amount of sunlight
	 * @author Jack Sabagh
	 */
	
	public void sunlightFilter() {
		setSelectedSunlight();
		Iterator<Plant> itr = soilFilteredPlants.iterator();
		Plant p;
		while(itr.hasNext()) {
			p=itr.next();
			if(p.getSunlight()==selectedSunlight || p.getSunlight()==Sunlight.ALL) {
				finalFilteredPlants.add(p);
			}
		}
		soilFilteredPlants.clear();
	}
	
	
	/** Iterates through plantID array
	 * <p>
	 * and passes them to Plant constructor. 
	 * Should then add the new Plant objects to protPlants
	 * 
	 * @author Ben Salis
	 */
	public void setProtPlants() {
		plantIDs.forEach((item-> {
			try {
				allPlants.add(new Plant(item));
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}));
	sortPlants();
	}
	
	/**
	 * Sets up the enum value based on user input.
	 * @author Michal Bargenda
	 */
	private void setSelectedMoisture() {
		if(this.moisture.equals("Dry")) {
			selectedMoisture=Moisture.DRY;
		}else if(this.moisture.equals("Medium")) {
			selectedMoisture=Moisture.MEDIUM;
		}else {
			selectedMoisture=Moisture.WET;
		}
	}
	
	
	/**
	 * Sets the enum value to what the user input was for soil type
	 * @author Jack Sabagh
	 */
	private void setSelectedSoil() {
		if(this.soil.equals("Clay")) {
			selectedSoil = Soil.CLAY;
		}else if(this.soil.equals("Loam")) {
			selectedSoil=Soil.LOAM;
		}else {
			selectedSoil=Soil.SANDY;
		}
	}
	
	
	/**
	 * Sets the enum value to what the user input was for the amount of sunlight
	 * @author Jack Sabagh
	 */
	private void setSelectedSunlight() {
		if(this.sunlight.equals("Shade")) {
			selectedSunlight = Sunlight.SHADE;
		}else if(this.sunlight.equals("Half")) {
			selectedSunlight=Sunlight.HALF;
		}else {
			selectedSunlight=Sunlight.FULL;
		}
	}
	
	/**
	 * Determines the ratio of the height to width.
	 * <p>
	 * It is used to scale the plants and garden.
	 * @return the ratio of height to width
	 */
	public double setGardenSize() {
		return this.gardenHeight/this.gardenWidth;
	}
	
	/**
	 * Calculates the amount of the current budget.
	 * @author Michal Bargenda
	 * @return the currentBudget value
	 */
	public int calculateCurrentBudget() {
		this.currentBudget=this.budget-this.currentSpendings;
		return this.currentBudget;
	}
	
	
	/**
	 * @return
	 */
	public double getGardenWidth() {
		return gardenWidth;
	}

	/**
	 * @param gardenWidth
	 */
	public void setGardenWidth(double gardenWidth) {
		this.gardenWidth = gardenWidth;
	}

	/**
	 * @return
	 */
	public double getGardenHeight() {
		return gardenHeight;
	}

	/**
	 * @param gardenHeight
	 */
	public void setGardenHeight(double gardenHeight) {
		this.gardenHeight = gardenHeight;
	}
	
	/**
	 * 
	 * @param soil
	 */
	public void setSoilType(String soil) {
		this.soil = soil;
	}
	
	/**
	 * 
	 * @param sunlight
	 */
	public void setLightType(String sunlight) {
		this.sunlight = sunlight;
	}
	
	/**
	 * 
	 * @param moisture
	 */
	public void setMoistureType(String moisture) {
		this.moisture = moisture;
	}

	public double getPlantGardenHeight() {
		return plantGardenHeight;
	}

	public void setPlantGardenHeight(double plantGardenHeight) {
		this.plantGardenHeight = plantGardenHeight;
	}

	public double getPlantGardenWidth() {
		return plantGardenWidth;
	}

	public void setPlantGardenWidth() {
		this.plantGardenWidth = this.plantGardenHeight*0.75;
	}
	
	
	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public int getCurrentBudget() {
		return currentBudget;
	}

	public void setCurrentBudget(int currentBudget) {
		this.currentBudget = currentBudget;
	}

	public int getCurrentSpendings() {
		return currentSpendings;
	}

	public void setCurrentSpendings(int currentSpendings) {
		this.currentSpendings = currentSpendings;
	}

	public long getLepCount() {
		return lepCount;
	}

	public void setLepCount(long lepCount) {
		this.lepCount = lepCount;
	}

	public Moisture getSelectedMoisture() {
		return selectedMoisture;
	}


	public ArrayList<Plant> getMoistureFilteredPlants() {
		return moistureFilteredPlants;
	}
	
	public Soil getSelectedSoil() {
		return selectedSoil;
	}


	public ArrayList<Plant> getSoilFilteredPlants() {
		return soilFilteredPlants;
	}
	
	
	
	public Sunlight getSelectedSunlight() {
		return selectedSunlight;
	}


	public ArrayList<Plant> getFinalFilteredPlants(){
		return finalFilteredPlants;
	}

	public void setFinalFilteredPlants(ArrayList<Plant> finalFilteredPlants) {
		this.finalFilteredPlants = finalFilteredPlants;
	}

	public void setGardenPlants(ArrayList<Plant> gardenPlants) {
		this.gardenPlants = gardenPlants;
	}

	public void setAllPlants(ArrayList<Plant> allPlants) {
		this.allPlants = allPlants;
	}

	public ArrayList<Plant> getAllPlants() {
		return allPlants;
	}

	public ArrayList<Plant> getGardenPlants() {
		return gardenPlants;
	}
	
	/*
	 * public ArrayList<String> getPlantNames(){ return plantNames; }
	 */

}
