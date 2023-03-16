package com.fredgar.pe.domain.repository;

import com.fredgar.pe.domain.model.Categoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoriaRepository extends ReactiveMongoRepository<Categoria, String> {
}
