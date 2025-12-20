# GraphQL - Exemplos com Dados Reais da Planilha Samarco

Este documento contém exemplos de mutations e queries usando os **valores reais** da planilha de planejamento energético.

---

## 1. PLANEJAMENTO PRODUCAO (INPUT)

### Cadastrar Filtragem Germano
```graphql
mutation CadastrarPlanejamentoProducao($dto: PlanejamentoProducaoDTOInput!) {
  cadastrarPlanejamentoProducao(planejamentoProducao: $dto) {
    id
    revisaoId
    areaId
    valorJaneiro
    valorFevereiro
    valorMarco
    valorAbril
    valorMaio
    valorJunho
    valorJulho
    valorAgosto
    valorSetembro
    valorOutubro
    valorNovembro
    valorDezembro
    totalAnual
  }
}
```

**Query Variables - Filtragem Germano (1059 ktms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 1,
    "valorJaneiro": 1059.0685,
    "valorFevereiro": 1021.0325,
    "valorMarco": 1004.4553,
    "valorAbril": 1119.8197,
    "valorMaio": 1137.3524,
    "valorJunho": 1148.2281,
    "valorJulho": 1188.5720,
    "valorAgosto": 1138.5105,
    "valorSetembro": 1149.7826,
    "valorOutubro": 1051.4632,
    "valorNovembro": 1120.4833,
    "valorDezembro": 1120.4833
  }
}
```

**Query Variables - Beneficiamento 2 (599 ktms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 5,
    "valorJaneiro": 599.7772,
    "valorFevereiro": 546.4746,
    "valorMarco": 576.6032,
    "valorAbril": 632.7965,
    "valorMaio": 644.9700,
    "valorJunho": 652.1432,
    "valorJulho": 690.9460,
    "valorAgosto": 628.4040,
    "valorSetembro": 646.9043,
    "valorOutubro": 571.6879,
    "valorNovembro": 612.4972,
    "valorDezembro": 612.4972
  }
}
```

**Query Variables - Beneficiamento 3 (660 ktms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 6,
    "valorJaneiro": 660.8133,
    "valorFevereiro": 683.6245,
    "valorMarco": 680.4743,
    "valorAbril": 762.2954,
    "valorMaio": 774.8256,
    "valorJunho": 793.2173,
    "valorJulho": 797.6672,
    "valorAgosto": 810.1518,
    "valorSetembro": 801.2609,
    "valorOutubro": 770.3453,
    "valorNovembro": 802.2949,
    "valorDezembro": 802.2949
  }
}
```

**Query Variables - Mineroduto 3 (1260 ktms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 10,
    "valorJaneiro": 1260.5905,
    "valorFevereiro": 1230.0991,
    "valorMarco": 1257.0775,
    "valorAbril": 1395.0919,
    "valorMaio": 1419.7956,
    "valorJunho": 1445.3605,
    "valorJulho": 1488.6132,
    "valorAgosto": 1438.5558,
    "valorSetembro": 1448.1652,
    "valorOutubro": 1342.0332,
    "valorNovembro": 1414.7921,
    "valorDezembro": 1414.7921
  }
}
```

**Query Variables - Usina 3 (474 ktms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 14,
    "valorJaneiro": 474.3914,
    "valorFevereiro": 457.3193,
    "valorMarco": 385.0529,
    "valorAbril": 527.5298,
    "valorMaio": 527.5298,
    "valorJunho": 560.4991,
    "valorJulho": 527.5298,
    "valorAgosto": 560.4991,
    "valorSetembro": 527.5298,
    "valorOutubro": 471.8953,
    "valorNovembro": 488.5426,
    "valorDezembro": 488.5426
  }
}
```

**Query Variables - Usina 4 (592 ktms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 15,
    "valorJaneiro": 592.8384,
    "valorFevereiro": 551.3332,
    "valorMarco": 678.8377,
    "valorAbril": 667.3810,
    "valorMaio": 691.5406,
    "valorJunho": 677.7466,
    "valorJulho": 756.6186,
    "valorAgosto": 671.3921,
    "valorSetembro": 712.4711,
    "valorOutubro": 660.1336,
    "valorNovembro": 717.4044,
    "valorDezembro": 717.4044
  }
}
```

**Query Variables - Estocagem (927 ktms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 16,
    "valorJaneiro": 1067.2298,
    "valorFevereiro": 1008.6525,
    "valorMarco": 1063.8906,
    "valorAbril": 1194.9108,
    "valorMaio": 1219.0704,
    "valorJunho": 1238.2457,
    "valorJulho": 1284.1484,
    "valorAgosto": 1231.8912,
    "valorSetembro": 1240.0009,
    "valorOutubro": 1132.0289,
    "valorNovembro": 1205.9470,
    "valorDezembro": 1205.9470
  }
}
```

---

## 2. CONSUMO ESPECIFICO (INPUT - kWh/tms)

### Cadastrar Consumo Especifico por Area
```graphql
mutation CadastrarConsumoEspecifico($dto: ConsumoEspecificoDTOInput!) {
  cadastrarConsumoEspecifico(dto: $dto) {
    id
    revisaoId
    areaId
    valorJaneiro
    valorFevereiro
    valorMarco
    valorAbril
    valorMaio
    valorJunho
    valorJulho
    valorAgosto
    valorSetembro
    valorOutubro
    valorNovembro
    valorDezembro
    mediaAnual
  }
}
```

**Query Variables - Filtragem Germano (3.27 kWh/tms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 1,
    "valorJaneiro": 3.2782,
    "valorFevereiro": 3.3595,
    "valorMarco": 3.2874,
    "valorAbril": 2.9699,
    "valorMaio": 2.9238,
    "valorJunho": 2.9399,
    "valorJulho": 2.8164,
    "valorAgosto": 2.9429,
    "valorSetembro": 2.9127,
    "valorOutubro": 3.1952,
    "valorNovembro": 3.0752,
    "valorDezembro": 3.1492
  }
}
```

**Query Variables - Mineracao (1.48 kWh/tms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 2,
    "valorJaneiro": 1.4841,
    "valorFevereiro": 1.3743,
    "valorMarco": 1.4888,
    "valorAbril": 1.3412,
    "valorMaio": 1.3187,
    "valorJunho": 1.2935,
    "valorJulho": 1.2575,
    "valorAgosto": 1.3061,
    "valorSetembro": 1.2815,
    "valorOutubro": 1.4191,
    "valorNovembro": 1.3193,
    "valorDezembro": 1.3234
  }
}
```

**Query Variables - Beneficiamento 2 (42.39 kWh/tms - alto consumo!):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 5,
    "valorJaneiro": 42.3960,
    "valorFevereiro": 46.5314,
    "valorMarco": 44.0999,
    "valorAbril": 40.1836,
    "valorMaio": 39.4258,
    "valorJunho": 39.0000,
    "valorJulho": 36.8100,
    "valorAgosto": 40.4635,
    "valorSetembro": 39.3087,
    "valorOutubro": 44.4954,
    "valorNovembro": 41.4920,
    "valorDezembro": 41.4920
  }
}
```

**Query Variables - Beneficiamento 3 (42.21 kWh/tms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 6,
    "valorJaneiro": 42.2150,
    "valorFevereiro": 40.8065,
    "valorMarco": 40.9953,
    "valorAbril": 36.5853,
    "valorMaio": 35.9694,
    "valorJunho": 35.1481,
    "valorJulho": 34.0981,
    "valorAgosto": 35.5035,
    "valorSetembro": 35.2276,
    "valorOutubro": 38.0319,
    "valorNovembro": 36.4269,
    "valorDezembro": 36.4269
  }
}
```

**Query Variables - Mineroduto 3 (7.10 kWh/tms):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 10,
    "valorJaneiro": 7.1051,
    "valorFevereiro": 7.1923,
    "valorMarco": 7.5586,
    "valorAbril": 7.0220,
    "valorMaio": 6.8932,
    "valorJunho": 7.2094,
    "valorJulho": 7.0220,
    "valorAgosto": 7.2094,
    "valorSetembro": 7.0220,
    "valorOutubro": 7.5586,
    "valorNovembro": 6.3563,
    "valorDezembro": 6.6599
  }
}
```

**Query Variables - Preparacao 2 (10.77 kWh/tms - FIXO):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 12,
    "valorJaneiro": 10.7700,
    "valorFevereiro": 10.7700,
    "valorMarco": 10.7700,
    "valorAbril": 10.7700,
    "valorMaio": 10.7700,
    "valorJunho": 10.7700,
    "valorJulho": 10.7700,
    "valorAgosto": 10.7700,
    "valorSetembro": 10.7700,
    "valorOutubro": 10.7700,
    "valorNovembro": 10.7700,
    "valorDezembro": 10.7700
  }
}
```

**Query Variables - Usina 3 (29.95 kWh/tms - FIXO):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 14,
    "valorJaneiro": 29.9500,
    "valorFevereiro": 29.9500,
    "valorMarco": 29.9500,
    "valorAbril": 29.9500,
    "valorMaio": 29.9500,
    "valorJunho": 29.9500,
    "valorJulho": 29.9500,
    "valorAgosto": 29.9500,
    "valorSetembro": 29.9500,
    "valorOutubro": 29.9500,
    "valorNovembro": 29.9500,
    "valorDezembro": 29.9500
  }
}
```

**Query Variables - Usina 4 (32.17 kWh/tms - FIXO):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 15,
    "valorJaneiro": 32.1700,
    "valorFevereiro": 32.1700,
    "valorMarco": 32.1700,
    "valorAbril": 32.1700,
    "valorMaio": 32.1700,
    "valorJunho": 32.1700,
    "valorJulho": 32.1700,
    "valorAgosto": 32.1700,
    "valorSetembro": 32.1700,
    "valorOutubro": 32.1700,
    "valorNovembro": 32.1700,
    "valorDezembro": 32.1700
  }
}
```

**Query Variables - Estocagem (0.86 kWh/tms - FIXO):**
```json
{
  "dto": {
    "revisaoId": 1,
    "areaId": 16,
    "valorJaneiro": 0.8688,
    "valorFevereiro": 0.8688,
    "valorMarco": 0.8688,
    "valorAbril": 0.8688,
    "valorMaio": 0.8688,
    "valorJunho": 0.8688,
    "valorJulho": 0.8688,
    "valorAgosto": 0.8688,
    "valorSetembro": 0.8688,
    "valorOutubro": 0.8688,
    "valorNovembro": 0.8688,
    "valorDezembro": 0.8688
  }
}
```

---

## 3. DEMANDA (INPUT - kW contratados)

### Cadastrar Demanda por Unidade/Horario
```graphql
mutation CadastrarDemanda($dto: DemandaDTOInput!) {
  cadastrarDemanda(dto: $dto) {
    id
    revisaoId
    unidadeId
    horario
    valorJaneiro
    valorFevereiro
    valorMarco
    valorAbril
    valorMaio
    valorJunho
    valorJulho
    valorAgosto
    valorSetembro
    valorOutubro
    valorNovembro
    valorDezembro
    mediaAnual
    totalAnual
  }
}
```

**Query Variables - Germano PONTA:**
```json
{
  "dto": {
    "revisaoId": 1,
    "unidadeId": 1,
    "horario": "PONTA",
    "valorJaneiro": 45000,
    "valorFevereiro": 45000,
    "valorMarco": 45000,
    "valorAbril": 50000,
    "valorMaio": 50000,
    "valorJunho": 50000,
    "valorJulho": 52000,
    "valorAgosto": 52000,
    "valorSetembro": 50000,
    "valorOutubro": 48000,
    "valorNovembro": 48000,
    "valorDezembro": 48000
  }
}
```

**Query Variables - Germano FORA_PONTA:**
```json
{
  "dto": {
    "revisaoId": 1,
    "unidadeId": 1,
    "horario": "FORA_PONTA",
    "valorJaneiro": 85000,
    "valorFevereiro": 85000,
    "valorMarco": 85000,
    "valorAbril": 95000,
    "valorMaio": 95000,
    "valorJunho": 95000,
    "valorJulho": 100000,
    "valorAgosto": 100000,
    "valorSetembro": 95000,
    "valorOutubro": 90000,
    "valorNovembro": 90000,
    "valorDezembro": 90000
  }
}
```

**Query Variables - Ubu PONTA:**
```json
{
  "dto": {
    "revisaoId": 1,
    "unidadeId": 2,
    "horario": "PONTA",
    "valorJaneiro": 35000,
    "valorFevereiro": 35000,
    "valorMarco": 35000,
    "valorAbril": 38000,
    "valorMaio": 40000,
    "valorJunho": 42000,
    "valorJulho": 45000,
    "valorAgosto": 45000,
    "valorSetembro": 42000,
    "valorOutubro": 40000,
    "valorNovembro": 38000,
    "valorDezembro": 38000
  }
}
```

---

## 4. GERACAO (INPUT - MWh usinas proprias)

### Cadastrar Geracao por Unidade Geradora
```graphql
mutation CadastrarGeracao($dto: GeracaoDTOInput!) {
  cadastrarGeracao(dto: $dto) {
    id
    revisaoId
    unidadeId
    valorJaneiro
    valorFevereiro
    valorMarco
    valorAbril
    valorMaio
    valorJunho
    valorJulho
    valorAgosto
    valorSetembro
    valorOutubro
    valorNovembro
    valorDezembro
    mediaMensal
    totalAnual
  }
}
```

**Query Variables - PCH Muniz Freire:**
```json
{
  "dto": {
    "revisaoId": 1,
    "unidadeId": 3,
    "valorJaneiro": 2500.00,
    "valorFevereiro": 2800.00,
    "valorMarco": 3200.00,
    "valorAbril": 2900.00,
    "valorMaio": 2600.00,
    "valorJunho": 2300.00,
    "valorJulho": 2100.00,
    "valorAgosto": 1900.00,
    "valorSetembro": 2000.00,
    "valorOutubro": 2200.00,
    "valorNovembro": 2400.00,
    "valorDezembro": 2700.00
  }
}
```

**Query Variables - PCH Jucu:**
```json
{
  "dto": {
    "revisaoId": 1,
    "unidadeId": 4,
    "valorJaneiro": 1800.00,
    "valorFevereiro": 2100.00,
    "valorMarco": 2400.00,
    "valorAbril": 2200.00,
    "valorMaio": 1900.00,
    "valorJunho": 1700.00,
    "valorJulho": 1500.00,
    "valorAgosto": 1400.00,
    "valorSetembro": 1500.00,
    "valorOutubro": 1700.00,
    "valorNovembro": 1900.00,
    "valorDezembro": 2000.00
  }
}
```

---

## 5. DISTRIBUIDORA (INPUT - cadastro)

### Cadastrar Distribuidoras
```graphql
mutation CadastrarDistribuidora($dto: DistribuidoraDTOInput!) {
  cadastrarDistribuidora(dto: $dto) {
    id
    nome
    cnpj
    siglaAgente
    estado
  }
}
```

**Query Variables - CEMIG (MG):**
```json
{
  "dto": {
    "nome": "CEMIG Distribuicao S.A.",
    "cnpj": "06981180000116",
    "siglaAgente": "CEMIG-D",
    "estado": "MG"
  }
}
```

**Query Variables - EDP (ES):**
```json
{
  "dto": {
    "nome": "EDP Espirito Santo Distribuicao de Energia S.A.",
    "cnpj": "28152650000171",
    "siglaAgente": "EDP-ES",
    "estado": "ES"
  }
}
```

---

## 6. FORNECEDOR (INPUT - cadastro com preco base)

### Cadastrar Fornecedores
```graphql
mutation CadastrarFornecedor($dto: FornecedorDTOInput!) {
  cadastrarFornecedor(dto: $dto) {
    id
    nome
    cnpj
    inicioDatabase
    precoBase
    estado
  }
}
```

**Query Variables - Cemig Convencional:**
```json
{
  "dto": {
    "nome": "Cemig Convencional",
    "cnpj": "06981180000116",
    "inicioDatabase": "2021-05-01",
    "precoBase": 142.59,
    "estado": "MG"
  }
}
```

**Query Variables - Cemig Incentivada:**
```json
{
  "dto": {
    "nome": "Cemig Incentivada 50%",
    "cnpj": "06981180000116",
    "inicioDatabase": "2021-05-01",
    "precoBase": 128.33,
    "estado": "MG"
  }
}
```

**Query Variables - Casa dos Ventos:**
```json
{
  "dto": {
    "nome": "Casa dos Ventos I5",
    "cnpj": "12345678000199",
    "inicioDatabase": "2022-01-01",
    "precoBase": 155.00,
    "estado": "CE"
  }
}
```

---

## 7. TARIFA FORNECEDOR (MISTO - INPUT + campos calculados)

### Cadastrar Tarifa Fornecedor
```graphql
mutation CadastrarTarifaFornecedor($dto: TarifaFornecedorDTOInput!) {
  cadastrarTarifaFornecedor(tarifaFornecedor: $dto) {
    id
    tarifaPlanejamentoId
    fornecedorId
    precoBase
    ipcaRealizada
    ipcaProjetado
    montante
    # CAMPOS CALCULADOS:
    ipcaTotal
    preco
    valorMontante
  }
}
```

**Query Variables - Cemig Convencional (80 MWmed):**
```json
{
  "dto": {
    "tarifaPlanejamentoId": 1,
    "fornecedorId": 1,
    "ipcaRealizada": 0.2760,
    "ipcaProjetado": 0.0235,
    "montante": 80
  }
}
```

**Resultado esperado (campos calculados):**
```
precoBase: 142.59 (vem do Fornecedor)
ipcaTotal: 0.2995 (= 0.2760 + 0.0235)
preco: 185.28 (= 142.59 × 1.2995)
valorMontante: 14822.40 (= 80 × 185.28)
```

**Query Variables - Cemig Incentivada (60 MWmed):**
```json
{
  "dto": {
    "tarifaPlanejamentoId": 1,
    "fornecedorId": 2,
    "ipcaRealizada": 0.2760,
    "ipcaProjetado": 0.0235,
    "montante": 60
  }
}
```

**Query Variables - Casa dos Ventos (40 MWmed):**
```json
{
  "dto": {
    "tarifaPlanejamentoId": 1,
    "fornecedorId": 3,
    "ipcaRealizada": 0.1500,
    "ipcaProjetado": 0.0400,
    "montante": 40
  }
}
```

---

## 8. TARIFA DISTRIBUIDORA (INPUT - uso de rede)

### Cadastrar Tarifa Distribuidora
```graphql
mutation CadastrarTarifaDistribuidora($dto: TarifaDistribuidoraDTOInput!) {
  cadastrarTarifaDistribuidora(tarifaDistribuidora: $dto) {
    id
    tarifaPlanejamentoId
    distribuidoraId
    periodoInicial
    periodoFinal
    valorPonta
    valorForaPonta
    valorEncargos
    valorEncargosAutoProducao
    percentualPisCofins
    sobrescreverICMS
    percentualICMS
    qtdeDeHorasPonta
  }
}
```

**Query Variables - CEMIG 2026:**
```json
{
  "dto": {
    "tarifaPlanejamentoId": 1,
    "distribuidoraId": 1,
    "periodoInicial": "2026-01-01",
    "periodoFinal": "2026-05-27",
    "valorPonta": 7.099049,
    "valorForaPonta": 0.505891,
    "valorEncargos": 112.50,
    "valorEncargosAutoProducao": 56.25,
    "percentualPisCofins": 0.0925,
    "sobrescreverICMS": false,
    "percentualICMS": null,
    "qtdeDeHorasPonta": 3
  }
}
```

**Query Variables - CEMIG 2026 (segundo periodo):**
```json
{
  "dto": {
    "tarifaPlanejamentoId": 1,
    "distribuidoraId": 1,
    "periodoInicial": "2026-05-28",
    "periodoFinal": "2026-12-31",
    "valorPonta": 7.455001,
    "valorForaPonta": 0.531185,
    "valorEncargos": 118.12,
    "valorEncargosAutoProducao": 59.06,
    "percentualPisCofins": 0.0925,
    "sobrescreverICMS": false,
    "percentualICMS": null,
    "qtdeDeHorasPonta": 3
  }
}
```

**Query Variables - EDP 2026:**
```json
{
  "dto": {
    "tarifaPlanejamentoId": 1,
    "distribuidoraId": 2,
    "periodoInicial": "2026-01-01",
    "periodoFinal": "2026-08-06",
    "valorPonta": 1.259940,
    "valorForaPonta": 0.325946,
    "valorEncargos": 95.00,
    "valorEncargosAutoProducao": 47.50,
    "percentualPisCofins": 0.0925,
    "sobrescreverICMS": true,
    "percentualICMS": 0.25,
    "qtdeDeHorasPonta": 3
  }
}
```

---

## 9. QUERIES - Consultas

### Listar Planejamentos Producao por Revisao
```graphql
query ListarPlanejamentosProducaoPorRevisao($revisaoId: BigInteger!) {
  planejamentosProducaoPorRevisao(revisaoId: $revisaoId) {
    id
    areaId
    valorJaneiro
    valorFevereiro
    valorMarco
    valorAbril
    valorMaio
    valorJunho
    valorJulho
    valorAgosto
    valorSetembro
    valorOutubro
    valorNovembro
    valorDezembro
    totalAnual
  }
}
```

```json
{
  "revisaoId": 1
}
```

### Listar Consumos Especificos por Revisao
```graphql
query ListarConsumosEspecificosPorRevisao($revisaoId: BigInteger!) {
  consumosEspecificosPorRevisao(revisaoId: $revisaoId) {
    id
    areaId
    valorJaneiro
    valorFevereiro
    valorMarco
    valorAbril
    valorMaio
    valorJunho
    valorJulho
    valorAgosto
    valorSetembro
    valorOutubro
    valorNovembro
    valorDezembro
    mediaAnual
  }
}
```

```json
{
  "revisaoId": 1
}
```

### Listar Demandas por Revisao
```graphql
query ListarDemandasPorRevisao($revisaoId: BigInteger!) {
  demandasPorRevisao(revisaoId: $revisaoId) {
    id
    unidadeId
    horario
    valorJaneiro
    valorFevereiro
    valorMarco
    valorAbril
    valorMaio
    valorJunho
    valorJulho
    valorAgosto
    valorSetembro
    valorOutubro
    valorNovembro
    valorDezembro
    mediaAnual
  }
}
```

```json
{
  "revisaoId": 1
}
```

### Listar Tarifas Fornecedor com Calculos
```graphql
query ListarTarifasFornecedorComCalculos($tarifaPlanejamentoId: BigInteger!) {
  tarifasFornecedorPorTarifaPlanejamento(tarifaPlanejamentoId: $tarifaPlanejamentoId) {
    results {
      id
      fornecedorId
      precoBase
      ipcaRealizada
      ipcaProjetado
      montante
      # Calculados:
      ipcaTotal
      preco
      valorMontante
    }
  }
}
```

```json
{
  "tarifaPlanejamentoId": 1
}
```

---

## 10. MAPEAMENTO AREAS (TipoArea)

| ID | Nome Area | TipoArea | Centro Custo | Estado |
|----|-----------|----------|--------------|--------|
| 1 | Filtragem Germano | FILTRAGEM | 11140030 | MG |
| 2 | Mineracao 1 | MINERACAO | 11110060 | MG |
| 3 | Beneficiamento 1 | BENEFICIAMENTO | 11130180 | MG |
| 5 | Beneficiamento 2 | BENEFICIAMENTO | 11131180 | MG |
| 6 | Beneficiamento 3 | BENEFICIAMENTO | 11132180 | MG |
| 8 | Mineroduto 1 | MINERODUTO | 11162040 | MG |
| 9 | Mineroduto 2 | MINERODUTO | 11162090 | MG |
| 10 | Mineroduto 3 | MINERODUTO | 11162120 | MG |
| 11 | Preparacao 1 | PREPARACAO | 12110080 | ES |
| 12 | Preparacao 2 | PREPARACAO | 12111080 | ES |
| 13 | Usina 1 | USINA | 12133040 | ES |
| 14 | Usina 3 | USINA | 12136040 | ES |
| 15 | Usina 4 | USINA | 12138040 | ES |
| 16 | Estocagem | ESTOCAGEM | 12420080 | ES |

**Padrao Centro de Custo:**
- `11xxxxxx` = Minas Gerais
- `12xxxxxx` = Espirito Santo

---

## 11. RESUMO - Fluxo de Dados

```
┌─────────────────────────────────────────────────────────────┐
│                      INPUTS (Usuario)                        │
├─────────────────────────────────────────────────────────────┤
│ PlanejamentoProducao   → ktms por area/mes                  │
│ ConsumoEspecifico      → kWh/tms por area/mes               │
│ Demanda                → kW por unidade/horario/mes         │
│ Geracao                → MWh por unidade geradora/mes       │
│ Distribuidora          → cadastro                           │
│ Fornecedor             → cadastro + precoBase               │
│ TarifaFornecedor       → IPCA + montante                    │
│ TarifaDistribuidora    → tarifas ponta/fora ponta           │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                   CALCULADOS (Sistema)                       │
├─────────────────────────────────────────────────────────────┤
│ Producao = PlanejamentoProducao × 1000                      │
│ ConsumoArea = Producao × ConsumoEspecifico                  │
│   (excecao: Mineracao usa soma dos Beneficiamentos)         │
│ TarifaFornecedor.ipcaTotal = ipcaRealizada + ipcaProjetado  │
│ TarifaFornecedor.preco = precoBase × (1 + ipcaTotal)        │
│ TarifaFornecedor.valorMontante = montante × preco           │
└─────────────────────────────────────────────────────────────┘
```
