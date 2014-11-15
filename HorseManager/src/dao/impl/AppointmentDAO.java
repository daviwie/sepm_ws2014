package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import persistence.H2Handler;
import dao.IAppointmentDAO;
import entities.Appointment;
import entities.Booking;
import entities.Horse;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
public class AppointmentDAO implements IAppointmentDAO {

	private PreparedStatement insertStmt;
	private PreparedStatement searchByApptStmt;
	private PreparedStatement searchAllStmt;
	private PreparedStatement searchByBookingStmt;
	private PreparedStatement searchByDateStmt;
	private PreparedStatement searchByHorseStmt;
	private PreparedStatement updateStmt;
	private PreparedStatement updateAllStmt;
	private PreparedStatement deleteStmt;

	private static final Logger logger = LogManager.getLogger("AppointmentDAO");

	/**
	 * Constructor initializes all of the PreparedStatements for the DAO that
	 * allow database access.
	 * 
	 * @throws PersistenceException
	 */
	public AppointmentDAO() throws PersistenceException {
		try {
			insertStmt = H2Handler.getInstance().getConnection().prepareStatement(
					"INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), ?, ?, ?, ?, ?)");
			searchByApptStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Appointment WHERE anr = ?;");
			searchAllStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Appointment;");
			searchByBookingStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Appointment WHERE bnr = ?;");
			searchByDateStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Appointment WHERE adate >= ? AND adate <= ?;");
			searchByHorseStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Appointment WHERE hname = ?;");
			updateStmt = H2Handler.getInstance().getConnection().prepareStatement(
					"UPDATE Appointment SET adate = ?, starttime = ?, endtime = ?, editable = ?, hname = ? WHERE anr = ?;");
			updateAllStmt = H2Handler.getInstance().getConnection().prepareStatement("UPDATE Appointment SET editable = false WHERE ? <= adate AND adate <= ?;");
			deleteStmt = H2Handler.getInstance().getConnection().prepareStatement("UPDATE Appointment SET deleted = 1 WHERE anr = ?;");
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Database unavailable");
		}
	}

	@Override
	public Appointment createAppt(Appointment appt, Booking booking, Horse horse) throws PersistenceException {
		try {
			insertStmt.setDate(1, appt.getaDate());
			insertStmt.setTime(2, appt.getStartTime());
			insertStmt.setTime(3, appt.getEndTime());
			insertStmt.setInt(4, booking.getBnr());
			insertStmt.setString(5, horse.getName());
			int rInt = insertStmt.executeUpdate();

			ResultSet rs = insertStmt.getGeneratedKeys();
			while (rs.next()) {
				// logger.debug(rs.getInt(1));
				appt.setAnr(rs.getInt(1));
			}

			if (rInt == 1)
				return appt;
			else
				return null;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Insert failed, please check values and try again");
		}
	}

	@Override
	public ArrayList<Appointment> searchAllAppts() throws PersistenceException {
		try {
			ResultSet rs = searchAllStmt.executeQuery();
			ArrayList<Appointment> rList = new ArrayList<Appointment>();
			Appointment rAppt = null;

			while (rs.next()) {
				rAppt = new Appointment(rs.getInt(1), rs.getDate(2), rs.getTime(3), rs.getTime(4), rs.getBoolean(5), rs.getBoolean(6), rs.getInt(7),
						rs.getString(8));
				rList.add(rAppt);
				// logger.debug(rAppt.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, database currently unavailable");
		}
	}

	@Override
	public Appointment searchByAppt(Appointment appt) throws PersistenceException {
		try {
			searchByApptStmt.setInt(1, appt.getAnr());
			ResultSet rs = searchByApptStmt.executeQuery();
			Appointment rAppt = null;

			while (rs.next()) {
				rAppt = new Appointment(rs.getInt(1), rs.getDate(2), rs.getTime(3), rs.getTime(4), rs.getBoolean(5), rs.getBoolean(6), rs.getInt(7),
						rs.getString(8));
				// logger.debug(rAppt.toString());
			}

			return rAppt;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public Appointment searchByAppt(Integer anr) throws PersistenceException {
		try {
			searchByApptStmt.setInt(1, anr);
			ResultSet rs = searchByApptStmt.executeQuery();
			Appointment rAppt = null;

			while (rs.next()) {
				rAppt = new Appointment(rs.getInt(1), rs.getDate(2), rs.getTime(3), rs.getTime(4), rs.getBoolean(5), rs.getBoolean(6), rs.getInt(7),
						rs.getString(8));
				// logger.debug(rAppt.toString());
			}

			return rAppt;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public ArrayList<Appointment> searchByBooking(Booking booking) throws PersistenceException {
		try {
			searchByBookingStmt.setInt(1, booking.getBnr());
			ResultSet rs = searchByBookingStmt.executeQuery();
			ArrayList<Appointment> rList = new ArrayList<Appointment>();
			Appointment rAppt = null;

			while (rs.next()) {
				rAppt = new Appointment(rs.getInt(1), rs.getDate(2), rs.getTime(3), rs.getTime(4), rs.getBoolean(5), rs.getBoolean(6), rs.getInt(7),
						rs.getString(8));
				rList.add(rAppt);
				// logger.debug(rAppt.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public ArrayList<Appointment> searchByBooking(Integer bnr) throws PersistenceException {
		try {
			searchByBookingStmt.setInt(1, bnr);
			ResultSet rs = searchByBookingStmt.executeQuery();
			ArrayList<Appointment> rList = new ArrayList<Appointment>();
			Appointment rAppt = null;

			while (rs.next()) {
				rAppt = new Appointment(rs.getInt(1), rs.getDate(2), rs.getTime(3), rs.getTime(4), rs.getBoolean(5), rs.getBoolean(6), rs.getInt(7),
						rs.getString(8));
				rList.add(rAppt);
				// logger.debug(rAppt.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public ArrayList<Appointment> searchByDate(Date startDate, Date endDate) throws PersistenceException {
		try {
			searchByDateStmt.setDate(1, startDate);
			searchByDateStmt.setDate(2, endDate);
			ResultSet rs = searchByDateStmt.executeQuery();
			ArrayList<Appointment> rList = new ArrayList<Appointment>();
			Appointment rAppt = null;

			while (rs.next()) {
				rAppt = new Appointment(rs.getInt(1), rs.getDate(2), rs.getTime(3), rs.getTime(4), rs.getBoolean(5), rs.getBoolean(6), rs.getInt(7),
						rs.getString(8));
				rList.add(rAppt);
				// logger.debug(rAppt.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public ArrayList<Appointment> searchByHorse(Horse horse) throws PersistenceException {
		try {
			searchByHorseStmt.setString(1, horse.getName());
			ResultSet rs = searchByHorseStmt.executeQuery();
			ArrayList<Appointment> rList = new ArrayList<Appointment>();
			Appointment rAppt = null;

			while (rs.next()) {
				rAppt = new Appointment(rs.getInt(1), rs.getDate(2), rs.getTime(3), rs.getTime(4), rs.getBoolean(5), rs.getBoolean(6), rs.getInt(7),
						rs.getString(8));
				rList.add(rAppt);
				// logger.debug(rAppt.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public ArrayList<Appointment> searchByHorse(String hname) throws PersistenceException {
		try {
			searchByHorseStmt.setString(1, hname);
			ResultSet rs = searchByHorseStmt.executeQuery();
			ArrayList<Appointment> rList = new ArrayList<Appointment>();
			Appointment rAppt = null;

			while (rs.next()) {
				rAppt = new Appointment(rs.getInt(1), rs.getDate(2), rs.getTime(3), rs.getTime(4), rs.getBoolean(5), rs.getBoolean(6), rs.getInt(7),
						rs.getString(8));
				rList.add(rAppt);
				// logger.debug(rAppt.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public boolean updateAppt(Appointment appt) throws PersistenceException {
		try {
			updateStmt.setDate(1, appt.getaDate());
			updateStmt.setTime(2, appt.getStartTime());
			updateStmt.setTime(3, appt.getEndTime());
			updateStmt.setBoolean(4, appt.isEditable());
			updateStmt.setString(5, appt.getHname());
			updateStmt.setInt(6, appt.getAnr());

			int rInt = updateStmt.executeUpdate();

			// logger.debug(rInt);

			if (rInt == 1)
				return true;
			else
				return false;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Update on Appointment row failed, parameters invalid");
		}
	}

	@Override
	public void updateAll(Date now, Date limit) throws PersistenceException {
		try {
			updateAllStmt.setDate(1, now);
			updateAllStmt.setDate(2, limit);
			updateAllStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			throw new PersistenceException("Update failed, parameters invalid");
		}
	}

	@Override
	public Appointment deleteAppt(Appointment appt) throws PersistenceException {
		try {
			deleteStmt.setInt(1, appt.getAnr());
			int rInt = deleteStmt.executeUpdate();

			// logger.debug(rInt);

			if (rInt == 1)
				return appt;
			else
				return null;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Delete on Appointment row failed, parameters invalid");
		}
	}

}
