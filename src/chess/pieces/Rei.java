package chess.pieces;

import chess.board.Tabuleiro;

public class Rei extends Peca{
    public Rei(String simbolo, String cor) {
        super(simbolo, cor);
    }

    @Override
    public boolean movimentoValido(Tabuleiro tabuleiro, int linhaAtual, int colunaAtual, int linhaDestino, int colunaDestino) {
        // TODO LOGICA DO MOVIMENTO
        int diffLinha = Math.abs(linhaAtual - linhaDestino);
        int diffColuna = Math.abs(colunaAtual - colunaDestino);

        // O movimento do Rei deve ser de apenas uma casa em qualquer direção.
        if (diffLinha <= 1 && diffColuna <= 1) {
            // A casa de destino não pode ser a mesma que a de origem.
            if (diffLinha == 0 && diffColuna == 0) {
                return false;
            }

            // O Rei não pode capturar uma peça da mesma cor.
            Peca pecaDestino = tabuleiro.getPeca(linhaDestino, colunaDestino);
            if (pecaDestino == null || !pecaDestino.getCor().equals(this.getCor())) {
                return true;
            }
        }

        return false;
    }
}
