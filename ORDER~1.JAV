import java.time.LocalDate;
import java.util.*;

public class Order implements Payable {
    private String OrderStatus;
    private double totalAmount;
    private List<Order> allOrders;
    private List<Item> items;
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private int OrderNumber;
    private List<Integer> UsedOrderNumber = new ArrayList<>();
    private   Random random = new Random();
    private   int OrderNumber_LENGTH = 3;



    public  int generateUniqueOrderNumber(){
        int id;
        do {
            id = random.nextInt((int) Math.pow(10, OrderNumber_LENGTH));
        } while (UsedOrderNumber.contains(id));
        UsedOrderNumber.add(id);
        return id;
    }


    private static Order instance = null;

    public static Order getInstance() {
        if (instance == null) {
            instance = new Order();
        }
        return instance;
    }



    public Order(List<Item> items) {
        this.OrderNumber = generateUniqueOrderNumber();
        this.items = new ArrayList<>();  // Initialize the items list
        this.totalAmount = calculateOrderTotal(this);
        this.OrderStatus = "pending";
        this.items.addAll(items);  // Add the provided items to the list
    }


    public Order() {
        this.OrderNumber = generateUniqueOrderNumber();
        this.totalAmount = 0;
        this.OrderStatus = "pending";
        this.allOrders = new ArrayList<>(); // Initialize the list here
    }


    public boolean isOrderStatus(String orderStatus) {
        return this.OrderStatus.equals(orderStatus);
    }




    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public int getOrderNumber() {
        return OrderNumber;
    }




    public double getTotalAmount() {
        return totalAmount;
    }




    public List<Item> getItems() {
        return items;
    }


    public void addOrder(Order order) {
        allOrders.add(order);
    }

    public List<Order> getAllOrders() {
        return allOrders;
    }




    @Override
    public double calculateOrderTotal(Order order) {
        double total = 0.0;
        for (Item item : order.getItems()) {
            total += item.getItemPrice() * item.getItemQuantity();
        }
        this.totalAmount =total;
        return total;
    }








}