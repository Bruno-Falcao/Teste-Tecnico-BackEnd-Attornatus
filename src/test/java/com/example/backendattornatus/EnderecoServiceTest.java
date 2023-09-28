package com.example.backendattornatus;

import com.example.backendattornatus.models.Endereco;
import com.example.backendattornatus.models.Pessoa;
import com.example.backendattornatus.models.dtos.EnderecoDTO;
import com.example.backendattornatus.repositories.EnderecoRepository;
import com.example.backendattornatus.repositories.PessoaRepository;
import com.example.backendattornatus.services.impl.EnderecoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoServiceImpl enderecoService;

    @Mock
    private PessoaRepository pessoaRepository;
    @Mock
    private EnderecoRepository enderecoRepository;

    @Test
    public void testCriaEndereco() {
        Long pessoaId = 1L;
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                null, "Rua A", "12345", 1L, "Cidade A", true
        );
        Endereco endereco = new Endereco(enderecoDTO.getId(), enderecoDTO.getLogradouro(), enderecoDTO.getCep(),
                enderecoDTO.getNumero(), enderecoDTO.getCidade(), enderecoDTO.getEnderecoPrincipal());

        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        Pessoa mockPessoa = new Pessoa(null, "Gabriel", "01/01/1990", enderecos);


        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(mockPessoa));
        when(pessoaRepository.save(mockPessoa)).thenReturn(mockPessoa);
        mockPessoa.setId(1L);

        enderecoService.criaEndereco(pessoaId, enderecoDTO);

        verify(pessoaRepository, times(1)).save(mockPessoa);
    }

    @Test
    public void testBuscaTodosEnderecosPessoa() {
        Long pessoaId = 1L;
        Endereco endereco1 = new Endereco(1L, "Rua A", "12345", 1L, "Cidade A", true);
        Endereco endereco2 = new Endereco(2L, "Rua B", "54321", 2L, "Cidade B", false);
        Pessoa mockPessoa = new Pessoa();
        mockPessoa.setId(pessoaId);
        mockPessoa.setEndereco(Arrays.asList(endereco1, endereco2));

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(mockPessoa));

        List<EnderecoDTO> result = enderecoService.buscaTodosEnderecosPessoa(pessoaId);

        assertEquals(2, result.size());
        assertEquals("Rua A", result.get(0).getLogradouro());
        assertEquals("Rua B", result.get(1).getLogradouro());
    }

    @Test
    public void testSetEnderecoPrincipal_Success() {
        Long pessoaId = 1L;
        Long enderecoId = 2L;

        Pessoa mockPessoa = new Pessoa();
        mockPessoa.setId(pessoaId);

        Endereco mockEndereco = new Endereco();
        mockEndereco.setId(enderecoId);

        mockPessoa.setEndereco(List.of(mockEndereco));

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(mockPessoa));
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(mockEndereco));

        boolean result = enderecoService.setEnderecoPrincipal(pessoaId, enderecoId);

        assertTrue(result);
        assertTrue((boolean) mockEndereco.getEnderecoPrincipal());
        assertFalse(mockPessoa.getEndereco().get(0).equals(false));
        verify(pessoaRepository, times(1)).save(mockPessoa);
    }

    @Test
    public void testSetEnderecoPrincipalPessoaNotFound() {
        Long pessoaId = 1L;
        Long enderecoId = 2L;

        when(pessoaRepository.findById(pessoaId)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> enderecoService.setEnderecoPrincipal(pessoaId,enderecoId));
    }

    @Test
    public void testSetEnderecoPrincipalEnderecoNotFound() {
        Long pessoaId = 1L;
        Long enderecoId = 2L;

        Pessoa mockPessoa = new Pessoa();
        mockPessoa.setId(pessoaId);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(mockPessoa));
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.empty());

        boolean result = enderecoService.setEnderecoPrincipal(pessoaId, enderecoId);

        assertFalse(result);
        verify(pessoaRepository, times(1)).findById(pessoaId);
    }

    @Test
    public void testSetEnderecoPrincipalAddressNaoPertenceAPessoa() {
        Long pessoaId = 1L;
        Long enderecoId = 2L;

        Pessoa mockPessoa = new Pessoa();
        mockPessoa.setId(pessoaId);

        Endereco mockEndereco = new Endereco();
        mockEndereco.setId(3L); // Different from enderecoId

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(mockPessoa));
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(mockEndereco));

        assertThrows(NullPointerException.class, () -> enderecoService.setEnderecoPrincipal(pessoaId, enderecoId));
    }
}
