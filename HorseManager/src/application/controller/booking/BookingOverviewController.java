package application.controller.booking;

import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import service.impl.ServiceHandler;
import application.MainApp;
import entities.Appointment;
import entities.Booking;
import exception.InvalidInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings("deprecation")
public class BookingOverviewController {
	@FXML
	private TableView<Booking> bookingTable;
	@FXML
	private TableColumn<Booking, Number> bnrColumn;
	@FXML
	private TableColumn<Booking, String> customerColumn;
	@FXML
	private TableColumn<Booking, Date> dateBookedColumn;

	@FXML
	private Label bnrLabel;
	@FXML
	private Label customerLabel;
	@FXML
	private Label dateBookedLabel;

	@FXML
	private ComboBox<Appointment> apptBox;

	ObservableList<Appointment> apptObList = FXCollections.observableArrayList();

	private MainApp mainApp;

	private static final Logger logger = LogManager.getLogger("BookingOverviewController");

	public BookingOverviewController() {

	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		logger.info("Initializing booking overview controller");
		// Initialize the booking table with the two columns
		bnrColumn.setCellValueFactory(cellData -> cellData.getValue().getBnrProperty());
		customerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerProperty());
		dateBookedColumn.setCellValueFactory(cellData -> cellData.getValue().getDayOfBookingProperty());

		// Clear booking details
		showBookingDetails(null);

		// Clear apptObList
		apptObList.clear();

		// Listen for selection changes, show the booking details when changed
		bookingTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showBookingDetails(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		logger.info("Setting the main app");
		this.mainApp = mainApp;

		logger.info("Setting the observable list data to the booking table");
		// Add observable list data to the table
		bookingTable.setItems(mainApp.getBookingData());
	}

	/**
	 * Show the booking details of a specific booking object in the right half
	 * of the AnchorPane
	 * 
	 * @param booking
	 */
	public void showBookingDetails(Booking booking) {
		if (booking != null) {
			logger.info("Showing booking details with information");
			// Fill the labels with info from the booking object
			bnrLabel.setText(booking.getBnr().toString());
			customerLabel.setText(booking.getCustomer());
			dateBookedLabel.setText(booking.getDayOfBooking().toString());
			apptObList.clear();
			apptObList.setAll(booking.getaList());
			apptBox.setItems(apptObList);
		} else {
			logger.info("Showing booking details with no information");
			bnrLabel.setText("");
			customerLabel.setText("");
			dateBookedLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks the delete button
	 */
	@FXML
	private void handleDeleteAppointment() {
		try {
			int selectedIndex = apptBox.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				Appointment deleteAppt = apptBox.getItems().get(selectedIndex);
				logger.debug("Attempting to delete the following appointment: " + deleteAppt.toString());

				// Delete appointment from database
				if (!deleteAppt.isDeleted() && deleteAppt.isEditable()) {
					logger.info("Deleting the appointment");
					ServiceHandler.getInstance().getAppointmentService().deleteAppointment(deleteAppt);

					int bookingIndex = bookingTable.getSelectionModel().getSelectedIndex();
					bookingTable.getItems().get(bookingIndex).getaList().get(selectedIndex).setDeleted(true);

					showBookingDetails(bookingTable.getItems().get(bookingIndex));
				} else {
					Dialogs.create().title("Not editable").masthead("Appointment cannot be changed")
							.message("This appointment cannot be changed, it is either already deleted or no longer able to be changed.")
							.showWarning();
					logger.info("This appointment cannot be changed, it is either already deleted or no longer able to be changed");
				}
			} else {
				// Nothing selected
				Dialogs.create().title("No selection").masthead("No appointment selected").message("Please select an appointment from the list")
						.showWarning();
				logger.info("No appointment selected");
			}
		} catch (InvalidInputException e) {
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getError()).showWarning();
			// e.printStackTrace();
			logger.warn(e.getError());
		}

	}

	/**
	 * Called when user clicks the edit button. Opens a dialog to edit details
	 * for the appointment.
	 */
	@FXML
	private void handleEditAppointment() {
		try {
			int selectedIndex = apptBox.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				Appointment updateAppt = apptBox.getItems().get(selectedIndex);
				logger.debug("Attempting to update the following appointment: " + updateAppt.toString());

				// Update appointment in database
				if (!updateAppt.isDeleted() && updateAppt.isEditable()) {
					boolean oKClicked = mainApp.showBookingEditDialog(updateAppt);

					if (oKClicked) {
						logger.info("Updating the appointment");
						ServiceHandler.getInstance().getAppointmentService().updateAppointment(updateAppt);

						int bookingIndex = bookingTable.getSelectionModel().getSelectedIndex();
						bookingTable.getItems().get(bookingIndex).getaList().set(selectedIndex, updateAppt);
						showBookingDetails(bookingTable.getItems().get(bookingIndex));
					}
				} else {
					Dialogs.create().title("Not editable").masthead("Appointment cannot be changed")
							.message("This appointment cannot be changed, please keep your appointment.").showWarning();
					logger.info("This appointment cannot be changed");
				}
			} else {
				// Nothing selected
				Dialogs.create().title("No selection").masthead("No appointment selected").message("Please select an appointment from the list")
						.showWarning();
				logger.info("No appointment selected");
			}
		} catch (InvalidInputException e) {
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getError()).showWarning();
			// e.printStackTrace();
			logger.warn(e.getError());
		}
	}

	/**
	 * Called when user clicks new. Opens a dialog to create a new booking with
	 * multiple appointments.
	 */
	@FXML
	private void handleNewAppointment() {
		Booking newBooking = new Booking();
		Booking dbBooking = new Booking();
		boolean oKClicked = mainApp.showBookingNewDialog(newBooking);
		if (oKClicked) {
			try {
				if (newBooking.getaList().size() > 0) {
					logger.debug("Attempting to add the following booking to the database: " + newBooking.getFileName());
					dbBooking = ServiceHandler.getInstance().getBookingService().insertBooking(newBooking);
					logger.debug("Inserted the following appointment into the database: " + dbBooking.getFileName());
				} else {
					logger.warn("Booking was not added to the database!");
					throw new InvalidInputException("Booking was not added to database!");
				}

				if (dbBooking != null) {
					for (int i = 0; i < newBooking.getaList().size(); i++) {
						newBooking.getaList().get(i).setBnr(dbBooking.getBnr());
						logger.debug("Attempting to insert the following appointment into the database: " + newBooking.getaList().get(i));
						ServiceHandler.getInstance().getAppointmentService().insertAppointment(newBooking.getaList().get(i));
						logger.debug("Inserted the appointment into the database");
					}

					logger.info("Adding the booking to the observable list in main");
					mainApp.getBookingData().add(dbBooking);

					showBookingDetails(null);
				}
			} catch (InvalidInputException e) {
				Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getError()).showWarning();
				// e.printStackTrace();
				logger.warn(e.getError());
			}
		}
	}

	/**
	 * Called when user decides to show the booking confirmation for a specific
	 * booking
	 */
	@FXML
	private void handleShowBookingConfirmation() {
		Booking selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
		if (selectedBooking != null) {
			logger.info("Show the confirmation for a booking selected");
			mainApp.showBookingConfirmationDialog(selectedBooking);
		} else {
			// Nothing selected
			Dialogs.create().title("No selection").masthead("No booking selected").message("Please select a booking from the table").showWarning();
		}
	}
}
