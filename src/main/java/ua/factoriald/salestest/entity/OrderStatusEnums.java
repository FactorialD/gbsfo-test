package ua.factoriald.salestest.entity;

public enum OrderStatusEnums {

    // TODO make more statuses, for ex. CANCELED, RETURNED
    CREATED(10L),
    PROCESSING(20L),
    SHIPPING(30L),
    DELIVERED(40L);

    private final Long statusId;

    OrderStatusEnums(Long statusId){
        this.statusId = statusId;
    }

    public Long getStatusId(){
        return statusId;
    }

}
