package ie.davidloftus.algorithms.compression;

import java.io.*;

public class AsciiRunLength {

    public static void compress(Reader reader, Writer writer) throws IOException {
        int count = 0;
        char lastChar = 0;
        while (true) {
            int c = reader.read();
            if (c != lastChar) {
                if (count > 0) {
                    writer.write(lastChar);
                    writer.write(Integer.toString(count));
                }

                lastChar = (char) c;
                count = 1;
            } else {
                count++;
            }
            if (c == -1) {
                break;
            }
        }
    }

    public static String compress(String input) {
        StringReader reader = new StringReader(input);
        StringWriter writer = new StringWriter();

        try {
            compress(reader, writer);
        } catch (IOException e) {
            throw new RuntimeException("Somehow got IOException with string operations", e);
        }

        return writer.toString();
    }

}
