public class Alunos {
    public static void main(String[] args) {
        String curso = "Ciencias de dados e IA";
        String disciplina = "Programação orientada a objeto";

        String[][] alunos = {
            {"Brenda Mendes Araujo", "202209150008"},
            {"Gabriel Franklin Monteiro", "202301135052"},
            {"Beatriz dos Santos Vieira", "202208384986"}
        };

        System.out.println("Curso: " + curso);
        System.out.println("Disciplina: " + disciplina);
        System.out.println();
        System.out.printf("%-30s | %-15s%n", "Aluno", "Matrícula");
        System.out.println("-------------------------------|---------------------------");

        for (String[] aluno : alunos) {
            System.out.printf("%-30s | %-15s%n", aluno[0], aluno[1]);
        }
    }
}
