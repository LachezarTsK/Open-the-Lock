
/**
 * @param {string[]} deadends
 * @param {string} target
 * @return {number}
 */
var openLock = function (deadends, target) {

    this.START_COMBINATION = 0000;
    this.MAX_COMBINATION = 9999;

    this.COMBINATION_SIZE = 4;
    this.MIN_DIGIT = 0;
    this.MAX_DIGIT = 9;

    this.NOT_POSSIBLE = -1;

    const visited = new Array(MAX_COMBINATION + 1).fill(false);
    for (let n of deadends) {
        visited[parseInt(n)] = true;
    }

    let targetAsInteger = parseInt(target);
    if (visited[START_COMBINATION]) {
        return NOT_POSSIBLE;
    }

    const queue = new Queue();
    queue.enqueue([0, 0, 0, 0]);
    visited[START_COMBINATION] = true;
    let numberOfMoves = 0;

    while (!queue.isEmpty()) {

        let size = queue.size();
        while (size-- > 0) {

            const current = queue.dequeue();
            if (toInteger(current) === targetAsInteger) {
                return numberOfMoves;
            }

            for (let i = 0; i < this.COMBINATION_SIZE; i++) {

                const storeOriginalValue = current[i];

                current[i] = rotateForward(current[i]);
                let forwardAsInteger = toInteger(current);
                if (!visited[forwardAsInteger]) {
                    visited[forwardAsInteger] = true;
                    queue.enqueue(Array.from(current));
                }

                current[i] = storeOriginalValue;

                current[i] = rotateBackward(current[i]);
                let backwardAsInteger = toInteger(current);
                if (!visited[backwardAsInteger]) {
                    visited[backwardAsInteger] = true;
                    queue.enqueue(Array.from(current));
                }

                current[i] = storeOriginalValue;
            }
        }
        numberOfMoves++;
    }
    return NOT_POSSIBLE;
};

/**
 * @param {number} n
 * @return {number}
 */
function rotateForward(n) {
    return n === this.MAX_DIGIT ? this.MIN_DIGIT : n + 1;
}

/**
 * @param {number} n
 * @return {number}
 */
function rotateBackward(n) {
    return n === this.MIN_DIGIT ? this.MAX_DIGIT : n - 1;
}

/**
 * @param {number[]} nums
 * @return {number}
 */
function toInteger(nums) {
    let value = 0;
    let digitPlace = 1;
    for (let i = this.COMBINATION_SIZE - 1; i >= 0; i--) {
        value += nums[i] * digitPlace;
        digitPlace *= 10;
    }
    return value;
}
