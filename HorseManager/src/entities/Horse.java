package entities;

import java.sql.Date;

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
public class Horse {
	// private String name;
	// private Integer weight;
	// private Integer height;
	// private String imgfilepath;
	// private Date bdate;
	// private boolean deleted;

	// JFX properties
	private final StringProperty name;
	private final IntegerProperty weight;
	private final IntegerProperty height;
	private final StringProperty imgFilePath;
	private final ObjectProperty<Date> bdate;
	private final BooleanProperty deleted;

	/**
	 * @param name
	 * @param weight
	 * @param height
	 * @param imgfilepath
	 * @param bdate
	 * @param deleted
	 */
	public Horse(String name, Integer weight, Integer height, String imgfilepath, Date bdate, boolean deleted) {
		this.name = new SimpleStringProperty(name);
		this.weight = new SimpleIntegerProperty(weight);
		this.height = new SimpleIntegerProperty(height);
		this.imgFilePath = new SimpleStringProperty(imgfilepath);
		this.bdate = new SimpleObjectProperty<Date>(bdate);
		this.deleted = new SimpleBooleanProperty(deleted);
	}

	public Horse() {
		this.name = new SimpleStringProperty();
		this.weight = new SimpleIntegerProperty();
		this.height = new SimpleIntegerProperty();
		this.imgFilePath = new SimpleStringProperty();
		this.bdate = new SimpleObjectProperty<Date>();
		this.deleted = new SimpleBooleanProperty();
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public Integer getWeight() {
		return weight.get();
	}

	public void setWeight(Integer weight) {
		this.weight.set(weight);
	}

	public Integer getHeight() {
		return height.get();
	}

	public void setHeight(Integer height) {
		this.height.set(height);
	}

	public String getImgfilepath() {
		return imgFilePath.get();
	}
	
	public void setImgFilePath(String imgFilePath) {
		this.imgFilePath.set(imgFilePath);
	}

	public Date getBdate() {
		return bdate.get();
	}
	
	public void setBdate(Date bDate) {
		this.bdate.set(bDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\n----------------------------\n" + "Horse: " + getName() + "\n" + "Weight: " + getWeight().intValue() + "\n" + "Height: "
				+ getHeight().intValue() + "\n" + "Birthday: " + getBdate() + "\n" + "----------------------------\n\n";
	}

	public BooleanProperty getDeletedProperty() {
		return deleted;
	}

	public StringProperty getNameProperty() {
		return name;
	}

	public IntegerProperty getWeightProperty() {
		return weight;
	}

	public IntegerProperty getHeightProperty() {
		return height;
	}

	public StringProperty getImgfilepathProperty() {
		return imgFilePath;
	}

	public ObjectProperty<Date> getBdateProperty() {
		return bdate;
	}

	public boolean getDeleted() {
		return deleted.get();
	}

	public void setDeleted(Boolean deleted) {
		this.deleted.setValue(deleted);
	}
}
