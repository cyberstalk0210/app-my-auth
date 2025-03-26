package uz.pdp.product;

import uz.pdp.BaseImpl;
import uz.pdp.ProductDAO;

public class ProductDAOImpl extends BaseImpl<Product, Integer> implements ProductDAO {
    public ProductDAOImpl(Class<Product> entityClass) {
        super(entityClass);
    }
}
