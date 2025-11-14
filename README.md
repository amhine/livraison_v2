

# 🚚 Système de Gestion Optimisée des Tournées de Livraison V2

## 🎯 Présentation

Le **Système de Gestion Optimisée de Tournées de Livraison** est une application **Spring Boot** permettant de planifier, gérer et optimiser les trajets de livraison afin de **réduire les distances parcourues** et **améliorer l’efficacité logistique**.
Elle s’appuie sur deux algorithmes d’optimisation afin de comparer leurs performances.

### Objectifs

* Gérer la flotte de véhicules et les entrepôts
* Planifier automatiquement les tournées de livraison
* Comparer deux méthodes d’optimisation : **Nearest Neighbor** et **Clarke & Wright**
* Exposer toutes les fonctionnalités via une **API REST complète et documentée**

---

## ✨ Fonctionnalités principales

### 🔹 Gestion des entités

* **CRUD complet** pour les : Entrepôts, Véhicules, Livraisons et Tournées
* Gestion des statuts de livraison : `PENDING`, `IN_TRANSIT`, `DELIVERED`, `FAILED`
* Prise en compte des **contraintes des véhicules** (poids, volume, capacité)

### 🔹 Optimisation des tournées

* **Nearest Neighbor** : algorithme rapide, donnant une solution locale
* **Clarke & Wright** : algorithme des économies, produisant une solution plus optimale
* Calcul automatique des distances via la **formule de Haversine**
* **Validation en temps réel** des contraintes de capacité des véhicules

---

## 🛠️ Technologies utilisées

| Technologie           | Version | Utilisation                     |
| --------------------- | ------- | ------------------------------- |
| **Java**              | 17+     | Langage principal               |
| **Spring Boot**       | 3.3.5   | Framework backend               |
| **Spring Data JPA**   | -       | Gestion et accès aux données    |
| **H2 Database**       | -       | Base de données en mémoire      |
| **Maven**             | 3.6+    | Gestion de dépendances et build |
| **Lombok**            | -       | Réduction du code répétitif     |
| **Swagger / OpenAPI** | -       | Documentation de l’API          |
| **JUnit 5**           | -       | Tests unitaires                 |

---

## 🧩 Architecture du projet

### 📁 Structure

```
src/main/java/com/livraison/
├── config/           # Configuration Spring & beans
├── controller/       # Endpoints REST
├── dto/              # Objets de transfert de données
├── entity/           # Entités JPA
├── mapper/           # Mapping DTO ↔ Entity
├── optimizer/        # Algorithmes d'optimisation
├── repository/       # Accès à la base de données
├── service/          # Logique métier
└── util/             # Fonctions utilitaires (calculs de distance)

src/main/resources/
├── application.properties   # Configuration principale
├── applicationContext.xml   # Configuration complémentaire (beans)
└── data.sql                 # Jeu de données de test
```

### 🧠 Couches applicatives

1. **Controller** → Expose les endpoints REST
2. **Service** → Contient la logique métier et les règles d’optimisation
3. **Repository** → Interagit avec la base de données via Spring Data JPA
4. **Optimizer** → Implémente les stratégies de calcul (pattern *Strategy*)

---

## ⚙️ Installation et exécution

### 🔧 Prérequis

* **JDK 17+** avec `JAVA_HOME` configuré
* **Maven 3.6+**
* **Git**
* **IDE** (IntelliJ IDEA recommandé)
* **Postman** (pour tester l’API)

### 🚀 Étapes d’installation

1. **Cloner le projet**

```bash
git clone https://github.com/amhine/livraison.git
cd livraison
```

2. **Compiler le projet**

```bash
mvn clean install
```

3. **Lancer l’application**

```bash
./mvnw spring-boot:run
```

L’application démarre par défaut sur :
👉 `http://localhost:8083`

---

## 🚗 Contraintes des véhicules

| Type      | Poids max | Volume max | Livraisons max |
| --------- | --------- | ---------- | -------------- |
| **BIKE**  | 50 kg     | 0.5 m³     | 15             |
| **VAN**   | 1000 kg   | 8 m³       | 50             |
| **TRUCK** | 5000 kg   | 40 m³      | 100            |

---

## 🌐 Documentation API REST

**Base URL** : `http://localhost:8083/api`

### 🏢 Entrepôts (Warehouses)

| Méthode | Endpoint           | Description            |
| ------- | ------------------ | ---------------------- |
| GET     | `/warehouses`      | Liste des entrepôts    |
| GET     | `/warehouses/{id}` | Détails d’un entrepôt  |
| POST    | `/warehouses`      | Création d’un entrepôt |
| PUT     | `/warehouses/{id}` | Mise à jour            |
| DELETE  | `/warehouses/{id}` | Suppression            |

### 🚙 Véhicules (Vehicles)

| Méthode | Endpoint         | Description           |
| ------- | ---------------- | --------------------- |
| GET     | `/vehicles`      | Liste des véhicules   |
| GET     | `/vehicles/{id}` | Détails d’un véhicule |
| POST    | `/vehicles`      | Ajout d’un véhicule   |
| PUT     | `/vehicles/{id}` | Mise à jour           |
| DELETE  | `/vehicles/{id}` | Suppression           |

### 📦 Livraisons (Deliveries)

| Méthode | Endpoint                  | Description             |
| ------- | ------------------------- | ----------------------- |
| GET     | `/deliveries`             | Liste des livraisons    |
| GET     | `/deliveries/{id}`        | Détails d’une livraison |
| POST    | `/deliveries`             | Création                |
| PUT     | `/deliveries/{id}`        | Modification            |
| PATCH   | `/deliveries/{id}/status` | Changement de statut    |
| DELETE  | `/deliveries/{id}`        | Suppression             |

### 🗺️ Tournées (Tours)

| Méthode | Endpoint               | Description                |
| ------- | ---------------------- | -------------------------- |
| GET     | `/tours`               | Liste des tournées         |
| GET     | `/tours/{id}`          | Détails d’une tournée      |
| POST    | `/tours`               | Création                   |
| POST    | `/tours/optimize`      | Optimisation d’une tournée |
| GET     | `/tours/{id}/distance` | Distance totale            |
| DELETE  | `/tours/{id}`          | Suppression                |

---

## 📸 Illustrations

| **Section**             | **Aperçu**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| ----------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Swagger UI**          | <p align="center"><img width="1461" height="414" src="https://github.com/user-attachments/assets/ae024882-42cd-4322-9cf7-83fe177d1366" /><br><img width="1476" height="669" src="https://github.com/user-attachments/assets/6e708dfb-5f52-428c-abb6-4b6683134fc2" /><br><img width="1495" height="718" src="https://github.com/user-attachments/assets/181c6b95-55d8-4433-95fe-45473d1d4852" /><br><img width="1494" height="660" src="https://github.com/user-attachments/assets/c1adfd81-6dcc-4c79-8dfb-eefafabab67a" /></p> |
| **Tests Postman**       | <p align="center"><img width="497" height="534" src="https://github.com/user-attachments/assets/2a0de1b5-7c01-490f-8b8f-8d3a09e7c96f" /></p>                                                                                                                                                                                                                                                                                                                                                                                   |
| **Structure du projet** |  <p align="center"><img width="617" height="671" alt="image" src="https://github.com/user-attachments/assets/912f61e8-0de4-4f00-a310-e6bb87639dcf" />
</p>                                                                                                                                                                                                                                      |
| **Schéma UML**          | <img width="904" height="592" alt="Capture d’écran du 2025-11-14 10-03-11" src="https://github.com/user-attachments/assets/6f471880-75cf-43d2-902d-d50960e2a25b" />


                                                                                                                                                                                                                                                                                                                                                                                   |


---

## 🧾 Outils d’évaluation et de test

1. **Swagger UI** – Documentation interactive de l’API
2. **Postman** – Tests des endpoints et automatisation
3. **H2 Console** – Consultation en temps réel de la base de données
4. **Logs Spring Boot** – Suivi des optimisations et diagnostics
5. **Diagrammes UML** – Vue globale de l’architecture et des relations
