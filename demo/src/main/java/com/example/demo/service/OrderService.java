package com.example.demo.service;

import com.example.demo.dto.ItemRequest;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.ResponseVO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    /**
     * Create a new Order.
     * This method creates a new Order in the system and saves it to the database.
     *
     * @Transactional annotation is used for Data operation for Order creation to Database
     * @param orderRequest the order details to be created. The orderId, price etc.
     * @return ResponseVO of Order that is saved in db.
     */
    @Transactional
    public ResponseVO<Order> createOrder(OrderRequest orderRequest)  {
        Order newOrder = new Order();
        newOrder.setOrderId(orderRequest.getOrderId());
        newOrder.setCustomerId(orderRequest.getCustomerId());
        newOrder.setShippingAddress(orderRequest.getShippingAddress());
        LocalDate date = LocalDate.parse(orderRequest.getOrderDate());
        newOrder.setOrderDate(date);
        List<OrderItem> itemList = new ArrayList<>();
        for (ItemRequest request:orderRequest.getOrderItems()){
            OrderItem item = new OrderItem();
            item.setProductId(request.getProductId());
            item.setQuantity(request.getQuantity());
            item.setPrice(request.getPrice());
          itemList.add(item);
        }
        newOrder.setTotalAmount(orderRequest.getTotalAmount());
        newOrder.setOrderItems(itemList);
        Order order = orderRepository.save(newOrder);
        return ResponseVO.<Order>builder()
                .success(true)
                .data(order)
                .message("Product Created Successfully")
                .build();
    }

    /**
     * Fetch all the available Orders.
     * This method Fetch all the available Orders from the DB.
     *
     * @return ResponseVO of List of Orders that is fetched from db.
     */
    public ResponseVO<List<Order>> fetchAllOrders() {
     List<Order> orderList = orderRepository.findAll();
     String message = "Orders fetched Successfully";
     boolean success = true;
     if (orderList.isEmpty()) {
         success = false;
         message = "No Data available";
     }
        return ResponseVO.<List<Order>>builder()
                .success(success)
                .data(orderList)
                .message(message)
                .build();
    }

    /**
     * Fetch all the Orders based on id.
     * This method Fetch available Order from the DB for given order id.
     *
     * @return ResponseVO of Orders that is fetched from db.
     */
    public ResponseVO<Order> fetchById(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        String message = "Order fetched Successfully";
        boolean success = true;
        if (order == null) {
            success = false;
            message = "No Order found";
        }
        return ResponseVO.<Order>builder()
                .success(success)
                .data(order)
                .message(message)
                .build();
    }

    /**
     * update the order dynamically for the given fields only.
     *
     * @return ResponseVO of Orders that is fetched from db.
     */
    public ResponseVO<Order> updateOrder(String id, Map<String, Object> updates) {
        Order order = orderRepository.findById(id).orElseThrow(
                ()->new OrderNotFoundException("Order Not found for given Id"+id));
        Order finalOrder = order;
        updates.forEach((key, value) -> {
            try {
                Field field = ReflectionUtils.findField(Order.class,key); // Get the field dynamically
                field.setAccessible(true); // Allow access to private fields
                ReflectionUtils.setField(field, finalOrder,value);
                field.set(finalOrder, value); // Set the field value dynamically
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Field update error: " + key, e);
            }
        });
        order = orderRepository.save(order); // Save the partially updated object
        return ResponseVO.<Order>builder()
                .message("order updated successfully")
                .success(true)
                .data(order)
                .build();
    }

    /**
     * delete the order  for the given id .
     *
     * @return boolean that represents order is deleted or not.
     */
    public boolean deleteOrderById(String id) throws OrderNotFoundException {
        Order existingOrder = orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Order Not found for given Id"+id));
        orderRepository.delete(existingOrder);
        return true;
    }
}
