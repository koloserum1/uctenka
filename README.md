# Účtenka

Jednoduchý program pro příkazovou řádku, který vytiskne účtenku z hospody.

- Údaje o podniku (název, adresa, IČO, pozdrav) se načítají ze souboru `uctenka.properties`.
- Položky (název, cena, počet) zadává uživatel v konzoli. Prázdný název ukončí zadávání.

## Autoři

- Matej Hrica
- Adam Hupka
- Matúš Antal
- Peter Ondrašina

## Sestavení a spuštění

Potřeba je Java 17 nebo novější. Maven instalovat nemusíte, stačí přiložený `mvnw` (na Windows `mvnw.cmd`).

```bash
./mvnw package                          # přeloží, spustí testy, vytvoří jar
java -jar target/uctenka-1.0.0.jar      # spustit ze složky projektu
java -jar target/uctenka-1.0.0.jar jiny.properties
```

Ukázka:

```
========================================
        Hospoda U Zlatého bajtu
         Náměstí Javy 42, Brno
             IČO: 12345678
----------------------------------------
22.10.2026 18:30
----------------------------------------
Pivo
  3 x 59,00                       177,00
----------------------------------------
CELKEM Kč                         177,00
----------------------------------------
         Děkujeme a na zdraví!
```

## Jak vydat novou verzi

Verze je sémantická (`MAJOR.MINOR.PATCH`) a je zapsaná jen v `pom.xml`. Program ji vypíše při startu.

1. V `pom.xml` změňte `<version>`: oprava chyby 1.0.0 → 1.0.1, nová funkce → 1.1.0, nekompatibilní změna → 2.0.0.
2. `./mvnw clean package`
3. `git commit -am "Verze 1.0.1"`, pak `git tag v1.0.1` a `git push --tags`.
