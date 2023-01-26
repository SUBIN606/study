package programmers;

public class 기사단원의_무기 {

    public static void main(String[] args) {
        기사단원의_무기 기사단원의_무기 = new 기사단원의_무기();
        int result = 기사단원의_무기.solution(10, 3, 2);
        System.out.println("result = " + result);
    }

    // 기사단원의 수를 나타내는 정수 number
        // 각 기사는 1번부터 number까지 번호가 지정되어 있다.
    // 이웃나라와 협약으로 정해진 공격력의 제한수치를 나타내는 정수 limit
    // 제한수치를 초과한 기사가 사용할 무기의 공격력을 나타내는 정수 power
    public int solution(int number, int limit, int power) {
        int answer = 0;
        for (int i = 1; i <= number; i++) {
            int count = countDivisors(i);
            if(count > limit) {
                answer += power;
            } else {
                answer += count;
            }
        }
        return answer;
    }

    // 약수 개수를 센다.
    private int countDivisors(int n) {
        if(n == 1) {
            return 1;
        }

        int count = 0;
        for (int i = 1; i <= (int) Math.sqrt(n); i++) {
            if(i * i == n) {
                count++;
            } else if(n % i == 0) {
                count += 2;
            }
        }

        return count;
    }
}
