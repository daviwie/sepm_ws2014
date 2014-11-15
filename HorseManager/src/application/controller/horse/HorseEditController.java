package application.controller.horse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import entities.Horse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings("deprecation")
public class HorseEditController {
	@FXML
	private Label hNameLabel;
	@FXML
	private TextField weightField;
	@FXML
	private TextField heightField;
	@FXML
	private TextField imgFilePathField;
	@FXML
	private Label bDateLabel;

	private Stage dialogStage;
	private Horse horse;
	private boolean oKClicked = false;

	private static final Logger logger = LogManager.getLogger("HorseEditController");

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void intialize() {
		logger.info("Initializing the Horse edit controller");
	}

	/**
	 * Sets the stage of this dialog
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		logger.info("Set the dialog stage");
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the horse to be edited in the dialog
	 * 
	 * @param horse
	 */
	public void setHorse(Horse horse) {
		this.horse = horse;
		logger.info("Set the horse");

		hNameLabel.setText(horse.getName());
		weightField.setText(horse.getWeight().toString());
		heightField.setText(horse.getHeight().toString());
		imgFilePathField.setText(horse.getImgfilepath());
		bDateLabel.setText(horse.getBdate().toString());
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return oKClicked;
	}

	/**
	 * Called when user clicks OK.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			horse.setWeight(Integer.parseInt(weightField.getText()));
			horse.setHeight(Integer.parseInt(heightField.getText()));
			horse.setImgFilePath(imgFilePathField.getText());

			oKClicked = true;
			logger.info("OK button clicked");
			dialogStage.close();
		}
	}

	/**
	 * Called when user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		logger.info("Cancel button clicked");
		dialogStage.close();
	}

	/**
	 * Validates user input in the text fields.
	 * 
	 * @return true if the input is valid, else false
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (weightField.getText() == null || weightField.getText().length() == 0) {
			errorMessage += "No valid weight given\n";
		} else {
			try {
				Integer.parseInt(weightField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid weight given (must be an integer)\n";
			}
		}

		if (heightField.getText() == null || heightField.getText().length() == 0) {
			errorMessage += "No valid height given\n";
			try {
				Integer.parseInt(heightField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid height given (must be an integer)\n";
			}
		}

		if (imgFilePathField.getText() == null || imgFilePathField.getText().length() == 0)
			errorMessage += "No valid image filepath given\n";

		logger.debug("Error message length: " + errorMessage.length());

		if (errorMessage.length() == 0)
			return true;
		else {
			Dialogs.create().title("Invalid fields").masthead("Please correct invalid fields").message(errorMessage).showError();
			logger.info("Error in validation");
			return false;
		}
	}
}
