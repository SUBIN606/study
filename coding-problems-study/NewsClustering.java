import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsClustering {

    /*
     * str1, str2 두 문자열이 주어진다.
     * 문자열을 두 글자씩 끊어서 다중 집합의 원소로 만든다. 단 영문자로 된 글자 쌍만 유효하다.
     * 예) aa1+aa2 -> aa, a1(무시), 1+(무시), +a(무시), aa, a2(무시) => { aa, aa }
     * 대문자와 소문자의 차이는 무시한다. (AB == Ab == ab)
     *
     * 입력으로 들어온 두 문자열의 자카드 유사도를 출력한다.
     * 자카드 유사도는 집합 간의 유사도를 검사하는 여러 방법 중의 하나로 알려져 있다.
     * 두 집합 A, B 사이의 자카드 유사도 J(A, B)는 두 집합의 교집합 크기를 두 집합의 합집합 크기로 나눈 값으로 정의된다.
     * 예를 들어 집합 A = {1, 2, 3}, 집합 B = {2, 3, 4}라고 할 때,
     * 교집합 A ∩ B = {2, 3}, 합집합 A ∪ B = {1, 2, 3, 4}이 되므로,
     * 집합 A, B 사이의 자카드 유사도 J(A, B) = 2/4 = 0.5가 된다.
     * 집합 A와 집합 B가 모두 공집합일 경우에는 나눗셈이 정의되지 않으니 따로 J(A, B) = 1로 정의한다.
     *
     * 유사도 값은 0에서 1사이의 실수이므로, 65536을 곱한 후 소수점 아래를 버리고 정수부만 출력한다.
     * */

    // 문자열을 입력받아 다중 집합을 반환한다.
    private Map<String, Integer> getMultipleSet(String str) {
        ArrayList<String> set = new ArrayList<>();
        for (int i = 1; i < str.length(); i++) {
            char c1 = str.charAt(i - 1);
            char c2 = str.charAt(i);
            if (Character.isAlphabetic(c1) && Character.isAlphabetic(c2)) {
                set.add((c1 + "" + c2).toLowerCase());
            }
        }
        return listToCharacterCountMap(set);
    }

    private Map<String, Integer> listToCharacterCountMap(List<String> list) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String str : list) {
            map.put(str, map.getOrDefault(str, 0) + 1);
        }
        return map;
    }

    // 두 리스트 사이의 교집합 개수를 반환한다.
    private int findInterSectionCount(Map<String, Integer> map1, Map<String, Integer> map2) {
//        int count = 0;
//        for (String key : map1.keySet()) {
//            if (map1.get(key) != null && map2.get(key) != null) {
//                if (Objects.equals(map1.get(key), map2.get(key))) {
//                    count += map1.get(key);
//                } else {
//                    count+= Integer.min(map1.get(key), map2.get(key));
//                }
//            }
//        }
//        return count;
        return map1.entrySet().stream()
                .filter(entry -> map2.containsKey(entry.getKey()))
                .map(entry -> Integer.min(entry.getValue(), map2.get(entry.getKey())))
                .mapToInt(Integer::intValue)
                .sum();
    }

    // 두 리스트의 합집합 개수를 반환한다.
    private int findUnionCount(Map<String, Integer> map1, Map<String, Integer> map2) {
        map2.forEach((key, value) -> {
            map1.merge(key, value, Integer::max);
        });
        return map1.values()
                .stream()
                .reduce(0, Integer::sum);
    }

    public int solution(String str1, String str2) {
        Map<String, Integer> map1 = getMultipleSet(str1);
        Map<String, Integer> map2 = getMultipleSet(str2);

        int intersection = findInterSectionCount(map1, map2);
        int union = findUnionCount(map1, map2);

        if (intersection == 0 && union == 0) {
            return 65536;
        }
        return (int) Math.abs((double) intersection / union * 65536);
    }
    
}
