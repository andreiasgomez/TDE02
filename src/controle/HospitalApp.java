package controle;

import model.Paciente;

import java.io.*;
import java.util.*;

public class HospitalApp {
    private static final String FILE_NAME = "pacientes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Incluir Paciente");
            System.out.println("2. Alterar Paciente");
            System.out.println("3. Excluir Paciente");
            System.out.println("4. Consultar Pacientes");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> incluirPaciente(scanner);
                case 2 -> alterarPaciente(scanner);
                case 3 -> excluirPaciente(scanner);
                case 4 -> listarPacientes();
                case 5 -> {
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void incluirPaciente(Scanner scanner) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            System.out.print("Número: ");
            int numero = scanner.nextInt();
            System.out.print("Peso: ");
            double peso = scanner.nextDouble();
            System.out.print("Altura: ");
            double altura = scanner.nextDouble();

            writer.write(numero + "," + peso + "," + altura);
            writer.newLine();
            System.out.println("Paciente incluído com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao incluir paciente: " + e.getMessage());
        }
    }

    private static void alterarPaciente(Scanner scanner) {
        System.out.print("Informe o número do paciente a alterar: ");
        int numero = scanner.nextInt();

        List<Paciente> pacientes = carregarPacientes();
        boolean encontrado = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Paciente p : pacientes) {
                if (p.getNumero() == numero) {
                    System.out.print("Novo peso: ");
                    p.setPeso(scanner.nextDouble());
                    System.out.print("Nova altura: ");
                    p.setAltura(scanner.nextDouble());
                    encontrado = true;
                }
                writer.write(p.getNumero() + "," + p.getPeso() + "," + p.getAltura());
                writer.newLine();
            }
            if (encontrado) {
                System.out.println("Paciente alterado com sucesso!");
            } else {
                System.out.println("Paciente não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao alterar paciente: " + e.getMessage());
        }
    }

    private static void excluirPaciente(Scanner scanner) {
        System.out.print("Informe o número do paciente a excluir: ");
        int numero = scanner.nextInt();

        List<Paciente> pacientes = carregarPacientes();
        boolean encontrado = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Paciente p : pacientes) {
                if (p.getNumero() != numero) {
                    writer.write(p.getNumero() + "," + p.getPeso() + "," + p.getAltura());
                    writer.newLine();
                } else {
                    encontrado = true;
                }
            }
            if (encontrado) {
                System.out.println("Paciente excluído com sucesso!");
            } else {
                System.out.println("Paciente não encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao excluir paciente: " + e.getMessage());
        }
    }

    private static void listarPacientes() {
        List<Paciente> pacientes = carregarPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            for (Paciente p : pacientes) {
                p.listarPaciente();
                System.out.println("----------");
            }
        }
    }

    private static List<Paciente> carregarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Nenhum dado encontrado. O arquivo será criado.");
            return pacientes;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                int numero = Integer.parseInt(dados[0]);
                double peso = Double.parseDouble(dados[1]);
                double altura = Double.parseDouble(dados[2]);
                pacientes.add(new Paciente(numero, peso, altura));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar pacientes: " + e.getMessage());
        }
        return pacientes;
    }
}
