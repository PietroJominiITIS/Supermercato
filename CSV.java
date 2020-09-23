import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class CSV {

  public static String separator = ",";

  public static void to(String nomefile, Vector<Vector<String>> rows) throws IOException {
    FileWriter writer = new FileWriter(new File(nomefile));

    for (Vector<String> row : rows) {
      writer.write(String.join(separator, row) + "\n");
    }

    writer.close();
  }

  public static Vector<Vector<String>> from(String nomefile) throws IOException, FileNotFoundException {
    Vector<Vector<String>> rows = new Vector<>();
    BufferedReader reader = new BufferedReader(new FileReader(nomefile));
    String row;

    while ((row = reader.readLine()) != null) {
      rows.add(new Vector<>(Arrays.asList(row.split(separator))));
    }

    reader.close();
    return rows;
  }

}
