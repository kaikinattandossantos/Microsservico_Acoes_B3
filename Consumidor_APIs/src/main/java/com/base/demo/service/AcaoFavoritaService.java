package com.base.demo.service;


import com.base.demo.dto.AcaoFavoritaDetalhadaDTO; 
import com.base.demo.model.AcaoFavorita;
import com.base.demo.repository.AcaoFavoritaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service 
public class AcaoFavoritaService {

    @Autowired
    private AcaoFavoritaRepository repository;

    @Autowired
    private BrapiService brapiService;

    @CacheEvict(value = "stocks", key = "#acao.getTicker()")
    public AcaoFavorita salvar(AcaoFavorita acao) {

        return repository.save(acao);
    }

    public List<AcaoFavorita> listarTodas() {
        return repository.findAll();
    }

    public Optional<AcaoFavorita> buscarPorId(Long id) {
        return repository.findById(id);
    }

// DENTRO DE AcaoFavoritaService.java

    public AcaoFavorita atualizar(Long id, AcaoFavorita acaoAtualizada) {
        return repository.findById(id)
            .map(acaoExistente -> {
                // Atualiza os campos que já existiam
                acaoExistente.setTicker(acaoAtualizada.getTicker());
                acaoExistente.setAnotacaoPessoal(acaoAtualizada.getAnotacaoPessoal());
                
                // --- LINHAS NOVAS E CORRIGIDAS ---
                // Agora também atualizamos os campos de alerta e e-mail
                acaoExistente.setUsuarioEmail(acaoAtualizada.getUsuarioEmail());
                acaoExistente.setPrecoAlvoCompra(acaoAtualizada.getPrecoAlvoCompra());
                acaoExistente.setPrecoAlvoVenda(acaoAtualizada.getPrecoAlvoVenda());
                
                return repository.save(acaoExistente);
            })
            .orElseThrow(() -> new RuntimeException("Ação com id " + id + " não encontrada!"));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Ação com id " + id + " não encontrada para exclusão!");
        }
        repository.deleteById(id);
    }
    @Cacheable(value = "favoritasComPreco") 
    public List<AcaoFavoritaDetalhadaDTO> listarFavoritasComPreco() {
        List<AcaoFavorita> todasAsFavoritas = repository.findAll(); // atenção no nome!

        List<CompletableFuture<AcaoFavoritaDetalhadaDTO>> futures = todasAsFavoritas.stream()
            .map(favorita -> brapiService.consultarAcaoAsync(favorita.getTicker())
                .thenApply(dados -> new AcaoFavoritaDetalhadaDTO(favorita, dados.getPreco())))
            .collect(Collectors.toList());

        // Espera todas finalizarem
        return futures.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
    }
}



    

