# Adatbázisok projekt

## Ez az alkalmazás a Szegedi Tudományegyetem Adatbázisok kurzus kötelező programjaként készült el.

### Az alábbiakban egy részlet található a programhoz tartozó dokumentációból

Az alkalmazás a Java 22.0.2-es verzióját haználja, és a JavaFX 23.0.1-es verzióját.

Az elkészült alkalmazás funkciói
- Bejelentkezés, regisztráció:
  - Regisztráció során a jelszó titkosítva kerül bejegyzésre az adatbázisba.
-	Menetkövetés több jogosultsági szinttel:
  -	Bejelentkezett felhasználó látja a saját ingatlanjait és telkeit, illetve statiszikákat tekinthet meg a portfoliójáról, és módosíthatja saját adatait.
  -	Bejelentkezett „moderátor” hozzá adni, módosítani illetve törölni tud ingatlanokat és telkeket.
-	Telek, ingatlan CRUD műveletek:
    -	Create:
        -	Telket és Ingatlant is létrehozhat egy moderátor.
        - Ingatlannál meg kell jelölni a hozzátartozó telket.
        - Meg lehet adni a tulajdonost, és hogy mekkora hanyadban tulajdonos.
        - Ellenőrizve van a tulajdonosi hanyad, hogy ne forduljon elő több mint 100%os tulajdoni hanyad egy bizonyos telken vagy ingatlanon.
    - Read:
        - Minden felhasználó látja a saját telkeit, a moderátor pedig az összeset.
        - A felhasználók az adatbázisban lévő telkek és ingatlanok alapján statisztikákat tudnak megtekinteni.
    -	Update:
        - Egy moderátor a táblázat egyes celláira dupla kattintással tudja módosítani is azt, és az enter gomb lenyomásával már le is menti az alkalmazás a változtatást az adatbázisban, ha érvényes adatot írt be.
    - Delete:
        - A moderátornak az összes telek és összes ingatlan táblázatai szélső oszlopában megjelennek a bejegyések mellett egy törlés gomb, amivel gyorsan ki lehet törölni egy egy bejegyzést.
        - Ha egy telek kitörlésre kerül, az összes rajta lévő ingatlan is törlésre kerül.
        - Felugró ablak biztosítja, hogy ne lehessen véletlenül kitörölni egyes bejegyzéseket, ha véletlen történne kattintás a törlés gombra.
- Felhasználói adatmódosítás:
    - Egy felhasználó tudja módosítania az adatait, ha bejelentkezés után az „Adatok módosítása” gombra kattint.
       - A biztonság szempontjából mindig köteles a felhasználó beírni a jelszavát ha adatot akar változtatni, akkor is, ha nem változtat jelszót.
-  Statisztikák megtekintése:
    - Egy bejelentkezett felhasználó különböző statisztikákat tud megtekinteni az ingatlan portfóliójáról, ezek az alábbiak:
      - Tulajdonban lévő ingatlanok száma,
      - Tulajdonban lévő telkek száma,
      - Tulajdonban lévő ingatlanok összértéke,
      - Tulajdonban lévő telkek összértéke,
      - Teljes ingatlanportfolió összértéke,
      - Ingatlanok mennyisége jelleg szerint csoportosítva,
      - Telkek mennyisége jelleg szerint csoportosítva.
