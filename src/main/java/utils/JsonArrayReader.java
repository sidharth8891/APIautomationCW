package utils;

import org.apache.commons.io.FileUtils;
import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class JsonArrayReader {
    public static void JsonArray() throws IOException, ParseException {
        File filename = new File("resources//TestData//testdata.json");
        String json = FileUtils.readFileToString(filename, "UTF-8");

        //converting String to JSONObject
        org.json.JSONObject jsonObject = new JSONObject(json);

        // converting JSONObject to JSONArray
        JSONArray languages = jsonObject.getJSONArray("languages");
        System.out.println(languages.getString(2));
        for (int i = 0; i < languages.length(); i++) {
            String language = languages.getString(i);
            System.out.println(language);
        }
    }

    public static JSONObject JsonObject() throws IOException, ParseException {
        File filename = new File("resources//TestData//testdata.json");
        String json = FileUtils.readFileToString(filename, "UTF-8");

        //converting String to JSONObject
        org.json.JSONObject jsonObject = new JSONObject(json);

        // converting JSONObject to JSONArray
        return jsonObject;
    }

    public static String getTestData(String key) throws IOException, org.json.simple.parser.ParseException {
        String testDataValue;
        return testDataValue = (String) JsonObject().get(key);//input is the key
    }
}
