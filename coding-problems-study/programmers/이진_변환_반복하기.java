package programmers;

import java.util.Arrays;

public class 이진_변환_반복하기 {
    public static void main(String[] args) {
        int[] answer = solution("1111111");
        System.out.println(Arrays.toString(answer));
    }

    public static int[] solution(String s) {
        int[] answer = new int[2];
        while (!s.equals("1")) {
            int beforeLength = s.length();
            String deletedStr = s.replaceAll("0", "");
            int length = deletedStr.length();

            answer[0]++;
            answer[1] += beforeLength-length;

            s = Long.toString(length, 2);
        }

        return answer;
    }

    private static int countZeros(String str) {
        return (int) Arrays.stream(str.split(""))
                .filter(s -> s.equals("0"))
                .count();
    }

}
