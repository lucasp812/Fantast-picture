package application;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import service.Modele;
import entite.Note;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

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
  private Text noteText;

  @FXML
  private Text textLogin;

  @FXML
  private Text textName;

  @FXML
  private TextField note;

  private Modele modele;

  @FXML
  public void initialize() {

    modele = new Modele();

    List<String> imageNames = modele.getImages().stream()
        .map(img -> img.getName())
        .collect(Collectors.toList());
    listImage.getItems().addAll(imageNames);


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

  private void showApplication() {
    
    image.setVisible(true);
    listImage.setVisible(true);
    note.setVisible(true);
    noteText.setVisible(true);
    validate.setVisible(true);
    validateLogin.setDisable(true);
    infoLogin.setText("Vous etes bien connecté");
    System.out.println("Utilisateur connecté !");
    
  }


  private void validateNote() {

    String selectedName = listImage.getSelectionModel().getSelectedItem();
    String userNameText = userName.getText();
    String noteText = note.getText();        
   
    
    
    
    
    
  }

}
