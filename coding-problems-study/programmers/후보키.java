package programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class 후보키 {

    /*
     * 후보키는 유일성과 최소성을 만족한다.
     * - 유일성(uniqueness) : 릴레이션에 있는 모든 튜플에 대해 유일하게 식별되어야 한다.
     * - 최소성(minimality) : 유일성을 가진 키를 구성하는 속성(Attribute) 중 하나라도 제외하는 경우 유일성이 깨지는 것을 의미한다.
     *                       즉, 릴레이션의 모든 튜플을 유일하게 식별하는 데 꼭 필요한 속성들로만 구성되어야 한다.
     * 릴레이션을 나타내는 문자열 배열 relation이 매개변수로 주어질 때, 이 릴레이션에서 후보 키의 개수를 return 하도록 solution 함수를 완성하라.
     */

    // 문제를 작게 쪼개기
    // 나올 수 있는 후보키의 조합을 다 구한다 (모든 컬럼의 조합?)
    private void combination(Set<String> combinations, boolean[] visited, int start, int end, int size) {
        if (size == 1) {
            for (int i = 0; i < visited.length; i++) {
                combinations.add(String.valueOf(i));
            }
            return;
        }

        if (end == 0) {
            String comb = "";
            for (int i = 0; i < visited.length; i++) {
                if (visited[i]) {
                    comb += i;
                }
            }
            combinations.add(comb);
            return;
        }

        for (int i = start; i < visited.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                combination(combinations, visited, start + 1, end - 1, size);
                visited[i] = false;
            }
        }
    }

    private Set<String> getAllKeys(String[][] relation) {
        int columnCount = relation[0].length;

        Set<String> combinations = new LinkedHashSet<>();
        for (int i = 1; i <= columnCount; i++) {
            combination(combinations, new boolean[columnCount], 0, i, i);
        }

        combinations.removeIf(combination ->
                !isUnique(relation, Arrays.stream(combination.split(""))
                        .map(Integer::parseInt)
                        .mapToInt(Integer::intValue)
                        .toArray()));

        return combinations;
    }

    // 후보 조합으로 후보키가 될 수 있는지 구한다 (컬럼으로 조합 해서 유니크한지 확인)
    private boolean isUnique(String[][] relation, int[] cols) {
        int rowCount = relation.length;

        HashSet<String> set = new HashSet<>();
        for (String[] strings : relation) {
            StringBuilder data = new StringBuilder();
            for (int col : cols) {
                data.append(strings[col]);
            }
            set.add(data.toString());
        }

        return set.size() == rowCount;
    }

    // 최소성을 만족하는지 확인한다
    private boolean isMinimality(List<String> candidateKeys, String combination) {
        if (candidateKeys.isEmpty()) {
            candidateKeys.add(combination);
            return true;
        }
        boolean flag = false;
        for (String comb : candidateKeys) {
            if (Arrays.asList(combination.split("")).containsAll(List.of(comb.split("")))) {
                flag = false;
                break;
            } else {
                flag =  true;
            }
        }
        if (flag) {
            candidateKeys.add(combination);
        }
        return flag;
    }

    public int solution(String[][] relation) {
        Set<String> combinations = getAllKeys(relation);

        List<String> candidateKeys = new ArrayList<>();

        for (String comb : combinations) {
            isMinimality(candidateKeys, comb);
        }

        return candidateKeys.size();
    }

}
