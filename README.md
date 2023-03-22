# HCERES-Spring-Boot

[Liste de tous les doc](doc/README.md)

Site currently hosted on [https://hceres.azurewebsites.net/](https://hceres.azurewebsites.net/)

## Initialisation du projet

* Require java 8+, maven, npm, postgres
* Executer le fichier [HCERES.sql](hceres/src/main/SQLdata/HCERES.sql) dans votre base de données
* Copier le fichier [application_template.properties](hceres/src/main/resources/application_template.properties)
  la configuration en meme endroit avec le nom application.properties
* Modifier la configuration pour la connection a la base de donnee selon votre setup.
  (note de cette facon on évite le track de fichier)

* Lancement du spring boot api backend:
    - Ouvrir un terminal et naviguer vers [hecers](hceres)
    - Lancer le commande **mvn spring-boot:run**

* Lancement du react web app frontend:
    - Ouvrir un autre terminal and navigeur vers [hceres\-frontend](hceres-frontend)
    - Lancer le commande pour une seule fois : **npm install --legacy-peer-deps**
    - Attender le chargement de tous les modules (ça prend peu du temps la premiere fois)
    - Lancer le commande de demarrage du web avec : **npm start**

* ouvrir le navigateur vers le lien suivant : **[http://localhost:3000](http://localhost:3000)**

## Configuration avec Intellij

* Telecharger Intellij depuis leur site
  officiel: [Intellij IDEA](https://www.jetbrains.com/idea/download/#section=windows)

* Activer la version ultimate offert pour les étudiants en utilisant votre mail de l'école

* Spring boot Backend
    - Right click on [pom.xml](hceres/pom.xml)
    - add as maven project
    - Naviguer vers la classe [org.centrale.hceres.HceresApplication](hceres/src/main/java/org/centrale/hceres/HceresApplication.java) 
  puis lancer la fonction main, ce lancement est sauvegardé par Intellij dans la configuration.
    - ou bien en haut à droite, cliquer sur le drop down list de run → Edit run configuration
    - cliquer le button + pour ajouter une nouvelle configuration
    - Choisir spring boot
        - choisir le
          classe [org.centrale.hceres.HceresApplication](hceres/src/main/java/org/centrale/hceres/HceresApplication.java)
          souvent automatiquement suggéré par Intellij
    - Ok

* React Frontend
    - Right click on [package.json](hceres-frontend/package.json) → Show npm Scrips → Lancer le script 'start'
      ce lancement est sauvegardé par Intellij dans la configuration.
    - Ou bien en haut à droite, cliquer sur le drop down list de run → Edit run configuration
    - cliquer le button + new configuration
    - Choisir npm
        - package json pointant vers [package.json](hceres-frontend/package.json) automatiquement suggéré par Intellij
        - Command = start
    - Ok
* Maintenant on peut lancer (également debugger) le project avec les buttons dans Intellij

* Rule #1 Accepter toutes les suggestions d'Intellij :) !
* Happy coding.
