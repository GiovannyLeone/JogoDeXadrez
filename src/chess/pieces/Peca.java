package chess.pieces;

import chess.board.Tabuleiro;

public abstract class Peca {
    // Símbolo para representação no console (e.g., 'p' para peão preto, 'T' para torre branca)
    protected String simbolo;

    // Cor da peça, que pode ser "branco" ou "preto"
    protected String cor;

    public Peca(String simbolo, String cor) {
        this.simbolo = simbolo;
        this.cor = cor;
    }

    public String getSimbolo() {
        return this.simbolo;
    }

    public String getCor() {
        return this.cor;
    }

    // Este é o método abstrato que cada peça terá que implementar.
    // Ele é a base da lógica do jogo e irá verificar se um movimento
    // para uma determinada linha e coluna é válido para aquela peça.
    public abstract boolean movimentoValido(Tabuleiro tabuleiro, int linhaAtual, int colunaAtual, int linhaDestino, int colunaDestino);
}