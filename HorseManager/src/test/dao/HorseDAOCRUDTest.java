package test.dao;

import static org.junit.Assert.*;

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
import util.ScriptRunner;
import dao.impl.DAOHandler;
import entities.Horse;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
public class HorseDAOCRUDTest {

	private ScriptRunner sr;
	private Reader createReader;
	private Reader insertReader;
	private Reader dropReader;

	private static final Logger logger = LogManager.getLogger("HorseDAOCRUDTest");

	public HorseDAOCRUDTest() throws PersistenceException, FileNotFoundException, SQLException {
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

	@Test
	public void passedInsertTest() throws PersistenceException {
		Horse horse = new Horse("HorseyTest", 500, 501, "filepath", Date.valueOf("2010-11-11"), false);
		Horse test = DAOHandler.getInstance().getHorseDAO().createHorse(horse);
		logger.debug(test);
		assertNotNull(test);
	}

	@Test
	public void passedUpdateTest() throws PersistenceException {
		Horse horse = new Horse("HorseyTest", 500, 501, "filepath", Date.valueOf("2010-11-11"), false);
		Horse test = DAOHandler.getInstance().getHorseDAO().createHorse(horse);
		test.setHeight(900);
		boolean testBool = DAOHandler.getInstance().getHorseDAO().updateHorse(test);
		// logger.debug(testBool);
		assertTrue(testBool);
	}

	@Test
	public void failedUpdateTest() throws PersistenceException {
		Horse horse = new Horse("HorseyTest", 500, 501, "filepath", Date.valueOf("2010-11-11"), false);
		horse.setName("Bob");
		boolean testBool = DAOHandler.getInstance().getHorseDAO().updateHorse(horse);
		// logger.debug(testBool);
		assertFalse(testBool);
	}

	@Test
	public void passedSearchByHorseTest() throws PersistenceException {
		Horse horse = new Horse("HorseyTest", 500, 501, "filepath", Date.valueOf("2010-11-11"), false);
		DAOHandler.getInstance().getHorseDAO().createHorse(horse);
		Horse test = DAOHandler.getInstance().getHorseDAO().searchByHorse(horse);
		// logger.debug(test);
		assertNotNull(test);
	}

	@Test
	public void passedSearchByStringTest() throws PersistenceException {
		Horse test = DAOHandler.getInstance().getHorseDAO().searchByHorse("Wham");
		// logger.debug(test);
		assertNotNull(test);
	}

	@Test
	public void failedSearchByHorseTest() throws PersistenceException {
		Horse horse = new Horse("HorseyTest", 500, 501, "filepath", Date.valueOf("2010-11-11"), false);
		Horse test = DAOHandler.getInstance().getHorseDAO().searchByHorse(horse);
		// logger.debug(test);
		assertNull(test);
	}

	@Test
	public void failedSearchByStringTest() throws PersistenceException {
		Horse test = DAOHandler.getInstance().getHorseDAO().searchByHorse("Steve");
		// logger.debug(test);
		assertNull(test);
	}

	@Test
	public void passedSearchAllHorsesTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getHorseDAO().searchAllHorses().size();
		// logger.debug(testInt);
		assertTrue(testInt >= 1);
	}

	@Test
	public void passedSearchByHeightTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getHorseDAO().searchHorseByHeight(100, 1000).size();
		// logger.debug(testInt);
		assertTrue(testInt >= 1);
	}

	@Test
	public void failedSearchByHeightTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getHorseDAO().searchHorseByHeight(800, 1000).size();
		// logger.debug(testInt);
		assertFalse(testInt >= 1);
	}

	@Test
	public void passedSearchByWeightTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getHorseDAO().searchHorseByWeight(100, 1000).size();
		// logger.debug(testInt);
		assertTrue(testInt >= 1);
	}

	@Test
	public void failedSearchByWeightTest() throws PersistenceException {
		int testInt = DAOHandler.getInstance().getHorseDAO().searchHorseByWeight(800, 1000).size();
		// logger.debug(testInt);
		assertFalse(testInt >= 1);
	}

	@Test
	public void passedIsAvailableHorseTest() throws PersistenceException {
		Horse horse = DAOHandler.getInstance().getHorseDAO().searchByHorse("Wham");
		// logger.debug(horse.toString());
		boolean testBool = DAOHandler.getInstance().getHorseDAO()
				.isHorseAvailable(Date.valueOf("2014-12-01"), Time.valueOf("15:00:00"), Time.valueOf("16:00:00"), horse);
		// logger.debug(testBool);
		assertTrue(testBool);
	}

	@Test
	public void failedIsAvailableHorseTest() throws PersistenceException {
		Horse horse = DAOHandler.getInstance().getHorseDAO().searchByHorse("Wham");
		// logger.debug(horse.toString());
		boolean testBool = DAOHandler.getInstance().getHorseDAO()
				.isHorseAvailable(Date.valueOf("2014-12-01"), Time.valueOf("11:00:00"), Time.valueOf("14:00:00"), horse);
		// logger.debug(testBool);
		assertFalse(testBool);
	}

	@Test
	public void passedIsStringAvailableTest() throws PersistenceException {
		boolean testBool = DAOHandler.getInstance().getHorseDAO()
				.isHorseAvailable(Date.valueOf("2014-12-01"), Time.valueOf("15:00:00"), Time.valueOf("16:00:00"), "Wham");
		// logger.debug(testBool);
		assertTrue(testBool);
	}

	@Test
	public void failedIsStringAvailableTest() throws PersistenceException {
		boolean testBool = DAOHandler.getInstance().getHorseDAO()
				.isHorseAvailable(Date.valueOf("2014-12-01"), Time.valueOf("11:00:00"), Time.valueOf("14:00:00"), "Wham");
		// logger.debug(testBool);
		assertFalse(testBool);
	}

}
