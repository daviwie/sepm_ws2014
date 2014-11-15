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
import entities.Booking;
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
@SuppressWarnings({ "deprecation", "unused" })
public class BookingNewDialogController {
	@FXML
	private TextField customerField;
	@FXML
	private ComboBox<Horse> horseBox;

	// A
	@FXML
	private DatePicker datePickerFieldA;
	@FXML
	private TextField startTimeFieldA;
	@FXML
	private TextField endTimeFieldA;
	// B
	@FXML
	private DatePicker datePickerFieldB;
	@FXML
	private TextField startTimeFieldB;
	@FXML
	private TextField endTimeFieldB;
	// C
	@FXML
	private DatePicker datePickerFieldC;
	@FXML
	private TextField startTimeFieldC;
	@FXML
	private TextField endTimeFieldC;

	private Stage dialogStage;
	private Booking booking;
	private boolean oKClicked = false;
	private MainApp mainApp;

	private static final Logger logger = LogManager.getLogger("BookingNewDialogController");

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		logger.info("Initializing New Booking Dialog");
	}

	/**
	 * Sets the main app to provide a reference to data.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		logger.info("Setting main app");
		horseBox.setItems(mainApp.getHorseData());
		logger.info("Setting the ComboBox horseBox");
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		logger.info("Setting the dialog stage");
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the booking to be edited in the dialog.
	 * 
	 * @param booking
	 */
	public void setBooking(Booking booking) {
		logger.info("Setting the booking");
		this.booking = booking;
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
	 * Called when the user clicks OK
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			if (isInputAValid()) {
				booking.setCustomer(customerField.getText());

				booking.setDayOfBooking(SQLDateTools.getNow());

				int horseIndex = horseBox.getSelectionModel().getSelectedIndex();
				boolean editable;

				/*
				 * Check to see if the date of our appointment is more than 14
				 * days in the future. If so, the appointment is marked as able
				 * to be changed. If not, the opposite.
				 */
				if (SQLDateTools.getSQLDateFromLocalDate(datePickerFieldA.getValue()).after(SQLDateTools.addDays(SQLDateTools.getNow(), 14)))
					editable = true;
				else
					editable = false;

				Appointment appt = new Appointment(SQLDateTools.getSQLDateFromLocalDate(datePickerFieldA.getValue()), Time.valueOf(startTimeFieldA
						.getText()), Time.valueOf(endTimeFieldA.getText()), editable, false, booking.getBnr(), horseBox.getItems().get(horseIndex)
						.getName());

				try {
					if (ServiceHandler.getInstance().getHorseService()
							.isHorseAvailable(appt.getaDate(), appt.getStartTime(), appt.getEndTime(), appt.getHname()))
						booking.getaList().add(appt);
					else
						Dialogs.create()
								.title("ERROR")
								.masthead("Invalid input")
								.message(
										"Appointment on " + appt.getaDate().toString() + " from " + appt.getStartTime().toString() + " and to "
												+ appt.getEndTime().toString() + " is not available for " + appt.getHname() + ". Sorry.")
								.showWarning();
				} catch (InvalidInputException e) {
					logger.debug(e.getError());
				}

				// Check row B
				if (isInputBValid()) {
					/*
					 * Check to see if the date of our appointment is more than
					 * 14 days in the future. If so, the appointment is marked
					 * as able to be changed. If not, the opposite.
					 */
					if (SQLDateTools.getSQLDateFromLocalDate(datePickerFieldB.getValue()).after(SQLDateTools.addDays(SQLDateTools.getNow(), 14)))
						editable = true;
					else
						editable = false;

					appt = new Appointment(SQLDateTools.getSQLDateFromLocalDate(datePickerFieldB.getValue()),
							Time.valueOf(startTimeFieldB.getText()), Time.valueOf(endTimeFieldB.getText()), editable, false, booking.getBnr(),
							horseBox.getItems().get(horseIndex).getName());

					try {
						if (ServiceHandler.getInstance().getHorseService()
								.isHorseAvailable(appt.getaDate(), appt.getStartTime(), appt.getEndTime(), appt.getHname()))
							booking.getaList().add(appt);
						else
							Dialogs.create()
									.title("ERROR")
									.masthead("Invalid input")
									.message(
											"Appointment on " + appt.getaDate().toString() + " from " + appt.getStartTime().toString() + " and to "
													+ appt.getEndTime().toString() + " is not available for " + appt.getHname() + ". Sorry.")
									.showWarning();
					} catch (InvalidInputException e) {
						logger.debug(e.getError());
					}

					// Check row C
					if (isInputCValid()) {
						/*
						 * Check to see if the date of our appointment is more
						 * than 14 days in the future. If so, the appointment is
						 * marked as able to be changed. If not, the opposite.
						 */
						if (SQLDateTools.getSQLDateFromLocalDate(datePickerFieldC.getValue()).after(SQLDateTools.addDays(SQLDateTools.getNow(), 14)))
							editable = true;
						else
							editable = false;

						appt = new Appointment(SQLDateTools.getSQLDateFromLocalDate(datePickerFieldC.getValue()), Time.valueOf(startTimeFieldC
								.getText()), Time.valueOf(endTimeFieldC.getText()), editable, false, booking.getBnr(), horseBox.getItems()
								.get(horseIndex).getName());

						try {
							if (ServiceHandler.getInstance().getHorseService()
									.isHorseAvailable(appt.getaDate(), appt.getStartTime(), appt.getEndTime(), appt.getHname()))
								booking.getaList().add(appt);
							else
								Dialogs.create()
										.title("ERROR")
										.masthead("Invalid input")
										.message(
												"Appointment on " + appt.getaDate().toString() + " from " + appt.getStartTime().toString()
														+ " and to " + appt.getEndTime().toString() + " is not available for " + appt.getHname()
														+ ". Sorry.").showWarning();
						} catch (InvalidInputException e) {
							logger.debug(e.getError());
						}
					}
				}

				oKClicked = true;
				logger.info("OK button clicked");
				dialogStage.close();
			}
		}
	}

	/**
	 * Called when user clicks cancel
	 */
	@FXML
	private void handleCancel() {
		logger.info("Cancel button clicked");
		dialogStage.close();
	}

	/**
	 * Validates user input for customer and horse
	 * 
	 * @return
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (customerField.getText() == null || customerField.getText().length() == 0)
			errorMessage += "No valid customer name given\n";

		if (horseBox.getSelectionModel().getSelectedIndex() < 0)
			errorMessage += "No valid horse selected\n";

		logger.debug("Error message length: " + errorMessage.length());

		if (errorMessage.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Validates user input for row A
	 * 
	 * @return
	 */
	private boolean isInputAValid() {
		String errorMessage = "";

		if (datePickerFieldA.getValue().isBefore(LocalDate.now()))
			errorMessage += "Date cannot be in the past\n";

		if (startTimeFieldA.getText() == null || startTimeFieldA.getText().length() == 0)
			errorMessage += "No valid start time given\n";

		// TODO Validate that startTimeField has format hh:mm:ss

		if (endTimeFieldA.getText() == null || endTimeFieldA.getText().length() == 0)
			errorMessage += "No valid end time given\n";

		// TODO Validate that endTimeField has format hh:mm:ss

		logger.debug("Error message length: " + errorMessage.length());

		if (errorMessage.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Validates user input for row B
	 * 
	 * @return
	 */
	private boolean isInputBValid() {
		String errorMessage = "";

		if (datePickerFieldB.getValue().isBefore(LocalDate.now()))
			errorMessage += "Date cannot be in the past\n";

		if (startTimeFieldB.getText() == null || startTimeFieldB.getText().length() == 0)
			errorMessage += "No valid start time given\n";

		// TODO Validate that startTimeField has format hh:mm:ss

		if (endTimeFieldB.getText() == null || endTimeFieldB.getText().length() == 0)
			errorMessage += "No valid end time given\n";

		// TODO Validate that endTimeField has format hh:mm:ss

		logger.debug("Error message lenght: " + errorMessage.length());

		if (errorMessage.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Validates user input for row C
	 * 
	 * @return
	 */
	private boolean isInputCValid() {
		String errorMessage = "";

		if (datePickerFieldC.getValue().isBefore(LocalDate.now()))
			errorMessage += "Date cannot be in the past\n";

		if (startTimeFieldC.getText() == null || startTimeFieldC.getText().length() == 0)
			errorMessage += "No valid start time given\n";

		// TODO Validate that startTimeField has format hh:mm:ss

		if (endTimeFieldC.getText() == null || endTimeFieldC.getText().length() == 0)
			errorMessage += "No valid end time given\n";

		// TODO Validate that endTimeField has format hh:mm:ss

		logger.debug("Error message lenght: " + errorMessage.length());

		if (errorMessage.length() == 0)
			return true;
		else
			return false;
	}
}
