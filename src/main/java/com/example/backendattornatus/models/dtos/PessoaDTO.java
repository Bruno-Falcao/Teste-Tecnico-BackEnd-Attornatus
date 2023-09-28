package com.example.backendattornatus.models.dtos;

import com.example.backendattornatus.models.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {
    private Long id;

    @NotBlank(message = "Nome é um campo obrigatório")
    private String nome;

    @JsonProperty("data_de_nascimento")
    private String dataDeNascimento;

    private List<Endereco> endereco;
}
