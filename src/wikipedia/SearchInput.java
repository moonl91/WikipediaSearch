package wikipedia;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Scanner;

public class SearchInput {
    private final Scanner scanner;

    public SearchInput() {
        this(createConsoleScanner());
    }

    public SearchInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public static Charset consoleCharset() {
        String[] properties = {"sun.stdout.encoding", "stdout.encoding"};
        for (String property : properties) {
            String name = System.getProperty(property);
            if (name != null && !name.isBlank()) {
                try {
                    return Charset.forName(name);
                } catch (Exception ignored) {
                }
            }
        }
        return Charset.defaultCharset();
    }

    public static Scanner createConsoleScanner() {
        Charset charset = consoleCharset();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, charset));
        return new Scanner(System.in, charset);
    }

    public String readQuery() {
        while (true) {
            System.out.print("Введите поисковый запрос: ");
            String query = scanner.nextLine().trim();
            if (!query.isEmpty()) {
                return query;
            }
            System.out.println("Запрос не должен быть пустым. Попробуйте ещё раз.");
        }
    }

    public int readNumber(int min, int max) {
        while (true) {
            System.out.print("Выберите номер статьи (0 - новый поиск): ");
            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Введите число от " + min + " до " + max + ".");
        }
    }

    public boolean askContinue() {
        while (true) {
            System.out.print("Хотите выполнить ещё один поиск? (Д/Н): ");
            String answer = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (answer.equals("д") || answer.equals("да") || answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("н") || answer.equals("нет") || answer.equals("n") || answer.equals("no")) {
                return false;
            }
            System.out.println("Введите Д или Н (можно Y или N).");
        }
    }
}