package com.example.backendattornatus.controllers;

import com.example.backendattornatus.models.HttpResponse;
import com.example.backendattornatus.models.dtos.PessoaDTO;
import com.example.backendattornatus.services.PessoaService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("pessoa")
@AllArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;


    @PostMapping
    public ResponseEntity<?> criaPessoa(@RequestBody PessoaDTO pessoaDTO) {
        try {
            pessoaService.criacaoPessoa(pessoaDTO);
            return ResponseEntity.status(201).body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .message("Pessoa Criada")
                            .build()
            );
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .errorMessage(ex.getMessage())
                            .message("Não foi possível criar nova Pessoa")
                            .build()
            );
        }
    }

    @PutMapping
    public ResponseEntity<?> editarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        try {
            pessoaService.alteraPessoa(pessoaDTO);
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .message("Pessoa alterada")
                            .build()
            );
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .errorMessage(ex.getMessage())
                            .message("Não foi possível alterar Pessoa")
                            .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultaPessoa(@PathVariable(name = "id") Long id) {
        try {
            val pessoaById = pessoaService.findPessoaById(id);
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .statusCode(HttpStatus.OK.value())
                            .data(Map.of(pessoaById.getNome(), pessoaById))
                            .status(HttpStatus.OK)
                            .build()
            );
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .errorMessage(ex.getMessage())
                            .message("Não foi possível encontrar Pessoa com o id: " + id)
                            .build()
            );
        }
    }

    @GetMapping
    public ResponseEntity<?> listaPessoas() {
        try {
            val pessoas = pessoaService.buscaTodasAsPessoas();
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .statusCode(HttpStatus.OK.value())
                            .data(Map.of("Pessoas", pessoas))
                            .status(HttpStatus.OK)
                            .build()
            );
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .errorMessage(ex.getMessage())
                            .message("Não foi possível listar as pessoas")
                            .build()
            );
        }
    }
}
