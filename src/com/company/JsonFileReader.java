package com.company;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JsonFileReader {

    LinkedList<?> data;

    public LinkedList read() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("src/resources/data.json");

        // create custom container for parsed json
        ContainerFactory containerFactory = new ContainerFactory() {
            @Override
            public Map createObjectContainer() {
                return new LinkedHashMap<>();
            }
            @Override
            public List creatArrayContainer() {
                return new LinkedList<>();
            }
        };

        try {
            data = (LinkedList) parser.parse(reader, containerFactory);
            data.forEach((i)->System.out.println("Item : " + i));

        } catch(ParseException pe) {
            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }
        return data;
    }
}
