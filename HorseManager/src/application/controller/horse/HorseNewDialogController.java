package application.controller.horse;

import java.sql.Date;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import entities.Horse;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings("deprecation")
public class HorseNewDialogController {
	@FXML
	private TextField hNameField;
	@FXML
	private TextField weightField;
	@FXML
	private TextField heightField;
	@FXML
	private TextField imgFilePathField;
	@FXML
	private DatePicker bDateField;

	private Stage dialogStage;
	private Horse horse;
	private boolean oKClicked = false;

	private static final Logger logger = LogManager.getLogger("HorseNewController");

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void intialize() {
		logger.info("Initializing the controller");
	}

	/**
	 * Sets the stage of this dialog
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		logger.info("Setting the dialog stage");
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the horse to be edited in the dialog
	 * 
	 * @param horse
	 */
	public void setHorse(Horse horse) {
		logger.info("Setting the horse");
		this.horse = horse;
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
			horse.setName(hNameField.getText());
			horse.setWeight(Integer.parseInt(weightField.getText()));
			horse.setHeight(Integer.parseInt(heightField.getText()));
			horse.setImgFilePath(imgFilePathField.getText());
			String tempString = bDateField.getValue().getYear() + "-" + bDateField.getValue().getMonthValue() + "-"
					+ bDateField.getValue().getDayOfMonth();
			horse.setBdate(Date.valueOf(tempString));

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

		if (hNameField.getText() == null || hNameField.getText().length() == 0)
			errorMessage += "No valid name given\n";

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

		if (bDateField.getValue().isAfter(LocalDate.now()))
			errorMessage += "Date must be in the past\n";

		logger.debug("Error message lenght: " + errorMessage.length());

		if (errorMessage.length() == 0)
			return true;
		else {
			Dialogs.create().title("Invalid fields").masthead("Please correct invalid fields").message(errorMessage).showError();
			return false;
		}
	}
}
