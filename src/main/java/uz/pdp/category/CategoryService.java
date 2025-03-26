package uz.pdp.category;

import java.util.List;

public class CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAOImpl(Category.class);

    public List<Category> list() {
        return categoryDAO.findAll();
    }
}
