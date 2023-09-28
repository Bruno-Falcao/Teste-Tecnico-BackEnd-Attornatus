package com.example.backendattornatus.controllers;

import com.example.backendattornatus.models.dtos.EnderecoDTO;
import com.example.backendattornatus.models.HttpResponse;
import com.example.backendattornatus.services.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("endereco")
@AllArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<?> criaEndereco(@RequestParam(name = "id") Long id,
                                          @RequestBody EnderecoDTO enderecoDTO) {
        try {
            enderecoService.criaEndereco(id, enderecoDTO);
            return ResponseEntity.status(201).body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .statusCode(HttpStatus.CREATED.value())
                            .status(HttpStatus.CREATED)
                            .message("Endereço Criado")
                            .build()
            );
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .errorMessage(ex.getMessage())
                            .message("Não foi possível criar novo endereço")
                            .build()
            );
        }
    }

    @GetMapping
    public ResponseEntity<?> buscaTodosEnderecosPessoa(@RequestParam(name = "id") Long id) {
        try {
            List<EnderecoDTO> enderecoDTOS = enderecoService.buscaTodosEnderecosPessoa(id);

            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .data(Map.of("Endereços", enderecoDTOS))
                            .statusCode(HttpStatus.OK.value())
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
                            .message("Não foi possível retornar lista de endereços")
                            .build()
            );
        }
    }

    @PutMapping("/{pessoa_id}{endereco_id}/principal")
    public ResponseEntity<?> setEnderecoPrincipal(
            @PathVariable(name = "pessoa_id") Long pessoaId,
            @PathVariable(name = "endereco_id") Long enderecoId
    ) {
        try {
            enderecoService.setEnderecoPrincipal(pessoaId,enderecoId);
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().withNano(0).toString())
                            .message("Endereço principal modificado" )
                            .statusCode(HttpStatus.OK.value())
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
                            .message("Não foi possível definir o endereço principal")
                            .build()
            );
        }
    }
}
