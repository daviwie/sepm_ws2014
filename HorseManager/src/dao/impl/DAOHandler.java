package dao.impl;

import dao.IAppointmentDAO;
import dao.IBookingDAO;
import dao.IHorseDAO;
import exception.PersistenceException;

/**
 * Singleton that gets the Singleton for each DAO for database access.
 * 
 * @author David Wietstruk 0706376
 *
 */
public class DAOHandler {

	private static DAOHandler daoHandler = null;
	private static IBookingDAO bDAO = null;
	private static IHorseDAO hDAO = null;
	private static IAppointmentDAO aDAO = null;

	private DAOHandler() {

	}

	public static synchronized DAOHandler getInstance() {
		if (daoHandler == null)
			daoHandler = new DAOHandler();

		return daoHandler;
	}

	public IBookingDAO getBookingDAO() throws PersistenceException {
		if (bDAO == null) {
			bDAO = new BookingDAO();
			return bDAO;
		} else
			return bDAO;
	}

	public IHorseDAO getHorseDAO() throws PersistenceException {
		if (hDAO == null) {
			hDAO = new HorseDAO();
			return hDAO;
		} else
			return hDAO;
	}

	public IAppointmentDAO getAppointmentDAO() throws PersistenceException {
		if (aDAO == null) {
			aDAO = new AppointmentDAO();
			return aDAO;
		} else
			return aDAO;
	}

}
