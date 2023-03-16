package com.fredgar.pe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Producto {
  @Id
  private String id;
  private String nombre;
  private String marcaId;
  private String categoriaId;
}