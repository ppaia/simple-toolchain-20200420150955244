package application.rest.v1.product;

import application.rest.v1.category.Category;
import application.rest.v1.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    public Product addOrUpdateProduct(Product product) throws Exception {
        Category category = categoryRepository.findById(product.getCategory().getChild())
                .orElseThrow(() -> new Exception("No such category found."));
        if(category.getHasSubCategory()==1) {
            throw new Exception("Cannot add/update product. Select the childmost category to add/update product.");
        }
        Product prodResp = productRepository.save(product);
        return prodResp;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategory(Long catId) {
        Category category = new Category(catId);
        List<Product> products = new ArrayList<>();
        productRepository.findByCategory(category).forEach(products::add);
        return products;
    }

    public List<Product> getProdByNameAndZipCode(String name, Long zipcode) {
        List<Product> products = new ArrayList<>();
        productRepository.findByNameIgnoreCaseContainingAndStockEqualsAndCategoryVendorPincode(name, 1, zipcode).forEach(products::add);
        /*List<Product> productsByZipCode = new ArrayList<>();
        products.stream().filter(product -> zipcode == product.getCategory().getVendor().getPincode())
                .forEach(productsByZipCode :: add);
        return productsByZipCode;*/
        return products;
    }

}
