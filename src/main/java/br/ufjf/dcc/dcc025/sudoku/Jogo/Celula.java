package br.ufjf.dcc.dcc025.sudoku.Jogo;

public class Celula {
    private int numero;
    private boolean isFixo;
    
    protected Celula(){
        this.numero = 0;
        this.isFixo = false;
    }
    
    protected int getNumero(){
        return this.numero;
    }
    
    protected void setNumero(int novoNumero){
        if(!isFixo){
            this.numero = novoNumero;
        }else{
            System.out.println("Essa posição é fixa e, portanto, não pode ser alterada.");
        }    
    }
    
    protected void setIsFixo(boolean newIsFixo){
        this.isFixo = newIsFixo;
    }
    
    protected boolean getIsFixo(){
        return this.isFixo;
    }
}
