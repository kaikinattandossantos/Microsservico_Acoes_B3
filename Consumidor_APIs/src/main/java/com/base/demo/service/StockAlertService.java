package com.base.demo.service;

import com.base.demo.dto.EmailRequestDTO;
import com.base.demo.dto.TelegramRequestdto;
import com.base.demo.dto.AcaoDTO;
import com.base.demo.model.AcaoFavorita;
import com.base.demo.repository.AcaoFavoritaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockAlertService {

    @Autowired
    private AcaoFavoritaRepository acaoRepository;

    @Autowired
    private BrapiService brapiService;

    @Autowired
    private EmailServiceClient emailServiceClient;

    @Autowired
    private TelegramServiceClient telegramService;

    public void verificarPrecosParaAlerta() {
        System.out.println("ROTINA: Verificando preços de ações para alertas...");

        List<AcaoFavorita> acoesComAlerta = acaoRepository
                .findByPrecoAlvoCompraIsNotNullOrPrecoAlvoVendaIsNotNull();

        for (AcaoFavorita acao : acoesComAlerta) {
            AcaoDTO dadosDaAcao = brapiService.consultarAcao(acao.getTicker());

            if (dadosDaAcao == null || dadosDaAcao.getPreco() == 0.0) continue;

            double precoAtual = dadosDaAcao.getPreco();
            String motivoAlerta = null;

            if (acao.getPrecoAlvoCompra() != null && precoAtual <= acao.getPrecoAlvoCompra()) {
                motivoAlerta = "atingiu seu alvo de compra de R$" + acao.getPrecoAlvoCompra();
            } else if (acao.getPrecoAlvoVenda() != null && precoAtual >= acao.getPrecoAlvoVenda()) {
                motivoAlerta = "atingiu seu alvo de venda de R$" + acao.getPrecoAlvoVenda();
            }

            if (motivoAlerta != null) {
                try {
                    String mensagem = String.format(
                            "📈 Ação %s %s. O preço atual é R$%.2f.",
                            acao.getTicker(), motivoAlerta, precoAtual
                    );

                    // E-mail
                    EmailRequestDTO emailRequest = new EmailRequestDTO(
                            acao.getUsuarioEmail(),
                            "Alerta de Preço: " + acao.getTicker(),
                            mensagem
                    );
                    emailServiceClient.enviarEmail(emailRequest);

                    // Telegram
                    TelegramRequestdto telegramRequest = new TelegramRequestdto(mensagem);
                    telegramService.sendNotification(telegramRequest);

                    System.out.println("✅ Alerta enviado: " + mensagem);

                } catch (Exception e) {
                    System.err.println("❌ Falha ao enviar alerta para " + acao.getTicker() + ": " + e.getMessage());
                }
            }
        }
    }
}
