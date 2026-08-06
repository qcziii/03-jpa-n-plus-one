# 03 - JPA: DTO, encje, relacje i N+1

## Cel

Rozpoznać problem N+1 po logach SQL i naprawić go kilkoma sposobami.

## Zadania

1. Uruchom aplikację i wejdź na `/authors`.
2. Sprawdź logi SQL.
3. Policz, ile zapytań wykonało się przy pobraniu autorów i ich książek.
4. Wyjaśnij, skąd bierze się N+1.
5. Zaproponuj i zaimplementuj poprawkę.
6. Porównaj logi SQL przed zmianą i po zmianie.

## Pytania kontrolne

- Po co tworzymy DTO?
- Dlaczego nie zawsze zwracamy encję z kontrolera?
- Czy DTO musi mieć mniej pól niż encja?
- Czym DTO różni się od widoku w bazie danych?

## Uruchomienie

```bash
mvn spring-boot:run
```
