package br.com.dio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.dio.model.Board;
import br.com.dio.model.GameStatusEnum;
import br.com.dio.model.Space;
public class Main {
    
    private final static Scanner scanner = new Scanner(System.in);

    private static Board board;

    private final static int BOARD_LIMIT = 9;
    
    public static void main(String[] args){
        final var positions = Stream.of(
            "0,0,5", "0,1,3", "0,4,7",
            "1,0,6", "1,3,1", "1,4,9", "1,5,5",
            "2,1,9", "2,2,8", "2,7,6",
            "3,0,8", "3,4,6", "3,8,3",
            "4,0,4", "4,3,8", "4,5,3", "4,8,1",
            "5,0,7", "5,4,2", "5,8,6",
            "6,1,6", "6,6,2", "6,7,8",
            "7,3,4", "7,4,1", "7,5,9", "7,8,5",
            "8,4,8", "8,7,7", "8,8,9"
        ).collect(Collectors.toMap(
            x -> x.split(",")[0] + "," + x.split(",")[1],
            v -> v.split(",")[2]
        ));

        var option = -1;
        while(true){
            System.out.println("1 - Iniciar um novo jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar o tabuleiro");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("0 - Sair");

            option = scanner.nextInt();

            switch(option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 0 -> System.exit(0);
                default -> {
                    System.out.println("Opção inválida! Selecione uma das opções do menu");
                }
            }
        }
    }

    private static void startGame(Map<String, String> positions) {
        // If a game is already in progress, ask to restart
        if (board != null && board.getStatus().equals(GameStatusEnum.IN_PROGRESS)) {
            System.out.println("Jogo em andamento. Deseja reiniciar? (S/N)");
            final var restart = scanner.next();
            if (!restart.equalsIgnoreCase("S")) {
                return;
            }
        }
        
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s, %s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(";")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(";")[1]);
                spaces.get(i).add(new Space(expected, fixed));
            }
        }
        board = new Board(spaces);
    }



    private static void inputNumber() {
        if(board == null){
            System.out.println("O jogo ainda não foi iniciado.");
            
            return;
        }
        
        System.out.println("Digite a coluna (0-8):");
        var col = runUntilGetValiNumber(0, 8);
        System.out.println("Digite a linha (0-8):");
        var row = runUntilGetValiNumber(0, 8);
        System.out.printf("Digite o número que vai entrar na posição (%d, %d) (1-9): ".formatted(col, row));
        var number = runUntilGetValiNumber(1, 9);
        if(board.changeValue(col, row, number)){
            System.out.printf("Número %d inserido na posição (%d, %d) com sucesso.%n".formatted(number, col, row));
        } else {
            System.out.printf("Não foi possível inserir o número %d na posição (%d, %d).%n".formatted(number, col, row));
        }
    }




    private static void removeNumber() {
        if(board == null){
            System.out.println("O jogo ainda não foi iniciado.");
            
            return;
        }

        System.out.println("Digite a coluna (0-8):");
        var col = runUntilGetValiNumber(0, 8);
        System.out.println("Digite a linha (0-8):");
        var row = runUntilGetValiNumber(0, 8);
        System.out.printf("Digite o número que vai entrar na posição (%d, %d) (1-9): ".formatted(col, row));
        var number = runUntilGetValiNumber(1, 9);
        if(!board.clearValue(col, row)){
            System.out.printf("Número %d removido da posição (%d, %d) com sucesso.%n".formatted(number, col, row));
        } else {
            System.out.printf("Não foi possível remover o número %d da posição (%d, %d).%n".formatted(number, col, row));
        }
        
    }

    private static void showCurrentGame() {
        if (board == null) {
            System.out.println("Nenhum jogo iniciado");
            return;
        }
        System.out.println("Exibindo tabuleiro atual");
    }

    private static void showGameStatus() {
        System.out.println("Exibindo status do jogo");
    }

    private static void clearGame() {
        board = null;
        System.out.println("Jogo limpo");
    }

    private static void finishGame() {
        System.out.println("Finalizando o jogo");
        System.exit(0);
    }

    private static int runUntilGetValiNumber(final int min, final int max) {
        int current = scanner.nextInt();
        while (current < min || current > max) {
            System.out.printf("Número inválido. Digite um número entre %d e %d: ", min, max);
            current = scanner.nextInt();
        }
        return current;
    }
}
