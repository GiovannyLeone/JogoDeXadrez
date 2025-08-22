package chess.pieces;

import chess.board.Tabuleiro;

public class Torre extends Peca {
    public Torre(String simbolo, String cor) {
        super(simbolo, cor);
    }

    @Override
    public boolean movimentoValido(Tabuleiro tabuleiro, int linhaAtual, int colunaAtual, int linhaDestino, int colunaDestino) {
        // 1. O movimento deve ser em linha reta (horizontal ou vertical).
        // Linha de destino igual à linha de origem ou coluna de destino igual à coluna de origem.
        if (linhaAtual != linhaDestino && colunaAtual != colunaDestino) {
            return false; // Não é um movimento reto
        }

        // 2. O caminho entre a origem e o destino deve estar livre.
        // Movimento horizontal
        if (linhaAtual == linhaDestino) {
            int direcao = (colunaDestino > colunaAtual) ? 1 : -1;
            for (int i = colunaAtual + direcao; i != colunaDestino; i += direcao) {
                if (tabuleiro.getPeca(linhaAtual, i) != null) {
                    return false; // Há uma peça no caminho
                }
            }
        }
        //Movimento vertical
        else { // colunaAtual == colunaDestino
            int direcao = (linhaDestino > linhaAtual) ? 1 : -1;
            for (int i = linhaAtual + direcao; i != linhaDestino; i += direcao) {
                if (tabuleiro.getPeca(i, colunaAtual) != null) {
                    return false; // Há uma peça no caminho
                }
            }
        }

        // 3. Verifica a casa de destino
        Peca pecaDestino = tabuleiro.getPeca(linhaDestino, colunaDestino);

        // o destino deve ser uma casa vazaio ou com uma peça inimiga.
        // Se a peça de destino é nula (casa vazia) ou a cor é diferente, o movimento é válido
        if (pecaDestino == null || !pecaDestino.getCor().equals(this.getCor())) {
            return true;
        }

        return false;
    }
}
