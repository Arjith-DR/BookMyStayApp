import java.util.*;

    class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();

        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public boolean hasRoom(String type) {
            return roomAvailability.containsKey(type);
        }
    }

    class ReservationValidator {
        public void validate(String guestName, String roomType, RoomInventory inventory) throws InvalidBookingException {
            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }
            if (!inventory.hasRoom(roomType)) {
                throw new InvalidBookingException("Invalid room type selected.");
            }
        }
    }

    class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    class BookingRequestQueue {
        private Queue<Reservation> requestQueue = new LinkedList<>();

        public void addRequest(Reservation reservation) {
            requestQueue.offer(reservation);
        }
    }

    public class BookMyStay{
        public static void main(String[] args) {
            System.out.println("Booking Validation");
            Scanner scanner = new Scanner(System.in);

            RoomInventory inventory = new RoomInventory();
            ReservationValidator validator = new ReservationValidator();
            BookingRequestQueue bookingQueue = new BookingRequestQueue();

            try {
                System.out.print("Enter guest name: ");
                String name = scanner.nextLine();

                System.out.print("Enter room type (Single/Double/Suite): ");
                String type = scanner.nextLine();

                validator.validate(name, type, inventory);

                bookingQueue.addRequest(new Reservation(name, type));
                System.out.println("Booking request accepted.");

            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            } finally {
                scanner.close();
            }
        }
    }