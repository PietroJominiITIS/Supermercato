import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.function.Predicate;

public class Supermercato {

  private Vector<Prodotto> prodotti;
  int codiceCorrente;

  Supermercato() {
    prodotti = new Vector<>();
    codiceCorrente = 0;
  }

  // C: create
  private void add(Prodotto p, int codice) {
    codiceCorrente = codice;
    p.codice = codice;
    prodotti.add(p);
  }

  public void add(Prodotto p) {
    this.add(p, codiceCorrente + 1);
  }

  // R : read
  public Vector<Prodotto> getAs(Predicate<Prodotto> cmp) {
    Vector<Prodotto> results = new Vector<>();
    for (Prodotto p : prodotti) {
      try {
        if (cmp.test(p))
          results.add(p);
      } catch (NullPointerException e) {
      }
    }
    return results;
  }

  public Vector<Prodotto> get() {
    return this.getAs(p -> true);
  }

  public Vector<Prodotto> get(int codice) {
    return this.getAs(p -> p.codice == codice);
  }

  public Vector<Prodotto> get(String nome) {
    return this.getAs(p -> p.nome.equals(nome));
  }

  public Vector<Prodotto> get(GregorianCalendar scadenza) {
    return this.getAs(p -> p.scadenza.equals(scadenza));
  }

  public Vector<Prodotto> get(Reparto r) {
    return this.getAs(p -> p.reparto == r);
  }

  // U: update
  public boolean update(int codice, Prodotto np) {
    for (int i = 0; i < prodotti.size(); i++) {
      Prodotto p = prodotti.get(i);
      if (p.codice == codice) {
        np.codice = p.codice;
        prodotti.set(i, np);
        return true;
      }
    }
    return false;
  }

  // D: delete
  public Prodotto delete(int codice) {
    for (int i = 0; i < prodotti.size(); i++) {
      Prodotto p = prodotti.get(i);
      if (p.codice == codice) {
        prodotti.remove(i);
        return p;
      }
    }
    return null;
  }

  // Serialization
  public void save(String nomefile) {
    Vector<Vector<String>> rows = new Vector<>();

    for (Prodotto p : prodotti) {
      rows.add(p.toVector());
    }

    try {
      CSV.to(nomefile, rows);
    } catch (IOException e) {
      System.out.println("IOException while saving! That shouldn't happen...");
    }
  }

  // Deserialization
  public static Supermercato load(String nomefile) throws FileNotFoundException {
    try {
      Supermercato s = new Supermercato();

      for (Vector<String> row : CSV.from(nomefile)) {
        Prodotto p = Prodotto.fromVector(row);
        s.add(p, p.codice);
      }

      return s;
    } catch (IOException e) {
      System.out.println("IOException while loading! That shouldn't happen...");
    }
    return null;
  }

  // Others
  @Override
  public String toString() {
    String out = "";
    for (Prodotto p : prodotti) {
      out += p.codice + " -> " + p.nome + "\n";
    }
    return out.subSequence(0, out.length() - 1).toString();
  }

}
