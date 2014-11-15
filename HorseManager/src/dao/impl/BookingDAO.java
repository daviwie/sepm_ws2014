package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import persistence.H2Handler;
import dao.IBookingDAO;
import entities.Booking;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
public class BookingDAO implements IBookingDAO {

	private PreparedStatement insertStmt;
	private PreparedStatement searchByBookingStmt;
	private PreparedStatement searchAllStmt;
	private PreparedStatement searchByDateStmt;
	private PreparedStatement searchByCustomerStmt;
	private PreparedStatement updateStmt;
	private PreparedStatement deleteStmt;

	private static final Logger logger = LogManager.getLogger("BookingDAO");

	/**
	 * Constructor initializes all of the PreparedStatements used in the DAO for
	 * database access.
	 * 
	 * @throws PersistenceException
	 */
	public BookingDAO() throws PersistenceException {
		try {
			insertStmt = H2Handler.getInstance().getConnection().prepareStatement(
					"INSERT INTO Booking (bnr, dayofbooking, customer) VALUES (nextval('seq_bnr'), ?, ?);");
			searchByBookingStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Booking b WHERE b.bnr = ?;");
			searchAllStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Booking;");
			searchByDateStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Booking WHERE dayofbooking >= ? AND dayofbooking <= ?;");
			searchByCustomerStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Booking WHERE customer = ?;");
			updateStmt = H2Handler.getInstance().getConnection().prepareStatement("UPDATE Booking SET customer = ? WHERE bnr = ?;");
			deleteStmt = H2Handler.getInstance().getConnection().prepareStatement("DELETE FROM Booking WHERE bnr = ?");
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Database unavailable");
		}
	}

	@Override
	public Booking createBooking(Booking booking) throws PersistenceException {
		try {
			insertStmt.setDate(1, booking.getDayOfBooking());
			insertStmt.setString(2, booking.getCustomer());
			int rInt = insertStmt.executeUpdate();

			ResultSet rs = insertStmt.getGeneratedKeys();
			while (rs.next()) {
				// logger.debug(rs.getInt(1));
				booking.setBnr(rs.getInt(1));
			}

			if (rInt == 1)
				return booking;
			else
				return null;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Insert failed, please check values and try again");
		}
	}

	@Override
	public ArrayList<Booking> searchAllBookings() throws PersistenceException {
		try {
			ResultSet rs = searchAllStmt.executeQuery();
			ArrayList<Booking> rList = new ArrayList<Booking>();
			Booking rBooking = null;

			while (rs.next()) {
				rBooking = new Booking(rs.getInt(1), rs.getDate(2), rs.getString(3));
				rBooking.setaList(DAOHandler.getInstance().getAppointmentDAO().searchByBooking(rBooking));
				rList.add(rBooking);
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, database currently unavailable");
		}
	}

	@Override
	public Booking searchByBooking(Booking booking) throws PersistenceException {
		try {
			searchByBookingStmt.setInt(1, booking.getBnr());
			ResultSet rs = searchByBookingStmt.executeQuery();
			Booking rBooking = null;

			while (rs.next()) {
				rBooking = new Booking(rs.getInt(1), rs.getDate(2), rs.getString(3));
				rBooking.setaList(DAOHandler.getInstance().getAppointmentDAO().searchByBooking(rBooking));
				// logger.debug(rBooking.toString());
			}

			return rBooking;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public Booking searchByBooking(Integer booking) throws PersistenceException {
		try {
			searchByBookingStmt.setInt(1, booking);
			ResultSet rs = searchByBookingStmt.executeQuery();
			Booking rBooking = null;

			while (rs.next()) {
				rBooking = new Booking(rs.getInt(1), rs.getDate(2), rs.getString(3));
				rBooking.setaList(DAOHandler.getInstance().getAppointmentDAO().searchByBooking(rBooking));
				// logger.debug(rBooking.toString());
			}

			return rBooking;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public ArrayList<Booking> searchBookingByDate(Date startDate, Date endDate) throws PersistenceException {
		try {
			searchByDateStmt.setDate(1, startDate);
			searchByDateStmt.setDate(2, endDate);
			ResultSet rs = searchByDateStmt.executeQuery();
			ArrayList<Booking> rList = new ArrayList<Booking>();
			Booking rBooking = null;

			while (rs.next()) {
				rBooking = new Booking(rs.getInt(1), rs.getDate(2), rs.getString(3));
				rBooking.setaList(DAOHandler.getInstance().getAppointmentDAO().searchByBooking(rBooking));
				rList.add(rBooking);
				// logger.debug(rBooking.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public ArrayList<Booking> searchByCustomer(String customer) throws PersistenceException {
		try {
			searchByCustomerStmt.setString(1, customer);
			ResultSet rs = searchByCustomerStmt.executeQuery();
			ArrayList<Booking> rList = new ArrayList<Booking>();
			Booking rBooking = null;
			while (rs.next()) {
				rBooking = new Booking(rs.getInt(1), rs.getDate(2), rs.getString(3));
				rBooking.setaList(DAOHandler.getInstance().getAppointmentDAO().searchByBooking(rBooking));
				rList.add(rBooking);
				// logger.debug(rBooking.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}

	}

	@Override
	public boolean updateBooking(Booking booking) throws PersistenceException {
		try {
			updateStmt.setString(1, booking.getCustomer());
			updateStmt.setInt(2, booking.getBnr());
			int rInt = updateStmt.executeUpdate();
			// logger.debug(rInt);

			if (rInt == 1)
				return true;
			else
				return false;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Update on Booking row failed, parameters invalid");
		}
	}

	@Override
	public void deleteBooking(Booking apptBooking) throws PersistenceException {
		try {
			deleteStmt.setInt(1, apptBooking.getBnr());
			deleteStmt.executeUpdate();
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Delete failed");
		}
	}

}
