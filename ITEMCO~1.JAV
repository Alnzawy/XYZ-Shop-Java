
import java.util.ArrayList;

import java.util.List;


public class ItemCollections {
    private List<Item> allItems;
    private List<Item> activatedItems;
    private List<Item> unactivatedItems;

    private static ItemCollections instance = null;

    public ItemCollections() {
        this.allItems = new ArrayList<>();
        this.activatedItems = new ArrayList<>();
        this.unactivatedItems = new ArrayList<>();
    }

    public static ItemCollections getInstance() {
        if (instance == null) {
            instance = new ItemCollections();
        }
        return instance;
    }



    public List<Item> getAllItems() {
        return allItems;
    }
    public List<Item> getActivatedItems() {
        return activatedItems;
    }
    public List<Item> getUnactivatedItems() {
        return unactivatedItems;
    }


    public void setActivatedItems(List<Item> ACTIVATEDItems) {
        this.activatedItems.addAll(ACTIVATEDItems);
    }






    public void printAllItems() {
        if (allItems.isEmpty()) {
            System.out.println("No Items.");
            return;
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("ALL Items:");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-2s |     | %-23s |     |%-5s |     |%-10s |     |%-5s |      |%-15s |       |%-10s |\n",
                "NO", "Item Name", "Item ID", "Item Price", "Quantity", "Seller", "Status");

        for (Item item : allItems) {
            System.out.printf("| %-2d |     | %-23s |     |%-7s |     |$%-9.2f |     |%-8s |      |%-15s |       |%-10s |\n",
                    allItems.indexOf(item) + 1, item.getItemName(), item.getItemIdNo(), item.getItemPrice(),
                    item.getItemQuantity(), item.GetSellerName(), item.getStatus());
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
    }
    public void PrintActivatedItems() {
        if (activatedItems.isEmpty()) {
            System.out.println("No Items.");
            return;
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Active Items:");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-2s |     | %-23s |     |%-5s |     |%-10s |     |%-5s |      |%-15s |       |%-10s |\n",
                "NO", "Item Name", "Item ID", "Item Price", "Quantity", "Seller", "Status");

        for (Item item : activatedItems) {
            System.out.printf("| %-2d |     | %-23s |     |%-7s |     |$%-9.2f |     |%-8s |      |%-15s |       |%-10s |\n",
                    activatedItems.indexOf(item) + 1, item.getItemName(), item.getItemIdNo(), item.getItemPrice(),
                    item.getItemQuantity(), item.GetSellerName(), item.getStatus());
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
    }
    public void printInactiveItems() {
        if (unactivatedItems.isEmpty()) {
            System.out.println("No Items.");
            return;
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("UnActive Items:");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-2s |     | %-23s |     |%-5s |     |%-10s |     |%-5s |      |%-15s |       |%-10s |\n",
                "NO", "Item Name", "Item ID", "Item Price", "Quantity", "Seller", "Status");

        for (Item item : unactivatedItems) {
            System.out.printf("| %-2d |     | %-23s |     |%-7s |     |$%-9.2f |     |%-8s |      |%-15s |       |%-10s |\n",
                    unactivatedItems.indexOf(item) + 1, item.getItemName(), item.getItemIdNo(), item.getItemPrice(),
                    item.getItemQuantity(), item.GetSellerName(), item.getStatus());
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
    }


    // Method to add a user to the appropriate lists
    public void addItem(Item item) {
        allItems.add(item);
        unactivatedItems.add(item); // Initially, all users are added to unactivatedUsers
    }

    // in main
    public void addItemsFromSeller(List<Item> sellerItems) {
        allItems.addAll(sellerItems);
        unactivatedItems.addAll(sellerItems);
    }





}