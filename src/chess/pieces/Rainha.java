package chess.pieces;

import chess.board.Tabuleiro;

public class Rainha extends Peca {

    public Rainha(String simbolo, String cor) {
        super(simbolo, cor);
    }

    @Override
    public boolean movimentoValido(Tabuleiro tabuleiro, int linhaAtual, int colunaAtual, int linhaDestino, int colunaDestino) {
        // 1. Lógica do movimento da Torre (horizontal ou vertical)
        if (linhaAtual == linhaDestino || colunaAtual == colunaDestino) {
            // Verifica se o caminho está livre para a Torre
            if (linhaAtual == linhaDestino) {
                int direcao = (colunaDestino > colunaAtual) ? 1 : -1;
                for (int i = colunaAtual + direcao; i != colunaDestino; i += direcao) {
                    if (tabuleiro.getPeca(linhaAtual, i) != null) {
                        return false; // Caminho bloqueado
                    }
                }
            } else { // movimento vertical
                int direcao = (linhaDestino > linhaAtual) ? 1 : -1;
                for (int i = linhaAtual + direcao; i != linhaDestino; i += direcao) {
                    if (tabuleiro.getPeca(i, colunaAtual) != null) {
                        return false; // Caminho bloqueado
                    }
                }
            }
            // Verifica a casa de destino
            Peca pecaDestinno = tabuleiro.getPeca(linhaDestino, colunaDestino);
            if (pecaDestinno == null || !pecaDestinno.getCor().equals(this.getCor())) {
                return true; // Movimento válido
            }
        }

        // 2. Verifica se é um movimento de Bispo (diagonal)
        if (Math.abs(linhaAtual - linhaDestino) == Math.abs(colunaAtual - colunaDestino)) {
            // Verifica se o caminho está livre
            int linhaDirecao = (linhaDestino > linhaAtual) ? 1 : -1;
            int colunaDirecao = (colunaDestino > colunaAtual) ? 1 : -1;

            int i = linhaAtual + linhaDirecao;
            int j = colunaAtual + colunaDirecao;

            while (i != linhaDestino && j != colunaDestino) {
                if (tabuleiro.getPeca(i, j) != null) {
                    return false; // Caminho bloqueado
                }
                i += linhaDirecao;
                j += colunaDirecao;
            }

            // Verifica a casa de destino
            Peca pecaDestino = tabuleiro.getPeca(linhaDestino, colunaDestino);
            if (pecaDestino == null || !pecaDestino.getCor().equals(this.getCor())) {
                return true; // Movimento válido
            }
        }

        // Se nenhuma das condições acima for atendida, o movimento é inválido
        return false;
    }
}
