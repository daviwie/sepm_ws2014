package test.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.H2Handler;
import service.impl.ServiceHandler;
import util.ScriptRunner;
import entities.Appointment;
import entities.Booking;
import entities.Horse;
import exception.InvalidInputException;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
public class AppointmentServiceTest {

	private ScriptRunner sr;
	private Reader createReader;
	private Reader insertReader;
	private Reader dropReader;

	private static final Logger logger = LogManager.getLogger("AppointmentServiceTest");

	public AppointmentServiceTest() throws PersistenceException, FileNotFoundException, SQLException {
		sr = new ScriptRunner(H2Handler.getInstance().getConnection(), true, true);
		createReader = new FileReader("..\\HorseManager\\src\\test\\sql\\create.sql");
		insertReader = new FileReader("..\\HorseManager\\src\\test\\sql\\insert.sql");
		dropReader = new FileReader("..\\HorseManager\\src\\test\\sql\\drop.sql");
	}

	@Before
	public void setUp() throws SQLException, IOException {
		sr.runScript(createReader);
		sr.runScript(insertReader);
	}

	@After
	public void tearDown() throws IOException, SQLException {
		/*
		 * Teardown can also be done with rollbacks! See Leitfaden.pdf (p.9)
		 */
		sr.runScript(dropReader);
		createReader.close();
		insertReader.close();
		dropReader.close();
	}

	@Test(expected = InvalidInputException.class)
	public void failedInsertAppointmentTest() throws InvalidInputException {
		Appointment appt = new Appointment(1, Date.valueOf("2014-12-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), true, false, 10, "Wham");
		logger.debug(appt);
		ServiceHandler.getInstance().getAppointmentService().insertAppointment(appt);
	}

	@Test(expected = InvalidInputException.class)
	public void failedInsertNullTest() throws InvalidInputException {
		ServiceHandler.getInstance().getAppointmentService().insertAppointment(null);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByApptTest() throws InvalidInputException {
		Appointment appt = null;
		ServiceHandler.getInstance().getAppointmentService().searchByAppointment(appt);
	}
	
	@Test(expected = InvalidInputException.class)
	public void failedSearchByANRTest() throws InvalidInputException {
		ServiceHandler.getInstance().getAppointmentService().searchByAppointment(-1);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByBookingTest() throws InvalidInputException {
		Booking booking = null;
		ServiceHandler.getInstance().getAppointmentService().searchByBooking(booking);
	}
	
	@Test(expected = InvalidInputException.class)
	public void failedSearchByBNRTest() throws InvalidInputException {
		ServiceHandler.getInstance().getAppointmentService().searchByBooking(-1);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByDateTest() throws InvalidInputException {
		ServiceHandler.getInstance().getAppointmentService().searchByDate(null, null);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByHorseTest() throws InvalidInputException {
		Horse horse = null;
		logger.debug(horse);
		ServiceHandler.getInstance().getAppointmentService().searchByHorse(horse);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByStringTest() throws InvalidInputException {
		ServiceHandler.getInstance().getAppointmentService().searchByHorse("");
	}

	@Test(expected = InvalidInputException.class)
	public void failedUpdateTest() throws InvalidInputException {
		ServiceHandler.getInstance().getAppointmentService().updateAppointment(null);
	}

	@Test(expected = InvalidInputException.class)
	public void failedDeleteTest() throws InvalidInputException {
		ServiceHandler.getInstance().getAppointmentService().deleteAppointment(null);
	}

	@Test(expected = InvalidInputException.class)
	public void failedInvalidDateTest() throws InvalidInputException {
		Appointment appt = new Appointment(Date.valueOf("2012-12-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), true, false, 10, "Wham");
		logger.debug(appt);
		ServiceHandler.getInstance().getAppointmentService().insertAppointment(appt);
	}

}
