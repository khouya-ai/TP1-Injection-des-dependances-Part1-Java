package metier;

import dao.IDao;

public interface IMetier {
    double calcul();

    public void setDao(IDao dao);
}
