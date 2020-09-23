import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Vector;

public class CLI {

  private static void usage() {
    // System.out.println("\nSupermercato CLI");
    // System.out.println("\nUsage:");
    // System.out.println("\t$ java CLI get|add|update|delete");
    try {
      System.out.println(new String(Files.readAllBytes(Paths.get("CLI.txt"))));
    } catch (IOException e) {
    }
  }

  private static void error(String e) {
    System.out.println("!> " + e);
  }

  private static void printProdotti(Vector<Prodotto> ps) {
    for (Prodotto p : ps) {
      System.out.println(p);
    }
  }

  private String filename;
  private Supermercato supermercato;

  CLI(String[] args) {
    filename = "supermercato.csv";

    try {
      supermercato = Supermercato.load(filename);
    } catch (FileNotFoundException e) {
      supermercato = new Supermercato();
    }

    try {
      parse(args);
    } catch (Exception e) {
      error(e.toString());
      usage();
    }

  }

  private void parse(String[] args) {
    if (args.length < 1)
      usage();
    else if (args[0].equals("get"))
      get(Arrays.copyOfRange(args, 1, args.length));
    else if (args[0].equals("add"))
      add(Arrays.copyOfRange(args, 1, args.length));
    else if (args[0].equals("update"))
      update(Arrays.copyOfRange(args, 1, args.length));
    else if (args[0].equals("delete"))
      delete(Arrays.copyOfRange(args, 1, args.length));
    else if (args[0].equals("--filename") || args[0].equals("-f")) {
      if (args.length < 2)
        usage();
      else {
        filename = args[1];
        parse(Arrays.copyOfRange(args, 2, args.length));
      }
    } else
      usage();
  }

  public void get(String[] args) {
    if (args.length < 1)
      printProdotti(supermercato.get());
    else if (args.length < 2)
      printProdotti(supermercato.get(Integer.parseInt(args[0])));
    else if (args[0].equals("codice"))
      printProdotti(supermercato.get(Integer.parseInt(args[1])));
    else if (args[0].equals("nome"))
      printProdotti(supermercato.get(args[1]));
    else if (args[0].equals("reparto"))
      printProdotti(supermercato.get(Reparto.valueOf(args[1])));
    else if (args[0].equals("responsabile"))
      printProdotti(supermercato.getAs(p -> p.responsabile.equals(args[1])));
    else
      usage();
  }

  private Prodotto inputProdotto(Prodotto p, String[] args) {

    for (String arg : args) {
      String[] parg = arg.split("=");
      if (parg.length > 1) {
        String v = parg[1];
        switch (parg[0]) {
          case "reparto":
            try {
              p.reparto = Reparto.valueOf(v);
            } catch (IllegalArgumentException e) {
              error("reparto [" + v + "] non esistente");
            }
            break;
          case "categoria":
            p.categoria = v;
            break;
          case "scaffale":
            p.scaffale = v;
            break;
          case "nome":
            p.nome = v;
            break;
          case "produttore":
            p.produttore = v;
            break;
          case "unitàMisura":
            p.unitàMisura = v;
            break;
          case "responsabile":
            p.responsabile = v;
            break;
          case "telefono":
            p.telefono = v;
            break;
          case "quantità":
            p.quantità = Integer.parseInt(v);
            break;
          case "prezzo":
            p.prezzo = Double.parseDouble(v);
            break;
          case "sconto":
            p.sconto = Double.parseDouble(v);
            break;
          case "scadenza":
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            p.scadenza = new GregorianCalendar();
            try {
              p.scadenza.setTime(formatter.parse(v));
            } catch (ParseException e) {
              error("scadenza [" + v + "] non valida");
            }
            break;

          default:
            break;
        }

      }
    }
    return p;
  }

  public void add(String[] args) {
    Prodotto p = new Prodotto();
    supermercato.add(inputProdotto(p, args));
    supermercato.save(filename);
  }

  public void update(String[] args) {
    if (args.length < 1)
      usage();
    else {
      int codice = Integer.parseInt(args[0]);
      Vector<Prodotto> src = supermercato.get(codice);
      if (src.size() < 1)
        error("codice non esistente");
      else {
        supermercato.update(codice, inputProdotto(src.get(0), args));
        supermercato.save(filename);
      }
    }
  }

  public void delete(String[] args) {
    if (args.length < 1)
      usage();
    else {
      supermercato.delete(Integer.parseInt(args[0]));
      supermercato.save(filename);
    }
  }

}
