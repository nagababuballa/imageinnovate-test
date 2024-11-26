package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

 @Id
 @Column(nullable = false, unique = true)
 private String orderId;

 private String customerId;
 private LocalDate orderDate;
 private String shippingAddress;

 @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
 private List<OrderItem> orderItems;

 private Double totalAmount;

 private String status;

}