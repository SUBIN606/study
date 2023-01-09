package programmers;

public class 타일링 {

    public static void main(String[] args) {
        int result = solution(7);
        System.out.println("result = " + result);
    }


    public static int solution(int n) {
        int[] memo = new int[n+1];
        memo[1] = 1;
        memo[2] = 2;
        for (int i = 3; i <= n; i++) {
            memo[i] = (memo[i-1] + memo[i-2]) % 1000000007;
        }
        return memo[n];
    }
}
