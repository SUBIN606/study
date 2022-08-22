import java.util.LinkedList;
import java.util.Queue;

class QueSameTotal {

    private Queue<Integer> makeQueue(int[] arr) {
        Queue<Integer> queue = new LinkedList<>();
        for (int num : arr) {
            queue.add(num);
        }
        return queue;
    }

    private long queueSum(Queue<Integer> queue) {
        return queue.stream().reduce(0, Integer::sum);
    }

    /*
     * 길이가 같은 두 개의 큐가 주어진다.
     * 하나의 큐에서 pop하고 다른 큐에 insert하는 과정을 반복해서 두 큐의 합을 같게 만들어야 한다.
     */
    public int solution(int[] queue1, int[] queue2) {
        int answer = 0;

        Queue<Integer> que1 = makeQueue(queue1);
        Queue<Integer> que2 = makeQueue(queue2);

        long que1Total = queueSum(que1);
        long que2Total = queueSum(que2);

        long expectTotal = (que1Total + que2Total) / 2;

        while (true) {
            if(que1Total > que2Total) {
                Integer poll = que1.poll();
                que2.add(poll);
                que1Total -= poll;
                que2Total += poll;
                answer++;
            }
            if (que1Total < que2Total) {
                Integer poll = que2.poll();
                que1.add(poll);
                que1Total += poll;
                que2Total -= poll;
                answer++;
            }
            if (answer > queue1.length * 4) {
                return -1;
            }
            if (que1Total == expectTotal) {
                break;
            }

        }
        return answer;
    }

}
