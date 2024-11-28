package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import entite.Image;
import entite.User;
import entite.Note;


public class Modele {

    private List<Image> images= new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private static final String file_path = "notes.txt";

    
    public Modele() {
      
        images.add(new Image("seoul", getClass().getResourceAsStream("../img/seoul.jpg")));
        images.add(new Image("rio", getClass().getResourceAsStream("../img/rio.jpg")));
        images.add(new Image("berlin", getClass().getResourceAsStream("../img/berlin.jpg")));
        images.add(new Image("paris", getClass().getResourceAsStream("../img/paris.jpg")));
      
        users.add(new User("lucas", "lucas"));
        users.add(new User("test", "test"));
        users.add(new User("bob", "bob"));
        users.add(new User("john", "john"));
        
    }
    
    public boolean authenticate(String login, String password) {
      return users.stream()
                  .anyMatch(user -> user.getLogin().equals(login) && user.getLogin().equals(password));
  }
    
    public List<Image> getImages() {
        return images;
    }
    
    public Image findImageByName(String name) {
        return images.stream()
                     .filter(img -> img.getName().equals(name))
                     .findFirst()
                     .orElse(null);
    }

    public void saveNoteToFile(Note note) {
      try   (BufferedWriter writer = new BufferedWriter(new FileWriter(file_path, true))) {
          writer.write(note.getUser().getName() + ";" + note.getImage().getName() + ";" + note.getNote());
          writer.newLine();
      } catch (IOException e) {
          System.err.println("Erreur lors de l'enregistrement de la note : " + e.getMessage());
      }
  }
    
    public InputStream getImageUrlByName(String name) {
        Image img = findImageByName(name);
        if (img != null) {
            return img.getInputStream();
        }
        return null;
    }
}
