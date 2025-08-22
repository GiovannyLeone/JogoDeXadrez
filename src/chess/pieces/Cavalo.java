package chess.pieces;

import chess.board.Tabuleiro;

public class Cavalo extends Peca {

    public Cavalo(String simbolo, String cor) {
        super(simbolo, cor);
    }

    @Override
    public boolean movimentoValido(Tabuleiro tabuleiro, int linhaAtual, int colunaAtual, int linhaDestino, int colunaDestino) {
        // A diferença nas linhas deve ser 2 e nas colunas 1, ou vice-versa.
        int diffLinha = Math.abs(linhaAtual - linhaDestino);
        int diffColuna = Math.abs(colunaAtual - colunaDestino);

        // o movimento do cavalo deve ser um "L"
        if (diffLinha == 2 && diffColuna == 1 || (diffLinha == 1 && diffColuna == 2)) {
            // o cavalo não pode capturar uma peça da mesma cor
            Peca pecaDestino = tabuleiro.getPeca(linhaDestino, colunaDestino);
            if (pecaDestino == null || !pecaDestino.getCor().equals(this.getCor())) {
                return true;
            }
        }
        return false;
    }

}
