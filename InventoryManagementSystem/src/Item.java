import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable, Comparable<Item>{

    //-- Instance Variables
    private int itemID;
    private String name;
    private int quantity;
    private float price;
    private static int numItems = 0;

    //-- Constructors
    Item(int itemID, String name, int quantity, float price){
        this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    //-- Getters & Setters
    public int getItemID() {return itemID;}
    public void setItemID(int itemID) {this.itemID = itemID;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public float getPrice() {return price;}
    public void setPrice(float price) {this.price = price;}
    public static int getNumItems() {return numItems;}
    public static void setNumItems(int numItems) {Item.numItems = numItems;}

    //-- Equals & HashCode
    @Override
    public int hashCode(){
        return Objects.hash(itemID);
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null) return true;
        if (getClass() != obj.getClass()) return false;
        Item other = (Item) obj;
        return Objects.equals(itemID, other.itemID);
    }

    //-- Comparable
    public int compareTo(Item o) {
        return this.getName().compareTo(o.getName());
    }

    //-- ToString
    @Override
    public String toString(){
        return "[" + itemID + "] " + name + " | " + quantity + " | $" + price;
    }
}
