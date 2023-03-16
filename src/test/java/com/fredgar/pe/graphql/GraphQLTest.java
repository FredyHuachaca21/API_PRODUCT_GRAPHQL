package com.fredgar.pe.graphql;

import com.fredgar.pe.domain.model.Categoria;
import com.fredgar.pe.domain.model.Marca;
import com.fredgar.pe.domain.model.Producto;
import com.fredgar.pe.domain.repository.CategoriaRepository;
import com.fredgar.pe.domain.repository.MarcaRepository;
import com.fredgar.pe.domain.repository.ProductoRepository;
import com.fredgar.pe.domain.resolvers.MutationResolver;
import com.fredgar.pe.domain.resolvers.ProductoResolver;
import com.fredgar.pe.domain.resolvers.QueryResolver;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class GraphQLTest {
  @Mock
  private ProductoRepository productoRepository;

  @Mock
  private MarcaRepository marcaRepository;

  @Mock
  private CategoriaRepository categoriaRepository;

  @InjectMocks
  private QueryResolver queryResolver;

  @InjectMocks
  private MutationResolver mutationResolver;

  @InjectMocks
  private ProductoResolver productoResolver;

  private GraphQL graphQL;

  @BeforeEach
  void setUp() {
    String schemaPath = "graphql/schema.graphqls";
    InputStream schemaStream = getClass().getClassLoader().getResourceAsStream(schemaPath);
    if (schemaStream == null) {
      throw new IllegalStateException("No se pudo encontrar el archivo del esquema");
    }
    String schemaContent = new BufferedReader(new InputStreamReader(schemaStream, StandardCharsets.UTF_8))
        .lines()
        .collect(Collectors.joining("\n"));

    GraphQLSchema graphQLSchema = SchemaParser.newParser()
        .schemaString(schemaContent)
        .resolvers(queryResolver, mutationResolver, productoResolver)
        .build()
        .makeExecutableSchema();

    graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  @Test
  void testGetProductos() {
    // Crear datos de prueba
    Marca marca = new Marca("1", "Marca de prueba");
    Categoria categoria = new Categoria("1", "Categoria de prueba");
    Producto producto = new Producto("1", "Producto de prueba", marca.getId(), categoria.getId());
    List<Producto> productos = List.of(producto);

    // Configurar comportamiento de los repositorios
    Mockito.when(productoRepository.findAll()).thenReturn(Flux.fromIterable(productos));
    Mockito.when(marcaRepository.findById(Mockito.anyString())).thenReturn(Mono.just(marca));
    Mockito.when(categoriaRepository.findById(Mockito.anyString())).thenReturn(Mono.just(categoria));

    // Ejecutar consulta GraphQL
    String query = "{ productos { id, nombre, marca { id, nombre }, categoria { id, nombre } } }";
    ExecutionResult result = graphQL.execute(query);

    // Verificar resultado
    assertTrue(result.getErrors().isEmpty());
    Map<String, Object> data = result.getData();
    List<Map<String, Object>> productosData = (List<Map<String, Object>>) data.get("productos");
    assertEquals(1, productosData.size());

    Map<String, Object> productoData = productosData.get(0);
    assertEquals(producto.getId(), productoData.get("id"));
    assertEquals(producto.getNombre(), productoData.get("nombre"));

    Map<String, Object> marcaData = (Map<String, Object>) productoData.get("marca");
    assertEquals(marca.getId(), marcaData.get("id"));
    assertEquals(marca.getNombre(), marcaData.get("nombre"));

    Map<String, Object> categoriaData = (Map<String, Object>) productoData.get("categoria");
    assertEquals(categoria.getId(), categoriaData.get("id"));
    assertEquals(categoria.getNombre(), categoriaData.get("nombre"));
  }
}
