
package com.tecnm.merida.market_backend.persistence.mapper;

import com.tecnm.merida.market_backend.domain.Category;
import com.tecnm.merida.market_backend.persistence.entity.Categoria;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "description", target = "category"),
            @Mapping(source = "estado", target = "active")
    })
    Category toCategory(Categoria categoria);

    @InheritInverseConfiguration
    Categoria toCategoria(Category category);

}
