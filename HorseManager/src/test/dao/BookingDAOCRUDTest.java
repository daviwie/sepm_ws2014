package test.dao;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.H2Handler;
import util.ScriptRunner;
import dao.impl.DAOHandler;
import entities.Booking;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
public class BookingDAOCRUDTest {

	private ScriptRunner sr;
	private Reader createReader;
	private Reader insertReader;
	private Reader dropReader;

	private static final Logger logger = LogManager.getLogger("BookingDAOCRUDTest");

	public BookingDAOCRUDTest() {

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
	public void insertBookingTest() throws PersistenceException {
		Booking b = new Booking(Date.valueOf("2015-03-09"), "David");
		Booking test = DAOHandler.getInstance().getBookingDAO().createBooking(b);
		logger.debug(test.toString());
		assertNotNull(test);
	}

	@Test
	public void passedSearchByBookingTest() throws PersistenceException {
		Booking b = new Booking(Date.valueOf("2015-03-09"), "David");
		Booking test = DAOHandler.getInstance().getBookingDAO().createBooking(b);
		// logger.debug(test.toString());
		assertNotNull(DAOHandler.getInstance().getBookingDAO().searchByBooking(test));
	}

	@Test
	public void failedSearchByBookingTest() throws PersistenceException {
		Booking b = new Booking(Integer.valueOf(500), Date.valueOf("2015-03-09"), "David");
		// logger.debug(b);
		assertNull(DAOHandler.getInstance().getBookingDAO().searchByBooking(b));
	}

	@Test
	public void passedSearchBookingByDateTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getBookingDAO().searchBookingByDate(Date.valueOf("2014-11-01"), Date.valueOf("2014-12-01")).size();
		// logger.debug(testInt);
		assertTrue(testInt >= 1);
	}

	@Test
	public void failedSearchBookingByDateTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getBookingDAO().searchBookingByDate(Date.valueOf("2015-11-01"), Date.valueOf("2015-12-01")).size();
		// logger.debug(testInt);
		assertFalse(testInt >= 1);
	}

	@Test
	public void passedSearchByCustomerTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getBookingDAO().searchByCustomer("David").size();
		// logger.debug(testInt);
		assertTrue(testInt >= 1);
	}

	@Test
	public void failedSearchByCustomerTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getBookingDAO().searchByCustomer("Brian").size();
		// logger.debug(testInt);
		assertFalse(testInt >= 1);
	}

	@Test
	public void passedUpdateTest() throws PersistenceException {
		Booking b = new Booking(Integer.valueOf(100), Date.valueOf("2014-11-01"), "Marky Mark");
		boolean testBool = DAOHandler.getInstance().getBookingDAO().updateBooking(b);
		// logger.debug(testBool);
		assertTrue(testBool);
	}

	@Test
	public void failedUpdateTest() throws PersistenceException {
		Booking b = new Booking(Integer.valueOf(500), Date.valueOf("2014-11-01"), "Marky Mark");
		boolean testBool = DAOHandler.getInstance().getBookingDAO().updateBooking(b);
		// logger.debug(testBool);
		assertFalse(testBool);
	}

}
