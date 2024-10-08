package com.fiap.hackathon.common.logging;

public class LoggingPattern {
    public static final String IDENTITY_PROVIDER_USER_CREATED = "Identity Provider user succesfully created for username {}";
    public static final String IDENTITY_PROVIDER_USER_CONFIRMED = "Identity Provider user succesfully confirmed for username {}";
    public static final String IDENTITY_PROVIDER_USER_DELETED = "Identity Provider user succesfully deleted for username {}";

    public static final String CREATE_ENTITY_SUCCESS = "{} was successfully created. The entity id is {}.";
    public static final String CREATE_ENTITY_ERROR = "Error creating entity on database: {}";

    public static final String GET_ENTITY_SUCCESS = "Entity with attribute '{}' found on table '{}'";
    public static final String GET_ENTITY_ERROR = "Error looking for entity with attribute {} on database: {}";
}
