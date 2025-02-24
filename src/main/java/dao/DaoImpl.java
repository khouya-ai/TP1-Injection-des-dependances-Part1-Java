package dao;

public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Verstion base de données");
        double temp = 23;
        return temp;
    }
}
