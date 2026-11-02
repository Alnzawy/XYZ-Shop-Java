import java.util.*;

public class Admin {

    private String AdminUSERNAME;
    private String AdminPASSWORD;

    UserCollections userCollections = UserCollections.getInstance();
    ItemCollections itemCollections = ItemCollections.getInstance();

    Scanner scanner = new Scanner(System.in);



    public Admin(String adminUserName, String adminPass) {
        AdminUSERNAME = adminUserName;
        AdminPASSWORD = adminPass;
    }

    public void AdminInterface() {
        while (true) {
            System.out.println("            Admin Interface:");
            System.out.println("==========================================");
            System.out.println("(1) Activate User ");
            System.out.println("(2) Display Inactive Users");
            System.out.println("(3) Display All the products");
            System.out.println("(4) Display All users");
            System.out.println("(5) Display Customers");
            System.out.println("(6) Display Sellers");
            System.out.println("(7) Approve products");
            System.out.println("(8) Remove Users");
            System.out.println("(9) Remove Products");
            System.out.println("(10) Display Statistics");
            System.out.println("(11) Display and change Admin Information");
            System.out.println("(12) Exit");
            System.out.println("==========================================");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    activateUser();
                    break;
                case "2":
                    userCollections.printInactiveUsers();
                    break;
                case "3":
                    itemCollections.printAllItems();
                    break;
                case "4":
                    userCollections.printAllUsers();
                    break;
                case "5":
                    userCollections.printAllCustomers();
                    break;
                case "6":
                    userCollections.printAllSellers();
                    break;
                case "7":
                    approveProduct();
                    break;
                case "8":
                    removeUser();
                    break;
                case "9":
                    removeProduct();
                    break;
                case "10":
                    displayStatistics();
                    break;
                case "11":
                    AdminInfo();
                    break;
                case "12":
                    System.out.println("Exiting Admin Panel.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 12.");
                    break;
            }
        }
    }

    public void activateUser() {

        List<User> usersToActivate = new ArrayList<>();
        List<User> inactiveUsers = userCollections.getUnactivatedUsers();

        if (inactiveUsers.isEmpty()) {
            System.out.println("No Inactive users.");
            return;
        }

        boolean repeat;
        do {
            repeat = false;

            System.out.println("Inactive Users:");
            System.out.println("===============");
            userCollections.printInactiveUsers();

            System.out.println("Enter user ID to activate, '1' to activate all, or '0' to go back:");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                return;
            } else if (input.equals("1")) {
                for (User user : inactiveUsers) {
                    if (!user.isActive()) {
                        user.setActive(true);
                        usersToActivate.add(user);
                    }
                }

                inactiveUsers.removeAll(usersToActivate);
                userCollections.setActivatedUsers(usersToActivate);

                System.out.println("All users activated.");
            } else {
                try {
                    int userIdToActivate = Integer.parseInt(input);
                    User userToActivate = null;

                    for (User user : inactiveUsers) {
                        if (user.getUserID() == userIdToActivate) {
                            userToActivate = user;
                            break;
                        }
                    }

                    if (userToActivate != null && !userToActivate.isActive()) {
                        userToActivate.setActive(true);

                        usersToActivate.add(userToActivate);
                        inactiveUsers.remove(userToActivate);

                        userCollections.setActivatedUsers(usersToActivate);

                        System.out.println("User with ID " + userIdToActivate + " activated.");
                    } else {
                        System.out.println("User with ID " + userIdToActivate + " is already active or not found.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid user ID, '1' to activate all, or '0' to go back.");
                    repeat = true;
                }
            }
        } while (repeat);
    }

    public void removeUser() {
        List<User> usersToRemove = new ArrayList<>();
        List<Item> itemsToRemove = new ArrayList<>();
        List<User> allUsers = userCollections.getAllUsers();
        List<Customer> customerUsers = userCollections.getCustomerUsers();
        List<Seller> sellerUsers = userCollections.getSellerUsers();
        List<User> activatedUsers = userCollections.getActivatedUsers();
        List<User> unactivatedUsers = userCollections.getUnactivatedUsers();
        List<Item> allItems = itemCollections.getAllItems();
        List<Item> activatedItems = itemCollections.getActivatedItems();
        List<Item> unactivatedItems = itemCollections.getUnactivatedItems();

        if (allUsers.isEmpty()) {
            System.out.println("No users.");
            return;
        }

        boolean repeat;
        do {
            repeat = false;
            System.out.println("All Users:");
            System.out.println("===============");

            userCollections.printAllUsers();

            System.out.println("Enter user ID to remove, '1' for all, or '0' to go back:");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                return;
            } else if (input.equals("1")) {
                for (User user : allUsers) {
                    usersToRemove.add(user);
                    if (user.getUserType().equals("Seller")) {
                        itemsToRemove.addAll(((Seller) user).getSellerProducts());
                    }
                }

                allUsers.removeAll(usersToRemove);
                customerUsers.removeAll(usersToRemove);
                sellerUsers.removeAll(usersToRemove);
                activatedUsers.removeAll(usersToRemove);
                unactivatedUsers.removeAll(usersToRemove);

                // Remove items from all items and activated items lists
                allItems.removeAll(itemsToRemove);
                activatedItems.removeAll(itemsToRemove);

                System.out.println("All users and their items removed.");
                return;
            } else {
                try {
                    int userIdToRemove = Integer.parseInt(input);
                    User userToRemove = null;
                    for (User user : allUsers) {
                        if (user.getUserID() == userIdToRemove) {
                            userToRemove = user;
                            if (user.getUserType().equals("Seller")) {
                                itemsToRemove.addAll(((Seller) user).getSellerProducts());
                            }
                            break;
                        }
                    }

                    if (userToRemove != null) {
                        usersToRemove.add(userToRemove);
                        allUsers.remove(userToRemove);
                        customerUsers.removeAll(usersToRemove);
                        sellerUsers.removeAll(usersToRemove);
                        activatedUsers.removeAll(usersToRemove);
                        unactivatedUsers.removeAll(usersToRemove);

                        // Remove items from all items and activated items lists
                        allItems.removeAll(itemsToRemove);
                        activatedItems.removeAll(itemsToRemove);

                        System.out.println("User with ID " + userIdToRemove + " and their items removed.");
                    } else {
                        System.out.println("User with ID " + userIdToRemove + " not found.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid user ID, '1' to remove all, or '0' to go back.");
                    repeat = true;
                }
            }
        } while (repeat);
    }

    public void approveProduct() {

        List<Item> itemsToActivate = new ArrayList<>();
        List<Item> inactiveItems = itemCollections.getUnactivatedItems();

        if (inactiveItems.isEmpty()) {
            System.out.println("No Inactive Items.");
            return;
        }

        boolean repeat;
        do {
            repeat = false;
            System.out.println("Inactive Items:");
            System.out.println("===============");

            itemCollections.printInactiveItems();

            System.out.println("Enter Item ID to activate, '1' to activate all, or '0' to go back:");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                return;
            } else if (input.equals("1")) {
                for (Item item : inactiveItems) {
                    if (!item.isApproved()) {
                        item.setApproved(true);
                        itemsToActivate.add(item);
                    }
                }
                // remove from inactive arraylist
                inactiveItems.removeAll(itemsToActivate);
                // ADD to activate array
                itemCollections.setActivatedItems(itemsToActivate);

                System.out.println("All Items activated.");

            } else {
                try {
                    int itemIdToActivate = Integer.parseInt(input);
                    Item itemToActivate = null;

                    for (Item item : inactiveItems) {
                        if (item.getItemIdNo() == itemIdToActivate) {
                            itemToActivate = item;
                            break;
                        }
                    }

                    if (itemToActivate != null && !itemToActivate.isApproved()) {
                        itemToActivate.setApproved(true);

                        itemsToActivate.add(itemToActivate);

                        inactiveItems.remove(itemToActivate);

                        itemCollections.setActivatedItems(itemsToActivate);

                        System.out.println("Item with ID " + itemIdToActivate + " activated.");
                    } else {
                        System.out.println("Item with ID " + itemIdToActivate + " is already active or not found.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid user ID, '1' to activate all, or '0' to go back.");
                    repeat = true;

                }
            }
        }
        while (repeat);
    }

    public void removeProduct() {

        List<Item> itemsToRemove = new ArrayList<>();
        List<Item> allItems = itemCollections.getAllItems();
        List<Item> activatedItems = itemCollections.getActivatedItems();
        List<Item> unactivatedItems = itemCollections.getUnactivatedItems();

        if (allItems.isEmpty()) {
            System.out.println("No Items.");
            return;
        }

        boolean repeat;
        do {
            repeat = false;
            System.out.println("All Items:");
            System.out.println("===============");

            itemCollections.printAllItems();

            System.out.println("Enter Item ID to remove, '1' for all, or '0' to go back:");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                return;
            } else if (input.equals("1")) {
                for (Item item : allItems) {
                    itemsToRemove.add(item);
                }
                allItems.removeAll(itemsToRemove);
                activatedItems.removeAll(itemsToRemove);
                unactivatedItems.removeAll(itemsToRemove);
                System.out.println("All Items removed.");
                return;
            } else {
                try {
                    int itemIdToRemove = Integer.parseInt(input);
                    Item itemToRemove = null;
                    for (Item item : allItems) {
                        if (item.getItemIdNo() == itemIdToRemove) {
                            itemToRemove = item;
                            break;
                        }
                    }

                    if (itemToRemove != null) {
                        itemsToRemove.add(itemToRemove);
                        allItems.remove(itemToRemove);
                        activatedItems.removeAll(itemsToRemove);
                        unactivatedItems.removeAll(itemsToRemove);
                        System.out.println("Item with ID " + itemIdToRemove + " removed.");
                    } else {
                        System.out.println("Item with ID " + itemIdToRemove + " not found.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid user ID, '1' to remove all, or '0' to go back.");
                    repeat = true;
                }
            }
        }
        while (repeat);
    }

    public void displayStatistics() {

        int activeUsers = userCollections.getActivatedUsers().size();
        int inactiveUsers = userCollections.getUnactivatedUsers().size();
        int totalItems = itemCollections.getAllItems().size();
        int approvedItems = itemCollections.getActivatedItems().size();

        System.out.println("Total Active Users: " + activeUsers);
        System.out.println("Total Inactive Users: " + inactiveUsers);
        System.out.println("Total Items: " + totalItems);
        System.out.println("Total Approved Items: " + approvedItems);
        System.out.println("---------------------------------------");

        List<Seller> allSellers = userCollections.getSellerUsers();

        // Sort the sellers based on total profit in descending order
        allSellers.sort(Comparator.comparingDouble(Seller::getProfit).reversed());

        int count = 0;

        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Top 3 Sellers:");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-2s |     | %-23s |     |%-5s |%n",
                "NO", "Seller Name", "Total Profit");

        for (Seller seller : allSellers) {
            if (count < 3) {
                System.out.printf("| %-2d |     | %-23s |     |%-5s |%n",
                        (count + 1), seller.getUSERNAME(), seller.getProfit());
                count++;
            } else {
                break; // Stop after printing the top 3 sellers
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public void AdminInfo(){

        System.out.println("===================================");
        System.out.println("Admin USERNAME: " + getAdminUSERNAME());
        System.out.println("Admin PASSWORD: " + getAdminPASSWORD());
        System.out.println("===================================");

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Do you want to change Your Information press \n 1) to change username \n 2) to change password \n 3) to Exit");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter your new USERNAME: ");
                    setAdminUserName(in.nextLine());
                    System.out.println("New username is : " + getAdminUSERNAME());
                    break;
                case "2":
                    System.out.print("Enter your new PassWord: ");
                    setAdminPass(in.nextLine());
                    System.out.println("New username is : " + getAdminPASSWORD());
                    break;
                case "3":
                    return; // Exit the method
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public String getAdminUSERNAME() {
        return AdminUSERNAME;
    }
    public String getAdminPASSWORD() {
        return AdminPASSWORD;
    }
    public void setAdminUserName(String adminUserName) {
        AdminUSERNAME = adminUserName;
    }
    public void setAdminPass(String adminPass) {
        AdminPASSWORD = adminPass;
    }


}




