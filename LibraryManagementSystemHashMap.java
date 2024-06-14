import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Book {
    private String title;
    private String author;
    private boolean checkedOut;
    private Date dueDate;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.checkedOut = false;
        this.dueDate = null;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}

class Library {
    private HashMap<String, Book> books;
    private HashMap<String, User> users;
    private HashMap<String, Book> cart;

    public Library() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
        this.cart = new HashMap<>();

        // Example books
        books.put("To Kill a Mockingbird", new Book("To Kill a Mockingbird", "Harper Lee"));
        books.put("1984", new Book("1984", "George Orwell"));
        books.put("The Catcher in the Rye", new Book("The Catcher in the Rye", "J.D. Salinger"));
    }

    public void addUser(String username, String password) {
        users.put(username, new User(username, password));
    }

    public boolean validateUser(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            return user.getPassword().equals(password);
        }
        return false;
    }

    public Book searchBook(String title) {
        return books.get(title);
    }

    public void checkoutBook(String title) {
        Book book = searchBook(title);
        if (book != null && !book.isCheckedOut()) {
            book.setCheckedOut(true);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 14);
            Date dueDate = calendar.getTime();
            book.setDueDate(dueDate);
            System.out.println("Book '" + title + "' has been checked out successfully.");
            System.out.println("Please return by: " + dueDate);
        } else if (book != null && book.isCheckedOut()) {
            System.out.println("Book '" + title + "' is already checked out.");
        } else {
            System.out.println("Book '" + title + "' not found.");
        }
    }

    public void addToCart(String title) {
        Book book = searchBook(title);
        if (book != null && !book.isCheckedOut()) {
            cart.put(title, book);
            System.out.println("Book '" + title + "' has been added to the cart.");
        } else if (book != null && book.isCheckedOut()) {
            System.out.println("Book '" + title + "' is already checked out.");
        } else {
            System.out.println("Book '" + title + "' not found.");
        }
    }

    public void removeFromCart(String title) {
        if (cart.containsKey(title)) {
            cart.remove(title);
            System.out.println("Book '" + title + "' has been removed from the cart.");
        } else {
            System.out.println("Book '" + title + "' not found in the cart.");
        }
    }

    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            System.out.println("Cart contains:");
            for (Map.Entry<String, Book> entry : cart.entrySet()) {
                System.out.println("Title: " + entry.getKey() + ", Author: " + entry.getValue().getAuthor());
            }
            System.out.println("Options:");
            System.out.println("1. Checkout Cart");
            System.out.println("2. Add More Books to Cart");
            System.out.println("3. Remove Books from Cart");
            System.out.println("4. Return to Main Menu");
        }
    }

    public void checkoutCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        for (Book book : cart.values()) {
            if (!book.isCheckedOut()) {
                book.setCheckedOut(true);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, 14);
                Date dueDate = calendar.getTime();
                book.setDueDate(dueDate);
            }
        }
        System.out.println("Cart checked out successfully.");
        cart.clear(); // Clear the cart after checkout
    }
}

public class LibraryManagementSystemHashMap {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // User registration
        System.out.println("User Registration:");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        library.addUser(username, password);

        // Login
        System.out.println("\nLogin:");
        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        if (library.validateUser(loginUsername, loginPassword)) {
            System.out.println("Login successful.");
            int choice;
            do {
                System.out.println("\nMenu:");
                System.out.println("1. Search Book");
                System.out.println("2. Check out Book");
                System.out.println("3. Add to Cart");
                System.out.println("4. Remove from Cart");
                System.out.println("5. View Cart");
                System.out.println("6. Logout");

                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter title of the book to search: ");
                        String searchTitle = scanner.nextLine();
                        Book foundBook = library.searchBook(searchTitle);
                        if (foundBook != null) {
                            System.out.println("Book Found - Title: " + foundBook.getTitle() + ", Author: " + foundBook.getAuthor());
                        } else {
                            System.out.println("Book not found.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter title of the book to check out: ");
                        String checkoutTitle = scanner.nextLine();
                        library.checkoutBook(checkoutTitle);
                        break;
                    case 3:
                        System.out.print("Enter title of the book to add to cart: ");
                        String addToCartTitle = scanner.nextLine();
                        library.addToCart(addToCartTitle);
                        break;
                    case 4:
                        System.out.print("Enter title of the book to remove from cart: ");
                        String removeFromCartTitle = scanner.nextLine();
                        library.removeFromCart(removeFromCartTitle);
                        break;
                    case 5:
                        library.viewCart();
                        int cartChoice;
                        do {
                            System.out.print("Enter your choice: ");
                            cartChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (cartChoice) {
                                case 1:
                                    library.checkoutCart();
                                    break;
                                case 2:
                                    System.out.print("Enter title of the book to add to cart: ");
                                    String addToCartTitleAgain = scanner.nextLine();
                                    library.addToCart(addToCartTitleAgain);
                                    break;
                                case 3:
                                    System.out.print("Enter title of the book to remove from cart: ");
                                    String removeFromCartTitleAgain = scanner.nextLine();
                                    library.removeFromCart(removeFromCartTitleAgain);
                                    break;
                                case 4:
                                    break; // Return to main menu
                                default:
                                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
                            }
                        } while (cartChoice != 4);
                        break;
                    case 6:
                        System.out.println("Logged out.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 6.");
                }
            } while (choice != 6);
        } else {
            System.out.println("Invalid username or password. Exiting...");
        }
    }
}
