package programmers;

public class 콜라_문제 {

    public static void main(String[] args) {
        콜라_문제 콜라_문제 = new 콜라_문제();
        int result = 콜라_문제.solution(3, 1, 20);
        System.out.println("result = " + result);
    }

    // 콜라를 받기 위해 마트에 주어야 하는 병 수 a,
    // 빈 병 a개를 가져다 주면 마트가 주는 콜라 병 수 b,
    // 상빈이가 가지고 있는 빈 병의 개수 n이 매개변수로 주어집니다.
    // 상빈이가 받을 수 있는 콜라의 병 수를 return
    public int solution(int a, int b, int n) {
        int answer = 0;

        // 현재 가지고 있는 병의 개수
        int stock  = n;

        while (stock >= a) {
            // 마트에 주는 개수
            int give = stock / a;
            stock -= (give * a);

            // 마트에서 받은 콜라 병 수
            int gave = give * b;
            answer += gave;
            stock += gave;
        }

        return answer;
    }
}
