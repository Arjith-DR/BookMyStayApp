import java.util.*;

    class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();

        public RoomInventory() {
            roomAvailability.put("Single", 5);
        }

        public void restoreInventory(String roomType) {
            int current = roomAvailability.getOrDefault(roomType, 0);
            roomAvailability.put(roomType, current + 1);
        }

        public int getCount(String roomType) {
            return roomAvailability.getOrDefault(roomType, 0);
        }
    }

    class CancellationService {
        private Stack<String> releasedRoomIds;
        private Map<String, String> reservationRoomTypeMap;

        public CancellationService() {
            releasedRoomIds = new Stack<>();
            reservationRoomTypeMap = new HashMap<>();
        }

        public void registerBooking(String reservationId, String roomType) {
            reservationRoomTypeMap.put(reservationId, roomType);
        }

        public void cancelBooking(String reservationId, RoomInventory inventory) {
            if (reservationRoomTypeMap.containsKey(reservationId)) {
                String type = reservationRoomTypeMap.get(reservationId);
                inventory.restoreInventory(type);
                releasedRoomIds.push(reservationId);
                System.out.println("Booking cancelled successfully. Inventory restored for room type: " + type);
            }
        }

        public void showRollbackHistory() {
            System.out.println("\nRollback History (Most Recent First):");
            while (!releasedRoomIds.isEmpty()) {
                System.out.println("Released Reservation ID: " + releasedRoomIds.pop());
            }
        }
    }

    public class BookMyStay{
        public static void main(String[] args) {
            System.out.println("Booking Cancellation");

            RoomInventory inventory = new RoomInventory();
            CancellationService cancellationService = new CancellationService();

            String resId = "Single-1";
            cancellationService.registerBooking(resId, "Single");

            cancellationService.cancelBooking(resId, inventory);

            cancellationService.showRollbackHistory();

            System.out.println("\nUpdated Single Room Availability: " + inventory.getCount("Single"));
        }
    }