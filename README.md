# Projet MOBG6

Ce dépôt contient les sources du projet <My NBA APP>.

## Description

L'application permet d'avoir une seul application pour tout les fans de NBA voulant en apprendre d'avantage sur la ligue majeur de basket. L'utilisateur doit tout d'abord se connecter à l'application et sera redirigé vers la page d'accueil. Sur cette page, il y'a 3 contenus: les équipes avec leurs stats les joueurs avec leurs détails également et les matchs à venir ou passées!

## Persistance des données

L'application conserve les favoris de l'utilisateur que ce soit équipe ou joueur.

## Backend (En cours de développement)

La partie backend de l'application est actuellement en cours de développement. Je prévois d'implémenter les fonctionnalités suivantes une fois que l'architecture et les technologies appropriées auront été définies :

1. Gestion des utilisateurs : Pour gérer les utilisateurs, j'ai mis en place un système d'authentification en utilisant Firebase Authentication. Cela me permet de gérer les processus suivants :

    Inscription et Connexion : Les      utilisateurs peuvent s'inscrire et se connecter facilement à l'application grâce à leur adresse mail et mot de passe.

2. Persistance des données : Intégration d'une base de données relationnelle pour stocker les informations sur les favoris des utilisateurs en utilisant Firebase Firestore.

3. Communication avec l'API externe : Mise en place de requêtes HTTP pour récupérer les données sur les équipes et les joueurs à partir de l'API https://sportsdata.io/.




## Service rest
Pour collecter les données relatives aux équipes et joueurs, des appels au service rest suivant sont effectués : https://sportsdata.io/
## Auteur

**Mehdi, Ilias** g55727
