package application.rest.v1.category;

import application.rest.v1.vendor.Vendor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long child;
    private Long parent;
    @NotNull
    @ManyToOne
    private Vendor vendor;
    private String name;
    private int hasSubCategory;

    public Category(Long child) {
        this.child = child;
    }

    public Category() {
    }

    public Category(Long child, Long parent, Long vendorId, String name,int hasSubCategory) {
        this.child = child;
        this.parent = parent;
        this.vendor = new Vendor(vendorId);
        this.name = name;
        this.hasSubCategory = hasSubCategory;
    }

    public Long getChild() {
        return child;
    }

    public void setChild(Long child) {
        this.child = child;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHasSubCategory(int hasSubCategory) {
        this.hasSubCategory = hasSubCategory;
    }

    public int getHasSubCategory() {
        return hasSubCategory;
    }
}
