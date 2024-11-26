package com.example.demo.controller;


import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.ResponseVO;
import com.example.demo.entity.Order;
import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.service.OrderService;
import com.example.demo.swagger.dto.SwaggerOrderResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * Get api used to create new order.
     * This method accepts the post method used to create new order
     * and creates it with the help of Business logic defined in service layer.
     *
     * @return ResponseEntity of ResponseVO of Orders that contains
     * valuable information like success,message,success call back data.
     */
    @Operation(summary="Details about creating the new Order")
    @ApiResponses({
            @ApiResponse(description = "Order created successfully",responseCode ="201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SwaggerOrderResponseVO.class))),
            @ApiResponse(description = "Error occurred while creating a order",responseCode ="500",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ResponseVO<Order>> createOrder(@Valid @RequestBody OrderRequest orderRequest) throws ParseException {
     return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    /**
     * Get api used to create new order.
     * This method accepts the post method used to create new order
     * and creates it with the help of Business logic defined in service layer.
     *
     * @return ResponseEntity of ResponseVO of List of Orders that contains
     * valuable information like success,message,success call back data.
     */
    @Operation(summary="Details about all the orders that are fetched from DB")
    @ApiResponses({
            @ApiResponse(description = "Orders fetched successfully",responseCode ="200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SwaggerOrderResponseVO.class))),
            @ApiResponse(description = "Error occurred while fetching the orders",responseCode ="500",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<ResponseVO<List<Order>>> fetchAllOrders() {
        return new ResponseEntity<>(orderService.fetchAllOrders(), HttpStatus.OK);
    }

    /**
     * Get api used to create new order.
     * This method accepts the post method used to create new order
     * and creates it with the help of Business logic defined in service layer.
     *
     * @return ResponseEntity of ResponseVO of Orders that contains
     * valuable information like success,message,success call back data.
     */
    @Operation(summary="Details about the order that are fetched from DB based on id")
    @ApiResponses({
            @ApiResponse(description = "Orders fetched successfully",responseCode ="200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SwaggerOrderResponseVO.class))),
            @ApiResponse(description = "Error occurred while fetching the order",responseCode ="500",
                    content = @Content)
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseVO<Order>> fetchOrderById(@PathVariable("orderId") String orderId) {
        return new ResponseEntity<>(orderService.fetchById(orderId), HttpStatus.OK);
    }

    /**
     * delete api used to delete the order based on id.
     *
     * @return ResponseEntity of ResponseVO of Orders that contains
     * valuable information like success,message,success call back data.
     */
    @Operation(summary = "Delete order for given id")
    @ApiResponses({
            @ApiResponse(description = "Order deleted successfully for given id", responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SwaggerOrderResponseVO.class))),
            @ApiResponse(description = "Error occurred while deleting the Order", responseCode = "500",
                    content = @Content)
    })
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ResponseVO<Order>> deleteProductById(@PathVariable("orderId") String id) throws OrderNotFoundException {
        boolean isDeleted = orderService.deleteOrderById(id);

        if (!isDeleted) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }

        return ResponseEntity
                .noContent()
                .build();
    }

    /**
     * put api used to update the order based on given fields.
     *
     * @return ResponseEntity of ResponseVO of Orders that contains
     * valuable information like success,message,success call back data.
     */
    @Operation(summary = "Update order for given id with provided fields")
    @ApiResponses({
            @ApiResponse(description = "Order updated successfully for given id", responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SwaggerOrderResponseVO.class))),
            @ApiResponse(description = "Error occurred while updating the Order", responseCode = "500",
                    content = @Content)
    })
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ResponseVO<Order>> updateOrder(@PathVariable("orderId") String id, @RequestBody Map<String, Object> updates) throws OrderNotFoundException {
        return new ResponseEntity<>(orderService.updateOrder(id,updates),HttpStatus.OK);
    }
}
