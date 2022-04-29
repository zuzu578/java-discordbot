package discordbot.utils.embededUtils;

import java.awt.Color;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbededUtilsChampionInfo {

    public EmbedBuilder BuidingChampionInfo(org.json.simple.JSONObject json, String championName) {

        List<Object> champInfo = new ArrayList<Object>();
        Iterator iter = json.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (json.get(key) instanceof JSONObject) {
                JSONObject value = (JSONObject) json.get(key);

                if (value.get("hp") != null) {
                    champInfo.add(value.get("hp"));
                }
                if (value.get("attackdamage") != null) {
                    champInfo.add(value.get("attackdamage"));
                }
                if (value.get("armor") != null) {
                    champInfo.add(value.get("armor"));
                }

                if (value.get("attackspeed") != null) {
                    champInfo.add(value.get("attackspeed"));
                }

                if (value.get("movespeed") != null) {
                    champInfo.add(value.get("movespeed"));
                }

                if (value.get("attackrange") != null) {
                    champInfo.add(value.get("attackrange"));
                }

                if (value.get("attackspeedperlevel") != null) {
                    champInfo.add(value.get("attackspeedperlevel"));
                }

                if (value.get("hpregenperlevel") != null) {
                    champInfo.add(value.get("hpregenperlevel"));
                }

            }
        }

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("챔피언기본능력치", null);
        eb.setColor(Color.red);
        eb.setColor(new Color(0xF40C0C));
        eb.setColor(new Color(255, 0, 54));

        eb.addField("기본체력", champInfo.get(0).toString(), true);
        eb.addField("기본공격력", champInfo.get(1).toString(), true);
        eb.addField("기본방어력", champInfo.get(2).toString(), true);
        eb.addField("기본공격속도", champInfo.get(3).toString(), true);
        eb.addField("기본 이동속도", champInfo.get(4).toString(), true);
        eb.addField("사거리", champInfo.get(5).toString(), true);
        eb.addField("레벨당 공격력", champInfo.get(6).toString(), true);
        eb.addField("레벨당 체력회복력", champInfo.get(7).toString(), true);
        eb.addBlankField(false);

        eb.setAuthor(championName, null,
                "https://opgg-static.akamaized.net/images/lol/champion/" + championName
                        + ".png?image=q_auto,f_webp,w_264&v=1651122763930");

        eb.setThumbnail("https://opgg-static.akamaized.net/images/lol/champion/" + championName
                + ".png?image=q_auto,f_webp,w_264&v=1651122763930");

        return eb;

    }
}