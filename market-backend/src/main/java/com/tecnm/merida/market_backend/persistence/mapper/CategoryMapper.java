package com.tecnm.merida.market_backend.persistence.mapper;

import com.tecnm.merida.market_backend.domain.Category;
import com.tecnm.merida.market_backend.persistence.entity.Categoria;
import com.tecnm.merida.market_backend.persistence.entity.Producto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "IdCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active")
    })
    Category toCategory(Categoria categoria);

    @InheritInverseConfiguration
    @Mapping(target = "productos", ignore = true)
    Category toCategory(Category category);
}
