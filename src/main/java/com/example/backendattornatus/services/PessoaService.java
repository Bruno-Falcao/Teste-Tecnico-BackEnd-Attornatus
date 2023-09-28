package com.example.backendattornatus.services;

import com.example.backendattornatus.models.Pessoa;
import com.example.backendattornatus.models.dtos.PessoaDTO;

import java.util.List;

public interface PessoaService {

    Boolean criacaoPessoa(PessoaDTO pessoaDTO);

    Boolean alteraPessoa(PessoaDTO pessoaDTO);

    Pessoa findPessoaById(Long id);

    List<PessoaDTO> buscaTodasAsPessoas();
}
