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

    class BookingRequestQueue {
        private Queue<Reservation> requestQueue = new LinkedList<>();

        public void addRequest(Reservation res) { requestQueue.offer(res); }
        public Reservation getNextRequest() { return requestQueue.poll(); }
        public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
    }

    class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();

        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public int getCount(String type) { return roomAvailability.getOrDefault(type, 0); }
        public void updateCount(String type, int count) { roomAvailability.put(type, count); }
        public Map<String, Integer> getInventory() { return roomAvailability; }
    }

    class RoomAllocationService {
        private Map<String, Integer> counter = new HashMap<>();

        public void allocateRoom(Reservation res, RoomInventory inventory) {
            if (res == null) return;
            String type = res.getRoomType();
            int available = inventory.getCount(type);
            if (available > 0) {
                int currentNum = counter.getOrDefault(type, 0) + 1;
                counter.put(type, currentNum);
                inventory.updateCount(type, available - 1);
                System.out.println("Booking confirmed for Guest: " + res.getGuestName() + ", Room ID: " + type + "-" + currentNum);
            }
        }
    }

    class ConcurrentBookingProcessor implements Runnable {
        private BookingRequestQueue bookingQueue;
        private RoomInventory inventory;
        private RoomAllocationService allocationService;

        public ConcurrentBookingProcessor(BookingRequestQueue bq, RoomInventory inv, RoomAllocationService as) {
            this.bookingQueue = bq;
            this.inventory = inv;
            this.allocationService = as;
        }

        @Override
        public void run() {
            while (true) {
                Reservation reservation = null;
                synchronized (bookingQueue) {
                    if (bookingQueue.hasPendingRequests()) {
                        reservation = bookingQueue.getNextRequest();
                    } else {
                        break;
                    }
                }
                synchronized (inventory) {
                    allocationService.allocateRoom(reservation, inventory);
                }
            }
        }
    }

    public class BookMyStay{
        public static void main(String[] args) {
            System.out.println("Concurrent Booking Simulation");

            BookingRequestQueue bookingQueue = new BookingRequestQueue();
            RoomInventory inventory = new RoomInventory();
            RoomAllocationService allocationService = new RoomAllocationService();

            bookingQueue.addRequest(new Reservation("Abhi", "Single"));
            bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
            bookingQueue.addRequest(new Reservation("Kural", "Suite"));
            bookingQueue.addRequest(new Reservation("Subha", "Single"));

            Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));
            Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                System.out.println("Thread execution interrupted.");
            }

            System.out.println("\nRemaining Inventory:");
            inventory.getInventory().forEach((k, v) -> System.out.println(k + ": " + v));
        }
    }