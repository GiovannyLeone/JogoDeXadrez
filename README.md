# Jogo de Xadrez em Java (Console)

## ♟️ Visão Geral do Projeto

Este é um jogo de xadrez completo, desenvolvido em Java, com uma interface de linha de comando. O objetivo principal deste projeto foi aplicar e aprofundar os conceitos de **Programação Orientada a Objetos (POO)**, como herança, polimorfismo e encapsulamento, para criar um motor de jogo robusto e modular.

O jogo permite a interação de dois jogadores (branco e preto) no mesmo terminal, com validação de movimentos e regras essenciais do xadrez.

---

## ✨ Funcionalidades Implementadas

* **Movimento de Todas as Peças:** Lógica completa para Peão, Torre, Cavalo, Bispo, Rainha e Rei.
* **Detecção de Xeque e Xeque-Mate:** O jogo identifica quando um rei está em xeque e encerra a partida em caso de xeque-mate.
* **Regras de Movimento do Rei:** Impede que um jogador faça uma jogada que coloque (ou mantenha) seu próprio rei em xeque.
* **Interface Amigável:** O tabuleiro é impresso no console com cores e formatação, facilitando a visualização.
* **Feedback ao Jogador:** Mensagens claras sobre jogadas válidas, movimentos ilegais, xeque e xeque-mate.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (versão **JDK 21**).
* **Interface:** Console (Terminal).
* **Recursos Visuais:** Códigos de Escape **ANSI** para formatação e cores.

---

## 🚀 Como Executar o Jogo

Para rodar este projeto, você precisará ter o **JDK 21** ou superior instalado em sua máquina.

1.  **Clone o Repositório:** Se você ainda não o fez, clone o projeto do GitHub para sua máquina.
2.  **Abra na sua IDE:** Importe o projeto para sua IDE favorita (IntelliJ IDEA, VS Code ou Eclipse).
3.  **Execute a Classe Principal:** Navegue até a classe `JogoDeXadrez.java` no pacote `chess` e execute o método `main`.

A partir daí, o jogo iniciará no terminal e pedirá as jogadas.

---

## 🏗️ Arquitetura e Design

O projeto foi construído com base em princípios de POO para garantir escalabilidade e organização.

* **Design Modular:** O código está organizado em pacotes lógicos:
    * `chess.pieces`: Contém as classes que representam cada peça.
    * `chess.board`: Gerencia o estado do tabuleiro e a lógica de jogo.
    * `chess`: Contém a classe principal que gerencia o fluxo da partida.

* **Herança:** Existe uma classe abstrata `Peca` que define o comportamento básico de uma peça (cor, símbolo) e um método abstrato `movimentoValido()`. Todas as peças específicas (`Peao`, `Torre`, `Rei`, etc.) herdam dessa classe e implementam sua lógica de movimento.

* **Polimorfismo:** O método `movimentoValido()` é um exemplo perfeito de polimorfismo. A chamada para `peca.movimentoValido()` executa o código específico da peça (por exemplo, a lógica do `Bispo` ou da `Rainha`) sem que o motor do jogo precise saber qual peça está sendo movida.

---

## 🔮 Próximos Passos e Possíveis Melhorias

Este projeto pode ser expandido com as seguintes funcionalidades:

* **Regras Especiais:**
    * `Roque` (Castling)
    * `Promoção do Peão` (Pawn Promotion)
    * `En Passant`
* **Interface Gráfica (GUI):** A lógica do motor do jogo está sólida, permitindo que você construa uma interface gráfica com **JavaFX** ou **Swing** sobre a base existente.
* **Salvamento de Partidas:** Adicionar a funcionalidade de salvar e carregar o estado do jogo.