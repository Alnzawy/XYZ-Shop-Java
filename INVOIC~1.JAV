

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Invoice {

    private int invoiceNumber;
    private List<Order> orders;

    private LocalDate date; // Using LocalDate for date

    private double totalAmount;




    private static List<Invoice> allInvoices = new ArrayList<>();
    private static Invoice instance = null;

    private List<Integer> InvoicesIds =  new ArrayList<>();
    private Random random = new Random();
    private int Invoice_LENGTH = 5;

    private int generateUniqueinvoiceNumber() {
        int id;
        do {
            id = random.nextInt((int) Math.pow(10, Invoice_LENGTH));
        } while (InvoicesIds.contains(id));
        InvoicesIds.add(id);
        return id;
    }


    public static Invoice getInstance() {
        if (instance == null) {
            instance = new Invoice();
        }
        return instance;
    }

    public static List<Invoice> getAllInvoices() {
        return allInvoices;
    }



    public Invoice(Order order,double totalAmount) {
        this.invoiceNumber = generateUniqueinvoiceNumber();
        this.date = LocalDate.now();
        this.totalAmount = order.getTotalAmount();
        this.orders = new ArrayList<>();
        this.orders.add(order);
        this.totalAmount = totalAmount;
    }


    public Invoice() {
    }


    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }



    public List<Order> getOrders() {
        return orders;
    }
}

