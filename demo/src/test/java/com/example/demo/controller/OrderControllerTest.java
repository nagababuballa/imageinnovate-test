package com.example.demo.controller;

import com.example.demo.dto.ItemRequest;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.ResponseVO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    private String jsonString;

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        this.jsonString = """
                {
                  "orderId": "O123",
                  "customerId": "C456",
                  "orderDate": "2023-11-01",
                  "shippingAddress": "123 Main St, Anytown, USA",
                  "orderItems": [
                    {
                      "productId": "P789",
                      "quantity": 2,
                      "price": 100.00
                    },
                    {
                      "productId": "P012",
                      "quantity": 1,
                      "price": 200.00
                    }
                  ],
                  "totalAmount": 400.00
                }
                """;
    }

    @Test
    public void testCreateProductSuccess() throws Exception {
        // Mock the service layer to return a product response with HATEOAS links

        OrderRequest order = new ObjectMapper().readValue(jsonString, OrderRequest.class);

        ResponseVO<Order> responseVO = new ResponseVO<>();
        responseVO.setSuccess(true);
        responseVO.setMessage("Order Created Successfully");
        responseVO.setData(convertOrderDTOtoOrder(order));

        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(responseVO);

        // Perform the POST request and check the response
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Order Created Successfully"))
                .andExpect(jsonPath("$.data.orderId").value("O123"))
                .andExpect(jsonPath("$.data.customerId").value("C456"))
                .andExpect(jsonPath("$.data.orderItems[0].productId").value("P789"))
                .andExpect(jsonPath("$.data.orderItems[1].price").value(200.00));
    }
@Test
    public void testFetchOrderByIdSuccess() throws Exception {
        OrderRequest request = new ObjectMapper().readValue(jsonString, OrderRequest.class);
        Order order = convertOrderDTOtoOrder(request);
        ResponseVO<Order> responseVO = new ResponseVO<>();
        responseVO.setSuccess(true);
        responseVO.setMessage("Order Fetched Successfully");
        responseVO.setData(order);
        when(orderService.fetchById(any(String.class))).thenReturn(responseVO);

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/orders/{orderId}", "O123")  // Change POST to GET
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk()) // This is okay if you want to check for 302
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Order Fetched Successfully"))
                .andExpect(jsonPath("$.data.orderId").value("O123"))
                .andExpect(jsonPath("$.data.customerId").value("C456"))
                .andExpect(jsonPath("$.data.orderItems[0].productId").value("P789"));
    }

    @Test
    public void testFetchAllOrdersSuccess() throws Exception {
        OrderRequest productResponse = new ObjectMapper().readValue(jsonString, OrderRequest.class);
        Order order = convertOrderDTOtoOrder(productResponse);
        List<Order> orderList = List.of(order);
        ResponseVO<List<Order>> responseVO = new ResponseVO<>();
        responseVO.setSuccess(true);
        responseVO.setMessage("Orders Fetched Successfully");
        responseVO.setData(orderList);
        when(orderService.fetchAllOrders()).thenReturn(responseVO);

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/orders")  // Change POST to GET
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().is2xxSuccessful()) // This is okay if you want to check for 302
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Orders Fetched Successfully"))
                .andExpect(jsonPath("$.data[0].orderId").value("O123"))
                .andExpect(jsonPath("$.data[0].customerId").value("C456"))
                .andExpect(jsonPath("$.data[0].orderItems[0].productId").value("P789"));
    }

    private Order convertOrderDTOtoOrder(OrderRequest orderRequest){
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
        return newOrder;
    }

}
