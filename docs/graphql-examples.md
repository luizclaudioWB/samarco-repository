# Exemplos GraphQL - Demanda, Consumo Específico e Geração

Este documento contém exemplos de queries e mutations GraphQL para as entidades criadas.

---

## DEMANDA (kW por unidade/horário)

### Queries

#### Listar Demandas (paginado)
```graphql
query ListarDemandas($page: Int!, $size: Int!) {
  demandas(page: $page, size: $size) {
    items {
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
    total
  }
}
```

**Query Variables:**
```json
{
  "page": 0,
  "size": 10
}
```

#### Buscar Demanda por ID
```graphql
query BuscarDemandaPorId($id: BigInteger!) {
  demandaPorId(id: $id) {
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
  }
}
```

**Query Variables:**
```json
{
  "id": 1
}
```

#### Listar Demandas por Revisão
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
  }
}
```

**Query Variables:**
```json
{
  "revisaoId": 1
}
```

### Mutations

#### Criar Demanda
```graphql
mutation CriarDemanda($input: DemandaDTOInput!) {
  criarDemanda(input: $input) {
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
  }
}
```

**Query Variables:**
```json
{
  "input": {
    "revisaoId": 1,
    "unidadeId": 5,
    "horario": "PONTA",
    "valorJaneiro": 1500.00,
    "valorFevereiro": 1520.00,
    "valorMarco": 1480.00,
    "valorAbril": 1510.00,
    "valorMaio": 1530.00,
    "valorJunho": 1550.00,
    "valorJulho": 1540.00,
    "valorAgosto": 1560.00,
    "valorSetembro": 1570.00,
    "valorOutubro": 1580.00,
    "valorNovembro": 1590.00,
    "valorDezembro": 1600.00
  }
}
```

#### Atualizar Demanda
```graphql
mutation AtualizarDemanda($input: DemandaDTOInput!) {
  atualizarDemanda(input: $input) {
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
  }
}
```

**Query Variables:**
```json
{
  "input": {
    "id": 1,
    "revisaoId": 1,
    "unidadeId": 5,
    "horario": "PONTA",
    "valorJaneiro": 1600.00,
    "valorFevereiro": 1620.00,
    "valorMarco": 1580.00,
    "valorAbril": 1610.00,
    "valorMaio": 1630.00,
    "valorJunho": 1650.00,
    "valorJulho": 1640.00,
    "valorAgosto": 1660.00,
    "valorSetembro": 1670.00,
    "valorOutubro": 1680.00,
    "valorNovembro": 1690.00,
    "valorDezembro": 1700.00
  }
}
```

#### Deletar Demanda
```graphql
mutation DeletarDemanda($id: BigInteger!) {
  deletarDemanda(id: $id)
}
```

**Query Variables:**
```json
{
  "id": 1
}
```

---

## CONSUMO ESPECÍFICO (kWh/tms por área)

### Queries

#### Listar Consumos Específicos (paginado)
```graphql
query ListarConsumosEspecificos($page: Int!, $size: Int!) {
  consumosEspecificos(page: $page, size: $size) {
    items {
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
      mediaMensal
      totalAnual
    }
    total
  }
}
```

**Query Variables:**
```json
{
  "page": 0,
  "size": 10
}
```

#### Buscar Consumo Específico por ID
```graphql
query BuscarConsumoEspecificoPorId($id: BigInteger!) {
  consumoEspecificoPorId(id: $id) {
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
  }
}
```

**Query Variables:**
```json
{
  "id": 1
}
```

#### Listar Consumos Específicos por Revisão
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
  }
}
```

**Query Variables:**
```json
{
  "revisaoId": 1
}
```

### Mutations

#### Criar Consumo Específico
```graphql
mutation CriarConsumoEspecifico($input: ConsumoEspecificoDTOInput!) {
  criarConsumoEspecifico(input: $input) {
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
  }
}
```

**Query Variables:**
```json
{
  "input": {
    "revisaoId": 1,
    "areaId": 3,
    "valorJaneiro": 45.5,
    "valorFevereiro": 46.2,
    "valorMarco": 44.8,
    "valorAbril": 45.1,
    "valorMaio": 46.0,
    "valorJunho": 47.3,
    "valorJulho": 46.8,
    "valorAgosto": 47.5,
    "valorSetembro": 48.0,
    "valorOutubro": 48.5,
    "valorNovembro": 49.0,
    "valorDezembro": 49.5
  }
}
```

#### Atualizar Consumo Específico
```graphql
mutation AtualizarConsumoEspecifico($input: ConsumoEspecificoDTOInput!) {
  atualizarConsumoEspecifico(input: $input) {
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
  }
}
```

**Query Variables:**
```json
{
  "input": {
    "id": 1,
    "revisaoId": 1,
    "areaId": 3,
    "valorJaneiro": 50.0,
    "valorFevereiro": 50.5,
    "valorMarco": 49.0,
    "valorAbril": 49.5,
    "valorMaio": 50.5,
    "valorJunho": 51.0,
    "valorJulho": 51.5,
    "valorAgosto": 52.0,
    "valorSetembro": 52.5,
    "valorOutubro": 53.0,
    "valorNovembro": 53.5,
    "valorDezembro": 54.0
  }
}
```

#### Deletar Consumo Específico
```graphql
mutation DeletarConsumoEspecifico($id: BigInteger!) {
  deletarConsumoEspecifico(id: $id)
}
```

**Query Variables:**
```json
{
  "id": 1
}
```

---

## GERAÇÃO PRÓPRIA (MWh por unidade geradora)

### Queries

#### Listar Gerações (paginado)
```graphql
query ListarGeracoes($page: Int!, $size: Int!) {
  geracoes(page: $page, size: $size) {
    items {
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
    total
  }
}
```

**Query Variables:**
```json
{
  "page": 0,
  "size": 10
}
```

#### Buscar Geração por ID
```graphql
query BuscarGeracaoPorId($id: BigInteger!) {
  geracaoPorId(id: $id) {
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
  }
}
```

**Query Variables:**
```json
{
  "id": 1
}
```

#### Listar Gerações por Revisão
```graphql
query ListarGeracoesPorRevisao($revisaoId: BigInteger!) {
  geracoesPorRevisao(revisaoId: $revisaoId) {
    id
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
  }
}
```

**Query Variables:**
```json
{
  "revisaoId": 1
}
```

#### Listar Gerações por Unidade
```graphql
query ListarGeracoesPorUnidade($unidadeId: BigInteger!) {
  geracoesPorUnidade(unidadeId: $unidadeId) {
    id
    revisaoId
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
  }
}
```

**Query Variables:**
```json
{
  "unidadeId": 2
}
```

### Mutations

#### Criar Geração
```graphql
mutation CriarGeracao($input: GeracaoDTOInput!) {
  criarGeracao(input: $input) {
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
  }
}
```

**Query Variables:**
```json
{
  "input": {
    "revisaoId": 1,
    "unidadeId": 2,
    "valorJaneiro": 12500.00,
    "valorFevereiro": 11800.00,
    "valorMarco": 13200.00,
    "valorAbril": 12900.00,
    "valorMaio": 13500.00,
    "valorJunho": 14000.00,
    "valorJulho": 14500.00,
    "valorAgosto": 15000.00,
    "valorSetembro": 14200.00,
    "valorOutubro": 13800.00,
    "valorNovembro": 13000.00,
    "valorDezembro": 12000.00
  }
}
```

#### Atualizar Geração
```graphql
mutation AtualizarGeracao($input: GeracaoDTOInput!) {
  atualizarGeracao(input: $input) {
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
  }
}
```

**Query Variables:**
```json
{
  "input": {
    "id": 1,
    "revisaoId": 1,
    "unidadeId": 2,
    "valorJaneiro": 13000.00,
    "valorFevereiro": 12300.00,
    "valorMarco": 13700.00,
    "valorAbril": 13400.00,
    "valorMaio": 14000.00,
    "valorJunho": 14500.00,
    "valorJulho": 15000.00,
    "valorAgosto": 15500.00,
    "valorSetembro": 14700.00,
    "valorOutubro": 14300.00,
    "valorNovembro": 13500.00,
    "valorDezembro": 12500.00
  }
}
```

#### Deletar Geração
```graphql
mutation DeletarGeracao($id: BigInteger!) {
  deletarGeracao(id: $id)
}
```

**Query Variables:**
```json
{
  "id": 1
}
```

---

## Notas Importantes

### Tipos de Horário (TipoHorario)
Para o campo `horario` em Demanda, os valores válidos são:
- `PONTA` - Horário de ponta (maior demanda)
- `FORA_PONTA` - Horário fora de ponta

### Validações de Negócio

1. **Revisão Finalizada**: Nenhuma operação de criação, atualização ou exclusão é permitida em revisões finalizadas (`isFinished = true`).

2. **Unicidade**:
   - **Demanda**: Combinação única de `revisaoId + unidadeId + horario`
   - **Consumo Específico**: Combinação única de `revisaoId + areaId`
   - **Geração**: Combinação única de `revisaoId + unidadeId`

3. **Geração - Unidade Geradora**: Apenas unidades com `unidadeGeradora = true` podem ter registros de geração.

### Acessando o GraphQL Playground

O playground GraphQL está disponível em:
```
http://localhost:8080/q/graphql-ui
```

### Autenticação

Todas as operações requerem autenticação. Adicione o header de autorização:
```
Authorization: Bearer <seu_token_jwt>
```
