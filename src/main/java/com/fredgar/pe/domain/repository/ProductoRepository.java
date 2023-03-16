package com.fredgar.pe.domain.repository;

import com.fredgar.pe.domain.model.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {
}
