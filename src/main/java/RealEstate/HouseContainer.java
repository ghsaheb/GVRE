package main.java.RealEstate;

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

        //add request header
        //con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response2 = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response2.append(inputLine);
        }
        in.close();
        //print result
        System.out.println(response2.toString());/*
        Object parsed = new JSONParser().parse(response2.toString());
        JSONArray array = (JSONArray)parsed;
        System.out.println("The 2nd element of array");
        System.out.println();*/
        houses.clear();
        this.houses.add(new House(1, 155, true, "تهران", true, 100, "09124", "alaki"));
        this.houses.add(new House(2, 144, false, "Tehran2", true, 122, "09123", "kheili alaki"));
        this.houses.add(new House(3, 123, true, "Tehran3", false, 111, "09122", "الکی"));
        this.houses.add(new House(4, 100, false, "Tehra4n", false, 144, "09121", "الکیییی"));
    }

    public ArrayList<House> getFiltered(){
        ArrayList<House> temp = new ArrayList<House>();
        for (int i = 0; i<houses.size();i++){
            temp.add(houses.get(i));
            System.out.println(temp.get(i).getId());
        }
        return temp;
    }
}
