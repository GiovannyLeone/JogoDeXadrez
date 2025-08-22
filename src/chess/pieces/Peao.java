package chess.pieces;

import chess.board.Tabuleiro;

public class Peao extends Peca {

    public Peao(String simbolo, String cor) {
        super(simbolo, cor);
    }

    @Override
    public boolean movimentoValido(Tabuleiro tabuleiro, int linhaAtual, int colunaAtual, int linhaDestino, int colunaDestino) {
        // ... agora a lógica pode usar linhaAtual e colunaAtual

        // Lógica para Peão Branco (início)
        if (this.getCor().equals("branco")) {
            // Avanço de uma casa
            if (linhaDestino == linhaAtual - 1 && colunaDestino == colunaAtual && tabuleiro.getPeca(linhaDestino, colunaDestino) == null) {
                return true;
            }
            // Avanço de duas casas (apenas na primeira jogada, linha 6)
            if (linhaAtual == 6 && linhaDestino == linhaAtual - 2 && colunaDestino == colunaAtual && tabuleiro.getPeca(linhaDestino, colunaDestino) == null) {
                if (tabuleiro.getPeca(linhaAtual - 1, colunaAtual) == null) {
                    return true;
                }
            }
            // Captura na diagonal
            if (linhaDestino == linhaAtual - 1 && Math.abs(colunaDestino - colunaAtual) == 1) {
                Peca pecaDestino = tabuleiro.getPeca(linhaDestino, colunaDestino);
                if (pecaDestino != null && !pecaDestino.getCor().equals(this.getCor())) {
                    return true;
                }
            }
        }

        // Lógica para Peão Preto (inversa)
        if (this.getCor().equals("preto")) {
            // Avanço de uma casa
            if (linhaDestino == linhaAtual + 1 && colunaDestino == colunaAtual && tabuleiro.getPeca(linhaDestino, colunaDestino) == null) {
                return true;
            }
            // Avanço de duas casas (apenas na primeira jogada, linha 1)
            if (linhaAtual == 1 && linhaDestino == linhaAtual + 2 && colunaDestino == colunaAtual && tabuleiro.getPeca(linhaDestino, colunaDestino) == null) {
                if (tabuleiro.getPeca(linhaAtual + 1, colunaAtual) == null) {
                    return true;
                }
            }
            // Captura na diagonal
            if (linhaDestino == linhaAtual + 1 && Math.abs(colunaDestino - colunaAtual) == 1) {
                Peca pecaDestino = tabuleiro.getPeca(linhaDestino, colunaDestino);
                if (pecaDestino != null && !pecaDestino.getCor().equals(this.getCor())) {
                    return true;
                }
            }
        }

        return false;
    }
}