
/* /****************************************************************************
CSE532 -- Project 2
File name: DatabaseConnection.java
Author(s): Harshini Kanaparthi (SBU Id )
Meghana vemulapalli (SBU Id )
Brief description: This file loads the postgres Connection with the postgres JDBC driver and returns connection object
****************************************************************************/


package postgres;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DatabaseConnection  {
	protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException { 
        Connection connection = DriverManager.getConnection(
        		"jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
            return connection;
    }
}