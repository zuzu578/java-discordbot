package discordbot.embededUtils;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbededUtilsRotationChampions {
    public EmbedBuilder rotationChampionsListBuilder(Object championList) {
        // Create the EmbedBuilder instance
        EmbedBuilder eb = new EmbedBuilder();

        /*
         * Set the title:
         * 1. Arg: title as string
         * 2. Arg: URL as string or could also be null
         */
        eb.setTitle("이번주로테이션 챔피언", null);

        /*
         * Set the color
         */
        eb.setColor(Color.red);
        eb.setColor(new Color(0xF40C0C));
        eb.setColor(new Color(255, 0, 54));

        eb.setDescription(championList.toString());
        eb.addBlankField(false);

        eb.setImage(
                "https://opgg-static.akamaized.net/images/lol/champion/" +
                        championList
                        + ".png?image=q_auto,f_webp,w_164&v=1651122764127");

        return eb;

    }
}
