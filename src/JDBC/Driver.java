package JDBC;

import java.sql.*;
import javax.sql.*;

public class Driver {

	public static void main(String[] args) throws SQLException {

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		String url = "jdbc:sqlserver://localhost:11000;databaseName=windows-sql17;instance=SQLSERVER;encrypt=true;TrustServerCertificate=true";
		String user = "user=<sa>";
		String password = "password=<PH@123456789>";
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			myConn = DriverManager.getConnection(url, user , password);
			
			myStmt = myConn.createStatement();
			
			myRs = myStmt.executeQuery("SELECT *\r\n" + 
					"FROM [HumanResources].[Employee]");
			
			while (myRs.next()) {
				System.out.println(myRs.getString("EmployeeFirstName") + ", " + myRs.getString("EmployeeLastName"));
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();
			}
		}
	}

}