package discordbot;

import javax.security.auth.login.LoginException;

import discordbot.lolapiutils.LolApiUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import org.json.simple.JSONObject;

public class DiscordBot extends ListenerAdapter{
	private boolean isCalled = false;
	
	private final static String token = "OTY5MTA0OTIxMDMzMDY4NTU0.YmojSg.rM_PgmWlNG_DPepaTHWGcN9t7nk";
	
	public static void main(String[] args) throws LoginException {
		  JDA jda = JDABuilder.createDefault(token).build();
	       
	        jda.addEventListener(new DiscordBot());
	}
	 	@Override
	    public void onMessageReceived(MessageReceivedEvent event){
	 		 
	 	    LolApiUtils api = new LolApiUtils();
	 		 
	 	    Message msg = event.getMessage();
	 	    MessageChannel channel = event.getChannel();
	 		 
	 	    if(event.getAuthor().isBot()) {
	 	    	return;
	 	    }
	 		 if(msg.getContentRaw().contains("!전적검색")) {
	 			isCalled = true;
	 			 channel.sendMessage("닉네임을 입력해주세요.🙂").queue();
	 		 }
	 		 if(isCalled == true && !msg.getContentRaw().contains("!")) {
	 			 JSONObject result = api.getSummonerInfo(msg.getContentRaw());
	 			 channel.sendMessage("소환사이름"+":"+result.get("summonerName")+","+"레벨"+":"+result.get("summonerLevel")
	 			 +"랭크" +":"+ result.get("tier")+","+result.get("rank")+","+"LP"+result.get("leaguePoints") +","+"승" +":" +result.get("wins") + "패" +":"+ result.get("losses")).queue();
	 			 
	 			 isCalled = false;
	 		 }
	         
	    }

}
