package programmers;

import java.util.HashMap;
import java.util.Map;

public class 할인_행사 {

    public static void main(String[] args) {
        int result = solution(
                new String[]{"banana"},
                new int[]{1},
                new String[]{"banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana"}
        );
        System.out.println("result = " + result);
    }

    public static int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;

        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < want.length; i++) {
            map.put(want[i], number[i]);
        }

        int start = 0;
        while (start < discount.length) {
            int end = Math.min(start + 10, discount.length);

            // 10일간의 할인 품목과 개수를 알아낸다
            HashMap<String, Integer> discountMap = new HashMap<>();
            for (int i = start; i < end; i++) {
                discountMap.put(
                        discount[i],
                        discountMap.getOrDefault(discount[i], 0) + 1
                );
            }

            // 할인 품목 중 하나라도 없으면 안된다
            boolean flag = false;
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                Integer capacity = discountMap.get(entry.getKey());
                if (capacity == null || entry.getValue() > capacity) {
                    flag = false;
                    break;
                } else {
                    flag = true;
                }
            }
            if (flag) {
                answer++;
            }
            start++;
        }

        return answer;
    }
}
