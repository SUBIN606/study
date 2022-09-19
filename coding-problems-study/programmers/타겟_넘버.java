package programmers;

public class 타겟_넘버 {

    public static void main(String[] args) {
        int answer = solution(new int[]{4, 1, 2, 1}, 4);
        System.out.println(answer);
    }

    static int answer = 0;
    /*
    * n개의 음이 아닌 정수들이 있습니다. 이 정수들을 순서를 바꾸지 않고 적절히 더하거나 빼서 타겟 넘버를 만들려고 합니다.
    * */
    public static int solution(int[] numbers, int target) {
        dfs(numbers, 0, target, 0);
        return answer;
    }

    private static void dfs(int[] numbers, int idx, int target, int sum) {
        if (idx >= numbers.length) {
            if (sum == target) {
                answer++;
            }
        } else {
            dfs(numbers, idx + 1, target, sum + numbers[idx]);
            dfs(numbers, idx + 1, target, sum - numbers[idx]);
        }
    }
}
