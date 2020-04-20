package application.rest.v1.product;

import application.rest.v1.category.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    public List<Product> findByNameIgnoreCaseContainingAndStockEqualsAndCategoryVendorPincode(String name, Integer stock, Long pincode);

    public List<Product> findByCategory(Category category);
}
