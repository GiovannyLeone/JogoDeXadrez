package chess.board;

import chess.pieces.Peca; // Precisamos das classes das peças.
import chess.pieces.Peao;
import chess.pieces.Torre;
import chess.pieces.Bispo;
import chess.pieces.Cavalo;
import chess.pieces.Rainha;
import chess.pieces.Rei;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    // A matriz 8x8 que armazena as peças do tabuleiro
    private Peca[][] pecas;
    // Variável que controla se o jogo terminou
    private boolean jogoAcabou = false;

    /**
     * Construtor da classe Tabuleiro.
     * Inicializa a matriz de peças.
     */
    public Tabuleiro() {
        this.pecas = new Peca[8][8];
    }

    /**
     * Posiciona todas as peças do xadrez nas suas posições iniciais.
     */

    public void inicializarTabuleiro() {
        // ADICIONANDO PEOES
        for (int j = 0; j < 8; j++) {
            this.pecas[1][j] = new Peao("p", "preto"); // Peões pretos na linha 1
            this.pecas[6][j] = new Peao("P", "branco"); // Peões brancos na linha 6
        }

        // ADICIONANDO TORRES
        this.pecas[0][0] = new Torre("t", "preto");
        this.pecas[0][7] = new Torre("t", "preto");
        this.pecas[7][0] = new Torre("T", "branco");
        this.pecas[7][7] = new Torre("T", "branco");


        // ADICIONANDO BISPO
        this.pecas[0][2] = new Bispo("b", "preto");
        this.pecas[0][5] = new Bispo("b", "preto");
        this.pecas[7][2] = new Bispo("B", "branco");
        this.pecas[7][5] = new Bispo("B", "branco");

        // ADICIONANDO CAVALO
        this.pecas[0][1] = new Cavalo("c", "preto");
        this.pecas[0][6] = new Cavalo("c", "preto");
        this.pecas[7][1] = new Cavalo("C", "branco");
        this.pecas[7][6] = new Cavalo("C", "branco");

        // ADICIONANDO RAINHA
        this.pecas[0][3] = new Rainha("q", "preto");
        this.pecas[7][3] = new Rainha("Q", "branco");

        // ADICIONANDO REI
        this.pecas[0][4] = new Rei("k", "preto");
        this.pecas[7][4] = new Rei("K", "branco");


        //
    }

    /**
     * Imprime o tabuleiro no console, com cores e formatação.
     * Destaca as casas para onde o rei não pode se mover se estiver em xeque.
     *
     * @param corJogadorAtual A cor do jogador na rodada atual.
     */

    public void imprimirTabuleiro(String corJogadorAtual) {
        // Códigos de escape ANSI para cores de fundo e texto
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_TEXT_BLACK = "\u001B[30m";
        final String ANSI_TEXT_WHITE = "\u001B[37m";
        final String ANSI_BG_BLACK = "\u001B[40m";
        final String ANSI_BG_WHITE = "\u001B[47m";
        final String ANSI_BG_RED = "\u001B[41m"; // Fundo Vermelho

        boolean reiEmXeque = isReiEmXeque(corJogadorAtual);

        // Se o rei estiver em xeque, encontra os movimentos ilegais para destaque
        List<int[]> movimentosIlegaisRei = new ArrayList<>();
        if (reiEmXeque) {
            movimentosIlegaisRei = getMovimentosIlegaisRei(corJogadorAtual);
        }

        System.out.println("\n     a   b   c   d   e   f   g   h");
        System.out.println("   ---------------------------------");

        for (int i = 0; i < 8; i++) {
            System.out.print(" " + (8 - i) + " |");
            for (int j = 0; j < 8; j++) {
                String backgroundColor = ((i + j) % 2 == 0) ? ANSI_BG_WHITE : ANSI_BG_BLACK;
                String pieceSymbol = " ";
                String pieceColor = "";

                // Verifica se há uma peça na casa para imprimi-la
                if (pecas[i][j] != null) {
                    pieceSymbol = pecas[i][j].getSimbolo();
                    pieceColor = (backgroundColor.equals(ANSI_BG_WHITE)) ? ANSI_TEXT_BLACK : ANSI_TEXT_WHITE;
                }

                // Lógica para destacar as casas ilegais para o Rei em xeque
                boolean isMovimentoIlegal = false;
                for (int[] coords : movimentosIlegaisRei) {
                    if (coords[0] == i && coords[1] == j) {
                        isMovimentoIlegal = true;
                        break;
                    }
                }
                if (isMovimentoIlegal) {
                    backgroundColor = ANSI_BG_RED;
                    pieceColor = ANSI_TEXT_WHITE; // Garante que o texto seja visível no fundo vermelho
                }

                System.out.print(backgroundColor + pieceColor + " " + pieceSymbol + " " + ANSI_RESET + "|");
            }
            System.out.println(" " + (8 - i));
            System.out.println("   ---------------------------------");
        }
        System.out.println("     a   b   c   d   e   f   g   h\n");
    }

    /**
     * Retorna a peça em uma posição específica do tabuleiro.
     *
     * @param linha  A linha (0-7).
     * @param coluna A coluna (0-7).
     * @return A peça na posição ou null se estiver fora do tabuleiro.
     */
    public Peca getPeca(int linha, int coluna) {
        if (linha < 0 || linha >= 8 || coluna < 0 || coluna >= 8) {
            return null;
        }
        return this.pecas[linha][coluna];
    }

    /**
     * Move uma peça da posição de origem para a de destino.
     *
     * @param linhaOrigem   Linha inicial da peça.
     * @param colunaOrigem  Coluna inicial da peça.
     * @param linhaDestino  Linha final da peça.
     * @param colunaDestino Coluna final da peça.
     */
    public void moverPeca(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        Peca peca = pecas[linhaOrigem][colunaOrigem];
        pecas[linhaDestino][colunaDestino] = peca;
        pecas[linhaOrigem][colunaOrigem] = null;
    }

    /**
     * Encontra e retorna a posição do rei de uma determinada cor.
     *
     * @param corRei A cor do rei a ser encontrada.
     * @return Um array de inteiros {linha, coluna} ou null se não for encontrado.
     */
    public int[] encontrarRei(String corRei) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca peca = this.pecas[i][j];
                // Verifica o símbolo do rei e a cor
                if (peca != null && peca.getSimbolo().equalsIgnoreCase("k") && peca.getCor().equals(corRei)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * Verifica se o rei de uma determinada cor está em xeque.
     *
     * @param corRei A cor do rei a ser verificada.
     * @return True se o rei estiver em xeque, false caso contrário.
     */
    public boolean isReiEmXeque(String corRei) {
        int[] posicaoRei = encontrarRei(corRei);
        if (posicaoRei == null) {
            return false;
        }
        int linhaRei = posicaoRei[0];
        int colunaRei = posicaoRei[1];
        String corAdversario = corRei.equals("branco") ? "preto" : "branco";

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca pecaAdversaria = this.pecas[i][j];
                if (pecaAdversaria != null && pecaAdversaria.getCor().equals(corAdversario)) {
                    if (pecaAdversaria.movimentoValido(this, i, j, linhaRei, colunaRei)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifica se um jogador está em xeque-mate.
     *
     * @param corJogador A cor do jogador a ser verificada.
     * @return True se for xeque-mate, false caso contrário.
     */
    public boolean isXequeMate(String corJogador) {
        if (!isReiEmXeque(corJogador)) {
            return false;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca pecaOrigem = this.pecas[i][j];

                if (pecaOrigem != null && pecaOrigem.getCor().equals(corJogador)) {
                    for (int destinoLinha = 0; destinoLinha < 8; destinoLinha++) {
                        for (int destinoColuna = 0; destinoColuna < 8; destinoColuna++) {
                            if (pecaOrigem.movimentoValido(this, i, j, destinoLinha, destinoColuna)) {
                                // Simula o movimento
                                Peca pecaCapturada = getPeca(destinoLinha, destinoColuna);
                                moverPeca(i, j, destinoLinha, destinoColuna);

                                // Verifica se o rei saiu do xeque após o movimento
                                boolean aindaEmXeque = isReiEmXeque(corJogador);

                                // Desfaz o movimento para não alterar o estado do tabuleiro
                                moverPeca(destinoLinha, destinoColuna, i, j);
                                pecas[destinoLinha][destinoColuna] = pecaCapturada;

                                if (!aindaEmXeque) {
                                    return false; // Encontrou um movimento que salva o rei
                                }
                            }
                        }
                    }
                }
            }
        }
        return true; // Nenhum movimento salvou o rei
    }

    /**
     * Simula um movimento e verifica se ele é legal (não deixa o próprio rei em xeque).
     * Se for legal, o movimento é mantido. Se for ilegal, é desfeito.
     *
     * @param linhaOrigem   Linha inicial.
     * @param colunaOrigem  Coluna inicial.
     * @param linhaDestino  Linha final.
     * @param colunaDestino Coluna final.
     * @return True se o movimento for seguro, false caso contrário.
     */
    public boolean tentarMoverPeca(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        // Pega a peça que será movida e a peça que está no destino (se houver)
        Peca pecaMovida = pecas[linhaOrigem][colunaOrigem];
        Peca pecaCapturada = pecas[linhaDestino][colunaDestino];

        // simula o movimento
        moverPeca(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);

        // verifica se o seu próprio rei ficou em xeque após a simulação
        if (isReiEmXeque(pecaMovida.getCor())) {
            // se sim, desfaz o movimento (reverte o tabuleiro ao estado original)
            pecas[linhaOrigem][colunaOrigem] = pecaMovida;
            pecas[linhaDestino][colunaDestino] = pecaCapturada;
            return false; // Retorna falso para indicar que a jogada é ilegal
        }

        // Se o movimento é seguro, o tabuleiro já está no estado final, então não é necessário reverter
        return true;
    }

    /**
     * Retorna a lista de movimentos ilegais para o rei em xeque.
     * Usado para a funcionalidade de destaque.
     *
     * @param corRei A cor do rei em xeque.
     * @return Uma lista de coordenadas de movimentos ilegais.
     */
    private List<int[]> getMovimentosIlegaisRei(String corRei) {
        List<int[]> movimentosIlegais = new ArrayList<>();
        int[] posicaoRei = encontrarRei(corRei);

        if (posicaoRei == null) {
            return movimentosIlegais;
        }

        int linhaRei = posicaoRei[0];
        int colunaRei = posicaoRei[1];
        Peca rei = getPeca(linhaRei, colunaRei);

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int destinoLinha = linhaRei + i;
                int destinoColuna = colunaRei + j;

                if (destinoLinha >= 0 && destinoLinha < 8 && destinoColuna >= 0 && destinoColuna < 8) {
                    if (rei.movimentoValido(this, linhaRei, colunaRei, destinoLinha, destinoColuna)) {
                        Peca pecaCapturada = getPeca(destinoLinha, destinoColuna);
                        moverPeca(linhaRei, colunaRei, destinoLinha, destinoColuna);

                        if (isReiEmXeque(corRei)) {
                            movimentosIlegais.add(new int[]{destinoLinha, destinoColuna});
                        }

                        moverPeca(destinoLinha, destinoColuna, linhaRei, colunaRei);
                        pecas[destinoLinha][destinoColuna] = pecaCapturada;
                    }
                }
            }
        }
        return movimentosIlegais;
    }

    /**
     * Verifica se o jogo já acabou.
     *
     * @return True se o jogo tiver sido encerrado, false caso contrário.
     */
    public boolean jogoAcabou() {
        return this.jogoAcabou;
    }

    /**
     * Define o estado do jogo (terminado ou não).
     *
     * @param status True para encerrar o jogo.
     */
    public void setJogoAcabou(boolean status) {
        this.jogoAcabou = status;
    }
}