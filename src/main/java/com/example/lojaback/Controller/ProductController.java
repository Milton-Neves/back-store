package com.example.lojaback.Controller;

import com.example.lojaback.Entity.Product;
import com.example.lojaback.Model.ApiResponse;
import com.example.lojaback.Model.PaginatedData;
import com.example.lojaback.Service.Criteria.ProductCriteria;
import com.example.lojaback.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/salvar")
    public ResponseEntity<ApiResponse<Product>> saveProduct(@RequestBody Product product) {
        ApiResponse<Product> response = productService.saveProduct(product);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedData<Product>>> findAll(Pageable pageable, ProductCriteria criteria) {
        ApiResponse<PaginatedData<Product>> response = productService.findAll(criteria, pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<Product>> getById(@PathVariable Long id) {
        ApiResponse<Product> response = productService.getById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping(value = "/editar/{id}")
    public ResponseEntity<ApiResponse<Product>> update(@PathVariable Long id, @RequestBody Product product) {
        ApiResponse<Product> response = productService.update(id, product);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<ApiResponse<Product>> delete(@PathVariable Long id) {
        ApiResponse<Product> response = productService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
