package br.ufjf.dcc.dcc025.sudoku;
import br.ufjf.dcc.dcc025.sudoku.Jogo.Tabuleiro;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    
    private Tabuleiro tabuleiro;
    
    public void menuInicial(){
        System.out.println("---------- Sudoku ----------");
        System.out.println("");
        System.out.println("[1] - Jogar");
        System.out.println("[2] - Tutorial");
        System.out.println("");
        System.out.println("[3] - Sair");
        System.out.println("");
        System.out.println("----------------------------");
        
        Scanner teclado = new Scanner(System.in);
        int escolha = teclado.nextInt();
        
        switch(escolha){
            case 1 -> menuEscolherJogo();
            case 2 -> menuTutorial();
            case 3 -> System.exit(0);
            default -> {
                System.out.println("O número escolhido é inválido. Escolha novamente");
                menuInicial();
            }     
        }  
    }
    
    private void menuTutorial(){
        System.out.println("------------------------------ Regras ------------------------------");
        System.out.println("");
        System.out.println("Deve-se preencher as lacunas do jogo seguindo as seguintes regras:");
        System.out.println("");
        System.out.println("[*] - Sem repetir numeros na horizontal (linha)");
        System.out.println("[*] - Sem repetir numeros na vertical (colunas)");
        System.out.println("[*] - Sem repetir numeros nos quadrados de tamanho 3x3.");
        System.out.println("");
        System.out.println("[1] - voltar");
        System.out.println("");
        System.out.println("--------------------------------------------------------------------");
        
        Scanner teclado = new Scanner(System.in);
        int escolha = teclado.nextInt(); 
        
        switch(escolha){
            case 1 -> menuInicial();
            default -> {
                System.out.println("O número escolhido é inválido. Escolha novamente");
                menuTutorial();
            }    
        } 
    }
    
    private void menuEscolherJogo(){
        System.out.println("------- Modo de Jogo -------");
        System.out.println("");
        System.out.println("[1] - Jogo Randomico");
        System.out.println("[2] - Definir Jogo");
        System.out.println("");
        System.out.println("[3] - Voltar");
        System.out.println("");
        System.out.println("----------------------------");
        
        Scanner teclado = new Scanner(System.in);
        int escolha = teclado.nextInt(); 
        
        switch(escolha){
            case 1 -> menuTabuleiroRandomico();
            case 2 -> menuTabuleiroDefinido();
            case 3 -> menuInicial();
            default -> {
                System.out.println("O número escolhido é inválido. Escolha novamente");
                menuEscolherJogo();
            }    
        } 
    }
    
    private void menuTabuleiroRandomico(){
        System.out.println("--------------- Montagem do Tabuleiro ---------------");
        System.out.println("");
        System.out.println("Quantos espacos em branco você gostaria que tivessem?");
        System.out.println("");
        System.out.println("[0] - Voltar");
        System.out.println("");
        System.out.println("-----------------------------------------------------");
        
        Scanner teclado = new Scanner(System.in);
        int escolha = teclado.nextInt(); 
        
        if(escolha < 1 || escolha > 81){
            if(escolha == 0){
                menuInicial();
                return;
            }
            System.out.println("Entrada Inválida!");
            menuTabuleiroRandomico();
        }
        
        this.tabuleiro = new Tabuleiro();
        tabuleiro.montaTabuleiroAleatorio(escolha);
        tabuleiro.imprimirTabuleiro();
        menuJogadas();
    }
    
    private void menuTabuleiroDefinido(){
        System.out.println("----------------- Definindo Tabuleiro ------------------");
        System.out.println("");
        System.out.println("[*] Formato de insercao: (linha,coluna,numero)");
        System.out.println("[*] Podem ser feitas mais de uma insercao ao mesmo tempo");
        System.out.println("[*] Quando estiver satisfeito e quiser jogar envie: (-1,-1,-1)");
        System.out.println("");
        System.out.println("--------------------------------------------------------");
        
        this.tabuleiro = new Tabuleiro();
        Scanner teclado = new Scanner(System.in);
        
        while(true){
            System.out.print("Faca uma insercao: ");

            String entrada = teclado.nextLine();
            
            String[] insercoes = entrada.replaceAll("\\s", "").split("\\)\\(");
            
            for(String insercao: insercoes){
                
                if("(-1,-1,-1)".equals(insercao)){
                   System.out.println("Finalizacao detectada. Finalizando recebimento de insercoes e iniciando jogo.");
                   menuJogadas();
                   return;
                }
                
                String insercaoSemParenteses = insercao.replaceAll("\\(|\\)","");
                String[] valoresJogada = insercaoSemParenteses.split(",");
             
                if (valoresJogada.length % 3 != 0) {
                    System.out.println("Erro: Formato de Entrada Inválida para " + insercao + " ! Insercao nao computada.");
                    break;
                }
                
                int linha, coluna, numero;
                
                try{
                    linha = Integer.parseInt(valoresJogada[0]);
                    coluna = Integer.parseInt(valoresJogada[1]);
                    numero = Integer.parseInt(valoresJogada[2]);

                    
                }catch (NumberFormatException e) {
                    System.out.println("Erro: Entrada contém valores nao numéricos!");
                    break;
                }
                
                ArrayList<String> retornos = tabuleiro.preencheManual(linha, coluna, numero);
                
                for(String retorno : retornos){
                    System.out.println(retorno);
                }
                
                System.out.println("Resultado do tabuleiro:");
                tabuleiro.imprimirTabuleiro();
                
                if(tabuleiro.verificaFimDoJogo()){
                    menuFinal();
                }
            }
                   
        }
        
    }
    
    private void menuJogadas(){
        System.out.println("--------- Jogando ----------");
        System.out.println("");
        System.out.println("[1] - Fazer Jogada");
        System.out.println("[2] - Remover Jogada");
        System.out.println("[3] - Dica");
        System.out.println("[4] - Verificar");
        System.out.println("");
        System.out.println("[5] - Sair");
        System.out.println("");
        System.out.println("----------------------------"); 
        
        Scanner teclado = new Scanner(System.in);
        int escolha = teclado.nextInt(); 
        
        switch(escolha){
            case 1 -> menuLerJogada(false);
            case 2 -> menuLerJogada(true);
            case 3 -> menuDica();
            case 4 -> menuVerificar();
            case 5 -> System.exit(0);
            default -> {
                System.out.println("O número escolhido é inválido. Escolha novamente");
                menuJogadas();
            } 
        }
    }
    
    private void menuLerJogada(boolean isRemover){
        
        if(!isRemover){
            System.out.println("----------------- Fazendo uma Jogada ------------------");
            System.out.println("");
            System.out.println("[*] Formato de jogada: (linha,coluna,numero)");
            System.out.println("[*] Podem ser feitas mais de uma jogada ao mesmo tempo");
            System.out.println("[*] Caso queira finalizar envie: (-1,-1,-1)");
            System.out.println("");
            System.out.println("-------------------------------------------------------"); 
        }else{
            System.out.println("----------------- Fazendo uma Remocao ------------------");
            System.out.println("");
            System.out.println("[*] Formato de remocao: (linha,coluna)");
            System.out.println("[*] Podem ser feitas mais de uma remocao ao mesmo tempo");
            System.out.println("[*] Caso queira finalizar envie: (-1,-1)");
            System.out.println("");
            System.out.println("-------------------------------------------------------"); 
        }
        
        Scanner teclado = new Scanner(System.in);
        
        while(true){
            if(!isRemover){
                System.out.print("Faca uma jogada: ");
            }else{
                System.out.print("Faca uma remocao: ");
            }
            
            String entrada = teclado.nextLine();
            
            String[] jogadas = entrada.replaceAll("\\s", "").split("\\)\\(");
            
            for(String jogada: jogadas){
                
                if(("(-1,-1,-1)".equals(jogada) && !isRemover) || ("(-1,-1)".equals(jogada) && isRemover)){
                   System.out.println("Finalizacao detectada. Finalizando recebimento de jogadas.");
                   menuJogadas();
                   return;
                }
                
                String jogadaSemParenteses = jogada.replaceAll("\\(|\\)","");
                String[] valoresJogada = jogadaSemParenteses.split(",");
             
                if (valoresJogada.length % 3 != 0 && !isRemover) {
                    System.out.println("Erro: Formato de Entrada Inválida para " + jogada + " ! Jogada nao computada.");
                    break;
                }
                
                if(valoresJogada.length % 2 != 0 && isRemover){
                    System.out.println("Erro: Formato de Remocao Inválida para " + jogada + " ! Remocao nao computada.");
                    break;   
                }
                
                int linha, coluna, numero;
                
                try{
                    linha = Integer.parseInt(valoresJogada[0]);
                    coluna = Integer.parseInt(valoresJogada[1]);
                    if(!isRemover){
                        numero = Integer.parseInt(valoresJogada[2]);
                    }else{
                        numero = 0;
                    }

                    
                }catch (NumberFormatException e) {
                    System.out.println("Erro: Entrada contém valores nao numéricos!");
                    break;
                }
                
                if(numero == 0 && !isRemover){
                    System.out.println("Numero inválido para jogada. Se deseja remover finalize com (-1,-1,-1) e selecione o menu de remocao");
                    break;
                }
                
                ArrayList<String> retornos = tabuleiro.fazJogada(linha, coluna, numero);
                
                for(String retorno : retornos){
                    System.out.println(retorno);
                }
                
                System.out.println("Resultado do tabuleiro:");
                tabuleiro.imprimirTabuleiro();
                
                if(tabuleiro.verificaFimDoJogo()){
                    menuFinal();
                }
            }
                   
        }
        
    }
    
    private void menuDica(){
        
        System.out.println("------------------ Pedindo uma Dica -------------------");
        System.out.println("");
        System.out.println("[*] Formato de dica: (linha,coluna)");
        System.out.println("[*] A dica te mostra as jogadas válidas para numeros não fixos");
        System.out.println("");
        System.out.println("-------------------------------------------------------"); 
        
        Scanner teclado = new Scanner(System.in);
        String entrada = teclado.nextLine();
        
        String[] entradas = entrada.replaceAll("[()\\s]", "").split(",");
        
        if(entradas.length % 2 != 0){
            System.out.println("Erro: Formato de Dica Inválida. Voltando para o menu anterior.");
            menuJogadas();
            return;
        }
        
        int linha = Integer.parseInt(entradas[0]);
        int coluna = Integer.parseInt(entradas[1]);
        
        ArrayList<String> retornos = tabuleiro.verificaDica(linha, coluna);
        
        for(String retorno : retornos){
            System.out.println(retorno);
        }
        
        menuJogadas();
    }
    
    private void menuVerificar(){
        ArrayList<String> retornos = tabuleiro.verificarJogoValido();
        
        if(retornos.isEmpty()){
            System.out.println("Nenhum erro foi encontrado");
        }
        for(String retorno : retornos){
            System.out.println(retorno);
        }
        menuJogadas();
    }
    
    private void menuFinal(){
        
        System.out.println("-------------------------- Final do Jogo -------------------------");
        System.out.println("");
        System.out.println("[*] Meus parabens! Voce conseguiu finalizar o sudoku corretamente!");
        System.out.println("");
        System.out.println("[1] Jogar Novamente");
        System.out.println("[2] Sair");
        System.out.println("------------------------------------------------------------------");
        
        Scanner teclado = new Scanner(System.in);
        int escolha = teclado.nextInt(); 
        
        switch(escolha){
            case 1 -> menuInicial();
            case 2 -> System.exit(0);

            default -> {
                System.out.println("O numero escolhido e invalido. Escolha novamente");
                menuFinal();
            } 
        }
    }
}
