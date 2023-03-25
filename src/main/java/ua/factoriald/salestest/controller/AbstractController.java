package ua.factoriald.salestest.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import ua.factoriald.salestest.entity.AbstractEntity;
import ua.factoriald.salestest.service.CommonService;



public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<E>>
        implements CommonController<E> {

    @Getter
    private final S service;
    @Autowired
    protected AbstractController(S service) {
        this.service = service;
    }

}
