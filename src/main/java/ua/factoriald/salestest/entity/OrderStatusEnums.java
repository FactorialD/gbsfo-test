package ua.factoriald.salestest.entity;

public enum OrderStatusEnums {

    CREATED(10L),
    PROCESSING(20L),
    SHIPPING(30L),
    DELIVERED(40L);

    private Long statusId;

    OrderStatusEnums(Long statusId){
        this.statusId = statusId;
    }

    public Long getStatusId(){
        return statusId;
    }

}
