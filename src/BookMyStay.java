import java.util.*;

    class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
    }

    class RoomInventory {
        private Map<String, Integer> roomAvailability;

        public RoomInventory() {
            roomAvailability = new HashMap<>();
            roomAvailability.put("Single", 5);
            roomAvailability.put("Suite", 2);
        }

        public void updateAvailability(String roomType, int count) {
            roomAvailability.put(roomType, count);
        }

        public int getCount(String roomType) {
            return roomAvailability.getOrDefault(roomType, 0);
        }
    }

    class RoomAllocationService {
        private Set<String> allocatedRoomIds;
        private Map<String, Set<String>> assignedRoomsByType;

        public RoomAllocationService() {
            allocatedRoomIds = new HashSet<>();
            assignedRoomsByType = new HashMap<>();
        }

        public void allocateRoom(Reservation reservation, RoomInventory inventory) {
            String type = reservation.getRoomType();
            int currentCount = inventory.getCount(type);

            if (currentCount > 0) {
                String roomId = generateRoomId(type);
                allocatedRoomIds.add(roomId);

                assignedRoomsByType.putIfAbsent(type, new HashSet<>());
                assignedRoomsByType.get(type).add(roomId);

                inventory.updateAvailability(type, currentCount - 1);

                System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() +
                        ", Room ID: " + roomId);
            }
        }

        private String generateRoomId(String roomType) {
            int nextNumber = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
            return roomType + "-" + nextNumber;
        }
    }

public class BookMyStay{
        public static void main(String[] args) {
            System.out.println("Room Allocation Processing");

            RoomInventory inventory = new RoomInventory();
            RoomAllocationService allocationService = new RoomAllocationService();

            Reservation r1 = new Reservation("Abhi", "Single");
            Reservation r2 = new Reservation("Subha", "Single");
            Reservation r3 = new Reservation("Vanmathi", "Suite");

            allocationService.allocateRoom(r1, inventory);
            allocationService.allocateRoom(r2, inventory);
            allocationService.allocateRoom(r3, inventory);
        }
    }