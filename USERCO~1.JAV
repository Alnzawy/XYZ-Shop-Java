import java.util.ArrayList;
import java.util.List;

public class UserCollections {

    private static List<User> allUsers;
    private static List<Seller> sellerUsers;
    private static List<Customer> customerUsers;
    private static List<User> activatedUsers;
    private static List<User> unactivatedUsers;

    private static UserCollections instance = null;


    public UserCollections() {
        this.allUsers = new ArrayList<>();
        this.sellerUsers = new ArrayList<>();
        this.customerUsers = new ArrayList<>();
        this.activatedUsers = new ArrayList<>();
        this.unactivatedUsers = new ArrayList<>();
    }
    public static UserCollections getInstance() {
        if (instance == null) {
            instance = new UserCollections();
        }
        return instance;
    }

    // print allusers in allusers array
    public void printAllUsers() {
        if (allUsers.isEmpty()) {
            System.out.println("No users.");
            return;
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("                                ALL Users:");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf(" | %-1s |    | %-3s |    | %-15s |    | %-8s |    | %-8s |%n", "NO", "ID", "USERNAME", "TYPE", "STATUS");
        System.out.println("------------------------------------------------------------------------");

        for (User user : allUsers) {
            System.out.printf(" | %-1d |     | %-3s |    | %-15s |    | %-8s |    | %-8s |%n",
                    (allUsers.indexOf(user) + 1),
                    user.getUserID(),
                    user.getUSERNAME(),
                    user.getUserType(),
                    user.getStatus());
        }
        System.out.println("-------------------------------------------------------------------------");
    }
    public void printAllSellers() {
        if (sellerUsers.isEmpty()) {
            System.out.println("No users.");
            return;
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("                                ALL Sellers:");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf(" | %-1s |    | %-3s |    | %-15s |    | %-8s |    | %-8s |%n", "NO", "ID", "USERNAME", "TYPE", "STATUS");
        System.out.println("------------------------------------------------------------------------");

        for (User user : sellerUsers) {
            System.out.printf(" | %-1d |     | %-3s |    | %-15s |    | %-8s |    | %-8s |%n",
                    (sellerUsers.indexOf(user) + 1),
                    user.getUserID(),
                    user.getUSERNAME(),
                    user.getUserType(),
                    user.getStatus());
        }
        System.out.println("-------------------------------------------------------------------------");
    }
    public void printAllCustomers() {
        if (customerUsers.isEmpty()) {
            System.out.println("No users.");
            return;
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("                                ALL Customers:");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf(" | %-1s |    | %-3s |    | %-15s |    | %-8s |    | %-8s |%n", "NO", "ID", "USERNAME", "TYPE", "STATUS");
        System.out.println("------------------------------------------------------------------------");

        for (User user : customerUsers) {
            System.out.printf(" | %-1d |     | %-3s |    | %-15s |    | %-8s |    | %-8s |%n",
                    (customerUsers.indexOf(user) + 1),
                    user.getUserID(),
                    user.getUSERNAME(),
                    user.getUserType(),
                    user.getStatus());
        }
        System.out.println("-------------------------------------------------------------------------");
    }
    public void printActiveUsers() {
        if (activatedUsers.isEmpty()) {
            System.out.println("No users.");
            return;
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("                                Active Users:");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf(" | %-1s |    | %-3s |    | %-15s |    | %-8s |    | %-8s |%n", "NO", "ID", "USERNAME", "TYPE", "STATUS");
        System.out.println("------------------------------------------------------------------------");

        for (User user : activatedUsers) {
            System.out.printf(" | %-1d |     | %-3s |    | %-15s |    | %-8s |    | %-8s |%n",
                    (activatedUsers.indexOf(user) + 1),
                    user.getUserID(),
                    user.getUSERNAME(),
                    user.getUserType(),
                    user.getStatus());
        }
        System.out.println("-------------------------------------------------------------------------");
    }
    public void printInactiveUsers() {
        if (unactivatedUsers.isEmpty()) {
            System.out.println("No users.");
            return;
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("                                UnActive Users:");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf(" | %-1s |    | %-3s |    | %-15s |    | %-8s |    | %-8s |%n", "NO", "ID", "USERNAME", "TYPE", "STATUS");
        System.out.println("------------------------------------------------------------------------");

        for (User user : unactivatedUsers) {
            System.out.printf(" | %-1d |     | %-3s |    | %-15s |    | %-8s |    | %-8s |%n",
                    (unactivatedUsers.indexOf(user) + 1),
                    user.getUserID(),
                    user.getUSERNAME(),
                    user.getUserType(),
                    user.getStatus());
        }
        System.out.println("-------------------------------------------------------------------------");
    }


    // Method to add a user to the appropriate lists
    public void addUser(User user) {
        allUsers.add(user);
        unactivatedUsers.add(user); // Initially, all users are added to unactivatedUsers
        if (user.getUserType().equals("Seller")) {
            sellerUsers.add((Seller) user);
        } else if (user.getUserType().equals("Customer")) {
            customerUsers.add((Customer) user);
        }
    }


    public List<User> getAllUsers() {
        return allUsers;
    }
    public List<Seller> getSellerUsers() {
        return sellerUsers;
    }
    public List<Customer> getCustomerUsers() {
        return customerUsers;
    }
    public List<User> getActivatedUsers() {
        return activatedUsers;
    }
    public List<User> getUnactivatedUsers() {
        return unactivatedUsers;
    }


    public void setActivatedUsers(List<User> ActivatedUSERS) {
        this.activatedUsers.addAll(ActivatedUSERS);
    }


    }



