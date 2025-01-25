import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArvoreRB rb = new ArvoreRB();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Inserir número(s)");
            System.out.println("2 - Remover número");
            System.out.println("0 - Encerrar");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 0) {
                System.out.println("Encerrando...");
                break;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite os números a serem inseridos (separados por vírgula): ");
                    String[] numerosInserir = scanner.nextLine().split(",");
                    for (String num : numerosInserir) {
                        int valor = Integer.parseInt(num.trim());
                        System.out.println("Inserindo: " + valor);
                        rb.inserir(valor);
                        rb.printTree();
                    }
                    break;

                case 2:
                    System.out.print("Digite o número a ser removido: ");
                    int numeroRemover = scanner.nextInt();
                    System.out.println("Removendo: " + numeroRemover);
                    rb.remover(numeroRemover);
                    rb.printTree();
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        scanner.close();
    }
}
