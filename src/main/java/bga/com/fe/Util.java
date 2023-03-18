package bga.com.fe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author bgarita, 28/11/2022
 */
public class Util {

    public static String fileToString(Path path) {
        StringBuilder sb = new StringBuilder();
        try {
            if (path.toFile().exists()) {
                System.out.println("File to String: " + path.toFile().getAbsolutePath());
                // Primero intento leer el archivo con UTF-8 y si se da un error
                // lo intento con ISO-8859-1
                Object[] content;

                try ( Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
                    content = stream.toArray();
                }

                for (Object o : content) {
                    sb.append(o).append("\n");
                } // end for

            } else {
                sb.append("Archivo no encontrado.");
            } // end if-else
        } // end fileToString
        catch (Exception ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            sb.append(ex.getMessage());
        }
        return sb.toString();
    } // end fileToString

    /**
     * Guardar texto en un archivo ASCII
     *
     * @param text String - texto a almacenar
     * @param path String - nombre del archivo a guardar (incluye la ruta
     * completa)
     * @param append boolean - true=Agrega el texto, false=Reemplaza el texto
     * existente
     * @throws IOException
     * @author Bosco Garita Azofeifa, 13/07/2019
     */
    public static void stringToFile(String text, String path, boolean append) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(path, append)) {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            outputStreamWriter.write(text);
            outputStreamWriter.flush();
        }
    } // end stringToFile

    public static void copyFile(File src, File dst) throws IOException {
        try ( OutputStream out = new FileOutputStream(dst)) {
            try ( InputStream in = new FileInputStream(src)) {
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                } // end while
            }
        }
    } // end copyFile
}
