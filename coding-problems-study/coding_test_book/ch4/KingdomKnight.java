package coding_test_book.ch4;

import java.util.Map;

public class KingdomKnight {

    public static void main(String[] args) {
        int solution = solution("c2");
        System.out.println("solution = " + solution);
    }

    /*
    * 왕실의 나이트
    *
    * 8 * 8 좌표 평면의 특정 한 칸에 나이트가 서 있다.
    * 나이트는 L자 형태로만 이동할 수 있으며 정원 밖으로는 나갈 수 없다.
    *
    * 수평으로 두 칸 이동한 뒤 수직으로 한 칸 이동할 수 있다.
    * 수직으로 두 칸 이동한 뒤 수평으로 한 칸 이동할 수 있다.
    *
    * 나이트의 위치가 주어졌을 때 나이트가 이동할 수 있는 경우의 수를 출력하는 프로그램을 작성하라.
    * 정원의 위치는 행은 1 ~ 8로, 열은 a ~ h로 표현한다.
    *
    * */
    static final Map<String, Integer> cols = Map.of(
            "a", 1,
            "b", 2,
            "c", 3,
            "d", 4,
            "e", 5,
            "f", 6,
            "g", 7,
            "h", 8
    );

    public static int solution(String location) {
        int answer = 0;
        String[] split = location.split("");
        Integer col = cols.get(split[0]);
        int row = Integer.parseInt(split[1]);
        answer += moveHorizontal(col, row);
        answer += moveVertical(col, row);
        return answer;
    }

    // 수직 이동
    private static int moveVertical(int col, int row) {
        int count;
        if (row > 2) {
            if (col > 1) {  // 수직으로 이동 후 수평으로 양쪽 이동 가능
                count = 4;
            } else {
                count = 2;
            }
        } else {    // 아래로만 이동 후
            if (col > 1) {  // 수직으로 이동 후 수평으로 양쪽 이동 가능
                count = 2;
            } else {
                count = 1;
            }
        }
        return count;
    }

    // 수평 이동
    private static int moveHorizontal(int col, int row) {
        int count;
        if (col > 2) {
            if (row > 1) {  // 수평으로 이동 후 수직으로 양쪽 이동 가능
                count = 4;
            } else {
                count = 2;
            }
        } else {    // 아래로만 이동 후
            if (row > 1) {  // 수직으로 이동 후 수평으로 양쪽 이동 가능
                count = 2;
            } else {
                count = 1;
            }
        }
        return count;
    }
}
