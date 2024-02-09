/*Fenesse Haywood
2/5/24*/

import java.util.*;

class ToppingsManager {
    private Set<String> toppings;

    public ToppingsManager() {
        toppings = new HashSet<>();
    }

    public List<String> listToppings() {
        return new ArrayList<>(toppings);
    }

    public boolean addTopping(String topping) {
        if (!toppings.contains(topping)) {
            toppings.add(topping);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTopping(String topping) {
        if (toppings.contains(topping)) {
            toppings.remove(topping);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateTopping(String oldTopping, String newTopping) {
        if (toppings.contains(oldTopping) && !toppings.contains(newTopping)) {
            toppings.remove(oldTopping);
            toppings.add(newTopping);
            return true;
        } else {
            return false;
        }
    }
}

class PizzaManager {
    private Map<String, List<String>> pizzas;
    private ToppingsManager toppingsManager;

    public PizzaManager(ToppingsManager toppingsManager) {
        pizzas = new HashMap<>();
        this.toppingsManager = toppingsManager;
    }

    public Map<String, List<String>> listPizzas() {
        return new HashMap<>(pizzas);
    }

    public boolean createPizza(String pizzaName, List<String> toppings) {
        if (!pizzas.containsKey(pizzaName) && toppings.stream().allMatch(t -> toppingsManager.listToppings().contains(t))) {
            pizzas.put(pizzaName, toppings);
            return true;
        } else {
            return false;
        }
    }

    public boolean deletePizza(String pizzaName) {
        if (pizzas.containsKey(pizzaName)) {
            pizzas.remove(pizzaName);
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePizza(String pizzaName, List<String> newToppings) {
        if (pizzas.containsKey(pizzaName) && newToppings.stream().allMatch(t -> toppingsManager.listToppings().contains(t))) {
            pizzas.put(pizzaName, newToppings);
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePizzaToppings(String pizzaName, List<String> addedToppings, List<String> removedToppings) {
        if (pizzas.containsKey(pizzaName) && addedToppings.stream().allMatch(t -> toppingsManager.listToppings().contains(t))
                && removedToppings.stream().allMatch(t -> pizzas.get(pizzaName).contains(t))) {
            Set<String> currentToppings = new HashSet<>(pizzas.get(pizzaName));
            currentToppings.addAll(addedToppings);
            currentToppings.removeAll(removedToppings);
            pizzas.put(pizzaName, new ArrayList<>(currentToppings));
            return true;
        } else {
            return false;
        }
    }
}

public class PizzaChef {
    public static void main(String[] args) {
        ToppingsManager toppingsManager = new ToppingsManager();
        PizzaManager pizzaManager = new PizzaManager(toppingsManager);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. List Toppings");
            System.out.println("2. Add Topping");
            System.out.println("3. Delete Topping");
            System.out.println("4. Update Topping");
            System.out.println("5. List Pizzas");
            System.out.println("6. Create Pizza");
            System.out.println("7. Delete Pizza");
            System.out.println("8. Update Pizza");
            System.out.println("9. Update Pizza Toppings");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Available Toppings: " + toppingsManager.listToppings());
                    break;
                case 2:
                    System.out.print("Enter topping to add: ");
                    String newTopping = scanner.nextLine();
                    if (toppingsManager.addTopping(newTopping)) {
                        System.out.println("Topping added successfully.");
                    } else {
                        System.out.println("Topping already exists.");
                    }
                    break;
                case 3:
                    System.out.print("Enter topping to delete: ");
                    String toppingToDelete = scanner.nextLine();
                    if (toppingsManager.deleteTopping(toppingToDelete)) {
                        System.out.println("Topping deleted successfully.");
                    } else {
                        System.out.println("Topping not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter old topping: ");
                    String oldTopping = scanner.nextLine();
                    System.out.print("Enter new topping: ");
                    String newToppingToUpdate = scanner.nextLine();
                    if (toppingsManager.updateTopping(oldTopping, newToppingToUpdate)) {
                        System.out.println("Topping updated successfully.");
                    } else {
                        System.out.println("Topping not found or new topping already exists.");
                    }
                    break;
                case 5:
                    System.out.println("Existing Pizzas:");
                    for (Map.Entry<String, List<String>> entry : pizzaManager.listPizzas().entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;
                case 6:
                    System.out.print("Enter pizza name: ");
                    String pizzaName = scanner.nextLine();
                    System.out.print("Enter toppings (comma-separated): ");
                    List<String> pizzaToppings = Arrays.asList(scanner.nextLine().split(","));
                    if (pizzaManager.createPizza(pizzaName, pizzaToppings)) {
                        System.out.println("Pizza created successfully.");
                    } else {
                        System.out.println("Pizza already exists or some toppings are invalid.");
                    }
                    break;
                case 7:
                    System.out.print("Enter pizza name to delete: ");
                    String pizzaToDelete = scanner.nextLine();
                    if (pizzaManager.deletePizza(pizzaToDelete)) {
                        System.out.println("Pizza deleted successfully.");
                    } else {
                        System.out.println("Pizza not found.");
                    }
                    break;
                case 8:
                    System.out.print("Enter pizza name to update: ");
                    String pizzaToUpdate = scanner.nextLine();
                    System.out.print("Enter new toppings (comma-separated): ");
                    List<String> newToppingsForPizza = Arrays.asList(scanner.nextLine().split(","));
                    if (pizzaManager.updatePizza(pizzaToUpdate, newToppingsForPizza)) {
                        System.out.println("Pizza updated successfully.");
                    } else {
                        System.out.println("Pizza not found or some toppings are invalid.");
                    }
                    break;
                case 9:
                    System.out.print("Enter pizza name to update toppings: ");
                    String pizzaToUpdateToppings = scanner.nextLine();
                    System.out.print("Enter toppings to add (comma-separated): ");
                    List<String> addedToppingsForPizza = Arrays.asList(scanner.nextLine().split(","));
                    System.out.print("Enter toppings to remove (comma-separated): ");
                    List<String> removedToppingsForPizza = Arrays.asList(scanner.nextLine().split(","));
                    if (pizzaManager.updatePizzaToppings(pizzaToUpdateToppings, addedToppingsForPizza, removedToppingsForPizza)) {
                        System.out.println("Pizza toppings updated successfully.");
                    } else {
                        System.out.println("Pizza not found or some toppings are invalid.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}