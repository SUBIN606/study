package programmers;

public class JadenCase_문자열_만들기 {

    public String solution(String s) {
        s = s.toLowerCase();

        StringBuilder answer = new StringBuilder();

        while (s.contains(" ")) {
            if (s.charAt(0) == ' ') {
                answer.append(s.charAt(0));
                s = s.substring(1);
            } else  {
                int i = s.indexOf(" ");
                String word = s.substring(0, i);
                if (Character.isAlphabetic(word.charAt(0))) {
                    answer.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
                } else {
                    answer.append(word);
                }
                s = s.substring(i);
            }
        }

        if (s.length() > 0) {
            if (Character.isAlphabetic(s.charAt(0))) {
                answer.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
            } else {
                answer.append(s);
            }
        }

        return answer.toString();
    }
}
