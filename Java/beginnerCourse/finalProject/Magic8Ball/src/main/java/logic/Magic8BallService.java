package logic;

import java.util.Random;

/**
 * Creates random answers for the Magic 8 Ball.
 *
 *  @author Maja P.
 */
public class Magic8BallService {
    // Positive answers.
    private final String[] positive = {
            "The stars align in your favor.",
            "Yes... it is destined.",
            "The universe agrees."
    };

    // Negative answers.
    private final String[] negative = {
            "Darkness says no.",
            "The answer is forbidden.",
            "It shall not be."
    };

    // Neutral answers.
    private final String[] neutral = {
            "The fog is too thick...",
            "Moonlight will tell.",
            "The answer is unclear."
    };

    // Pick random values.
    private final Random random = new Random();

    /**
     * Returns one random answer.
     *
     * @return random answer text.
     */
    public String getRandomAnswer() {
        // Chooses a random answer group.
        int category = random.nextInt(3);

        // Returns one answer from the chosen group.
        switch (category) {
            case 0:
                return positive[random.nextInt(positive.length)];
            case 1:
                return negative[random.nextInt(negative.length)];
            default:
                return neutral[random.nextInt(neutral.length)];
        }
    }
}