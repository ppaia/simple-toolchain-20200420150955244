package application.rest.v1.product;

import application.rest.v1.vendor.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public @ResponseBody Product addProduct(@RequestBody Product product) throws Exception {
        return productService.addOrUpdateProduct(product);
    }

    @PutMapping("/{id}")
    public @ResponseBody Product updateProduct(@PathVariable Long id, @RequestBody Product product) throws Exception {
        product.setId(id);
        return productService.addOrUpdateProduct(product);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody Map<String,Long> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        Map<String,Long> deleteMap = new HashMap<>();
        deleteMap.put("Deleted",id);
        return deleteMap;
    }

    @GetMapping("/{id}")
    public @ResponseBody List<Product> getProductsByCategory(@PathVariable Long id) {
        return productService.getProductsByCategory(id);
    }

    @GetMapping
    public @ResponseBody List<ProductVO> getProdByNameAndZipCode(@PathParam("name") String name,
                                                               @PathParam("zipcode") Long zipcode) {
        List<ProductVO> productResponse = new ArrayList<>();
        List<Product> products = productService.getProdByNameAndZipCode(name, new Long(zipcode));
        for(Product product : products) {
            if(1 == product.getStock()) {
                Vendor vendor = product.getCategory().getVendor();
                ProductVO prodVO = new ProductVO(
                    product.getName(),
                    product.getStock(),
                    vendor.getId(),
                    vendor.getShopname(),
                    vendor.getAddress1(),
                    vendor.getAddress2(),
                    vendor.getCity(),
                    vendor.getState(),
                    vendor.getPincode(),
                    vendor.getPhone()
                );
                productResponse.add(prodVO);
            }
        }
        return productResponse;
    }
}
