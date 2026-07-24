package dev.rynwllngtn.common.messaging;

public final class IdentityMessagingConstants {

    private IdentityMessagingConstants() {
        throw new UnsupportedOperationException();
    }

    public static final String EXCHANGE_NAME = "identity.events";

    public static final String CREATED = "identity.created";
    public static final String UPDATED_EMAIL = "identity.email.updated";

}