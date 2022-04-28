package discordbot;

import java.util.List;

import javax.security.auth.login.LoginException;

import discordbot.embededUtils.EmbededUtilsRotationChampions;
import discordbot.embededUtils.EmbededUtilsSummonerInfo;
import discordbot.lolapiutils.LolApiUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONObject;

public class DiscordBot extends ListenerAdapter {
	private boolean isCalled = false;

	private final static String token = "OTY5MTA0OTIxMDMzMDY4NTU0.YmojSg.mQafcUNlUcC_Z904VQ59Wvmk3NQ";

	public static void main(String[] args) throws LoginException {
		JDA jda = JDABuilder.createDefault(token).build();

		jda.addEventListener(new DiscordBot());
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		LolApiUtils api = new LolApiUtils();

		Message msg = event.getMessage();
		MessageChannel channel = event.getChannel();

		if (event.getAuthor().isBot()) {
			return;
		}
		if (msg.getContentRaw().contains("!전적검색")) {
			isCalled = true;
			channel.sendMessage("닉네임을 입력해주세요.😇").queue();
		}
		if (msg.getContentRaw().contains("!이번주로테이션")) {
			isCalled = true;
			List<Object> rotationChampionsList = api.getRotationChampionList();

			EmbededUtilsRotationChampions em = new EmbededUtilsRotationChampions();
			EmbedBuilder eb;
			for (int i = 0; i < rotationChampionsList.size(); i++) {
				eb = em.rotationChampionsListBuilder(rotationChampionsList.get(i));
				channel.sendMessage(eb.build()).queue();
			}

			isCalled = false;

		}
		if (isCalled == true && !msg.getContentRaw().contains("!")) {
			JSONObject result = api.getSummonerInfo(msg.getContentRaw());

			if (result.size() == 0) {
				channel.sendMessage("존재하지않는 소환사입니다. 다시확인해주세요.😱").queue();
				return;
			}

			EmbededUtilsSummonerInfo em = new EmbededUtilsSummonerInfo();
			EmbedBuilder eb = em.summonerInfoBuilder(result);

			channel.sendMessage(eb.build()).queue();

			isCalled = false;
		}

	}

}
