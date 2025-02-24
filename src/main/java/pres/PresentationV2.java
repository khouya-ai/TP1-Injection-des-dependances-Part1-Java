package pres;

import dao.IDao;
import ext.DaoImplV2;
import metier.IMetier;
import metier.MetierImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
