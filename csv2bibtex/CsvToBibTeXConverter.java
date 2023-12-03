package csv2bib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CsvToBibTeXConverter {

    public static void main(String[] args) {
        String csvFile = "dump.csv"; // Passe den Dateinamen an
        String bibtexFile = "lit.bib"; // Passe den Dateinamen an

        try {
            convertCsvToBibTeX(csvFile, bibtexFile);
            System.out.println("Konvertierung abgeschlossen. Ergebnis in " + bibtexFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertCsvToBibTeX(String csvFile, String bibtexFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
             FileWriter fw = new FileWriter(bibtexFile)) {

            String line= br.readLine(); // 1. Zeile fällt weg
           while ((line = br.readLine()) != null) {
                String[] fields = line.split("\",\"");

                // führendes " löschen & Key erzeugen
                String groups = fields[0].substring(1);
                String year;
                if (fields[5].startsWith("9")) year = "19"+fields[5];
                	else year = "20"+fields[5];
                String bibtexKey = fields[2].substring(0, fields[2].indexOf(' '));
                bibtexKey = bibtexKey.substring(0, bibtexKey.length()-1);
                bibtexKey = bibtexKey + year;

                // Aufbau der BibTeX-Einträge (angepasst an deine CSV-Struktur)
                String bibtexEntry = "@book{" + bibtexKey + ",\n";
                bibtexEntry += "  groups = {" + groups + "},\n";
                bibtexEntry += "  author = {" + fields[2] + "},\n";
                bibtexEntry += "  title = {" + fields[3] + "},\n";
                bibtexEntry += "  date = {" + year + "-0" + fields[4] + "-01},\n";
                bibtexEntry += "  priority = {" + fields[1] + "},\n";
                bibtexEntry += "  owner = {" + fields[6] + "},\n";
                bibtexEntry += "  number = {" + fields[7] + "},\n";
                bibtexEntry += "  edition = {" + fields[8] + "},\n";
                bibtexEntry += "  publisher = {" + fields[9] + "},\n";
                bibtexEntry += "  number = {" + fields[10] + "},\n";
                bibtexEntry += "  year = {" + fields[11] + "},\n";
                bibtexEntry += "  isbn = {" + fields[12] + "},\n";
                bibtexEntry += "  abstract = {" + fields[13] + "},\n";
                // Füge weitere Felder nach Bedarf hinzu

                bibtexEntry += "}\n\n";

                fw.write(bibtexEntry);
            }
        }
    }
}
