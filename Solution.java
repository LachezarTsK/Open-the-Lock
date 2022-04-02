
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {

    private static final int START_COMBINATION = 0000;
    private static final int MAX_COMBINATION = 9999;

    private static final int COMBINATION_SIZE = 4;
    private static final int MIN_DIGIT = 0;
    private static final int MAX_DIGIT = 9;

    private static final int NOT_POSSIBLE = -1;

    public int openLock(String[] deadends, String target) {

        boolean[] visited = new boolean[MAX_COMBINATION + 1];
        for (String n : deadends) {
            visited[Integer.parseInt(n)] = true;
        }

        int targetAsInteger = Integer.parseInt(target);
        if (visited[START_COMBINATION]) {
            return NOT_POSSIBLE;
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0, 0, 0});
        visited[START_COMBINATION] = true;
        int numberOfMoves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();
            while (size-- > 0) {

                int[] current = queue.poll();
                if (toInteger(current) == targetAsInteger) {
                    return numberOfMoves;
                }

                for (int i = 0; i < COMBINATION_SIZE; i++) {

                    int storeOriginalValue = current[i];

                    current[i] = rotateForward(current[i]);
                    int forwardAsInteger = toInteger(current);
                    if (!visited[forwardAsInteger]) {
                        visited[forwardAsInteger] = true;
                        queue.add(Arrays.copyOf(current, COMBINATION_SIZE));
                    }

                    current[i] = storeOriginalValue;

                    current[i] = rotateBackward(current[i]);
                    int backwardAsInteger = toInteger(current);
                    if (!visited[backwardAsInteger]) {
                        visited[backwardAsInteger] = true;
                        queue.add(Arrays.copyOf(current, COMBINATION_SIZE));
                    }

                    current[i] = storeOriginalValue;
                }
            }
            numberOfMoves++;
        }
        return NOT_POSSIBLE;
    }

    private int rotateForward(int n) {
        return n == MAX_DIGIT ? MIN_DIGIT : n + 1;
    }

    private int rotateBackward(int n) {
        return n == MIN_DIGIT ? MAX_DIGIT : n - 1;
    }

    private int toInteger(int[] nums) {
        int value = 0;
        int digitPlace = 1;
        for (int i = COMBINATION_SIZE - 1; i >= 0; i--) {
            value += nums[i] * digitPlace;
            digitPlace *= 10;
        }
        return value;
    }
}
