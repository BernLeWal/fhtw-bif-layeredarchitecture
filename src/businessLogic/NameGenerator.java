package businessLogic;

import java.util.Random;

public class NameGenerator {
    public static String Generate(int len) {
        Random r = new Random();
        String[] consonants = { "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "l", "n", "p", "q", "r", "s", "sh", "zh", "t", "v", "w", "x" };
        String[] vowels = { "a", "e", "i", "o", "u", "ae", "y" };
        String Name = "";
        Name += getRandom(consonants);
        Name += getRandom(vowels);
        int b = 2; //b tells how many times a new letter has been added. It's 2 right now because the first two letters are already in the name.
        while (b < len) {
            Name += getRandom(consonants);
            b++;
            Name += getRandom(vowels);
            b++;
        }

        return Name;
    }

    private static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
}
