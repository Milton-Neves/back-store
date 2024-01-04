package com.example.lojaback.Service;

import com.example.lojaback.Entity.Product;
import com.example.lojaback.Model.ApiResponse;
import com.example.lojaback.Model.PaginatedData;
import com.example.lojaback.Model.Pagination;
import com.example.lojaback.Repository.ProductRepository;
import com.example.lojaback.Service.Criteria.ProductCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ApiResponse<Product> saveProduct(Product product){
        ApiResponse<Product> apiResponse = new ApiResponse<>();

        productRepository.findByName(product.getName()).ifPresentOrElse(
                it -> {
                    apiResponse.of(HttpStatus.BAD_REQUEST,"Produto já disponivel");
                },
                () -> {
                    apiResponse.of("Produto salvo com sucesso!", productRepository.save(product));
                }
        );

        return apiResponse;
    }

    public ApiResponse<Product> getById(Long id){
        ApiResponse<Product> response = new ApiResponse<>();

        productRepository.findById(id).ifPresentOrElse(
                it -> response.of(HttpStatus.OK,"Produto encontrado", it),
                () -> response.of(HttpStatus.NOT_FOUND, "Nao encotrei o produto")
        );

        return response;
    }

    public ApiResponse<PaginatedData<Product>> findAll (ProductCriteria criteria, Pageable pageable) {
        ApiResponse<PaginatedData<Product>> response = new ApiResponse<>();

        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC,
                        "name")
        );

        Page<Product> products = productRepository.findAll(createSpecification(criteria),pageable);
        final Pagination pagination = Pagination.from(products,
                pageable);
        return response.of(HttpStatus.OK,
                "Produtos encontrados.",
                new PaginatedData<>(products.getContent(),
                        pagination));
    }


    public ApiResponse<Product> delete(Long id){
        ApiResponse<Product> apiResponse = new ApiResponse<>();
        productRepository.findById(id).ifPresentOrElse(
                it -> {
                    apiResponse.of(HttpStatus.OK,"Produto deletado com sucesso!", it);
                    productRepository.deleteById(it.getId());
                },
                () -> {
                    apiResponse.of(HttpStatus.NOT_FOUND, "Nenhum produto encontrado");
                }
        );

        return apiResponse;
    }

    public ApiResponse<Product> update(Long id, Product product){
        ApiResponse<Product> apiResponse = new ApiResponse<>();

        productRepository.findById(id).ifPresentOrElse(
                it -> {
                    product.setId(it.getId());
                    apiResponse.of(HttpStatus.OK,"Alterado com sucesso",productRepository.save(product));
                },
                () -> {
                    apiResponse.of(HttpStatus.NOT_FOUND, "Nenhum produto encontrado.");
                }
        );
        return apiResponse;
    }


    private Specification<Product> createSpecification(ProductCriteria criteria){
        Specification<Product> specs = Specification.where(null);
        if (criteria.getName() != null) {
            specs = specs.and(ProductCriteria.searchLikeName(criteria.getName()));
        }

        return specs;
    }

}
