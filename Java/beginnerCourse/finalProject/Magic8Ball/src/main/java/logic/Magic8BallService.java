package logic;

import java.util.Random;

public class Magic8BallService {
    private final String[] positive = {
            "The stars align in your favor.",
            "Yes… it is destined.",
            "The universe agrees."
    };

    private final String[] negative = {
            "Darkness says no.",
            "The answer is forbidden.",
            "It shall not be."
    };

    private final String[] neutral = {
            "The fog is too thick…",
            "Ask again when the moon rises.",
            "The answer is unclear."
    };


    private final Random random = new Random();

    public String getRandomAnswer() {
        int category = random.nextInt(3);

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
