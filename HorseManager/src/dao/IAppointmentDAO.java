package dao;

import java.sql.Date;
import java.util.ArrayList;

import entities.Appointment;
import entities.Booking;
import entities.Horse;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
public interface IAppointmentDAO {
	/**
	 * This method adds an Appointment object to the database.
	 * 
	 * @param appt
	 *            the Appointment to be added to the database
	 * @param booking
	 *            the Booking to which the Appointment belongs
	 * @param horse
	 *            the Horse reserved for the Appointment
	 * @return
	 */
	public Appointment createAppt(Appointment appt, Booking booking, Horse horse) throws PersistenceException;

	/**
	 * Returns an ArrayList of Appointment objects that represent every
	 * Appointment row in the database.
	 * 
	 * @return all Appointment rows stored in the database
	 */
	public ArrayList<Appointment> searchAllAppts() throws PersistenceException;

	/**
	 * Looks for an Appointment object in the database. If unsuccessful, returns
	 * null.
	 * 
	 * @param appt
	 *            the Appointment to be found
	 * @return the appt searched for, else null
	 */
	public Appointment searchByAppt(Appointment appt) throws PersistenceException;
	
	/**
	 * Looks for an Appointment object in the database. If unsuccessful, returns
	 * null.
	 * 
	 * @param anr
	 *            the Appointment to be found
	 * @return the Appointment searched for, else null
	 */
	public Appointment searchByAppt(Integer anr) throws PersistenceException;

	/**
	 * Searches the database for all Appointments belonging to a specific
	 * booking and returns them as an ArrayList of Appointments.
	 * 
	 * @param booking
	 * @return all Appointments belonging to booking
	 */
	public ArrayList<Appointment> searchByBooking(Booking booking) throws PersistenceException;
	
	/**
	 * Searches the database for all Appointments belonging to a specific
	 * booking and returns them as an ArrayList of Appointments.
	 * 
	 * @param bnr
	 * @return all Appointments belonging to bnr
	 */
	public ArrayList<Appointment> searchByBooking(Integer bnr) throws PersistenceException;

	/**
	 * Filters the Appointments within the database by two dates.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return all Appointments from startDate to endDate
	 */
	public ArrayList<Appointment> searchByDate(Date startDate, Date endDate) throws PersistenceException;

	/**
	 * Searches the database for all Appointments that have a specific Horse reserved.
	 * 
	 * @param horse the Horse object used as the search criteria
	 * @return an ArrayList of Appointments
	 */
	public ArrayList<Appointment> searchByHorse(Horse horse) throws PersistenceException;
	
	/**
	 * Searches the database for all Appointments that have a specific Horse reserved.
	 * 
	 * @param hname the Horse name used as the search criteria
	 * @return an ArrayList of Appointments
	 */
	public ArrayList<Appointment> searchByHorse(String hname) throws PersistenceException;

	/**
	 * Updates a specific Appointment using the data stored in the object passed to it.
	 * 
	 * @param appt the Appointment to be updated
	 * @return true if update successful, else false
	 */
	public boolean updateAppt(Appointment appt) throws PersistenceException;

	/**
	 * Marks all Appointments in the database between two dates as no longer editable.
	 * 
	 * @param now the current date
	 * @param limit the specified limit
	 * @throws PersistenceException
	 */
	public void updateAll(Date now, Date limit) throws PersistenceException;

	/**
	 * Marks an Appointment row for deletion.
	 * 
	 * @param appt the Appointment to be deleted
	 * @return the Appointment deleted if successful, else false
	 */
	public Appointment deleteAppt(Appointment appt) throws PersistenceException;
}
