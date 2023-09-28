package com.example.backendattornatus.models;

import com.example.backendattornatus.models.dtos.EnderecoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tb_endereco")
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;

    private String cep;

    private Long numero;

    private String cidade;

    @JsonProperty("endereco_principal")
    private Boolean enderecoPrincipal;
}
