package br.ufjf.dcc.dcc025.sudoku.Jogo;

public class ValidarJogada {
    
    protected static boolean validarLinha(Celula[][] tabuleiro, int linha, int coluna, int numero){
        for(int j = 0; j < 9; j++){
            if(j != coluna && tabuleiro[linha][j].getNumero() == numero){
                return false;
            }
        }
        return true;
    }
    
    protected static boolean validarColuna(Celula[][] tabuleiro, int linha, int coluna, int numero){
        for(int i = 0; i < 9; i++){
            if(i != linha && tabuleiro[i][coluna].getNumero() == numero){
                return false;
            }
        }
        return true;
    }
    
    protected static boolean validarMatriz(Celula[][] tabuleiro, int linha, int coluna, int numero){
        final int inicioLinha = (linha/3)*3;
        final int inicioColuna = (coluna/3)*3;
        
        for(int i = inicioLinha; i < inicioLinha + 3; i++) {
            for(int j = inicioColuna; j < inicioColuna + 3; j++) {
                if((i != linha || j != coluna) && tabuleiro[i][j].getNumero() == numero) {
                    return false;
                }
            }
        }
        return true;        
    }
}
