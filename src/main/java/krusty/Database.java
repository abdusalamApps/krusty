package krusty;

import spark.Request;
import spark.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import static krusty.Jsonizer.toJson;

public class Database {
	/**
	 * Modify it to fit your environment and then use this string when connecting to your database!
	 */
	private static final String jdbcString = "jdbc:mysql://35.228.254.209/krusty";

	// For use with MySQL or PostgreSQL
	private static final String jdbcUsername = "abdo";
	private static final String jdbcPassword = "";

	private static Connection connection;

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
			PreparedStatement preparedStatement =
					connection.prepareStatement("select * from Customers");
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
			PreparedStatement preparedStatement =
					connection.prepareStatement("select raw_material_name, amount, unit  from RawMaterials");
			rawMaterials = toJson(preparedStatement.executeQuery(), "raw-materials");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(rawMaterials);
		return rawMaterials;
	}

	public static void main(String[] args) {
		try {
			connection = DriverManager.getConnection(jdbcString, jdbcUsername, jdbcPassword);

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		String rawMaterials = "{}";
		try {
			PreparedStatement preparedStatement =
					connection.prepareStatement("select raw_material_name, amount, unit  from RawMaterials");
			rawMaterials = toJson(preparedStatement.executeQuery(), "raw-materials");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(rawMaterials);
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
		return "{}";
	}

	public String getPallets(Request req, Response res) {
		return "{\"pallets\":[]}";
	}

	public String reset(Request req, Response res) {
		try {
			Statement statement = connection.createStatement();

			InputStream inputStream = getClass().getResource("/reset.sql").openStream();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

			bufferedReader.lines()
					.filter(line -> !line.trim().startsWith("--") || !line.trim().isEmpty())
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
		return "{}";
	}
}
