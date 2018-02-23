package RealEstate;

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
import java.util.ArrayList;

public class HouseContainer {
    private ArrayList<House> houses;
    private ArrayList<House> localHouses;
    private static HouseContainer houseContainer = null;

    private HouseContainer() {
        houses = new ArrayList<House>();
        localHouses = new ArrayList<House>();
    }

    public static HouseContainer getHouseContainer(){
        if (houseContainer == null){
            houseContainer = new HouseContainer();
        }
        return houseContainer;
    }

    public void getHouses() throws Exception {
        String url = "http://acm.ut.ac.ir/khaneBeDoosh/house";
        HttpURLConnection con = getHttpURLConnection(url);
        StringBuffer response = getStringBuffer(con);
        JSONArray housesData = getJsonArray(response);
        addHousesToContainer(housesData);
    }

    private void addHousesToContainer(JSONArray housesData) throws IOException {
        houses.clear();
        for (int i=0;i<housesData.size();i++) {
            JSONObject temp = (JSONObject)housesData.get(i);
            House s = readJsonWithObjectMapper(temp.toString());
            this.houses.add(s);
        }
    }

    private HttpURLConnection getHttpURLConnection(String url) throws IOException {
        URL obj = new URL(url);
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
        JSONArray housesData = (JSONArray) jsonObject.get("data");
        return housesData;
    }

    private JSONObject getJsonObject(StringBuffer response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
        JSONObject house = (JSONObject) jsonObject.get("data");
        return house;
    }

    public ArrayList<House> getFiltered(long area, boolean dealType, String buildingType, int maxPrice){
        ArrayList<House> filtered = new ArrayList<House>();
        for (int i = 0; i<houses.size();i++){
            House temp = houses.get(i);
            applyFilter(area, dealType, buildingType, maxPrice, filtered, temp);
        }
        for (int i = 0; i<localHouses.size();i++){
            House temp = localHouses.get(i);
            applyFilter(area, dealType, buildingType, maxPrice, filtered, temp);
        }
        return filtered;
    }

    private void applyFilter(long area, boolean dealType, String buildingType, int maxPrice, ArrayList<House> filtered, House temp) {
        if (temp.getArea() >= area && temp.isDealType() == dealType && temp.getBuildingType().equals(buildingType)){
            if (!temp.isDealType() && temp.getPrice().getSellPrice() <= maxPrice){
                filtered.add(temp);

            }
            else if (temp.isDealType() && temp.getPrice().getRentPrice() <= maxPrice){
                filtered.add(temp);
            }
        }
    }

    public House findHouse(String id) throws HouseNotFindException, IOException, ParseException {
        for (int i=0;i<houses.size();i++){
            if (houses.get(i).getId().equals(id)) {
                String url = "http://acm.ut.ac.ir/khaneBeDoosh/house/" + houses.get(i).getId();
                HttpURLConnection con = getHttpURLConnection(url);
                StringBuffer response = getStringBuffer(con);
                JSONObject temp = getJsonObject(response);
                House s = readJsonWithObjectMapper(temp.toString());
                return s;
            }
        }
        for (int i=0;i<localHouses.size();i++){
            if (localHouses.get(i).getId().equals(id)){
                return localHouses.get(i);
            }
        }
        throw new HouseNotFindException();
    }

    public House readJsonWithObjectMapper(String s) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        House h = objectMapper.readValue(s, House.class);
        return h;
    }

    public void addNewHouse(House h){
        this.localHouses.add(h);
    }
}
