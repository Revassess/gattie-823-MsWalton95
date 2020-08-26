package com.revature.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.revature.tier2.model.User;
import com.revature.tier2.model.UserProblem4;
import com.revature.tier2.model.UserStudySet;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TestConfiguration {
    private static SessionFactory sesfact;
    static String engine;

    private static SessionFactory buildFactory() throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(new File("src/sql/setup.properties")));
        return new Configuration().addProperties(props)
                .setProperty("hibernate.connection.driver_class", findDriver(props))
                .setProperty("hibernate.connection.pool_size", "1")
                .setProperty("hibernate.connection.isolation", String.valueOf(Connection.TRANSACTION_SERIALIZABLE))
                .setProperty("hibernate.hbm2ddl.auto", "none").setProperty("hibernate.show_sql", "true")
                .addAnnotatedClass(UserStudySet.class).addAnnotatedClass(UserProblem4.class)
                .addAnnotatedClass(User.class).buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (sesfact == null) {
            try {
                sesfact = buildFactory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sesfact;
    }

    private static String findDriver(Properties props) {
        engine = props.getProperty("database.engine");
        switch (engine) {
            case "oraclesql":
                return "oracle.jdbc.OracleDriver";
            case "postgresql":
                return "org.postgresql.Driver";
            case "mysql":
                return "com.mysql.jdbc.Driver";
            default:
                return "";
        }
    }

    public static String getEngine() {
        return engine;
    }

    public static String getFileContents(String filename) throws IOException {
        File answer = new File("src/sql/" + filename + ".sql");
        String contents = "";

        String line;
        BufferedReader br = new BufferedReader(new FileReader(answer));
        while ((line = br.readLine()) != null) {
            contents += line;
        }
        br.close();
        return contents;
    }
    
    private static final String USERNAME = "associate";
	private static final String PASSWORD = "password";
	private static final String URL = "jdbc:postgresql://postgresql-class.cks98gmxels6.us-west-1.rds.amazonaws.com:5432/revassess";
	private static final String DRIVER ="org.postgresql.Driver";
	
	public static Connection connect() throws SQLException {
			try {
				Class.forName(DRIVER);
				
			} catch (ClassNotFoundException e) {
				System.out.println("Unable to find driver");
			}
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	public static void main(String[] args) {
		
		//Try with resources block. The try statement will stake a method that creates a resource, that will
		//automatically be closed at the end of the try or catch block. It avoids the need for a finally block.
		try(Connection conn = TestConfiguration.connect()){
			System.out.println("connection successful");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		TestConfiguration tc = new TestConfiguration();
		
	}
	User u = new User();
	
	public List<User> users(){
		try(Connection conn = TestConfiguration.connect()){
			String sql ="SELECT * FROM app_user WHERE role_id=3;";
			Statement stmt = conn.createStatement();
			
			List<User> user = new ArrayList<User>();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				u.setId(rs.getInt("user_id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setUserRole(rs.getInt("role_id"));
				user.add(u);
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean addUser(User u){
		try(Connection conn = TestConfiguration.connect()){
			return false;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}