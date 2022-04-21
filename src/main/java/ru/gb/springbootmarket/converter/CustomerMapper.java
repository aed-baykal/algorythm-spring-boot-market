package ru.gb.springbootmarket.converter;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.gb.springbootmarket.dto.CustomerDto;
import ru.gb.springbootmarket.model.Customer;

@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring"
)
public abstract class CustomerMapper {
    public abstract Customer mapModel(CustomerDto customerDto);
    public abstract CustomerDto mapDto(Customer customer);
}
