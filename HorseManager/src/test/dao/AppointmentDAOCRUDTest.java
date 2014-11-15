package test.dao;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import persistence.H2Handler;
import util.ScriptRunner;
import dao.impl.DAOHandler;
import entities.Appointment;
import entities.Booking;
import entities.Horse;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
public class AppointmentDAOCRUDTest {

	private ScriptRunner sr;
	private Reader createReader;
	private Reader insertReader;
	private Reader dropReader;

	private static final Logger logger = LogManager.getLogger("AppointmentDAOCRUDTest");

	public AppointmentDAOCRUDTest() {

	}

	@Before
	public void setUp() throws SQLException, IOException {
		sr = new ScriptRunner(H2Handler.getInstance().getConnection(), true, true);
		createReader = new FileReader("..\\HorseManager\\src\\test\\sql\\create.sql");
		insertReader = new FileReader("..\\HorseManager\\src\\test\\sql\\insert.sql");
		dropReader = new FileReader("..\\HorseManager\\src\\test\\sql\\drop.sql");
		sr.runScript(createReader);
		sr.runScript(insertReader);
	}

	@After
	public void tearDown() throws SQLException, IOException {
		/*
		 * Teardown can also be done with rollbacks! See Leitfaden.pdf (p.9)
		 */
		sr.runScript(dropReader);
		createReader.close();
		insertReader.close();
		dropReader.close();
	}

	@Test
	public void insertAppointmentTest() throws PersistenceException {
		Booking b = new Booking(Date.valueOf("2015-03-09"), "David");
		Horse horse = new Horse("HorseyHorse", 500, 501, "filepath", Date.valueOf("2010-11-11"), false);
		Appointment appt = new Appointment(Date.valueOf("2015-01-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), true, false, DAOHandler
				.getInstance().getBookingDAO().createBooking(b).getBnr(), DAOHandler.getInstance().getHorseDAO().createHorse(horse).getName());
		Appointment test = DAOHandler.getInstance().getAppointmentDAO().createAppt(appt, b, horse);
		// logger.debug(test.toString());
		assertNotNull(test);
	}

	@Test
	public void passedSearchByAppointmentTest() throws PersistenceException {
		Booking b = new Booking(Date.valueOf("2015-03-09"), "David");
		Horse horse = new Horse("HorseyHorse", 500, 501, "filepath", Date.valueOf("2010-11-11"), false);
		Appointment appt = new Appointment(Date.valueOf("2015-01-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), true, false, DAOHandler
				.getInstance().getBookingDAO().createBooking(b).getBnr(), DAOHandler.getInstance().getHorseDAO().createHorse(horse).getName());
		Appointment test = DAOHandler.getInstance().getAppointmentDAO().createAppt(appt, b, horse);
		// logger.debug(test.toString());
		assertNotNull(DAOHandler.getInstance().getAppointmentDAO().searchByAppt(test));
	}

	@Test
	public void failedSearchByAppointmentTest() throws PersistenceException {
		Appointment appt = new Appointment(20, Date.valueOf("2015-01-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), true, false, 500,
				"HorseyHorse");
		Appointment test = DAOHandler.getInstance().getAppointmentDAO().searchByAppt(appt);
		logger.debug(test);
		assertNull(test);
	}

	@Test
	public void passedSearchByBookingTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getAppointmentDAO().searchByBooking(new Booking(100, Date.valueOf("2014-11-01"), "David")).size();
		// logger.debug(testInt);
		assertTrue(testInt >= 1);
	}

	@Test
	public void failedSearchByBookingTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getAppointmentDAO().searchByBooking(new Booking(500, Date.valueOf("2014-11-01"), "David")).size();
		// logger.debug(testInt);
		assertFalse(testInt >= 1);
	}

	@Test
	public void passedSearchByDateTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getAppointmentDAO().searchByDate(Date.valueOf("2014-01-01"), Date.valueOf("2014-12-31")).size();
		// logger.debug(testInt);
		assertTrue(testInt >= 1);
	}

	@Test
	public void failedSearchByDateTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getAppointmentDAO().searchByDate(Date.valueOf("2015-01-01"), Date.valueOf("2015-12-31")).size();
		// logger.debug(testInt);
		assertFalse(testInt >= 1);
	}

	@Test
	public void passedSearchByHorseTest() throws PersistenceException {
		Horse horse = new Horse("Wham", 500, 501, "filepath", Date.valueOf("2010-11-11"), false);
		int testInt = DAOHandler.getInstance().getAppointmentDAO().searchByHorse(horse).size();
		// logger.debug(testInt);
		assertTrue(testInt >= 1);
	}

	@Test
	public void failedSearchByHorseTest() throws PersistenceException {
		Horse horse = new Horse("HorseyHorse", 500, 501, "filepath", Date.valueOf("2010-11-11"), false);
		int testInt = DAOHandler.getInstance().getAppointmentDAO().searchByHorse(horse).size();
		// logger.debug(testInt);
		assertFalse(testInt >= 1);
	}

	@Test
	public void passedUpdateTest() throws PersistenceException {
		Appointment appt = new Appointment(11, Date.valueOf("2015-01-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), true, false, 100,
				"Wham");
		boolean testBool = DAOHandler.getInstance().getAppointmentDAO().updateAppt(appt);
		// logger.debug(testBool);
		assertTrue(testBool);
	}

	@Test
	public void failedUpdateTest() throws PersistenceException {
		Appointment appt = new Appointment(12, Date.valueOf("2015-01-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), true, false, 500,
				"HorseyHorse");
		boolean testBool = DAOHandler.getInstance().getAppointmentDAO().updateAppt(appt);
		// logger.debug(testBool);
		assertFalse(testBool);
	}

	@Test
	public void passedDeleteTest() throws PersistenceException {
		Appointment appt = new Appointment(11, Date.valueOf("2015-01-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), true, false, 100,
				"Wham");
		Appointment test = DAOHandler.getInstance().getAppointmentDAO().deleteAppt(appt);
		// logger.debug(test);
		assertNotNull(test);
	}

	@Test
	public void failedDeleteTest() throws PersistenceException {
		Appointment appt = new Appointment(12, Date.valueOf("2015-01-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), true, false, 500,
				"HorseyHorse");
		Appointment test = DAOHandler.getInstance().getAppointmentDAO().deleteAppt(appt);
		// logger.debug(test);
		assertNull(test);
	}

}
