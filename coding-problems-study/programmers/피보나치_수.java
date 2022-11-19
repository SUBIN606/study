package programmers;

public class 피보나치_수 {

    public int solution(int n) {
        int[] f = new int[n + 1];

        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i < f.length; i++) {
            f[i] = (f[i - 1] % 1234567) + (f[i - 2] % 1234567);
        }

        return f[n] % 1234567;
    }
}
