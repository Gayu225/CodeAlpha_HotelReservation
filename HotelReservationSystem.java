import java.util.Scanner;

public class HotelReservationSystem {
    private static String name;
    private static int roomNo;
    private static int bookingIdCounter = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean[] rooms = new boolean[50]; // Array to track room availability

        System.out.println("Welcome to the Hotel Reservation System");
        System.out.print("Please enter your name : ");
        name = sc.nextLine();

        while (true) {
            System.out.println("\nCategories of rooms : ");
            System.out.println("1. Standard : Rooms 1 - 20 -> Charges per day : 1000");
            System.out.println("2. Deluxe   : Rooms 21 - 40 -> Charges per day : 5000");
            System.out.println("3. Suite    : Rooms 41 - 50 -> Charges per day : 10000");
            System.out.println("4. Exit");

            System.out.println("Please enter your choice (1, 2, 3, or 4): ");
            char choice = sc.next().charAt(0);

            switch (choice) {
                case '1', '2', '3' -> {
                    roomNo = selectRoom(sc, rooms, choice);
                    if (roomNo != -1) {
                        bookRoom(sc, rooms, choice);
                    }
                    return; // Exit after booking is confirmed
                }
                case '4' -> {
                    System.out.println("Thank you for using the Hotel Reservation System. Have a nice day!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static int selectRoom(Scanner sc, boolean[] rooms, char choice) {
        int startRange = 0, endRange = 0;

        switch (choice) {
            case '1' -> {
                startRange = 0;
                endRange = 19;
            }
            case '2' -> {
                startRange = 20;
                endRange = 39;
            }
            case '3' -> {
                startRange = 40;
                endRange = 49;
            }
        }

        System.out.println("Available rooms : ");
        boolean roomsAvailable = false;
        for (int i = startRange; i <= endRange; i++) {
            if (!rooms[i]) {
                System.out.print((i + 1) + " ");
                roomsAvailable = true;
            }
        }
        System.out.println();

        if (!roomsAvailable) {
            System.out.println("No rooms available in this category. Please choose another category.");
            return -1;
        }

        System.out.println("Please select the room number from the available rooms: ");
        int roomChoice = sc.nextInt();
        if (roomChoice < startRange + 1 || roomChoice > endRange + 1 || rooms[roomChoice - 1]) {
            System.out.println("Invalid room selection. Please select a valid room number.");
            return -1;
        }

        return roomChoice;
    }

    private static void bookRoom(Scanner sc, boolean[] rooms, char choice) {
        System.out.println("Please enter the check-in date (DD/MM/YYYY): ");
        String checkInDate = sc.next();
        System.out.println("Please enter the number of days you want to stay: ");
        int numDays = sc.nextInt();

        System.out.println("Please wait while the payment is being processed...");
        simulatePaymentProcessing();

        System.out.println("Booking is confirmed.");

        // Update room status and generate booking ID
        rooms[roomNo - 1] = true;
        int bookingId = generateBookingId();
        
        System.out.println();
        viewDetails(bookingId, choice, numDays, checkInDate);
    }

    private static void simulatePaymentProcessing() {
        try {
            Thread.sleep(5000); // Simulate payment processing delay
        } catch (InterruptedException e) {
        }
    }

    private static int generateBookingId() {
        return bookingIdCounter++;
    }

    private static void viewDetails(int bookingId, char choice, int numDays, String checkInDate) {
        System.out.println("Booking Details : ");
        System.out.println("Booking ID     : " + bookingId);
        System.out.println("Name           : " + name);
        System.out.println("Room Number    : " + roomNo);

        String roomCategory = "";
        switch (choice) {
            case '1' -> roomCategory = "Standard";
            case '2' -> roomCategory = "Deluxe";
            case '3' -> roomCategory = "Suite";
        }
        System.out.println("Room Category  : " + roomCategory);
        System.out.println("Check-in Date  : " + checkInDate);
        System.out.println("Number of days : " + numDays);
        System.out.println("Total Charges  : " + calculateCharges(choice, numDays));
        System.out.println();
    }

    private static int calculateCharges(char choice, int numDays) {
        int chargesPerDay = 0;
        switch (choice) {
            case '1' -> chargesPerDay = 1000;
            case '2' -> chargesPerDay = 5000;
            case '3' -> chargesPerDay = 10000;
        }
        return chargesPerDay * numDays;
    }
}