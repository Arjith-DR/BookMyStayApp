import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    class Service {
        private String serviceName;
        private double cost;

        public Service(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    class AddOnServiceManager {
        private Map<String, List<Service>> servicesByReservation;

        public AddOnServiceManager() {
            servicesByReservation = new HashMap<>();
        }

        public void addService(String reservationId, Service service) {
            servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
            servicesByReservation.get(reservationId).add(service);
        }

        public double calculateTotalServiceCost(String reservationId) {
            double total = 0;
            List<Service> services = servicesByReservation.get(reservationId);
            if (services != null) {
                for (Service s : services) {
                    total += s.getCost();
                }
            }
            return total;
        }
    }

    public class BookMyStay{
        public static void main(String[] args) {
            System.out.println("Add-On Service Selection");

            AddOnServiceManager manager = new AddOnServiceManager();
            String resId = "Single-1";

            Service breakfast = new Service("Breakfast", 500.0);
            Service spa = new Service("Spa", 1000.0);

            manager.addService(resId, breakfast);
            manager.addService(resId, spa);

            System.out.println("Reservation ID: " + resId);
            System.out.println("Total Add-On Cost: " + manager.calculateTotalServiceCost(resId));
        }
    }