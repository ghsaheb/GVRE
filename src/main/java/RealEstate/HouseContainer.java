package main.java.RealEstate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


import java.util.ArrayList;
import java.net.*;
import java.io.*;

public class HouseContainer {
    private ArrayList<House> houses;
    private static HouseContainer houseContainer = null;

    private HouseContainer() {
        houses = new ArrayList<House>();
    }

    public static HouseContainer getHouseContainer(){
        if (houseContainer == null){
            houseContainer = new HouseContainer();
        }
        return houseContainer;
    }

    public void getHouses() throws Exception {
        String url = "http://acm.ut.ac.ir/khaneBeDoosh/house";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONArray housesData = getJsonArray(response);


        houses.clear();
        for (int i=0;i<housesData.size();i++) {
            JSONObject temp = (JSONObject)housesData.get(i);
            House s = readJsonWithObjectMapper(temp.toString());
            this.houses.add(s);
        }
    }

    private JSONArray getJsonArray(StringBuffer response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
        JSONArray housesData = (JSONArray) jsonObject.get("data");
        return housesData;
    }

    public ArrayList<House> getFiltered(){
        ArrayList<House> temp = new ArrayList<House>();
        for (int i = 0; i<houses.size();i++){
            temp.add(houses.get(i));
        }
        return temp;
    }

    public House findHouse(String id) throws HouseNotFindException {
        for (int i=0;i<houses.size();i++){
            if (houses.get(i).getId().equals(id))
                return houses.get(i);
        }
        throw new HouseNotFindException();
    }

    public House readJsonWithObjectMapper(String s) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        House h = objectMapper.readValue(s, House.class);
        return h;
    }
}
