import java.sql.*;

public class DatabaseExample {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        try {
            // Connect to the MySQL database
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Insert a new user with their favorite books
            insertUser(conn, "JohnDoe", "john@example.com", "The Great Gatsby", "To Kill a Mockingbird");

            // Retrieve user's favorite books
            retrieveUserBooks(conn, "JohnDoe");

            // Close the connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertUser(Connection conn, String username, String email, String... favoriteBooks) throws SQLException {
        // Insert user into users table
        String insertUserSQL = "INSERT INTO users (username, email) VALUES (?, ?)";
        PreparedStatement insertUserStmt = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
        insertUserStmt.setString(1, username);
        insertUserStmt.setString(2, email);
        insertUserStmt.executeUpdate();

        // Get the auto-generated user ID
        ResultSet generatedKeys = insertUserStmt.getGeneratedKeys();
        int userId;
        if (generatedKeys.next()) {
            userId = generatedKeys.getInt(1);
        } else {
            throw new SQLException("Failed to insert user, no ID obtained.");
        }

        // Insert favorite books into user_books table
        String insertUserBooksSQL = "INSERT INTO user_books (user_id, book_id) VALUES (?, (SELECT book_id FROM books WHERE title = ?))";
        PreparedStatement insertUserBooksStmt = conn.prepareStatement(insertUserBooksSQL);
        for (String book : favoriteBooks) {
            insertUserBooksStmt.setInt(1, userId);
            insertUserBooksStmt.setString(2, book);
            insertUserBooksStmt.executeUpdate();
        }
    }

    private static void retrieveUserBooks(Connection conn, String username) throws SQLException {
        // Retrieve user's favorite books
        String retrieveUserBooksSQL = "SELECT b.title, b.author FROM user_books ub JOIN books b ON ub.book_id = b.book_id JOIN users u ON ub.user_id = u.user_id WHERE u.username = ?";
        PreparedStatement retrieveUserBooksStmt = conn.prepareStatement(retrieveUserBooksSQL);
        retrieveUserBooksStmt.setString(1, username);
        ResultSet resultSet = retrieveUserBooksStmt.executeQuery();

        // Print user's favorite books
        System.out.println("Favorite books of " + username + ":");
        while (resultSet.next()) {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            System.out.println("- " + title + " by " + author);
        }
    }
}