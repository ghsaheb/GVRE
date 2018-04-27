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
    private Thread realEstateThread;

    private class RealEstateThread implements Runnable {
        public void run(){
            System.out.println("thread started");
            while (true) {
                long expireTime = updateHouses();
                System.out.println("Updated:  "+ (expireTime-System.currentTimeMillis()));
                try {
                    if (expireTime - System.currentTimeMillis() > 0) {
                        Thread.sleep(expireTime - System.currentTimeMillis());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    RealEstate(String name, String URL, boolean isThreadNeeded) {
        super(name);
        this.URL = URL;
        if (isThreadNeeded) {
            realEstateThread = new Thread(new RealEstateThread());
            realEstateThread.start();
        }
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    long updateHouses() {
        try {
            HouseDatabaseController.getInstance().delete(this);
            HttpURLConnection con = getHttpURLConnection(this.URL);
            StringBuffer response = getStringBuffer(con);
            long expireTime = getExpireTime(response);
            System.out.println("expire time: "+expireTime);
            JSONArray housesData = getJsonArray(response);
            addHousesToDatabase(housesData);
            return expireTime;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void addHousesToDatabase(JSONArray housesData) throws IOException {
        for (Object HousesData : housesData) {
            JSONObject temp = (JSONObject) HousesData;
            House house = readJsonWithObjectMapper(temp.toString());
            HouseDatabaseController.getInstance().insert(house,this);
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

    private long getExpireTime(StringBuffer response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
        System.out.println("json object : "+jsonObject.toString());
        return (Long) jsonObject.get("expireTime");
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
            String URL = this.URL + "/" + id;
            HttpURLConnection con = getHttpURLConnection(URL);
            StringBuffer response = getStringBuffer(con);
            JSONObject temp = getJsonObject(response);
            return readJsonWithObjectMapper(temp.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
