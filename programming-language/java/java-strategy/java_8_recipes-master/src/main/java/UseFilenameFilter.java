import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

@SuppressWarnings({"Convert2Lambda", "CodeBlock2Expr"})
public class UseFilenameFilter {
    public static void main(String[] args) {
        File directory = new File("./src/main/java");
        System.out.println(directory.getAbsolutePath());

        // Anonymous inner class
        String[] names = directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        System.out.println(Arrays.asList(names));

        // Use lambda expression instead
        names = directory.list((dir, name) -> name.endsWith(".java"));
        System.out.println(Arrays.asList(names));

        // Explict data types
        names = directory.list((File dir, String name) -> name.endsWith(".java"));
        System.out.println(Arrays.asList(names));

        names = directory.list((File dir, String name) -> {
            return name.endsWith(".java");
        });
        System.out.println(Arrays.asList(names));
    }
}
