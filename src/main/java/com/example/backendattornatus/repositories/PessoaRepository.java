package com.example.backendattornatus.repositories;

import com.example.backendattornatus.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
