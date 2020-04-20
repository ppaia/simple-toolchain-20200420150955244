package application.rest.v1.category;

import application.rest.v1.vendor.Vendor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    public List<Category> findByVendor(Vendor vendor);

    public List<Category>  findByParent(Long child);
}
