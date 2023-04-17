package murach.ap;

public class Item implements SQLScripts {
    private String upc;
    private String name;
    private double price;
    private double discount;

    public Item(String upc, String name, double price, double discount) {
        this.upc = upc;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public Item(String upc, String name) {
        this.upc = upc;
        this.name = name;
        price = 0.0;
        discount = 0.0;
    }

    public String getUpc() {
        return upc;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public double getDiscount() {
        return discount;
    }

    public String getInsertIntoDatabaseStatement() {
        return 
        // create a call statement
        "CALL addItem('" + upc + "', '" + name + "', " + price + ", " + discount + " )";
    }

    
}
