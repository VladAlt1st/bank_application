package com.telran.bankapplication.mapper;

import com.telran.bankapplication.dto.ProductDto;
import com.telran.bankapplication.entity.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {

    @Mapping(target = "createdAt", source = "createdAt",  dateFormat="dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "updatedAt", source = "updatedAt",  dateFormat="dd-MM-yyyy HH:mm:ss")
    ProductDto toDto(Product product);
}
