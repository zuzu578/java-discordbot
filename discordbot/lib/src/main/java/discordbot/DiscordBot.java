package discordbot;

import java.util.List;

import javax.security.auth.login.LoginException;

import discordbot.utils.championNamingUtils.ChampionNamingUtils;
import discordbot.utils.embededUtils.*;
import discordbot.utils.embededUtils.EmbededUtilsSummonerInfo;
import discordbot.utils.lolapiutils.LolApiUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

public class DiscordBot extends ListenerAdapter {
	private boolean isCalled = false;

	private String type = "";
	private final static String token = "";

	public static void main(String[] args) throws LoginException {
		JDA jda = JDABuilder.createDefault(token).build();

		jda.addEventListener(new DiscordBot());
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		if (event.getAuthor().isBot()) {
			return;
		}

		LolApiUtils api = new LolApiUtils();

		Message msg = event.getMessage();
		MessageChannel channel = event.getChannel();

		EmbededUtilsHello sayHello = new EmbededUtilsHello();
		EmbedBuilder hello = sayHello.sayHello();
		// 첫 인삿말
		channel.sendMessage(hello.build()).queue();

		if (msg.getContentRaw().contains(("!챔피언정보"))) {
			isCalled = true;
			type = "champion";
			channel.sendMessage("챔피언 영문이름을 입력해주세요.😇").queue();
		}
		if (msg.getContentRaw().contains("!전적검색")) {
			isCalled = true;
			type = "search";
			channel.sendMessage("닉네임을 입력해주세요.😇").queue();
		}
		if (msg.getContentRaw().contains("!이번주로테이션")) {

			isCalled = true;
			type = "rotation";

			List<Object> rotationChampionsList = api.getRotationChampionList();

			EmbededUtilsRotationChampions em = new EmbededUtilsRotationChampions();
			EmbedBuilder eb;

			for (int i = 0; i < rotationChampionsList.size(); i++) {
				eb = em.rotationChampionsListBuilder(rotationChampionsList.get(i));
				channel.sendMessage(eb.build()).queue();
			}

			isCalled = false;

		}
		if (isCalled == true && !msg.getContentRaw().contains("!") && type.equals("search")) {
			JSONObject result = api.getSummonerInfo(msg.getContentRaw());

			if (result.size() == 0) {
				channel.sendMessage("존재하지않는 소환사입니다. 다시확인해주세요.😱").queue();
				return;
			}

			EmbededUtilsSummonerInfo em = new EmbededUtilsSummonerInfo();
			EmbedBuilder eb = em.summonerInfoBuilder(result, msg.getContentRaw());

			channel.sendMessage(eb.build()).queue();

			isCalled = false;
		}

		if (isCalled == true && !msg.getContentRaw().contains("!") && type.equals("champion")) {

			ChampionNamingUtils c1 = new ChampionNamingUtils();
			org.json.simple.JSONArray a1 = new org.json.simple.JSONArray();
			List<Object> result = c1.changeNameByIdx(a1, msg.getContentRaw(), "championInfo");

			String jsonLiteral = "";
			for (int i = 0; i < result.size(); i++) {
				jsonLiteral = result.get(i).toString();
			}

			JSONObject json;
			EmbededUtilsChampionInfo e1 = new EmbededUtilsChampionInfo();
			try {

				json = (JSONObject) new JSONParser().parse(jsonLiteral);
				EmbedBuilder em = e1.BuidingChampionInfo(json, msg.getContentRaw());
				channel.sendMessage(em.build()).queue();

			} catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
				channel.sendMessage("챔피언 이름을 다시한번 확인후 시도해주세요!😱").queue();
			}

			isCalled = false;
		}

	}

}
