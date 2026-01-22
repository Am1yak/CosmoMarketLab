package org.labweb.webjavalab.domain.adapters.usecases;

import lombok.RequiredArgsConstructor;
import org.labweb.webjavalab.config.FeatureProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeatureToggleService {
    private final FeatureProperties featureProperties;

    public boolean isFeatureEnabled(String featureName) {
        return featureProperties.getToggles().getOrDefault(featureName, false);
    }
}
