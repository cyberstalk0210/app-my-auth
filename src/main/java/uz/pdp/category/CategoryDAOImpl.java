package uz.pdp.category;

import uz.pdp.BaseImpl;

public class CategoryDAOImpl extends BaseImpl<Category, Long> implements CategoryDAO {

    public CategoryDAOImpl(Class<Category> categoryClass) {
        super(categoryClass);
    }
}
