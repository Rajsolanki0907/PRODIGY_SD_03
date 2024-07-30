import java.io.*;
import java.util.*;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber + ", Email Address: " + emailAddress;
    }
}

public class ContactManager {
    private static final String FILE_NAME = "contacts.ser";
    private List<Contact> contacts;

    public ContactManager() {
        contacts = loadContacts();
    }

    public void addContact(String name, String phoneNumber, String emailAddress) {
        contacts.add(new Contact(name, phoneNumber, emailAddress));
        saveContacts();
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i));
            }
        }
    }

    public void editContact(int sn, String name, String phoneNumber, String emailAddress) {
        if (sn >= 0 && sn < contacts.size()) {
            Contact contact = contacts.get(sn);
            contact.setName(name);
            contact.setPhoneNumber(phoneNumber);
            contact.setEmailAddress(emailAddress);
            saveContacts();
        } else {
            System.out.println("Invalid contact sn.");
        }
    }

    public void deleteContact(int sn) {
        if (sn >= 0 && sn < contacts.size()) {
            contacts.remove(sn);
            saveContacts();
        } else {
            System.out.println("Invalid contact sn.");
        }
    }

    @SuppressWarnings("unchecked")
    private List<Contact> loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            System.out.println("Failed to save contacts.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactManager contactManager = new ContactManager();

        while (true) {
            System.out.println("\nContact Manager");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email address: ");
                    String emailAddress = scanner.nextLine();
                    contactManager.addContact(name, phoneNumber, emailAddress);
                    break;
                case 2:
                    contactManager.viewContacts();
                    break;
                case 3:
                    System.out.print("Enter the contact number to edit: ");
                    int editsn = scanner.nextInt() - 1;
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter new email address: ");
                    String newEmailAddress = scanner.nextLine();
                    contactManager.editContact(editsn, newName, newPhoneNumber, newEmailAddress);
                    break;
                case 4:
                    System.out.print("Enter the contact number to delete: ");
                    int deletesn = scanner.nextInt() - 1;
                    contactManager.deleteContact(deletesn);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

