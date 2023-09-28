package com.example.backendattornatus.services;

import com.example.backendattornatus.models.dtos.EnderecoDTO;

import java.util.List;

public interface EnderecoService {

    void criaEndereco(Long id, EnderecoDTO endereco);
    List<EnderecoDTO> buscaTodosEnderecosPessoa(Long id);
    boolean setEnderecoPrincipal(Long pessoaId, Long enderecoId);
}
