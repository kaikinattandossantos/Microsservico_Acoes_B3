package com.base.demo.controller;

import com.base.demo.dto.AcaoFavoritaDetalhadaDTO;
import com.base.demo.model.AcaoFavorita;
import com.base.demo.service.AcaoFavoritaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acoes-favoritas")
public class AcaoFavoritaController {

    @Autowired
    private AcaoFavoritaService service;

    @Operation(summary = "Cadastrar uma nova ação favorita",
               description = "Cria uma ação favorita no banco de dados e retorna os dados cadastrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ação cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public AcaoFavorita criarAcao(@RequestBody AcaoFavorita acao) {
        return service.salvar(acao);
    }

    @Operation(summary = "Listar todas as ações favoritas",
               description = "Retorna a lista de todas as ações favoritas cadastradas no sistema")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<AcaoFavorita> listarTodasAcoes() {
        return service.listarTodas();
    }

    @Operation(summary = "Buscar ação favorita por ID",
               description = "Retorna os detalhes de uma ação favorita específica pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ação encontrada"),
        @ApiResponse(responseCode = "404", description = "Ação não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AcaoFavorita> buscarAcaoPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar uma ação favorita",
               description = "Atualiza os dados de uma ação favorita existente pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ação atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ação não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AcaoFavorita> atualizarAcao(@PathVariable Long id, @RequestBody AcaoFavorita acao) {
        try {
            AcaoFavorita atualizada = service.atualizar(id, acao);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deletar uma ação favorita",
               description = "Remove uma ação favorita existente pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ação deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ação não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAcao(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar ações favoritas com detalhes",
               description = "Retorna todas as ações favoritas já com detalhes atualizados da Brapi API")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/detalhes")
    public List<AcaoFavoritaDetalhadaDTO> listarFavoritasComDetalhes() {
        return service.listarFavoritasComPreco();
    }
}
