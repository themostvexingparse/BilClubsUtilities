import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class HTMLTemplate {
    private File file;
    private StringBuilder stringBuilder = new StringBuilder();
    private String content;
    
    public HTMLTemplate(String fileName) {
        file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append("\n");
            }
            scanner.close();
            content = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void format(HashMap<String, String> formatMap) {
        while (content.contains("{{")) {
            int formatStartIndex = content.indexOf("{{");
            int formatEndIndex = content.indexOf("}}", formatStartIndex);
            String pattern = content.substring(formatStartIndex, formatEndIndex+2);
            String injectionKey = pattern.replace("{", "")
                                         .replace("}", "")
                                         .replace(" ", "");
            String injectionValue = formatMap.get(injectionKey);
            content = content.replace(pattern, injectionValue);
        }
    }

    public void format(String key, String value) {
        int formatStartIndex = 0;
        int formatEndIndex = 0;
        while (formatStartIndex > -1) {
            formatStartIndex = content.indexOf("{{", formatEndIndex);
            formatEndIndex = content.indexOf("}}", formatStartIndex);
            if (formatStartIndex < 0) break;
            String pattern = content.substring(formatStartIndex, formatEndIndex+2);
            String injectionKey = pattern.replace("{", "")
                                         .replace("}", "")
                                         .replace(" ", "");
            if (!injectionKey.equals(key)) continue;
            content = content.replace(pattern, value);
        }
        System.out.println("Done formatting");
    } // Why does this formatting fail?

    public String formatted(HashMap<String, String> formatMap) {
        String returnContent = content;
        while (returnContent.contains("{{")) {
            int formatStartIndex = returnContent.indexOf("{{");
            int formatEndIndex = returnContent.indexOf("}}", formatStartIndex);
            String pattern = returnContent.substring(formatStartIndex, formatEndIndex+2);
            String injectionKey = pattern.replace("{", "")
                                         .replace("}", "")
                                         .replace(" ", "");
            String injectionValue = formatMap.get(injectionKey);
            returnContent = returnContent.replace(pattern, injectionValue);
        }
        return returnContent;
    }

    public String toString() {
        return content;
    }
}
