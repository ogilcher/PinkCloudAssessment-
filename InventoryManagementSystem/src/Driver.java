/*
    Author: Oliver Gilcher
    Name: Driver
    Date: 2/10/2024
    Desc: Creates the GUI and acts as an executable for the program
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Driver extends JFrame{

    //-- JFrame Variables
    private JLabel itemIDL, nameL, quantityL, priceL, fileNameL, messageL;
    private JTextField itemIDTF, nameTF, quantityTF, priceTF, fileNameTF;
    private JScrollPane messageSP;
    private JTextArea messagesTA;
    private JButton enterB, displayB, updateB, deleteB, loadB, saveB, okayB, cancelB;

    //-- Remaining Variables
    private ItemList iL;
    private String fileName;
    private Item currentItem, found;
    private boolean processingSave, duplicateRecord, processingDelete;
    private Container myCP;
    private String errorMsg, itemID, name, quantity, price;

    //-- Constructor
    public Driver(){

        //-- Fundementals of the GUI - size, location, layout, etc.
        super("Inventory Management");
        setSize(560, 670);
        setLocation(100, 100);
        myCP = getContentPane();
        myCP.setLayout(new FlowLayout());

        //-- Constructing ItemList
        iL = new ItemList();
        iL.loadFromFile("Inventory.txt");
        processingSave = false;
        duplicateRecord = false;
        processingDelete = false;

        //-- Labels, TextFields and Buttons

            // JLabel for Item ID
            itemIDL = new JLabel("Item ID:");
            itemIDL.setFont(new Font("Arial", Font.PLAIN, 30));
            myCP.add(itemIDL);

            // TextField for Item ID
            itemIDTF = new JTextField(20);
            itemIDTF.setFont(new Font("Arial", Font.PLAIN, 20));
            itemIDTF.setText("");
            myCP.add(itemIDTF);

            // JLabel for name
            nameL = new JLabel("Enter name:");
            nameL.setFont(new Font("Arial", Font.PLAIN, 25));
            myCP.add(nameL);

            // TextField for name
            nameTF = new JTextField(20);
            nameTF.setFont(new Font("Arial", Font.PLAIN, 20));
            nameTF.setText("");
            myCP.add(nameTF);

            // JLabel for quantity
            quantityL = new JLabel("Enter quantity:");
            quantityL.setFont(new Font("Arial", Font.PLAIN, 25));
            myCP.add(quantityL);

            // TextField for quantity
            quantityTF = new JTextField(20);
            quantityTF.setFont(new Font("Arial", Font.PLAIN, 20));
            quantityTF.setText("");
            myCP.add(quantityTF);

            // JLabel for price
            priceL = new JLabel("Enter price:");
            priceL.setFont(new Font("Arial", Font.PLAIN, 25));
            myCP.add(priceL);

            // TextField for price
            priceTF = new JTextField(20);
            priceTF.setFont(new Font("Arial", Font.PLAIN, 20));
            priceTF.setText("");
            myCP.add(priceTF);

            // Enter Button
            enterB = new JButton("Enter");
            enterB.setFont(new Font("Arial", Font.PLAIN, 30));
            enterB.addActionListener(new EnterBHandler());
            myCP.add(enterB);

            // Display Button
            displayB = new JButton("Display");
            displayB.setFont(new Font("Arial", Font.PLAIN, 30));
            displayB.addActionListener(new DisplayBHandler());
            myCP.add(displayB);

            // Search Button
            updateB = new JButton("Update");
            updateB.setFont(new Font("Arial", Font.PLAIN, 30));
            updateB.addActionListener(new UpdateBHandler());
            myCP.add(updateB);

            // Delete Button
            deleteB = new JButton("Delete");
            deleteB.setFont(new Font("Arial", Font.PLAIN, 30));
            deleteB.addActionListener(new DeleteBHandler());
            myCP.add(deleteB);

            // JLabel for file name
            fileNameL = new JLabel("File Name:");
            fileNameL.setFont(new Font("Arial", Font.PLAIN, 25));
            myCP.add(fileNameL);

            // TextField for file name
            fileNameTF = new JTextField(20);
            fileNameTF.setFont(new Font("Arial", Font.PLAIN, 20));
            fileNameTF.setText("Inventory.txt");
            myCP.add(fileNameTF);

            // Load Button
            loadB = new JButton("Load");
            loadB.setFont(new Font("Arial", Font.PLAIN, 30));
            loadB.addActionListener(new LoadBHandler());
            myCP.add(loadB);

            // Save Button
            saveB = new JButton("Save");
            saveB.setFont(new Font("Arial", Font.PLAIN, 30));
            saveB.addActionListener(new SaveBHandler());
            myCP.add(saveB);

            // Okay Button
            okayB = new JButton("OK");
            okayB.setFont(new Font("Arial", Font.PLAIN, 30));
            okayB.addActionListener(new OkayBHandler());
            myCP.add(okayB);

            // Cancel Button
            cancelB = new JButton("Cancel");
            cancelB.setFont(new Font("Arial", Font.PLAIN, 30));
            cancelB.addActionListener(new CancelBHandler());
            myCP.add(cancelB);

            // JLabel for messages
            messageL = new JLabel("Messages:");
            messageL.setFont(new Font("Arial", Font.PLAIN, 25));
            myCP.add(messageL);

            messagesTA = new JTextArea("Welcome to the Inventory Management System.", 4, 40);
            messageSP = new JScrollPane(messagesTA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            messageSP.setPreferredSize(new Dimension(500,200));
            messagesTA.setFont(new Font("Arial", Font.PLAIN, 20));
            myCP.add(messageSP);

            setVisible(true);
            addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            });

            adjustButtons(true);
    }

    //-- Clears all the input fields to make them reusable
    private void ClearInputFields(){
        itemIDTF.setText("");
        nameTF.setText("");
        quantityTF.setText("");
        priceTF.setText("");
    }

    //-- Makes the buttons adjustable
    private void adjustButtons(boolean tFValue) {
        saveB.setEnabled(tFValue);
        enterB.setEnabled(tFValue);
        displayB.setEnabled(tFValue);
        updateB.setEnabled(tFValue);
        deleteB.setEnabled(tFValue);
        loadB.setEnabled(tFValue);
        okayB.setEnabled(!tFValue);
        cancelB.setEnabled(!tFValue);
    }

    //-- Resets the adjustable buttons and input fields
    private void reset() {
        adjustButtons(true);
        ClearInputFields();
    }

    //-- Grabbing the user input from the TextFields

        // Item ID input
        private String getUserInputItemID(){

            String userEntered = itemIDTF.getText();
            itemID = userEntered;
            return userEntered;
        }

        // Name input
        private String getUserInputName(){

            String userEntered = nameTF.getText();
            name = userEntered;
            return userEntered;
        }

        // Quantity input
        private String getUserInputQuantity(){

            String userEntered = quantityTF.getText();
            quantity = userEntered;
            return userEntered;
        }

        // Price input
        private String getUserInputPrice(){

            String userEntered = priceTF.getText();
            price = userEntered;
            return userEntered;
        }

    //-- Cast values from inputs
    private boolean validInput() {
        errorMsg = "";
        itemID = getUserInputItemID();
        name = getUserInputName();
        quantity = getUserInputQuantity();
        price = getUserInputPrice();
        return errorMsg.equals("");
    }

    //-- Enter Button
    public class EnterBHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(validInput()) {
                try {
                    currentItem = new Item(Integer.parseInt(itemID), name, Integer.parseInt(quantity), Float.parseFloat(price));
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                found = iL.search(currentItem);
                if (found != null) {
                    duplicateRecord = true;
                    adjustButtons(false);
                    messagesTA.setText("\nRecord already exists:\n" + found.toString()
                            + "\nPress OK to replace old record " +
                            "or Cancel to cancel new entry.\n");
                }else {
                    if(iL.add(currentItem)){
                        messagesTA.setText("\n" + currentItem + " added.\n" +
                                " To see current list, press Display.\n" +
                                " To save this list to file, press Save.\n");
                    }else {
                        messagesTA.setText("\nFailed to add " + currentItem + " to the DB.\n");
                    }
                }
                ClearInputFields();
            }else {
                messagesTA.setText(errorMsg + "\n");
            }
        }
    }

    //-- Save Button
    public class SaveBHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileName = fileNameTF.getText();
            String message = "";
            if(fileName.compareTo("") > 0) {
                File theFile = new File(fileName);
                if(!theFile.exists()) {
                    message = iL.saveToFile(fileName);
                    messagesTA.setText("Data saved to file "+ fileName+".\n"
                            + message + "\n");
                }else if(theFile.isDirectory()){
                    messagesTA.setText("Error: " + fileName + " is a directory. \n");
                }else if(!theFile.canWrite()) {
                    messagesTA.setText("Cannot write data to " + fileName + "\n.");
                }else {
                    adjustButtons(false);
                    processingSave = true;
                    messagesTA.setText("\nPress OK to overwrite file "+
                            fileName + " or press Cancel to cancel save request.\n");
                }
            }else {
                messagesTA.setText("You must enter a file name in order to save a file.");
            }
        }
    }

    //-- Cancel Button
    public class CancelBHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(processingSave) {
                messagesTA.setText("Save request cancelled. "+
                        fileName + " unchanged.\n");
                processingSave = false;
            }else if(duplicateRecord) {
                messagesTA.setText("Information about " + name + " unchanged.\n");
                duplicateRecord = false;
            }else if (processingDelete) {
                messagesTA.setText("Delete request cancelled. \n");
                processingDelete = false;
            }else {
                System.out.println("Cancel Button being handled at inappropriate time " + e.toString());
            }
            reset();
        }
    }

    //-- Okay Button
    public class OkayBHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(processingSave) {
                String errmsg = iL.saveToFile(fileName);
                messagesTA.setText(fileName + " overwritten.\n"
                        + errmsg + "\n");
                processingSave = false;
            }else if (duplicateRecord) {
                if(iL.delete(found.getItemID())) {
                    if(iL.add(currentItem)) {
                        messagesTA.setText("\nRecord for " + currentItem + " changed.\n");
                    }else {
                        messagesTA.setText("\nError in adding new record. " +
                                name + " deleted from DB.\n");
                    }
                }else {
                    messagesTA.setText("\nError in deleting old record. No change in DB.\n");
                }
                duplicateRecord = false;
            }else if(processingDelete) {
                if(iL.delete(found.getItemID())) {
                    messagesTA.setText("The record for " + found + " was deleted.");
                }else {
                    messagesTA.setText("Failure occurred in deleting " + found + ".\n");
                }
                processingDelete = false;
            }else {
                System.out.println("OK button being handled at inappropriate time " + e.toString());
            }
            reset();
        }
    }

    //-- Loads the file
    public class LoadBHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileName = fileNameTF.getText();
            if(fileName.compareTo("") > 0) {
                File theFile = new File(fileName);
                if(!theFile.exists()) {
                    messagesTA.setText(fileName + " does not exist: cannot load data.\n");
                }else if(!theFile.canRead()) {
                    messagesTA.setText("Cannot read from " + fileName + "\n");
                }else {
                    String fromLoad = iL.loadFromFile(fileName);
                    messagesTA.setText("Data loaded from " + fileName+ "\n"
                            + fromLoad + "\n");
                }
                ClearInputFields();
            }else {
                messagesTA.setText("You must enter a file name " +
                        "in order to load a file.");
            }
        }
    }

    //-- Displays all the items in the list
    public class DisplayBHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String entries = iL.printList();
            messagesTA.setText(entries);
        }
    }

    //-- Updates an item's quantity on the list
    public class UpdateBHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            Item foundItem;

            String userInput = itemIDTF.getText();
            int userItemID = Integer.parseInt(userInput);
            if(!userInput.equals("")){
                foundItem = iL.findItemByItemID(userItemID);
                foundItem.setQuantity(Integer.parseInt(quantityTF.getText()));
                messagesTA.setText("Item successfully updated!");
            } else {
                messagesTA.setText("Please give a valid Item ID!");
            }
        }
    }

    //-- Deletes an item from the list
    public class DeleteBHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            // Variables
            String userInput = itemIDTF.getText();

            if(!userInput.equals("")){

                int itemID = Integer.parseInt(userInput);
                boolean response = iL.delete(itemID);
                if(response){
                    messagesTA.setText("Item has successfully been deleted.");
                } else {
                    messagesTA.setText("Item has NOT been deleted.");
                }
                return;
            }else {
                messagesTA.setText("You must enter an ItemID to delete.");
            }
        }
    }

    //-- Executes the program
    public static void main(String[] args){
        Driver myApp = new Driver();
    }
}
