package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import entities.Booking;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
public interface IBookingDAO {
	/**
	 * Inserts a keyless Booking object into the database, generates the key for
	 * the object and returns the complete Booking object to the calling method.
	 * 
	 * @param booking
	 *            the booking to be inserted into the database
	 * @param customer
	 *            the customer with which the booking is associated
	 * @param horse
	 *            the horse with which the booking is associated
	 * @return the complete booking (with generated key) that was added to the
	 *         database, else null
	 * @throws SQLException
	 */
	public Booking createBooking(Booking booking) throws PersistenceException;

	/**
	 * When a list of all non-deleted Booking objects needs to be returned, this
	 * method can be used.
	 * 
	 * @return an ArrayList of all non-deleted Booking objects in the database
	 * @throws SQLException
	 */
	public ArrayList<Booking> searchAllBookings() throws PersistenceException;

	/**
	 * If a specific booking is known and needs to be checked to see if it does
	 * indeed exist in the database, this method can be used.
	 * 
	 * @param booking
	 *            the booking to be extracted from the database
	 * @return the booking found in the database, else null
	 * @throws SQLException
	 */
	public Booking searchByBooking(Booking booking) throws PersistenceException;

	/**
	 * If a specific booking is known and needs to be checked to see if it does
	 * indeed exist in the database, this method can be used.
	 * 
	 * @param booking
	 *            the booking to be extracted from the database
	 * @return the booking found in the database, else null
	 * @throws SQLException
	 */
	public Booking searchByBooking(Integer booking) throws PersistenceException;

	/**
	 * Returns a list of all non-deleted Booking objects within a specific time
	 * period.
	 * 
	 * @param startDate
	 *            the lower bound of the time period to be searched
	 * @param endDate
	 *            the upper bound of the time period to be searched
	 * @return an ArrayList of all Booking objects that have dateBooked within
	 *         the specified time period
	 * @throws SQLException
	 */
	public ArrayList<Booking> searchBookingByDate(Date startDate, Date endDate) throws PersistenceException;

	/**
	 * Returns a list of all (deleted and non-deleted) bookings associated with
	 * a customer. This method is used to construct the booking confirmation
	 * message for a customer.
	 * 
	 * @param customer
	 *            the customer that requires a booking confirmation
	 * @return a list of all bookings made by a specific customer
	 * @throws SQLException
	 */
	public ArrayList<Booking> searchByCustomer(String customer) throws PersistenceException;

	/**
	 * If changes are made to an existing booking, this method updates the row
	 * in the database.
	 * 
	 * @param booking
	 *            the booking whose row in the database should be updated
	 * @return true if the update was successful, false if unsuccessful
	 * @throws SQLException
	 */
	public boolean updateBooking(Booking booking) throws PersistenceException;

	/**
	 * Deletes a booking completely from the database. Should only ever be used
	 * when the process of adding a series of appointments to the database fails
	 * completely and no appointments are actually added after inserting the
	 * initial booking row.
	 * 
	 * @param apptBooking
	 * @throws PersistenceException
	 */
	public void deleteBooking(Booking apptBooking) throws PersistenceException;

}
