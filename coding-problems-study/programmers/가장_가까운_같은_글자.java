package programmers;

import java.util.Arrays;
import java.util.HashMap;

public class 가장_가까운_같은_글자 {
    public static void main(String[] args) {
        가장_가까운_같은_글자 가장_가까운_같은_글자 = new 가장_가까운_같은_글자();
        int[] result = 가장_가까운_같은_글자.solution("foobar");
        System.out.println("result = " + Arrays.toString(result));
    }
    public int[] solution(String s) {
        int[] answer = new int[s.length()];

        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Integer beforeIndex = map.get(c);

            if(beforeIndex == null) {
                answer[i] = -1;
            } else {
                answer[i] = i - beforeIndex;
            }

            map.put(c, i);
        }

        return answer;
    }
}
