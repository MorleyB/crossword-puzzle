package com.company;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonFileReader {

    public void read() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(".\\resources\\data.json");
        Object obj = parser.parse(reader);
        JSONObject data = (JSONObject) obj;
    }
}
