package ru.evseev.phrases.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by anev on 16/05/16.
 */
public class SimpleHealthCheck extends HealthCheck {

    @Override
    protected HealthCheck.Result check() throws Exception {
        return HealthCheck.Result.healthy();
    }
}
