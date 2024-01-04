package com.example.lojaback.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Data
@Table(name = "produto")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "preco")
    private Double price;

    @Column(name = "quantidade")
    private Long aLong;

    @Column(name = "imagem")
    private String image;


}
