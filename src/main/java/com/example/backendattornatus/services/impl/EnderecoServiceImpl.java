package com.example.backendattornatus.services.impl;

import com.example.backendattornatus.models.Endereco;
import com.example.backendattornatus.models.Pessoa;
import com.example.backendattornatus.models.dtos.EnderecoDTO;
import com.example.backendattornatus.repositories.EnderecoRepository;
import com.example.backendattornatus.repositories.PessoaRepository;
import com.example.backendattornatus.services.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaRepository pessoaRepository;

    /**
     * Cria o endereço para uma pessoa específica
     *
     * @param id
     * @param enderecoDTO
     */
    @Override
    public void criaEndereco(Long id, EnderecoDTO enderecoDTO) {
        if (id == null || enderecoDTO == null) {
            throw new RuntimeException("Não foi possível criar o endereço");
        }
        Pessoa pessoa = getPessoaById(id);
        Endereco endereco = new Endereco(enderecoDTO.getId(),
                enderecoDTO.getLogradouro(),
                enderecoDTO.getCep(),
                enderecoDTO.getNumero(),
                enderecoDTO.getCidade(),
                enderecoDTO.getEnderecoPrincipal());
        if (endereco.getEnderecoPrincipal()) {
            setEnderecosToFalse(pessoa);
        }

            pessoa.getEndereco().add(endereco);
            pessoaRepository.save(pessoa);
    }

    private Pessoa getPessoaById(Long id) {
        return pessoaRepository.findById(id).orElseThrow();
    }

    @Override
    public List<EnderecoDTO> buscaTodosEnderecosPessoa(Long id) {
        return getPessoaById(id).getEndereco().stream().map(endereco ->
                        new EnderecoDTO(
                                endereco.getId(),
                                endereco.getLogradouro(),
                                endereco.getCep(),
                                endereco.getNumero(),
                                endereco.getCidade(),
                                endereco.getEnderecoPrincipal()
                        ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean setEnderecoPrincipal(Long pessoaId, Long enderecoId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElse(null);
        Endereco endereco = enderecoRepository.findById(enderecoId).orElse(null);

        if (pessoa != null && endereco != null) {
            if (pessoa.getEndereco().contains(endereco)) {
                setEnderecosToFalse(pessoa);
                endereco.setEnderecoPrincipal(true);
                pessoaRepository.save(pessoa);
                return true;
            }
        }
        return false;
    }

    private static void setEnderecosToFalse(Pessoa pessoa) {
        pessoa.getEndereco().forEach(e -> e.setEnderecoPrincipal(false));
    }
}
