package com.example.demo.dto;

import com.example.demo.annotation.ValidateItems;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotEmpty
    private String orderId;
    @NotEmpty
    private String customerId;

    private String orderDate;
    private String shippingAddress;
    //@ValidateItems(message = "Items cannot be Empty")
    private List<ItemRequest> orderItems;
    private Double totalAmount;
    private String status;
}
