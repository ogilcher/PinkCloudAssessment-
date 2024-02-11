import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class ItemList {

    //-- Instance Variables
    LinkedList<Item> list = new LinkedList<>();

    //-- Adds an item to the file
    public boolean add(Item i){

        // if the list contains this person, add them, otherwise return false
        if(!list.contains(i)){
            list.add(i);
            return true;
        } else {
            return false;
        }
    }

    //-- Searches for an entry within the file
    public Item search(Item i){

        // If the list contains this item, return it, otherwise return null.
        if(list.contains(i)){
            return i;
        } else {
            return null;
        }
    }

    //-- Saves an entry to the file
    public String saveToFile(String fileName){

        // variables
        String messageFromSave = "";

        // try catch block when handling writing the object to the file
        try{
            ObjectOutputStream oOS = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
            for(int i = 0; i < list.size(); i++) {
                oOS.writeObject(list.get(i));
            }
            oOS.flush();
            oOS.close();

        }catch(Exception e){
            messageFromSave = e.toString();
        }

        // return a message to the GUI that would tell the user whether this function was completed successfully
        return messageFromSave;
    }

    //-- Loads informatiom the file into the Program
    public String loadFromFile(String fileName){

        // variables
        String messageFromLoad = "";

        // try catch block when handling loading the text from the file
        try{

            ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(fileName));
            while(oIS.available() > -1) {
                Item fromFile = (Item)(oIS.readObject());
                Item found = search(fromFile);
                if(found == null) {
                    if(add(fromFile)) {
                        messageFromLoad += fromFile + "\n";
                    }else {
                        messageFromLoad += fromFile + " not successfully added to DB.\n";
                    }
                }else {
                    messageFromLoad += found + " already in the DB.\n";
                }
            }
            oIS.close();

        // dual catches because we want to make sure that an EOFException doesn't bog up our return statement
        }catch(EOFException ignored){
        }catch(Exception e){
            messageFromLoad += e;
        }

        // returns a message to the GUI that would tell the user whether this function was completed successfully
        return messageFromLoad;

    }

    //-- Deletes an entry in the list by its itemID
    public boolean delete(int itemID){

        // Variables
        boolean isDeleted = false;

        // Iterate through the list, find the item, and remove it if found
        for(Item l : list){
            if(l.getItemID() == itemID){
                list.remove(l);
                isDeleted = true;
            }
        }

        // Return following
        return isDeleted;
    }

    //-- Returns a string with the entire list of items
    public String printList(){

        // Variables
        String toReturn = "";

        // Iterate through the list to add on the toStrings of all the items in the list
        for(Item l : list){
            toReturn += "/n" + l.toString();
        }

        // Return following
        return toReturn;
    }

    //-- Searches the list for an item by its itemID. Return either the item's toString or a message that it wasn't found
    public Item findItemByItemID(int itemID){

        // Variables
        Item item = null;

        // Iterate through the list to find the item
        for(Item l : list){
            if(l.getItemID() == itemID){
                item = l;
            }
        }

        // Return following
        return item;
    }
}
