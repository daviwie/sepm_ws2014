package application.controller.root;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import persistence.H2Handler;
import javafx.fxml.FXML;
import application.MainApp;

/**
 * Controller for the rootlayout.fxml. 
 * 
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings("deprecation")
public class RootLayoutController {
	private MainApp mainApp;
	private static final Logger logger = LogManager.getLogger("RootLayoutController");
	
	/**
	 * Is called by the main application to give a reference back to itself. 
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		logger.info("Setting the main app");
	}
	
	/**
	 * Called when clicking the Booking Search menu item
	 */
	@FXML
	private void handleBookingSearch() {
		mainApp.showBookingOverview();
		logger.info("Showing booking search");
	}
	
	/**
	 * Called when clicking the Horse Search menu item
	 */
	@FXML
	private void handleHorseSearch() {
		mainApp.showHorseSearch();
		logger.info("Showing horse search");
	}
	
	/**
	 * Closes the application when the menu item Close is selected
	 */
	@SuppressWarnings("static-access")
	@FXML
	private void handleClose(){
		logger.info("Closing the app");
		try {
			/*
			 * This might violate the 3-layer architecture...
			 */
			H2Handler.getInstance().closeConnection();
			logger.info("Closing the database connection");
		} catch (SQLException e) {
			Dialogs.create().title("Database error").masthead("Connection could not be closed").message(e.getMessage()).showError();
			logger.warn(e.getMessage());
		}
		mainApp.getServer().stop();
		logger.info("Stopping the TCP server");
		System.exit(0);
	}
}
