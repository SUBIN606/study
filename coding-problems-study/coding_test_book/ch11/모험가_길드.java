package coding_test_book.ch11;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class 모험가_길드 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] info = new int[N];
        for (int i = 0; i < N; i++) {
            info[i] = sc.nextInt();
        }

        System.out.println(solution(info));
    }

    /*
     * N명의 모험가가 있다. 모험가들은 각각 공포도를 가진다.
     * 공포도가 X인 모험가는 반드시 X명 이상으로 구성한 모험가 그룹에 참여해야 한다.
     *
     * N명의 모험가에 대한 정보가 주어졌을 때, 여행을 떠날 수 있는 그룹 수의 최댓값을 구하라.
     * 반드시 모든 모험가가 여행에 참여해야 하는 것은 아니다.
     * */
    public static int solution(int[] arr) {
        int groupCount = 0;

        // 오름차순으로 정렬
        Arrays.sort(arr);

        int memberCount = 0;
        for (int fear : arr) {
            // 일단 그룹 멤버로 추가
            memberCount += 1;

            // 그룹 멤버가 공포도보다 많거나 같으면 그룹을 만들 수 있다.
            if (memberCount >= fear) {
                groupCount++;       // 그룹 수 증가
                memberCount = 0;    // 멤버 카운트 초기화
            }
        }

        return groupCount;
    }
}
