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
import entities.Horse;
import exception.InvalidInputException;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings("unused")
public class HorseServiceTest {

	private ScriptRunner sr;
	private Reader createReader;
	private Reader insertReader;
	private Reader dropReader;

	private static final Logger logger = LogManager.getLogger("HorseServiceTest");

	public HorseServiceTest() throws InvalidInputException, FileNotFoundException, SQLException {
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
	public void failedInsertHorseTest() throws InvalidInputException {
		Horse horse = new Horse("Wham", 500, 501, "", Date.valueOf("2000-11-11"), false);
		// logger.debug("failedInsertHorseTest()");
		// logger.debug(horse);
		ServiceHandler.getInstance().getHorseService().insertHorse(horse);
	}

	@Test(expected = InvalidInputException.class)
	public void failedInsertNullTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().insertHorse(null);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByHorseTest() throws InvalidInputException {
		Horse horse = null;
		// logger.debug("failedSearchByHorseTest()");
		// logger.debug(horse);
		ServiceHandler.getInstance().getHorseService().searchByHorse(horse);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByNullHorseNameTest() throws InvalidInputException {
		Horse horse = new Horse(null, 500, 501, "", Date.valueOf("2000-11-11"), false);
		// logger.debug("failedSearchByNullHorseNameTest()");
		// logger.debug(horse);
		ServiceHandler.getInstance().getHorseService().searchByHorse(horse);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByStringTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().searchByHorse("");
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByWeightTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().searchHorseByWeight(-12, -2);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByOppositeWeightTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().searchHorseByWeight(500, 0);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByNullWeightTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().searchHorseByWeight(null, null);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByHeightTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().searchHorseByHeight(-12, -2);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByOppositeHeightTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().searchHorseByWeight(500, 0);
	}

	@Test(expected = InvalidInputException.class)
	public void failedSearchByNullHeightTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().searchHorseByHeight(null, null);
	}

	@Test(expected = InvalidInputException.class)
	public void failedUpdateHorse() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().updateHorse(null);
	}

	@Test(expected = InvalidInputException.class)
	public void failedUpdateHorseNullName() throws InvalidInputException {
		Horse horse = new Horse(null, 500, 501, "", Date.valueOf("2000-11-11"), false);
		// logger.debug("failedUpdateHorseNullName()");
		// logger.debug(horse);
		ServiceHandler.getInstance().getHorseService().updateHorse(horse);
	}

	@Test(expected = InvalidInputException.class)
	public void failedDeleteHorse() throws InvalidInputException {
		Horse horse = null;
		ServiceHandler.getInstance().getHorseService().deleteHorse(horse);
	}

	@Test(expected = InvalidInputException.class)
	public void failedDeleteHorseNullName() throws InvalidInputException {
		Horse horse = new Horse(null, 500, 501, "", Date.valueOf("2000-11-11"), false);
		// logger.debug("failedDeleteHorseNullName()");
		// logger.debug(horse);
		ServiceHandler.getInstance().getHorseService().deleteHorse(horse);
	}
	
	@Test(expected = InvalidInputException.class)
	public void failedDeleteHorseEmptyName() throws InvalidInputException {
		Horse horse = new Horse("", 500, 501, "", Date.valueOf("2000-11-11"), false);
		// logger.debug("failedDeleteHorseNullName()");
		// logger.debug(horse);
		ServiceHandler.getInstance().getHorseService().deleteHorse(horse);
	}

	@Test(expected = InvalidInputException.class)
	public void failedIsHorseAvailableTest() throws InvalidInputException {
		Horse horse = null;
		// logger.debug(horse);
		ServiceHandler.getInstance().getHorseService().isHorseAvailable(null, null, null, horse);
	}

	@Test(expected = InvalidInputException.class)
	public void failedIsStringAvailableTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().isHorseAvailable(null, null, null, "");
	}

	@Test(expected = InvalidInputException.class)
	public void failedIsHorseAvailablePastDateTest() throws InvalidInputException {
		Horse horse = new Horse("Wham", 500, 501, "", Date.valueOf("2000-11-11"), false);
		// logger.debug(horse);
		ServiceHandler.getInstance().getHorseService()
				.isHorseAvailable(Date.valueOf("2012-01-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), horse);
	}

	@Test(expected = InvalidInputException.class)
	public void failedIsStringAvailablePastDateTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService()
				.isHorseAvailable(Date.valueOf("2012-01-01"), Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), "Wham");
	}
	
	@Test(expected = InvalidInputException.class)
	public void failedIsAvailableInvalidTimeTest() throws InvalidInputException {
		ServiceHandler.getInstance().getHorseService().isHorseAvailable(Date.valueOf("2012-01-01"), Time.valueOf("12:00:00"), Time.valueOf("10:00:00"), "Wham");
	}

	@Test(expected = InvalidInputException.class)
	public void failedInvalidBirthdayTest() throws InvalidInputException {
		Horse horse = new Horse("HorseyTest", 500, 501, "", Date.valueOf("2019-11-11"), false);
		// logger.debug(horse);
		ServiceHandler.getInstance().getHorseService().insertHorse(horse);
	}
}
