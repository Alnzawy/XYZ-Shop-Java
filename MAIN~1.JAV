import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        User Ahmed = new Customer("Ahmed", "AA12345@");
        User Basem = new Customer("Basem", "Basem99");


        User Abdulrahman = new Customer("Abdulrahman", "Abdulrahman1");
        Abdulrahman.setActive(true);

        ( (Customer) Abdulrahman ).setBalance(50000);

        User Abdullah = new Customer("Abdullah", "Xa09@@1");
        Abdullah.setActive(true);

        ( (Customer) Abdullah ).setBalance(90000);

        User Jawad = new Customer("JAWAD", "JA10@#100");
        Jawad.setActive(true);
        ( (Customer) Jawad ).setBalance(45000);


        Seller Khaled = new Seller("Khaled_11", "Khaled10");
        Khaled.setActive(true);
        List<Item> Khaled_Items = new ArrayList<>();

        Khaled_Items.add(new Item("iPhone 15 ", 250, 2299.99, Khaled));
        Khaled_Items.add(new Item("Galaxy S23 ", 25, 1509.99, Khaled));
        Khaled_Items.add(new Item("OnePlus 11", 18, 1200, Khaled));
        Khaled_Items.add(new Item("Xiaomi 13T ", 15, 770.50, Khaled));
        Khaled.setSellerProducts(Khaled_Items);


        Seller Faisal = new Seller("Faisal_1", "1FAISAL1");
        Faisal.setActive(true);
        List<Item> Faisal_Items = new ArrayList<>();

        Faisal_Items.add(new Item("Phone Holder", 50, 19.99, Faisal));
        Faisal_Items.add(new Item("Seat Covers", 35, 79.99, Faisal));
        Faisal_Items.add(new Item("Portable Vacuum Cleaner", 10, 139.99, Faisal));
        Faisal_Items.add(new Item("Car Battery Charger", 18, 79.99, Faisal));
        Faisal_Items.add(new Item("Dashboard Camera", 15, 229.99, Faisal));
        Faisal.setSellerProducts(Faisal_Items);

        Seller Abdulaziz = new Seller("Abdul-Aziz", "ZA1186@@1");
        Abdulaziz.setActive(true);
        List<Item> Abdulaziz_Items = new ArrayList<>();

        Abdulaziz_Items.add(new Item("LG TV", 50, 4850, Abdulaziz));
        Abdulaziz_Items.add(new Item("Laptop", 25, 3200, Abdulaziz));
        Abdulaziz_Items.add(new Item("Wireless Mouse", 60, 425, Abdulaziz));
        Abdulaziz_Items.add(new Item("Mechanical Keyboard", 40, 120, Abdulaziz));
        Abdulaziz_Items.add(new Item("Computer Monitor", 35, 900, Abdulaziz));

        Abdulaziz.setSellerProducts(Abdulaziz_Items);


        Seller Yahya = new Seller("YAHYA", "Y1666@09");
        Yahya.setActive(true);
        List<Item> Yahya_Items = new ArrayList<>();

        Yahya_Items.add(new Item("Wireless Headphones", 100, 150, Yahya));
        Yahya_Items.add(new Item("Digital Camera", 30, 450, Yahya));
        Yahya_Items.add(new Item("Bluetooth Speaker", 80, 80, Yahya));
        Yahya_Items.add(new Item("Smartphone", 50, 800, Yahya));
        Yahya_Items.add(new Item("Gaming Laptop", 15, 1500, Yahya));
        Yahya_Items.add(new Item("Smartwatch", 60, 200, Yahya));
        Yahya_Items.add(new Item("External Hard Drive", 25, 100, Yahya));
        Yahya_Items.add(new Item("Drone", 10, 700, Yahya));
        Yahya_Items.add(new Item("Tablet", 40, 300, Yahya));
        Yahya_Items.add(new Item("Portable Charger", 70, 30, Yahya));

        Yahya.setSellerProducts(Yahya_Items);


        UserCollections userCollections = UserCollections.getInstance();
        userCollections.addUser(Ahmed);
        userCollections.addUser(Basem);
        userCollections.addUser(Abdulrahman);
        userCollections.addUser(Abdullah);
        userCollections.addUser(Jawad);
        userCollections.addUser(Khaled);
        userCollections.addUser(Faisal);
        userCollections.addUser(Abdulaziz);
        userCollections.addUser(Yahya);

        List<User> activatedUsers = new ArrayList<>();
        activatedUsers.add(Abdulrahman);
        activatedUsers.add(Abdullah);
        activatedUsers.add(Jawad);
        activatedUsers.add(Khaled);
        activatedUsers.add(Faisal);
        activatedUsers.add(Abdulaziz);
        activatedUsers.add(Yahya);

        userCollections.getUnactivatedUsers().removeAll(activatedUsers);
        userCollections.setActivatedUsers(activatedUsers);


        ItemCollections itemCollections = ItemCollections.getInstance();
        itemCollections.addItemsFromSeller(Khaled_Items);
        itemCollections.addItemsFromSeller(Faisal_Items);
        itemCollections.addItemsFromSeller(Abdulaziz_Items);
        itemCollections.addItemsFromSeller(Yahya_Items);

        List<Item> approvedItems = new ArrayList<>();
        approvedItems.addAll(Khaled_Items);
        approvedItems.addAll(Faisal_Items);
        approvedItems.addAll(Abdulaziz_Items);
        approvedItems.addAll(Yahya_Items);

        for (Item item : approvedItems) {
            item.setApproved(true);
        }

        itemCollections.getActivatedItems().addAll(approvedItems);
        itemCollections.getUnactivatedItems().removeAll(approvedItems);



        Admin admin = new Admin("AA", "AA");
        // no user can register with admin credentials
        XYZShop X = new XYZShop(admin);
        X.run();





    }
}
