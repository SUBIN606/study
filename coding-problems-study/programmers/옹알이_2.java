package programmers;

import java.util.Arrays;

public class 옹알이_2 {

    static String[] words = {"aya", "ye", "woo", "ma"};

    public static void main(String[] args) {
        옹알이_2 c = new 옹알이_2();
        int result = c.solution(new String[]{"ayaye", "uuu", "yeye", "yemawoo", "ayaayaa"});
        System.out.println("result = " + result);
    }


    public int solution(String[] babbling) {
        return (int) Arrays.stream(babbling)
                .filter(str -> remove(str))
                .count();
    }

    private boolean remove(String str) {
        if(str.isEmpty()) {
            return true;
        }
        for (String word : words) {
            if(str.startsWith(word)) {
                String replaced = str.replaceFirst(word, "");
                if (replaced.startsWith(word)) {
                    return false;
                }
                return remove(replaced);
            }
        }
        return false;
    }

}
