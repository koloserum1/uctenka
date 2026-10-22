package cz.hospoda.uctenka;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Uctenka {
    private static final int SIRKA = 40;

    private final Properties obchod;
    private final List<Polozka> polozky = new ArrayList<>();

    public Uctenka(Properties obchod) {
        this.obchod = obchod;
    }

    public void pridej(Polozka polozka) {
        polozky.add(polozka);
    }

    public boolean jePrazdna() {
        return polozky.isEmpty();
    }

    public BigDecimal celkem() {
        BigDecimal soucet = BigDecimal.ZERO;
        for (Polozka p : polozky) {
            soucet = soucet.add(p.celkem());
        }
        return soucet.setScale(2, RoundingMode.HALF_UP);
    }

    public String vytiskni(LocalDateTime cas) {
        String cara = "-".repeat(SIRKA) + "\n";
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(SIRKA)).append("\n");
        sb.append(naStred(obchod.getProperty("nazev", ""))).append("\n");
        sb.append(naStred(obchod.getProperty("adresa", ""))).append("\n");
        sb.append(naStred("IČO: " + obchod.getProperty("ico", ""))).append("\n");
        sb.append(cara);
        sb.append(cas.format(DateTimeFormatter.ofPattern("d.M.yyyy HH:mm"))).append("\n");
        sb.append(cara);
        for (Polozka p : polozky) {
            sb.append(p.getNazev()).append("\n");
            sb.append(radek("  " + p.getPocet() + " x " + kc(p.getCena()), p.celkem()));
        }
        sb.append(cara);
        sb.append(radek("CELKEM Kč", celkem()));
        sb.append(cara);
        sb.append(naStred(obchod.getProperty("pozdrav", ""))).append("\n");
        return sb.toString();
    }

    private static String radek(String vlevo, BigDecimal castka) {
        String vpravo = kc(castka);
        int mezery = Math.max(1, SIRKA - vlevo.length() - vpravo.length());
        return vlevo + " ".repeat(mezery) + vpravo + "\n";
    }

    private static String kc(BigDecimal castka) {
        return castka.setScale(2, RoundingMode.HALF_UP).toPlainString().replace('.', ',');
    }

    private static String naStred(String text) {
        return " ".repeat(Math.max(0, (SIRKA - text.length()) / 2)) + text;
    }
}
