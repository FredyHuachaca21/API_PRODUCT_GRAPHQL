package com.fredgar.pe.domain.resolvers;

import com.fredgar.pe.domain.model.Categoria;
import com.fredgar.pe.domain.model.Marca;
import com.fredgar.pe.domain.model.Producto;
import com.fredgar.pe.domain.repository.CategoriaRepository;
import com.fredgar.pe.domain.repository.MarcaRepository;
import com.fredgar.pe.domain.repository.ProductoRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MutationResolver implements GraphQLMutationResolver {
  private final ProductoRepository productoRepository;
  private final CategoriaRepository categoriaRepository;
  private final MarcaRepository marcaRepository;

  public Producto createProducto(String nombre, String marcaId, String categoriaId) {
    Producto producto = new Producto(null, nombre, marcaId, categoriaId);
    return productoRepository.save(producto).block();
  }

  public Categoria createCategoria(String nombre) {
    Categoria categoria = new Categoria(null, nombre);
    return categoriaRepository.save(categoria).block();
  }

  public Marca createMarca(String nombre) {
    Marca marca = new Marca(null, nombre);
    return marcaRepository.save(marca).block();
  }
}
