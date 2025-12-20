package br.com.wisebyte.samarco.business.consumo;

import br.com.wisebyte.samarco.dto.consumo.ConsumoAreaResultDTO;
import br.com.wisebyte.samarco.model.area.Area;
import br.com.wisebyte.samarco.model.area.TipoArea;
import br.com.wisebyte.samarco.model.consumo.ConsumoEspecifico;
import br.com.wisebyte.samarco.model.planejamento.Revisao;
import br.com.wisebyte.samarco.model.producao.PlanejamentoProducao;
import br.com.wisebyte.samarco.repository.area.AreaRepository;
import br.com.wisebyte.samarco.repository.consumo.ConsumoEspecificoRepository;
import br.com.wisebyte.samarco.repository.producao.PlanejamentoProducaoRepository;
import br.com.wisebyte.samarco.repository.revisao.RevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Use Case para calcular o Consumo de Área.
 *
 * Fórmula padrão:
 *   ConsumoArea[area][mes] = Producao[area][mes] × ConsumoEspecifico[area][mes]
 *   Onde: Producao = PlanejamentoProducao × 1000
 *
 * Exceção para MINERACAO:
 *   Mineração não tem produção própria. Usa a soma das produções de BENEFICIAMENTO.
 *   ConsumoArea[Mineracao][mes] = (Σ Producao[Beneficiamentos][mes]) × ConsumoEspecifico[Mineracao][mes]
 */
@ApplicationScoped
public class CalcConsumoAreaUC {

    private static final BigDecimal MULTIPLICADOR_PRODUCAO = new BigDecimal("1000");

    @Inject
    AreaRepository areaRepository;

    @Inject
    RevisaoRepository revisaoRepository;

    @Inject
    PlanejamentoProducaoRepository planejamentoProducaoRepository;

    @Inject
    ConsumoEspecificoRepository consumoEspecificoRepository;

    /**
     * Calcula o consumo de todas as áreas para uma revisão.
     */
    public List<ConsumoAreaResultDTO> calcularPorRevisao(Long revisaoId) {
        Revisao revisao = revisaoRepository.findById(revisaoId)
                .orElseThrow(() -> new IllegalArgumentException("Revisão não encontrada: " + revisaoId));

        List<ConsumoAreaResultDTO> resultados = new ArrayList<>();

        // Busca todas as áreas ativas
        List<Area> areas = areaRepository.findByAtivo(true);

        for (Area area : areas) {
            ConsumoAreaResultDTO resultado = calcularParaArea(area, revisao);
            if (resultado != null) {
                resultados.add(resultado);
            }
        }

        return resultados;
    }

    /**
     * Calcula o consumo de uma área específica para uma revisão.
     */
    public ConsumoAreaResultDTO calcular(Long areaId, Long revisaoId) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new IllegalArgumentException("Área não encontrada: " + areaId));

        Revisao revisao = revisaoRepository.findById(revisaoId)
                .orElseThrow(() -> new IllegalArgumentException("Revisão não encontrada: " + revisaoId));

        return calcularParaArea(area, revisao);
    }

    private ConsumoAreaResultDTO calcularParaArea(Area area, Revisao revisao) {
        // Busca o consumo específico da área
        Optional<ConsumoEspecifico> consumoEspecificoOpt = consumoEspecificoRepository
                .findByRevisaoAndArea(revisao, area);

        if (consumoEspecificoOpt.isEmpty()) {
            return null; // Área sem consumo específico cadastrado
        }

        ConsumoEspecifico consumoEspecifico = consumoEspecificoOpt.get();

        // Calcula produção baseado no tipo de área
        ProducaoMensal producao = calcularProducao(area, revisao);

        // Calcula consumo de área para cada mês
        return ConsumoAreaResultDTO.builder()
                .areaId(area.getId())
                .nomeArea(area.getNomeArea())
                .centroCusto(null) // Adicionar quando existir campo na entidade
                .tipoArea(area.getTipoArea().name())
                .estado(null) // Adicionar quando existir campo na entidade
                .valorJaneiro(calcularMes(producao.janeiro, consumoEspecifico.getValorJaneiro()))
                .valorFevereiro(calcularMes(producao.fevereiro, consumoEspecifico.getValorFevereiro()))
                .valorMarco(calcularMes(producao.marco, consumoEspecifico.getValorMarco()))
                .valorAbril(calcularMes(producao.abril, consumoEspecifico.getValorAbril()))
                .valorMaio(calcularMes(producao.maio, consumoEspecifico.getValorMaio()))
                .valorJunho(calcularMes(producao.junho, consumoEspecifico.getValorJunho()))
                .valorJulho(calcularMes(producao.julho, consumoEspecifico.getValorJulho()))
                .valorAgosto(calcularMes(producao.agosto, consumoEspecifico.getValorAgosto()))
                .valorSetembro(calcularMes(producao.setembro, consumoEspecifico.getValorSetembro()))
                .valorOutubro(calcularMes(producao.outubro, consumoEspecifico.getValorOutubro()))
                .valorNovembro(calcularMes(producao.novembro, consumoEspecifico.getValorNovembro()))
                .valorDezembro(calcularMes(producao.dezembro, consumoEspecifico.getValorDezembro()))
                .producaoUtilizada(producao.janeiro) // Exemplo: valor de janeiro
                .consumoEspecificoUsado(consumoEspecifico.getValorJaneiro())
                .build();
    }

    /**
     * Calcula a produção mensal de uma área.
     * Para MINERACAO, soma as produções de todas as áreas de BENEFICIAMENTO.
     */
    private ProducaoMensal calcularProducao(Area area, Revisao revisao) {
        if (area.getTipoArea() == TipoArea.MINERACAO) {
            return calcularProducaoMineracao(revisao);
        }
        return calcularProducaoArea(area, revisao);
    }

    /**
     * Calcula produção padrão: PlanejamentoProducao × 1000
     */
    private ProducaoMensal calcularProducaoArea(Area area, Revisao revisao) {
        Optional<PlanejamentoProducao> ppOpt = planejamentoProducaoRepository
                .findByRevisaoAndArea(revisao, area);

        if (ppOpt.isEmpty()) {
            return ProducaoMensal.zero();
        }

        PlanejamentoProducao pp = ppOpt.get();

        return new ProducaoMensal(
                multiplicar(pp.getValorJaneiro()),
                multiplicar(pp.getValorFevereiro()),
                multiplicar(pp.getValorMarco()),
                multiplicar(pp.getValorAbril()),
                multiplicar(pp.getValorMaio()),
                multiplicar(pp.getValorJunho()),
                multiplicar(pp.getValorJulho()),
                multiplicar(pp.getValorAgosto()),
                multiplicar(pp.getValorSetembro()),
                multiplicar(pp.getValorOutubro()),
                multiplicar(pp.getValorNovembro()),
                multiplicar(pp.getValorDezembro())
        );
    }

    /**
     * Calcula produção especial para Mineração: soma das produções de Beneficiamento.
     * Mineração processa o output das usinas de beneficiamento.
     */
    private ProducaoMensal calcularProducaoMineracao(Revisao revisao) {
        List<Area> areasBeneficiamento = areaRepository.findByTipoArea(TipoArea.BENEFICIAMENTO);

        ProducaoMensal total = ProducaoMensal.zero();

        for (Area benef : areasBeneficiamento) {
            ProducaoMensal producaoBenef = calcularProducaoArea(benef, revisao);
            total = total.somar(producaoBenef);
        }

        return total;
    }

    private BigDecimal multiplicar(BigDecimal valor) {
        if (valor == null) return BigDecimal.ZERO;
        return valor.multiply(MULTIPLICADOR_PRODUCAO);
    }

    private BigDecimal calcularMes(BigDecimal producao, BigDecimal consumoEspecifico) {
        if (producao == null || consumoEspecifico == null) {
            return BigDecimal.ZERO;
        }
        return producao.multiply(consumoEspecifico);
    }

    /**
     * Classe auxiliar para armazenar produção mensal.
     */
    private static class ProducaoMensal {
        final BigDecimal janeiro;
        final BigDecimal fevereiro;
        final BigDecimal marco;
        final BigDecimal abril;
        final BigDecimal maio;
        final BigDecimal junho;
        final BigDecimal julho;
        final BigDecimal agosto;
        final BigDecimal setembro;
        final BigDecimal outubro;
        final BigDecimal novembro;
        final BigDecimal dezembro;

        ProducaoMensal(BigDecimal jan, BigDecimal fev, BigDecimal mar, BigDecimal abr,
                       BigDecimal mai, BigDecimal jun, BigDecimal jul, BigDecimal ago,
                       BigDecimal set, BigDecimal out, BigDecimal nov, BigDecimal dez) {
            this.janeiro = jan != null ? jan : BigDecimal.ZERO;
            this.fevereiro = fev != null ? fev : BigDecimal.ZERO;
            this.marco = mar != null ? mar : BigDecimal.ZERO;
            this.abril = abr != null ? abr : BigDecimal.ZERO;
            this.maio = mai != null ? mai : BigDecimal.ZERO;
            this.junho = jun != null ? jun : BigDecimal.ZERO;
            this.julho = jul != null ? jul : BigDecimal.ZERO;
            this.agosto = ago != null ? ago : BigDecimal.ZERO;
            this.setembro = set != null ? set : BigDecimal.ZERO;
            this.outubro = out != null ? out : BigDecimal.ZERO;
            this.novembro = nov != null ? nov : BigDecimal.ZERO;
            this.dezembro = dez != null ? dez : BigDecimal.ZERO;
        }

        static ProducaoMensal zero() {
            return new ProducaoMensal(
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
            );
        }

        ProducaoMensal somar(ProducaoMensal outra) {
            return new ProducaoMensal(
                    this.janeiro.add(outra.janeiro),
                    this.fevereiro.add(outra.fevereiro),
                    this.marco.add(outra.marco),
                    this.abril.add(outra.abril),
                    this.maio.add(outra.maio),
                    this.junho.add(outra.junho),
                    this.julho.add(outra.julho),
                    this.agosto.add(outra.agosto),
                    this.setembro.add(outra.setembro),
                    this.outubro.add(outra.outubro),
                    this.novembro.add(outra.novembro),
                    this.dezembro.add(outra.dezembro)
            );
        }
    }
}
