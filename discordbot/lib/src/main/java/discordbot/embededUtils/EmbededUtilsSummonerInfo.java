package discordbot.embededUtils;

import java.awt.Color;
import java.util.List;

import org.json.simple.JSONObject;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbededUtilsSummonerInfo {

        private EmbedBuilder eb = new EmbedBuilder();

        public EmbedBuilder summonerInfoBuilder(JSONObject object) {

                System.out.println("fqfqwfqwfq" + object.get("tier").toString().toLowerCase());
                eb.setColor(Color.red);
                eb.setColor(new Color(0xF40C0C));
                eb.setColor(new Color(255, 0, 54));

                eb.setImage("https://opgg-static.akamaized.net/images/medals/"
                                + object.get("tier").toString().toLowerCase()
                                + "_1.png?image=q_auto&image=q_auto,f_webp,w_auto&v=1651122763930");

                // eb.addField("Title of field", "test of field", false);
                eb.setAuthor(object.get("summonerName").toString() + "Lv." + object.get("summonerLevel"), null,
                                "https://opgg-static.akamaized.net/images/profile_icons/profileIcon"
                                                + object.get("profileIconId")
                                                + ".jpg?image=q_auto&image=q_auto,f_webp,w_auto&v=1651122763930");

                eb.setDescription("승" + ":" + object.get("wins").toString() + "패" + ":" + object.get("losses") +
                                "랭크" + ":" + object.get("tier").toString() + " " + object.get("rank") + "/"
                                + object.get("leaguePoints")
                                + "LP");

                /*
                 * Add spacer like field
                 * Arg: inline mode true / false
                 */

                // eb.setThumbnail("https://opgg-static.akamaized.net/images/medals/" +
                // object.get("tier")
                // + "_2.png?image=q_auto&image=q_auto,f_webp,w_auto&v=1651122763930");

                return eb;

        }
}
