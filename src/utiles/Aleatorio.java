package utiles;

import java.util.concurrent.ThreadLocalRandom;

public class Aleatorio {

    public static int intAleatorio(int desde, int hasta) {
        return ThreadLocalRandom.current().nextInt(desde, hasta);
    }
}
