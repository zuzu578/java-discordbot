package discordbot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import discordbot.TaikoVo.TaikoDiscordBotVO;
import discordbot.utils.crawlling.TaikoSongCrawllingUtils;
import discordbot.utils.dataBase.Dbcp;
import discordbot.utils.excelgenerator.ExcelGenerator;
import discordbot.utils.messagebuilder.HelloMessageBuilder;
import discordbot.utils.messagebuilder.SongInfoMessageBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBot extends ListenerAdapter {

	private final static String token = "";
	private Dbcp db = new Dbcp();
	private boolean isCalled = false;
	private String type = "";
	private boolean isFirstHello = false;

	public static void main(String[] args) throws LoginException {
		JDA jda = JDABuilder.createDefault(token).build();

		jda.addEventListener(new DiscordBot());

		// 초기 데이터 크롤링

		// TaikoSongCrawllingUtils t1 = new TaikoSongCrawllingUtils();
		// try {
		// t1.startCrawling();
		// } catch (IOException e) {

		// e.printStackTrace();
		// }
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		Message msg = event.getMessage();
		MessageChannel channel = event.getChannel();
		HelloMessageBuilder sayHello = new HelloMessageBuilder();
		SongInfoMessageBuilder songInfo = new SongInfoMessageBuilder();
		EmbedBuilder hello = sayHello.sayHello();

		// 봇끼리 대화 감지 금지
		if (event.getAuthor().isBot()) {
			return;
		}

		// guild ban
		if (msg.getContentRaw().equals("!밴")) {

			channel.sendMessage("밴할 멤버를 태그해줘.").queue();
			isCalled = true;
			type = "ban";
		}

		if (msg.getContentRaw().equals("!킥")) {

			channel.sendMessage("킥 할 유저를 태그해줘.").queue();
			isCalled = false;
			type = "kick";

		}
		// kick
		if (isCalled == true && !msg.getContentRaw().contains("!") && type.equals("kick")) {

			Guild guild = event.getGuild();
			Member target = event.getMessage().getMentionedMembers().get(0);

			if (event.getMessage().getMentionedMembers().isEmpty()) {
				System.out.println(" 닉네임을 입력해주세요.");
			}
			// if exists
			guild.kick(target, "킥 사유").queue();
			isCalled = false;
			channel.sendMessage("잘가시게~~~~").queue();

		}
		if (isCalled == true && !msg.getContentRaw().contains("!") && type.equals("ban")) {

			Guild guild = event.getGuild();
			Member target = event.getMessage().getMentionedMembers().get(0);

			if (event.getMessage().getMentionedMembers().isEmpty()) {
				System.out.println(" 닉네임을 입력해주세요.");
			}
			// if exists
			guild.ban(target, 0, "ban command").queue();
			isCalled = false;

			channel.sendMessage("잘가시게~~~~").queue();
		}

		if (!isFirstHello || msg.getContentRaw().equals("!help")) {
			channel.sendMessage(hello.build()).queue();
			isFirstHello = true;
		}

		if (msg.getContentRaw().equals("!수록곡정보 다운로드")) {

			HashMap<String, ArrayList<String>> result = db.getAllSong();

			ExcelGenerator excel = new ExcelGenerator();

			try {
				excel.excelGenerate(result);
				System.out.println("success Generate ExcelData");
			} catch (IOException e) {
				e.printStackTrace();
			}

			channel.sendMessage("수록곡 엑셀파일 이다 동!!").addFile(new File("/Users/helloworld/git/java-discordbot/taiko.xlsx"))
					.queue();
			isCalled = false;

		}

		if (msg.getContentRaw().equals("!곡 난이도 검색")) {
			channel.sendMessage("곡 이름을 입력해라 동!").queue();
			isCalled = true;
			type = "search";
		}

		if (isCalled == true && !msg.getContentRaw().contains("!") && type.equals("search")) {

			List<TaikoDiscordBotVO> result = db.getSong(msg.getContentRaw());

			if (result.get(0).getDifficult() == null || result.get(0).getSongGenre() == null
					|| result.get(0).getSongName() == null) {

				channel.sendMessage("해당곡을 못찾겠다동...").queue();

				return;

			}

			EmbedBuilder eb;

			for (int i = 0; i < result.size(); i++) {
				eb = songInfo.returnSongInfo(result);
				channel.sendMessage(eb.build()).queue();
			}

			isCalled = false;
		}

		if (msg.getContentRaw().equals("!전체곡수")) {
			isCalled = true;
			int result = db.getSongCount("all");
			channel.sendMessage("아케이드전체 수록곡 수는 총" + Integer.toString(result) + "개 있다동!").queue();
			isCalled = false;

		} else if (msg.getContentRaw().equals("!남코오리지날 곡 수")) {
			isCalled = true;
			int result = db.getSongCount("ナムコオリジナル");
			channel.sendMessage("남코오리지날 곡 수는 총 " + Integer.toString(result) + "개 있다동!").queue();
			isCalled = false;

		} else if (msg.getContentRaw().equals("!jpop 곡 수")) {

			isCalled = true;
			int result = db.getSongCount("ポップス");
			channel.sendMessage("jpop 곡 수는 총 " + Integer.toString(result) + "개 있다동!").queue();
			isCalled = false;

		} else if (msg.getContentRaw().equals("!버라이어티 곡 수")) {

			isCalled = true;
			int result = db.getSongCount("バラエティ");
			channel.sendMessage("버라이어티 곡 수는 총" + Integer.toString(result) + "개 있다동!").queue();
			isCalled = false;

		} else if (msg.getContentRaw().equals("!키즈 곡 수")) {

			isCalled = true;
			int result = db.getSongCount("キッズ");
			channel.sendMessage("키즈 곡 수는 총" + Integer.toString(result) + "개 있다동!").queue();
			isCalled = false;

		} else if (msg.getContentRaw().equals("!애니메이션 곡 수")) {

			isCalled = true;
			int result = db.getSongCount("アニメ");
			channel.sendMessage("애니메이션 곡 수는 총" + Integer.toString(result) + "개 있다동!").queue();
			isCalled = false;

		} else if (msg.getContentRaw().equals("!게임뮤직 곡 수")) {

			isCalled = true;
			int result = db.getSongCount("ゲームミュージック");
			channel.sendMessage("게임뮤직 곡 수는 총" + Integer.toString(result) + "개 있다동!").queue();
			isCalled = false;

		} else if (msg.getContentRaw().equals("!클래식 곡 수")) {

			isCalled = true;
			int result = db.getSongCount("クラシック");
			channel.sendMessage("클래식 곡 수는 총" + Integer.toString(result) + "개 있다동!").queue();
			isCalled = false;

		} else if (msg.getContentRaw().equals("!보컬로이드 곡 수")) {

			isCalled = true;
			int result = db.getSongCount("ボーカロイド™曲");
			channel.sendMessage("보컬로이드 곡 수는 총" + Integer.toString(result) + "개 있다동!").queue();
			isCalled = false;

		}
	}

}
