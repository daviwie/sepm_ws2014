package application.controller.horse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entities.Horse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class HorseDeleteDialogController {
	@FXML
	private Label horseLabel;
	
	private Horse horse;
	private Stage dialogStage;
	private boolean oKClicked = false;
	
	private static final Logger logger = LogManager.getLogger("HorseDeleteDialogController");
	
	/**
	 * Initializes the controller class. This method is automatically called after the fxml file has been loaded. 
	 */
	@FXML
	private void initialize(){
		logger.info("Initializing the delete dialog");
	}
	
	/**
	 * Sets the stage for this dialog
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		logger.info("Set the dialog stage");
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Sets the horse to be edited in the dialog. 
	 * 
	 * @param horse
	 */
	public void setHorse(Horse horse) {
		logger.info("Set the horse");
		this.horse = horse;
		
		logger.info("Set the label");
		horseLabel.setText(horse.toString());
	}
	
	/**
	 * Returns true if the user clicked OK, else false
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return oKClicked;
	}
	
	/**
	 * Called when the user clicks OK
	 */
	@FXML
	private void handleOk() {
		oKClicked = true;
		logger.info("OK button clicked");
		dialogStage.close();
	}
	
	/**
	 * Called when the user clicks cancel
	 */
	@FXML
	private void handleCancel() {
		logger.info("Cancel button clicked");
		dialogStage.close();
	}
}
