package chess.board;

import chess.pieces.Peca; // Precisamos das classes das peças.
import chess.pieces.Peao;
import chess.pieces.Torre;
import chess.pieces.Bispo;
import chess.pieces.Cavalo;
import chess.pieces.Rainha;
import chess.pieces.Rei;

public class Tabuleiro {
    private Peca[][] pecas;
    private boolean jogoAcabou = false; // Adicione esta linha

    public Tabuleiro() {
        this.pecas = new Peca[8][8];
    }

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

    // chess.board.Tabuleiro.java

// ...

    public void imprimirTabuleiro() {
        // Códigos de escape ANSI para cores de fundo e texto
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_TEXT_BLACK = "\u001B[30m";
        final String ANSI_TEXT_WHITE = "\u001B[37m";
        final String ANSI_BG_BLACK = "\u001B[40m";
        final String ANSI_BG_WHITE = "\u001B[47m";

        System.out.println("\n     a   b   c   d   e   f   g   h");
        System.out.println("   ---------------------------------");

        for (int i = 0; i < 8; i++) {
            System.out.print(" " + (8 - i) + " |");
            for (int j = 0; j < 8; j++) {
                String backgroundColor = ((i + j) % 2 == 0) ? ANSI_BG_WHITE : ANSI_BG_BLACK;
                String pieceSymbol = " ";
                String pieceColor = "";

                if (pecas[i][j] != null) {
                    pieceSymbol = pecas[i][j].getSimbolo();
                    // A cor do texto é baseada na cor do fundo para garantir contraste
                    pieceColor = (backgroundColor.equals(ANSI_BG_WHITE)) ? ANSI_TEXT_BLACK : ANSI_TEXT_WHITE;
                }

                System.out.print(backgroundColor + pieceColor + " " + pieceSymbol + " " + ANSI_RESET + "|");
            }
            System.out.println(" " + (8 - i));
            System.out.println("   ---------------------------------");
        }
        System.out.println("     a   b   c   d   e   f   g   h\n");
    }

    // metodo getPeca para comer peças

    public Peca getPeca(int linha, int coluna) {
    // verifica se as coordenadas são válidas.
        if (linha < 0 || linha >= 8 || coluna < 0 || coluna >=8) {
            return null; // fora do tabuleiro
        }
        return this.pecas[linha][coluna];
    }

    public void moverPeca(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        // 1. Pega a peça da posição de origem
        Peca peca = pecas[linhaOrigem][colunaOrigem];

        // 2. Coloca a peça na posição de destino
        pecas[linhaDestino][colunaDestino] = peca;

        // 3. Esvazia a posição de origem
        pecas[linhaOrigem][colunaOrigem] = null;
    }

    public int[] encontrarRei(String corRei) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca peca = this.pecas[i][j];
                if (peca != null && peca.getSimbolo().equalsIgnoreCase("K") && peca.getCor().equals(corRei)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public boolean isReiEmXeque(String corRei) {
        // 1. Encontra a posição do rei
        int[] posicaoRei = encontrarRei(corRei);
        if (posicaoRei == null) {
            return false; // Ou lance uma exceção, o rei não deveria estar fora do tabuleiro
        }
        int linhaRei = posicaoRei[0];
        int colunaRei = posicaoRei[1];

        // 2. Determina a cor do adversário
        String corAdversario = corRei.equals("branco") ? "preto" : "branco";

        // 3. Percorre o tabuleiro para encontrar as peças do adversário
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca pecaAdversaria = this.pecas[i][j];

                // 4. Se a Peça pertence ao adversário, verifica se ela pode atacar o rei
                if (pecaAdversaria != null && pecaAdversaria.getCor().equals(corAdversario)) {
                    // chama o movimentoValido da peça do adversário.
                    // A posição de destino é a do rei.
                    if (pecaAdversaria.movimentoValido(this, i, j, linhaRei, colunaRei)) {
                        System.out.println("Xeque!");
                        return true; // Encontrou uma peça que pode atacar o rei
                    }
                }
            }
        }
        return false; // Nenhum ataque foi encontrado, o rei não está em xeque
    }

    public boolean isXequeMate(String corJogador) {
        // 1. O rei precisa estar em xeque para que seja xeque-mate.
        // O método correto a ser chamado aqui é isReiEmXeque.
        if (!isReiEmXeque(corJogador)){
            return false;
        }

        // 2. Tenta encontrar um movimento que salve o rei.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca pecaOrigem = this.pecas[i][j];

                // Verifica se a peça é do jogador atual
                if (pecaOrigem != null && pecaOrigem.getCor().equals(corJogador)) {
                    // Tenta mover a peça para todas as casas do tabuleiro
                    for (int destinoLinha = 0; destinoLinha < 8; destinoLinha++) {
                        for (int destinoColuna = 0; destinoColuna < 8; destinoColuna++) {
                            // Verifica se o movimento é válido e se a jogada salva o rei
                            if (pecaOrigem.movimentoValido(this, i, j, destinoLinha, destinoColuna)) {

                                // Se encontrar uma jogada legal, usa o tentarMoverPeca para testar
                                if (tentarMoverPeca(i, j, destinoLinha, destinoColuna)) {
                                    // Se for um movimento legal, desfaz a jogada e retorna false.
                                    // Já que tentarMoverPeca retorna true, o movimento já foi feito,
                                    // então precisamos desfazê-lo para que o loop continue.
                                    Peca pecaCapturada = this.pecas[destinoLinha][destinoColuna];
                                    moverPeca(destinoLinha, destinoColuna, i, j);
                                    this.pecas[destinoLinha][destinoColuna] = pecaCapturada;
                                    return false; // Encontrou um movimento de escape
                                }
                            }
                        }
                    }
                }
            }
        }
        // Se nenhuma jogada salvou o rei, é xeque-mate
        return true;
    }

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

        // Se não, o movimento é válido
        return true;
    }

    // Este método irá verificar se o jogo já acabou (xeque-mate).
    // Por enquanto, podemos retornar 'false'.
    public boolean jogoAcabou() {
        return this.jogoAcabou;
    }
    // Adicione este método ao final da classe Tabuleiro
    public void setJogoAcabou(boolean status) {
        this.jogoAcabou = status;
    }
}