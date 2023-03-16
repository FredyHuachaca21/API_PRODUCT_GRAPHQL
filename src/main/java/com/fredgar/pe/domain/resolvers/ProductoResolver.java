package com.fredgar.pe.domain.resolvers;

import com.fredgar.pe.domain.model.Categoria;
import com.fredgar.pe.domain.model.Marca;
import com.fredgar.pe.domain.model.Producto;
import com.fredgar.pe.domain.repository.CategoriaRepository;
import com.fredgar.pe.domain.repository.MarcaRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoResolver implements GraphQLResolver<Producto> {

  private final MarcaRepository marcaRepository;
  private final CategoriaRepository categoriaRepository;

  public Marca getMarca(Producto producto) {
    return marcaRepository.findById(producto.getMarcaId()).block();
  }

  public Categoria getCategoria(Producto producto) {
    return categoriaRepository.findById(producto.getCategoriaId()).block();
  }
}
