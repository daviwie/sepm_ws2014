package application.controller.horse;

import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import entities.Horse;
import exception.InvalidInputException;
import service.impl.ServiceHandler;
import application.MainApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings({ "deprecation", "unused" })
public class HorseSearchController {
	@FXML
	private Toggle weightGreaterThanToggle;
	@FXML
	private Toggle heightGreaterThanToggle;
	@FXML
	private TextField weightFilterField;
	@FXML
	private TextField heightFilterField;
	@FXML
	private TableView<Horse> horseTable = new TableView<Horse>();
	@FXML
	private TableColumn<Horse, String> hNameColumn;
	@FXML
	private TableColumn<Horse, Number> weightColumn;
	@FXML
	private TableColumn<Horse, Number> heightColumn;
	@FXML
	private TableColumn<Horse, Date> bDateColumn;

	@FXML
	private Label hNameLabel;
	@FXML
	private Label weightLabel;
	@FXML
	private Label heightLabel;
	@FXML
	private Label bDateLabel;

	private MainApp mainApp;

	private ObservableList<Horse> horseData = FXCollections.observableArrayList();

	private static final Logger logger = LogManager.getLogger("HorseSearchController");

	public HorseSearchController() {

	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the horse table with columns
		hNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		weightColumn.setCellValueFactory(cellData -> cellData.getValue().getWeightProperty());
		heightColumn.setCellValueFactory(cellData -> cellData.getValue().getHeightProperty());
		bDateColumn.setCellValueFactory(cellData -> cellData.getValue().getBdateProperty());

		// Clear horse details
		showHorseDetails(null);

		// Listen for selection changes and show the horse details when changed
		horseTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showHorseDetails(newValue));

		weightFilterField.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				handleWeightFilter();
			}
		});
		
		heightFilterField.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				handleHeightFilter();
			}

			/*
			 * (observable, oldValue, newValue) -> {
			 * filteredData.setPredicate(horse -> { // If filter text is empty,
			 * display all horses if (newValue == null || newValue.isEmpty()) {
			 * logger.debug("In the initialization method..."); return false; }
			 * else { try { Integer tempHeight = Integer.parseInt(newValue);
			 * 
			 * if (heightGreaterThanToggle.isSelected()) {
			 * logger.debug("Toggle: "+heightGreaterThanToggle.isSelected()); if
			 * (horse.getHeight() > tempHeight) return true; else return false;
			 * } else { if (horse.getHeight() < tempHeight) return true; else
			 * return false; } } catch (NumberFormatException e) {
			 * Dialogs.create
			 * ().title("Invalid fields").masthead("Please correct invalid fields"
			 * ) .message("Filter text must be of type Integer").showError();
			 * return false; }
			 * 
			 * } });
			 */
		});

		// Wrap the FilteredList in a SortedList
		/*
		 * SortedList<Horse> sortedData = new SortedList<>(filteredData);
		 * logger.debug(sortedData);
		 * 
		 * // Bind the SortedList comparator to the TableView comparator
		 * sortedData
		 * .comparatorProperty().bind(horseTable.comparatorProperty());
		 * 
		 * // Add sorted (and filtered) data to the table
		 * horseTable.setItems(sortedData);
		 */
		logger.info("Initializing the horse search controller");
	}

	/**
	 * Called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		logger.info("Set the main app");

		// Add observable list data to the table
		horseTable.setItems(mainApp.getHorseData());
		logger.info("Set the horse table");
		horseData = this.mainApp.getHorseData();
		logger.info("Set the horse data");
	}

	/**
	 * Fills all text fields to show details about the horse. If the specified
	 * horse is null, all text fields are cleared.
	 * 
	 * @param horse
	 *            the horse or null
	 */
	private void showHorseDetails(Horse horse) {
		if (horse != null) {
			// Fill labels with info from the Horse object
			hNameLabel.setText(horse.getName());
			weightLabel.setText(Integer.toString(horse.getWeight()));
			heightLabel.setText(Integer.toString(horse.getHeight()));
			bDateLabel.setText(horse.getBdate().toString());
			logger.debug("Showing all of the horse information when a horse is selected");
		} else {
			hNameLabel.setText("");
			weightLabel.setText("");
			heightLabel.setText("");
			bDateLabel.setText("");
			logger.debug("Showing all of the horse information when no horse is selected");
		}
	}

	/**
	 * Called when user clicks on the Delete button
	 */
	@FXML
	private void handleDeleteHorse() {
		try {
			int selectedIndex = horseTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				Horse deleteHorse = horseTable.getItems().get(selectedIndex);
				logger.debug("Attempting to delete the following horse: " + deleteHorse.toString());
				boolean oKClicked = mainApp.showHorseDeleteDialog(deleteHorse);
				if (oKClicked) {
					logger.info("Deleting the horse");
					// Delete horse from database
					ServiceHandler.getInstance().getHorseService().deleteHorse(deleteHorse);
					// Delete horse from JFX table
					horseTable.getItems().remove(selectedIndex);
				}
			} else {
				// Nothing selected
				Dialogs.create().title("No Selection").masthead("No horse selected").message("Please select a horse from the table").showWarning();
				logger.info("No horse selected");
			}
		} catch (InvalidInputException e) {
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getError()).showWarning();
			// http://code.makery.ch/java/javafx-8-tutorial-part3/
			logger.warn(e.getError());
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new horse.
	 */
	@FXML
	private void handleNewHorse() {
		Horse tempHorse = new Horse();
		boolean oKClicked = mainApp.showHorseNewDialog(tempHorse);
		if (oKClicked) {
			logger.debug("Attempting to add the following horse to the database: " + tempHorse.toString());
			this.mainApp.getHorseData().add(tempHorse);
			try {
				ServiceHandler.getInstance().getHorseService().insertHorse(tempHorse);
				logger.info("Inserted the horse");
			} catch (InvalidInputException e) {
				Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getError()).showWarning();
				logger.warn(e.getError());
			}
		}
	}

	/**
	 * Called when user clicks the edit button. Opens a dialog to edit the
	 * details for a horse.
	 */
	@FXML
	private void handleEditHorse() {
		Horse selectedHorse = horseTable.getSelectionModel().getSelectedItem();
		if (selectedHorse != null) {
			logger.debug("Attempting to edit: " + selectedHorse.toString());
			boolean oKClicked = mainApp.showHorseEditDialog(selectedHorse);
			if (oKClicked) {
				try {
					ServiceHandler.getInstance().getHorseService().updateHorse(selectedHorse);
					showHorseDetails(selectedHorse);
					logger.info("Edited horse");
				} catch (InvalidInputException e) {
					Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getError()).showWarning();
					logger.warn(e.getError());
				}
			}
		} else {
			// Nothing selected
			Dialogs.create().title("No selection").masthead("No horse selected").message("Please select a horse from the table").showWarning();
		}
	}

	@FXML
	private void handleHeightFilter() {
		/*
		 * Wrap the ObservableList from mainApp in a FilteredList (initially
		 * display all data)
		 */
		FilteredList<Horse> filteredData = new FilteredList<>(horseData, h -> true);

		// Set the filter predicate whenever the filter changes

		filteredData.setPredicate(horse -> {
			// If filter text is empty, display all horses
			String heightFilter = heightFilterField.getText();
				if (heightFilter == null || heightFilter.isEmpty()) {
					logger.debug("We are here");
					return true;
				} else {
					try {
						Integer tempHeight = Integer.parseInt(heightFilter);

						if (heightGreaterThanToggle.isSelected()) {
							if (horse.getHeight() > tempHeight) {
								logger.debug(tempHeight);
								return true;
							}
						} else {
							if (horse.getHeight() < tempHeight)
								return true;
						}
					} catch (NumberFormatException e) {
						Dialogs.create().title("Invalid fields").masthead("Please correct invalid fields")
								.message("Filter text must be of type Integer").showError();
					}
				}
				return false;
			});

		horseTable.setItems(filteredData);
	}
	
	@FXML
	private void handleWeightFilter() {
		/*
		 * Wrap the ObservableList from mainApp in a FilteredList (initially
		 * display all data)
		 */
		FilteredList<Horse> filteredData = new FilteredList<>(horseData, h -> true);

		// Set the filter predicate whenever the filter changes

		filteredData.setPredicate(horse -> {
			// If filter text is empty, display all horses
			String weightFilter = weightFilterField.getText();
				if (weightFilter == null || weightFilter.isEmpty()) {
					logger.debug("We are here");
					return true;
				} else {
					try {
						Integer tempWeight = Integer.parseInt(weightFilter);

						if (heightGreaterThanToggle.isSelected()) {
							if (horse.getWeight() > tempWeight) {
								logger.debug(tempWeight);
								return true;
							}
						} else {
							if (horse.getWeight() < tempWeight)
								return true;
						}
					} catch (NumberFormatException e) {
						Dialogs.create().title("Invalid fields").masthead("Please correct invalid fields")
								.message("Filter text must be of type Integer").showError();
					}
				}
				return false;
			});

		horseTable.setItems(filteredData);
	}

}
