package chess;

import chess.board.Tabuleiro;
import chess.pieces.Peca;

import java.util.Scanner;

public class JogoDeXadrez {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Cria uma instância do nosso tabuleiro.
        Tabuleiro tabuleiro = new Tabuleiro();

        // Chamamos um método para inicializar o tabuleiro com as peças.
        tabuleiro.inicializarTabuleiro(); // Preenche o tabuleiro com as peças

        String jogadorAtual = "branco";

        // Loop principal do jogo.
        while (!tabuleiro.jogoAcabou()) {
            tabuleiro.imprimirTabuleiro();
            System.out.println("Vez do jogador " + jogadorAtual + ".");
            System.out.print("Digite sua jogada (ex: a2 a4): ");
            String jogada = scanner.nextLine();

            // 1. Processa a jogada
            String[] partes = jogada.split(" ");
            if (partes.length == 2) {
                String origem = partes[0];
                String destino = partes[1];

                int linhaOrigem = '8' - origem.charAt(1);
                int colunaOrigem = origem.charAt(0) - 'a';

                int linhaDestino = '8' - destino.charAt(1);
                int colunaDestino = destino.charAt(0) - 'a';

                // Adicione uma verificação para garantir que as coordenadas são válidas
                if (linhaOrigem < 0 || linhaOrigem > 7 || colunaOrigem < 0 || colunaOrigem > 7 ||
                        linhaDestino < 0 || linhaDestino > 7 || colunaDestino < 0 || colunaDestino > 7) {
                    System.out.println("Coordenadas fora do tabuleiro. Tente novamente.");
                    continue; // Pula para a próxima iteração do loop
                }

                // 2. Obtém a peça da posição de origem
                Peca peca = tabuleiro.getPeca(linhaOrigem, colunaOrigem);

                // 3. Valida a jogada
                if (peca != null && peca.getCor().equals(jogadorAtual)) {
                    if (peca.movimentoValido(tabuleiro, linhaOrigem, colunaOrigem, linhaDestino, colunaDestino)) {

                        // Depois, tenta mover a peça, verificando se isso não causa um xeque no próprio rei
                        if (tabuleiro.tentarMoverPeca(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino)) {
                            System.out.println("Jogada válida!");
                            jogadorAtual = (jogadorAtual.equals("branco")) ? "preto" : "branco";
                        } else {
                            System.out.println("Movimento ilegal. Você não pode colocar seu prórpio rei em xeque.");
                        }

                    } else {
                        System.out.println("Movimento inválido para esta peça. Tente novamente.");
                    }
                } else {
                    System.out.println("Não há uma peça sua na posição de origem. Tente novamente.");
                }
            } else {
                System.out.println("Formato de jogada inválido. Use o formato 'a2 a4'.");
            }

            // ao final de um turno válido (após o jogador ter se movido),
            // verifica se o jogador adversário foi colocado em xeque-mate.
            if (tabuleiro.isXequeMate(jogadorAtual.equals("branco") ? "preto" : "branco")) {
                tabuleiro.imprimirTabuleiro();
                System.out.println("XEQUE-MATE! O jogador " + jogadorAtual + " venceu!");
                tabuleiro.setJogoAcabou(true); // TODO FAZER O METODO PARA ISSO
            } else if (tabuleiro.isReiEmXeque(jogadorAtual.equals("branco") ? "preto" : "branco")) {
                System.out.println("XEQUE!");
            }
        }
        scanner.close();
        System.out.println("O jogo acabou!");
    }
}
