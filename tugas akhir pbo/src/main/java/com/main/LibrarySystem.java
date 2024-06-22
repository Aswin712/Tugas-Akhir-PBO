package com.main;

import data.Admin;
import data.Student;
import exception.custom.IllegalAdminAccess;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LibrarySystem extends Application {

    public static String NIM;

    @Override
    public void start(Stage primaryStage) {
        Admin adminObj = new Admin();
        Student studentObj = new Student();

        primaryStage.setTitle("UMM Library");

        // Label
        Label sceneTitle = new Label("UMM Library");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");

        // Label Notifikasi
        Label errorLoginMessage = new Label("Pengguna tidak ditemukan");

        // Field
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Gaya Font
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 28));
        usernameLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 18));
        passwordLabel.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 18));
        errorLoginMessage.setFont(Font.font("Calibri Body", FontWeight.NORMAL, 14));

        // Warna Font
        sceneTitle.setStyle("-fx-text-fill: #A91D3A;");
        errorLoginMessage.setStyle("-fx-text-fill: #FF1E1E;");

        // Pengaturan Visibilitas Font
        errorLoginMessage.setVisible(false);

        // Tombol
        Button loginButton = new Button("Login");
        Button backButton = new Button("Kembali");
        backButton.setVisible(false); // Awalnya sembunyikan tombol kembali

        // Button Styles
        loginButton.setStyle("-fx-background-color: #A91D3A; -fx-text-fill: white; -fx-font-size: 14px;");
        backButton.setStyle("-fx-background-color: #CCCCCC; -fx-text-fill: black; -fx-font-size: 14px;");

        // Tata Letak Grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(sceneTitle, 0, 0, 2, 1);
        grid.add(usernameLabel, 0, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(errorLoginMessage, 0, 4, 2, 1);
        grid.add(usernameField, 1, 1);
        grid.add(passwordField, 1, 2);
        grid.add(loginButton, 1, 3);
        grid.add(backButton, 0, 3); // Tambahkan tombol kembali ke grid
        
        // Main Layout using BorderPane
        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(grid);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));

        // Buat Window/display
        Scene scene = new Scene(mainLayout, 800, 600);

        // Setel gambar latar belakang
        Image background = new Image("file:src/main/java/img/LIBRARY.jpg");
        grid.setBackground(new Background(new BackgroundFill(new ImagePattern(background), CornerRadii.EMPTY, Insets.EMPTY)));

        primaryStage.setScene(scene);
        primaryStage.show();

        // Aksi Tombol Login
        loginButton.setOnAction(event -> {
            if (usernameField.getText().equals(Admin.adminusername) && passwordField.getText().equals(Admin.adminpassword)) {
                adminObj.menu();
                primaryStage.close();
            } else if (usernameField.getText().length() == 15 && passwordField.getText().length() < 15) {
                try {
                    if (studentObj.isStudents(usernameField.getText())) {
                        errorLoginMessage.setVisible(false);
                        studentObj.menu(); // Anggap ada metode menu untuk siswa
                        backButton.setVisible(true); // Tampilkan tombol kembali setelah login berhasil
                    } else {
                        errorLoginMessage.setVisible(true);
                    }
                } catch (IllegalAdminAccess pesanError) {
                    errorLoginMessage.setText(pesanError.getMessage());
                    errorLoginMessage.setVisible(true);
                }
            } else {
                errorLoginMessage.setVisible(true);
            }
        });

        // Aksi Tombol Kembali
        backButton.setOnAction(event -> {
            // Reset field dan visibilitas pesan error
            usernameField.clear();
            passwordField.clear();
            errorLoginMessage.setVisible(false);
            backButton.setVisible(false);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}