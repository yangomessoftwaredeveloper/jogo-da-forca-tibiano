//Bibliotecas
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        // Jogo da forca em Java - versão tibiana.

        //Criando caminho do arquivo.txt que contém as palavras que iremos utilizar no jogo.
        String caminhoArquivo = "palavras.txt"; // -> String com nome do arquivo criado no projeto.
        ArrayList<String> palavras = new ArrayList<>(); // -> arraylist com as palavras.

        try(BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))){ //Try-Catch com buffer+reader
            String linha; // -> Variável linha
            while((linha = reader.readLine()) != null){ // -> enquanto o reader não retornar null, adicione palavras na linha + trim para formatar a resposta.
                palavras.add(linha.trim());
            }
        }
        catch(FileNotFoundException e){ //-> exception para arquivo nao encontrado.
            System.out.println("Não foi possível encontrar esse arquivo");
        }
        catch(IOException e){ // -> exception para erro de input ou output, serve como "safety net".
            System.out.println("Algo deu errado.");
        }

        //Classe random.
        Random random = new Random();

        //Scanner para input.
        Scanner scanner = new Scanner(System.in);

        //Variável que receberá a palavra para chutar.
        String palavra = palavras.get(random.nextInt(palavras.size()));

        //Class Wrapper.
        ArrayList<Character> estadoPalavra = new ArrayList<>();
        int chutesErrados = 0;
        int chutesCertos = 0;

        //Mensagem inical ao iniciar programa
        System.out.println("-------------------------------------------------------");
        System.out.println("Seja bem-vindo ao jogo da forca em Java! (Versão Tibia)");
        System.out.println("-------------------------------------------------------");
        System.out.print("Digite seu nick: ");
        String nick = scanner.next(); // -> Pega o nick do user.

        //Loop para adicionar o underline na exata quantia de letras de qualquer palavra do input da variável "palavra".
        for(int i = 0; i < palavra.length(); i++){
            estadoPalavra.add('_');
        }


        //Loop While para funcionamento do programa enquanto o chute errado for menor que 6 e underlines na palavra.
        while(chutesErrados < 6 && estadoPalavra.contains('_') ){

            System.out.println(getHangmanArt(chutesErrados)); //-> pega o método do bonequinho da forca
            System.out.print("Palavra: ");

            for(char c : estadoPalavra){ // loop para colocar um espaço entre os underlines.
                System.out.print(c + " ");
            }
            System.out.println(" ");

            System.out.print("Chute uma letra: ");
            String entrada = scanner.next().toLowerCase(); // -> recebe o caractere que foi chutado.

            // Verifica se digitou apenas 1 caractere no input.
            if (entrada.length() != 1) {
                System.out.println("Digite apenas UMA letra.");
                continue;
            }

            char chute = entrada.charAt(0);

            // Verifica se é uma letra no input.
            if (!Character.isLetter(chute)) {
                System.out.println("Digite apenas letras.");
                continue;
            }

            //Nested conditionals para validar o erro/acerto do chute.
            if(palavra.indexOf(chute) >= 0){
                System.out.println("Chute correto!");

                for(int i = 0; i < palavra.length(); i++){
                    if(palavra.charAt(i) == chute){
                        estadoPalavra.set(i, chute);
                        chutesCertos++;
                    }
                }
            }
            else{
                chutesErrados++;
                System.out.println("Chute errado.");
            }
        }


        //Output caso tenha acertado a palavra - caso nao tenha underline, significa que a palavra estará completa.
        if(!estadoPalavra.contains('_')){
            System.out.println("Você ganhou!");
            System.out.println("A palavra era: " + palavra);
            System.out.println("Você teve um total de " + chutesErrados + " erros e " + chutesCertos + " acertos.");
        }

        //Output caso o user ultrapasse o numero máximo de tentativas.
        if(chutesErrados == 6){
            System.out.println(getHangmanArt(chutesErrados));
            System.out.println(nick + ", você perdeu.");
            System.out.println("A palavra era: " + palavra);
            System.out.println("O total de chutes certos de " + nick + " foi: " + chutesCertos);
        }

        //Fechando scanner
        scanner.close();
    }
    static String getHangmanArt(int chutesErrados){

        return switch(chutesErrados){
            case 0 -> """
                    
                    
                    
                    """;
            case 1 -> """
                     o    
                    
                    
                    """;
            case 2 -> """
                     o
                     |
                    
                    """;
            case 3 -> """
                     o
                    /|
                    
                    """;
            case 4 -> """
                     o
                    /|\\
                    
                    
                    """;
            case 5 -> """
                     o
                    /|\\
                    / 
                    """;
            case 6 -> """
                     o
                    /|\\
                    / \\ 
                    
                    """;
            default -> " ";
        };
    } //Método para mostrar o bonequinho da forca conforme os erros
}
