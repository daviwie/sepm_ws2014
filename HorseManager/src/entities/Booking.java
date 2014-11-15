package entities;

import java.sql.Date;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author David Wietstruk 0706376
 *
 */
public class Booking {
	// private Integer bnr;
	// private Date dayOfBooking;
	// private String customer;
	// private String fileName;
	private ArrayList<Appointment> aList = new ArrayList<Appointment>();

	// JFX properties
	private final IntegerProperty bnr;
	private final ObjectProperty<Date> dayOfBooking;
	private final StringProperty customer;
	private final StringProperty fileName;

	public Booking() {
		this.bnr = new SimpleIntegerProperty();
		this.dayOfBooking = new SimpleObjectProperty<Date>();
		this.customer = new SimpleStringProperty();
		this.fileName = new SimpleStringProperty();
	}

	/**
	 * @param dayOfBooking
	 * @param customer
	 */
	public Booking(Date dayOfBooking, String customer) {
		this.bnr = new SimpleIntegerProperty();
		this.dayOfBooking = new SimpleObjectProperty<Date>(dayOfBooking);
		this.customer = new SimpleStringProperty(customer);
		this.fileName = new SimpleStringProperty();
	}

	/**
	 * @param bnr
	 * @param dayOfBooking
	 * @param customer
	 */
	public Booking(Integer bnr, Date dayOfBooking, String customer) {
		this.bnr = new SimpleIntegerProperty(bnr);
		this.dayOfBooking = new SimpleObjectProperty<Date>(dayOfBooking);
		this.customer = new SimpleStringProperty(customer);
		this.fileName = new SimpleStringProperty();
	}

	/**
	 * @return the BNR of a booking
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
	 * @return the day on which a booking was entered into the system
	 */
	public Date getDayOfBooking() {
		return dayOfBooking.get();
	}

	/**
	 * @param dayOfBooking
	 */
	public void setDayOfBooking(Date dayOfBooking) {
		this.dayOfBooking.set(dayOfBooking);
	}

	/**
	 * @return the customer that made the booking
	 */
	public String getCustomer() {
		return customer.get();
	}

	/**
	 * @param customer
	 */
	public void setCustomer(String customer) {
		this.customer.set(customer);
	}

	/**
	 * @return all the Appointments associated with this booking
	 */
	public ArrayList<Appointment> getaList() {
		return aList;
	}

	/**
	 * @param aList
	 */
	public void setaList(ArrayList<Appointment> aList) {
		this.aList = aList;
	}

	/**
	 * 
	 * @return fileName The filename used to generate a booking confirmation.
	 */
	public String getFileName() {
		if (fileName.get().length() == 0)
			setFileName(getBnr() + "_" + getDayOfBooking().toString() + "_" + getCustomer());

		return fileName.get();
	}

	/**
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName.set(fileName);
	}

	/**
	 * This representation of a Booking object is used for booking confirmations
	 * on a by customer basis.
	 * 
	 * @return a formatted String representation of a Booking object
	 */
	@Override
	public String toString() {
		String out = "\n----------------------------\n" + "Booking #: " + getBnr().intValue() + "\n" + "Day of Booking: "
				+ getDayOfBooking().toString() + "\n" + "Customer: " + getCustomer() + "\n" + "----------------------------\n\n"
				+ ">>>>>>> Appointments <<<<<<<<\n\n";

		for (int i = 0; i < getaList().size(); i++) {
			out += getaList().get(i).toString();
		}

		return out;
	}

	public IntegerProperty getBnrProperty() {
		return bnr;
	}
	
	public ObjectProperty<Date> getDayOfBookingProperty() {
		return dayOfBooking;
	}
	
	public StringProperty getCustomerProperty(){
		return customer;
	}
	
	public StringProperty getFileNameProperty() {
		return fileName;
	}

}