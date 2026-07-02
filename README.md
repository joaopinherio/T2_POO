# T2_POO

# Relatório

**Colaboradores :** João Pinheiro de Paula , Rafael Inacio Ledur
## 1. Diagrama de Classes (arquivo astah e pdf estão no anexo do projeto)

## 2. Coleções de Dados

O sistema usa coleções diferentes dependendo da necessidade de cada entidade. A tabela resume onde cada uma é usada:

| Coleção      | Tipo                           | Classe / Local   | Uso                                                                     |
| ------------ | ------------------------------ | ---------------- | ----------------------------------------------------------------------- |
| `catalogo`   | `ArrayList<Jogo>`              | `Catalogo`       | Guarda todos os jogos cadastrados, ordenados por código                 |
| `cadastro`   | `ArrayList<Cliente>`           | `Clientela`      | Guarda os clientes (Individual/Corporativo), ordenados por número       |
| `pagamentos` | `ArrayList<FormaPagamento>`    | `LogPagamentos`  | Guarda os pagamentos (PIX/CartaoCredito), ordenados por código          |
| `quadro`     | `Queue<Contrato>` (LinkedList) | `QuadroContrato` | Fila de contratos, exigência do trabalho de que Contrato fosse uma fila |

### ArrayList (Catalogo, Clientela, LogPagamentos)

Usado para as três entidades que precisam de acesso indexado e ordenação simples. Sempre que um item é adicionado (`addJogo`, `addCliente`, `addPagamento`), a lista é reordenada com `Collections.sort()`/`List.sort()` usando `Comparator.comparing()` pelo atributo identificador (código, número ou cod), garantindo que a Grid do Vaadin sempre mostre os dados ordenados.

### Queue (QuadroContrato)

`Contrato` foi implementado como fila (`Queue<Contrato>`, usando `LinkedList` como implementação concreta) por exigência do enunciado do trabalho. A inserção usa `offer()` e a remoção usa `remove(Contrato)`. Isso trouxe uma limitação: `Queue` não tem `sort()` nativo, então para ordenar por ID é necessário fazer o cast para `List` (`Collections.sort((List<Contrato>) quadro, ...)`), o que funciona porque `LinkedList` implementa as duas interfaces.

### Streams (List/Set temporários)

Para os relatórios e telas de consulta (maior valor, filtragem por cliente, etc.), usamos a Stream API para gerar listas imutáveis (`.toList()`) ou filtradas (`Collectors.toList()`) sem alterar a coleção original. O método `calculaMontanteEachCliente()` usa `Collectors.toSet()` para pegar os clientes únicos que têm contrato antes de somar o valor de cada um, evitando somar o mesmo cliente mais de uma vez.

## 3. Armazenamento (Persistência) de Dados

A persistência é feita em arquivos de texto no formato CSV, com ponto e vírgula (`;`) como separador de campo, sem uso de serialização binária nem de bibliotecas prontas para CSV.

### Leitura

O método de leitura é o mesmo (mesma "fórmula") em todas as classes de coleção (`inicializaJogos`, `inicializaClientes`, `inicializaPagamentos`, `inicializaContratos`), mudando apenas os índices de cada campo:

- O arquivo é lido linha a linha com `BufferedReader`.
- Cada linha é quebrada pelo separador `;` e todos os valores são concatenados em uma única `StringBuilder`, separados por vírgula.
- A string final é quebrada de novo com `.split(",")`, gerando um array plano com todos os campos do arquivo inteiro (cabeçalho + todos os registros em sequência).
- Um loop percorre esse array pulando o cabeçalho (offset inicial fixo) e, para cada registro, lê os campos na ordem certa (incrementando um contador `count`), até chegar no penúltimo/último registro (`data.length - N`, onde N é o tamanho do cabeçalho).

Essa abordagem surgiu depois de um problema inicial: tentamos usar leitura orientada a objeto (casting antecipado de classes, como se fosse um `.bin`), o que gerava `IOException`. O problema não eram os imports, e sim a forma de ler o arquivo — trocamos para leitura de texto puro com `BufferedReader` + `split`, que resolveu o problema. Essa "fórmula" foi replicada para os outros três arquivos CSV, mudando apenas os índices lidos.

Um caso à parte foi `FormaPagamento`: o CSV traz também o número do cliente dono daquele pagamento, que não é atributo da classe. Por isso, na leitura, associamos o pagamento ao cliente correspondente (`clientela.pesquisaNum(numeroCliente)`) logo depois de instanciar o objeto, ao invés de guardar esse vínculo numa estrutura de Map separada.

### Escrita

Cada classe de coleção (`Catalogo`, `Clientela`, `LogPagamentos`, `QuadroContrato`) tem um método `salvaX(String pathS)` que escreve um cabeçalho fixo e depois uma linha por item, chamando um método `getCsv()` na respectiva classe de domínio (`Jogo`, `Cliente`, `FormaPagamento`, `Contrato`) responsável por montar a própria linha CSV do objeto.

### Arquivos utilizados

- `JOGOSINICIAL.CSV`, `CLIENTESINICIAL.CSV`, `FORMASPAGAMENTOINICIAL.CSV`, `CONTRATOSINICIAL.CSV` — carga inicial fixa, lida automaticamente pela `MainView` ao abrir o sistema, caso as coleções ainda estejam vazias.
- **Salvar/Carregar dados do usuário** — na `MainView`, os botões "Salvar dados" e "Carregar dados" pedem um nome de arquivo (`fileNameField`/`fileNameLoad`) e usam esse nome como prefixo, concatenando os sufixos `CLIENTES`, `JOGOS`, `PAGAMENTOS` e `CONTRATOS` para gerar/ler os quatro arquivos correspondentes.

Esse mecanismo garante a persistência exigida pelo trabalho: ao fechar o sistema (Finalizar Sistema) e abrir de novo, os dados salvos por esses arquivos continuam disponíveis, desde que o usuário use a opção de salvar antes de sair.