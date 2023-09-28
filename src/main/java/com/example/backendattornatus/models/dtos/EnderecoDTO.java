package com.example.backendattornatus.models.dtos;

import com.example.backendattornatus.models.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {

    private Long id;
    private String logradouro;
    private String cep;
    private Long numero;
    private String cidade;
    private Boolean enderecoPrincipal;
}
