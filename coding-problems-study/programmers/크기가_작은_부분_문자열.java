package programmers;

public class 크기가_작은_부분_문자열 {
    public static void main(String[] args) {
        크기가_작은_부분_문자열 크기가_작은_부분_문자열 = new 크기가_작은_부분_문자열();
        int result = 크기가_작은_부분_문자열.solution("10203", "15");
        System.out.println("result = " + result);
    }

    public int solution(String t, String p) {
        int answer = 0;

        int tLen = t.length();
        int pLen = p.length();
        long pNum = Long.parseLong(p);

        if(tLen == pLen){
            return Integer.parseInt(t) <= pNum ? 1 : 0;
        }

        for (int i = 0; i < tLen; i++) {
            if (i + pLen > tLen) {
                break;
            }
            long target = Long.parseLong(t.substring(i, i + pLen));
            if (target <= pNum) {
                answer++;
            }
        }
        return answer;
    }
}
