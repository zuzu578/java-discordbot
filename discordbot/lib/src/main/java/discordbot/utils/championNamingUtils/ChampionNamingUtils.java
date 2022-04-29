package discordbot.utils.championNamingUtils;

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

import discordbot.utils.namingConventionUtils.NamingConventionUtils;

public class ChampionNamingUtils {
    URL url = null;
    HttpURLConnection conn = null;
    String responseData = "";
    BufferedReader br = null;
    StringBuffer sb = null;
    String returnData = "";
    String api_url = "http://ddragon.leagueoflegends.com/cdn/11.19.1/data/de_DE/champion.json";

    public List<Object> changeNameByIdx(org.json.simple.JSONArray jsonArray, String championName, String type) {

        HashMap<Object, Object> p1 = new HashMap<Object, Object>();
        HashMap<Object, Object> championInfo = new HashMap<Object, Object>();

        List<Object> championInfoData = new ArrayList<Object>();

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
                    championInfo.put("data", value);

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
            /**
             * type이 챔피언 검색일경우
             */
            if (championName.length() != 0 && type.equals("championInfo")) {
                // 첫글자가 소문자인경우 대문자로 치환한다.
                NamingConventionUtils n1 = new NamingConventionUtils();
                championName = n1.capitalizeFirstLetter(championName);

                String result = championInfo.get("data").toString();
                JSONParser parsing = new JSONParser();

                JSONObject jsonResult = (JSONObject) parsing.parse(result);

                Iterator iterator = jsonResult.keySet().iterator();

                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    if (jsonResult.get(key) instanceof JSONObject) {
                        JSONObject value = (JSONObject) jsonResult.get(key);
                        // 챔피언 검색

                        if (value.get("name").equals(championName)) {
                            championInfoData.add(value);
                            return championInfoData;
                        }

                    }

                }

            }
            /**
             * type 이 로테이션 챔피언 일경우
             */
            if (type.equals("rotation")) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    rotationList.add(p1.get(jsonArray.get(i).toString()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotationList;
    }

}
