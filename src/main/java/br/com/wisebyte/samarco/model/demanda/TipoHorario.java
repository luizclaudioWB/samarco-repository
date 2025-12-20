package br.com.wisebyte.samarco.model.demanda;

/**
 * Tipos de horário para tarifação de energia elétrica.
 *
 * No setor elétrico brasileiro:
 * - PONTA: 3 horas mais caras do dia (geralmente 18h-21h)
 * - FORA_PONTA: As outras 21 horas do dia
 */
public enum TipoHorario {

    PONTA("Horário de Ponta"),
    FORA_PONTA("Horário Fora de Ponta");

    private final String descricao;

    TipoHorario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
