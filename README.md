# javaFullStack_Oranoos – Maaltijdboxen

Examenproject Fullstack Java (PXL). Microservices-backend met Spring Boot 4.0.8 en Java 25, later een Angular-frontend.

## Structuur van de repository

| Map | Inhoud |
|---|---|
| `exam/backend` | Het examenproject: multi-module Maven-project met de vijf services |
| `exam/docs` | Analyse, C2- en C4-diagram (`docs/architecture`), wireframes |
| `exam/frontend` | Angular-frontend (later) |
| `microservices` | Labo-oefeningen uit de lessen (employee, department, organization, notification) |

## Services en waarom deze grenzen

| Service | Poort | Database | Verantwoordelijkheid |
|---|---|---|---|
| recipe-service | 8081 | `recipe` (3316) | Weekmenu, recepten en ingrediënten |
| order-service | 8082 | `orders` (3317) | Bestellingen plaatsen, opvolgen en annuleren |
| inventory-service | 8083 | `inventory` (3318) | Voorraad en reservaties per bestelling |
| delivery-service | 8084 | `delivery` (3319) | Leverslots en ritten van de chauffeur |
| notification-service | 8085 | geen | Meldingen naar de klant (schrijft naar de log) |

- **Recipe en inventory apart.** Een recept verandert met het weekmenu, de voorraad bij elke levering en bestelling. Twee redenen om te wijzigen, dus twee services. "Ingrediënt" betekent ook iets anders: "200 g per persoon" tegenover "nog 12 kg in stock".
- **Delivery apart.** Tijdsloten en capaciteit zijn een eigen probleem, en een chauffeur ziet iets anders dan een klant.
- **Geen customer-service.** De klant-id komt uit de header `X-User-Id` en het adres reist mee met de bestelling.
- **Eigen database per service.** Verwijzingen naar een andere service zijn gewone `Long`-id's, geen `@ManyToOne`.

Meer uitleg staat in `exam/docs/analyse.md`.

## Lokaal opstarten

Vereist: Java 25, Maven, Docker.

1. Start de vier databases:

```bash
docker run -d --name mb-recipe -p 3316:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=recipe mysql:8.4
docker run -d --name mb-orders -p 3317:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=orders mysql:8.4
docker run -d --name mb-inventory -p 3318:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=inventory mysql:8.4
docker run -d --name mb-delivery -p 3319:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=delivery mysql:8.4
```

2. Bouw het project:

```bash
cd exam/backend
mvn clean install -DskipTests
```

3. Start een service vanuit IntelliJ of met `mvn spring-boot:run -pl recipe-service`.

Vanaf week 14 komt hier `docker compose up -d`.

## Vereenvoudigingen

- Geen echte login: rol en gebruiker komen uit de headers `X-User-Role` en `X-User-Id`.
- Geen customer-service: het leveradres zit in de bestelling.
- Eén rit per leverslot, de chauffeur komt uit testdata. Geen routeplanning.
- notification-service verstuurt geen echte mails, enkel een logregel.
