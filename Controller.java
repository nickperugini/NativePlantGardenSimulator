package mainPkg;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {
	Plant plant;
	Garden garden = new Garden();
	
	View view; 
	
	/**
	 * Prevents users from putting in none number characters to a text field
	 * @author CJ Mitterer
	 */
	UnaryOperator<TextFormatter.Change> filter = change -> {
	    String text = change.getText();
	    if (text.matches("[0-9]*")) {
	        return change;
	    }
	    return null;
	};
	
	/**
	 * Prevents users from putting in none number characters and limits length of user input to 3 characters
	 * This is to be used for the widthTextField and heightTextField
	 * Users can only submit integers ranging from 0-999 meaning 999ft x 999ft is the upper limit of the garden
	 * @author CJ Mitterer
	 */
	UnaryOperator<TextFormatter.Change> sizeLimits = change -> {
		String text = change.getText();
		String wholeWord = change.getControlText();
	    if (text.matches("[0-9]*") && wholeWord.length() < 3 || change.isDeleted()) {
	        return change;
	    }
	    return null;
	};
	
	
	/**
	 * Class constructor.
	 * @param view
	 */
	public Controller(View view){ 
		this.view=view; 
	}
	
	
	
	/**
	 * Handles data save.
	 * @author Michal Bargenda
	 * @return event
	 */
	public EventHandler saveDataHandler() {
		return event -> saveData();
	}

	/**
	 * Saves all the data from the user's input into the model and opens a new window where user will 
	 * create a garden.
	 * @author Michal Bargenda
	 */
	public void saveData() {
		garden.setGardenHeight(Double.parseDouble(view.getHeightTextField().getText()));
		garden.setGardenWidth(Double.parseDouble(view.getWidthTextField().getText()));
		garden.setMoistureType(view.getMoistureMenu().getValue());
		garden.setSoilType(view.getSoilMenu().getValue());
		garden.setLightType(view.getSunMenu().getValue());
		garden.setBudget(Integer.parseInt(view.getBudgetTextField().getText()));
		garden.launchFilters();
		view.setRatio(garden.setGardenSize());
		view.setGardenSize();
		view.populatePlantTable();
	}
	
	
	/**
	 * Calculates current budget and lep count
	 * @author Michal Bargenda
	 */
	public void calcCurrentBudget() {
		view.setCurrentBudget(garden.calculateCurrentBudget());
		view.setCurrentLepCount(garden.getLepCount());
	}
	
	
	/**
	 * Changes the current Spendings and lep count
	 * Boolean Value checks if the user is adding a plant or removing a plant
	 * @author Michal Bargenda
	 * @author CJ Mitterer
	 * @param price, cost of the plant
	 * @param leps, amount of lep species supported
	 * @param addingPlant, based on if this function is called when adding or removing a plant
	 */
	public void changeSpendings(int price, long leps, boolean addingPlant) {
		if(addingPlant == true) {
			garden.setCurrentSpendings(garden.getCurrentSpendings()+price);
			garden.setLepCount(garden.getLepCount()+leps);
		}
		else {
			garden.setCurrentSpendings(garden.getCurrentSpendings()-price);
			garden.setLepCount(garden.getLepCount()-leps);
		}
		
	}
	
	

	
	
	/**
	 * Event handler for the opeWebpage method
	 * @author CJ Mitterer
	 * @return Button click to brin the user to the website
	 */
	public EventHandler openWebpageHandler() {
		
		return event -> {
			try {
				openWebpage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
	
	
	/**
	 * Method to open the web page to the Mt. Cuba Website
	 * @author CJ Mitterer
	 */
	public void openWebpage() throws Exception{
		java.awt.Desktop.getDesktop().browse(new URI("https://mtcubacenter.org/"));
	}
	
	
	/**																				
	 * Handler for the removeplant function to be called in View.java
	 * @author CJ Mitterer
	 * @param p The plant being removed
	 * @return Removes the plant from the garden, readds the cost, and removes the added leps
	 */
	public EventHandler removePlantHandler(Plant p) {
		return event -> removePlant((MouseEvent) event, p);
	}
	
	
	/**
	 * Removes a plant from the garden when right clicked
	 * Also updates the balance, lep count, and current plants list
	 * @author CJ Mitterer
	 * @param p The plant object that determines what is to be removed and what info it has such as cost and lep count
	 * @param event the mouse event that causes this to start, this function will only work for right clicks
	 */
	public void removePlant(MouseEvent event, Plant p) {
		if(event.isSecondaryButtonDown()) {
			changeSpendings(p.getPrice(), p.getLepCount(), false);
			view.calculationsPlantRemoved(p);
			Shape plantSource = (Shape) event.getSource();
			Pane plantPane = (Pane) plantSource.getParent();
			plantPane.getChildren().remove(plantSource);
			garden.getGardenPlants().remove(p);
			view.getNodes().remove(plantSource);
		}
	}
	
	/**
	 * @author Nick Perugini
	 * Event handler for saving the garden
	 */
	public EventHandler saveAllDataHandler() {
		return event -> saveAllData();
	}
	
	
	/**
	 * saveAllData method
	 * @author Nick Perugini
	 * Saves the garden object to src/main/resources as garden.ser
	/**
	public void saveAllData() {
		try {
			FileOutputStream fos = new FileOutputStream("src/main/resources/garden.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);	
			oos.writeObject(garden);
			fos.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Event handler for the loading of the garden
	 * @author Nick Perugini
	 */
	public EventHandler loadAllDataHandler() {
		return event -> loadAllData();
	}
	
	
	/**
	 * loadAllData method
	 * @author Nick Perugini
	 * @author Michal Bargenda
	 * Opens file chooser where garden file can be selected
	 * Sets attributes of garden object to that of saved garden object
	 * Size of tables is then set and plant table and garden pane are populated
	 * The lep count, the remaining budget, and current plants placed inside of the garden
	 */
	public void loadAllData(){
		try {
			FileInputStream fis = new FileInputStream("src/main/resources/garden.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Garden newGarden = (Garden) ois.readObject();
			garden.setBudget(newGarden.getBudget());
			garden.setGardenHeight(newGarden.getGardenHeight());
			garden.setGardenWidth(newGarden.getGardenWidth());
			garden.setCurrentBudget(newGarden.getCurrentBudget());
			garden.setLepCount(newGarden.getLepCount());
			garden.setGardenPlants(newGarden.getGardenPlants());
			garden.setFinalFilteredPlants(newGarden.getFinalFilteredPlants());
			garden.setAllPlants(newGarden.getAllPlants());
			garden.setBudget(newGarden.getBudget());
			garden.setCurrentSpendings(newGarden.getCurrentSpendings());
			view.setRatio(garden.setGardenSize());
			view.setGardenSize();
			view.populatePlantTable();
			view.currentBudgetAndLepCount();
			for(Plant p: garden.getFinalFilteredPlants()) {
				p.setImg(p);
			}
			 for(Plant p: garden.getGardenPlants()) { 
					p.setImg(p);
					view.setSelectedPlant(p);
					view.restoreOldImage(p, p.getImg());
					view.addPlantToCurrentPlants(p);
			 }
			ois.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles clicks on the garden and clicks on the list
	 * @author CJ Mitterer
	 * @return selects plant upon mouse press
	 */
	public EventHandler selectPlantPressedHandler() {
		return event -> selectPlant((MouseEvent) event);
	}
	
	/**
	 * Selects the plant from the table on left click
	 * @author Michal Bargenda
	 */
	public void selectPlant(MouseEvent e) {
		if(e.isPrimaryButtonDown()) {
			Node temp = (Node) e.getSource();
			ObservableList selectedItems = view.getSelectionModel().getSelectedItems();
			plant = (Plant) selectedItems.get(0);
			garden.getGardenPlants().add(plant);
			changeSpendings(plant.getPrice(), plant.getLepCount(), true);
			view.calculationsPlantAdded(plant);
			view.setSelectedPlant(plant);
			view.createNewImage(plant, plant.getImg());
			view.setX(plant.getStartX());
			view.setY(plant.getStartY());
		}
	}
	
	/**
	 * Sets the size of the plant in the garden.
	 * <p>
	 * Depending on the size of the garden it changes the size of the image of the plant to make it look smaller or bigger.
	 * @author Michal Bargenda
	 * @param p
	 * @param iv
	 */
	public void plantSize(Plant p, ImageView iv) {
		double height = p.getPlantGardenHeight()/(Math.min(garden.getGardenHeight(), garden.getGardenWidth())/80);
		garden.setPlantGardenHeight(height);
		garden.setPlantGardenWidth();
	}
	
	/**
	 * Returns an array containing size of the plant.
	 * @author Michal Bargenda
	 * @return height of the plant arr[0] and width of the plant arr[1]
	 */
	public double[] getPlantSize() {
		double arr[] = new double[2];
		arr[0]=garden.getPlantGardenHeight();
		arr[1]=garden.getPlantGardenWidth();
		return arr;
	}
	
	/**
	 * Handles the drag
	 * @author Michal Bargenda
	 * @param p
	 * @param img
	 * @param pane
	 * @return
	 */
	public EventHandler dragPlantHandler(Plant p, Shape img, Pane pane) {
		return event -> dragPlant((MouseEvent) event, p, img, pane);
	}
	
	/**
	 * Moves the plant around the garden.
	 * @author Michal Bargenda
	 * @param event
	 * @param p
	 * @param img
	 * @param pane
	 */
	public void dragPlant(MouseEvent event, Plant p, Shape img, Pane pane) {
		if(event.isPrimaryButtonDown()) {
			Node n = (Node) event.getSource();
			p.setX(p.getX()+event.getX(), pane, p, garden);
			p.setY(p.getY()+event.getY(), pane, p, garden);
			view.setX(p.getX());
			view.setY(p.getY());
			view.dragImage(p, img);
			System.out.println("Plant x:" + p.getX());
			System.out.println("Plant y:" + p.getY());
		}
	}
	
	/**
	 * Handles the back button
	 * @param pane
	 * @return reset garden
	 */
	public EventHandler getResetGardenHandler(Pane pane) {
		return event -> resetGarden(pane);
	}
	
	/**
	 * Resets the garden and the view.
	 * <p>
	 * After user decides to go back to the input screen, it resets all values and plants.
	 * @author Michal Bargenda
	 * @param pane
	 */
	public void resetGarden(Pane pane) {
		System.out.println("here");
		pane.getChildren().clear();
		Iterator<Plant> itr = garden.getGardenPlants().iterator();
		Plant p;
		while(itr.hasNext()) {
			p = itr.next();
			p.setFrequency(p.getFrequency()-1);
		}
		garden.getGardenPlants().clear();
		garden.setCurrentSpendings(0);
		garden.setLepCount(0);
		garden.getMoistureFilteredPlants().clear();
		garden.getFinalFilteredPlants().clear();
		calcCurrentBudget();
		view.getCurrentPlantsTable().getItems().clear();
		view.currentBudgetAndLepCount();
		view.resetTable();
	}
	
	
	
	/**
	 * Creates an instance of all plants.
	 * <p>
	 * Method is called on start of the application to ensure higher speeds.
	 * @author Michal Bargenda
	 */
	public void setAllPlants() {
		garden.setProtPlants();
	}
	
	/**
	 * @author Michal Bargenda
	 * @return the ArrayList of plants that follow user's requirements
	 */
	public ArrayList<Plant> getProtPlant() {
		return garden.getFinalFilteredPlants();
	}

	/**
	 * @author Michal Bargenda
	 * @return the ArrayList of plants added to the garden.
	 */
	public ArrayList<Plant> getGardenPlants(){
		return garden.getGardenPlants();
	}
	
	
}
