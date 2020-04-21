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
import java.text.SimpleDateFormat;
import java.util.*;

import spark.QueryParamsMap;
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

    private static final int cookiesPerPallet = (15 * 10 * 36) / 100; //54

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
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select name, amount, unit  from RawMaterials");
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
        Map<String, String> supportedFilters = new HashMap<String, String>();
        supportedFilters.put("from", "prod_date >=");
        supportedFilters.put("to", "prod_date <=");
        supportedFilters.put("cookie", "cookie_name =");
        supportedFilters.put("blocked", "blocked =");

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pallet_id AS id, cookie_name AS cookie, prod_date AS production_date, IF (blocked = 1, 'yes', 'no') AS blocked");
            sb.append(" FROM Pallets ");


            Map<String, String[]> params = req.queryMap().toMap();
            Iterator<String> paramIterator = params.keySet().iterator();
            if (supportedFilters.keySet().stream().anyMatch(params.keySet()::contains))
                sb.append("WHERE "); // if contains any
            while (paramIterator.hasNext()) {
                String key = paramIterator.next();
                if (!supportedFilters.containsKey(key)) continue;

                sb.append(supportedFilters.get(key)).append("? ");
                if (paramIterator.hasNext()) sb.append(" and ");

            }
            sb.append("ORDER BY prod_date DESC");

            PreparedStatement ps = connection.prepareStatement(sb.toString());

            paramIterator = params.keySet().iterator();
            int count = 1;
            while (paramIterator.hasNext()) {
                String key = paramIterator.next();
                if (!supportedFilters.containsKey(key)) continue;

                String value;
                if (key.equals("blocked")) {
                    ps.setBoolean(count++, params.get(key)[0].equals("yes"));
                    continue;
                }
                ps.setString(count++, params.get(key)[0]);

            }
            ResultSet rs = ps.executeQuery();
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
        if (cookieName.length() == 0) return "{}";

        if (!cookieExists(cookieName))
            return "{}";

        //Hämtar recept
        Map<String, Integer> recepie = getRecipe(cookieName);


        //Hämtar vad som finns på lager
        String[] materialArray = new String[recepie.keySet().size()];
        recepie.keySet().toArray(materialArray);
        Map<String, Integer> materials = getMaterials(materialArray);

        //Checking
        for (String materialRequired : recepie.keySet()) {
            // Finns ingrediensen överhuvudtaget
            if (!materials.containsKey(materialRequired)) {
                return "{}";
            }

            // Finns det tillräckligt med ingrediensen på lager
            if (materials.get(materialRequired) < recepie.get(materialRequired) * cookiesPerPallet) {
                return "{}";
            }
        }

        for (String materialRequired : recepie.keySet()) {
            try {
                PreparedStatement ps = connection.prepareStatement("UPDATE RawMaterials SET amount = amount - ? WHERE name = ?");
                ps.setInt(1, recepie.get(materialRequired) * cookiesPerPallet);
                ps.setString(2, materialRequired);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            String sql = "INSERT INTO Pallets(prod_date, blocked, location, cookie_name) VALUES (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String today = formatter.format(new Date());
            ps.setString(1, today);
            ps.setBoolean(2, false);
            ps.setString(3, "Krusty");
            ps.setString(4, cookieName);
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

    private Map<String, Integer> getMaterials(String[] materialNames) {
        Map<String, Integer> materials = new HashMap<String, Integer>(); // Materialnamnet och mängden
        ResultSet result;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from RawMaterials where name in (");
            for (int i = 0; i < materialNames.length; i++) {
                sb.append("?");
                if (i < materialNames.length - 1) sb.append(",");
            }
            sb.append(")");

            PreparedStatement ps = connection.prepareStatement(sb.toString());

            for (int i = 0; i < materialNames.length; i++) {
                ps.setString(i + 1, materialNames[i]);
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
