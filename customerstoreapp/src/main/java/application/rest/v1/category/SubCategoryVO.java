package application.rest.v1.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SubCategoryVO {
    @JsonProperty("id")
    private Long child;
    @JsonProperty("text")
    private String name;
    private boolean expanded;
    @JsonIgnore
    private Map<Long, SubCategoryVO> categories = new TreeMap<Long, SubCategoryVO>();
    @JsonProperty("items")
    private List<SubCategoryVO> categoriesList = new ArrayList<>();
    @JsonIgnore
    private Long parent;

    public List<SubCategoryVO> getCategoriesList() {
        return categoriesList;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public void setCategoriesList(List<SubCategoryVO> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public Map<Long, SubCategoryVO> getCategories() {
        return categories;
    }

    public void setCategories(Map<Long, SubCategoryVO> categories) {
        this.categories = categories;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
