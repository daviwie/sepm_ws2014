package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import org.h2.tools.Server;

import service.impl.ServiceHandler;
import util.SQLDateTools;
import entities.Appointment;
import entities.Booking;
import entities.Horse;
import exception.InvalidInputException;
import application.controller.booking.BookingConfirmationDialogController;
import application.controller.booking.BookingEditDialogController;
import application.controller.booking.BookingNewDialogController;
import application.controller.booking.BookingOverviewController;
import application.controller.horse.HorseDeleteDialogController;
import application.controller.horse.HorseEditController;
import application.controller.horse.HorseNewDialogController;
import application.controller.horse.HorseSearchController;
import application.controller.root.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

@SuppressWarnings("deprecation")
public class MainApp extends Application {

	private static Stage primaryStage;
	private BorderPane rootLayout;

	private static Server server;

	private ObservableList<Horse> horseData = FXCollections.observableArrayList();
	private ObservableList<Booking> bookingData = FXCollections.observableArrayList();

	private static final Logger logger = LogManager.getLogger("MainApp");

	public MainApp() {
		try {
			logger.info("Initializing the main app and all of the data");
			/*
			 * Update the editable flag on all appointments in the database. All
			 * appointments within the next two weeks are no longer editable.
			 */
			ServiceHandler.getInstance().getAppointmentService().updateAll(SQLDateTools.getNow(), SQLDateTools.addDays(SQLDateTools.getNow(), 14));
			// Initialize all lists with data from server
			ArrayList<Horse> horseTransfer = ServiceHandler.getInstance().getHorseService().searchAllHorses();
			ArrayList<Booking> bookingTransfer = ServiceHandler.getInstance().getBookingService().searchAllBookings();

			for (int i = 0; i < horseTransfer.size(); i++)
				horseData.add(horseTransfer.get(i));

			for (int i = 0; i < bookingTransfer.size(); i++)
				bookingData.add(bookingTransfer.get(i));
		} catch (InvalidInputException e) {
			logger.warn(e.getError());
			Dialogs.create().title("ERROR").masthead("Program start failed").message(e.getMessage()).showWarning();
		}

	}

	@Override
	public void start(Stage primaryStage) {
		MainApp.primaryStage = primaryStage;
		MainApp.primaryStage.setTitle("HorseManager");

		initRootLayout();
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("controller/root/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give controller access to the main app
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getMessage()).showWarning();
		}
	}

	public void showHorseSearch() {
		try {
			// Load horse search
			FXMLLoader loader = new FXMLLoader();
			// loader.setLocation(MainApp.class.getResource("controller/horse/HorseOverview.fxml"));
			loader.setLocation(MainApp.class.getResource("controller/horse/HorseSearch.fxml"));
			AnchorPane horseSearch = (AnchorPane) loader.load();

			// Set horse search into the center of root layout
			rootLayout.setCenter(horseSearch);

			// Give controller access to the main app
			HorseSearchController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getMessage()).showWarning();
		}
	}

	public boolean showHorseEditDialog(Horse horse) {
		try {
			// Load the fxml file and create a new stage for the popup dialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("controller/horse/HorseEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Horse");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the horse into the controller
			HorseEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setHorse(horse);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getMessage()).showWarning();
			return false;
		}
	}

	public boolean showHorseNewDialog(Horse horse) {
		try {
			// Load the fxml file and create a new stage for the popup dialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("controller/horse/HorseNewDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("New Horse");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the horse into the controller
			HorseNewDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setHorse(horse);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getMessage()).showWarning();
			return false;
		}
	}

	public boolean showHorseDeleteDialog(Horse horse) {
		try {
			// Load the fxml file and create a new stage for the popup dialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("controller/horse/HorseDeleteDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Delete horse");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the horse into the controller
			HorseDeleteDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setHorse(horse);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getMessage()).showWarning();
			return false;
		}
	}

	public void showBookingOverview() {
		try {
			// Load Booking Overview
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("controller/booking/BookingOverview.fxml"));
			AnchorPane bookingOverview = (AnchorPane) loader.load();

			// Set booking overview into the center of root layout
			rootLayout.setCenter(bookingOverview);

			// Give controller access to the main app
			BookingOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			logger.warn(e.getMessage());
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getMessage()).showWarning();
		}
	}

	public boolean showBookingConfirmationDialog(Booking booking) {
		try {
			// Load the fxml file and create a new stage for the popup dialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("controller/booking/BookingConfirmationDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Booking Confirmation");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the booking into the controller
			BookingConfirmationDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setBooking(booking);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getMessage()).showWarning();
			return false;
		}
	}

	public boolean showBookingNewDialog(Booking booking) {
		try {
			// Load the fxml file and create a new stage for the popup dialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("controller/booking/BookingNewDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("New Booking");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the booking into the controller
			BookingNewDialogController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
			controller.setBooking(booking);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getMessage()).showWarning();
			return false;
		}
	}

	public boolean showBookingEditDialog(Appointment appt) {
		try {
			// Load the fxml file and create a new stage for the popup dialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("controller/booking/BookingEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Appointment");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the booking into the controller
			BookingEditDialogController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
			controller.setAppointment(appt);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			Dialogs.create().title("ERROR").masthead("Invalid input").message(e.getMessage()).showWarning();
			return false;
		}
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public ObservableList<Horse> getHorseData() {
		return horseData;
	}

	public ObservableList<Booking> getBookingData() {
		return bookingData;
	}

	public static Server getServer() {
		return server;
	}

	public static void main(String[] args) throws SQLException {
		server = Server.createTcpServer(args).start();
		logger.info("Starting the TCP server");
		launch(args);
		logger.info("Launching the app");

		// TODO When window closes, STOP SERVER!
		/*
		 * Program must be closed via File -> Close otherwise server keeps
		 * running
		 */
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			// This doesn't work - why?
			public void handle(WindowEvent t) {
				logger.debug("Trying to get and handle the event for setOnCloseRequest");
				getServer().stop();
			}
		});
	}
}
