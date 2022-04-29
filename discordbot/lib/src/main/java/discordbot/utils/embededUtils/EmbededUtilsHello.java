package discordbot.utils.embededUtils;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbededUtilsHello {

    public EmbedBuilder sayHello() {
        // Create the EmbedBuilder instance
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("롤전적검색기", "https://github.com/zuzu578/java-discordbot");

        eb.setColor(Color.red);
        eb.setColor(new Color(0xF40C0C));
        eb.setColor(new Color(255, 0, 54));

        // eb.setDescription("!전적검색 명령어를 봇에게 명령하면 간단한 전적을검색할수있습니다🙂.");
        // eb.setDescription("!챔피언정보 명령어를 봇에게 명령하면 챔피언의 기본 stat 정보를 알려줍니다🤑.");
        // eb.setDescription("!이번주로테이션 명령어를 봇에게 명령하면 이번주 로테이션챔피언 정보를 알려줍니다😏.");

        eb.addField("안녕하세요 롤전적검색기입니다.", "test of field", true);
        eb.addField("!전적검색 명령어를 봇에게 명령하면 간단한 전적을검색할수있습니다🙂.", "", true);
        eb.addField("!챔피언정보 명령어를 봇에게 명령하면 챔피언의 기본 stat 정보를 알려줍니다🤑.", "", true);
        eb.addField("!이번주로테이션 명령어를 봇에게 명령하면 이번주 로테이션챔피언 정보를 알려줍니다😏.", "", true);

        // eb.addBlankField(false);

        eb.setAuthor("by zuzu578", null,
                "https://github.com/zuzu578/java-discordbot");

        // eb.setFooter("Text",
        // "https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/zekroBot_Logo_-_round_small.png");
        // eb.setImage("https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/logo%20-%20title.png");
        // eb.setThumbnail("https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/logo%20-%20title.png");

        return eb;
    }
}
