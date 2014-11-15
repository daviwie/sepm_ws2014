package application.controller.booking;

import java.sql.Time;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import service.impl.ServiceHandler;
import util.SQLDateTools;
import application.MainApp;
import entities.Appointment;
import entities.Horse;
import exception.InvalidInputException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings({ "unused", "deprecation" })
public class BookingEditDialogController {
	@FXML
	private DatePicker datePickerField;
	@FXML
	private TextField startTimeField;
	@FXML
	private TextField endTimeField;
	@FXML
	private ComboBox<Horse> horseBox;

	private Stage dialogStage;
	private Appointment appt;
	private boolean oKClicked = false;
	private MainApp mainApp;

	private static final Logger logger = LogManager.getLogger("BookingEditDialogController");

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		logger.info("Initializing BookingEditDialogController");
	}

	/**
	 * Returns true if the user clicked OK, false otherwise
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return oKClicked;
	}

	/**
	 * Sets the reference back to the main app for reference to data.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		logger.info("Setting main app");
		logger.debug(mainApp);
		horseBox.toString();
		horseBox.setItems(mainApp.getHorseData());
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		logger.info("Setting dialog stage");
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the appointment to be edited
	 * 
	 * @param appt
	 */
	public void setAppointment(Appointment appt) {
		this.appt = appt;

		logger.info("Setting appointment");

		datePickerField.setValue(appt.getaDate().toLocalDate());
		startTimeField.setText(appt.getStartTime().toString());
		endTimeField.setText(appt.getEndTime().toString());
		try {
			horseBox.setValue(ServiceHandler.getInstance().getHorseService().searchByHorse(appt.getHname()));
		} catch (InvalidInputException e) {
			logger.debug(e.getError());
		}
	}

	/**
	 * Called if user clicks OK
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			appt.setaDate(SQLDateTools.getSQLDateFromLocalDate(datePickerField.getValue()));
			appt.setStartTime(Time.valueOf(startTimeField.getText()));
			appt.setEndTime(Time.valueOf(endTimeField.getText()));
			int horseIndex = horseBox.getSelectionModel().getSelectedIndex();
			appt.setHname(horseBox.getItems().get(horseIndex).getName());

			oKClicked = true;
			logger.info("OK clicked");
			dialogStage.close();
		}
	}

	/**
	 * Called if user clicks cancel
	 */
	@FXML
	private void handleCancel() {
		logger.info("Cancel clicked");
		dialogStage.close();
	}

	/**
	 * Validates user input
	 * 
	 * @return
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (datePickerField.getValue().isBefore(LocalDate.now()))
			errorMessage += "Date cannot be in the past\n";

		if (startTimeField.getText() == null || startTimeField.getText().length() == 0)
			errorMessage += "No valid start time given\n";

		// TODO Validate that startTimeField has format hh:mm:ss

		if (endTimeField.getText() == null || endTimeField.getText().length() == 0)
			errorMessage += "No valid end time given\n";

		// TODO Validate that endTimeField has format hh:mm:ss

		if (horseBox.getSelectionModel().getSelectedIndex() < 0)
			errorMessage += "No valid horse selected\n";

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
