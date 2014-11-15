package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import persistence.H2Handler;
import dao.IHorseDAO;
import entities.Horse;
import exception.PersistenceException;

/**
 * @author David Wietstruk 0706376
 *
 */
public class HorseDAO implements IHorseDAO {

	private PreparedStatement insertStmt;
	private PreparedStatement searchByHorseStmt;
	private PreparedStatement searchAllStmt;
	private PreparedStatement searchByWeightStmt;
	private PreparedStatement searchByHeightStmt;
	private PreparedStatement updateStmt;
	private PreparedStatement deleteStmt;
	// the two ? for time need to be equivalent
	// If this SQL statement returns at least 1 row -> CLASH!!!
	private PreparedStatement availableStmt;

	private static final Logger logger = LogManager.getLogger("HorseDAO");

	/**
	 * Constructor initializes all of the PreparedStatements in the DAO for
	 * database access.
	 * 
	 * @throws PersistenceException
	 */
	public HorseDAO() throws PersistenceException {
		try {
			insertStmt = H2Handler.getInstance().getConnection()
					.prepareStatement("INSERT INTO Horse (name, weight, height, imgfilepath, bdate) VALUES (?, ?, ?, ?, ?);");
			searchByHorseStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Horse h WHERE h.name = ? AND deleted = 0;");
			searchAllStmt = H2Handler.getInstance().getConnection().prepareStatement("SELECT * FROM Horse WHERE deleted = 0;");
			searchByWeightStmt = H2Handler.getInstance().getConnection()
					.prepareStatement("SELECT * FROM Horse WHERE weight >= ? AND weight <= ? AND deleted = 0;");
			searchByHeightStmt = H2Handler.getInstance().getConnection()
					.prepareStatement("SELECT * FROM Horse WHERE height >= ? AND height <= ? AND deleted = 0;");
			updateStmt = H2Handler.getInstance().getConnection()
					.prepareStatement("UPDATE Horse SET weight = ?, height = ?, imgfilepath = ? WHERE name = ? AND deleted = 0;");
			deleteStmt = H2Handler.getInstance().getConnection().prepareStatement("UPDATE Horse SET deleted = 1 WHERE name = ? AND deleted = 0;");
			availableStmt = H2Handler
					.getInstance()
					.getConnection()
					.prepareStatement(
							"SELECT * FROM Appointment a JOIN Horse h ON a.hname = h.name WHERE a.adate = ? AND (a.starttime <= ? AND ? <= a.endtime) AND a.hname = ?");
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Database unavailable");
		}
	}

	@Override
	public Horse createHorse(Horse horse) throws PersistenceException {
		try {
			insertStmt.setString(1, horse.getName());
			insertStmt.setInt(2, horse.getWeight());
			insertStmt.setInt(3, horse.getHeight());
			insertStmt.setString(4, horse.getImgfilepath());
			insertStmt.setDate(5, horse.getBdate());

			int rInt = insertStmt.executeUpdate();
			// logger.debug(rInt);

			if (rInt == 1) 
				return horse;
			else
				return null;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Insert failed, please check values and try again");
		}
	}

	@Override
	public ArrayList<Horse> searchAllHorses() throws PersistenceException {
		try {
			ResultSet rs = searchAllStmt.executeQuery();
			ArrayList<Horse> rList = new ArrayList<Horse>();
			Horse rHorse = null;

			while (rs.next()) {
				rHorse = new Horse(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getBoolean(6));
				rList.add(rHorse);
				// logger.debug(rHorse.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, database currently unavailable");
		}
	}

	@Override
	public Horse searchByHorse(Horse horse) throws PersistenceException {
		try {
			searchByHorseStmt.setString(1, horse.getName());
			ResultSet rs = searchByHorseStmt.executeQuery();
			Horse rHorse = null;

			while (rs.next()) {
				rHorse = new Horse(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getBoolean(6));
				// logger.debug(rHorse.toString());
			}

			return rHorse;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public Horse searchByHorse(String name) throws PersistenceException {
		try {
			searchByHorseStmt.setString(1, name);
			ResultSet rs = searchByHorseStmt.executeQuery();
			Horse rHorse = null;

			while (rs.next()) {
				rHorse = new Horse(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getBoolean(6));
				// logger.debug(rHorse.toString());
			}

			return rHorse;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public ArrayList<Horse> searchHorseByWeight(Integer lowerWeightLimit, Integer upperWeightLimit) throws PersistenceException {
		try {
			searchByWeightStmt.setInt(1, lowerWeightLimit);
			searchByWeightStmt.setInt(2, upperWeightLimit);
			ResultSet rs = searchByWeightStmt.executeQuery();
			ArrayList<Horse> rList = new ArrayList<Horse>();
			Horse rHorse = null;

			while (rs.next()) {
				rHorse = new Horse(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getBoolean(6));
				rList.add(rHorse);
				// logger.debug(rHorse.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public ArrayList<Horse> searchHorseByHeight(Integer lowerHeightLimit, Integer upperHeightLimit) throws PersistenceException {
		try {
			searchByHeightStmt.setInt(1, lowerHeightLimit);
			searchByHeightStmt.setInt(2, upperHeightLimit);
			ResultSet rs = searchByHeightStmt.executeQuery();
			ArrayList<Horse> rList = new ArrayList<Horse>();
			Horse rHorse = null;

			while (rs.next()) {
				rHorse = new Horse(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getBoolean(6));
				rList.add(rHorse);
				// logger.debug(rHorse.toString());
			}

			return rList;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public boolean isHorseAvailable(Date date, Time startTime, Time endTime, Horse horse) throws PersistenceException {
		try {
			availableStmt.setDate(1, date);
			availableStmt.setTime(2, startTime);
			availableStmt.setTime(3, startTime);
			availableStmt.setString(4, horse.getName());

			ResultSet rs = availableStmt.executeQuery();
			boolean firstCondition = rs.next();

			availableStmt.setTime(2, endTime);
			availableStmt.setTime(3, endTime);

			rs = availableStmt.executeQuery();
			boolean secondCondition = rs.next();

			if (firstCondition && secondCondition)
				return false;
			else
				return true;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public boolean isHorseAvailable(Date date, Time startTime, Time endTime, String hname) throws PersistenceException {
		try {
			availableStmt.setDate(1, date);
			availableStmt.setTime(2, startTime);
			availableStmt.setTime(3, startTime);
			availableStmt.setString(4, hname);

			ResultSet rs = availableStmt.executeQuery();
			boolean firstCondition = rs.next();

			availableStmt.setTime(2, endTime);
			availableStmt.setTime(3, endTime);

			rs = availableStmt.executeQuery();
			boolean secondCondition = rs.next();

			if (firstCondition && secondCondition)
				return false;
			else
				return true;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Search failed, please check search parameters and try again");
		}
	}

	@Override
	public boolean updateHorse(Horse horse) throws PersistenceException {
		try {
			updateStmt.setInt(1, horse.getWeight());
			updateStmt.setInt(2, horse.getHeight());
			updateStmt.setString(3, horse.getImgfilepath());
			updateStmt.setString(4, horse.getName());
			int rInt = updateStmt.executeUpdate();

			// logger.debug(rInt);

			if (rInt == 1)
				return true;
			else
				return false;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Update on Horse row failed, parameters invalid");
		}
	}

	@Override
	public Horse deleteHorse(Horse horse) throws PersistenceException {
		try {
			deleteStmt.setString(1, horse.getName());
			int rInt = deleteStmt.executeUpdate();

			// logger.debug(rInt);

			if (rInt == 1)
				return horse;
			else
				return null;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Delete on Horse row failed, parameters invalid");
		}
	}

	@Override
	public Horse deleteHorse(String horse) throws PersistenceException {
		try {
			Horse rHorse = searchByHorse(horse);
			deleteStmt.setString(1, horse);
			int rInt = deleteStmt.executeUpdate();

			// logger.debug(rInt);

			if (rInt == 1)
				return rHorse;
			else
				return null;
		} catch (SQLException e) {
			logger.warn(e.getMessage());
			throw new PersistenceException("Delete on Horse row failed, parameters invalid");
		}
	}

}
