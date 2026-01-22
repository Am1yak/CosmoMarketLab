package org.labweb.webjavalab.domain.adapters.aspects;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.labweb.webjavalab.domain.adapters.usecases.FeatureToggleService;
import org.labweb.webjavalab.domain.adapters.usecases.features.FeatureCheck;
import org.labweb.webjavalab.exceptions.FeatureNotAvailableException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {
    private final FeatureToggleService featureToggleService;

    @Around("@annotation(featureCheck)")
    public Object checkFeatureToggle(ProceedingJoinPoint joinPoint, FeatureCheck featureCheck) throws Throwable {
        String featureName = featureCheck.value();

        if(featureToggleService.isFeatureEnabled(featureName)) {
            return joinPoint.proceed();
        } else {
            throw new FeatureNotAvailableException("Feature " + featureName + " is not available");
        }
    }
}
