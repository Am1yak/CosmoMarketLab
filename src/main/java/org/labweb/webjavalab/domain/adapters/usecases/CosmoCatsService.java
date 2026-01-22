package org.labweb.webjavalab.domain.adapters.usecases;

import org.labweb.webjavalab.domain.adapters.usecases.features.FeatureCheck;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CosmoCatsService {
    private final List<String> testCats = List.of("Basik", "Sebastien", "Bibi");
    private final List<String> testProducts = List.of("Cosmoshake", "Spacedonut");

    @FeatureCheck("cosmoCats")
    public List<String> getCosmoCats() {
        return testCats;
    }

    @FeatureCheck("kittyProducts")
    public List<String> getKittyProducts() {
        return testProducts;
    }
}
