package org.labweb.webjavalab.repositories;

import org.labweb.webjavalab.domain.adapters.usecases.entities.Category;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class CategoryRepository {
    private HashMap<UUID, Category> categories = new HashMap<>();

    public HashMap<UUID, Category> getCategories() {
        return categories;
    }

    public Category getCategory(UUID id) {
        return categories.get(id);
    }

    public Category save(Category product) {
        categories.put(product.getId(), product);
        return product;
    }

    public void removeCategory(UUID id) {
        categories.remove(id);
    }

    public Category updateCategory(Category product) {
        categories.put(product.getId(), product);
        return product;
    }

    public boolean existsCategory(UUID id) {
        return categories.containsKey(id);
    }
}
