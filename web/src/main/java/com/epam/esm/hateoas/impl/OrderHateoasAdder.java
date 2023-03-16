package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderHateoasAdder implements HateoasAdder<OrderDto> {
    private static final Class<OrderController> ORDER_CONTROLLER_CLASS = OrderController.class;

    @Override
    public void addLinks(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(ORDER_CONTROLLER_CLASS).getOrderById(orderDto.getId())).withSelfRel());
        orderDto.add(linkTo(methodOn(ORDER_CONTROLLER_CLASS).createOrder(orderDto)).withRel("new"));
    }
}