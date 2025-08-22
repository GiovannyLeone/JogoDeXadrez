package chess.pieces;

import chess.board.Tabuleiro;

public class Bispo extends Peca{

    public Bispo(String simbolo, String cor) {
        super(simbolo, cor);
    }

    @Override
    public boolean movimentoValido(Tabuleiro tabuleiro, int linhaAtual, int colunaAtual, int linhaDestino, int colunaDestino) {
        // 1. O movimento deve ser na diagonal
        // A Diferença absoluta das linhas deve ser igual à diferença absoluta das colunas.

        if (Math.abs(linhaAtual - linhaDestino) != Math.abs(colunaAtual - colunaDestino)) {
            return false;
        }

        // 2. O caminho entre a origem e o destino deve estar livre.
        int linhaDirecao = (linhaDestino > linhaAtual)  ? 1 : -1;
        int colunaDirecao = (colunaDestino > colunaAtual) ? 1 : -1;

        int i = linhaAtual + linhaDirecao;
        int j = colunaAtual + colunaDirecao;

        while (i != linhaDestino && j != colunaDestino) {
            if (tabuleiro.getPeca(i, j) != null) {
                return false; // há uma peça no caminho
            }
            i += linhaDirecao;
            j += colunaDirecao;
        }

        // 3. Verifica a casa de destino
        Peca pecaDestino = tabuleiro.getPeca(linhaDestino, colunaDestino);

        // O destino deve ser una casa vaia ou com uma peça inimiga.
        if (pecaDestino == null || !pecaDestino.getCor().equals(this.getCor())) {
            return true;
        }

        return false;
    }

}
