package test.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.H2Handler;
import service.impl.ServiceHandler;
import util.ScriptRunner;
import entities.Booking;
import exception.InvalidInputException;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings("unused")
public class BookingServiceTest {

	private ScriptRunner sr;
	private Reader createReader;
	private Reader insertReader;
	private Reader dropReader;
	
	private static final Logger logger = LogManager.getLogger("BookingServiceTest");

	public BookingServiceTest() throws PersistenceException, FileNotFoundException, SQLException {
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
	public void failedInsertBookingTest() throws InvalidInputException {
		ServiceHandler.getInstance().getBookingService().insertBooking(null);
	}
	
	@Test(expected = InvalidInputException.class)
	public void failedSearchByBookingTest() throws InvalidInputException {
		Booking booking = null;
		ServiceHandler.getInstance().getBookingService().searchByBooking(booking);
	}
	
	@Test(expected = InvalidInputException.class)
	public void failedSearchByIntegerTest() throws InvalidInputException {
		Integer integer = null;
		ServiceHandler.getInstance().getBookingService().searchByBooking(integer);
	}
	
	@Test(expected = InvalidInputException.class)
	public void failedSearchByNegativeIntegerTest() throws InvalidInputException {
		ServiceHandler.getInstance().getBookingService().searchByBooking(-5);
	}
	
	@Test(expected = InvalidInputException.class)
	public void failedSearchByCustomerTest() throws InvalidInputException{
		ServiceHandler.getInstance().getBookingService().searchByCustomer(null);
	}
	
	@Test(expected = InvalidInputException.class)
	public void failedSearchByDateTest() throws InvalidInputException{
		ServiceHandler.getInstance().getBookingService().searchBookingByDate(null, null);
	}
	
	@Test(expected = InvalidInputException.class)
	public void failedUpdateTest() throws InvalidInputException{
		ServiceHandler.getInstance().getBookingService().updateBooking(null);
	}

}
