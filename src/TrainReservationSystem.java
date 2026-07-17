

import java.sql.*;
import java.util.*;

public class TrainReservationSystem {

    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/trainreservation";
    static final String USER = "root";
    static final String PASS = "Varsh@02"; 
    static Scanner scanner = new Scanner(System.in);
    static String loggedInUserEmail = null;
    static boolean isAdmin = false;
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nTrain Ticket Reservation System");
            System.out.println("1. Register");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Admin");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    if (loginUser()) {
                        showUserMenu();
                    }
                    break;
                case 3:
                    if (loginAdmin()) {
                        showAdminMenu();
                    }
                    break;
                case 4:
                    System.exit(0);
            }
        }
    }

    static void registerUser() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();
            System.out.print("Enter ID Proof: ");
            String idProof = scanner.nextLine();
            System.out.print("Set Password: ");
            String password = scanner.nextLine();

            String sql = "INSERT INTO users (name, email, phone, address, id_proof, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setString(5, idProof);
            pstmt.setString(6, password);

            pstmt.executeUpdate();

            System.out.println("Registration successful!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static boolean loginUser() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                loggedInUserEmail = email;
                isAdmin = false;
                System.out.println("Login successful! Welcome " + rs.getString("name"));
                return true;
            } else {
                System.out.println("Invalid credentials.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    static boolean loginAdmin() {
        System.out.print("Enter Admin Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (email.equals("admin@trs.com") && password.equals("admin123")) {
            loggedInUserEmail = email;
            isAdmin = true;
            System.out.println("Admin login successful!");
            return true;
        } else {
            System.out.println("Invalid admin credentials.");
            return false;
        }
    }

static void showUserMenu() {
    while (true) {
        System.out.println("\nUser Menu");
        System.out.println("1. View Trains");
        System.out.println("2. Search Trains");
        System.out.println("3. Filter Trains");
        System.out.println("4. Book Ticket");
        System.out.println("5. View Profile");
        System.out.println("6. View Booking History");
       
        System.out.println("8. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewTrains();
                break;
            case 2:
                searchTrains();
                break;
            case 3:
                filterTrains();
                break;
            case 4:
                bookTicket();
                break;
            case 5:
                viewProfile();
                break;
            case 6:
                viewBookingHistory();
                break;
            case 7:
                updateProfile();
                break;
            case 8:
                loggedInUserEmail = null;
                return;
        }
    }
}

    static void showAdminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Train");
            System.out.println("2. View Trains");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTrain();
                    break;
                case 2:
                    viewTrains();
                    break;
                case 3:
                    loggedInUserEmail = null;
                    isAdmin = false;
                    return;
            }
        }
    }

    static void addTrain() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter Train Number: ");
            int trainNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Train Name: ");
            String trainName = scanner.nextLine();
            System.out.print("Enter Origin: ");
            String origin = scanner.nextLine();
            System.out.print("Enter Destination: ");
            String destination = scanner.nextLine();
            System.out.print("Enter Departure Time (YYYY-MM-DD HH:MM:SS): ");
            String departureTime = scanner.nextLine();
            System.out.print("Enter Available Seats: ");
            int seats = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Class (AC/Sleeper/General): ");
            String travelClass = scanner.nextLine();
            System.out.print("Enter Travel Type (Express/Superfast): ");
            String travelType = scanner.nextLine();

            String sql = "INSERT INTO trains (train_number, train_name, origin, destination, departure_time, available_seats, class, travel_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, trainNumber);
            pstmt.setString(2, trainName);
            pstmt.setString(3, origin);
            pstmt.setString(4, destination);
            pstmt.setString(5, departureTime);
            pstmt.setInt(6, seats);
            pstmt.setString(7, travelClass);
            pstmt.setString(8, travelType);
            pstmt.executeUpdate();

            System.out.println("Train added successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewTrains() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM trains";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(
                        "Train " + rs.getInt("train_number") + " - " + rs.getString("train_name") +
                                " (" + rs.getString("origin") + " to " + rs.getString("destination") + ") - Departure: " +
                                rs.getString("departure_time") + ", Seats: " + rs.getInt("available_seats") +
                                ", Class: " + rs.getString("class") + ", Type: " + rs.getString("travel_type")
                );

            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void searchTrains() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter Origin: ");
            String origin = scanner.nextLine();
            System.out.print("Enter Destination: ");
            String destination = scanner.nextLine();
            System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();
            System.out.print("Enter Time (HH:MM): ");
            String time = scanner.nextLine();

            String sql = "SELECT * FROM trains WHERE origin = ? AND destination = ? AND DATE(departure_time) = ? AND TIME(departure_time) >= ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, origin);
            pstmt.setString(2, destination);
            pstmt.setString(3, date);
            pstmt.setString(4, time);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.printf("Train %d - %s (%s to %s) - Departure: %s, Seats: %d, Class: %s, Type: %s\n",
                        rs.getInt("train_number"), rs.getString("train_name"),
                        rs.getString("origin"), rs.getString("destination"),
                        rs.getString("departure_time"), rs.getInt("available_seats"),
                        rs.getString("class"), rs.getString("travel_type"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void filterTrains() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Filter by Class (AC/Sleeper/General or leave blank): ");
            String travelClass = scanner.nextLine();
            System.out.print("Filter by Travel Type (Express/Superfast or leave blank): ");
            String travelType = scanner.nextLine();

            StringBuilder sql = new StringBuilder("SELECT * FROM trains WHERE class = ? AND travel_type = ?");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            pstmt.setString(1, travelClass);
            pstmt.setString(2, travelType);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.printf("Train %d - %s (%s to %s) - Departure: %s, Seats: %d, Class: %s, Type: %s\n",
                        rs.getInt("train_number"), rs.getString("train_name"),
                        rs.getString("origin"), rs.getString("destination"),
                        rs.getString("departure_time"), rs.getInt("available_seats"),
                        rs.getString("class"), rs.getString("travel_type"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void bookTicket() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter Train Number: ");
            int trainNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Travel Class (AC/Sleeper/General): ");
            String travelClass = scanner.nextLine();
            System.out.print("Enter Number of Adults: ");
            int adults = scanner.nextInt();
            System.out.print("Enter Number of Children: ");
            int children = scanner.nextInt();
            System.out.print("Enter Number of Senior Citizens: ");
            int seniors = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Seat Preference (Window/Aisle/Lower): ");
            String preference = scanner.nextLine();

            int totalSeats = adults + children + seniors;

            double totalCost = (adults * 500 + children * 250 + seniors * 300) * 1.05;

            // Step 1: Get user_id using email
            String userSql = "SELECT id FROM users WHERE email = ?";
            PreparedStatement userStmt = conn.prepareStatement(userSql);
            userStmt.setString(1, loggedInUserEmail);
            ResultSet userRs = userStmt.executeQuery();

            if (!userRs.next()) {
                System.out.println("Error: Logged-in user not found.");
                return;
            }
            int userId = userRs.getInt("id");

            // Step 2: Check seat availability
            String checkSql = "SELECT available_seats FROM trains WHERE train_number = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, trainNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("available_seats") >= totalSeats) {
                // Step 3: Update available seats
                String updateSql = "UPDATE trains SET available_seats = available_seats - ? WHERE train_number = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, totalSeats);
                updateStmt.setInt(2, trainNumber);
                updateStmt.executeUpdate();

                // Step 4: Insert booking
                String bookSql = "INSERT INTO booking (email, train_number, seats_booked, seat_preference, total_cost) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement bookStmt = conn.prepareStatement(bookSql);
                bookStmt.setString(1, loggedInUserEmail);
                bookStmt.setInt(2, trainNumber);

                bookStmt.setInt(3, totalSeats);
                bookStmt.setString(4, preference);
                bookStmt.setDouble(5, totalCost);
                bookStmt.executeUpdate();

                System.out.printf("Booking confirmed! Total Cost: Rs.%.2f\n", totalCost);
            } else {
                System.out.println("❌ Not enough seats available.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    static void viewProfile() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserEmail);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\nProfile:");
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("ID Proof: " + rs.getString("id_proof"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void updateProfile() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter new Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Enter new Address: ");
            String address = scanner.nextLine();

            String sql = "UPDATE users SET phone = ?, address = ? WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            pstmt.setString(2, address);
            pstmt.setString(3, loggedInUserEmail);
            pstmt.executeUpdate();

            System.out.println("Profile updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewBookingHistory() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM booking WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loggedInUserEmail);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nBooking History:");
            while (rs.next()) {
                System.out.printf("Train No: %d, Class: %s, Seats: %d, Cost: %.2f, Seat Pref: %s\n",
                        rs.getInt("train_number"), rs.getString("train_number"),
                        rs.getInt("seats_booked"), rs.getDouble("total_cost"), rs.getString("seat_preference"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}