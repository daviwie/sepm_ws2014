package service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import entities.Horse;
import exception.InvalidInputException;

/**
 * @author David Wietstruk 0706376
 *
 */
public interface IHorseService {

	/**
	 * Inserts a Horse object into the database. Method also validates input.
	 * Error messages are returned via InvalidInputException.
	 * 
	 * @param horse
	 *            the Horse to be inserted into the database
	 * @return The Horse inserted into the database if successful, else null
	 * @throws InvalidInputException
	 * @throws IOException 
	 */
	public Horse insertHorse(Horse horse) throws InvalidInputException;

	/**
	 * Searches for all Horses stored in the database. Any database errors are
	 * returned via the InvalidInputException.
	 * 
	 * @return An ArrayList of all Horses stored in the database
	 * @throws InvalidInputException
	 * @throws IOException 
	 */
	public ArrayList<Horse> searchAllHorses() throws InvalidInputException;

	/**
	 * Searches for a specific Horse in the database. The method also validates
	 * the search parameters to make sure nothing invalid is passed to the DAO
	 * and the database queries. Any error messages are returned via
	 * InvalidInputException.
	 * 
	 * @param horse
	 *            The Horse that needs to be found
	 * @return A specific Horse from the database, else null
	 * @throws InvalidInputException
	 */
	public Horse searchByHorse(Horse horse) throws InvalidInputException;

	/**
	 * Searches for a specific Horse in the database. The method also validates
	 * the search parameters to make sure nothing invalid is passed to the DAO
	 * and the database queries. Any error messages are returned via
	 * InvalidInputException.
	 * 
	 * @param horse
	 *            The Horse that needs to be found
	 * @return A specific Horse from the database, else null
	 * @throws InvalidInputException
	 */
	public Horse searchByHorse(String horse) throws InvalidInputException;

	/**
	 * Searches for all of the Horses in the database that have a weight within
	 * a specific range. The method also checks to make sure the input
	 * parameters are valid and that the upper bound is greater than the lower
	 * bound. Any error messages are returned via the InvalidInputException.
	 * 
	 * @param lowerWeightLimit
	 *            the lower bound of the search query
	 * @param upperWeightLimit
	 *            the upper bound of the search query
	 * @return an ArrayList of Horses that fit to the search query
	 * @throws InvalidInputException
	 */
	public ArrayList<Horse> searchHorseByWeight(Integer lowerWeightLimit, Integer upperWeightLimit) throws InvalidInputException;

	/**
	 * Searches for all of the Horses in the database that have a height within
	 * a specific range. The method also checks to make sure the input
	 * parameters are valid and that the upper bound is greater than the lower
	 * bound. Any error messages are returned via the InvalidInputException.
	 * 
	 * @param lowerHeightLimit
	 *            the lower bound of the search query
	 * @param upperHeightLimit
	 *            the upper bound of the search query
	 * @return an ArrayList of Horses that fit to the search query
	 * @throws InvalidInputException
	 */
	public ArrayList<Horse> searchHorseByHeight(Integer lowerHeightLimit, Integer upperHeightLimit) throws InvalidInputException;

	/**
	 * Checks to see if a Horse is available to be booked for a specific
	 * appointment. The method also checks the input parameters to make sure
	 * everything is valid. The method also checks to make sure that endTime is
	 * indeed after startTime and that the date selected is not in the past.
	 * Error messages are returned via InvalidInputException.
	 * 
	 * @param date
	 *            The Date to be checked
	 * @param startTime
	 *            The starting time for a potential appointment
	 * @param endTime
	 *            The end time for a potential appointment
	 * @param horse
	 *            The Horse whose availability needs to be checked
	 * @return true if the Horse is available, else false
	 * @throws InvalidInputException
	 */
	public boolean isHorseAvailable(Date date, Time startTime, Time endTime, Horse horse) throws InvalidInputException;

	/**
	 * Checks to see if a Horse is available to be booked for a specific
	 * appointment. The method also checks the input parameters to make sure
	 * everything is valid. The method also checks to make sure that endTime is
	 * indeed after startTime and that the date selected is not in the past.
	 * Error messages are returned via InvalidInputException.
	 * 
	 * @param date
	 *            The Date to be checked
	 * @param startTime
	 *            The starting time for a potential appointment
	 * @param endTime
	 *            The end time for a potential appointment
	 * @param hname
	 *            The Horse whose availability needs to be checked
	 * @return true if the Horse is available, else false
	 * @throws InvalidInputException
	 */
	public boolean isHorseAvailable(Date date, Time startTime, Time endTime, String hname) throws InvalidInputException;

	/**
	 * Updates a specific row in the Horse table. Input parameters are checked
	 * to make sure that they are valid. Error messages are returned via the
	 * InvalidInputException.
	 * 
	 * @param horse
	 *            The Horse to be updated
	 * @return true if update successful, else false
	 * @throws InvalidInputException
	 */
	public boolean updateHorse(Horse horse) throws InvalidInputException;

	/**
	 * Marks a specific Horse for deletion. Input parameters are checked to make
	 * sure they are valid. Error messages are returned via the
	 * InvalidInputException.
	 * 
	 * @param horse
	 *            The Horse to be marked for deletion
	 * @return The Horse marked for deletion, else exception is thrown
	 * @throws InvalidInputException
	 */
	public Horse deleteHorse(Horse horse) throws InvalidInputException;

	/**
	 * Marks a specific Horse for deletion. Input parameters are checked to make
	 * sure they are valid. Error messages are returned via the
	 * InvalidInputException.
	 * 
	 * @param horse
	 *            The Horse to be marked for deletion
	 * @return The Horse marked for deletion, else exception is thrown
	 * @throws InvalidInputException
	 */
	public Horse deleteHorse(String horse) throws InvalidInputException;
}