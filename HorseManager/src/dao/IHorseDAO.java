package dao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import entities.Horse;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
public interface IHorseDAO {
	/**
	 * Inserts a Horse object into the database. If insertion successful returns
	 * the object back to the calling method, else null.
	 * 
	 * @param horse
	 *            the horse to be inserted into the database
	 * @return the horse inserted into the database if successful, else null
	 * @throws SQLException
	 */
	public Horse createHorse(Horse horse) throws PersistenceException;

	/**
	 * Constructs a list of all the Horse objects in the database.
	 * 
	 * @return a list of all Horse objects found in the database
	 * @throws SQLException
	 */
	public ArrayList<Horse> searchAllHorses() throws PersistenceException;

	/**
	 * Searches for a specific Horse object in the database and returns it if
	 * successful, else null.
	 * 
	 * @param horse
	 *            the horse to be found in the database
	 * @return the horse found in the database, else null
	 * @throws SQLException
	 */
	public Horse searchByHorse(Horse horse) throws PersistenceException;

	/**
	 * Searches for a specific Horse object in the database and returns it if
	 * successful, else null.
	 * 
	 * @param name
	 *            the horse to be found in the database
	 * @return the horse found in the database, else null
	 * @throws SQLException
	 */
	public Horse searchByHorse(String name) throws PersistenceException;

	/**
	 * Constructs a list of all Horse objects filtered by their weight
	 * 
	 * @param lowerWeightLimit
	 *            the lower bound for the filter
	 * @param upperWeightLimit
	 *            the upper bound for the filter
	 * @return a list of all Horse objects that conform to the filtered values
	 * @throws SQLException
	 */
	public ArrayList<Horse> searchHorseByWeight(Integer lowerWeightLimit, Integer upperWeightLimit) throws PersistenceException;

	/**
	 * Constructs a list of all Horse objects filtered by their height
	 * 
	 * @param lowerHeightLimit
	 *            the lower bound for the filter
	 * @param upperHeightLimit
	 *            the upper bound for the filter
	 * @return a list of all Horse objects that conform to the filtered values
	 * @throws SQLException
	 */
	public ArrayList<Horse> searchHorseByHeight(Integer lowerHeightLimit, Integer upperHeightLimit) throws PersistenceException;

	/**
	 * Checks the database to see if a horse would potentially be available
	 * 
	 * @param date The date of the candidate appointment
	 * @param startTime The start time of the candidate appointment
	 * @param endTime The end time of the candidate appointment
	 * @param horse The horse that the user wants to reserve for an appointment
	 * @return true if horse available, else false
	 * @throws PersistenceException
	 */
	public boolean isHorseAvailable(Date date, Time startTime, Time endTime, Horse horse) throws PersistenceException;

	/**
	 * Checks the database to see if a horse would potentially be available
	 * 
	 * @param date The date of the candidate appointment
	 * @param startTime The start time of the candidate appointment
	 * @param endTime The end time of the candidate appointment
	 * @param hname The horse that the user wants to reserve for an appointment
	 * @return true if horse available, else false
	 * @throws PersistenceException
	 */
	public boolean isHorseAvailable(Date date, Time startTime, Time endTime, String hname) throws PersistenceException;

	/**
	 * When an update for a specific horse needs to be completed, use this
	 * method. Returns true when update successful, else false.
	 * 
	 * @param horse
	 *            the row to be updated
	 * @return true if update successful, else false
	 * @throws SQLException
	 */
	public boolean updateHorse(Horse horse) throws PersistenceException;

	/**
	 * If a horse needs to be deleted, use this method. Returns the Horse to be
	 * deleted if the horse exists in the database and deletion was successful.
	 * Take note that no rows are actually deleted but instead marked using a
	 * flag!
	 * 
	 * @param horse that is to be marked for deletion
	 * @return the horse that is marked for deletion if successful, else null
	 */
	public Horse deleteHorse(Horse horse) throws PersistenceException;

	/**
	 * If a horse needs to be deleted, use this method. Returns the Horse to be
	 * deleted if the horse exists in the database and deletion was successful.
	 * Take note that no rows are actually deleted but instead marked using a
	 * flag!
	 * 
	 * @param horse that is to be marked for deletion
	 * @return the horse that is marked for deletion if successful, else null
	 * @throws PersistenceException 
	 */
	public Horse deleteHorse(String horse) throws PersistenceException;
}
