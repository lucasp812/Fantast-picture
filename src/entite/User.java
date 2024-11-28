package entite;

public class User {
  
  String name; 
  String login;
  
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getLogin() {
    return login;
  }
  public void setLogin(String login) {
    this.login = login;
  }
  
  
  public User(String name, String login) {
    this.name = name;
    this.login = login;
  }

  
  
  

}
