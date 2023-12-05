import java.sql.*;
public class SQLInjection {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "1234";

            conn = DriverManager.getConnection(url, user, password);

            String inputUsername = "shishir"; // User input, for example
            String inputPassword = "' OR '1'='1" ; // Malicious input
            String query = "SELECT * FROM user WHERE username = '" + inputUsername +
                    "' AND password = '" + inputPassword + "'";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println("Logged in User: " + rs.getString("username"));
            } else {
                System.out.println("Login failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
