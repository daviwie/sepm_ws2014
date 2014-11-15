package entities;

import java.sql.Date;
import java.sql.Time;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author David Wietstruk 0706376
 *
 */
public class Appointment {
	// private Integer anr;
	// private Date aDate;
	// private Time startTime;
	// private Time endTime;
	// private boolean editable;
	// private boolean deleted;
	// private Integer bnr;
	// private String hname;
	
	private final IntegerProperty anr;
	private final ObjectProperty<Date> aDate;
	private final ObjectProperty<Time> startTime;
	private final ObjectProperty<Time> endTime;
	private final BooleanProperty editable;
	private final BooleanProperty deleted;
	private final IntegerProperty bnr;
	private final StringProperty hname;
	
	public Appointment() {
		this.anr = new SimpleIntegerProperty();
		this.aDate = new SimpleObjectProperty<Date>();
		this.startTime = new SimpleObjectProperty<Time>();
		this.endTime = new SimpleObjectProperty<Time>();
		this.editable = new SimpleBooleanProperty();
		this.deleted = new SimpleBooleanProperty();
		this.bnr = new SimpleIntegerProperty();
		this.hname = new SimpleStringProperty();
	}

	/**
	 * @param aDate
	 * @param starttime
	 * @param endTime
	 * @param editable
	 * @param deleted
	 * @param bnr
	 * @param hname
	 */
	public Appointment(Date aDate, Time startTime, Time endTime, boolean editable, boolean deleted, Integer bnr, String hname) {
		this.anr = new SimpleIntegerProperty();
		this.aDate = new SimpleObjectProperty<Date>(aDate);
		this.startTime = new SimpleObjectProperty<Time>(startTime);
		this.endTime = new SimpleObjectProperty<Time>(endTime);
		this.editable = new SimpleBooleanProperty(editable);
		this.deleted = new SimpleBooleanProperty(deleted);
		this.bnr = new SimpleIntegerProperty(bnr);
		this.hname = new SimpleStringProperty(hname);
	}

	/**
	 * @param anr
	 * @param aDate
	 * @param starttime
	 * @param endTime
	 * @param editable
	 * @param deleted
	 * @param bnr
	 * @param hname
	 */
	public Appointment(Integer anr, Date aDate, Time startTime, Time endTime, boolean editable, boolean deleted, Integer bnr, String hname) {
		this.anr = new SimpleIntegerProperty(anr);
		this.aDate = new SimpleObjectProperty<Date>(aDate);
		this.startTime = new SimpleObjectProperty<Time>(startTime);
		this.endTime = new SimpleObjectProperty<Time>(endTime);
		this.editable = new SimpleBooleanProperty(editable);
		this.deleted = new SimpleBooleanProperty(deleted);
		this.bnr = new SimpleIntegerProperty(bnr);
		this.hname = new SimpleStringProperty(hname);
	}

	/**
	 * @return the ANR of an Appointment
	 */
	public Integer getAnr() {
		return anr.get();
	}

	/**
	 * @param anr
	 */
	public void setAnr(Integer anr) {
		this.anr.set(anr);
	}

	/**
	 * @return the date on which an Appointment takes place
	 */
	public Date getaDate() {
		return aDate.get();
	}

	/**
	 * @param aDate
	 */
	public void setaDate(Date aDate) {
		this.aDate.set(aDate);
	}

	/**
	 * @return the time when an Appointment begins
	 */
	public Time getStartTime() {
		return startTime.get();
	}

	/**
	 * @param starttime
	 */
	public void setStartTime(Time startTime) {
		this.startTime.set(startTime);
	}

	/**
	 * @return the time when an Appointment ends
	 */
	public Time getEndTime() {
		return endTime.get();
	}

	/**
	 * @param endTime
	 */
	public void setEndTime(Time endTime) {
		this.endTime.set(endTime);
	}

	/**
	 * @return whether or not an Appointment can be edited
	 */
	public boolean isEditable() {
		return editable.get();
	}

	/**
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		this.editable.setValue(editable);
	}

	/**
	 * @return whether or not an Appointment is marked for deletion
	 */
	public boolean isDeleted() {
		return deleted.get();
	}

	/**
	 * @param deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted.set(deleted);
	}

	/**
	 * @return the BNR to which an Appointment belongs
	 */
	public Integer getBnr() {
		return bnr.get();
	}

	/**
	 * @param bnr
	 */
	public void setBnr(Integer bnr) {
		this.bnr.set(bnr);
	}

	/**
	 * @return the Horse reserved for a specific Appointment
	 */
	public String getHname() {
		return hname.get();
	}

	/**
	 * @param hname
	 */
	public void setHname(String hname) {
		this.hname.set(hname);
	}

	/**
	 * This representation of an Appointment object is used for booking
	 * confirmations on a by customer basis.
	 * 
	 * @return a formatted String representation of an Appointment object
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\n----------------------------\n" + 
				"Appointment #: " + getAnr().intValue() + "\n" +
				"Horse: " + getHname() + "\n" +
				"Date: " + getaDate().toString() + "\n" + 
				"Start: " + getStartTime().toString() + "\n" + 
				"End: " + getEndTime().toString() + "\n" + 
				"Editable: " + isEditable() + "\n" + 
				"Canceled: " + isDeleted() + "\n" + 
				"----------------------------\n\n";
	}

}
