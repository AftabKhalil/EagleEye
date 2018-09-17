package eagleeye;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author aftab
 */
 public class SQLObject
{
     public Connection con = null;
     public Statement stmnt = null;
     public ResultSet rs = null;

     public SQLObject(String databaseName, String userName, String password) throws SQLException
    {        
         try
        {  
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             String connectionUrl = "jdbc:sqlserver://localhost:1433;"+"databaseName="+databaseName+";user="+userName+";password="+password+";";
             
             con = DriverManager.getConnection(connectionUrl);
        }
         catch (SQLException e)
        {
             System.out.println("SQL Exception: "+ e.toString());
        }
         catch (ClassNotFoundException cE)
        {
            System.out.println("Class Not Found Exception: "+ cE.toString());
        } 
         stmnt = con.createStatement();       
    }
}
