package cz.hospoda.uctenka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class UctenkaTest {

    private Uctenka vzorovaUctenka() {
        Properties obchod = new Properties();
        obchod.setProperty("nazev", "U Testu");
        obchod.setProperty("ico", "11223344");
        Uctenka u = new Uctenka(obchod);
        u.pridej(new Polozka("Pivo", new BigDecimal("59"), 3));
        u.pridej(new Polozka("Utopenec", new BigDecimal("79.90"), 1));
        return u;
    }

    @Test
    void celkem_sectePolozky() {
        assertEquals(new BigDecimal("256.90"), vzorovaUctenka().celkem());
    }

    @Test
    void vytiskni_obsahujeHlavickuPolozkyACelkem() {
        String text = vzorovaUctenka().vytiskni(LocalDateTime.of(2026, 10, 22, 18, 30));

        assertTrue(text.contains("U Testu"));
        assertTrue(text.contains("IČO: 11223344"));
        assertTrue(text.contains("22.10.2026 18:30"));
        assertTrue(text.contains("3 x 59,00"));
        assertTrue(text.contains("CELKEM Kč"));
        assertTrue(text.contains("256,90"));
    }

    @Test
    void nactiObchod_cteSouborVUtf8(@TempDir Path slozka) throws IOException {
        Path soubor = slozka.resolve("uctenka.properties");
        Files.writeString(soubor, "nazev=Hospoda Účko\n");

        assertEquals("Hospoda Účko", Main.nactiObchod(soubor).getProperty("nazev"));
    }

    @Test
    void nactiObchod_souborNeexistuje_vyhodiVyjimku(@TempDir Path slozka) {
        assertThrows(IOException.class, () -> Main.nactiObchod(slozka.resolve("neni.properties")));
    }

    @Test
    void parseCena_beriTeckuICarku() {
        assertEquals(new BigDecimal("12.50"), Main.parseCena(" 12,50 "));
        assertEquals(new BigDecimal("12.50"), Main.parseCena("12.50"));
        assertThrows(NumberFormatException.class, () -> Main.parseCena("abc"));
    }
}
