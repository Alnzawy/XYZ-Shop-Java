import java.util.*;

public class Item {

    private String itemName;
    private int itemQuantity;
    private double itemPrice;
    private boolean approved;
    private int itemIdNo;

    private Seller seller;
    private Customer customer;






    private List<Integer> itemIds =  new ArrayList<>();
    private Random random = new Random();
    private int ID_LENGTH = 5;

    private int generateUniqueID() {
        int id;
        do {
            id = random.nextInt((int) Math.pow(10, ID_LENGTH));
        } while (itemIds.contains(id));
        itemIds.add(id);
        return id;
    }


    // Constructor to create a new item with a unique ID
    public Item(String itemName, int itemQuantity, double itemPrice, Seller seller) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.approved = false; // Initially not approved
        this.itemIdNo = generateUniqueID(); // Generate a unique ID
        this.seller = seller;
    }

    // Constructor to create a new item with an existing ID
    public Item(String itemName, int itemQuantity, double itemPrice, Seller seller, int itemId,Customer customer) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
         this.itemIdNo = itemId;
         this.seller = seller;
         this.customer = customer;
     }
    public String GetSellerName(){
        return seller.getUSERNAME();
    }
    public String GetCustomerUsername(){
        return customer.getUSERNAME();
    }














    public Seller getSeller() {
        return seller;
    }
    public Customer getCustomer() {
        return customer;
    }


    public String getStatus() {
        if (approved == true) {
            return "Active";
        } else {
            return "Inactive";
        }
    }



    public int getItemIdNo() {
        return itemIdNo;
    }



    // Getters
    public String getItemName() {
        return itemName;
    }




    public int getItemQuantity() {
        return itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }




    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }




    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}




