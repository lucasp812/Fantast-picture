package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import service.Modele;
import java.util.stream.Collectors;


import java.io.InputStream;

public class VueController {

    @FXML
    private ListView<String> listImage;

    @FXML
    private ImageView image;

    @FXML
    private Label infoLogin;

    @FXML
    private Button validate;

    @FXML
    private Button validateLogin;

    @FXML
    private TextField userName;

    @FXML
    private TextField userLogin;

    @FXML
    private TextField note;

    @FXML
    private Text noteText;

    private Modele modele;

    @FXML
    public void initialize() {
        modele = new Modele();

        listImage.getItems().addAll(
            modele.getImages().stream()
                  .map(entite.Image::getName)
                  .collect(Collectors.toList())
        );
        listImage.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Gestionnaire de clic explicite avec logs
        listImage.setOnMouseClicked(event -> {
            String selectedName = listImage.getSelectionModel().getSelectedItem();
            if (selectedName != null) {
                System.out.println("Sélection confirmée : " + selectedName);
                ImageSelection(); // Appelle la méthode pour gérer la sélection
            } else {
                System.out.println("Aucune sélection détectée.");
            }
        });
    }


    
    
    
    
    @FXML
    private void ImageSelection() {
        String selectedName = listImage.getSelectionModel().getSelectedItem();

        if (selectedName != null) {
            InputStream imageUrl = modele.getImageUrlByName(selectedName);
            if (imageUrl != null) {
                Image imageIS = new Image(imageUrl);
                image.setImage(imageIS);
            }

            Integer userNote = modele.getNoteForCurrentUser(selectedName);
            
            if (userNote != null) {
                note.setText(String.valueOf(userNote)); 
            } else {
                note.clear(); 
            }
        }
    }


    @FXML
    private void handleLogin() {
        try {
            String login = userName.getText();
            String password = userLogin.getText(); 

            if (login.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Les champs login et mot de passe ne doivent pas être vides.");
            }

            if (!modele.authenticate(login, password)) {
                throw new IllegalArgumentException("Login ou mot de passe incorrect.");
            }

            showApplication();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }



    @FXML
    private void validateNote() {
        try {
            String selectedImage = listImage.getSelectionModel().getSelectedItem();
            String noteValueText = note.getText();

            if (selectedImage == null || noteValueText.isEmpty()) {
                throw new IllegalArgumentException("Sélectionnez une image et entrez une note valide.");
            }

            int noteValue = Integer.parseInt(noteValueText);

            modele.addOrUpdateNote(modele.getCurrentUser(), selectedImage, noteValue);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Note enregistrée !");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer une note valide.");
            alert.showAndWait();
        }
    }


    private void showApplication() {
        image.setVisible(true);
        listImage.setVisible(true);
        note.setVisible(true);
        noteText.setVisible(true);
        validate.setVisible(true);
        validateLogin.setDisable(true);
        infoLogin.setText("Vous êtes connecté.");
    }
}
