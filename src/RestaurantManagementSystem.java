import java.io.*;
import java.time.LocalDate;
import java.util.*;

class MenuItem {
    int id;
    String name;
    double price;

    MenuItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

public class RestaurantManagementSystem {

    static List<MenuItem> menu = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadMenu();

        while (true) {
            System.out.println("\n===== RESTAURANT SYSTEM =====");
            System.out.println("1. Show Menu");
            System.out.println("2. Take Order");
            System.out.println("3. Show Daily Transactions");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> showMenu();
                case 2 -> takeOrder();
                case 3 -> showDailyTransactions();
                case 4 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    // Load menu from file
    static void loadMenu() {
        try (BufferedReader br = new BufferedReader(new FileReader("menu.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                menu.add(new MenuItem(
                        Integer.parseInt(data[0]),
                        data[1],
                        Double.parseDouble(data[2])
                ));
            }
        } catch (IOException e) {
            System.out.println("Error loading menu file.");
        }
    }

    // Display menu
    static void showMenu() {
        System.out.println("\n--- MENU ---");
        for (MenuItem item : menu) {
            System.out.println(item.id + ". " + item.name + " - Rs " + item.price);
        }
    }

    // Take order
    static void takeOrder() {
        showMenu();

        double total = 0;
        StringBuilder orderDetails = new StringBuilder();
        String todayFile = "orders_" + LocalDate.now() + ".txt";

        while (true) {
            System.out.print("Enter item ID (0 to finish): ");
            int id = scanner.nextInt();

            if (id == 0) break;

            Optional<MenuItem> selected = menu.stream()
                    .filter(m -> m.id == id)
                    .findFirst();

            if (selected.isPresent()) {
                System.out.print("Enter quantity: ");
                int qty = scanner.nextInt();

                double itemTotal = selected.get().price * qty;
                total += itemTotal;

                orderDetails.append(selected.get().name)
                        .append(" x")
                        .append(qty)
                        .append(" = Rs ")
                        .append(itemTotal)
                        .append("\n");
            } else {
                System.out.println("Invalid Item ID!");
            }
        }

        orderDetails.append("TOTAL = Rs ").append(total).append("\n");
        orderDetails.append("----------------------\n");

        try (FileWriter fw = new FileWriter(todayFile, true)) {
            fw.write(orderDetails.toString());
        } catch (IOException e) {
            System.out.println("Error saving order.");
        }

        System.out.println("Order saved successfully!");
    }

    // Show daily transactions
    static void showDailyTransactions() {
        String todayFile = "orders_" + LocalDate.now() + ".txt";

        try (BufferedReader br = new BufferedReader(new FileReader(todayFile))) {
            String line;
            System.out.println("\n--- DAILY TRANSACTIONS ---");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No transactions found for today.");
        }
    }
}