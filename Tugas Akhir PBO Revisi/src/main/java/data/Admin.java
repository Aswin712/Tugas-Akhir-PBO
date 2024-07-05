package data;

import books.Book;
import com.main.LibrarySystem;
import util.iMenu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Admin extends User implements iMenu {

    //=================================== ATRIBUT =====================================
    public static String adminusername = "admin";
    public static String adminpassword = "admin";

    //=================================== Main & Start Method ==================================
    public static void main(String[] args) {
        // Example data
        Book.arr_borrowedBook.add(new Book("1", "Book Title 1", "Author 1", "Category 1", 7));
        Book.arr_borrowedBook.add(new Book("2", "Book Title 2", "Author 2", "Category 2", 14));

        launch(Admin.class, args);
    }

    private static void launch(Class<Admin> adminClass, Object args) {

    }

    @Override
    public void start(Stage primaryStage) {
        menu();
    }

    //======================================= MENU Method =======================================
    @Override
    public void menu(){
        Stage adminMenuStage = new Stage();
        adminMenuStage.setTitle("UMM Library - Admin Menu");

        //Label
        Label sceneTitle = new Label("Menu Admin");

        //Font Style
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

        //Font Color
        sceneTitle.setStyle("-fx-text-fill: #A91D3A;");

        //Button
        Button addStudentButton     = new Button("Tambah Mahasiswa");
        Button displayStudentButton = new Button("Daftar Mahasiswa");
        Button addBookButton        = new Button("Tambah Buku");
        Button loanHistoryButton    = new Button("Riwayat Peminjaman");
        Button logoutButton         = new Button("Logout");

        //Grid Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.add(sceneTitle,0,1);

        grid.add(addStudentButton, 2,0);
        grid.add(displayStudentButton, 2,1);
        grid.add(addBookButton, 2,2);
        grid.add(loanHistoryButton, 2, 3);
        grid.add(logoutButton,2,4);

        grid.setVgap(10);
        grid.setHgap(6);

        Scene scene = new Scene(grid, 1360, 720);
        adminMenuStage.setScene(scene);
        adminMenuStage.show();

        //Action Button
        addStudentButton.setOnAction(event -> {
            addstudent();
            adminMenuStage.close();
        });

        displayStudentButton.setOnAction(event -> {
            displaystudent();
            adminMenuStage.close();
        });

        addBookButton.setOnAction(event -> {
            inputBook();
            adminMenuStage.close();
        });

        loanHistoryButton.setOnAction(event -> {
            showLoanHistory();
            adminMenuStage.close();
        });

        logoutButton.setOnAction(event -> {
            LibrarySystem librarySystemObj = new LibrarySystem();
            librarySystemObj.start(new Stage());
            adminMenuStage.close();
        });

    }

    //===================================== Other Method =======================================
    public void addstudent() {

        // Membuat form baru
        Stage addStudentStage = new Stage();
        addStudentStage.setTitle("Tambah Mahasiswa");

        //Label
        Label sceneTitle    = new Label("Tambah Mahasiswa");
        Label nameLabel     = new Label("Nama");
        Label nimLabel      = new Label("NIM");
        Label fakultasLabel = new Label("Fakultas");
        Label jurusanLabel  = new Label("Jurusan");

        //Notification Label
        Label sumbitFailed = new Label("NIM harus 15 digit!");
        sumbitFailed.setVisible(false);

        //Field
        TextField nameField     = new TextField();
        TextField nimField      = new TextField();
        TextField fakultasField = new TextField();
        TextField jurusanField  = new TextField();

        //Font Style
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        nameLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 15));
        nimLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 15));
        fakultasLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 15));
        jurusanLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 15));

        //Font Color
        sceneTitle.setStyle("-fx-text-fill: #A91D3A;");
        sumbitFailed.setStyle("-fx-text-fill: #FF1E1E;");

        //Button
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        //Grid Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.add(sceneTitle, 0,0);

        grid.add(nameLabel, 0,1);
        grid.add(nimLabel, 0,2);
        grid.add(fakultasLabel, 0,3);
        grid.add(jurusanLabel, 0,4);

        grid.add(nameField, 1,1);
        grid.add(nimField, 1,2);
        grid.add(fakultasField, 1,3);
        grid.add(jurusanField, 1,4);

        grid.add(submitButton,1,5);
        grid.add(backButton, 1, 6);

        grid.add(sumbitFailed, 0,5);

        grid.setVgap(10);
        grid.setHgap(5);

        Scene scene = new Scene(grid, 1360, 720);
        addStudentStage.setScene(scene);
        addStudentStage.show();

        //Action Button
        submitButton.setOnAction(event -> {
            if (nimField.getText().length() == 15) {
                Admin adminObj = new Admin();

                Student.arr_userStudent.add(new Student.UserStudent(nameField.getText(), nimField.getText(), fakultasField.getText(), jurusanField.getText()));
                adminObj.menu();
                addStudentStage.close();

            } else {
                sumbitFailed.setVisible(true);
            }
        });

        backButton.setOnAction(event -> {
            menu();
            addStudentStage.close();
        });
    }

    public void displaystudent() {
        // Membuat stage baru
        Stage displayStudentStage = new Stage();
        displayStudentStage.setTitle("UMM Library - Daftar Mahasiswa");

        //Label
        Label sceneTitle    = new Label("Daftar Mahasiswa");

        //Font Style
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

        //Font Color
        sceneTitle.setStyle("-fx-text-fill: #A91D3A;");

        // Membuat ListView untuk menampilkan mahasiswa
        ListView<String> listView = new ListView<>();

        for (Student.UserStudent i : Student.arr_userStudent) {
            String studentInfo = "Nama     : " + i.nama +"\n" +
                    "NIM      : " + i.nim + "\n" +
                    "Fakultas : " + i.fakultas + "\n" +
                    "Prodi    : " + i.prodi + "\n" +
                    "===========================";
            listView.getItems().add(studentInfo);
        }

        //Button
        Button backButton = new Button("Back");

        //Grid Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.add(sceneTitle,0,0);
        grid.add(listView,0,1);
        grid.add(backButton, 0, 2);

        grid.setVgap(5);

        Scene scene = new Scene(grid, 1360, 720);
        displayStudentStage.setScene(scene);
        displayStudentStage.show();

        backButton.setOnAction(event -> {
            menu();
            displayStudentStage.close();
        });
    }

    public void inputBook(){
        super.inputBook();
    }

    public void showLoanHistory() {
        // Membuat stage baru
        Stage loanHistoryStage = new Stage();
        loanHistoryStage.setTitle("UMM Library - Riwayat Peminjaman");

        //Label
        Label sceneTitle = new Label("Riwayat Peminjaman");

        //Font Style
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

        //Font Color
        sceneTitle.setStyle("-fx-text-fill: #A91D3A;");

        // Membuat ListView untuk menampilkan riwayat peminjaman
        ListView<String> listView = new ListView<>();

        // Memastikan arr_borrowedBook tidak null dan memiliki data
        if (Book.arr_borrowedBook != null) {
            for (Book book : Book.arr_borrowedBook) {
                String bookInfo = "ID Buku    : " + book.getId() + "\n" +
                        "Judul      : " + book.getTitle() + "\n" +
                        "Peminjam   : " + book.getBorrower() + "\n" +
                        "Tenggat    : " + book.getDueDays() + " hari\n" +
                        "===========================";
                listView.getItems().add(bookInfo);
            }
        }

        //Button
        Button backButton = new Button("Back");

        //Grid Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.add(sceneTitle, 0, 0);
        grid.add(listView, 0, 1);
        grid.add(backButton, 0, 2);

        grid.setVgap(5);

        Scene scene = new Scene(grid, 1360, 700);
        loanHistoryStage.setScene(scene);
        loanHistoryStage.show();

        backButton.setOnAction(event -> {
            menu();
            loanHistoryStage.close();
        });
    }

    public String generateId() {
        return null;
    }
}
