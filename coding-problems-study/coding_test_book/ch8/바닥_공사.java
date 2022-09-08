package coding_test_book.ch8;

public class 바닥_공사 {

    public static void main(String[] args) {
        int answer = solution(3);
        System.out.println("answer = " + answer);
    }

    /*
     * 가로의 길이가 N, 세로의 길이가 2인 직사각형 형태의 바닥이 있다.
     * 이 바닥을 1 X 2의 덮개, 2 X 1의 덮개, 2 X 2의 덮개를 이용해 채우고자 한다.
     *
     * 이때 바닥을 채우는 모든 경우의 수를 구하는 프로그램을 작성하라.
     * (바닥을 채우는 방법의 수를 796,796으로 나눈 나머지를 반환)
     * */
    public static int solution(int N) {
        int[] memo = new int[N + 1];
        memo[1] = 1;
        memo[2] = 3;
        for (int i = 3; i <= N; i++) {
            memo[i] = (memo[i - 1] + (memo[i - 2] * 2)) % 796796;
        }
        return memo[N];
    }

}
