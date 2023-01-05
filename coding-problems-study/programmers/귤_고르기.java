package programmers;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class 귤_고르기 {

    public static void main(String[] args) {
        int answer = solution(4, new int[]{1, 3, 2, 5, 4, 5, 2, 3});
        System.out.println("answer = " + answer);
    }

    public static int solution(int k, int[] tangerine) {
        int answer = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : tangerine) {
            map.put(i, map.getOrDefault(i, 0) + 1);
            if(map.get(i) >= k) {
                return 1;
            }
        }

        int[] values = map.values()
                .stream()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();

        for (int i = 0; i < values.length; i++) {
            answer += values[i];
            if(answer >= k) {
                return i + 1;
            }
        }
        return k;
    }

}
