package books;

import java.time.LocalDate;
import java.util.ArrayList;

public class Book {

    //==================================== ATTRIBUTES ====================================
    private String bookId;
    private String title;
    private String author;
    private String category;
    private int stock;
    private int duration;
    private LocalDate dueDate;

    // ArrayList to store registered books
    public static ArrayList<Book> arr_bookList = new ArrayList<>();

    // ArrayList to store borrowed books
    public static ArrayList<Book> arr_borrowedBook = new ArrayList<>();

    //====================================== METHODS ======================================

    // Constructors to implement overloading
    public Book() {
    }

    public Book(String category) {
        this.category = category;
    }

    public Book(String bookId, int stock, int duration) {
        this.bookId = bookId;
        this.stock = stock;
        this.duration = duration;
    }

    public Book(String bookId, String title, String author, String category, int duration, int stock) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.stock = stock;
        this.duration = duration;
    }

    //=================================== SETTER METHODS ====================================
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    //=================================== GETTER METHODS ==================================
    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    //=================================== OTHER METHODS ==================================
    public void borrowBook() {
        if (stock > 0) {
            stock--;
            dueDate = LocalDate.now().plusDays(duration);
            arr_borrowedBook.add(this);
        }
    }

    public void returnBook() {
        stock++;
        arr_borrowedBook.remove(this);
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate);
    }
}
