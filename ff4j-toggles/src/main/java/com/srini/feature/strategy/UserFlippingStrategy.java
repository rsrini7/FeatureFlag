package com.srini.feature.strategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ff4j.core.FeatureStore;
import org.ff4j.core.FlippingExecutionContext;
import org.ff4j.strategy.AbstractFlipStrategy;

public class UserFlippingStrategy extends AbstractFlipStrategy {

    /**
     * initial parameter.
     */

    public static final String INIT_PARAMNAME_ALLOWED_USERS = "allowedUsers";

    /**
     * current user attribute
     */
    public static final String PARAMNAME_USER = "user";

    /**
     * Initial Granted Users.
     */
    private Set<String> setOfAllowedUsers;

    public UserFlippingStrategy() {
        this.setOfAllowedUsers = new HashSet<String>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(String featureName, Map<String, String> initValue) {
        super.init(featureName, initValue);
        assertRequiredParameter(INIT_PARAMNAME_ALLOWED_USERS);
        String[] arrayOfUsers = initValue.get(INIT_PARAMNAME_ALLOWED_USERS).split(",");
        setOfAllowedUsers.addAll(Arrays.asList(arrayOfUsers));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evaluate(String fName, FeatureStore fStore, FlippingExecutionContext ctx) {
        if (ctx == null)
            return false;
        // true means required here
        String user = ctx.getString(PARAMNAME_USER, true);
        return setOfAllowedUsers.contains(user);
    }
}
