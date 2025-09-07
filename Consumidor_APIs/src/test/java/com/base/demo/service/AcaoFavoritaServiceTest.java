package com.base.demo.service;

import com.base.demo.model.AcaoFavorita;
import com.base.demo.repository.AcaoFavoritaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcaoFavoritaServiceTest {

    @Mock
    private AcaoFavoritaRepository repository;

    @InjectMocks
    private AcaoFavoritaService service;

    private AcaoFavorita acao;

    @BeforeEach
    void setUp() {
        acao = new AcaoFavorita(1L, "PETR4", "Monitorar pré-sal", "teste@email.com", 25.0, 35.0);
    }

    @Test
    void deveSalvarAcao() {
        when(repository.save(any(AcaoFavorita.class))).thenReturn(acao);

        AcaoFavorita resultado = service.salvar(acao);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTicker()).isEqualTo("PETR4");

        verify(repository, times(1)).save(acao);
    }

    @Test
    void deveListarTodasAcoes() {
        when(repository.findAll()).thenReturn(Arrays.asList(acao));

        List<AcaoFavorita> lista = service.listarTodas();

        assertThat(lista).hasSize(1);
        assertThat(lista.get(0).getTicker()).isEqualTo("PETR4");

        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(acao));

        Optional<AcaoFavorita> resultado = service.buscarPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTicker()).isEqualTo("PETR4");

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveAtualizarAcao() {
        AcaoFavorita nova = new AcaoFavorita(1L, "VALE3", "Anotação atualizada", "novo@email.com", 60.0, 80.0);

        when(repository.findById(1L)).thenReturn(Optional.of(acao));
        when(repository.save(any(AcaoFavorita.class))).thenReturn(nova);

        AcaoFavorita resultado = service.atualizar(1L, nova);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTicker()).isEqualTo("VALE3");
        assertThat(resultado.getPrecoAlvoVenda()).isEqualTo(80.0);

        verify(repository).findById(1L);
        verify(repository).save(any(AcaoFavorita.class));
    }

    @Test
    void deveDeletarAcaoComSucesso() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.deletar(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void naoDeveDeletarAcaoInexistenteELancarExcecao() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            service.deletar(99L);
        });

        verify(repository, never()).deleteById(anyLong());
    }
}
