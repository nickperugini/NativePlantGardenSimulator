package mainPkg;

import java.io.IOException;
import java.util.Iterator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.TableView;


public class View extends Application {

	final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth(); // Width of a window
	final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight()*0.95; // Height of a window
	private double screenRatio = WIDTH/HEIGHT;
	final int plantHeight = 50;
	final int plantWidth = 50;
	private Stage window; // The main stage

	// Intro attributes
	private Scene scene; // Intro window scene
	private StackPane spIntro = new StackPane(); // The parent Pane
	private VBox buttonBox = new VBox();
	private Button createButton = new Button();
	private Button loadButton = new Button();
	private	Button moreInfoButton = new Button(); //This button links the user to more information

	// Create garden attributes
	private Scene scene2; // Create Garden scene
	private BorderPane createRoot = new BorderPane(); // The parent Pane
	private GridPane gpCreateGarden = new GridPane(); // Contains fields for user input

	private Label sizeLabel = new Label("Size");
	private Label footLabel1 = new Label("ft");
	private Label footLabel2 = new Label("ft");
	private Label sunlightLabel = new Label("Sunlight Level");
	private Label soilLabel = new Label("Soil Type");
	private Label moistureLabel = new Label("Moisture Level");
	private Label budgetLabel = new Label("Budget $");
	private Label titleLabel = new Label("Garden Design Simulator");
	
	private TextField widthTextField = new TextField();
	private TextField heightTextField = new TextField();
	private TextField budgetTextField = new TextField();
	
	private ComboBox<String> sunMenu = new ComboBox<String>();
	private ComboBox<String> soilMenu = new ComboBox<String>();
	private ComboBox<String> moistureMenu = new ComboBox<String>();

	// private Button infinityButton = new Button("infinity");
	private Button continueButton = new Button("Create Garden!");
	private Button backButton = new Button("Return to Garden Creation");
	private Button backButton2 = new Button("Return to Main Menu");
	
	
	// Info for warning tab
	private Label warningLabel = new Label("WARNING: You have exceeded your maximum budget.");
	private Label tipLabel = new Label("TIP: right-click to remove plants.");
	private Button closeButton2 = new Button("Close");

	// Garden design attributes
	private Scene scene3; // Garden Design scene

	private BorderPane bp = new BorderPane(); // The parent Pane
	private AnchorPane apGarden = new AnchorPane(); // Pane of the garden

	public void setApGarden(AnchorPane apGarden) {
		this.apGarden = apGarden;
	}

	private BorderPane bottomPane = new BorderPane();
	private BorderPane topPane = new BorderPane();
	private BorderPane bpCurrent = new BorderPane();
	private GridPane current = new GridPane();
	
	private Label currentBudgetLabel = new Label("Current budget: $");
	private Label budgetValueLabel = new Label();
	private Label lepCountLabel = new Label("Current Lep Count: ");
	private Label lepCountValueLabel = new Label();

	private Button currentPlantsButton = new Button("Current Plants");
	private Button saveGardenButton = new Button("Save");

	private int currentBudget;
	private long currentLepCount;
	private Plant selectedPlant;
	
	private StackPane searchPane = new StackPane();
	private TextField searchField = new TextField();
	

	private double x;
	private double y;
	private double xTrans;
	private double yTrans;
	private boolean isUsed;
	private boolean alreadyShown = false;
	private double ratio;
	private double gardenWidth;
	private double gardenHeight;
	private double gardenWidthNoScale;
	private double gardenHeightNoScale;

	private TableView plantTable = new TableView();
	TableViewSelectionModel selectionModel;
	private ArrayList<Shape> nodes = new ArrayList<Shape>();

	// Current plants window
	private TableView<Plant> currentPlantsTable = new TableView<Plant>();
	private Button closeButton = new Button("Close");
	

	private Controller controller;

	/**
	 * Class constructor.
	 */
	public View() {
		controller = new Controller(this);
	}

	/**
	 * Sets the starting scene and calls methods creating other scenes used in the
	 * program.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		controller.setAllPlants();
		createIntroScene();
		createCreateGardenScene();
		createGardenDesignScene();

		window.setResizable(false);
		window.setScene(getScene());
		window.show();
	}

	/**
	 * Launches the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch();
	}

	/**
	 * Method creates a scene for the beginning window.
	 * @author Michal Bargenda
	 */
	private void createIntroScene() {
		setScene(new Scene(spIntro, WIDTH, HEIGHT));
		
		spIntro.getChildren().add(titleLabel);
		titleLabel.setFont(new Font("Impact", 80));
		titleLabel.setTextFill(Color.YELLOW);
	    StackPane.setAlignment(titleLabel, Pos.TOP_CENTER);
	    titleLabel.setTranslateY(HEIGHT * .125);

		
		createButton.setText("Create New Garden");
		loadButton.setText("Load an existing Garden");
		moreInfoButton.setText("More Information");

		StackPane.setAlignment(buttonBox, Pos.CENTER);
		
		buttonBox.setMaxHeight(200);
		buttonBox.setMaxWidth(450);
		buttonBox.setSpacing(25);
		buttonBox.setPadding(new Insets(15, 12, 15, 12));
		spIntro.getChildren().add(buttonBox);
		buttonBox.getChildren().add(createButton);
		buttonBox.getChildren().add(loadButton);
		buttonBox.getChildren().add(moreInfoButton);
		
		createButton.setStyle("-fx-font-size:25");
		loadButton.setStyle("-fx-font-size:25");
		moreInfoButton.setStyle("-fx-font-size:25");

		createButton.setOnMouseClicked(e -> window.setScene(scene2));
		createButton.setPrefWidth(800);
		createButton.setPrefHeight(100);
		loadButton.setPrefWidth(800);
		loadButton.setPrefHeight(100);
		
		moreInfoButton.setPrefWidth(800);
		moreInfoButton.setPrefHeight(100);
		moreInfoButton.setOnMouseClicked(controller.openWebpageHandler());					
		
		loadButton.setOnMousePressed(controller.loadAllDataHandler());
		loadButton.setOnMouseReleased(e -> window.setScene(scene3));
		
		BackgroundImage myBI= new BackgroundImage(new Image("/img/createRoot.jpg", (int) Screen.getPrimary().getVisualBounds().getWidth(),(int) Screen.getPrimary().getVisualBounds().getHeight(),true,false),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
		          BackgroundSize.DEFAULT);
		spIntro.setBackground(new Background(myBI));

	}

	/**
	 * Creates a scene for the garden design. The parent pane is bp which is a
	 * BorderPane.
	 * <p>
	 * At the center there is a AnchorPane apGarden which is where the garden is. To
	 * the left ther is a TableView plant Table which is a plant selection menu. On
	 * the top there is a BorderPane bpCurrent which is modified by
	 * currentBudgetAndLepCountDisplay() On the bottom there is a BorderPane
	 * bottomPane which contains a Button currentPlantsButton
	 * 
	 * @author Michal Bargenda
	 * @author Nick Perugini
	 */
	public void createGardenDesignScene() {
		scene3 = new Scene(bp, WIDTH, HEIGHT);
		bp.setCenter(apGarden);
		


		bp.setTop(topPane);
		topPane.setCenter(plantTable);
		plantTable.setOnMousePressed(controller.selectPlantPressedHandler());

		currentBudgetAndLepCountDisplay();

		populateCurrentPlantsTable();
		searchBar();
		bp.setBottom(bottomPane);
		bottomPane.setCenter(currentPlantsButton);
		bottomPane.setRight(saveGardenButton);
		currentPlantsButton.setTranslateX(WIDTH * -.012);
		currentPlantsButton.setTranslateY(HEIGHT * -.02);
		currentPlantsButton.setOnAction(e -> currentPlantsWindow());
		currentPlantsButton.setStyle("-fx-margin: 10;");
		
		saveGardenButton.setTranslateX(WIDTH * -.012);
		saveGardenButton.setTranslateY(HEIGHT * -.02);
		saveGardenButton.setStyle("-fx-margin: 10;");
		saveGardenButton.setOnMousePressed(controller.saveAllDataHandler());
		
		bottomPane.setLeft(backButton);
		backButton.setTranslateX(WIDTH * .012);
		backButton.setTranslateY(HEIGHT * -.02);
		backButton.setOnMousePressed(controller.getResetGardenHandler(apGarden));
		backButton.setOnMouseReleased(e->window.setScene(scene2));
		
		// Changes size of back button and current plants button
		backButton.setStyle("-fx-font-size:20");
		backButton.setPrefWidth(250);
		backButton.setPrefHeight(30);
		
		// Changes size of button
		currentPlantsButton.setStyle("-fx-font-size:20");
		currentPlantsButton.setPrefWidth(150);
		currentPlantsButton.setPrefHeight(30);
		
		saveGardenButton.setStyle("-fx-font-size:20");
		saveGardenButton.setPrefWidth(150);
		saveGardenButton.setPrefHeight(30);
		
		apGarden.setStyle("-fx-background-color: green");

	}
	
	/**
	 * Sets the size of the garden so it reflects the size inputted by the user.
	 * <p>
	 * sizing is used to make sure that the whole garden fits on the screen. the ratio values make sure that
	 * garden's size ratio is the same as the one inputted by the user.
	 * @author Michal Bargenda
	 */
	public void setGardenSize() {
		double sizing=0.3;
		if(ratio<=0.8) {
			sizing=0.7;
		}else if(ratio>0.8&&ratio<1.2) {
			sizing = 0.3;
		}
		gardenWidth = WIDTH * sizing;
		gardenHeight = HEIGHT * sizing * screenRatio*ratio;
		gardenWidthNoScale = WIDTH;
		gardenHeightNoScale = HEIGHT * screenRatio;
		apGarden.setMaxSize(gardenWidth, gardenHeight);
	}

	/**
	 * Creates an ImageView of a plant on the garden.
	 * <p>
	 * When a plant is selected it adds an ImageView to the garden at (x,y)
	 * coordinates. Sets up action handlers for mouse events allowing to drag and
	 * drop the plant.
	 * 
	 * @author Michal Bargenda
	 * @param img the ImageView of the plant added to the garden.
	 */
	public void createNewImage(Plant p, ImageView img) {
		controller.plantSize(p, img);
		double height = controller.getPlantSize()[0];
		double width = controller.getPlantSize()[1];
		
		//These three lines create a circle plant node that mostly collides with the border
		Circle image = new Circle((height/2), new ImagePattern(img.getImage()));
		image.setCenterX(height/2);
		image.setCenterY(width/2);
		
		apGarden.getChildren().add(image);
		image.setOnMousePressed(controller.dragPlantHandler(p, image, apGarden));
		image.setOnMouseDragged(controller.dragPlantHandler(p, image, apGarden));
		image.setOnMousePressed(controller.removePlantHandler(p));
		nodes.add(image);
	}
	
	public void restoreOldImage(Plant p, ImageView img) {
		controller.plantSize(p, img);
		double height = controller.getPlantSize()[0];
		double width = controller.getPlantSize()[1];
		Circle image = new Circle((height/2), new ImagePattern(img.getImage()));
		apGarden.getChildren().add(image);
		image.setOnMousePressed(controller.dragPlantHandler(p, image, apGarden));
		image.setOnMouseDragged(controller.dragPlantHandler(p, image, apGarden));
		image.setOnMousePressed(controller.removePlantHandler(p));
		nodes.add(image);
		image.setLayoutX(p.getX());
		image.setLayoutY(p.getY());
	}

	/**
	 * Displays currentBudget and currentLepCount in the top right corner.
	 * <p>
	 * Sets up a BorderPane bpCurrent on top of the bp. Adds a GridPane current to
	 * the right of the bpCurrent containing labels.
	 * 
	 * @author Michal Bargenda
	 */
	private void currentBudgetAndLepCountDisplay() {
		bp.setLeft(bpCurrent);
		bpCurrent.setCenter(current);
		bpCurrent.setMaxHeight(HEIGHT*0.1);
		current.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: green;");
		current.add(currentBudgetLabel, 1, 1);
		current.add(budgetValueLabel, 2, 1);
		budgetValueLabel.setTextFill(Color.GREEN);
		current.add(lepCountLabel, 1, 2);
		current.add(lepCountValueLabel, 2, 2);
		
		currentBudgetLabel.setFont(new Font("Arial", 30));
		budgetValueLabel.setFont(new Font("Arial", 30));
		lepCountLabel.setFont(new Font("Arial", 30));
		lepCountValueLabel.setFont(new Font("Arial", 30));
	}
	
	/**
	 * Calls methods modifying currentBudget and currentLepCount as well as
	 * increments the frequency of how many times a certain type of plant has been
	 * used.
	 * 
	 * @author Michal Bargenda
	 * @param plant the plant that has been added to the garden
	 */
	public void calculationsPlantAdded(Plant plant) {
		currentBudgetAndLepCount();
		addPlantToCurrentPlants(plant);
		plant.setFrequency(plant.getFrequency() + 1);
	}

	/**
	 * Calls methods modifying currentBudget and currentLepCount as well as
	 * increments the frequency of how many times a certain type of plant has been
	 * used but for plant removal
	 *  @author CJ Mitterer
	 *  @param plant the plant that has been removed from the garden
	 */
	public void calculationsPlantRemoved(Plant plant) {
		currentBudgetAndLepCount();
		plant.setFrequency(plant.getFrequency() - 1);
		removePlantToCurrentPlants(plant);
	}
	
	/**
	 * Calls a method from the controller calculating currentBudget and
	 * currentLepCount.
	 * <p>
	 * Changes the values of the labels displaying the values of currentBudget and
	 * currentLepCount.
	 * 
	 * @author Michal Bargenda
	 */
	public void currentBudgetAndLepCount() {
		controller.calcCurrentBudget();
		checkOverBudget(currentBudget);
		budgetValueLabel.setText(String.valueOf(currentBudget));
		lepCountValueLabel.setText(String.valueOf(currentLepCount));
	}


	/**
	 * Changes the x an y of an ImageView that is being dragged.
	 * 
	 * @author Michal Bargenda
	 * @param img the ImageView that is being dragged
	 */
	public void dragImage(Plant p, Shape img) {
		xTrans =  x;
		yTrans =  y;
		img.setTranslateX(xTrans);
		img.setTranslateY(yTrans);
		checkBounds(img);
	}
	
	/**
	 * Checks if two images collide with each other.
	 * <p>
	 * If two images collide with each other the picture changes color to black and white.
	 * @author Michal Bargenda
	 * @param block
	 */
	private void checkBounds(Shape block) {
		  boolean collisionDetected = false;
		  ColorAdjust monochrome = new ColorAdjust();
	      monochrome.setSaturation(-1.0);
	      for (Shape static_bloc : nodes) {
	    	    if (static_bloc != block) {
	    	    	block.setEffect(null);
	    	      if (block.getBoundsInParent().intersects(static_bloc.getBoundsInParent())) {
	    	        collisionDetected = true;
	    	      }
	    	    }
	    	  }
		  if (collisionDetected) {
		    block.setEffect(monochrome);
		  } else {
			  block.setEffect(null);
		  }
		}
	
	/**
	 * Populates the TableView with plant names and pictures.
	 * 
	 * @author Michal Bargenda
	 */
	public void populatePlantTable() {
		
		
		TableColumn<Plant, String> columnSciName = new TableColumn<>("Scientific Name");
		columnSciName.setCellValueFactory(new PropertyValueFactory<>("sciName"));
		columnSciName.setStyle("-fx-font-size:25px;"+"-fx-alignment: center;");
		
		TableColumn<Plant, String> columnComName = new TableColumn<>("Common Name");
		columnComName.setCellValueFactory(new PropertyValueFactory<>("comName"));
		columnComName.setStyle("-fx-font-size:25px;"+"-fx-alignment: center;");
		
		TableColumn<Plant, ImageView> columnImg = new TableColumn<>("Image");
		columnImg.setCellValueFactory(new PropertyValueFactory<>("img"));
		columnImg.setStyle("-fx-font-size:25px;"+"-fx-alignment: center;");
		
		TableColumn<Plant, String> columnType = new TableColumn<>("Type");
		columnType.setCellValueFactory(new PropertyValueFactory<>("plantType"));
		columnType.setStyle("-fx-font-size:25px;"+"-fx-alignment: center;");
		
		TableColumn<Plant, String> columnLeps = new TableColumn<>("Leps supported");
		columnLeps.setCellValueFactory(new PropertyValueFactory<>("lepCount"));
		columnLeps.setStyle("-fx-font-size:25px;"+"-fx-alignment: center;");
		columnLeps.setPrefWidth(190);
				
		
		plantTable.getColumns().add(columnImg);
		plantTable.getColumns().add(columnSciName);
		plantTable.getColumns().add(columnComName);		
		plantTable.getColumns().add(columnType);
		plantTable.getColumns().add(columnLeps);
	
		
		plantTable.getItems().addAll(controller.getProtPlant());
		
		selectionModel = plantTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		
		plantTable.setMaxHeight(HEIGHT*0.25);
		plantTable.setMaxWidth(WIDTH*0.9);
		
	}
	
	/**
	 * Creates a dynamic search bar.
	 * @author Michal Bargenda
	 */
	private void searchBar() {
		topPane.setTop(searchPane);
		searchPane.getChildren().addAll(searchField);
		searchField.setPromptText("SEARCH");
		searchField.textProperty().addListener((observable, oldValue, newValue) ->
	    plantTable.setItems(filterList(controller.getProtPlant(), newValue))
	);
	}
	
	/**
	 * Search for inputted sequene of characters in the list of plants.
	 * <p>
	 * https://edencoding.com/search-bar-dynamic-filtering/
	 * @author Michal Bargenda
	 * @param plant
	 * @param searchText
	 * @return whether there is a chracter used in any of the plants.
	 */
	private boolean searchFindsPlants(Plant plant, String searchText){
	    return (plant.getSciName().toLowerCase().contains(searchText.toLowerCase())) ||
	            (plant.getComName().toLowerCase().contains(searchText.toLowerCase()));
	}
	
	/**
	 * Goes through the list of plants searching for those cointaing searched sequence of characters.
	 * <p>
	 * https://edencoding.com/search-bar-dynamic-filtering/
	 * @author Michal Bargenda
	 * @param list
	 * @param searchText
	 * @return an observable list of plants with the character inputted.
	 */
	private ObservableList<Plant> filterList(List<Plant> list, String searchText){
	    List<Plant> filteredList = new ArrayList<>();
	    for (Plant plant : list){
	        if(searchFindsPlants(plant, searchText)) filteredList.add(plant);
	    }
	    return FXCollections.observableList(filteredList);
	}
	
	
	/**
	 * Resets the plantTable
	 * @author Michal Bargenda
	 */
	public void resetTable() {
		plantTable.getColumns().clear();
		plantTable.getItems().clear();
		nodes.clear();
	}


	/**
	 * Creates a pop out window.
	 * <p>
	 * The popout window contains a table view with current plants used in the garden.
	 * @author Michal Bargenda
	 */
	private void currentPlantsWindow() {
		Stage stage = new Stage();
		BorderPane bp = new BorderPane();
		BorderPane bottom = new BorderPane();

		Scene scene = new Scene(bp, 300, 300);
		bp.setCenter(currentPlantsTable);
		bp.setBottom(bottom);
		bottom.setRight(closeButton);
		closeButton.setOnMouseClicked(e -> stage.close());
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Creates warning pop out window when the user goes over the budget.
	 * @author Jack Sabagh
	 */
	private void overBudgetWindow() {
		Stage stage = new Stage();
		BorderPane bp = new BorderPane();
		BorderPane bottom = new BorderPane();
		BorderPane top = new BorderPane();
		
		warningLabel.setFont(new Font("Comfortaa", 20));
		warningLabel.setTextFill(Color.RED);
		tipLabel.setFont(new Font("Comfortaa", 16));
		
		Scene scene = new Scene(bp, 550, 150);
		bp.setCenter(warningLabel);
		bp.setBottom(bottom);
		bp.setTop(top);
		top.setCenter(tipLabel);
		tipLabel.setTranslateY(HEIGHT * .1);
		warningLabel.setTranslateY(HEIGHT * -.025);
		bottom.setCenter(closeButton2);
		closeButton2.setOnMouseClicked(e -> stage.close());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Creates columns in the currentPlantsTable.
	 * @author Michal Bargenda
	 */
	private void populateCurrentPlantsTable() {
		TableColumn<Plant, String> column1 = new TableColumn<>("Common Name");
		column1.setCellValueFactory(new PropertyValueFactory<>("comName"));
		
		TableColumn<Plant, String> column2 = new TableColumn<>("Scientific Name");
		column2.setCellValueFactory(new PropertyValueFactory<>("sciName"));
		

		TableColumn<Plant, String> column3 = new TableColumn<>("Count");
		column3.setCellValueFactory(new PropertyValueFactory<>("frequency"));
		
		TableColumn<Plant, Integer> column4 = new TableColumn<>("Leps Supported");
		column4.setCellValueFactory(new PropertyValueFactory<>("lepCount"));

		currentPlantsTable.getColumns().add(column1);
		currentPlantsTable.getColumns().add(column2);
		currentPlantsTable.getColumns().add(column3);
		currentPlantsTable.getColumns().add(column4);
	}

	/**
	 * Adds a plant to the TableView currentPlantsTable.
	 * <p>
	 * Iterates through a ObservableList of all the names of currently used plants.
	 * If the plant has already been used it will do nothing.
	 * 
	 * @author Michal Bargenda
	 * @param p, a plant added to the garden
	 */
	public void addPlantToCurrentPlants(Plant p) {
		isUsed = false;
		ObservableList<Plant> list = currentPlantsTable.getItems();
		list.forEach(plant -> {
			if (plant.equals(p)) {
				isUsed = true;
			}
		});
		if (!isUsed)
			currentPlantsTable.getItems().add(p);
	}
	
	
	
	/**
	 * Called when removing a plant, checks if 0 plants are in a spot in current plants
	 * Removes the spot on the table if at 0
	 * @author CJ Mitterer
	 * @param p the plant that is being removed
	 */
	private void removePlantToCurrentPlants(Plant p) {
		ObservableList<Plant> list = currentPlantsTable.getItems();
		if (p.getFrequency() == 0)
			list.remove(p);
	}

	
	/**
	 * Creates a scene for the user's input window.
	 * <p>
	 * It creates a scene containing a GridPane which is populated with labels, text
	 * fields and combo boxes used for user's input. It calls methods:
	 * setSizeInput(), setSunLevelInput(), setSoilTypeInput(),
	 * setMoistureLevelInput() and setBudgetInput(). Contains a Button
	 * continueButton which changes the scene and saves all the data.
	 * 
	 * @author Michal Bargenda
	 */
	private void createCreateGardenScene() {
		scene2 = new Scene(createRoot, WIDTH, HEIGHT);

		createRoot.setCenter(gpCreateGarden);
		gpCreateGarden.setHgap(20);
		gpCreateGarden.setVgap(20);
		gpCreateGarden.setMaxHeight(HEIGHT * 0.7);
		gpCreateGarden.setMaxWidth(WIDTH * 0.7);

		setSizeInput();
		setSunLevelInput();
		setSoilTypeInput();
		setMoistureLevelInput();
		setBudgetInput();

		gpCreateGarden.add(continueButton, 4, 10);
		continueButton.setOnMousePressed(controller.saveDataHandler());
		continueButton.setOnAction(e -> window.setScene(scene3));
		continueButton.setOnMouseReleased(e -> currentBudgetAndLepCount());
		continueButton.disableProperty().bind(Bindings.isEmpty(heightTextField.textProperty()));	//These 3 lines prevent the user
		continueButton.disableProperty().bind(Bindings.isEmpty(widthTextField.textProperty()));		//from moving on if the text field's
		continueButton.disableProperty().bind(Bindings.isEmpty(budgetTextField.textProperty()));	//are blank
		
		// Allows user to go back to the input scene
		gpCreateGarden.add(backButton2, 4, 11);
		backButton2.setOnMousePressed(controller.saveDataHandler());
		backButton2.setOnAction(e -> window.setScene(getScene()));
		backButton2.setOnMousePressed(controller.getResetGardenHandler(apGarden));
		
		// Changes size of back button and create garden button
		continueButton.setStyle("-fx-font-size:20");
		continueButton.setPrefWidth(200);
		continueButton.setPrefHeight(40);
		
		backButton2.setStyle("-fx-font-size:19");
		backButton2.setPrefWidth(200);
		backButton2.setPrefHeight(40);
		

		
		BackgroundImage myBI= new BackgroundImage(new Image("/img/createRoot.jpg", (int) Screen.getPrimary().getVisualBounds().getWidth(),(int) Screen.getPrimary().getVisualBounds().getHeight(),true,false),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
		          BackgroundSize.DEFAULT);
		createRoot.setBackground(new Background(myBI));
	}
	
	/**
	 * Adds labels and text field for size input
	 * 
	 * @author Michal Bargenda
	 */
	private void setSizeInput() {
		gpCreateGarden.add(sizeLabel, 3, 3);
		gpCreateGarden.add(widthTextField, 4, 3);
		gpCreateGarden.add(footLabel1, 5, 3);
		gpCreateGarden.add(heightTextField, 6, 3);
		gpCreateGarden.add(footLabel2, 7, 3);
		widthTextField.setPromptText("WIDTH");
		heightTextField.setPromptText("HEIGHT");
		
		// Change the font size and color
		footLabel1.setFont(new Font("Arial", 30));
		footLabel1.setTextFill(Color.WHITE);
		footLabel2.setFont(new Font("Arial", 30));
		footLabel2.setTextFill(Color.WHITE);
		sizeLabel.setFont(new Font("Oswald", 25));
		sizeLabel.setTextFill(Color.WHITE);
		widthTextField.setFont(Font.font("Arial",20));
		heightTextField.setFont(Font.font("Arial",20));
		
		
		//These commands prevent the user from typing anything but numbers into the below fields.
		heightTextField.setTextFormatter(new TextFormatter<>(controller.sizeLimits));
		widthTextField.setTextFormatter(new TextFormatter<>(controller.sizeLimits));
		budgetTextField.setTextFormatter(new TextFormatter<>(controller.filter));
		

	}

	/**
	 * Adds labels and drop down menus for sunlight level input
	 * 
	 * @author Michal Bargenda
	 */
	private void setSunLevelInput() {
		gpCreateGarden.add(sunlightLabel, 3, 4);
		gpCreateGarden.add(sunMenu, 4, 4);
		sunMenu.getItems().add("Full sun");
		sunMenu.getItems().add("Half");
		sunMenu.getItems().add("Shade");
		sunMenu.getSelectionModel().selectFirst();
		
		// Changes font size and color
		sunlightLabel.setFont(new Font("Comfortaa", 25));
		sunlightLabel.setTextFill(Color.WHITE);
	}

	/**
	 * Adds labels and drop down menus for soil type input
	 * 
	 * @author Michal Bargenda
	 */
	private void setSoilTypeInput() {
		gpCreateGarden.add(soilLabel, 3, 5);
		gpCreateGarden.add(soilMenu, 4, 5);
		soilMenu.getItems().add("Sandy");
		soilMenu.getItems().add("Clay");
		soilMenu.getItems().add("Loam");
		soilMenu.getSelectionModel().selectFirst();
		
		// Changes font size and color
		soilLabel.setFont(new Font("Comfortaa", 25));
		soilLabel.setTextFill(Color.WHITE);
	}

	/**
	 * Adds labels and drop down menus for moisture level input
	 * 
	 * @author Michal Bargenda
	 */
	private void setMoistureLevelInput() {
		gpCreateGarden.add(moistureLabel, 3, 6);
		gpCreateGarden.add(moistureMenu, 4, 6);
		moistureMenu.getItems().add("Dry");
		moistureMenu.getItems().add("Medium");
		moistureMenu.getItems().add("Wet");
		moistureMenu.getSelectionModel().selectFirst();
		
		// Changes font size and color
		moistureLabel.setFont(new Font("Comfortaa", 25));
		moistureLabel.setTextFill(Color.WHITE);
	}

	/**
	 * Adds labels and text field for budget input
	 * 
	 * @author Michal Bargenda
	 */
	private void setBudgetInput() {
		gpCreateGarden.add(budgetLabel, 3, 7);
		gpCreateGarden.add(budgetTextField, 4, 7);
		budgetTextField.setPromptText("BUDGET");
		// gpCreateGarden.add(infinityButton, 6, 7);
		
		// Changes font size and color
		budgetLabel.setFont(new Font("Comfortaa", 25));
		budgetLabel.setTextFill(Color.WHITE);
		budgetTextField.setFont(Font.font("Arial",20));
	}
	
	/**
	 * Checks if the user went over their budget
	 * @param budget
	 * 
	 * @author Jack Sabagh
	 */
	private void checkOverBudget(int budget) {
		if((budget < 0) && (alreadyShown == false)) {
			alreadyShown = true;
			overBudgetWindow();

			budgetValueLabel.setTextFill(Color.RED);
		}
		else if((alreadyShown == true) && (budget >= 0)) {
			alreadyShown = false;
			budgetValueLabel.setTextFill(Color.GREEN);
		}
	}
	
	

	/**
	 * @param x current x coordinate of a dragged ImageView of a Plant
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @param y current y coordinate of a dragged ImageView of a Plant
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return x current x coordinate of a dragged ImageView of a Plant
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * @return y current y coordinate of a dragged ImageView of a Plant
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * @return sunlightLabel
	 */
	public Label getSunlightLabel() {
		return sunlightLabel;
	}

	/**
	 * @return widthTextField
	 */
	public TextField getWidthTextField() {
		return widthTextField;
	}

	/**
	 * @return heightTextField
	 */
	public TextField getHeightTextField() {
		return heightTextField;
	}

	/**
	 * @return budgetTextField
	 */
	public TextField getBudgetTextField() {
		return budgetTextField;
	}

	/**
	 * @return sunMenu
	 */
	public ComboBox<String> getSunMenu() {
		return sunMenu;
	}

	/**
	 * @return soilMenu
	 */
	public ComboBox<String> getSoilMenu() {
		return soilMenu;
	}

	/**
	 * @return moistureMenu
	 */
	public ComboBox<String> getMoistureMenu() {
		return moistureMenu;
	}

	/**
	 * @param currentBudget
	 */
	public void setCurrentBudget(int currentBudget) {
		this.currentBudget = currentBudget;
	}

	/**
	 * @param currentLepCount
	 */
	public void setCurrentLepCount(long currentLepCount) {
		this.currentLepCount = currentLepCount;
	}

	/**
	 * @return a plant that was selected
	 */
	public Plant getSelectedPlant() {
		return selectedPlant;
	}

	/**
	 * @param selectedPlant
	 */
	public void setSelectedPlant(Plant selectedPlant) {
		this.selectedPlant = selectedPlant;
	}

	/**
	 * @return selectionModel
	 */
	public TableViewSelectionModel getSelectionModel() {
		return selectionModel;
	}
	
	/**
	 * @param ratio
	 */
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	/**
	 * @return table with plants
	 */
	public TableView<Plant> getCurrentPlantsTable() {
		return currentPlantsTable;
	}

	/**
	 * @return scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * @param scene
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	/**
	 * @return ArrayList of picture nodes
	 */
	public ArrayList<Shape> getNodes() {
		return nodes;
	}
}
