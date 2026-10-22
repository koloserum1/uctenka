package cz.hospoda.uctenka;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String verze = Main.class.getPackage().getImplementationVersion();
        System.out.println("Účtenka " + (verze != null ? verze : "dev"));

        String soubor = args.length > 0 ? args[0] : "uctenka.properties";
        Properties obchod;
        try {
            obchod = nactiObchod(Path.of(soubor));
        } catch (IOException | InvalidPathException e) {
            System.err.println("Nejde načíst soubor " + soubor + ": " + e.getMessage());
            System.exit(1);
            return;
        }

        Uctenka uctenka = new Uctenka(obchod);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadávej položky, prázdný název = konec.");
        while (true) {
            String nazev = precti(scanner, "Název: ");
            if (nazev == null || nazev.isBlank()) {
                break;
            }
            String cena = precti(scanner, "Cena: ");
            String pocet = precti(scanner, "Počet: ");
            if (cena == null || pocet == null) {
                break;
            }
            try {
                uctenka.pridej(new Polozka(nazev, parseCena(cena), Integer.parseInt(pocet.trim())));
            } catch (NumberFormatException e) {
                System.out.println("Špatné číslo, položka se nepřidala.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Položka se nepřidala.");
            }
        }

        if (uctenka.jePrazdna()) {
            System.out.println("Žádné položky, nic se netiskne.");
            return;
        }
        System.out.println();
        System.out.print(uctenka.vytiskni(LocalDateTime.now()));
    }

    static Properties nactiObchod(Path soubor) throws IOException {
        Properties p = new Properties();
        try (Reader r = Files.newBufferedReader(soubor, StandardCharsets.UTF_8)) {
            p.load(r);
        }
        return p;
    }

    static BigDecimal parseCena(String text) {
        return new BigDecimal(text.trim().replace(',', '.'));
    }

    private static String precti(Scanner scanner, String vyzva) {
        System.out.print(vyzva);
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }
}
