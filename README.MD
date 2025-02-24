## Activité Pratique N°1 - Injection des dépendances

Ce TP permet d'appliquer les bonnes pratiques de développement, comme avoir une application fermée à la modification et ouverte à l'extension, en utilisant les interfaces, l'injection de dépendances et en séparant le code en un ensemble de packages.

On va essayer d'implimenter l'exemple suivant: 
![img.png](images/img.png)
1. Créer des couches dao, metier et presentation
![img_1.png](img_1.png)
2. Créer l'interface IDao avec une méthode getData
3. Créer une implémentation de cette interface
4. Créer l'interface IMetier avec une méthode calcul
5. Créer une implémentation de cette interface en utilisant le couplage faible
6. Faire l'injection des dépendances :
   a. Par instanciation statique
   b. Par instanciation dynamique
   c. En utilisant le Framework Spring
   - Version XML
   - Version annotations