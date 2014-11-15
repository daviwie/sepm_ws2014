package application.controller.booking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import entities.Booking;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings("unused")
public class BookingConfirmationDialogController {
	@FXML
	private TextArea bookingConfirmation;
	
	private Stage dialogStage;
	private Booking booking;
	private boolean oKClicked = false;
	
	private static final Logger logger = LogManager.getLogger("BookingConfirmationDialogController");
	
	/**
	 * Automatically called by loader
	 */
	@FXML
	private void initialize() {
		logger.info("Initialize Booking Confirmation Dialog");
	}
	
	/**
	 * Set the stage for the dialog window
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		logger.info("Set dialog stage");
	}
	
	/**
	 * Set the booking for this dialog and also fill the text area with the booking confirmation
	 * 
	 * @param booking
	 */
	public void setBooking(Booking booking) {
		this.booking = booking;
		
		logger.info("Setting booking");
		
		bookingConfirmation.setText(booking.toString());
	}
	
	/**
	 * If OK has been clicked then true, else false
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return oKClicked;
	}
	
	/**
	 * Close the dialog box after OK has been clicked
	 */
	@FXML
	private void handleOk() {
		oKClicked = true;
		logger.info("OK was clicked");
		dialogStage.close();
	}

}
