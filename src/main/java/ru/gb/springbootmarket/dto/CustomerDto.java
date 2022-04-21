package ru.gb.springbootmarket.dto;

import lombok.Data;
import ru.gb.springbootmarket.model.Order;

import java.util.List;

@Data
public class CustomerDto {
    private Long id;
    private String email;
    private String address;
    private List<Order> orderList;
}
