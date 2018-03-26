package RealEstatePackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RealEstate extends User {
    private String URL;

    RealEstate(String name, String URL) {
        super(name);
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    void updateHouses() {
        try {
            HttpURLConnection con = getHttpURLConnection(this.URL);
            StringBuffer response = getStringBuffer(con);
            JSONArray housesData = getJsonArray(response);
            addHousesToContainer(housesData);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addHousesToContainer(JSONArray housesData) throws IOException {
        ownedHouses.clear();
        for (Object aHousesData : housesData) {
            JSONObject temp = (JSONObject) aHousesData;
            House s = readJsonWithObjectMapper(temp.toString());
            this.addHouse(s);
        }
    }

    private HttpURLConnection getHttpURLConnection(String url) throws IOException {
        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        return con;
    }

    private StringBuffer getStringBuffer(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    private JSONArray getJsonArray(StringBuffer response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
        return (JSONArray) jsonObject.get("data");
    }

    private JSONObject getJsonObject(StringBuffer response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
        return (JSONObject) jsonObject.get("data");
    }

    private House readJsonWithObjectMapper(String s) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(s, House.class);
    }

    public House findHouse(String id) {
        try {
            for (House ownedHouse : ownedHouses) {
                if (ownedHouse.getId().equals(id)) {
                    String URL = this.URL + "/" + ownedHouse.getId();
                    HttpURLConnection con = getHttpURLConnection(URL);
                    StringBuffer response = getStringBuffer(con);
                    JSONObject temp = getJsonObject(response);
                    return readJsonWithObjectMapper(temp.toString());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
