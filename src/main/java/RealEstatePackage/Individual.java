package RealEstatePackage;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class Individual extends User {
    private String phone;
    private int credit;
    private String username;
    private String password;
    private ArrayList<House> paidHouses;
    private static final String bankURL = "http://acm.ut.ac.ir/ieBank/pay";
    private static final String apiKey = "4e4d47e0-13c6-11e8-87b4-496f79ef1988";

    Individual(String name, String phone, int credit, String username, String password) {
        super(name);
        this.phone = phone;
        this.credit = credit;
        this.username = username;
        this.password = password;
        this.paidHouses = new ArrayList<House>();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
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

    private void increaseCredit(int inc){
        this.credit += inc;
        IndividualDatabaseController.getInstance().update(this);
    }

    private void decreaseCredit(int dec){
        this.credit -= dec;
        IndividualDatabaseController.getInstance().update(this);

    }

    public boolean addPaidHouse(String id) throws HouseNotFindException {
        House house = House.findHouse(id);
        for (House paidHouse : paidHouses) {
            if (paidHouse.getId().equals(house.getId()))
                return true;
        }
        if (this.credit >= 1000){
            this.decreaseCredit(1000);
            this.paidHouses.add(house);
            return true;
        }
        return false;
    }

    public boolean searchForHouse(String id) throws HouseNotFindException {
        House house = House.findHouse(id);
        for (House paidHouse : paidHouses) {
            if (paidHouse.getId().equals(id))
                return true;
        }
        return false;
    }

    public House findHouse(String id) throws IOException, ParseException {
        for (House ownedHouse : ownedHouses) {
            if (ownedHouse.getId().equals(id)) {
                return ownedHouse;
            }
        }
        return null;
    }

    public int addCredit(int amount) {
        try {
            URL obj = new URL(bankURL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("apiKey", apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            JSONObject data = new JSONObject();
            data.put("userId", this.username);
            data.put("value", amount);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data.toString());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + bankURL);
            System.out.println("Response Code : " + responseCode);
            if (responseCode == 200) {
                this.increaseCredit(amount);
            }
            return responseCode;
        } catch (ProtocolException e) {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        } catch (MalformedURLException e) {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        } catch (IOException e) {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
    }
}
