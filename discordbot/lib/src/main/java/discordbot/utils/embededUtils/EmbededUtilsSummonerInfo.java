package discordbot.utils.embededUtils;

import java.awt.Color;

import org.json.simple.JSONObject;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbededUtilsSummonerInfo {

        private EmbedBuilder eb = new EmbedBuilder();

        public EmbedBuilder summonerInfoBuilder(JSONObject object, String summonerName) {
                try {
                        eb.setColor(Color.red);
                        eb.setColor(new Color(0xF40C0C));
                        eb.setColor(new Color(255, 0, 54));
                        eb.setAuthor(object.get("summonerName").toString() + "Lv." + object.get("summonerLevel"), null,
                                        "https://opgg-static.akamaized.net/images/profile_icons/profileIcon"
                                                        + object.get("profileIconId")
                                                        + ".jpg?image=q_auto&image=q_auto,f_webp,w_auto&v=1651122763930");
                        eb.setThumbnail("https://opgg-static.akamaized.net/images/medals/"
                                        + object.get("tier").toString().toLowerCase()
                                        + "_1.png?image=q_auto&image=q_auto,f_webp,w_auto&v=1651122763930");

                        eb.addField("승", object.get("wins").toString(), true);
                        eb.addField("패", object.get("losses").toString(), true);
                        eb.addField("랭크", object.get("tier").toString(), true);
                        eb.addField("티어", object.get("rank").toString(), true);
                        eb.addField("LP", object.get("leaguePoints").toString(), true);

                } catch (Exception e) {
                        eb.setColor(Color.red);
                        eb.setColor(new Color(0xF40C0C));
                        eb.setColor(new Color(255, 0, 54));
                        eb.setDescription(summonerName + "님은 언랭입니다.");
                        eb.setAuthor(summonerName + "Lv." + object.get("summonerLevel"), null,
                                        "https://opgg-static.akamaized.net/images/profile_icons/profileIcon"
                                                        + object.get("profileIconId")
                                                        + ".jpg?image=q_auto&image=q_auto,f_webp,w_auto&v=1651122763930");
                        eb.setThumbnail("https://opgg-static.akamaized.net/images/medals/default.png?image=q_auto&image=q_auto,f_webp,w_auto&v=1651209490425");
                }

                return eb;

        }
}
