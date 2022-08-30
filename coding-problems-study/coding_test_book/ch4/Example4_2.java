package coding_test_book.ch4;

public class Example4_2 {

    public static void main(String[] args) {
        int solution = solution(5);
        System.out.println("solution = " + solution);
    }

    /*
    * 시각
    *
    * 정수 N이 입력되면 00시 00분 00초 부터 N시 59분 59초까지의 모든 시각 중에서
    * 3이 하나라도 포함되는 모든 경우의 수를 구하는 프로그램을 작성하라.
     */
    public static int solution(int n) {
        int count = 0;
        for (int i = 0; i <= n; i++) {
            if (String.valueOf(i).contains("3")) {
                count += 3600;  // 시간에 3이 포함되면 매 초마다 count가 증가하므로 3600을 더한다.
            } else {    // 포함되지 않으면 분에 포함되는지 확인한다.
                for (int j = 0; j < 60; j++) {
                    if (String.valueOf(j).contains("3")) {  // 분에 3이 포함되면 매 초마다 count가 증가하므로 60을 더한다.
                        count += 60;
                    } else {    // 포함되지 않으면 60 중 3이 포함되는 횟수인 15만 더한다.
                        count += 15;
                    }
                }
            }
        }
        return count;
    }

}
