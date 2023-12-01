package csv2bib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CsvToBibTeXConverter {

    public static void main(String[] args) {
        String csvFile = "deine_datei.csv"; // Passe den Dateinamen an
        String bibtexFile = "ergebnis.bib"; // Passe den Dateinamen an

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

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                // Annahme: Die erste Spalte enthält den BibTeX-Schlüssel
                String bibtexKey = fields[0].trim();

                // Aufbau der BibTeX-Einträge (angepasst an deine CSV-Struktur)
                String bibtexEntry = "@article{" + bibtexKey + ",\n";
                bibtexEntry += "  author = {" + fields[1] + "},\n";
                bibtexEntry += "  title = {" + fields[2] + "},\n";
                bibtexEntry += "  journal = {" + fields[3] + "},\n";
                bibtexEntry += "  year = {" + fields[4] + "},\n";
                // Füge weitere Felder nach Bedarf hinzu

                bibtexEntry += "}\n\n";

                fw.write(bibtexEntry);
            }
        }
    }
}
