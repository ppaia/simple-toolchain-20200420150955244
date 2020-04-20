package application.rest.v1.category;

import application.rest.v1.product.Product;
import application.rest.v1.product.ProductService;
import application.rest.v1.vendor.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    public Category addCategory(Category category) throws Exception {
        if(category.getParent()!=null) {
            Category parentCat = categoryRepository.findById(category.getParent()).orElse(null);
            if(parentCat.getHasSubCategory() == 0) {
                throw new Exception("Cannot create SubCategory. You can only add Products under this category.");
            }
        }
        Category prodResp = categoryRepository.save(category);
        return prodResp;
    }

    public Category updateCategory(Category category) throws Exception {
        Category categoryResp = null;
        Category cat = getCategory(category.getChild());
        category.setParent(cat.getParent());
        category.setVendor(cat.getVendor());
        if(cat!=null && cat.getHasSubCategory()!=category.getHasSubCategory()) {
            List<Category> childCat = findByParent(category.getChild());
            List<Product> productsByCategory = productService.getProductsByCategory(category.getChild());
            if(childCat.size() > 0 ) {
                throw new Exception("Cannot change SubCategory flag. Remove the sub categories and then update.");
            } else if(productsByCategory.size() > 0) {
                throw new Exception("Cannot change SubCategory flag. Remove all the products under this category.");
            } else {
                categoryResp = categoryRepository.save(category);
            }
        } else {
            categoryResp = categoryRepository.save(category);
        }
        return categoryResp;
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public CategoryVO getCategoryByVendor(Long vendorId) {
        CategoryVO categoryVO = new CategoryVO();
        List<SubCategoryVO> categoryVOList = new ArrayList<>();
        Vendor vendor = new Vendor(vendorId);
        List<Category> categories = new ArrayList<>();
        categoryRepository.findByVendor(vendor).forEach(categories::add);

        categories.forEach(category -> {
            if(categoryVO.getText()==null) {
                Vendor vend = category.getVendor();
                categoryVO.setId(vend.getId());
                categoryVO.setText(vend.getShopname());
                categoryVO.setAddress1(vend.getAddress1());
                categoryVO.setAddress2(vend.getAddress2());
                categoryVO.setCity(vend.getCity());
                categoryVO.setState(vend.getState());
                categoryVO.setPincode(vend.getPincode());
                categoryVO.setPhone(vend.getPhone());
                categoryVO.setExpanded(true);
            }
            SubCategoryVO subCategoryVO = new SubCategoryVO();
            subCategoryVO.setChild(category.getChild());
            if(category.getHasSubCategory()==1)
                subCategoryVO.setExpanded(true);
            else
                subCategoryVO.setExpanded(false);
            subCategoryVO.setName(category.getName());
            subCategoryVO.setParent(category.getParent());
            categoryVOList.add(subCategoryVO);
        });

        Map<Long,SubCategoryVO> subCategoryMap = new TreeMap<Long, SubCategoryVO>();
        for(SubCategoryVO subCategoryVO : categoryVOList) {
            if(subCategoryVO.getParent()==null) {
                subCategoryMap.put(subCategoryVO.getChild(), subCategoryVO);
            } else {
                searchAndPut(subCategoryMap, subCategoryVO);
            }
        }
        List<SubCategoryVO> subCategoryVOList = new ArrayList<>();
        for ( Map.Entry<Long, SubCategoryVO> subCategoryEntry : subCategoryMap.entrySet()) {
            SubCategoryVO subCatVO = subCategoryEntry.getValue();
            if(subCatVO.getCategories().size()>0) {
                searchAndAdd(subCatVO);
            }
            subCategoryVOList.add(subCatVO);
        }
        System.out.println(subCategoryVOList);
        categoryVO.setSubCategoryVOList(subCategoryVOList);
        return categoryVO;
    }

    private void searchAndAdd(SubCategoryVO subCatVO) {
        List<SubCategoryVO> categoriesList = new ArrayList<>();
        for ( Map.Entry<Long, SubCategoryVO> subCategoryEntry : subCatVO.getCategories().entrySet()) {
            SubCategoryVO subCat = subCategoryEntry.getValue();
            if(subCat.getCategories().size()>0) {
                searchAndAdd(subCat);
            }
            subCatVO.getCategoriesList().add(subCat);
        }
    }

    private void searchAndPut(Map<Long, SubCategoryVO> subCategoryMap, SubCategoryVO subCategoryVO) {
        SubCategoryVO subCategory = subCategoryMap.get(subCategoryVO.getParent());
        if(subCategoryMap.containsKey(subCategoryVO.getParent())) {
            subCategory.getCategories().put(subCategoryVO.getChild(), subCategoryVO);
            return;
        } else {
            searchAndPut(subCategory.getCategories(), subCategoryVO);
        }
    }

    public void deleteCategory(Long id) throws Exception {
        Category categoryResp = null;
        Category category = getCategory(id);
        if (category == null) {
            throw new Exception("This Category does not exist.");
        }
        List<Category> categoryList = findByParent(id);
        List<Product> productsByCategory = productService.getProductsByCategory(id);
        if (categoryList.size() > 0) {
            throw new Exception("Cannot delete. Remove all the sub categories and then try.");
        } else if (productsByCategory.size() > 0) {
            throw new Exception("Cannot delete. Remove all the products under this category and then try.");
        }
        categoryRepository.deleteById(id);
    }

    private List<Category> findByParent(Long child) {
        List<Category> categoryList = new ArrayList<>();
        categoryRepository.findByParent(child).forEach(categoryList::add);
        return categoryList;
    }

    /*
    public List<Category> getCategoryById(Long id) {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    public List<Category> getProdByNameAndZipCode(String name, String zipcode) {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findByNameIgnoreCaseContaining(name).forEach(categories::add);
        return categories;
    }*/
}
