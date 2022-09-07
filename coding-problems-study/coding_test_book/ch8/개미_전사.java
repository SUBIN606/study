package coding_test_book.ch8;

public class 개미_전사 {

    public static void main(String[] args) {
        int[] arr = {2, 3, 7, 1, 4};
        int answer = solution(arr);
        System.out.println("answer = " + answer);
    }

    /*
     * 메뚜기 마을의 식량창고는 일직선으로 이어져있다.
     * 각 식량 창고에는 정해진 수의 식량을 저장하고 있으며 개미 전사는 식량창고를 선택적으로 약탈하여 식량을 빼앗을 예정이다.
     * 이때 메뚜기 정찰병들은 일직선상에 존재하는 식량창고 중에서 서로 인접한 식량창고가 공격받으면 바로 알아챌 수 있다.
     * 따라서 개미 전사가 정찰병에게 들키지 않고 식량창고를 약탈하기 위해서는 최소한 한 칸 이상 떨어진 식량창고를 약탈해야 한다.
     *
     * 개미 전사는 식량창고가 일직선상일 때 최대한 많은 식량을 덕기를 원한다.
     * 개미 전사를 위해 식량창고 N개에 대한 정보가 주어졌을 때 얻을 수 있는 식량의 최댓값을 구하는 프로그램을 작성하라.
     * */
    public static int solution(int[] arr) {
        int[] memo = new int[arr.length + 1];
        // 맨 처음 식량창고를 턴다.
        memo[0] = arr[0];

        // 첫 번째와 두 번째 중 더 많은 식량을 턴다.
        memo[1] = Math.max(arr[0], arr[1]);

        // 세 번째 식량창고 부터는 바로 전 식량창고를 털지, 전전 식량창고와 지금 식량창고(i번째)를 터는 것 중 큰 값을 저장한다.
        for (int i = 2; i < arr.length; i++) {
            memo[i] = Math.max(memo[i - 1], memo[i - 2] + arr[i]);
        }
        return memo[arr.length - 1];
    }
}
