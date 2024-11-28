package entite;

public class Note {

    private User user;
    private Image image;
    private int note;

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Note(User user, Image image, int note) {
        this.user = user;
        this.image = image;
        this.note = note;
    }

    public Note() {
    }
}
