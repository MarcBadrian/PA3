/*
 * COMP 6302 - Web Services / Internet
 * PA2: Hotel Reservation System - Web Interface
 * Marc Badrian and Hien Vo - Due 4/7/16
 * 
 */

package REST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import REST.TheatreMashup;



public class MysqlConnector {

	private static String dbName = "Theatre_Mashup";
	private static String dbUser = "root";
	private static String dbPassword = "1234";
    int new_customer_id = 0;

	public MysqlConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//System.out.println("JDBC driver registered");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public MysqlConnector(String db, String user, String pass) {
		try {
			// The newInstance() call is a work around for some
			// broken Java implementations
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbName = db;
			dbUser = user;
			dbPassword = pass;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String getUser() {
		return dbUser;
	}
	
	public String getPassword() {
		return dbPassword;
	}
	
	public Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?" + 
					"user=" + dbUser + "&password=" + dbPassword + "&useSSL=false");
			//System.out.println("Got Mysql database connection");
			return conn;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}

	public void createDB(Connection conn, String dbname) {
		try {
			PreparedStatement createDB = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS " + dbname);
			createDB.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database creation failed");
			e.printStackTrace();
		}
	}

	public void createReviewsTable(Connection conn) {

		PreparedStatement createTable;
			PreparedStatement createTable1;
			try {
				createTable = conn.prepareStatement("USE Theatre_Mashup; ");
				createTable.executeUpdate();
				createTable1 = conn.prepareStatement( 
						"CREATE TABLE IF NOT EXISTS reviews ( review_id INT NOT NULL AUTO_INCREMENT, review_source VARCHAR(25) NOT NULL, play_name VARCHAR(80) NOT NULL, title VARCHAR(100) NOT NULL, review_date VARCHAR(15) NOT NULL, genre VARCHAR(40), critique TEXT, PRIMARY KEY(review_id))");

				createTable1.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}


	public boolean insertReview(TheatreMashup review) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("INSERT INTO Theatre_Mashup.reviews (review_source, play_name, title, review_date, genre, critique) VALUES (?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, review.getReviewSource());
			stmt.setString(2, review.getPlayName());
			stmt.setString(3, review.getTitle());
			stmt.setString(4, review.getReviewDate());
			stmt.setString(5, review.getGenre());
			stmt.setString(6, review.getCritique());
			System.out.println(stmt);
			boolean success = stmt.executeUpdate() > 0;
			return success;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return false;
	};

	
	public boolean isEmpty() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT count(*) FROM Theatre_Mashup.reviews");
			rs = stmt.executeQuery();
			rs.next();
			int count = rs.getInt("count");
			if(count == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return false;
	}
	
	
	public boolean reserveRoom(int id, int room_number) {
		// Get the connection to the database
		Connection conn = getConnection();
		PreparedStatement stmt = null;
			// Execute the query
			try {
				stmt = conn.prepareStatement("UPDATE Hotel_Reservation_System.rooms SET current_occupant = ? WHERE room_number = ?");
				stmt.setInt(1, id);
				stmt.setInt(2, room_number);
				stmt.executeUpdate();
				boolean reserved = stmt.executeUpdate() > 0;
				return reserved;
			} catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			} finally {
				// it is a good idea to release
				// resources in a finally{} block
				// in reverse-order of their creation
				// if they are no-longer needed

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					} // ignore

					stmt = null;
				}
				if(conn != null){
					try {
						conn.close();
					} catch (SQLException sqlEx) {
					} // ignore

					conn = null;
				}

			}
		return false;
	};
	
	public boolean createPayment(int customer_id, int room_number, float amount, String cc_number, String exp_date) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("INSERT INTO Hotel_Reservation_System.transactions (customer_id, room_number, amount, cc_number, expiration_date) VALUES (?,?,?,?,?)");
			stmt.setInt(1, customer_id);
			stmt.setInt(2, room_number);
			stmt.setFloat(3, amount);
			stmt.setString(4, cc_number);
			stmt.setString(5, exp_date);
			System.out.println(stmt);
			boolean success = stmt.executeUpdate() > 0;
			return success;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return false;
	};

	
	public int getTransactionId(int customer_id, int room_number) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT transaction_id FROM Hotel_Reservation_System.transactions WHERE customer_id = ? AND room_number = ? ORDER BY transaction_id DESC");
			stmt.setInt(1, customer_id);
			stmt.setInt(2, room_number);
			rs = stmt.executeQuery();
			rs.next();
			int transaction_id = rs.getInt("transaction_id");
			return transaction_id;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return 0;
	}

	public String getCustomer(int id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT * FROM Hotel_Reservation_System.customers WHERE customer_id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			rs.next();
			String customer_id = Integer.toString(rs.getInt("customer_id"));
			String first_name = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			String phone_number = rs.getString("phone_number");
			String billing_address = rs.getString("billing_address");
			String billing_city = rs.getString("billing_city");
			String billing_state = rs.getString("billing_state");
			String billing_zip = rs.getString("billing_zip");
			String checkin_date = rs.getString("checkin_date");
			String checkout_date = rs.getString("checkout_date");
			String custInfo =  "<br>Customer Information: " + "<br>" + "<br>" +
					"Id: " + customer_id + "<br>" +
					"Name: " + first_name + " " + last_name + "<br>" +
					"Phone Number: " + phone_number + "<br>" +
					"Billing Address: " + billing_address + "<br>" + "&emsp;&emsp;" + "&emsp;&emsp;" + billing_city + ", " + billing_state + "  " + billing_zip + "<br>" +
					"Check-In Date: " + checkin_date + "<br>" +
					"Check-Out Date: " + checkout_date + "<br>";
			return "<p align=\"left\">"+ "<font size = 6>" + custInfo + "</font>" + "</p>";
			//return custInfo;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return "Error";
	}
	
	public String getCustomersByName(String f_name, String l_name) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;	
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT * FROM Hotel_Reservation_System.customers WHERE first_name = ? OR last_name = ?");
			stmt.setString(1, f_name);
			stmt.setString(2, l_name);
			rs = stmt.executeQuery();
			String allMatches = "Last Name:		First Name:		Id:		Phone Number: " + "<br>" + "<br>";
			while (rs.next()) {
			String customer_id = Integer.toString(rs.getInt("customer_id"));
			String first_name = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			String phone_number = rs.getString("phone_number");
			String custInfo = last_name + "&emsp;&emsp;" + "&emsp;&emsp;" + "&emsp;&emsp;" + first_name + "&emsp;&emsp;" + "&emsp;&emsp;" + "&emsp;&emsp;" + customer_id + "&emsp;&emsp;" + "&emsp;&emsp;" + phone_number + "<br>";
			allMatches += custInfo;
			}
			return allMatches;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return "Error";
	}
	
	public String getPlays() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;	
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT play_name FROM Theatre_Mashup.reviews");
			rs = stmt.executeQuery();
			String allMatches = "";
			while (rs.next()) {
			String play_name = rs.getString("play_name");
			String names = play_name + "|";
			allMatches += names;
			}
			return allMatches;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return "Error";
	}
	
	
	public String getGenres() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;	
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT genre FROM Theatre_Mashup.reviews");
			rs = stmt.executeQuery();
			String allMatches = "";
			while (rs.next()) {
			String genre = rs.getString("genre");
			String names = genre + "|";
			allMatches += names;
			}
			return allMatches;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return "Error";
	}
	
	
	public String getDates() {
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Connection con = null;	
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT DISTINCT review_date FROM Theatre_Mashup.reviews");
			rs = stmt.executeQuery();
			String allMatches = "";
			while (rs.next()) {
				String date = rs.getString("review_date");
				String names = date + "|";
				allMatches += names;
				// Execute the second query
				stmt2 = con.prepareStatement("SELECT DISTINCT title FROM Theatre_Mashup.reviews WHERE review_date = ?");
				stmt2.setString(1, date);
				rs2 = stmt2.executeQuery();
				while (rs2.next()) {
					String title = rs2.getString("title");
					String titles = title + "|";
					allMatches += titles;
				}
			}
			return allMatches;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return "Error";
	}
	
	
	public String getSource() {
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Connection con = null;	
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT DISTINCT review_source FROM Theatre_Mashup.reviews");
			rs = stmt.executeQuery();
			String allMatches = "";
			while (rs.next()) {
				String source = rs.getString("review_source");
				String names = source + "|";
				allMatches += names;
				// Execute the second query
				stmt2 = con.prepareStatement("SELECT * FROM Theatre_Mashup.reviews WHERE review_source = ?");
				stmt2.setString(1, source);
				rs2 = stmt2.executeQuery();
				while (rs2.next()) {
					String name = rs2.getString("play_name");
					String title = rs2.getString("title");
					String date = rs2.getString("review_date");
					String genre = rs2.getString("genre");
					String critique = rs2.getString("critique");
					allMatches += "<h2>" + name + "</h2> <br> <h2>" + title + "</h2> <br> <h3>Review Date: " + date + "</h3> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
				}
			}
			return allMatches;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return "Error";
	}
	
	
	public String getName(String name) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;	
		try {
			// Get the connection to the database
			con = getConnection();
			// Execute the query
			stmt = con.prepareStatement("SELECT * FROM Theatre_Mashup.reviews WHERE name = ?");
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			String allMatches = "";
			while (rs.next()) {
				String source = rs.getString("review_source");
				String title = rs.getString("title");
				String date = rs.getString("review_date");
				String genre = rs.getString("genre");
				String critique = rs.getString("critique");
			allMatches += "<h1>" + title + "</h1> <br> <h2>Publication:" + source + "&emsp&emsp&emsp&emsp&emsp&emsp Review Date: " + date + "</h2> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
			}
			return allMatches;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException sqlEx) {
				} // ignore
				con = null;
			}
		}
		return "Error";
	}
	
	
}
