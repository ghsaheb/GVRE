package RealEstate;

public class Individual extends User {
    private String phone;
    private int balance;
    private String username;
    private String password;

    public Individual(String name, int id, String phone, int balance, String username, String password) {
        super(name, id);
        this.phone = phone;
        this.balance = balance;
        this.username = username;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void increaseBalance(int inc){
        this.balance += inc;
    }

    public void decreaseBalance(int dec){
        this.balance -= dec;
    }
}
