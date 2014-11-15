package service;

import java.sql.Date;
import java.util.ArrayList;

import entities.Booking;
import exception.InvalidInputException;

/**
 * @author David Wietstruk 0706376
 *
 */
public interface IBookingService {
	/**
	 * Inserts a Booking into the database. All input parameters are checked to
	 * make sure everything is valid. Error messages are returned via
	 * InvalidInputException.
	 * 
	 * @param booking
	 *            The Booking to be inserted into the database
	 * @return The Booking inserted into the database if successful, else null
	 * @throws InvalidInputException
	 */
	public Booking insertBooking(Booking booking) throws InvalidInputException;

	/**
	 * Searches for a specific Booking. All input parameters are checekd to make
	 * sure everything is valid. Error messages are returned via
	 * InvalidInputException.
	 * 
	 * @param booking
	 *            The Booking for which to be searched
	 * @return The Booking found if successful, else null
	 * @throws InvalidInputException
	 */
	public Booking searchByBooking(Booking booking) throws InvalidInputException;

	/**
	 * Searches for a specific Booking. All input parameters are checekd to make
	 * sure everything is valid. Error messages are returned via
	 * InvalidInputException.
	 * 
	 * @param booking
	 *            The Booking for which to be searched
	 * @return The Booking found if successful, else null
	 * @throws InvalidInputException
	 */
	public Booking searchByBooking(Integer booking) throws InvalidInputException;

	/**
	 * Searches for all Bookings stored in the database. Any database errors are
	 * returned via an InvalidInputException.
	 * 
	 * @return An ArrayList of all Bookings stored in the database
	 * @throws InvalidInputException
	 */
	public ArrayList<Booking> searchAllBookings() throws InvalidInputException;

	/**
	 * Searches for all Bookings with a dateBooked between the two bounds. The
	 * method also checks to make sure that the input is not invalid in any way
	 * and that the endDate is indeed not before startDate. All Bookings made in
	 * the past are also ignored, so the lower bound must be the current day or
	 * later. Errors are returned via the InvalidInputException.
	 * 
	 * @param startDate
	 *            the lower bound of the search query
	 * @param endDate
	 *            the upper bound of the search query
	 * @return an ArrayList of all Booking objects that fit to the query
	 * @throws InvalidInputException
	 */
	public ArrayList<Booking> searchBookingByDate(Date startDate, Date endDate) throws InvalidInputException;

	/**
	 * Searches for all Bookings that belong to a certain customer. Method also
	 * checks to make sure that parameter is valid. If input is invalid in some
	 * way (empty, null) then an error message is returned via the
	 * InvalidInputException.
	 * 
	 * @param customer
	 *            The search parameter
	 * @return An ArrayList of Bookings that match the search query
	 * @throws InvalidInputException
	 */
	public ArrayList<Booking> searchByCustomer(String customer) throws InvalidInputException;

	/**
	 * Updates a specific Booking row. Method also makes sure input is not
	 * somehow invalid. Error messages are returned via the
	 * InvalidInputException.
	 * 
	 * @param booking the Booking to updated
	 * @return true if update successful, else false
	 * @throws InvalidInputException
	 */
	public boolean updateBooking(Booking booking) throws InvalidInputException;

	/**
	 * Deletes a booking completely from the database. Should only ever be used
	 * when the process of adding a series of appointments to the database fails
	 * completely and no appointments are actually added after inserting the
	 * initial booking row.
	 * 
	 * @param apptBooking the booking to be deleted
	 * @throws InvalidInputException
	 */
	public void deleteBooking(Booking apptBooking) throws InvalidInputException;
}
