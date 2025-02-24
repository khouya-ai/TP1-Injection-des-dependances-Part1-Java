## Activité Pratique N°1 - Injection des dépendances

Ce TP permet d'appliquer les bonnes pratiques de développement, comme avoir une application fermée à la modification et ouverte à l'extension, en utilisant les interfaces, l'injection de dépendances et en séparant le code en un ensemble de packages.

On va essayer d'implementer l'exemple suivant:

![img.png](images/img.png)
### Creation des couches dao, metier et presentation
![img_1.png](img_1.png)
### Creation de l'interface IDao avec une méthode getData
    ```java
    public interface IDao {
        double getData();
    }
    ```
### Creation d'une implémentation de cette interface
```java
public class DaoImpl implements IDao {
@Override
public double getData() {
  System.out.println("Version base de données");
  double temp = 23;
  return temp;
 }
}
```
### Creation de l'interface IMetier avec une méthode calcul
```java
public interface IMetier {
    double calcul();

    public void setDao(IDao dao);
}
```
### Creation d'une implémentation de cette interface en utilisant le couplage faible
```java
public class MetierImpl implements IMetier {

    //Couplage faible
    private IDao dao = null;

    @Override
    public double calcul() {
        double t = dao.getData();
        double res = t * 23;
        return res;
    }

    // Pour Injecter dans la variable dao un objet
    // d'une classe qui imlémente dl'interface IDao
    @Override
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
```
## L'injection des dépendances :
### <u>Injection par instanciation statique via le Setter</u>
 ```java
 public class PresentationV1 {
     public static void main(String[] args) {
         // Injection des dépendances par instanciation statique (new)
         DaoImpl dao = new DaoImpl();
         MetierImpl metier = new MetierImpl();
         metier.setDao(dao); // Injection via le setter
         System.out.println("Resulat = " + metier.calcul());
     }
 }
 ```

### <u>Injection par instanciation statique via le constructeur </u>
```java
public class PresentationV1 {
    public static void main(String[] args) {
        // Injection des dépendances par instanciation statique (new)
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl(dao); // Injection via constructeur
        System.out.println("Resulat = " + metier.calcul());
    }
}
 // on ajoute les deux constructeur 
public class MetierImpl implements IMetier {

    public MetierImpl() { // pour le setter
    }

    public MetierImpl(IDao dao) { // pour l'injection via constructreur
        this.dao = dao;
    }

}
```
   Maintenant nous voulons creer une autre implimentation de l'interface IDao [DaoImplV2.java](src/main/java/ext/DaoImplV2.java)
```java
public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version Web Service");
        double temp = 11;
        return temp;
    }
}
```
Pour l'utiliser on est obligé de changer le code dans [PresentationV1.java](src/main/java/pres/PresentationV1.java)
```java
public class PresentationV1 {
    public static void main(String[] args) {
        IDao dao = new DaoImplV2(); // Changement
        MetierImpl metier = new MetierImpl(dao); 
        System.out.println("Resulat = " + metier.calcul());
    }
}
```
### <u>Injection par instanciation dynamique via le constructeur</u>
1. créer un fichier [config.txt](config.txt)
   ```text
   dao.DaoImpl
   metier.MetierImpl
   ```
 2. créer une class [PresentationV2.java](src/main/java/pres/PresentationV2.java) pour refaire le code statique dans [PresentationV1.java](src/main/java/pres/PresentationV1.java) dynamiquement via le fichier **config.txt**

```java
public class PresentationV2 {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            // Instanciation dynamique d'une implimentation de IDao
            // equivalent à IDao dao = new DaoImplV2();
            String daoClassName = scanner.nextLine();
            Class cDao = Class.forName(daoClassName);
            IDao dao = (IDao) cDao.getConstructor().newInstance();

            // Instanciation dynamique d'une implimentation de IMetier
            // equivalent à MetierImpl metier = new MetierImpl(dao);
            String metierClassName = scanner.nextLine();
            Class cMetier = Class.forName(metierClassName);
            IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);
            System.out.println("Resulat = " + metier.calcul());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```
### <u>Injection par instanciation dynamique via le setter</u>

```java
public class PresentationV2 {
   public static void main(String[] args) {
      try {
         Scanner scanner = new Scanner(new File("config.txt"));
         // Instanciation dynamique d'une implimentation de IDao
         // equivalent à IDao dao = new DaoImplV2();
         String daoClassName = scanner.nextLine();
         Class cDao = Class.forName(daoClassName);
         IDao dao = (IDao) cDao.getConstructor().newInstance();

         // Instanciation dynamique d'une implimentation de IMetier
         // equivalent à MetierImpl metier = new MetierImpl(dao);
         String metierClassName = scanner.nextLine();
         Class cMetier = Class.forName(metierClassName);
         IMetier metier = (IMetier) cMetier.getConstructor().newInstance();

         // Injection via setter
         // equivalent à metier.setDao(dao);
         Method setDaoMethod = cMetier.getDeclaredMethod("setDao", IDao.class);
         setDaoMethod.invoke(metier, dao);

         System.out.println("Resulat = " + metier.calcul());
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
   }
}
```
- c. En utilisant le Framework Spring
  - Version XML
  - Version annotations