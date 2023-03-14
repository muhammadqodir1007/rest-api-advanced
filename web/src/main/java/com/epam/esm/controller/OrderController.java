/**
 * The OrderController class provides REST endpoints for handling orders.
 */
package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.Order;
import com.epam.esm.hateoas.impl.OrderHateoasAdder;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final DtoConverter<Order, OrderDto> orderDtoConverter;
    private final OrderHateoasAdder hateoasAdder;

    /**
     * Get an order by its ID
     *
     * @param id The ID of the order to retrieve
     * @return A response containing the retrieved order and its HATEOAS links
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") long id) {
        Order order = orderService.getById(id);
        OrderDto orderDto = orderDtoConverter.convertToDto(order);
        hateoasAdder.addLinks(orderDto);
        return ResponseEntity.ok(orderDto);
    }

    /**
     * Create a new order
     *
     * @param orderDto The DTO of the order to create
     * @return A response containing the created order and its HATEOAS links
     */
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        Order order = orderService.insert(orderDtoConverter.convertToEntity(orderDto));
        OrderDto addedOrderDto = orderDtoConverter.convertToDto(order);
        hateoasAdder.addLinks(addedOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedOrderDto);
    }

    /**
     * Get a list of orders by user ID
     *
     * @param userId The ID of the user to retrieve orders for
     * @param page The page number of results to retrieve (default 0)
     * @param size The number of results to retrieve per page (default 5)
     * @return A response containing a list of orders and their HATEOAS links
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(
            @PathVariable long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<Order> orders = orderService.getOrdersByUserId(userId, page, size);
        List<OrderDto> orderDtos = orders.stream()
                .map(orderDtoConverter::convertToDto)
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDtos);
    }
}
