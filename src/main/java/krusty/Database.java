package krusty;

import static krusty.Jsonizer.toJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
	
	private static final int cookiesPerPallet = (15*10*36)/100; //54
	
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
			PreparedStatement preparedStatement = connection.prepareStatement("select * from Recipes");
			recepies = toJson(preparedStatement.executeQuery(), "recepies");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recepies;
	}

	public String getPallets(Request req, Response res) {

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select pallet_id as id, cookie_name as cookie, prod_date as production_date, IF (blocked = 1, 'yes', 'no') as blocked\n" +
					"from Pallets\n");

			int counter = 0;
			if (req.queryParams("from") != null || req.queryParams("to") != null || req.queryParams("cookie") != null || req.queryParams("blocked") != null) {
				sb.append(" where ");
			}
			if (req.queryParams("from") != null) {
				sb.append(" prod_date >= '").append(req.queryParams("from")).append("' ");
				counter++;
			}
			if (req.queryParams("to") != null) {
				if (counter != 0) {
					sb.append(" and ");
				}
				sb.append(" prod_date <= '").append(req.queryParams("to")).append("' ");
				counter++;
			}
			if (req.queryParams("cookie") != null) {
				if (counter != 0) {
					sb.append(" and ");
				}
				sb.append(" cookie_name= '").append(req.queryParams("cookie")).append("' ");
				counter++;
			}
			if (req.queryParams("blocked") != null) {
				if (counter != 0) {
					sb.append(" and ");
				}
				if (req.queryParams("blocked").equals("yes")) {
					sb.append(" blocked = 1 ");
				} else {
					sb.append(" blocked = 0 ");
				}
			}
			sb.append(" order by  prod_date desc \n");

			PreparedStatement stmt = connection.prepareStatement(sb.toString());
			ResultSet rs = stmt.executeQuery();
			return Jsonizer.toJson(rs, "pallets");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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
		 * 1) Se om cookie finns i databasen (bara för att vara säker) #
		 * 2) Hämta recept (dvs vilka ingredieser och mängden) #
		 * 3) Kolla så att råvarorna finns tillgängliga #
		 * 4) Subtrahera råvaror #
		 * 5) Skapa pallet i tabellen pallet
		 */
		String cookieName = req.queryParams("cookie");
		if(cookieName.length()==0) return "{}";
		
		if (!cookieExists(cookieName))
			return "{}";

		//Hämtar recept
		Map<String, Integer> recepie = getRecipe(cookieName);


		//Hämtar vad som finns på lager
		String[] materialArray = new String[recepie.keySet().size()];
		recepie.keySet().toArray(materialArray);
		Map<String, Integer> materials = getMaterials(materialArray);
		
		//Checking
		for(String materialRequired:recepie.keySet()) {
			// Finns ingrediensen överhuvudtaget
			if(!materials.containsKey(materialRequired)) {
				return "{}";
			}

			// Finns det tillräckligt med ingrediensen på lager
			if(materials.get(materialRequired)<recepie.get(materialRequired)* cookiesPerPallet) {
				return "{}";
			}
		}
		
		for(String materialRequired:recepie.keySet()) {
			try {
				PreparedStatement ps = connection.prepareStatement("UPDATE RawMaterials SET amount = amount - ? WHERE name = ?");
				ps.setInt(1, recepie.get(materialRequired)* cookiesPerPallet);
				ps.setString(2, materialRequired);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			String sql = "INSERT INTO Pallets(prod_date, blocked, location, delivered_date, cookie_name) VALUES (?,?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1,System.currentTimeMillis());
			ps.setBoolean(2, false);
			ps.setString(3, "Krusty");
			ps.setLong(4,System.currentTimeMillis() + 1000*60*60*24*(10)); //Tio dagar
			ps.setString(5, cookieName);
			ps.execute();

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

	private Map<String, Integer> getRecipe(String cookieName) {
		Map<String, Integer> repecie = new HashMap<String, Integer>(); // Ingrediensnamnet och mängden
		ResultSet result;
		try {
			PreparedStatement ps = connection.prepareStatement("select * from Recipes where cookie_name=?");
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
			StringBuilder sb = new StringBuilder();
			sb.append("select * from RawMaterials where name in (");
			for(int i = 0;i<materialNames.length;i++){
				sb.append("?");
				if(i<materialNames.length-1) sb.append(",");
			}
			sb.append(")");

			PreparedStatement ps = connection.prepareStatement(sb.toString());

			for(int i = 0;i<materialNames.length;i++){
				ps.setString(i+1, materialNames[i]);
			}
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
