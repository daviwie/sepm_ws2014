package util;

import entities.Appointment;
import entities.Booking;
import entities.Horse;

/**
 * Originally used to validate entities but was later no longer used in the
 * final implementation. Left behind for posterity.
 * 
 * @author David Wietstruk 0706376
 *
 */
public class EntityValidator {
	private static EntityValidator instance;

	private EntityValidator() {

	}

	public static EntityValidator getInstance() {
		if (instance == null)
			return new EntityValidator();
		else
			return instance;
	}

	public boolean isValidAppt(Appointment appt) {
		if (appt == null)
			return false;
		else {
			if (appt.getAnr() == null || appt.getAnr() <= 0)
				return false;
			else
				return true;
		}
	}

	public boolean isValidAppt(Integer anr) {
		if (anr == null || anr <= 0)
			return false;
		else
			return true;
	}

	public boolean isValidBooking(Booking booking) {
		if (booking == null)
			return false;
		else {
			if (booking.getBnr() == null || booking.getBnr() <= 0)
				return false;
			else
				return true;
		}
	}

	public boolean isValidBooking(Integer bnr) {
		if (bnr == null || bnr <= 0)
			return false;
		else
			return true;
	}

	public boolean isValidHorse(Horse horse) {
		if (horse == null)
			return false;
		else {
			if (horse.getName() == null || horse.getName().isEmpty())
				return false;
			else
				return true;
		}
	}

	public boolean isValidHorse(String hname) {
		if (hname == null || hname.isEmpty())
			return false;
		else
			return true;
	}
}
