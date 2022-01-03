import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // files

    static String filename1 = "AttacksP1.txt";
    static String filename2 = "AttacksP2.txt";
    static String filename3 = "saida.txt";
    static File f1 = new File(filename1);
    static File f2 = new File(filename2);
    static File f3 = new File(filename3);

    //lists
    static ArrayList<String> ataques1 = new ArrayList<String> ();
    static ArrayList<Integer> ataques2 = new ArrayList<Integer> ();
    static ArrayList<String> saida = new ArrayList<String> ();

    // static atributos
    static int ataquesj1 = 0;
    static int ataquesj2 = 0;

    static final int VAZIO = 0;
    static final int NAVIO = 1;
    static final int ERROU_TIRO = 2;
    static final int ACERTOU_TIRO = 3;

    static final int POSICAO_X = 0;
    static final int POSICAO_Y = 1;


    static String nomeJogador1, nomeJogador2;
    static int tamanhoX, tamanhoY, quantidadeDeNavios, limiteMaximoDeNavios;
    static int tabuleiroJogador1[][], tabuleiroJogador2[][];
    static Scanner input = new Scanner(System.in);
    static int naviosJogador1, naviosJogador2;


    public static void obterTamanhoDosTabuleiros() {
        tamanhoX = 10;
        tamanhoY = 10;

    }

    public static void obterNomesDosJogadores() {
        System.out.println("Type the name for player 1: ");
        nomeJogador1 = input.next();
        nomeJogador2 = "Computer";
    }

    //funcionamento dos tabuleiros
    public static void iniciandoOsTamanhosDosTabuleiros() {
        tabuleiroJogador1 = retornarNovoTabuleiroVazio();
        tabuleiroJogador2 = retornarNovoTabuleiroVazio();
    }

    public static int[][] retornarNovoTabuleiroVazio() {
        return new int[tamanhoX][tamanhoY];
    }

    public static void obterQuantidadeDeNaviosNoJogo() {
        quantidadeDeNavios = 10;
    }

    public static void instanciarContadoresDeNaviosDosJogadores() {
        naviosJogador1 = quantidadeDeNavios;
        naviosJogador2 = quantidadeDeNavios;
    }

    // coloca os navios no tabuleiro
    public static int[][] retornarNovoTabuleiroComOsNavios() {
        int novoTabuleiro[][] = retornarNovoTabuleiroVazio();
        int quantidadeRestanteDeNavios = quantidadeDeNavios;
        int x= 0, y= 0;
        Random numeroAleatorio = new Random();
        do {
            x = 0;
            y = 0;
            for(int[] linha : novoTabuleiro) {
                for (int coluna : linha) {
                    if (numeroAleatorio.nextInt(100) <= 10) {
                        if(coluna == VAZIO) {
                            novoTabuleiro[x][y] = NAVIO;
                            quantidadeRestanteDeNavios--;
                            break;
                        }
                        if(quantidadeRestanteDeNavios < 0) {
                            break;
                        }
                    }
                    y++;
                }
                y = 0;
                x++;
                if(quantidadeRestanteDeNavios <= 0) {
                    break;
                }
            }
        } while (quantidadeRestanteDeNavios > 0);
        return novoTabuleiro;
    }

    public static void inserirOsNaviosNosTabuleirosDosJogadores() {
        tabuleiroJogador1 = retornarNovoTabuleiroComOsNavios();
        tabuleiroJogador2 = retornarNovoTabuleiroComOsNavios();
    }

    public static void exibirNumerosDasColunasDosTabuleiros() {
        int numeroDaColuna = 1;
        String numerosDoTabuleiro = "   ";

        for(int i = 0; i < tamanhoY; i++) {
            numerosDoTabuleiro += (numeroDaColuna++) + " ";
        }
        System.out.println(numerosDoTabuleiro);
    }

    public static void exibirTabuleiro(String nomeDoJogador, int[][] tabuleiro, boolean seuTabuleiro) {
        System.out.println("|----- " + nomeDoJogador + " -----|");
        exibirNumerosDasColunasDosTabuleiros();
        String linhaDoTabuleiro = "";
        char letraDaLinha = 65;
        for(int[] linha : tabuleiro) {
            linhaDoTabuleiro = (letraDaLinha++) + " |";

            for (int coluna : linha) {
                switch(coluna) {
                    case VAZIO :
                        linhaDoTabuleiro += " |";
                        break;
                    case NAVIO :
                        if (seuTabuleiro) {
                            linhaDoTabuleiro += "N|";
                            break;
                        } else {
                            linhaDoTabuleiro += " |";
                            break;
                        }
                    case ERROU_TIRO :
                        linhaDoTabuleiro += "X|";
                        break;

                    case ACERTOU_TIRO :
                        linhaDoTabuleiro += "D|";
                        break;
                }
            }
            System.out.println(linhaDoTabuleiro);
        }
    }

    public static void exibirTabuleirosDosJogadores() {
        exibirTabuleiro(nomeJogador1, tabuleiroJogador1, true);
        exibirTabuleiro(nomeJogador2, tabuleiroJogador2, false);
    }

    public static boolean validarPosicoesInseridasPeloJogador(int[] posicoes) {
        boolean retorno = true;
        if (posicoes[0] > tamanhoX -1) {
            retorno = false;
            System.out.println("The position of the letter ca not be higher than " + (char)(tamanhoX + 64));
        }

        if (posicoes[1] > tamanhoY) {
            retorno = false;
            System.out.println("The position of the numbers ca not be higher than " + tamanhoY);
        }

        ataquesj1++;
        return retorno;
    }

    public static String receberValorDigitadoPeloJogador() {
        System.out.println("Type in the position of your shot:");
        return input.next();

    }





    public static boolean validarTiroDoJogador(String tiroDoJogador) {
        int quantidadeDeNumeros = (tamanhoY > 10) ? 2 : 1;
        String expressaoDeVerificacao = "^[A-Za-z]{1}[0-9]{" + quantidadeDeNumeros + "}$";

        // coloca input no file 1
        ataques1.add(tiroDoJogador);
        try {
            FileWriter fw = new FileWriter(f1);

            fw.write(ataques1+ "\n");

            fw.close();
        } catch (IOException e) {
            System.out.println("IO Error.");
        }

        return tiroDoJogador.matches(expressaoDeVerificacao);
    }

    public static int[] retornarPosicoesDigitadasPeloJogador(String tiroDoJogador) {
        String tiro = tiroDoJogador.toLowerCase();
        int[] retorno = new int[2];
        retorno[POSICAO_X] = tiro.charAt(0) - 97;
        retorno[POSICAO_Y] = Integer.parseInt(tiro.substring(1)) - 1;

        return retorno;
    }

    public static void inserirValoresDaAcaoNoTabuleiro(int[] posicoes, int numeroDoJogador) {
        if (numeroDoJogador == 1) {
            if (tabuleiroJogador2[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] == NAVIO) {
                tabuleiroJogador2[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ACERTOU_TIRO;
                naviosJogador2--;
                System.out.println(nomeJogador1 + " You hit a Ship!");
            } else {
                tabuleiroJogador2[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ERROU_TIRO;
                System.out.println(nomeJogador1 + " You missed!");
            }
        } else {
            if (tabuleiroJogador1[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] == NAVIO) {
                tabuleiroJogador1[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ACERTOU_TIRO;
                naviosJogador1--;
                System.out.println(nomeJogador2 + " You hit a Ship!");
            } else {
                tabuleiroJogador1[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ERROU_TIRO;
                System.out.println(nomeJogador2 + " You missed!");
            }
        }
    }

    public static boolean acaoDoJogador() {
        boolean acaoValida = true;
        String tiroDoJogador = receberValorDigitadoPeloJogador();
        if (validarTiroDoJogador(tiroDoJogador)) {
            int[] posicoes = retornarPosicoesDigitadasPeloJogador(tiroDoJogador);
            if (validarPosicoesInseridasPeloJogador(posicoes)) {
                inserirValoresDaAcaoNoTabuleiro(posicoes, 1);
            } else {
                acaoValida = false;
            }
        } else {
            System.out.println("Position not valid");
            acaoValida = false;
        }
        return acaoValida;
    }

    //acoes do computador
    public static void acaoDoComputador() {
        int[] posicoes = retornarJogadaDoComputador();
        inserirValoresDaAcaoNoTabuleiro(posicoes, 2);
    }

    public static int[] retornarJogadaDoComputador() {
        int[] posicoes = new int[2];
        posicoes[POSICAO_X] = retornarJogadaAleatoriaDoComputador(tamanhoX);
        posicoes[POSICAO_Y] = retornarJogadaAleatoriaDoComputador(tamanhoY);


        // coloca input no file 2
        ataques2.add(retornarJogadaAleatoriaDoComputador(tamanhoX) + retornarJogadaAleatoriaDoComputador(tamanhoY));
        try {
            FileWriter fw = new FileWriter(f2);
            fw.write(ataques2+ "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("IO Error.");
        }
        ataquesj2++;
        return posicoes;
    }

    public static int retornarJogadaAleatoriaDoComputador(int limite) {
        Random jogadaDoComputador = new Random();
        int numeroGerado = jogadaDoComputador.nextInt(limite);

        return (numeroGerado == limite) ? --numeroGerado : numeroGerado;

    }


    // main onde tudo acontece

    public static void main(String[] args) {

        System.out.println("~~Welcome to Battleship War Game~~");
        System.out.println("\n");


        obterNomesDosJogadores();
        obterTamanhoDosTabuleiros();
        iniciandoOsTamanhosDosTabuleiros();
        obterQuantidadeDeNaviosNoJogo();
        instanciarContadoresDeNaviosDosJogadores();
        inserirOsNaviosNosTabuleirosDosJogadores();




        //verifica jogo e diz quem venceu e com quantas jogadas

        boolean jogoAtivo = true;
        do{
            exibirTabuleirosDosJogadores();
            if (acaoDoJogador()) {
                if (naviosJogador2 <= 0) {
                    System.out.println(nomeJogador1 + " won the game!");
                    try {
                        FileWriter fw = new FileWriter(f3);
                        fw.write(nomeJogador1 + " won the game with " + ataquesj1 + " attacks!");

                        fw.close();
                    } catch (IOException e) {
                        System.out.println("Um erro do tipo IO ocorreu.");
                    }

                    break;
                }
                acaoDoComputador();
                if (naviosJogador1 <= 0) {
                    System.out.println(nomeJogador2 + " won the game!");
                    try {
                        FileWriter fw = new FileWriter(f3);
                        fw.write(nomeJogador2 + " won the game with " + ataquesj2 + " attacks!");

                        fw.close();
                    } catch (IOException e) {
                        System.out.println("IO Error.");
                    }



                }
            }

        }while (jogoAtivo);
        exibirTabuleirosDosJogadores();
        input.close();



    }

}