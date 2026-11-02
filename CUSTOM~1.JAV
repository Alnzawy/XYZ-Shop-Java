import java.time.format.DateTimeFormatter;
import java.util.*;

public class Customer extends User  {

    private double balance;

    private List<Invoice> UserInvoices;
    Order allorders = Order.getInstance();

    ItemCollections itemCollections = ItemCollections.getInstance();
    UserCollections userCollections = UserCollections.getInstance();

    Scanner scanner = new Scanner(System.in);


    public Customer(String username, String password) {
        super(username, password);
        this.balance = 0.0;
        UserInvoices = new ArrayList<>();
    }


    @Override
    public void displayUserInfo() {
        System.out.println("Customer Information");
        System.out.println("Username: " + getUSERNAME());
        System.out.println("User ID: " + getUserID());
        System.out.println("User Password: " + getPASSWORD());
        System.out.println("Type: " + getUserType());
        System.out.println("Active :" + getStatus());
        System.out.println("Balance :" + getBalance());
        System.out.println("==================================");

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Do you want to chang Your Information press \n 1) to chang username \n 2) to chang password \n 3) to Exit");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter your new USERNAME: ");
                    setUSERNAME(in.nextLine());
                    System.out.println("New username is : " + getUSERNAME());
                    break;
                case "2":
                    System.out.print("Enter your new PassWord: ");
                    setPASSWORD(in.nextLine());
                    System.out.println("New username is : " + getPASSWORD());
                    break;
                case "3":
                    return; // Exit the method
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    @Override
    public String getUserType() {
        return "Customer";
    }

    @Override
    public void Interface() {

        while (true) {
            System.out.println("                        Customer Interface:");
            System.out.println("===================================================================");
            System.out.println("(1) Display orders");
            System.out.println("(2) Create order");
            System.out.println("(3) Delete order");
            System.out.println("(4) Display Invoices");
            System.out.println("(5) Rate seller");
            System.out.println("(6) Set and see Balance");
            System.out.println("(7) display User Info");
            System.out.println("(8) Exit");
            System.out.println("===================================================================");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    displayOrders();
                    break;
                case "2":
                    createOrder();
                    break;
                case "3":
                    deleteOrder();
                    break;
                case "4":
                    displayInvoices();
                    break;
                case "5":
                    rateSeller();
                    break;
                case "6":
                    DisplayAndSetBalance();
                    break;
                case "7":
                    displayUserInfo();
                    break;
                case "8":
                    System.out.println("Exiting Customer Interface.");
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a valid number.");
                    break;
            }
        }

    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    private void DisplayAndSetBalance() {
        if (getStatus().equals("Inactive")) {
            System.out.println("You can not set Balance");
            System.out.println("Because you are not activated");
            return;
        }
        System.out.println("Customer " + getUSERNAME() + " balance: " + getBalance());
        System.out.println("");
        System.out.println("Press 1 to add new balance ");
        System.out.println("Press 2 to go back ");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("2")) {
            return; // Exit the method
        }
        if (input.equalsIgnoreCase("1")) {

            System.out.println("enter the balance that you want to add ");

            try {
                double newBalance = Double.parseDouble(scanner.nextLine());
                if (newBalance >= 0) {
                    this.balance = newBalance + balance;
                    System.out.println("New balance set: $" + balance);
                } else {
                    System.out.println("Balance cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public void displayOrders() {
        List<Order> allOrders = allorders.getAllOrders();

        if (getStatus().equals("Inactive")) {
            System.out.println("You cannot display Orders because you are not activated.");
            return;


        }

        System.out.println("Customer Orders:");

        if (!allOrders.isEmpty()) {
            System.out.println("Customer Orders:");
            System.out.println("---------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-12s | %-23s | %-8s | %-12s | %-8s | %-23s |%n",
                    "Order Number", "Item Name", "Item ID", "Item Price", "Quantity", "Seller Name");

            for (Order order : allOrders) {
                for (Item item : order.getItems()) {
                    if (item.GetCustomerUsername().equals(this.getUSERNAME())) {
                        System.out.printf("| %-12d | %-23s | %-8s | $%-11s | %-8s | %-23s |%n",
                                order.getOrderNumber(), item.getItemName(), item.getItemIdNo(), item.getItemPrice(),
                                item.getItemQuantity(), item.GetSellerName());
                    }
                }
                System.out.println("---------------------------------------------------------------------------------------------------------");
                System.out.println("Total Amount: $" + order.calculateOrderTotal(order));
                System.out.println("");
            }
        } else {
            System.out.println("No Orders found.");
        }
    }


    public void displayInvoices() {
        List<Invoice> allInvoices = Invoice.getAllInvoices();

        if (allInvoices == null || allInvoices.isEmpty()) {
            System.out.println("No Invoices found.");
            return;
        }

        System.out.println("Customer Invoices:");

        if (!allInvoices.isEmpty()) {
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Customer Invoices:");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-5s | %-15s | %-9s | %-20s |%n",
                    "Invoice Number", "Date", "Total ", "Seller ");

            for (Invoice invoice : allInvoices) {
                for (Order order : invoice.getOrders()) {
                    for (Item item : order.getItems()) {
                        if (item.getCustomer() == this) {
                            System.out.printf("| %-14d | %-15s | $%-8s | %-20s |%n",
                                    invoice.getInvoiceNumber(),
                                    invoice.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                                    invoice.getTotalAmount(),
                                    item.GetSellerName());
                        }
                    }
                }
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("No Invoices found.");
        }
    }


    public void createOrder() {

        if (getStatus().equals("Inactive")) {
            System.out.println("You cannot create Orders because you are not activated.");
            return;
        }

        if (itemCollections.getActivatedItems().isEmpty()){
           return;
        }

        do {
            List<Item> currentOrderItems = new ArrayList<>();

            itemCollections.PrintActivatedItems();
            System.out.println("Select from the following available products: Enter Item ID to buy, or '0' to go back:");

            String input = scanner.nextLine();

            if (input.equals("0")) {
                    System.out.println("No items selected. Exiting order creation.");
                    return;
            }

            int itemIdToBuy;

            try {
                itemIdToBuy = Integer.parseInt(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid Item ID.");
                continue;
            }

            Item itemToBuy = findItemById(itemIdToBuy);

            if (itemToBuy == null ) {
                System.out.println("Item not found.");
                continue;
            }

            System.out.println("Enter quantity to buy:");
            int quantity;
            try {
                quantity = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid quantity.");
                continue;
            }

            if (quantity <= 0 || quantity > itemToBuy.getItemQuantity()) {
                System.out.println("Invalid quantity or insufficient stock.");
                continue;
            }

            double cost = quantity * itemToBuy.getItemPrice();
            if (cost > getBalance()) {
                System.out.println("Insufficient balance to complete the purchase.");
                continue;
            }



            setBalance(getBalance() - cost);

            itemToBuy.setItemQuantity(itemToBuy.getItemQuantity() - quantity);

            currentOrderItems.add(new Item(itemToBuy.getItemName(), quantity, itemToBuy.getItemPrice(), itemToBuy.getSeller(), itemToBuy.getItemIdNo(),this));

            Order newOrder = new Order(currentOrderItems);
            newOrder.calculateOrderTotal(newOrder);
            newOrder.setCustomer(this);
            newOrder.calculateOrderTotal(newOrder);


            allorders.addOrder(newOrder);


            System.out.println("Total Order Cost: $" + cost);
            System.out.println("Order has been successfully processed.");
            System.out.println("Order generated: " + newOrder.getOrderNumber());

            return;
        } while (true);

    }

    private Item findItemById(int id) {

        for (Item item : itemCollections.getActivatedItems()) {
            if (item.getItemIdNo() == id) {
                return item; // Return the item if the ID matches
            }
        }
        return null; // Return null if no item is found with the given ID
    }


    public void deleteOrder() {


        if (getStatus().equals("Inactive")) {
            System.out.println("You cannot remove products because you are not activated.");
            return;
        }
        List<Order> ALLorders = allorders.getAllOrders();

        if (ALLorders.isEmpty()) {
            System.out.println("No Orders.");
            return;
        }

        System.out.println("All Items:");
        System.out.println("===============");

        displayOrders();

        System.out.println("Enter Order ID to remove, or '0' to go back:");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            return;
        } else {
            try {
                int orderNumberToRemove = Integer.parseInt(input);
                Order orderToRemove = null;
                for (Order order : ALLorders) {
                    if (order.getOrderNumber() == orderNumberToRemove) {
                        orderToRemove = order;
                        break;
                    }
                }

                if (orderToRemove != null) {
                    double refundAmount = orderToRemove.calculateOrderTotal(orderToRemove);

                    // Refund the money to the customer's balance
                    setBalance(getBalance() + refundAmount);

                    // Remove the order from allorders directly
                    ALLorders.remove(orderToRemove);

                    for (Item item : orderToRemove.getItems()) {
                        Item originalItem = findItemById(item.getItemIdNo());
                        originalItem.setItemQuantity(originalItem.getItemQuantity() + item.getItemQuantity());
                    }

                    System.out.println("Order with ID " + orderNumberToRemove + " has been successfully deleted.");
                    System.out.println("You have been refunded $" + refundAmount);
                } else {
                    System.out.println("Order with ID " + orderNumberToRemove + " not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid order ID, '1' to remove all, or '0' to go back.");
            }
        }
    }


    public void rateSeller() {
        if (getStatus().equals("Inactive")) {
            System.out.println("You cannot rate the seller because you are not activated.");
            return;
        }

        List<Invoice> allInvoices = Invoice.getAllInvoices();

        if (allInvoices == null || allInvoices.isEmpty()) {
            System.out.println("No Invoices found.");
            return;
        }

        System.out.println("Customer Invoices:");

        if (!allInvoices.isEmpty()) {
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Customer Invoices:");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-5s | %-15s | %-9s | %-20s |%n",
                    "Invoice Number", "Date", "Total ", "Seller ");

            for (Invoice invoice : allInvoices) {
                for (Order order : invoice.getOrders()) {
                    for (Item item : order.getItems()) {
                        if (item.getCustomer() == this) {
                            System.out.printf("| %-14d | %-15s | $%-8s | %-20s |%n",
                                    invoice.getInvoiceNumber(),
                                    invoice.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                                    invoice.getTotalAmount(),
                                    item.GetSellerName());
                        }
                    }
                }
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("No Invoices found.");
        }


        System.out.print("Enter seller UserName: ");
        String userSeller = scanner.nextLine(); // Improved handling of user input

        System.out.print("Enter rating (between 1 and 5): ");
        String ratingInput = scanner.nextLine();

        try {
            int rating = Integer.parseInt(ratingInput);

            if (rating < 1 || rating > 5) {
                System.out.println("Invalid rating. Rating should be between 1 and 5.");
                return;
            }

            boolean sellerFound = false;
            for (User user : userCollections.getSellerUsers()) {
                if (user.getUSERNAME().equals(userSeller)) {
                    Seller seller = (Seller) user;
                    seller.addRating(rating);
                    sellerFound = true;
                    System.out.println("Seller with UserName " + userSeller + " has been rated successfully.");
                    break;
                }
            }

            if (!sellerFound) {
                System.out.println("Seller with UserName " + userSeller + " not found or not a seller.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer for the rating.");
        }
    }





}
