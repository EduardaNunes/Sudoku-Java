package br.ufjf.dcc.dcc025.sudoku.Jogo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Tabuleiro {
    private final Celula[][] tabuleiro;
    private boolean isJogoValido;
    
    public Tabuleiro(){
        isJogoValido = false;
        tabuleiro = new Celula[9][9];
        iniciaTabuleiroZerado();
    }
    
    private void iniciaTabuleiroZerado(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tabuleiro[i][j] = new Celula(); // Inicia numero = 0 e isFixo = falso por padrão
            }
        }
    }
    
    private void setIsJogoValido(boolean newEstadoDoJogo){
        this.isJogoValido = newEstadoDoJogo;
    }
    
    public void montaTabuleiroAleatorio(int remover){ 
        // cria tabuleiro válido
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(tabuleiro[i][j].getNumero() == 0){
                    boolean encontrouLugarValido = false;
                    ArrayList<Integer> numeros = criaListaRandomica();
                    for(int numero: numeros){
                        if(isJogadaValida(i, j, numero)){
                            tabuleiro[i][j].setNumero(numero);
                            tabuleiro[i][j].setIsFixo(true);
                            encontrouLugarValido = true;
                            break;
                        }                        
                    }
                    if(!encontrouLugarValido){
                        iniciaTabuleiroZerado();
                        montaTabuleiroAleatorio(remover);
                        return;
                    }
                }
            }
        }
        
        // retira os numeros a serem jogados
        for (int k = 0; k < remover; k++){
            Random random = new Random();
            int linha = random.nextInt(9);
            int coluna = random.nextInt(9);
            
            if(tabuleiro[linha][coluna].getNumero() != 0){
                tabuleiro[linha][coluna].setIsFixo(false);
                tabuleiro[linha][coluna].setNumero(0);
            }else{
                k--;
            }
        }
        
        setIsJogoValido(true);
    }

    public ArrayList<String> preencheManual(int linha, int coluna, int numero){
        
        ArrayList<String> saida = new ArrayList<>();
        
        if(linha < 0 || linha > 8 || coluna < 0 || coluna > 8 || numero < 0 || numero > 9){
            
            if(linha < 0 || linha > 8){
                saida.add("Valor da linha e invalido");
            }
            if(coluna < 0 || coluna > 8){
               saida.add("Valor da coluna e inválido"); 
            }
            if(numero < 0 || numero > 9){
                saida.add("Valor do número e invalido");
            }
            
            return saida;
        }
        
        tabuleiro[linha][coluna].setNumero(numero);
        tabuleiro[linha][coluna].setIsFixo(true);
        this.isJogoValido = isJogadaValida(linha, coluna, numero);
        
        saida.add("Numero: " + numero + " inserido na posicao: (" + linha + "," + coluna + ")");
       
        return saida;
    }

    
    public ArrayList<String> fazJogada(int linha, int coluna, int numero){
        
        ArrayList<String> saida = new ArrayList<>();
        
        if(linha < 0 || linha > 8 || coluna < 0 || coluna > 8 || numero < 0 || numero > 9){
            
            if(linha < 0 || linha > 8){
                saida.add("Valor da linha e invalido");
            }
            if(coluna < 0 || coluna > 8){
               saida.add("Valor da coluna e inválido"); 
            }
            if(numero < 0 || numero > 9){
                saida.add("Valor do número e invalido");
            }
            
            return saida;
        }
        
        if(tabuleiro[linha][coluna].getIsFixo() == false){
            tabuleiro[linha][coluna].setNumero(numero);  
            if(numero != 0){
                saida.add("Jogada em (" + linha + "," + coluna + "," + numero + ") computada");
            }else{
                saida.add("Remocao em (" + linha + "," + coluna + ") computada");
            }
            this.isJogoValido = isJogadaValida(linha, coluna, numero);
            
        }else{
            saida.add("Posicao e fixa e nao pode ser alterada");
        }
       
        return saida;
    }
    
    private boolean isJogadaValida(int linha, int coluna, int numero){
        return ValidarJogada.validarLinha(tabuleiro, linha, coluna, numero) &&
               ValidarJogada.validarColuna(tabuleiro, linha, coluna, numero) &&
               ValidarJogada.validarMatriz(tabuleiro, linha, coluna, numero);
    }
    
    public ArrayList<String> verificarJogoValido(){
        
        ArrayList<String> erros = new ArrayList<>();
        
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                
                int numero = tabuleiro[i][j].getNumero();
                
                if(numero != 0 && !isJogadaValida(i, j, numero) && !tabuleiro[i][j].getIsFixo()){
                    erros.add("A posicao: (" + i + "," + j + ") esta incorreta");
                }
            }
        }
        
        return erros;
    }
    
    public ArrayList<String> verificaDica(int linha, int coluna){
        ArrayList<String> jogadasValidas = new ArrayList<>();
        ArrayList<String> erro = new ArrayList<>();
        
        if(tabuleiro[linha][coluna].getIsFixo()){
            erro.add("Essa posicao e fixa e valida, nao e possivel alterar.");
            return erro;
        }

        for(int k = 1; k <=9; k++){
            
            if(isJogadaValida(linha, coluna, k)){
                jogadasValidas.add("A posicao: (" + linha + "," + coluna + ") pode receber o valor: " + k);
            }
        }
        
        return jogadasValidas;
    }
    
    public boolean verificaFimDoJogo(){
        
        if(!this.isJogoValido){
            return false;
        }
        
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(tabuleiro[i][j].getNumero() == 0){
                    return false;
                }
            }
        }
        return true;
    }
    
    private ArrayList<Integer> criaListaRandomica(){
        ArrayList<Integer> numeros = new ArrayList<>();
        
        for (int i = 1; i <= 9; i++) {
            numeros.add(i);
        }
        
        Collections.shuffle(numeros);
        return numeros;
    }
    
    public void imprimirTabuleiro() {
        
        System.out.println(""); 
        if(this.isJogoValido){
            System.out.println("O jogo esta valido");
        }else{
           System.out.println("O jogo nao esta valido"); 
        }
        
        
        for (int i = 0; i < 9; i++) {

            if (i % 3 == 0) {
                System.out.println("+-------+-------+-------+");
            }

            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                int numero = tabuleiro[i][j].getNumero();
                System.out.print((numero == 0 ? " " : numero) + " ");
            }
            System.out.println("|");
        }
    
        System.out.println("+-------+-------+-------+");
    }
}
