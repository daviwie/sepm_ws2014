/**
 * 
 */
package persistence;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import util.ScriptRunner;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings("unused")
public class H2Handler {
	private static H2Handler instance;
	private Connection con = null;
	private ScriptRunner sr;
	private Reader createReader;
	private Reader insertReader;
	private Reader dropReader;

	private static final Logger logger = LogManager.getLogger("H2Handler");

	private H2Handler() {

	}
	
	public static H2Handler getInstance() {
		if(instance == null)
			instance = new H2Handler();
		
		return instance;
	}

	/**
	 * Gets the static instance of Connection to facilitate communication with
	 * the H2 database.
	 * 
	 * @return a static instance of Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		if (con == null) {
			try {
				Class.forName("org.h2.Driver");
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}

			/*
			 * Two different connections to make it easier to switch between
			 * embedded and tcp modes for the database. Only used for testing!
			 */
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/horseTestDatabase", "SA", "");
			/*con = DriverManager.getConnection("jdbc:h2:~/horseTestDatabase", "SA", "");*/
		}

		return con;
	}

	/**
	 * Once the application is closed, the connection to the database must be
	 * severed.
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		if (con != null)
			con.close();
	}

	/**
	 * Builds a database with test data. 
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	private void buildDB() throws SQLException, IOException {
		logger.info("Building database");
		sr = new ScriptRunner(H2Handler.getInstance().getConnection(), true, true);
		createReader = new FileReader("..\\HorseManager\\src\\test\\sql\\create.sql");
		// insertReader = new
		// FileReader("..\\HorseManager\\src\\test\\sql\\insert.sql");
		sr.runScript(createReader);
		// sr.runScript(insertReader);
	}
}
