import java.util.*;
import java.io.*;

    class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();

        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public Map<String, Integer> getInventory() {
            return roomAvailability;
        }

        public void setInventory(String type, int count) {
            roomAvailability.put(type, count);
        }
    }

    class FilePersistenceService {
        public void saveInventory(RoomInventory inventory, String filePath) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
                    writer.println(entry.getKey() + "=" + entry.getValue());
                }
                System.out.println("Inventory saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving inventory.");
            }
        }

        public void loadInventory(RoomInventory inventory, String filePath) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        inventory.setInventory(parts[0], Integer.parseInt(parts[1]));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading inventory.");
            }
        }
    }

    public class BookMyStay{
        public static void main(String[] args) {
            System.out.println("System Recovery");

            RoomInventory inventory = new RoomInventory();
            FilePersistenceService persistenceService = new FilePersistenceService();
            String filePath = "inventory.txt";

            persistenceService.loadInventory(inventory, filePath);

            System.out.println("\nCurrent Inventory:");
            inventory.getInventory().forEach((k, v) -> System.out.println(k + ": " + v));

            persistenceService.saveInventory(inventory, filePath);
        }
    }