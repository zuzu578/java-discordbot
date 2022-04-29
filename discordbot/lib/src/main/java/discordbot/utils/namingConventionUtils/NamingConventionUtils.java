package discordbot.utils.namingConventionUtils;

/**
 * 챔피언 첫글자를 대문자로 , 나머지를 소문자로 바꾸는 util 클래스
 */
public class NamingConventionUtils {

    public String capitalizeFirstLetter(String str) {
        // 1. 첫번째 글자 substring
        String firstLetter = str.substring(0, 1);
        // 2. 첫번째 글자를 제외한 나머지 글자 substring
        String remainLetter = str.substring(1);
        // 3. 첫번째 글자를 대문자로 변환
        firstLetter = firstLetter.toUpperCase();
        // 4. 나머지 글자를 소문자로 변환
        remainLetter = remainLetter.toLowerCase();
        // 5. 첫번째 글자(대문자) + 나머지 글자(소문자)
        String result = firstLetter + remainLetter;
        return result;
    }

}
