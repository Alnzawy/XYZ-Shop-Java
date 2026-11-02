import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Seller extends User {

    double Profit;
    int Quantity;
    private List<Integer> ratings;

    private List<Order> productOrders;

    Scanner scanner = new Scanner(System.in);

    ItemCollections itemCollections = ItemCollections.getInstance();
    Order orderInstance = Order.getInstance();
    Invoice invoice = Invoice.getInstance();
    List<Order> SellerOrders;

    private List<Item> sellerProducts;

    public Seller(String username, String password) {
        super(username, password);
        sellerProducts = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.productOrders = new ArrayList<>();
        this.SellerOrders = new ArrayList<>();
        this.Profit = 0.0;
    }

    @Override
    public void displayUserInfo() {
        System.out.println("Seller Information");
        System.out.println("User ID: " + getUserID());
        System.out.println("USERNAME: " + getUSERNAME());
        System.out.println("PASSWORD : " + getPASSWORD());
        System.out.println("Status " + getStatus());
        System.out.println("User type: " + getUserType());

    }

    @Override
    public String getUserType() {
        return "Seller";
    }

    @Override
    public void Interface() {
        while (true) {
            System.out.println("");
            System.out.println("Seller Interface");
            System.out.println("==========================================");
            System.out.println("1) Add product.  ");
            System.out.println("2) Remove Product. ");
            System.out.println("3) Display All products.      ");
            System.out.println("4) Display selling statistics. ");
            System.out.println("5) Display and approve orders. ");
            System.out.println("6) Display User Information. ");
            System.out.println("7) Exit ");
            System.out.println("==========================================");


            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addProduct();
                    break;
                case "2":
                    removeProduct();
                    break;
                case "3":
                    displayAllProducts();
                    break;
                case "4":
                    displaySellingStatistics();
                    break;
                case "5":
                    ApproveOrder();
                    break;
                case "6":
                    displayUserInfo();
                    break;
                case "7":
                    System.out.println("Exiting Seller Panel.");
                    System.out.println("");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    break;
            }
        }
    }

    public List<Item> getSellerProducts() {
        return sellerProducts;
    }

    public void setSellerProducts(List<Item> sellerProducts) {
        this.sellerProducts = sellerProducts;
    }

    private void printSellerItems() {
        if (sellerProducts.isEmpty()) {
            System.out.println("No Items.");
            return;
        }
        System.out.println("ALL Items:");
        System.out.println("============================================================================");
        System.out.printf("%-5s%-20s%-15s%-15s%-15s%-15s%-15s\n", "NO", "Item Name", "Item ID", "Item Price", "Quantity", "Seller", "Status");

        for (int i = 0; i < sellerProducts.size(); i++) {
            Item item = sellerProducts.get(i);
            System.out.printf("%-5d%-20s%-15s%-15s%-15s%-15s%-15s\n",
                    (i + 1), item.getItemName(), item.getItemIdNo(), item.getItemPrice(),
                    item.getItemQuantity(), this.getUSERNAME(), item.getStatus());
        }
    }


    public void addProduct() {
        if (getStatus().equals("Inactive")) {
            System.out.println("You cannot add products because you are not activated.");
            return;
        }

        System.out.println("=====================================================");
        System.out.println("Your Products");
        printSellerItems();
        System.out.println("=====================================================");


        String productName;
        do {
            System.out.print("Enter product name: ");
            productName = scanner.nextLine().trim();

            if (productName.isEmpty()) {

                System.out.println("Error: The product name must not be blank!");

            } else if (findItemsInUserProduct(productName) != null) {
                System.out.println("Error: You already have a product with this name!");
            }
        } while (productName.isEmpty() || findItemsInUserProduct(productName) != null);

        String ProductQuantity; // Moved this line up
        do {
            System.out.print("Enter product quantity : ");
            ProductQuantity = scanner.nextLine().trim();

            if (ProductQuantity.isEmpty()) {
                System.out.println("Error: The product quantity must not be blank.");
                continue; // Continue the loop to re-enter quantity
            }

            int productQuantity;
            try {
                productQuantity = Integer.parseInt(ProductQuantity);

                if (productQuantity < 1 || productQuantity >= 500) {
                    System.out.println("Error: The product quantity must be between 1 and 500.");
                    continue; // Continue the loop to re-enter quantity
                }

                // If we reach this point, the input is valid
                break; // Exit the loop
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        } while (true); // Use an infinite loop and break when valid input is provided

        String ProductPrice; // Moved this line up
        do {
            System.out.print("Enter product Price : ");
            ProductPrice = scanner.nextLine().trim();

            if (ProductPrice.isEmpty()) {
                System.out.println("Error: The product Price must not be blank.");
                continue; // Continue the loop to re-enter price
            }

            double productPrice;
            try {
                productPrice = Double.parseDouble(ProductPrice);

                if (productPrice < 0 || productPrice >= 5000) {
                    System.out.println("Error: The product price must be higher than 0 and less or equal 5000.");
                    continue; // Continue the loop to re-enter price
                }

                // If we reach this point, the input is valid
                break; // Exit the loop
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        } while (true); // Use an infinite loop and break when valid input is provided

        Item newItem = new Item(productName, Integer.parseInt(ProductQuantity), Double.parseDouble(ProductPrice), this); // 'this' refers to the Seller object

        // Add the new item to the seller's product list
        sellerProducts.add(newItem);

        // Add the new item to the ItemCollections instance
        itemCollections.addItem(newItem);

        System.out.println("Product added successfully.");

        try (FileWriter writer = new FileWriter("Product.txt", true)) { // Append to the file
            writer.write("Product Name: " + newItem.getItemName() + "\n");
            writer.write("Product Price: " + newItem.getItemPrice() + "\n");
            writer.write("Product ID: " + newItem.getItemIdNo() + "\n");
            writer.write("Product Quantity: " + newItem.getItemQuantity() + "\n");
            System.out.println("Product information saved to Product.txt");
        } catch (IOException e) {
            System.out.println("Error saving product data: " + e.getMessage());
        }
    }


    public void ApproveOrder() {
        List<Order> allOrders = orderInstance.getAllOrders();
        List<Invoice> allInvoices = invoice.getAllInvoices();


        if (allOrders.isEmpty()) {
            System.out.println("No Orders.");
            return;
        }

        System.out.println("Orders containing your products:");
        boolean hasOrdersToApprove = false; // Flag to check if there are orders to approve

        for (Order order : allOrders) {
            boolean hasItemsForSeller = false;

            for (Item item : order.getItems()) {
                if (item.GetSellerName().equals(this.getUSERNAME())) {
                    if (!hasItemsForSeller) {
                        System.out.printf("%-15s%-20s%-10s%-10s%-10s%-20s\n",
                                "Order Number", "Item Name", "Item ID", "Quantity", "Price", "Customer Name");
                        hasItemsForSeller = true;
                    }

                    System.out.printf("%-15s%-20s%-10s%-10s%-10.2f%-20s\n",
                            order.getOrderNumber(), item.getItemName(), item.getItemIdNo(),
                            item.getItemQuantity(), item.getItemPrice(), item.GetCustomerUsername());

                }
                setProfit(item.getItemPrice()*item.getItemQuantity());
                setQuantity(item.getItemQuantity());
            }

            if (hasItemsForSeller) {
                hasOrdersToApprove = true;
            }
        }

        if (!hasOrdersToApprove) {
            System.out.println("You don't have orders to approve.");
            return;
        }

        System.out.println("Enter order number to approve, or '0' to go back:");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            return;
        } else {
            int orderNumberToApprove = Integer.parseInt(input);
            Order orderToApprove = new Order();

            for (Order order : allOrders) {
                if (order.getOrderNumber() == orderNumberToApprove) {
                    orderToApprove = order;
                    break;
                }
            }

            if (orderToApprove != null && !orderToApprove.isOrderStatus("Approved") && !orderToApprove.isOrderStatus("Rejected")) {
                orderToApprove.setOrderStatus("Approved");




                productOrders.add(orderToApprove);
                System.out.println("Order with ID " + orderNumberToApprove + " approved.");

                Invoice invoice = new Invoice(orderToApprove,orderToApprove.getTotalAmount());


                allInvoices.add(invoice);
                allOrders.removeAll(productOrders);
                SellerOrders.addAll(productOrders);


                // Update seller's balance
                this.setProfit(orderToApprove.getTotalAmount());

                // Remove the items in the approved order from the seller's products
                for (Item item : orderToApprove.getItems()) {
                    sellerProducts.remove(item);
                }
            } else {
                System.out.println("Order not found or not eligible for approval.");
            }
        }
    }

    public void setProfit(double profit) {
        Profit = +profit;
    }

    private Item findItemsInUserProduct(String productName) {
        for (Item item : getSellerProducts()) {
            if (item.getItemName().equals(productName)) {
                return item;
            }
        }
        return null;
    }


    public void removeProduct() {

        if (getStatus().equals("Inactive")) {
            System.out.println("You cannot remove products because you are not activated.");
            return;
        }
        List<Item> itemsToRemove = new ArrayList<>();
        List<Item> allItems = itemCollections.getAllItems();
        List<Item> activatedItems = itemCollections.getActivatedItems();
        List<Item> unactivatedItems = itemCollections.getUnactivatedItems();

        if (sellerProducts.isEmpty()) {
            System.out.println("No Items.");
            return;
        }

        System.out.println("All Items:");
        System.out.println("===============");

        printSellerItems();

        System.out.println("Enter Item ID to remove, '1' for all, or '0' to go back:");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            return;
        } else if (input.equals("1")) {
            for (Item item : sellerProducts) {
                itemsToRemove.add(item);
            }

            allItems.removeAll(itemsToRemove);
            activatedItems.removeAll(itemsToRemove);
            unactivatedItems.removeAll(itemsToRemove);
            sellerProducts.removeAll(itemsToRemove);

            System.out.println("All Items removed.");
            return;
        } else {
            try {
                int itemIdToRemove = Integer.parseInt(input);
                Item itemToRemove = null;
                for (Item item : sellerProducts) {
                    if (item.getItemIdNo() == itemIdToRemove) {
                        itemToRemove = item;
                        break;
                    }
                }

                if (itemToRemove != null) {
                    itemsToRemove.add(itemToRemove);

                    allItems.removeAll(itemsToRemove);
                    activatedItems.removeAll(itemsToRemove);
                    unactivatedItems.removeAll(itemsToRemove);
                    sellerProducts.removeAll(itemsToRemove);

                    System.out.println("Item with ID " + itemIdToRemove + " removed.");
                } else {
                    System.out.println("Item with ID " + itemIdToRemove + " not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid user ID, '1' to remove all, or '0' to go back.");
            }
        }



    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = +quantity;
    }

    public void displayAllProducts() {
        if (getStatus().equals("Inactive")) {
            System.out.println("You cannot display products because you are not activated.");
            return;
        }
        printSellerItems();

    }


    public double getProfit() {
        return Profit;
    }

    public void displaySellingStatistics() {
        if (getStatus().equals("Inactive")) {
            System.out.println("You cannot display selling statistics because you are not activated.");
            return;
        }

        double averageRating = getAverageRating();

        System.out.println("Total Profit : " + Profit);
        System.out.println("Total Products Sold: " + Quantity);
        System.out.println("Average Rating: " + averageRating);

    }



    public void addRating(int rating) {
        ratings.add(rating);
    }

    // Method to calculate average rating
    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        return sum / ratings.size();
    }






}
