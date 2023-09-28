package com.example.backendattornatus;

import com.example.backendattornatus.models.Pessoa;
import com.example.backendattornatus.models.dtos.PessoaDTO;
import com.example.backendattornatus.repositories.PessoaRepository;
import com.example.backendattornatus.services.impl.PessoaServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

public class PessoaServiceTest {

    @InjectMocks
    private PessoaServiceImpl pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Test
    public void testCriacaoPessoa_Successo() {
        PessoaDTO pessoaDTO = new PessoaDTO(null, "João", "01/01/1990", null);

        boolean result = pessoaService.criacaoPessoa(pessoaDTO);

        assertTrue(result);
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    public void testCriacaoPessoa_NullInput() {
        PessoaDTO pessoaDTO = null;

        assertThrows(RuntimeException.class, () -> pessoaService.criacaoPessoa(pessoaDTO));
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

    @Test
    public void testAlteraPessoa_Successo() {
        Long pessoaId = 1L;
        PessoaDTO pessoaDTO = new PessoaDTO(pessoaId, "Maria", "02/02/1980", null);

        Pessoa mockPessoa = new Pessoa();
        mockPessoa.setId(pessoaId);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(mockPessoa));

        boolean result = pessoaService.alteraPessoa(pessoaDTO);

        assertTrue(result);
        assertEquals("Maria", mockPessoa.getNome());
        assertEquals("02/02/1980", mockPessoa.getDataDeNascimento());
        verify(pessoaRepository, times(1)).save(mockPessoa);
    }

    @Test
    public void testAlteraPessoa_NullInput() {
        PessoaDTO pessoaDTO = null;

        assertThrows(RuntimeException.class, () -> {
            pessoaService.alteraPessoa(pessoaDTO);
        });
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

    @Test
    public void testAlteraPessoa_PessoaNotFound() {
        Long pessoaId = 1L;
        PessoaDTO pessoaDTO = new PessoaDTO(pessoaId, "Maria", "02/02/1980", null);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            pessoaService.alteraPessoa(pessoaDTO);
        });
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

    @Test
    public void testFindPessoaById_Success() {
        Long pessoaId = 1L;
        Pessoa mockPessoa = new Pessoa();
        mockPessoa.setId(pessoaId);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(mockPessoa));

        Pessoa result = pessoaService.findPessoaById(pessoaId);

        assertNotNull(result);
        assertEquals(pessoaId, result.getId());
    }

    @Test
    public void testFindPessoaById_PessoaNotFound() {
        Long pessoaId = 1L;

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            pessoaService.findPessoaById(pessoaId);
        });
    }

    @Test
    public void testBuscaTodasAsPessoas() {
        List<Pessoa> mockPessoas = new ArrayList<>();
        mockPessoas.add(new Pessoa(1L, "João", "01/01/1990", null));
        mockPessoas.add(new Pessoa(2L, "Maria", "02/02/1980", null));

        when(pessoaRepository.findAll()).thenReturn(mockPessoas);

        List<PessoaDTO> result = pessoaService.buscaTodasAsPessoas();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("João", result.get(0).getNome());
        assertEquals("Maria", result.get(1).getNome());
    }
}
