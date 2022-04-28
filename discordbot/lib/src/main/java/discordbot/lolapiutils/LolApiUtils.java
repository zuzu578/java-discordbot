package discordbot.lolapiutils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;

import discordbot.championNamingUtils.ChampionNamingUtils;

public class LolApiUtils {
	private final static String api_key = "RGAPI-1ebc41c2-4d8b-4b6a-bf07-be8032b471f1";

	URL url = null;
	HttpURLConnection conn = null;
	String responseData = "";
	BufferedReader br = null;
	StringBuffer sb = null;
	String returnData = "";

	public JSONObject getSummonerInfo(String summonerName) {

		String api_url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/";

		JSONObject result = new JSONObject();

		try {

			url = new URL(api_url + summonerName + "?" + "api_key=" + api_key);
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
			JSONObject jsonObject = (JSONObject) parser.parse(response);

			JSONArray arr = getSummonerLeague(jsonObject.get("id").toString());

			result.put("summonerLevel", jsonObject.get("summonerLevel"));
			result.put("profileIconId", jsonObject.get("profileIconId"));

			JSONObject element;
			for (int i = 0; i < arr.size(); i++) {
				element = (JSONObject) arr.get(i);
				result.put("wins", element.get("wins"));
				result.put("summonerName", element.get("summonerName"));
				result.put("leaguePoints", element.get("leaguePoints"));
				result.put("losses", element.get("losses"));
				result.put("tier", element.get("tier"));
				result.put("rank", element.get("rank"));
				result.put("queueType", element.get("queueType"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public JSONArray getSummonerLeague(String encryptedSummonerId) {
		String api_url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/";

		JSONArray jsonArray = new JSONArray();
		try {

			url = new URL(api_url + encryptedSummonerId + "?" + "api_key=" + api_key);
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
			jsonArray = (JSONArray) parser.parse(response);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonArray;
	}

	public List<Object> getRotationChampionList() {
		String api_url = "https://kr.api.riotgames.com/lol/platform/v3/champion-rotations";

		List<Object> result = new ArrayList<Object>();

		try {

			url = new URL(api_url + "?" + "api_key=" + api_key);
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

			ChampionNamingUtils c1 = new ChampionNamingUtils();

			result = c1.changeNameByIdx((JSONArray) j1.get("freeChampionIds"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}
}
