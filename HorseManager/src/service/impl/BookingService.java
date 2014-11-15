package service.impl;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.impl.DAOHandler;
import entities.Booking;
import exception.InvalidInputException;
import exception.PersistenceException;
import service.IBookingService;

/**
 * @author David Wietstruk 0706376
 *
 */
public class BookingService implements IBookingService {

	private static final Logger logger = LogManager.getLogger("BookingService");

	@Override
	public Booking insertBooking(Booking booking) throws InvalidInputException {
		try {
			if (booking == null) {
				throw new InvalidInputException("Input cannot be null!");
			} else {
				// java.util.Date today = new java.util.Date();
				// if (!booking.getDayOfBooking().after(today)) {
				// logger.debug(booking.getDayOfBooking().toString());
				// throw new
				// InvalidInputException("Day of booking cannot be before today.");
				// } else {
				Booking rBooking = DAOHandler.getInstance().getBookingDAO().createBooking(booking);
				if (rBooking == null)
					throw new InvalidInputException("This booking is already in the database!");
				else
					return rBooking;
				// }
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Booking> searchAllBookings() throws InvalidInputException {
		try {
			return DAOHandler.getInstance().getBookingDAO().searchAllBookings();
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public Booking searchByBooking(Booking booking) throws InvalidInputException {
		try {
			if (booking == null) {
				throw new InvalidInputException("Input cannot be null!");
			} else {
				Booking rBooking = DAOHandler.getInstance().getBookingDAO().searchByBooking(booking);
				if (rBooking == null)
					throw new InvalidInputException("This booking is not in the database, please review input.");
				else
					return rBooking;
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public Booking searchByBooking(Integer booking) throws InvalidInputException {
		try {
			if (booking == null) {
				throw new InvalidInputException("Input cannot be null!");
			} else {
				if (booking <= 0) {
					throw new InvalidInputException("Booking number cannot be less than or equal to 0, please review input and try again.");
				} else {
					Booking rBooking = DAOHandler.getInstance().getBookingDAO().searchByBooking(booking);
					if (rBooking == null)
						throw new InvalidInputException("Booking not found, please review input and try again.");
					else
						return rBooking;
				}
			}
		} catch (PersistenceException e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Booking> searchBookingByDate(Date startDate, Date endDate) throws InvalidInputException {
		try {
			if (startDate == null || endDate == null) {
				throw new InvalidInputException("Input cannot be null!");
			} else {
				if (endDate.before(startDate)) {
					throw new InvalidInputException("Upper bound of search query cannot be before lower bound");
				} else {
					return DAOHandler.getInstance().getBookingDAO().searchBookingByDate(startDate, endDate);
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Booking> searchByCustomer(String customer) throws InvalidInputException {
		try {
			if (customer == null || customer.isEmpty()) {
				throw new InvalidInputException("Horse cannot be null!");
			} else {
				return DAOHandler.getInstance().getBookingDAO().searchByCustomer(customer);
			}
		} catch (PersistenceException e) {
			// logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public boolean updateBooking(Booking booking) throws InvalidInputException {
		try {
			if (booking == null) {
				throw new InvalidInputException("Horse cannot be null!");
			} else {
				boolean rBool = DAOHandler.getInstance().getBookingDAO().updateBooking(booking);
				if (rBool)
					return rBool;
				else
					throw new InvalidInputException("Horse either not in database or update not possible at this time");
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public void deleteBooking(Booking apptBooking) throws InvalidInputException {
		try {
			DAOHandler.getInstance().getBookingDAO().deleteBooking(apptBooking);
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

}
