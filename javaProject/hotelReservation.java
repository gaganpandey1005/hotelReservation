//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Hotel {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String user = "root";
    private static final String password = "Tiger";

    public Hotel() {
    }

    public static void main(String[] param0) throws Exception {
        // $FF: Couldn't be decompiled
    }

    private static void reservRoom(Connection connection, Scanner sc) throws SQLException {
        try {
            System.out.println("Enter Guest name");
            String name = sc.next();
            System.out.println("Enter the room number");
            int roomNumber = sc.nextInt();
            System.out.println("Enter Contact Number");
            String contact = sc.next();
            String sql = "INSERT INTO reservations (guest_name, room_no, contact_no) VALUES ('" + name + "', " + roomNumber + ", '" + contact + "')";
            Statement statement = connection.createStatement();

            try {
                int affectedRow = statement.executeUpdate(sql);
                if (affectedRow > 0) {
                    System.out.println("Reservation has been added");
                } else {
                    System.out.println("Reservation has not been added");
                }
            } catch (Throwable var10) {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Throwable var9) {
                        var10.addSuppressed(var9);
                    }
                }

                throw var10;
            }

            if (statement != null) {
                statement.close();
            }
        } catch (SQLException var11) {
            SQLException e = var11;
            System.out.println(e.getMessage());
        }

    }

    private static void viewReservation(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        try {
            ResultSet rs = statement.executeQuery("Select * from reservations");
            System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            while(true) {
                if (!rs.next()) {
                    System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
                    break;
                }

                int reservationNo = rs.getInt("res_id");
                String guestName = rs.getString("guest_name");
                int roomNo = rs.getInt("room_no");
                String contact = rs.getString("contact_no");
                String reservationTime = rs.getString("reservation_time").toString();
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n", reservationNo, guestName, roomNo, contact, reservationTime);
            }
        } catch (Throwable var9) {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Throwable var8) {
                    var9.addSuppressed(var8);
                }
            }

            throw var9;
        }

        if (statement != null) {
            statement.close();
        }

    }

    private static void getRoomNo(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter Reservation No: ");
            int reservationNo = sc.nextInt();
            System.out.println("Enter Guest Name: ");
            String guestName = sc.next();
            String sql = "Select room_no from reservations where res_id= " + reservationNo + " and guest_name = '" + guestName + "'";
            Statement statement = connection.createStatement();

            try {
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next()) {
                    int roomNo = rs.getInt("room_no");
                    System.out.println(roomNo);
                } else {
                    System.out.println("Reservation does not exist");
                }
            } catch (Throwable var9) {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                    }
                }

                throw var9;
            }

            if (statement != null) {
                statement.close();
            }
        } catch (SQLException var10) {
            SQLException e = var10;
            System.out.println(e.getMessage());
        }

    }

    private static void updateReservation(Connection connection, Scanner sc) {
        SQLException e;
        int reservationId;
        try {
            System.out.println("Enter Reservation No: ");
            reservationId = sc.nextInt();
            sc.nextLine();
            if (!reservationExist(connection, reservationId)) {
                System.out.println("Reservation does not exist");
                return;
            }
        } catch (SQLException var13) {
            e = var13;
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Enter new guest_name");
            String newGuestName = sc.nextLine();
            System.out.println("Enter new room number");
            int newRoomNumber = sc.nextInt();
            System.out.println("Enter new contact number");
            String newContact = sc.next();
            String sql = "Update reservations SET guest_name='" + newGuestName + "',room_no= " + newRoomNumber + ",contact_no= '" + newContact + "' WHERE res_id= " + reservationId + ";";
            Statement statement = connection.createStatement();

            try {
                int affectedRow = statement.executeUpdate(sql);
                if (affectedRow > 0) {
                    System.out.println("Reservation has been updated");
                } else {
                    System.out.println("Reservation has not been updated");
                }
            } catch (Throwable var11) {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Throwable var10) {
                        var11.addSuppressed(var10);
                    }
                }

                throw var11;
            }

            if (statement != null) {
                statement.close();
            }

        } catch (SQLException var12) {
            e = var12;
            throw new RuntimeException(e);
        }
    }

    private static void deleteReservation(Connection connection, Scanner sc) {
        try {
            System.out.println("Enter Reservation No: ");
            int reservationId = sc.nextInt();
            if (!reservationExist(connection, reservationId)) {
                System.out.println("Reservation does not exist");
                return;
            }

            String sql = "DELETE FROM reservations WHERE res_id= " + reservationId + ";";
            Statement statement = connection.createStatement();

            try {
                int affectedRows = statement.executeUpdate(sql);
                if (affectedRows > 0) {
                    System.out.println("Reservation has been deleted");
                } else {
                    System.out.println("Reservation has not been deleted");
                }
            } catch (Throwable var8) {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }
                }

                throw var8;
            }

            if (statement != null) {
                statement.close();
            }
        } catch (Exception var9) {
            Exception e = var9;
            e.printStackTrace();
        }

    }

    private static boolean reservationExist(Connection connection, int reservationNo) {
        try {
            String sql = "Select res_id from reservations where res_id= " + reservationNo + ";";
            Statement statement = connection.createStatement();

            boolean var5;
            try {
                ResultSet rs = statement.executeQuery(sql);
                var5 = rs.next();
            } catch (Throwable var7) {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (statement != null) {
                statement.close();
            }

            return var5;
        } catch (SQLException var8) {
            SQLException e = var8;
            e.printStackTrace();
            return false;
        }
    }

    private static void exit() throws InterruptedException {
        System.out.print("Goodbye");

        for(int i = 4; i != 0; --i) {
            System.out.print(".");
            Thread.sleep(900L);
        }

        System.out.println();
        System.out.println("Thank You for using Hotel reservation system");
    }
}
