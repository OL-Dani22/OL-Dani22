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
                String bibtexKey = "";
                try{ 
                	bibtexKey = fields[2].substring(0, fields[2].indexOf(' ')); 
                	int firstWordLength = bibtexKey.length()-1;
                	if (bibtexKey.endsWith(",")) // 2 Schreibweisen Daniel Enke & Enke, Daniel immer Nachname
                    	bibtexKey = bibtexKey.substring(0, firstWordLength);  // letztes Zeichen löschen bei ","
                	else
                		bibtexKey = fields[2].substring(firstWordLength+2, fields[2].length()); 
                } catch (StringIndexOutOfBoundsException s) {}
                bibtexKey = bibtexKey + year;
                bibtexKey = fields[1] + "-" + bibtexKey; // vorne noch eine laufende Nummer ran

                // Aufbau der BibTeX-Einträge (angepasst an deine CSV-Struktur)
                String bibtexEntry = "@book{" + bibtexKey + ",\n";
                bibtexEntry += "  groups = {" + groups + "},\n";
                bibtexEntry += "  author = {" + fields[2] + "},\n";
                bibtexEntry += "  title = {{" + fields[3] + "}},\n";
                bibtexEntry += "  date = {" + year + "-0" + fields[4] + "-01},\n";
                bibtexEntry += "  year = {" + year + "},\n";
                bibtexEntry += "  priority = {" + fields[1] + "},\n";
                bibtexEntry += "  owner = {" + fields[6] + "},\n";
                if (! fields[7].isEmpty()) // nur gefüllte Felder transferieren
                	bibtexEntry += "  number = {" + fields[7] + "},\n";
                if (Integer.parseInt(fields[8]) != 0)
                	bibtexEntry += "  note = {" + fields[8] + "},\n";
                if (! fields[9].isEmpty())
                	bibtexEntry += "  publisher = {" + fields[9] + "},\n";
                if (Integer.parseInt(fields[10]) != 0)
                	bibtexEntry += "  edition = {" + fields[10] + "},\n";
                if (Integer.parseInt(fields[11]) != 0)
                	bibtexEntry += "  printed = {" + fields[11] + "},\n";
                if (! fields[12].isEmpty())
                	bibtexEntry += "  isbn = {" + fields[12] + "},\n";
                // [13] waren Schlüsselwörter die aber in anderen Tabellen standen
                String abstracts =  fields[14].substring(0, fields[14].length()-1);  // letztes Zeichen löschen *"*

                bibtexEntry += "  abstract = {" + abstracts + "},\n";
                // Füge weitere Felder nach Bedarf hinzu

                bibtexEntry += "}\n\n";

                fw.write(bibtexEntry);
            }
        }
    }
}
