package cz.hospoda.uctenka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PolozkaTest {

    @Test
    void celkem_cenaKratPocet() {
        Polozka pivo = new Polozka("Pivo", new BigDecimal("59.50"), 3);
        assertEquals(new BigDecimal("178.50"), pivo.celkem());
    }

    @Test
    void konstruktor_spatneHodnoty_vyhodiVyjimku() {
        assertThrows(IllegalArgumentException.class, () -> new Polozka("", BigDecimal.ONE, 1));
        assertThrows(IllegalArgumentException.class, () -> new Polozka("Pivo", BigDecimal.ZERO, 1));
        assertThrows(IllegalArgumentException.class, () -> new Polozka("Pivo", new BigDecimal("1.234"), 1));
        assertThrows(IllegalArgumentException.class, () -> new Polozka("Pivo", BigDecimal.ONE, 0));
    }
}
