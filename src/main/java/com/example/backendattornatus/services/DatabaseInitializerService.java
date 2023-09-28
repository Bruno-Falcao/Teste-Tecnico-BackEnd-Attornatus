package com.example.backendattornatus.services;

import com.example.backendattornatus.models.Endereco;
import com.example.backendattornatus.models.Pessoa;
import com.example.backendattornatus.repositories.PessoaRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DatabaseInitializerService {
    private final PessoaRepository pessoaRepository;

    @PostConstruct
    public void init () {
        Endereco endereco1 = new Endereco(null,"Rua A", "7894512", 1L, "São Luís", true);
        Endereco endereco2 = new Endereco(null,"Rua B", "45796341", 2L, "Vitória", false);

        // Crie uma pessoa com os endereços
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco1);
        enderecos.add(endereco2);

        Pessoa pessoa = new Pessoa(null,"Gabriel", "01/01/1990", enderecos);

        // Salve a pessoa no repositório
        pessoaRepository.save(pessoa);
    }
}
