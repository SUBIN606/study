package programmers;

public class 삼총사 {

    public static void main(String[] args) {
        삼총사 삼총사 = new 삼총사();
        int result = 삼총사.solution(new int[]{-1, 1, -1, 1});
        System.out.println("result = " + result);
    }

    public int solution(int[] number) {
        int answer = 0;
        for (int i = 0; i < number.length; i++) {
            for (int j = i+1; j < number.length; j++) {
                for (int k = j+1; k < number.length; k++) {
                    if(number[i] + number[j] + number[k] == 0) {
                        answer++;
                    }
                }
            }
        }
        return answer;
    }
}
