# Livraison - Système d'Optimisation de Livraison

## 📋 Description
Application Spring Boot pour l'optimisation des tournées de livraison. Ce projet permet de gérer et d'optimiser les itinéraires de livraison en utilisant des algorithmes d'optimisation et l'IA.

## 🚀 Fonctionnalités

- Gestion des tournées de livraison
- Optimisation des itinéraires
- Intégration avec des services externes via API REST
- Interface d'administration
- Base de données relationnelle avec H2
- Gestion des dépendances avec Maven

## 🛠️ Prérequis

- Java 17 ou supérieur
- Maven 3.6.0 ou supérieur
- Base de données H2 (embarquée) ou configuration d'une base de données externe

## 🚀 Installation

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/amhine/livraison_v2.git
   cd Livraison_v2
   ```

2. Configurer les variables d'environnement :
   Créer un fichier `.env` à la racine du projet avec les variables nécessaires.

3. Construire le projet :
   ```bash
   mvn clean install
   ```

4. Lancer l'application :
   ```bash
   mvn spring-boot:run
   ```

## 🏗️ Structure du Projet

```
lviraison/
├── src/
│   ├── main/java/com/livraison/
│   │   ├── config/         # Configurations Spring
│   │   ├── controller/     # Contrôleurs REST
│   │   ├── dto/            # Objets de transfert de données
│   │   ├── entity/         # Entités JPA
│   │   ├── mapper/         # Mappers MapStruct
│   │   ├── optimizer/      # Logique d'optimisation
│   │   ├── repository/     # Couche d'accès aux données
│   │   ├── service/        # Couche métier
│   │   └── util/           # Utilitaires
│   └── resources/
│       ├── application.yml # Configuration de l'application
│       └── db/changelog/   # Scripts de migration Liquibase
└── pom.xml                 # Configuration Maven
```
## 📸 Illustrations
🟦 Swagger UI
<p align="center"> <img width="100%" src="https://github.com/user-attachments/assets/ae024882-42cd-4322-9cf7-83fe177d1366" /> <br><br> <img width="100%" src="https://github.com/user-attachments/assets/fdf69b56-5b10-42ab-8bfa-054a112fbd31" /> <br><br> <img width="100%" src="https://github.com/user-attachments/assets/181c6b95-55d8-4433-95fe-45473d1d4852" /> <br><br> <img width="100%" src="https://github.com/user-attachments/assets/c1adfd81-6dcc-4c79-8dfb-eefafabab67a" /> </p>
🟩 Tests Postman
<p align="center"> <img width="500" src="https://github.com/user-attachments/assets/2a0de1b5-7c01-490f-8b8f-8d3a09e7c96f" /> </p>
🟧 Structure du projet
<p align="center"> <img width="650" src="https://github.com/user-attachments/assets/912f61e8-0de4-4f00-a310-e6bb87639dcf" /> </p>
🟥 Schéma UML
<p align="center"> <img width="900" src="https://github.com/user-attachments/assets/6f471880-75cf-43d2-902d-d50960e2a25b" /> </p>


## 🔧 Configuration

Les principales configurations se trouvent dans `src/main/resources/application.yml`.

## 📚 Technologies Utilisées

- **Backend** : Spring Boot 3.3.5
- **Base de données** : H2 (développement), configurable pour d'autres SGBD
- **Gestion des dépendances** : Maven
- **Mapping objet-relationnel** : Spring Data JPA
- **API REST** : Spring Web
- **Traitement asynchrone** : WebFlux
- **Migration de base de données** : Liquibase
- **Mapping objet-objet** : MapStruct
- **Traitement des variables d'environnement** : Dotenv
- **IA** : Spring AI

