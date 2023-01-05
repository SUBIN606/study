package programmers;

public class 문자열_나누기 {

    public static void main(String[] args) {
        int solution = cal("banana", 0);
        System.out.println(solution);
    }

    public static int cal(String s, int count) {
        if (s.length() == 0) {
            return count;
        }
        int same = 0;
        int diff = 0;
        char target = s.charAt(0);
        char[] chars = s.toCharArray();
        int nextStart = 0;
        for (int i = 0; i < chars.length; i++) {
            if(target == chars[i]) {
                same++;
            } else {
                diff++;
            }

            if(same == diff) {
                nextStart = i+1;
                break;
            } else if(i == s.length()-1) {
               return count+1;
            }
        }
        return cal(s.substring(nextStart), count+1);
    }
}
