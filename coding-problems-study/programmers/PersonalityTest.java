import java.util.HashMap;

public class PersonalityTest {

    /*
        성격은 4개 지표로 구분한다.
        1번 - R, T
        2번 - C, F
        3번 - J, M
        4번 - A, N
        총 16가지의 성격 유형이 나올 수 있다.

        검사지는 총 n개의 질문이 있고 7개의 선택지가 있다.
        1: 매우 비동의(3)
        2: 비동의(2)
        3: 약간 비동의(1)
        4: 모르겠음(0)
        5: 약간 동의(1)
        6: 동의(2)
        7: 매우 동의(3)
    */

    // 질문마다 판단하는 지표를 담은 survey, 질문마다 선택한 선택지를 담은 choices
    public String solution(String[] survey, int[] choices) {
        String answer = "";

        HashMap<String, Integer> results = new HashMap<>();

        for (int i = 0; i < survey.length; i++) {
            String type1 = String.valueOf(survey[i].charAt(0));
            String type2 = String.valueOf(survey[i].charAt(1));

            if(choices[i] == 4) {
                continue;
            }
            if (choices[i] < 4) {
                results.put(type1, results.getOrDefault(type1, 0) + Math.abs(choices[i] - 4));
            }
            if (choices[i] > 4) {
                results.put(type2, results.getOrDefault(type2, 0) + choices[i] - 4);
            }
        }

        answer += getGreaterType('R', results.getOrDefault("R", 0),
                'T', results.getOrDefault("T", 0));
        answer += getGreaterType('C', results.getOrDefault("C", 0),
                'F', results.getOrDefault("F", 0));
        answer += getGreaterType('J', results.getOrDefault("J", 0),
                'M', results.getOrDefault("M", 0));
        answer += getGreaterType('A', results.getOrDefault("A", 0),
                'N', results.getOrDefault("N", 0));

        return answer;
    }

    private char getGreaterType(char type1, int type1Score, char type2, int type2Score) {
        return type1Score >= type2Score ? type1 : type2;
    }

}
