# Beslissingen

Per week: wat ik gedaan heb, wat nog open staat, en welke keuze ik gemaakt heb en waarom.

## Week 8 – Analyse en servicegrenzen

- Gedaan: analyse (`exam/docs/analyse.md`), C2-containerdiagram, C4-domeinmodel en wireframes.
- Keuze: vijf services (recipe, order, inventory, delivery, notification). Recipe en inventory apart, omdat ze om een andere reden veranderen en "ingrediënt" in beide iets anders betekent.
- Keuze: geen customer-service. Het adres reist mee met de bestelling; dat is genoeg voor deze case.
- Keuze: bestellen is synchroon (Feign naar recipe, inventory en delivery), alles na de bevestiging asynchroon via RabbitMQ.
- Open: voorraad wordt synchroon gecontroleerd maar pas asynchroon gereserveerd. Twee gelijktijdige bestellingen kunnen allebei door de controle raken.

## Week 9 – Eerste services

- Gedaan: multi-module `pom.xml` in `exam/backend` met vijf modules. Elke service behalve notification heeft een eigen MySQL-database (poort 3316–3319).
- Gedaan: recipe-service volledig in drie lagen (controller, service, repository) met DTO-records, validatie en een `GlobalExceptionHandler`. Order- en inventory-service hebben hun entities; inventory ook repositories en service.
- Keuze: regels staan in de entity zelf, zoals `Order.cancel()` (enkel bij `PLACED` of `CONFIRMED`) en `StockItem.canReserve()`. Zo kan de service ze niet omzeilen.
- Keuze: verwijzingen naar een andere service zijn gewone `Long`-id's, geen `@ManyToOne`, omdat elke service een eigen database heeft.
- Open: controllers voor order en inventory, delivery-service volledig, en de tests (week 10).
