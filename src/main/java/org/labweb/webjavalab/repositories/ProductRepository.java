package org.labweb.webjavalab.repositories;

import org.labweb.webjavalab.domain.adapters.usecases.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class ProductRepository {
    private HashMap<UUID, Product> products = new HashMap<>();

    public HashMap<UUID, Product> getProducts() {
        return products;
    }

    public Product getProduct(UUID id) {
        return products.get(id);
    }

    public Product save(Product product) {
        products.put(product.getId(), product);
        return product;
    }

    public void removeProduct(UUID id) {
        products.remove(id);
    }

    public Product updateProduct(Product product) {
        products.put(product.getId(), product);
        return product;
    }

    public boolean existsProduct(UUID id) {
        return products.containsKey(id);
    }

    public void deleteAll(){
        products.clear();
    }
}
