package cz.hospoda.uctenka;

import java.math.BigDecimal;

public class Polozka {
    private final String nazev;
    private final BigDecimal cena;
    private final int pocet;

    public Polozka(String nazev, BigDecimal cena, int pocet) {
        if (nazev == null || nazev.isBlank()) {
            throw new IllegalArgumentException("Název nesmí být prázdný.");
        }
        if (cena.signum() <= 0 || cena.scale() > 2) {
            throw new IllegalArgumentException("Cena musí být kladná a mít nejvýš 2 desetinná místa.");
        }
        if (pocet <= 0) {
            throw new IllegalArgumentException("Počet musí být kladný.");
        }
        this.nazev = nazev.trim();
        this.cena = cena;
        this.pocet = pocet;
    }

    public String getNazev() {
        return nazev;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public int getPocet() {
        return pocet;
    }

    public BigDecimal celkem() {
        return cena.multiply(BigDecimal.valueOf(pocet));
    }
}
