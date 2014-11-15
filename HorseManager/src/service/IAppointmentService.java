package service;

import java.sql.Date;
import java.util.ArrayList;

import entities.Appointment;
import entities.Booking;
import entities.Horse;
import exception.InvalidInputException;

/**
 * @author David Wietstruk 0706376
 *
 */
public interface IAppointmentService {
	/**
	 * This method validates the Appointment object passed to it and returns any
	 * error messages via an InvalidInputException if the object is somehow
	 * invalid. If input is valid, the Appointment object is passed to the
	 * IAppointmentDAO and inserted into the database.
	 * 
	 * @param appt
	 *            The Appointment to be inserted into the database.
	 * @return The Appointment inserted in the database if successful, else
	 *         null,
	 * @throws InvalidInputException
	 */
	public Appointment insertAppointment(Appointment appt) throws InvalidInputException;

	/**
	 * Gets all of the Appointment objects stored in the database. Any error
	 * messages from the database are returned via the InvalidInputException.
	 * 
	 * @return An ArrayList of Appointment objects
	 * @throws InvalidInputException
	 */
	public ArrayList<Appointment> searchAllAppointments() throws InvalidInputException;

	/**
	 * Searches for a specific Appointment and performs the necessary validation
	 * of the input. If input is invalid, the error message is returned via the
	 * InvalidInputException.
	 * 
	 * @param appt
	 *            The Appointment to be searched
	 * @return The Appointment found, else null
	 * @throws InvalidInputException
	 */
	public Appointment searchByAppointment(Appointment appt) throws InvalidInputException;

	/**
	 * Searches for a specific Appointment and performs the necessary validation
	 * of the input. If input is invalid, the error message is returned via the
	 * InvalidInputException.
	 * 
	 * @param anr
	 *            The Appointment number to be searched
	 * @return The Appointment found, else null
	 * @throws InvalidInputException
	 */
	public Appointment searchByAppointment(Integer anr) throws InvalidInputException;

	/**
	 * Searches for all of the Appointments associated with a specific Booking
	 * object. Method also performs additional input validation to make sure
	 * that any invalid parameters are caught here. Error messages are returned
	 * by InvalidInputException.
	 * 
	 * @param booking
	 *            The Booking used to look for its Appointments
	 * @return An ArrayList of all Appointments associated with the Booking
	 * @throws InvalidInputException
	 */
	public ArrayList<Appointment> searchByBooking(Booking booking) throws InvalidInputException;

	/**
	 * Searches for all of the Appointments associated with a specific Booking
	 * object. Method also performs additional input validation to make sure
	 * that any invalid parameters are caught here. Error messages are returned
	 * by InvalidInputException.
	 * 
	 * @param bnr
	 *            The Booking used to look for its Appointments
	 * @return An ArrayList of all Appointments associated with the Booking
	 * @throws InvalidInputException
	 */
	public ArrayList<Appointment> searchByBooking(Integer bnr) throws InvalidInputException;

	/**
	 * Searches for all Appointments that take place between two dates. Input is
	 * validated in this method to make sure that endDate is after startDate and
	 * that only dates in the future can be passed as parameters. Everything in
	 * the past is unimportant. Error messages are returned by
	 * InvalidInputException.
	 * 
	 * @param startDate
	 *            the lower bound of the search query
	 * @param endDate
	 *            the upper bound of the search query
	 * @return an ArrayList of Appointments
	 * @throws InvalidInputException
	 */
	public ArrayList<Appointment> searchByDate(Date startDate, Date endDate) throws InvalidInputException;

	/**
	 * Searches for all Appointments that reserve a specific Horse. Input is
	 * validated in this method to make sure that no invalid parameters are
	 * passed to the DAO and database queries. Error messages are returned by
	 * InvalidInputException.
	 * 
	 * @param horse
	 *            The Horse used as the search parameter
	 * @return An ArrayList of all Appointments associated with a specific Horse
	 * @throws InvalidInputException
	 */
	public ArrayList<Appointment> searchByHorse(Horse horse) throws InvalidInputException;

	/**
	 * Searches for all Appointments that reserve a specific Horse. Input is
	 * validated in this method to make sure that no invalid parameters are
	 * passed to the DAO and database queries. Error messages are returned by
	 * InvalidInputException.
	 * 
	 * @param hname
	 *            The Horse used as the search parameter
	 * @return An ArrayList of all Appointments associated with a specific Horse
	 * @throws InvalidInputException
	 */
	public ArrayList<Appointment> searchByHorse(String hname) throws InvalidInputException;

	/**
	 * Updates a row in the database for a specific Appointment. If successful
	 * the method returns true, else false. Input is also validated to make sure
	 * that nothing invalid is passed to the DAO and database queries. Error
	 * messages are returned by InvalidInputException.
	 * 
	 * @param appt
	 *            The Appointment that needs to be updated in the database
	 * @return true if successful, else false
	 * @throws InvalidInputException
	 */
	public boolean updateAppointment(Appointment appt) throws InvalidInputException;

	/**
	 * Marks all appointments in the database as no longer editable between two
	 * specified dates. 
	 * 
	 * @param now
	 *            current date
	 * @param limit
	 *            the upper bound
	 * @throws InvalidInputException
	 */
	public void updateAll(Date now, Date limit) throws InvalidInputException;

	/**
	 * Marks a specific Appointment row for deletion. If successful the method
	 * returns the Appointment that was deleted, else null. Input validation is
	 * also done to make sure nothing invalid is passed to the DAO and the
	 * database queries. Error messages are returned by InvalidInputException.
	 * 
	 * @param appt
	 *            The Appointment to be marked for deletion
	 * @return
	 * @throws InvalidInputException
	 */
	public Appointment deleteAppointment(Appointment appt) throws InvalidInputException;
}
