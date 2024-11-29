package service;

import java.io.*;


import java.util.*;
import entite.Image;
import entite.Note;
import entite.User;

public class Modele {
  
  private User currentUser;


    private List<Image> images = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();
    private static final String FILE_PATH = "notes.txt";

    public Modele() {
        images.add(new Image("seoul", getClass().getResourceAsStream("../img/seoul.jpg")));
        images.add(new Image("rio", getClass().getResourceAsStream("../img/rio.jpg")));
        images.add(new Image("berlin", getClass().getResourceAsStream("../img/berlin.jpg")));
        images.add(new Image("paris", getClass().getResourceAsStream("../img/paris.jpg")));

        users.add(new User("lucas", "lucas"));
        users.add(new User("test", "test"));
        users.add(new User("bob", "bob"));
        users.add(new User("john", "john"));

        loadNotesFromFile();
    }
    

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }


    public boolean authenticate(String login, String password) {
      User user = users.stream()
                       .filter(u -> u.getLogin().equals(login) && u.getLogin().equals(password)) 
                       .findFirst()
                       .orElse(null);

      if (user != null) {
          currentUser = user;  
          return true;
      }
      return false;
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

    public InputStream getImageUrlByName(String name) {
        Image img = findImageByName(name);
        return img != null ? img.getInputStream() : null;
    }

    public int getNoteForImage(String imageName) {
        return notes.stream()
                .filter(note -> note.getImage().getName().equals(imageName))
                .map(Note::getNote)
                .findFirst()
                .orElse(0);
    }

    public void addOrUpdateNote(User user, String imageName, int noteValue) {
      Image image = findImageByName(imageName);
      if (image == null) return;

      Note existingNote = notes.stream()
              .filter(n -> n.getUser().equals(user) && n.getImage().equals(image))
              .findFirst()
              .orElse(null);

      if (existingNote != null) {
          existingNote.setNote(noteValue);
      } else {
          Note newNote = new Note(user, image, noteValue);
          notes.add(newNote);
      }

      saveNotesToFile();  
  }
    
    public Integer getNoteForCurrentUser(String imageName) {
      if (currentUser == null) {
          return null; 
      }

      Image image = findImageByName(imageName);
      if (image == null) {
          return null; 
      }

      return notes.stream()
                  .filter(n -> n.getUser().equals(currentUser) && n.getImage().equals(image))
                  .map(Note::getNote)
                  .findFirst()
                  .orElse(null); 
  }



    private void saveNotesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Note note : notes) {
                writer.write(note.getUser().getName() + ";" + note.getImage().getName() + ";" + note.getNote());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement des notes : " + e.getMessage());
        }
    }

    private void loadNotesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String userName = parts[0];
                    String imageName = parts[1];
                    int noteValue = Integer.parseInt(parts[2]);

                    User user = users.stream()
                            .filter(u -> u.getName().equals(userName))
                            .findFirst()
                            .orElse(null);

                    Image image = findImageByName(imageName);

                    if (user != null && image != null) {
                        notes.add(new Note(user, image, noteValue));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des notes : " + e.getMessage());
        }
    }
}
