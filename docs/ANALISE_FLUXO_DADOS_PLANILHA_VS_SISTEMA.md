# ANÁLISE: PLANILHA SAMARCO vs SISTEMA ATUAL

**Data:** 2025-12-20
**Objetivo:** Verificar alinhamento entre a planilha Excel e o sistema implementado

---

## RESUMO EXECUTIVO

| Componente | Planilha | Sistema | Status |
|------------|----------|---------|--------|
| Planejamento Produção | ✅ | ✅ | ✅ ALINHADO |
| Produção (calc) | ✅ | ✅ | ✅ ALINHADO |
| ProducaoConfig (multiplicador) | ✅ | ✅ | ✅ ALINHADO |
| Área com TipoArea | ✅ | ✅ | ⚠️ PARCIAL |
| Centro de Custo | ✅ | ❌ | ❌ FALTA |
| Consumo Específico | ✅ | ❌ | ❌ FALTA |
| Consumo Área | ✅ | ❌ | ❌ FALTA |
| Demanda | ✅ | ❌ | ❌ FALTA |
| Planejamento Geração | ✅ | ❌ | ❌ FALTA |
| Geração Própria | ✅ | ❌ | ❌ FALTA |
| Divisão de Custo por Estado | ✅ | ❌ | ❌ FALTA |
| Fórmula especial Mineração | ✅ | ❌ | ❌ FALTA |
| Override manual | ✅ | ❌ | ❌ FALTA |

---

## PARTE 1: O QUE ESTÁ IMPLEMENTADO E ALINHADO

### 1.1 PlanejamentoProducao ✅

**Planilha (Aba: Planejamento Produção):**
```
| Área                    | Jan      | Fev      | Mar      | ... |
|-------------------------|----------|----------|----------|-----|
| Filtragem Germano       | 1059.07  | 1021.03  | 1004.45  | ... |
| Beneficiamento Usina 2  | 599.78   | 546.47   | 576.60   | ... |
| Beneficiamento Usina 3  | 660.81   | 683.62   | 680.47   | ... |
```

**Sistema (Entidade):** `PlanejamentoProducao.java:31`
```java
@Entity
public class PlanejamentoProducao extends BaseModel {
    private Revisao revisao;
    private Area area;
    private BigDecimal valorJaneiro;
    private BigDecimal valorFevereiro;
    // ... até valorDezembro
}
```

**Avaliação:** ✅ TOTALMENTE ALINHADO
- Estrutura idêntica (área + 12 meses)
- Valores com precisão adequada (precision=20, scale=10)
- Constraint de unicidade (revisao_id + area_id)

---

### 1.2 Produção (Calculado) ✅

**Planilha (Aba: Produção):**
```
Produção = Planejamento × 1000

Exemplo:
Filtragem = 1059.0685 × 1000 = 1.059.068,48 tms
```

**Sistema (Use Case):** `TabelaProducaoQueryUC.java:51`
```java
private TablePlanejamentoProducaoDTO calcProducao(PlanejamentoProducao it, BigDecimal multiplicador) {
    return TablePlanejamentoProducaoDTO.builder()
        .valorPlanejadoJaneiro(it.getValorJaneiro().multiply(multiplicador, DECIMAL64))
        .valorPlanejadoFevereiro(it.getValorFevereiro().multiply(multiplicador, DECIMAL64))
        // ... demais meses
        .build();
}
```

**Avaliação:** ✅ TOTALMENTE ALINHADO
- Cálculo idêntico (valor × multiplicador)
- Multiplicador configurável via ProducaoConfig (padrão: 1000)

---

### 1.3 ProducaoConfig (Multiplicador) ✅

**Planilha:**
- Célula B20 contém o valor 1000 (multiplicador fixo)

**Sistema (Entidade):** `ProducaoConfig.java:28`
```java
@Entity
public class ProducaoConfig extends BaseModel {
    private Revisao revisao;
    private Integer multiplicador;  // ← Corresponde à célula B20
    private Set<Area> areas;        // Áreas configuradas para produção
}
```

**Avaliação:** ✅ TOTALMENTE ALINHADO
- Multiplicador configurável por revisão

---

### 1.4 TipoArea ⚠️ PARCIALMENTE ALINHADO

**Planilha (Áreas identificadas):**
```
MINAS GERAIS:
- Filtragem Germano
- Mineração 1
- Beneficiamento 1, 2, 3
- Mineroduto 1, 2, 3

ESPÍRITO SANTO:
- Preparação 1, 2
- Usina 1, 2, 3, 4
- Estocagem

OUTRAS:
- Vendas
- Pellet Feed
```

**Sistema (Enum):** `TipoArea.java:3`
```java
public enum TipoArea {
    FILTRAGEM,      // ✅
    MINERACAO,      // ✅
    BENEFICIAMENTO, // ✅
    MINERODUTO,     // ✅
    PREPARACAO,     // ✅
    USINA,          // ✅
    VENDAS,         // ✅
    ESTOCAGEM,      // ✅
    PRODUCAO,       // ❓ Não identificado na planilha
    EMBARQUE;       // ❓ Não identificado na planilha
}
```

**Gaps identificados:**
- ❌ Falta `PELLET_FEED` no enum
- ⚠️ `PRODUCAO` e `EMBARQUE` existem no sistema mas não aparecem na planilha

---

## PARTE 2: O QUE ESTÁ FALTANDO

### 2.1 Centro de Custo ❌ NÃO EXISTE

**Planilha (mapeamento):**
```
MINAS GERAIS (prefixo 11):
  11140030 → Filtragem Germano
  11110060 → Mineração 1
  11130180 → Beneficiamento 1
  11131180 → Beneficiamento 2
  11132180 → Beneficiamento 3
  11162040 → Mineroduto 1
  11162090 → Mineroduto 2
  11162120 → Mineroduto 3

ESPÍRITO SANTO (prefixo 12):
  12110080 → Preparação 1
  12111080 → Preparação 2
  12133040 → Usina 1
  12135040 → Usina 2
  12136040 → Usina 3
  12138040 → Usina 4
  12420080 → Estocagem
```

**Sistema atual:** A entidade `Area` não tem campo de Centro de Custo

**Recomendação:** Adicionar campo `centroCusto` na entidade `Area`:
```java
@Entity
public class Area extends BaseModel {
    // ... campos existentes ...

    @Column(length = 10)
    private String centroCusto;  // Ex: "11140030"
}
```

---

### 2.2 Consumo Específico (INPUT) ❌ NÃO EXISTE

**Planilha (Aba: Planejamento Consumo Específico):**
```
| Área                | Jan    | Fev    | Mar    | Unidade   |
|---------------------|--------|--------|--------|-----------|
| Filtragem Germano   | 3.2782 | 3.3595 | 3.2874 | kWh/tms   |
| Mineração           | 1.4841 | 1.3743 | 1.4888 | kWh/tms   |
| Beneficiamento 2    | 42.396 | 46.531 | 44.099 | kWh/tms   |
| Preparação 2        | 10.770 | 10.770 | 10.770 | kWh/tms ← FIXO! |
```

**Sistema atual:** ❌ Não existe entidade para isso

**Entidade necessária:**
```java
@Entity
public class PlanejamentoConsumoEspecifico extends BaseModel {
    private Revisao revisao;
    private Area area;

    // Pode ser valor único OU 12 valores mensais
    private BigDecimal valorUnico;       // Para áreas com consumo fixo
    private BigDecimal valorJaneiro;     // Para áreas com consumo variável
    private BigDecimal valorFevereiro;
    // ... até valorDezembro

    private boolean consumoFixo;  // Flag: usar valorUnico ou valores mensais
}
```

---

### 2.3 Consumo Área (CALCULADO + INPUTS MANUAIS) ❌ NÃO EXISTE

**Planilha (Aba: Consumo Área):**

Esta aba tem 4 seções e é a mais complexa:

#### Seção 1: Consumo por Centro de Custo (kWh)
```
Fórmula padrão: Consumo = Produção × Consumo Específico

EXCEÇÕES:
1. Mineração = (Benef1 + Benef2 + Benef3) × ConsumoEsp.Mineração
2. Beneficiamento 1 = INPUT MANUAL (1.040.116 kWh)
3. Mineroduto 1 = INPUT MANUAL (varia: 230k, 210k...)
```

#### Seção 2: Consumo por Área Agregada (MWh)
```
Concentração = Beneficiamento 1 + 2 + 3
Mineroduto   = Mineroduto 1 + 2 + 3
Pelotização  = Usina 1 + 2 + 3 + 4
```

#### Seção 3: Totais por Estado (MWh)
```
Total MG = Filtragem + Mineração + Concentração + Mineroduto
Total ES = Preparação + Pelotização + Estocagem
```

#### Seção 4: Divisão de Custo (%)
```
% Área = Consumo_Área / Total_Estado
```

**Entidades necessárias:**
```java
// 1. Consumo calculado
@Entity
public class ConsumoArea extends BaseModel {
    private Revisao revisao;
    private Area area;

    // Valores calculados (12 meses)
    private BigDecimal consumoJaneiro;
    // ... até consumoDezembro

    // Override manual (quando o usuário quer sobrescrever o cálculo)
    private BigDecimal overrideJaneiro;
    // ... até overrideDezembro

    // Método para obter valor final
    public BigDecimal getConsumoMes(int mes) {
        BigDecimal override = getOverride(mes);
        return override != null ? override : getCalculado(mes);
    }
}

// 2. Enum para tipo de cálculo especial
public enum TipoCalculoConsumo {
    PADRAO,              // Produção × ConsumoEspecifico
    SOMA_BENEFICIAMENTO, // Para Mineração
    MANUAL               // Valor digitado
}
```

---

### 2.4 Demanda ❌ NÃO EXISTE

**Planilha (Aba: Demanda):**
```
| Unidade  | Horário     | Jan   | Fev   | Mar   | ... |
|----------|-------------|-------|-------|-------|-----|
| Germano  | Ponta       | 15000 | 15000 | 15000 | ... |
| Germano  | Fora Ponta  | 45000 | 45000 | 45000 | ... |
| Ubu      | Ponta       | 22000 | 22000 | 22000 | ... |
| Ubu      | Fora Ponta  | 55000 | 55000 | 55000 | ... |
```

**Entidade necessária:**
```java
@Entity
public class Demanda extends BaseModel {
    private Revisao revisao;
    private Unidade unidade;

    @Enumerated(EnumType.STRING)
    private TipoHorario tipoHorario;  // PONTA, FORA_PONTA

    private BigDecimal valorJaneiro;
    // ... até valorDezembro
}

public enum TipoHorario {
    PONTA,
    FORA_PONTA
}
```

---

### 2.5 Planejamento Geração / Geração Própria ❌ NÃO EXISTE

**Planilha (Abas: Planejamento Geração e Geração Própria):**
```
Planejamento Geração (INPUT):
| Usina     | Jan   | Fev   | Mar   | ... |
|-----------|-------|-------|-------|-----|
| PCH 1     | 5000  | 4500  | 5200  | ... |
| Solar 1   | 3000  | 2800  | 3500  | ... |

Geração Própria (CALCULADO):
- Aplica calendário de operação
- Converte unidades
- Calcula total disponível
```

**Entidades necessárias:**
```java
@Entity
public class PlanejamentoGeracao extends BaseModel {
    private Revisao revisao;
    private Unidade unidadeGeradora;  // Unidade com flag unidadeGeradora=true

    private BigDecimal valorJaneiro;  // MWh planejado
    // ... até valorDezembro
}
```

---

### 2.6 Fórmula Especial para Mineração ❌ NÃO IMPLEMENTADA

**Planilha:**
```
Mineração NÃO usa produção própria.
Usa a SOMA das produções de Beneficiamento:

Consumo_Mineração = (Prod_Benef1 + Prod_Benef2 + Prod_Benef3) × ConsumoEsp_Mineração
                  = (0 + 599.777 + 660.813) × 1,4841
                  = 1.870.828 kWh
```

**Excel (fórmula real):**
```
=(Produção!B4+Produção!B5+Produção!B6)*'Consumo Especifico'!C4
```

**Implementação necessária:**

Opção 1: Flag no TipoArea
```java
public enum TipoArea {
    MINERACAO(true),  // usaSomaProducaoBeneficiamento = true
    BENEFICIAMENTO(false),
    // ... outros

    private final boolean usaSomaProducaoBeneficiamento;
}
```

Opção 2: Lógica no Use Case
```java
public BigDecimal calcularConsumoArea(Area area, Revisao revisao, int mes) {
    BigDecimal producao;

    if (area.getTipoArea() == TipoArea.MINERACAO) {
        // Soma produção de todas as áreas de beneficiamento
        producao = producaoRepository.findByTipoArea(TipoArea.BENEFICIAMENTO, revisao)
            .stream()
            .map(p -> p.getValorMes(mes))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    } else {
        producao = producaoRepository.findByArea(area, revisao).getValorMes(mes);
    }

    BigDecimal consumoEspecifico = consumoEspRepository.findByArea(area, revisao).getValorMes(mes);
    return producao.multiply(consumoEspecifico);
}
```

---

### 2.7 Agrupamento Hierárquico de Áreas ❌ NÃO IMPLEMENTADO

**Planilha (Seção 2 do Consumo Área):**
```
Concentração  = Beneficiamento 1 + 2 + 3
Mineroduto    = Mineroduto 1 + 2 + 3
Preparação    = Preparação 1 + 2
Pelotização   = Usina 1 + 2 + 3 + 4
```

**Implementação sugerida:**
```java
public enum TipoAreaAgregado {
    FILTRAGEM(TipoArea.FILTRAGEM),
    MINERACAO(TipoArea.MINERACAO),
    CONCENTRACAO(TipoArea.BENEFICIAMENTO),  // Agrupa todos os Beneficiamentos
    MINERODUTO(TipoArea.MINERODUTO),
    PREPARACAO(TipoArea.PREPARACAO),
    PELOTIZACAO(TipoArea.USINA),  // Agrupa todas as Usinas
    ESTOCAGEM(TipoArea.ESTOCAGEM);

    private final TipoArea tipoAreaBase;
}
```

---

## PARTE 3: MATRIZ DE IMPLEMENTAÇÃO

### Prioridade 1 (Crítico - Bloqueia fluxo principal)

| Item | Esforço | Impacto |
|------|---------|---------|
| Centro de Custo na Area | Baixo | Alto |
| PlanejamentoConsumoEspecifico | Médio | Alto |
| ConsumoArea (cálculo básico) | Médio | Alto |

### Prioridade 2 (Importante - Completa o fluxo)

| Item | Esforço | Impacto |
|------|---------|---------|
| Fórmula especial Mineração | Baixo | Médio |
| Override manual no ConsumoArea | Baixo | Médio |
| Agregação por TipoArea | Baixo | Médio |
| Totais por Estado | Baixo | Médio |
| Divisão de Custo (%) | Baixo | Médio |

### Prioridade 3 (Complementar)

| Item | Esforço | Impacto |
|------|---------|---------|
| Demanda | Médio | Médio |
| PlanejamentoGeracao | Médio | Médio |
| GeracaoPropria (calc) | Médio | Médio |

---

## PARTE 4: DIAGRAMA DE ENTIDADES PROPOSTO

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          ENTIDADES EXISTENTES                                │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────┐      ┌──────────────┐      ┌──────────────┐
│ Planejamento │◄─────│   Revisao    │◄─────│ProducaoConfig│
│              │      │              │      │multiplicador │
└──────────────┘      └──────┬───────┘      └──────────────┘
                             │
                             │
              ┌──────────────┴──────────────┐
              │                             │
              ▼                             ▼
┌─────────────────────┐          ┌─────────────────────┐
│PlanejamentoProducao │          │  TarifaPlanejamento │
│  valorJan...valorDez│          │                     │
│  + area             │          │                     │
└──────────┬──────────┘          └─────────────────────┘
           │
           │ × multiplicador
           ▼
┌─────────────────────┐
│   Produção (DTO)    │ ← Calculado pelo TabelaProducaoQueryUC
│ valorPlanejadoJan...│
└─────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                         ENTIDADES A CRIAR                                    │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────┐
│   Revisao    │
└──────┬───────┘
       │
       │ 1:N
       ▼
┌─────────────────────────────┐      ┌──────────────────────────────┐
│PlanejamentoConsumoEspecifico│      │         ConsumoArea           │
│  + area                     │      │  + area                       │
│  + valorJan...valorDez      │─────►│  + consumoJan...consumoDez    │
│  (kWh/tms)                  │      │  + overrideJan...overrideDez  │
└─────────────────────────────┘      │  (kWh)                        │
                                     └──────────────────────────────┘
                                                   │
                                                   │ ÷ 1000
                                                   ▼
                                     ┌──────────────────────────────┐
                                     │     ConsumoAgregado (DTO)     │
                                     │  + tipoAreaAgregado           │
                                     │  + valorMWh                   │
                                     └──────────────────────────────┘
                                                   │
                                                   │ SUM por estado
                                                   ▼
                                     ┌──────────────────────────────┐
                                     │      ConsumoEstado (DTO)      │
                                     │  + estado (MG/ES)             │
                                     │  + totalMWh                   │
                                     └──────────────────────────────┘
                                                   │
                                                   │ Área / Total
                                                   ▼
                                     ┌──────────────────────────────┐
                                     │      DivisaoCusto (DTO)       │
                                     │  + area                       │
                                     │  + percentual                 │
                                     └──────────────────────────────┘


┌──────────────┐
│   Revisao    │
└──────┬───────┘
       │
       │ 1:N
       ▼
┌─────────────────────────────┐      ┌──────────────────────────────┐
│         Demanda             │      │    PlanejamentoGeracao        │
│  + unidade                  │      │  + unidadeGeradora            │
│  + tipoHorario              │      │  + valorJan...valorDez        │
│  + valorJan...valorDez      │      │  (MWh)                        │
│  (kW)                       │      └──────────────────────────────┘
└─────────────────────────────┘


┌──────────────┐
│    Area      │ ← MODIFICAR
└──────────────┘
    ADICIONAR:
    - centroCusto: String (ex: "11140030")
```

---

## PARTE 5: CONCLUSÃO

### Status Atual
O sistema tem a base implementada para **Planejamento de Produção** e **cálculo de Produção**, mas falta toda a parte de **Consumo** que é essencial para o fluxo completo da planilha.

### Próximos Passos Recomendados

1. **Adicionar `centroCusto` na entidade Area**
2. **Criar entidade `PlanejamentoConsumoEspecifico`**
3. **Criar Use Case de cálculo de Consumo Área**
   - Com suporte a fórmula especial para Mineração
   - Com suporte a override manual
4. **Criar DTOs para agregação e divisão de custo**
5. **Criar entidade `Demanda`**
6. **Criar entidade `PlanejamentoGeracao`**

### Estimativa de Trabalho

| Fase | Entidades/Componentes | Complexidade |
|------|----------------------|--------------|
| Fase 1 | centroCusto + ConsumoEspecifico + ConsumoArea básico | Média |
| Fase 2 | Fórmula Mineração + Override + Agregação | Média |
| Fase 3 | Demanda + Geração | Média |

---

*Documento gerado para análise do fluxo de dados Samarco*
