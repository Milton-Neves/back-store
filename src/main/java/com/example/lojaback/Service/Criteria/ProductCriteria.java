package com.example.lojaback.Service.Criteria;

import com.example.lojaback.Entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@Data
@NoArgsConstructor
public class ProductCriteria {
    private String name;

    public static Specification<Product> searchLikeName(String name){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));

    }

}

