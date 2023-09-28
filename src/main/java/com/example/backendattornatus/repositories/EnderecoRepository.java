package com.example.backendattornatus.repositories;

import com.example.backendattornatus.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
