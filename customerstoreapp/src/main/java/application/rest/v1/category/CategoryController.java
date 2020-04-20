package application.rest.v1.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public @ResponseBody Category addCategory(@RequestBody Category category) throws Exception {
        return categoryService.addCategory(category);
    }

    @PutMapping("/{id}")
    public @ResponseBody Category updateCategoryt(@PathVariable Long id, @RequestBody Category category) throws Exception {
        category.setChild(id);
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Map<String,Long> deleteCategory(@PathVariable Long id) throws Exception {
        categoryService.deleteCategory(id);
        Map<String,Long> deleteMap = new HashMap<>();
        deleteMap.put("Deleted",id);
        return deleteMap;
    }

    @GetMapping("/vendor/{vendorId}")
    public @ResponseBody List<CategoryVO> getCategoryByVendor(@PathVariable Long vendorId) {
        List<CategoryVO> categoryVOList = new ArrayList<>();
        CategoryVO categoryByVendor = categoryService.getCategoryByVendor(vendorId);
        categoryVOList.add(categoryByVendor);
        return categoryVOList;
    }
}
