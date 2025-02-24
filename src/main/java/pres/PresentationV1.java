package pres;

import dao.IDao;
import ext.DaoImplV2;
import metier.MetierImpl;

public class PresentationV1 {
    public static void main(String[] args) {
        // Injection des dépendances par instanciation statique (new)
        IDao dao = new DaoImplV2();
        MetierImpl metier = new MetierImpl(dao); // Injection via constructeur
        System.out.println("Resulat = " + metier.calcul());
    }
}
