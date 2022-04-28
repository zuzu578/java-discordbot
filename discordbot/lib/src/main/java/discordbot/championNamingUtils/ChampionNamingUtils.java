package discordbot.championNamingUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ChampionNamingUtils {
    URL url = null;
    HttpURLConnection conn = null;
    String responseData = "";
    BufferedReader br = null;
    StringBuffer sb = null;
    String returnData = "";
    String api_url = "http://ddragon.leagueoflegends.com/cdn/10.16.1/data/de_DE/champion.json";

    public List<Object> changeNameByIdx(org.json.simple.JSONArray jsonArray) {

        HashMap<Object, Object> p1 = new HashMap<Object, Object>();

        List<Object> rotationList = new ArrayList<Object>();

        try {
            url = new URL(api_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(inputLine);
            }
            bufferedReader.close();

            String response = stringBuffer.toString();

            JSONParser parser = new JSONParser();

            JSONObject j1 = (JSONObject) parser.parse(response);

            Iterator iter = j1.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                if (j1.get(key) instanceof JSONObject) {
                    JSONObject value = (JSONObject) j1.get(key);

                    Iterator iter2 = value.keySet().iterator();
                    while (iter2.hasNext()) {
                        String key2 = (String) iter2.next();
                        if (value.get(key2) instanceof JSONObject) {
                            JSONObject value2 = (JSONObject) value.get(key2);
                            p1.put(value2.get("key"), value2.get("id"));

                        }
                    }

                }
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                System.out.println("champion Name =>" + p1.get(jsonArray.get(i).toString()));
                rotationList.add(p1.get(jsonArray.get(i).toString()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotationList;
    }

}
