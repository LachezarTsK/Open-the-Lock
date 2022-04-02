
#include <string>
#include <queue>
#include <array>
#include <vector>
using namespace std;

class Solution {
    
    inline static const int START_COMBINATION = 0000;
    inline static const int MAX_COMBINATION = 9999;

    inline static const int COMBINATION_SIZE = 4;
    inline static const int MIN_DIGIT = 0;
    inline static const int MAX_DIGIT = 9;

    inline static const int NOT_POSSIBLE = -1;

    typedef array<int, COMBINATION_SIZE> combination;

public:

    int openLock(vector<string>& deadends, string target) {

        array<bool, MAX_COMBINATION + 1 > visited{};
        for (const auto& n : deadends) {
            visited[stoi(n)] = true;
        }

        int targetAsInteger = stoi(target);
        if (visited[START_COMBINATION]) {
            return NOT_POSSIBLE;
        }

        queue<combination> queue;
        queue.push(combination{0, 0, 0, 0});

        visited[START_COMBINATION] = true;
        int numberOfMoves = 0;

        while (!queue.empty()) {

            int size = queue.size();
            while (size-- > 0) {

                combination current = queue.front();
                queue.pop();

                if (toInteger(current) == targetAsInteger) {
                    return numberOfMoves;
                }

                for (int i = 0; i < COMBINATION_SIZE; i++) {

                    int storeOriginalValue = current[i];

                    current[i] = rotateForward(current[i]);
                    int forwardAsInteger = toInteger(current);
                    if (!visited[forwardAsInteger]) {
                        visited[forwardAsInteger] = true;
                        queue.push(combination(current));
                    }

                    current[i] = storeOriginalValue;

                    current[i] = rotateBackward(current[i]);
                    int backwardAsInteger = toInteger(current);
                    if (!visited[backwardAsInteger]) {
                        visited[backwardAsInteger] = true;
                        queue.push(combination(current));
                    }

                    current[i] = storeOriginalValue;
                }
            }
            numberOfMoves++;
        }
        return NOT_POSSIBLE;
    }

private:

    int rotateForward(int n) {
        return n == MAX_DIGIT ? MIN_DIGIT : n + 1;
    }

    int rotateBackward(int n) {
        return n == MIN_DIGIT ? MAX_DIGIT : n - 1;
    }

    int toInteger(const combination& nums) {
        int value = 0;
        int digitPlace = 1;
        for (int i = COMBINATION_SIZE - 1; i >= 0; i--) {
            value += nums[i] * digitPlace;
            digitPlace *= 10;
        }
        return value;
    }
};
