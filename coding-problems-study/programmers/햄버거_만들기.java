package programmers;

import java.util.Arrays;
import java.util.stream.Collectors;

public class 햄버거_만들기 {

    public static void main(String[] args) {
        int[] ingredient = {1, 2, 3, 1, 2, 3, 1, 1, 1, 1, 2, 3, 1, 2, 3, 1, 1, 1, 1, 1, 1, 2, 3, 3, 1, 2, 3, 1, 3, 3, 3, 2, 1, 2, 3, 1};
        int answer = solution(ingredient);
        System.out.println("answer = " + answer);
    }

    // 빵(1) - 야채(2) - 고기(3) - 빵(1)
    public static int solution(int[] ingredient) {
        int answer = 0;
        StringBuffer stringBuffer = new StringBuffer(Arrays.stream(ingredient)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining()));

        String target = "1231";
        while (stringBuffer.indexOf(target) != -1) {
            int indexOf = stringBuffer.indexOf(target);
            stringBuffer.replace(indexOf, indexOf + target.length(), "");
            answer++;
        }
        return answer;
    }
}
