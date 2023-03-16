package com.fredgar.pe.domain.resolvers;

import com.fredgar.pe.domain.model.Categoria;
import com.fredgar.pe.domain.model.Marca;
import com.fredgar.pe.domain.model.Producto;
import com.fredgar.pe.domain.repository.CategoriaRepository;
import com.fredgar.pe.domain.repository.MarcaRepository;
import com.fredgar.pe.domain.repository.ProductoRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
  private final ProductoRepository productoRepository;
  private final CategoriaRepository categoriaRepository;
  private final MarcaRepository marcaRepository;

  public Iterable<Producto> getProductos() {
    return productoRepository.findAll().toIterable();
  }

  public Iterable<Categoria> getCategorias() {
    return categoriaRepository.findAll().toIterable();
  }

  public Iterable<Marca> getMarcas() {
    return marcaRepository.findAll().toIterable();
  }
}
