package data;

import books.Book;
import books.HistoryBook;
import books.StoryBook;
import books.TextBook;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public abstract class User {

    // Method untuk memilih buku
    public void choiceBooks() {
        Book bookObj = new Book();
        Student studentObj = new Student();

        Stage choiceBooksStage = new Stage();
        choiceBooksStage.setTitle("UMM library - Pilih Buku");

        TableView<Book> tableView = new TableView<>();

        TableColumn<Book, String> idColumn = new TableColumn<>("ID Buku");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Nama Buku");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Penulis");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Kategori");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stok");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableView.getColumns().addAll(idColumn, titleColumn, authorColumn, categoryColumn, stockColumn);

        for (Book i : Book.arr_bookList) {
            tableView.getItems().add(i);
        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(tableView, 0, 0);

        Label bookIdLabel = new Label("Input ID buku yang ingin dipinjam:");
        grid.add(bookIdLabel, 0, 1);

        TextField bookIdField = new TextField();
        grid.add(bookIdField, 0, 2);

        Label durationLabel = new Label("Berapa hari ingin meminjam buku? (Max 14 hari)");
        grid.add(durationLabel, 0, 3);

        TextField durationField = new TextField();
        grid.add(durationField, 0, 4);

        Button submitButton = new Button("Submit");
        grid.add(submitButton, 0, 5);

        Button backButton = new Button("Back");
        grid.add(backButton, 0, 6);

        Label messageLabel = new Label();
        grid.add(messageLabel, 0, 7);

        Scene scene = new Scene(grid, 1360, 720);
        choiceBooksStage.setScene(scene);
        choiceBooksStage.show();

        submitButton.setOnAction(e -> {
            boolean validasi = false;

            String idBukuYangDipinjam = bookIdField.getText();

            for (Book i : Book.arr_bookList) {
                if (i.getBookId().equals(idBukuYangDipinjam)) {
                    if (i.getStock() > 0) {
                        int a = i.getStock();
                        a--;
                        i.setStock(a);

                        int inputwaktuPinjaman = Integer.parseInt(durationField.getText());

                        if (inputwaktuPinjaman < 15) {
                            bookObj.setDuration(inputwaktuPinjaman);
                            Book.arr_borrowedBook.add(new Book(idBukuYangDipinjam, i.getStock(), bookObj.getDuration()));
                            validasi = true;
                            break;
                        } else {
                            messageLabel.setText("Max 14 hari");
                        }
                    } else if (i.getStock() == 0) {
                        messageLabel.setText("== Stok buku habis! ==");
                        studentObj.menu();
                    }
                }
            }
            if (validasi) {
                messageLabel.setText("==== Buku berhasil dipinjam! ====");
            } else {
                messageLabel.setText("== ID tidak ditemukan! ==");
            }
        });

        backButton.setOnAction(event -> {
            studentObj.menu();
            choiceBooksStage.close();
        });
    }

    // Method untuk menginput buku
    public void inputBook() {
        Book textBookObj = new TextBook();
        Book storyBookObj = new StoryBook();
        Book historyBookObj = new HistoryBook();

        Stage inputBookStage = new Stage();
        inputBookStage.setTitle("UMM Library - Input Book");

        // Label
        Label sceneTitle = new Label("Tambah Buku");
        Label chooseBook = new Label("Pilih kategori buku:");

        // Font Label style
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        chooseBook.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 15));

        // Font label color
        sceneTitle.setStyle("-fx-text-fill: #A91D3A;");

        // Button
        Button historyBookButton = new Button("History Book");
        Button storyBookButton = new Button("Story Book");
        Button textBookButton = new Button("Text Book");
        Button backButton = new Button("Back");

        // Grid layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(5);

        grid.add(sceneTitle, 0, 0);
        grid.add(chooseBook, 0, 1);
        grid.add(historyBookButton, 2, 0);
        grid.add(storyBookButton, 2, 1);
        grid.add(textBookButton, 2, 2);
        grid.add(backButton, 2, 3);

        Scene scene = new Scene(grid, 1360, 720);
        inputBookStage.setScene(scene);
        inputBookStage.show();

        // Action Button
        historyBookButton.setOnAction(event -> {
            addBook(historyBookObj, "UMM Library - Add History Book", "History Book");
            inputBookStage.close();
        });

        storyBookButton.setOnAction(event -> {
            addBook(storyBookObj, "UMM Library - Add Story Book", "Story Book");
            inputBookStage.close();
        });

        textBookButton.setOnAction(event -> {
            addBook(textBookObj, "UMM Library - Add Text Book", "Text Book");
            inputBookStage.close();
        });

        backButton.setOnAction(event -> {
            menu();
            inputBookStage.close();
        });
    }

    // Method untuk menambahkan buku
    public void addBook(Book genreBook, String addBookStageTitle, String addBookSceneTitle) {
        Admin adminObj = new Admin();
        Book bookObj = new Book();

        Stage addbookStage = new Stage();
        addbookStage.setTitle(addBookStageTitle);

        // Label
        Label sceneTitleLabel = new Label(addBookSceneTitle);
        Label bookIdLabel = new Label("ID Buku    :");
        Label bookTitleLabel = new Label("Judul Buku :");
        Label authorLabel = new Label("Penulis    :");
        Label stockLabel = new Label("Stok       :");

        // Notification Label
        Label errorMessageLabel = new Label("Stok harus berupa angka");

        // Field
        TextField bookIdField = new TextField(adminObj.generateId());
        TextField bookTitleField = new TextField();
        TextField authorField = new TextField();
        TextField stockField = new TextField();

        // Font label style
        sceneTitleLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        bookIdLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 15));
        bookTitleLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 15));
        authorLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 15));
        stockLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 15));

        // Font label color
        sceneTitleLabel.setStyle("-fx-text-fill: #A91D3A;");
        errorMessageLabel.setStyle("-fx-text-fill: #FF1E1E;");

        // Font visible settings
        errorMessageLabel.setVisible(false);

        // Button
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        // Grid layout
        GridPane gridAddBook = new GridPane();
        gridAddBook.setAlignment(Pos.CENTER);
        gridAddBook.setVgap(10);
        gridAddBook.setHgap(5);

        gridAddBook.add(sceneTitleLabel, 1, 0);
        gridAddBook.add(bookIdLabel, 0, 1);
        gridAddBook.add(bookTitleLabel, 0, 2);
        gridAddBook.add(authorLabel, 0, 3);
        gridAddBook.add(stockLabel, 0, 4);
        gridAddBook.add(errorMessageLabel, 0, 5);

        gridAddBook.add(bookIdField, 2, 1);
        gridAddBook.add(bookTitleField, 2, 2);
        gridAddBook.add(authorField, 2, 3);
        gridAddBook.add(stockField, 2, 4);

        gridAddBook.add(submitButton, 2, 5);
        gridAddBook.add(backButton, 2, 6);

        Scene addbookScene = new Scene(gridAddBook, 600, 400);
        addbookStage.setScene(addbookScene);
        addbookStage.show();

        // Action button
        submitButton.setOnAction(event -> {
            try {
                errorMessageLabel.setVisible(false);

                bookObj.setBookId(bookIdField.getText());
                bookObj.setTitle(bookTitleField.getText());
                genreBook.setCategory(addBookSceneTitle);
                bookObj.setAuthor(authorField.getText());
                bookObj.setStock(Integer.parseInt(stockField.getText()));

                Book.arr_bookList.add(new Book(bookObj.getBookId(), bookObj.getTitle(), bookObj.getAuthor(), genreBook.getCategory(), bookObj.getStock()));

                adminObj.menu();
                addbookStage.close();
            } catch (NumberFormatException message) {
                errorMessageLabel.setVisible(true);
            }
        });

        backButton.setOnAction(event -> {
            menu();
            addbookStage.close();
        });
    }

    // Abstract method to be implemented by subclasses
    public abstract void menu();

    // Abstract method to be implemented by subclasses
    public abstract void start(Stage primaryStage);
}
