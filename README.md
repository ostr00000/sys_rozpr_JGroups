## Zadanie - komunikacja grupowa
### Cel zadania
Celem zadania jest implementacja rozproszonej tablicy haszującej. Aplikacje z niej korzystające powinny mieć możliwość dodawania elementów do tablicy i jednocześnie pobierania elementów wcześniej dodanych, być może również przez inne aplikacje.

W wyniku realizacji zadania powinna powstać implementacja klasy DistributedMap, implementująca interfejs:
```
public interface SimpleStringMap {
    boolean containsKey(String key);

    String get(String key);

    String put(String key, String value);

    String remove(String key);
}
```
 

Powinna też zostać opracowana przykładowa aplikacja korzystająca z rozproszonej tablicy haszującej. Funkcjonalność aplikacji powinna umożliwiać interaktywną interakcję i eksponować metody zawarte w interfejsie implementowanej tablicy.

### Własności rozproszonej tablicy
Uwzględniając teorię CAP , implementacja rozwiązania powinna cechować się dostępnością i tolerowaniem partycjonowania.

W związku z tym każda instancja klasy DistributedMap powinna mieć własną kopię tablicy rozproszonej, a uspójnianie stanu powinno być zrealizowana podczas operacji dodawania elementów do tablicy. Do rozproszonej komunikacji pomiędzy instancjami należy wykorzystać bibliotekę JGroups:

- w przypadku tworzenia nowej instancji klasy DistributedMap, powinna ona pozyskać początkowy stan od członków grupy, do której dołącza,
- w przypadku podziału grupy węzłów na dwie partycje, powinny one utrzymywać swój stan niezależnie; w przypadku ponownego scalania dwóch partycji, członkowie jednej z losowo wybranych partycji powinno stracić swój stan i pozyskać go na nowo od członków drugiej z partycji.
### Dodatkowe informacje
Ze względu na swój rozproszony charakter, aplikacja będzie testowana w laboratorium, i w sposób naturalny będzie wchodzić w interakcję z aplikacjami innych studentów, dlatego warto rozważyć modyfikację domyślnego adresu grupowego wykorzystywanego przez protokół UDP:

new UDP().setValue("mcast_group_addr",InetAddress.getByName("230.0.0.x"))

Tutorial opisujący mechanizm przesyłania stanu za pomocą biblioteki JGroups:
http://www.jgroups.org/manual/index.html#StateTransfer

Tutorial opisujący mechanizm łączenia partycji za pomocą biblioteki JGroups:
http://www.jgroups.org/manual/index.html#HandlingNetworkPartitions

 

### Elementy oceny
- Podstawowa wymagana funkcjonalność aplikacji.
- Obsługa synchronizacji stanu po dołączeniu nowego węzła.
- Obsługa łączenia partycji.
