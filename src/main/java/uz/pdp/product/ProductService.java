package uz.pdp.product;

import uz.pdp.ProductDAO;

import java.util.List;

public class ProductService {

    //create, update, read, delete

    private final ProductDAO productDAO = new ProductDAOImpl(Product.class);

    public List<Product> list(int page, int size) {
        return productDAO.findAll(page, size);
    }

    public Product add(Product product) {
        return productDAO.save(product);
    }

    public Product update(Product product) {
        return productDAO.update(product);
    }

    public Product getProduct(Integer id) {
        return productDAO.findById(id);
    }

    public void delete(Integer id) {
        productDAO.deleteById(id);
    }
}
