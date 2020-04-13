package krusty;

import static krusty.Jsonizer.toJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;

public class Database {
	/**
	 * Modify it to fit your environment and then use this string when connecting to
	 * your database!
	 */
	private static final String jdbcString = "jdbc:mysql://puccini.cs.lth.se/hbg05";

	// For use with MySQL or PostgreSQL
	private static final String jdbcUsername = "hbg05";
	private static final String jdbcPassword = "fpz731gw";

	private static Connection connection;
	
	private static final int palletSize = 30;
	
	public void connect() {
		try {
			connection = DriverManager.getConnection(jdbcString, jdbcUsername, jdbcPassword);

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	// TODO: Implement and change output in all methods below!
	public String getCustomers(Request req, Response res) {
		String customers = "{}";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from Customers");
			customers = toJson(preparedStatement.executeQuery(), "customers");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(customers);
		return customers;
	}

	public String getRawMaterials(Request req, Response res) {
		String rawMaterials = "{}";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select name, amount, unit  from RawMaterials");
			rawMaterials = toJson(preparedStatement.executeQuery(), "raw-materials");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rawMaterials;
	}

	public String getCookies(Request req, Response res) {
		String cookies = "{}";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from Cookies");
			cookies = toJson(preparedStatement.executeQuery(), "cookies");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cookies;
	}

	public String getRecipes(Request req, Response res) {
		String recepies = "{}";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from Recepies");
			recepies = toJson(preparedStatement.executeQuery(), "recepies");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recepies;
	}

	public String getPallets(Request req, Response res) {
		return "{\"pallets\":[]}";
	}

	public String reset(Request req, Response res) {
		try {
			Statement statement = connection.createStatement();

			InputStream inputStream = getClass().getResource("/reset.sql").openStream();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream, StandardCharsets.UTF_8));

			bufferedReader.lines().filter(line -> !line.trim().startsWith("--") || !line.trim().isEmpty())
					.forEach(line -> {
						try {
							statement.addBatch(line.trim());
						} catch (SQLException e) {
							System.out.println("Line: " + line);
							e.printStackTrace();
						}
					});
			statement.executeBatch();
			statement.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not open reset file.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "{}";
	}

	public String createPallet(Request req, Response res) {
		/*
		 * Se om cookie finns i databasen (bara för att vara säker) #
		 * Hämta recept (dvs vilka ingredieser och mängden) #
		 * Kolla så att råvarorna finns tillgängliga #
		 * Subtrahera råvaror #
		 * Skapa pallet i tabellen pallet 
		 * 
		 */
		String cookieName = req.params("cookie");
		if(cookieName.length()>0) return "{}";
		
		if (!cookieExists(cookieName))
			return "{}";
		
		Map<String, Integer> recepie = getRecepie(cookieName);
		Map<String, Integer> materials = getMaterials((String[]) recepie.keySet().toArray());
		
		//Checking
		for(String materialRequired:recepie.keySet()) {
			if(!materials.containsKey(materialRequired)) {
				return "{}";
			}
			if(materials.get(materialRequired)<recepie.get(materialRequired)*palletSize) {
				return "{}";
			}
		}
		
		for(String materialRequired:recepie.keySet()) {
			try {
				PreparedStatement ps = connection.prepareStatement("UPDATE RawMaterials SET amount = amount - ? WHERE name = ?");
				ps.setInt(1, recepie.get(materialRequired)*palletSize);
				ps.setString(2, materialRequired);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Pallets(prod_date, blocked, location, delivered_date, order_id, cookie_name) VALUES (?,?,?,?,?,?,?)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		return "{}";
	}

	private boolean cookieExists(String cookieName) {
		ResultSet result;
		try {
			PreparedStatement ps = connection.prepareStatement("select * from Cookies where name=?");
			ps.setString(1, cookieName);
			result = ps.executeQuery();
			int size = 0;
			if (result != null) {
				result.last(); // moves cursor to the last row
				size = result.getRow(); // get row id
			}
			return size > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private Map<String, Integer> getRecepie(String cookieName) {
		Map<String, Integer> repecie = new HashMap<String, Integer>(); // Ingrediensnamnet och mängden
		ResultSet result;
		try {
			PreparedStatement ps = connection.prepareStatement("select * from Recepies where cookie_name=?");
			ps.setString(1, cookieName);
			result = ps.executeQuery();
			while (result.next()) {
				repecie.put(result.getString("raw_material"), result.getInt("amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return repecie;
	}
	
	private Map<String, Integer> getMaterials(String[] materialNames){
		Map<String, Integer> materials = new HashMap<String, Integer>(); // Materialnamnet och mängden
		ResultSet result;
		try {
			PreparedStatement ps = connection.prepareStatement("select * from RawMaterials where name in (?)");
			Array array = connection.createArrayOf("VARCHAR", materialNames);
			ps.setArray(1, array);
			result = ps.executeQuery();
			while (result.next()) {
				materials.put(result.getString("name"), result.getInt("amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return materials;
	}
	
	
}
