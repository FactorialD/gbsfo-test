package ua.factoriald.salestest.util;

public enum RepositoryError {
    ENTITY_NOT_FOUND("Record not found, id: %s");

    private String description;

    RepositoryError(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
