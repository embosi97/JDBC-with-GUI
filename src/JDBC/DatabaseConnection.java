package JDBC;
/** 
 * JDBC class to connect to the database and execute queries as well as process the result set.
 */
import java.io.File;
import java.io.PrintWriter;
import java.sql.*;

public class DatabaseConnection {

	private static Connection conn;
	private static String url;
	private static String username;
	private static String password;
	private static String dbName;

	/**
	 * Constructs a DatabaseConnection object with the given url, database name,
	 * username and password to establish a connection
	 * 
	 * @param dbURL     the database url
	 * @param theDbName the database name
	 * @param user      database user
	 * @param pass      database password
	 */
	public DatabaseConnection(String dbURL, String theDbName, String user, String pass) {
		url = dbURL;
		dbName = theDbName;
		username = user;
		password = pass;
		url += ";" + "databaseName=" + dbName;
		url = "jdbc:sqlserver://localhost:"+url;
	}

	/**
	 * 
	 * @return A connection object with a successful connection
	 * @throws SQLException
	 */
	public Connection connect() throws SQLException {
		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url, username, password);
		if (conn != null)
			System.out.println("Successfully Connected to " + dbName);
		return conn;
	}

	// /**
	//  * Writes a result set from executing the sql query into an HTML file as a
	//  * table.
	//  * 
	//  * @param rs  the result set from the query execution
	//  * @param out the printwriter object that writes
	//  * @return the row count for the result set
	//  * @throws Exception SQLException
	//  */
	// public int dumpData(java.sql.ResultSet rs, java.io.PrintWriter out) throws Exception {
	// 	int rowCount = 0;
	// 	out.println("<head>");
	// 	out.println("<link rel=\"stylesheet\" href=\"demo.css\">");
	// 	out.println("</head>");
	// 	out.println("<P ALIGN='center'><TABLE BORDER=1>");
	// 	ResultSetMetaData rsmd = rs.getMetaData(); // Contains column count and header info.
	// 	int columnCount = rsmd.getColumnCount();
	// 	// table header
	// 	out.println("<TR>");
	// 	for (int i = 0; i < columnCount; i++) {
	// 		out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
	// 	}
	// 	out.println("</TR>");
	// 	// the data
	// 	while (rs.next()) {
	// 		rowCount++;
	// 		out.println("<TR>");
	// 		for (int i = 0; i < columnCount; i++) {
	// 			out.println("<TD>" + rs.getString(i + 1) + "</TD>");
	// 		}
	// 		out.println("</TR>");
	// 	}
	// 	out.println("</TABLE></P>");
	// 	return rowCount;
	// }

	/**
	 * Takes in the connection object and get the result set from the query using
	 * jdbc.
	 * 
	 * @param c        Connection object to connet to database
	 * @param query    the query to execute
	 * @param FileName Name of the HTML file to write to
	 * @throws Exception SQL or Write exception
	 */
	public java.sql.ResultSet execQueryToHTML(Connection c, String query, String FileName) throws Exception {
		java.sql.ResultSet resultSet = null;
		try {
			File file = new File(FileName);
			PrintWriter writer = new PrintWriter(file);
			c = connect();
			// Statement Object in order to execute our query.
			Statement stmt = conn.createStatement();
			ResultSet myRs = stmt.executeQuery(query);
			// Processing the result set from the query
			resultSet = myRs;
			// System.out.println("Rows processed: " + dumpData(myRs, writer));
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		// } finally {
		// 	// At the end, we close the connection to the database to not waste any
		// 	// resources
		// 	try {
		// 		if (conn != null && !conn.isClosed()) {
		// 			conn.close();

		// 		}
		// 	} catch (SQLException e) {
		// 		e.printStackTrace();
		// 	}
		}
		return resultSet;
	}

}