package org.labweb.webjavalab.misc;

import org.aspectj.lang.annotation.Around;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.junit.jupiter.api.Test;
import org.labweb.webjavalab.domain.adapters.usecases.CosmoCatsService;
import org.labweb.webjavalab.exceptions.FeatureNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class FeatureToggleTest {
    @Autowired
    private CosmoCatsService cosmoCatsService;

    @Test
    public void testGetCosmoCats() {
        List<String> cats = cosmoCatsService.getCosmoCats();

        assertTrue(cats.size() == 3);
        assertTrue(cats.contains("Basik"));
    }

    @Test
    public void testGetKittyProducts_FeatureFalse() {
        assertThrows(FeatureNotAvailableException.class, () -> cosmoCatsService.getKittyProducts());
    }
}
