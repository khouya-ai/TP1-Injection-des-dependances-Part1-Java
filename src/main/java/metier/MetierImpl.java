package metier;

import dao.IDao;

public class MetierImpl implements IMetier {

    //Couplage faible
    private IDao dao = null;

    @Override
    public double calcul() {
        double t = dao.getData();
        double res = t * 23;
        return res;
    }

    public MetierImpl() {
    }

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    // Pour Injecter dans la variable dao un objet
    // d'une classe qui imlémente dl'interface IDao
    @Override
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
