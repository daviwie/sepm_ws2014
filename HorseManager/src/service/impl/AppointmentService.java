package service.impl;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.impl.DAOHandler;
import entities.Appointment;
import entities.Booking;
import entities.Horse;
import exception.InvalidInputException;
import exception.PersistenceException;
import service.IAppointmentService;

/**
 * @author David Wietstruk 0706376
 *
 */
public class AppointmentService implements IAppointmentService {

	private static final Logger logger = LogManager.getLogger("AppointmentService");

	@Override
	public Appointment insertAppointment(Appointment appt) throws InvalidInputException {
		try {
			if (appt == null) {
				throw new InvalidInputException("Input cannot be null!");
			} else {
				java.util.Date today = new java.util.Date();

				if (appt.getaDate().before(today)) {
					throw new InvalidInputException("Date of appointment cannot be before today");
				} else {
					Appointment rAppt;
					if (appt.getBnr() == null || appt.getHname() == null || appt.getHname().isEmpty()) {
						throw new InvalidInputException(
								"An appointment that does not belong to a booking or have a valid horse cannot be added to the database");
					} else {
							rAppt = DAOHandler
									.getInstance()
									.getAppointmentDAO()
									.createAppt(appt, ServiceHandler.getInstance().getBookingService().searchByBooking(appt.getBnr()),
											ServiceHandler.getInstance().getHorseService().searchByHorse(appt.getHname()));
					}

					if (rAppt == null) {
						throw new InvalidInputException("Appointment is already in the database or input is invalid. Review input and try again.");
					} else
						return rAppt;
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Appointment> searchAllAppointments() throws InvalidInputException {
		try {
			return DAOHandler.getInstance().getAppointmentDAO().searchAllAppts();
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public Appointment searchByAppointment(Appointment appt) throws InvalidInputException {
		try {
			if (appt == null) {
				throw new InvalidInputException("Input cannot be null!");
			} else {
				if (appt.getAnr() == null || appt.getAnr() <= 0) {
					throw new InvalidInputException("ANR cannot be null and must be greater than 0.");
				} else {
					Appointment rAppt = DAOHandler.getInstance().getAppointmentDAO().searchByAppt(appt);
					if (rAppt == null)
						throw new InvalidInputException("The appointment is not in the database, please review input.");
					else
						return rAppt;
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public Appointment searchByAppointment(Integer anr) throws InvalidInputException {
		try {
			if (anr == null) {
				throw new InvalidInputException("Input cannot be null");
			} else {
				if (anr <= 0) {
					throw new InvalidInputException("Input must be greater than 0");
				} else {
					Appointment rAppt = DAOHandler.getInstance().getAppointmentDAO().searchByAppt(anr);

					if (rAppt == null)
						throw new InvalidInputException("Appointment is not in the database, please review input.");
					else
						return rAppt;
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Appointment> searchByBooking(Booking booking) throws InvalidInputException {
		try {
			if (booking == null) {
				throw new InvalidInputException("Input cannot be null");
			} else {
				if (booking.getBnr() == null) {
					throw new InvalidInputException("Booking number cannot be null");
				} else {
					if (booking.getBnr() <= 0) {
						throw new InvalidInputException("Booking must be greater than 0");
					} else {
						return DAOHandler.getInstance().getAppointmentDAO().searchByBooking(booking);
					}
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Appointment> searchByBooking(Integer bnr) throws InvalidInputException {
		try {
			if (bnr == null) {
				throw new InvalidInputException("Input cannot be null");
			} else {
				if (bnr <= 0) {
					throw new InvalidInputException("Booking must be greater than 0");
				} else {
					return DAOHandler.getInstance().getAppointmentDAO().searchByBooking(bnr);
				}
			}

		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Appointment> searchByDate(Date startDate, Date endDate) throws InvalidInputException {
		try {
			if (startDate == null || endDate == null) {
				throw new InvalidInputException("Input cannot be null!");
			} else {
				if (endDate.before(startDate)) {
					throw new InvalidInputException("Upper bound of search query cannot be before lower bound");
				} else {
					return DAOHandler.getInstance().getAppointmentDAO().searchByDate(startDate, endDate);
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Appointment> searchByHorse(Horse horse) throws InvalidInputException {
		try {
			if (horse == null) {
				throw new InvalidInputException("Horse cannot be null!");
			} else {
				if (horse.getName() == null || horse.getName().isEmpty())
					throw new InvalidInputException("Horse name cannot be null or empty.");
				else
					return DAOHandler.getInstance().getAppointmentDAO().searchByHorse(horse);
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Appointment> searchByHorse(String hname) throws InvalidInputException {
		try {
			if (hname == null || hname.isEmpty()) {
				throw new InvalidInputException("Horse name cannot be null or empty");
			} else {
				return DAOHandler.getInstance().getAppointmentDAO().searchByHorse(hname);
			}
		} catch (PersistenceException e) {
			// logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public boolean updateAppointment(Appointment appt) throws InvalidInputException {
		try {
			if (appt == null) {
				throw new InvalidInputException("Input cannot be null!");
			} else {
				if (appt.getAnr() == null || appt.getAnr() <= 0) {
					throw new InvalidInputException("ANR cannot be null or less than 1");
				} else {
					boolean rBool = DAOHandler.getInstance().getAppointmentDAO().updateAppt(appt);
					if (rBool)
						return rBool;
					else
						throw new InvalidInputException("Appointment cannot be updated");
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}
	
	@Override
	public void updateAll(Date now, Date limit) throws InvalidInputException {
		try {
			DAOHandler.getInstance().getAppointmentDAO().updateAll(now, limit);
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public Appointment deleteAppointment(Appointment appt) throws InvalidInputException {
		try {
			if (appt == null) {
				throw new InvalidInputException("Input cannot be null!");
			} else {
				if (appt.getAnr() == null || appt.getAnr() <= 0) {
					throw new InvalidInputException("ANR cannot be null or less than 1");
				} else {
					Appointment rAppt = DAOHandler.getInstance().getAppointmentDAO().deleteAppt(appt);
					if (rAppt != null)
						return rAppt;
					else
						throw new InvalidInputException("Appointment cannot be deleted");
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

}
