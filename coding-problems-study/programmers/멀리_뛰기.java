package programmers;

public class 멀리_뛰기 {

    public static void main(String[] args) {
        멀리_뛰기 멀리_뛰기 = new 멀리_뛰기();
        long result = 멀리_뛰기.solution(1);
        System.out.println("result = " + result);
    }

    public long solution(int n) {
        if(n < 3) {
            return n;
        }

        long[] memo = new long[n + 1];
        memo[1] = 1;
        memo[2] = 2;

        for (int i = 3; i <= n; i++) {
            memo[i] = (memo[i - 1] + memo[i - 2]) % 1234567;
        }

        return memo[n];
    }
}
