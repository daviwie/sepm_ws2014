package service.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.impl.DAOHandler;
import entities.Horse;
import exception.InvalidInputException;
import exception.PersistenceException;
import service.IHorseService;

/**
 * @author David Wietstruk 0706376
 *
 */
public class HorseService implements IHorseService {

	private static final Logger logger = LogManager.getLogger("HorseService");

	@Override
	public Horse insertHorse(Horse horse) throws InvalidInputException {
		try {
			if (horse == null) {
				throw new InvalidInputException("Horse cannot be null!");
			} else {
				java.util.Date today = new java.util.Date();
				// logger.debug(today);

				if (horse.getBdate().before(today)) {
					Horse rHorse = DAOHandler.getInstance().getHorseDAO().createHorse(horse);
					if (rHorse == null)
						throw new InvalidInputException("This horse is already in the database, please review input and try again.");
					else
						return rHorse;
				} else
					throw new InvalidInputException("Horse's birthday cannot be in the future.");
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Horse> searchAllHorses() throws InvalidInputException {
		try {
			return DAOHandler.getInstance().getHorseDAO().searchAllHorses();
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public Horse searchByHorse(Horse horse) throws InvalidInputException {
		try {
			if (horse == null) {
				throw new InvalidInputException("Horse cannot be null!");
			} else {
				if (horse.getName() == null || horse.getName().isEmpty()) {
					throw new InvalidInputException("Horse name cannot be null");
				} else {
					Horse rHorse = DAOHandler.getInstance().getHorseDAO().searchByHorse(horse);
					if (rHorse == null)
						throw new InvalidInputException("This horse is not in the database, please review input.");
					else {
						return rHorse;
					}
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public Horse searchByHorse(String horse) throws InvalidInputException {
		try {
			if (horse == null || horse.isEmpty()) {
				throw new InvalidInputException("Horse cannot be null!");
			} else {
				Horse rHorse = DAOHandler.getInstance().getHorseDAO().searchByHorse(horse);
				if (rHorse == null)
					throw new InvalidInputException("This horse is not in the database, please review input.");
				else
					return rHorse;
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Horse> searchHorseByWeight(Integer lowerWeightLimit, Integer upperWeightLimit) throws InvalidInputException {
		try {
			if (lowerWeightLimit == null || upperWeightLimit == null) {
				throw new InvalidInputException("Inputs cannot be null!");
			} else {
				if (upperWeightLimit < lowerWeightLimit) {
					throw new InvalidInputException("Please check to make sure lower bound is not greater than upper bound");
				} else {
					if (lowerWeightLimit < 0 || upperWeightLimit < 0) {
						throw new InvalidInputException("Please use only positive integers for search criteria");
					} else
						return DAOHandler.getInstance().getHorseDAO().searchHorseByWeight(lowerWeightLimit, upperWeightLimit);
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public ArrayList<Horse> searchHorseByHeight(Integer lowerHeightLimit, Integer upperHeightLimit) throws InvalidInputException {
		try {
			if (lowerHeightLimit == null || upperHeightLimit == null) {
				throw new InvalidInputException("Inputs cannot be null!");
			} else {
				if (upperHeightLimit < lowerHeightLimit) {
					throw new InvalidInputException("Please check to make sure lower bound is not greater than upper bound");
				} else {
					if (lowerHeightLimit < 0 || upperHeightLimit < 0)
						throw new InvalidInputException("Please use only positive integers for search criteria");
					else
						return DAOHandler.getInstance().getHorseDAO().searchHorseByHeight(lowerHeightLimit, upperHeightLimit);
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public boolean isHorseAvailable(Date date, Time startTime, Time endTime, Horse horse) throws InvalidInputException {
		try {
			if (date == null || startTime == null || endTime == null || horse == null) {
				throw new InvalidInputException("Inputs cannot be null!");
			} else {
				java.util.Date today = new java.util.Date();
				// logger.debug(today);

				if (date.before(today))
					throw new InvalidInputException("Date of appointment cannot be before today!");
				else {
					if (endTime.before(startTime))
						throw new InvalidInputException("End time of appointment cannot be before startime!");
					else
						return DAOHandler.getInstance().getHorseDAO().isHorseAvailable(date, startTime, endTime, horse);
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public boolean isHorseAvailable(Date date, Time startTime, Time endTime, String hname) throws InvalidInputException {
		try {
			if (date == null || startTime == null || endTime == null || hname == null) {
				throw new InvalidInputException("Inputs cannot be null!");
			} else {
				if (hname.isEmpty())
					throw new InvalidInputException("Horse name cannot be empty!");
				else {
					java.util.Date today = new java.util.Date();
					// logger.debug(today);

					if (date.before(today))
						throw new InvalidInputException("Date of appointment cannot be before today!");
					else {
						if (endTime.before(startTime))
							throw new InvalidInputException("End time of appointment cannot be before startime!");
						else
							return DAOHandler.getInstance().getHorseDAO().isHorseAvailable(date, startTime, endTime, hname);
					}
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public boolean updateHorse(Horse horse) throws InvalidInputException {
		try {
			if (horse == null) {
				throw new InvalidInputException("Horse cannot be null!");
			} else {
				if (horse.getName() == null || horse.getName().isEmpty()) {
					throw new InvalidInputException("Horse name cannot be null or empty!");
				} else {
					boolean rBool = DAOHandler.getInstance().getHorseDAO().updateHorse(horse);
					if (rBool)
						return rBool;
					else
						throw new InvalidInputException("Horse either not in database or update not possible at this time");
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public Horse deleteHorse(Horse horse) throws InvalidInputException {
		try {
			if (horse == null) {
				throw new InvalidInputException("Horse cannot be null");
			} else {
				if (horse.getName() == null || horse.getName().isEmpty())
					throw new InvalidInputException("Horse name cannot be null");
				else {
					Horse rHorse = DAOHandler.getInstance().getHorseDAO().deleteHorse(horse);
					if (rHorse != null)
						return rHorse;
					else
						throw new InvalidInputException("Horse either not in database or delete not possible at this time");
				}
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

	@Override
	public Horse deleteHorse(String horse) throws InvalidInputException {
		try {
			if (horse == null || horse.isEmpty())
				throw new InvalidInputException("Horse name cannot be null");
			else {
				Horse rHorse = DAOHandler.getInstance().getHorseDAO().deleteHorse(horse);
				if (rHorse != null)
					return rHorse;
				else
					throw new InvalidInputException("Horse either not in database or delete not possible at this time");
			}
		} catch (PersistenceException e) {
			logger.warn(e.getMessage());
			throw new InvalidInputException(e.getError());
		}
	}

}
