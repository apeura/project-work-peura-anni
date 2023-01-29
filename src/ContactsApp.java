import java.util.*;
import java.util.ArrayList;
import java.io.Console; 
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
/**
* The class ContactsApp contains methods relating to storing, accessing and
* editing contact information 
*
* @author Anni Peura
*/
public class ContactsApp {
    static Console c = System.console();
    static private ArrayList<Person> contactDetails = new ArrayList<Person>();
    /**
    * main method runs methods to synch up information, present main menu
    * and take user's initial feed to use the application. if no text file
    * exists one is created
    *
    * @param args default parameter for main method
    */
    public static void main (String[] args) {
        Path filePath = Paths.get("./contact_data.txt");
        if (!Files.exists(filePath)) {
            try {    
                Files.createFile(filePath);        
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        listSynch();
        printMainMenu(); 
        userChoice(userInputMenu());
    }
    /**
    * prints out main menu choices user has to use the application
    */
    public static void printMainMenu() {
        System.out.println();
        System.out.println("        < Your Contacts App >");
        System.out.println("");
        System.out.println("  Please type a corresponding number ");
        System.out.println();
        System.out.println("    1 - show contacts");
        System.out.println("    2 - add a new contact");
        System.out.println("    3 - delete a contact");
        System.out.println("    4 - edit a contact");
        System.out.println("    5 - quit");
        System.out.println();
    }
    /**
    * prints out choices to quit or return to main menu
    */
    public static void printContactBookMenu() {
        System.out.println();
        System.out.println("    0 - back");
        System.out.println("    5 - quit");
        System.out.println();
        userChoice(userInputMenu());
    }
    /**
    * prints out choices to quit or return to main menu
    */
    public static void printContactBook() {
        for(int i = 0; i < contactDetails.size(); i++) {
            System.out.println("ID: " + contactDetails.get(i).getId());
            System.out.println("Name: " + contactDetails.get(i).getFirstName() 
            + " " + contactDetails.get(i).getLastName());
            System.out.println("Phone Number: " + 
            contactDetails.get(i).getPhoneNumber());
            System.out.println("Email address: " + 
            contactDetails.get(i).getEmail());
            System.out.println("Address: " + 
            contactDetails.get(i).getAddress());
            System.out.println();
        }
    }
    /**
    * receives and validates user's input to use mainMenu/contactBookMenu
    * choices.
    *
    * @return int returns userChoice 
    */
    public static int userInputMenu() {
        int userChoice = 0;
        System.out.println("    Please input a number to proceed");
        try {
            userChoice = Integer.parseInt(c.readLine());
        } catch (NumberFormatException e) {              
            System.out.println();    
            System.out.println("Error: Invalid input, answer must be a number");
        }
        if (userChoice < 0 || userChoice > 5) {
            System.out.println("Input a number within the given options");
            printMainMenu();
            return userInputMenu();
        }
        return userChoice;
    }
    /**
    * switch-case operating via int, runs method based on received feed
    *
    * @param command is int received from userInputMenu (0-5)
    */
    public static void userChoice(int command) {
        switch(command) {
            case 0: printMainMenu();
                userChoice(userInputMenu());
                break;
            case 1: System.out.println("            Contact Book");
                printContactBook();             
                printContactBookMenu();
                break;
            case 2: System.out.println("        Add a new contact here");
                addNewContact();
                printContactBookMenu();
                break;
            case 3: deleteContact(choosingContact());
                printContactBookMenu();
                break;
            case 4: editContact(choosingContact());
                printContactBookMenu();
                break;
            case 5: 
                System.out.println("        Bye!");//
                break;
        }
    }
    /**
    * method used for choosing a contact, user inputs id and method seeks it
    * from existing data
    *
    * @return returns -5 if contact's not found otherwise returns target's index
    */
    public static int choosingContact() {
        System.out.println("Type your target id");
        String targetId = c.readLine();
        System.out.println();
        for(int i = 0; i<contactDetails.size(); i++) {
            String searchedId = contactDetails.get(i).getId();
            if(searchedId.equals(targetId)) {
                System.out.println("contact chosen:");
                return i;
            } 
        }
        return -5;
    }    
    /**
    * method used for deleting a contact, if no match prints out no target found
    *
    * @param index is int used to identify chosen contact
    */
    public static void deleteContact(int index){
        if(index == -5){
            System.out.println("no matching target found!"); 
        } else {
            System.out.println(contactDetails.get(index).getFirstName() + " " + 
            contactDetails.get(index).getLastName());

            String originalId = contactDetails.get(index).getId();
            Iterator<Person> searchedId = contactDetails.iterator();
            while(searchedId.hasNext()) {
                Person newPerson = searchedId.next();
                if(newPerson.getId().equals(originalId)) {
                    searchedId.remove();
                    System.out.println("contact deleted!");
                } 
            }
            saveToContactBook();
        }
    }
    /**
    * method used for editing a contact, if no match prints out no target found
    *
    * @param index is int used to identify chosen contact
    */
    public static void editContact(int index) {
        if(index == -5){
            System.out.println("no matching target found!"); 
        } else {
            System.out.println("ID: " + contactDetails.get(index).getId());
            System.out.println("Name: " 
            + contactDetails.get(index).getFirstName() + " " + 
            contactDetails.get(index).getLastName());
            System.out.println("Phone Number: " + 
            contactDetails.get(index).getPhoneNumber());
            System.out.println("Email address: " + 
            contactDetails.get(index).getEmail());
            System.out.println("Address: " + 
            contactDetails.get(index).getAddress());
            System.out.println();
            
            System.out.println("Please make desired changes to contact");

            System.out.println("id: (mandatory, form of DDMMYYAXXXX)");
            String idNumber = c.readLine();
            contactDetails.get(index).setId(idNumber);
            System.out.println("firstname? (mandatory)");
            String firstname = c.readLine();
            contactDetails.get(index).setFirstName(firstname);
            System.out.println("lastname? (mandatory)");
            String lastname = c.readLine();
            contactDetails.get(index).setLastName(lastname);
            System.out.println("phonenumber? (mandatory)");
            String phonenumber = c.readLine();
            contactDetails.get(index).setPhoneNumber(phonenumber);
            System.out.println("email?");
            String email = c.readLine();
            if(email.trim().isEmpty()) {
                contactDetails.get(index).setEmail("-");
            } else {
                contactDetails.get(index).setEmail(email);
            }
            System.out.println("address?");
            String address = c.readLine();
            if(address.trim().isEmpty()) {
                contactDetails.get(index).setAddress("-");
            } else {
                contactDetails.get(index).setAddress(address);
            }
            saveToContactBook();
            System.out.println("Contact updated!"); 
        }
    } 
    /**
    * method used for adding a new contact with mandatory details (id, first 
    * name, last name, phone number) and optional details (email and address)
    */
    public static void addNewContact() {
        Person x = new Person();
        System.out.println("id? (mandatory, form of DDMMYYAXXXX)");
        String idNumber = c.readLine();
        x.setId(idNumber);
        System.out.println("firstname? (mandatory)");
        String firstname = c.readLine();
        x.setFirstName(firstname);
        System.out.println("lastname? (mandatory)");
        String lastname = c.readLine();
        x.setLastName(lastname);
        System.out.println("phonenumber? (mandatory)");
        String phonenumber = c.readLine();
        x.setPhoneNumber(phonenumber);
        System.out.println("email?");
        String email = c.readLine();
        if(email.trim().isEmpty()) {
            x.setEmail("-");
        } else {
            x.setEmail(email);
        }
        System.out.println("address?");
        String address = c.readLine();
        if(address.trim().isEmpty()) {
            x.setAddress("-");
        } else {
            x.setAddress(address); 
        }
        contactDetails.add(x);
        saveToContactBook();
        System.out.println("        Contact saved!");
    }
    /**
    * method used for saving array list data to a text file
    */
    public static void saveToContactBook() {
        Path filePath = Paths.get("./contact_data.txt");
        String data = contactDetails.toString();
        String editedLines = data.replaceAll("[\\[\\]]", "");
        try {
            Files.writeString(filePath, editedLines);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    * method used for synchronizing text file data to arraylist
    */
    public static void listSynch() {
        ArrayList<Person> contactDetails = new ArrayList<Person>();
        Path path = Paths.get("./contact_data.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {  
                String editedLines = line.replaceAll("[\\[\\]]", "");
                String[] parts = editedLines.split(",,,");
                Person x = new Person();
                x.setId(parts[0]);
                x.setFirstName(parts[1]);
                x.setLastName(parts[2]);
                x.setPhoneNumber(parts[3]);
                x.setEmail(parts[4]);
                x.setAddress(parts[5]);
                contactDetails.add(x);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/**
* The class Person contains set and get methods for Person's contact information 
* as well as a overridden toString method. 
*/
class Person {
    static protected String id;
    static protected String firstName;
    static protected String lastName;
    static protected String phoneNumber;
    static protected String emailAddress;
    static protected String address;
    /**
    * Turns every Person's data to a String, divided with ",,,"
    *
    * @return a string of the person's information divided with ",,,"
    */
    @Override
    public String toString() {
        String personInfo = "";
        personInfo =  personInfo + Person.getId() + ",,," 
        + Person.getFirstName() + ",,," + Person.getLastName() + ",,," 
        + Person.getPhoneNumber() + ",,," + Person.getEmail() + ",,," 
        + Person.getAddress() + ",,,";
        return personInfo;
    }
    /**
    * setId contains set method for id which is validated to MMDDYYAXXX format, 
    * if user's feed is invalid throws IllegalArgumentException
    *
    * @throws IllegalArgumentException if input doesn't match pattern
    * @param n is the String setId validates 
    */
    public void setId(String n) {
        String idPattern 
                = "\\d\\d\\d\\d\\d\\d([\\w,\\W])\\d\\d\\d([\\d,\\a-zA-Z])";
        if (n.matches(idPattern)) {
            id = n;
        } else {
            throw new IllegalArgumentException("id must be MMDDYYAXXXX");
        }
    }
    /**
    * getId contains get method for id
    *
    * @return method returns id 
    */
    public static String getId() {
        return id;
    }
    /**
    * setFirstName contains setter for first name, if user's feed is less than 2 
    * or more than 20 characters throws IllegalArgumentException
    *
    * @throws IllegalArgumentException if input is not 2-20 characters
    * @param n is the String setFirstName validates 
    */
    public void setFirstName(String n) {
        if (n.length() < 2 || n.length() > 20) {
            throw new IllegalArgumentException("name must be 2-20 characters");
        } else {
            firstName = n;
        }
    }
    /**
    * getfirstName contains get method for first name
    *
    * @return method returns first name
    */
    public static String getFirstName() {
        return firstName;
    }
    /**
    * setLastName contains set method for last name, if user's feed is less than 
    * 2 or more than 20 characters throws IllegalArgumentException
    *
    * @throws IllegalArgumentException if input is not 2-20 characters
    * @param n is the String setLastName validates 
    */
    public void setLastName(String n) {
        if (n.length() < 2 || n.length() > 20) {
            throw new IllegalArgumentException("name must be 2-20 characters");
        } else {
            lastName = n;
        }
    }
    /**
    * getLastName contains get method for last name
    *
    * @return method returns last name
    */
    public static String getLastName() {
        return lastName;
    }
    /**
    * setPhoneNumber contains set method for phone number, if user's feed is 
    * doesn't match the pattern throws IllegalArgumentException
    *
    * @throws IllegalArgumentException if input doesn't match the pattern
    * @param n is the String setPhoneNumber validates 
    */
    public void setPhoneNumber(String n) {
        String phoneNumberPattern = "([\\w,\\W])?\\d{3,20}";
        if (!n.matches(phoneNumberPattern)) {
            throw new IllegalArgumentException("phoneNumber length 3-20");
        } else {
            phoneNumber = n;
        }
    }    
    /**
    * getLastName contains get method for phone number
    *
    * @return method returns phone number
    */
    public static String getPhoneNumber() {
        return phoneNumber;
    }
    /**
    * setEmail contains set method for email, if user's feed is more than 50
    * characters throws IllegalArgumentException
    *
    * @throws IllegalArgumentException if input is not less than 50 characters
    * @param n is the String setEmail validates 
    */
    public void setEmail(String n) {
        if ( n.length() > 50) {
            throw new IllegalArgumentException("email must be < 50 char");
        } else {
            emailAddress = n;
        }
    }
    /**
    * getLastName contains get method for email
    *
    * @return method returns email
    */
    public static String getEmail() {
        return emailAddress;
    }
    /**
    * setAddress contains set method for address, if user's feed is more than 50
    * characters throws IllegalArgumentException
    *
    * @throws IllegalArgumentException if input is not less than 50 characters
    * @param n is the String setAddress validates 
    */
    public void setAddress(String n) {
        if (n.length() > 50) {
            throw new IllegalArgumentException("must be less than 50 char");
        } else {
            address = n;
        }
    }
    /**
    * getAddress contains get method for address
    *
    * @return method returns address
    */
    public static String getAddress() {
        return address;
    }
}
