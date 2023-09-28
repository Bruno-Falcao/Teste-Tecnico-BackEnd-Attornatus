package com.example.backendattornatus.services.impl;

import com.example.backendattornatus.models.Pessoa;
import com.example.backendattornatus.models.dtos.PessoaDTO;
import com.example.backendattornatus.repositories.PessoaRepository;
import com.example.backendattornatus.services.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PessoaServiceImpl implements PessoaService {
    private final PessoaRepository pessoaRepository;

    @Override
    public Boolean criacaoPessoa(PessoaDTO pessoaDTO) {
        if (pessoaDTO != null) {
            Pessoa novaPessoa = new Pessoa(pessoaDTO.getId(),
                    pessoaDTO.getNome(),
                    pessoaDTO.getDataDeNascimento(),
                    pessoaDTO.getEndereco());
            pessoaRepository.save(novaPessoa);
            return true;
        }

        throw new RuntimeException("Não foi possível criar nova pessoa");
    }

    @Override
    public Boolean alteraPessoa(PessoaDTO pessoaDTO) {
        if (pessoaDTO == null) {
            throw new RuntimeException("O campo pessoa é nulo");
        }

        if (pessoaDTO.getId() != null) {
            Pessoa pessoa = findPessoaById(pessoaDTO.getId());
            pessoa.setNome(pessoaDTO.getNome());
            pessoa.setDataDeNascimento(pessoaDTO.getDataDeNascimento());
            pessoaRepository.save(pessoa);
            return true;
        }
        throw new RuntimeException("Não foi possível alterar pessoa");
    }

    @Override
    public Pessoa findPessoaById(Long id) {
        return pessoaRepository.findById(id).orElseThrow();
    }

    @Override
    public List<PessoaDTO> buscaTodasAsPessoas() {
        return pessoaRepository.findAll().stream().map(pessoa ->
                        new PessoaDTO(
                                pessoa.getId(),
                                pessoa.getNome(),
                                pessoa.getDataDeNascimento(),
                                pessoa.getEndereco()
                        ))
                .collect(Collectors.toList());
    }
}
