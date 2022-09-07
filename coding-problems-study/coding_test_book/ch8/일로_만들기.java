package coding_test_book.ch8;

public class 일로_만들기 {

    public static void main(String[] args) {
        int answer = solution(6);
        System.out.println("answer = " + answer);
    }

    /*
     * 1로 만들기
     *
     * 정수 X가 주어질 때 정수 X에 사용할 수 있는 연산은
     *   1. X가 5로 나누어떨어지면, 5로 나눈다.
     *   2. X가 3으로 나누어떨어지면, 3으로 나눈다.
     *   3. X가 2로 나누어떨어지면, 2로 나눈다.
     *   4. X에서 1을 뺀다.
     * 의 4가지 이다.
     *
     * 정수 X가 주어졌을 때, 연산 4개를 적절히 사용해서 1을 만들려고 한다. 연산을 사용하는 횟수의 최솟값을 출력하라.
     * */
    public static int solution(int x) {
        int[] memo = new int[x + 1];

        for (int i = 2; i <= x; i++) {

            memo[i] = memo[i - 1] + 1;
            if (i % 2 == 0) {
                memo[i] = Math.min(memo[i], memo[i / 2] + 1);
            }
            if (i % 3 == 0) {
                memo[i] = Math.min(memo[i], memo[i / 3] + 1);
            }
            if (i % 5 == 0) {
                memo[i] = Math.min(memo[i], memo[i / 5] + 1);
            }
        }

        return memo[x];
    }

}
