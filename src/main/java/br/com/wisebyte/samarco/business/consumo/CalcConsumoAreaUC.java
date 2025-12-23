package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.business.exception.ValidadeExceptionBusiness;
import br.com.wisebyte.samarco.dto.QueryList;
import br.com.wisebyte.samarco.dto.area.AreaIdDTO;
import br.com.wisebyte.samarco.dto.consumo.ConsumoAreaResultDTO;
import br.com.wisebyte.samarco.model.area.TipoArea;
import br.com.wisebyte.samarco.model.consumo.PlanejamentoConsumoEspecifico;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import br.com.wisebyte.samarco.model.producao.ProducaoConfig;
import br.com.wisebyte.samarco.repository.consumo.PlanejamentoConsumoEspecificoRepository;
import br.com.wisebyte.samarco.repository.producao.PlanejamentoProducaoRepository;
import br.com.wisebyte.samarco.repository.producao.ProducaoConfigRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.math.MathContext.DECIMAL64;

@ApplicationScoped
public class CalcConsumoAreaUC {

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    ProducaoConfigRepository producaoConfigRepository;

    @Inject
    PlanejamentoProducaoRepository planejamentoProducaoRepository;

    @Inject
    PlanejamentoConsumoEspecificoRepository consumoEspecificoRepository;

    public QueryList<ConsumoAreaResultDTO> calcConsumoArea(@NotNull Long revisaoId) {
        var revisao = revisaoRepository.findById(revisaoId)
                .orElseThrow(() -> new ValidadeExceptionBusiness("Revisao", "Revisao", "Revisao nao encontrada"));

        var config = producaoConfigRepository.findByRevisaoId(revisaoId)
                .orElseThrow(() -> new ValidadeExceptionBusiness("ProducaoConfig", "ProducaoConfig", "Config de producao nao encontrada"));

        BigDecimal multiplicador = valueOf(config.getMultiplicador());

        // Busca todas as producoes e consumos especificos da revisao
        List<PlanejamentoProducao> producoes = planejamentoProducaoRepository.findByRevisao(revisao);
        List<PlanejamentoConsumoEspecifico> consumosEspecificos = consumoEspecificoRepository.findByRevisao(revisao);

        // Mapeia producao por area para acesso rapido
        Map<Long, PlanejamentoProducao> producaoPorArea = producoes.stream()
                .collect(Collectors.toMap(p -> p.getArea().getId(), p -> p));

        // Calcula soma das producoes de beneficiamento (para uso em Mineracao)
        BigDecimal[] somaProducaoBeneficiamento = calcularSomaProducaoBeneficiamento(producoes, multiplicador);

        // Calcula consumo para cada area que tem consumo especifico cadastrado
        List<ConsumoAreaResultDTO> resultados = consumosEspecificos.stream()
                .map(ce -> calcularConsumoParaArea(ce, producaoPorArea, multiplicador, somaProducaoBeneficiamento))
                .sorted(Comparator.comparing(dto -> dto.getArea().getId()))
                .toList();

        return QueryList.<ConsumoAreaResultDTO>builder()
                .totalElements((long) resultados.size())
                .totalPages(1L)
                .results(resultados)
                .build();
    }

    private BigDecimal[] calcularSomaProducaoBeneficiamento(List<PlanejamentoProducao> producoes, BigDecimal multiplicador) {
        BigDecimal[] somaMensal = new BigDecimal[12];
        for (int i = 0; i < 12; i++) {
            somaMensal[i] = ZERO;
        }

        producoes.stream()
                .filter(p -> p.getArea().getTipoArea() == TipoArea.BENEFICIAMENTO)
                .forEach(p -> {
                    somaMensal[0] = somaMensal[0].add(safeMultiply(p.getValorJaneiro(), multiplicador));
                    somaMensal[1] = somaMensal[1].add(safeMultiply(p.getValorFevereiro(), multiplicador));
                    somaMensal[2] = somaMensal[2].add(safeMultiply(p.getValorMarco(), multiplicador));
                    somaMensal[3] = somaMensal[3].add(safeMultiply(p.getValorAbril(), multiplicador));
                    somaMensal[4] = somaMensal[4].add(safeMultiply(p.getValorMaio(), multiplicador));
                    somaMensal[5] = somaMensal[5].add(safeMultiply(p.getValorJunho(), multiplicador));
                    somaMensal[6] = somaMensal[6].add(safeMultiply(p.getValorJulho(), multiplicador));
                    somaMensal[7] = somaMensal[7].add(safeMultiply(p.getValorAgosto(), multiplicador));
                    somaMensal[8] = somaMensal[8].add(safeMultiply(p.getValorSetembro(), multiplicador));
                    somaMensal[9] = somaMensal[9].add(safeMultiply(p.getValorOutubro(), multiplicador));
                    somaMensal[10] = somaMensal[10].add(safeMultiply(p.getValorNovembro(), multiplicador));
                    somaMensal[11] = somaMensal[11].add(safeMultiply(p.getValorDezembro(), multiplicador));
                });

        return somaMensal;
    }

    private ConsumoAreaResultDTO calcularConsumoParaArea(
            PlanejamentoConsumoEspecifico consumoEsp,
            Map<Long, PlanejamentoProducao> producaoPorArea,
            BigDecimal multiplicador,
            BigDecimal[] somaProducaoBeneficiamento) {

        var area = consumoEsp.getArea();
        boolean isMineracao = area.getTipoArea() == TipoArea.MINERACAO;

        // Para Mineracao, usa soma das producoes de beneficiamento
        // Para outras areas, usa a producao da propria area
        PlanejamentoProducao producao = producaoPorArea.get(area.getId());

        BigDecimal[] producaoMensal;
        if (isMineracao) {
            producaoMensal = somaProducaoBeneficiamento;
        } else if (producao != null) {
            producaoMensal = new BigDecimal[] {
                safeMultiply(producao.getValorJaneiro(), multiplicador),
                safeMultiply(producao.getValorFevereiro(), multiplicador),
                safeMultiply(producao.getValorMarco(), multiplicador),
                safeMultiply(producao.getValorAbril(), multiplicador),
                safeMultiply(producao.getValorMaio(), multiplicador),
                safeMultiply(producao.getValorJunho(), multiplicador),
                safeMultiply(producao.getValorJulho(), multiplicador),
                safeMultiply(producao.getValorAgosto(), multiplicador),
                safeMultiply(producao.getValorSetembro(), multiplicador),
                safeMultiply(producao.getValorOutubro(), multiplicador),
                safeMultiply(producao.getValorNovembro(), multiplicador),
                safeMultiply(producao.getValorDezembro(), multiplicador)
            };
        } else {
            producaoMensal = new BigDecimal[] { ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO, ZERO };
        }

        // Consumo = Producao x Consumo Especifico
        return ConsumoAreaResultDTO.builder()
                .area(AreaIdDTO.builder().id(area.getId()).build())
                .nomeArea(area.getNomeArea())
                .tipoArea(area.getTipoArea().name())
                .consumoJaneiro(safeMultiply(producaoMensal[0], consumoEsp.getValorJaneiro()))
                .consumoFevereiro(safeMultiply(producaoMensal[1], consumoEsp.getValorFevereiro()))
                .consumoMarco(safeMultiply(producaoMensal[2], consumoEsp.getValorMarco()))
                .consumoAbril(safeMultiply(producaoMensal[3], consumoEsp.getValorAbril()))
                .consumoMaio(safeMultiply(producaoMensal[4], consumoEsp.getValorMaio()))
                .consumoJunho(safeMultiply(producaoMensal[5], consumoEsp.getValorJunho()))
                .consumoJulho(safeMultiply(producaoMensal[6], consumoEsp.getValorJulho()))
                .consumoAgosto(safeMultiply(producaoMensal[7], consumoEsp.getValorAgosto()))
                .consumoSetembro(safeMultiply(producaoMensal[8], consumoEsp.getValorSetembro()))
                .consumoOutubro(safeMultiply(producaoMensal[9], consumoEsp.getValorOutubro()))
                .consumoNovembro(safeMultiply(producaoMensal[10], consumoEsp.getValorNovembro()))
                .consumoDezembro(safeMultiply(producaoMensal[11], consumoEsp.getValorDezembro()))
                .build();
    }

    private BigDecimal safeMultiply(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return ZERO;
        return a.multiply(b, DECIMAL64);
    }
}
