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
    p.codice = codice;
    prodotti.add(p);
  }

  public void add(Prodotto p) {
    this.add(p, ++codiceCorrente);
  }

  // R : read
  public Vector<Prodotto> getAs(Predicate<Prodotto> cmp) {
    Vector<Prodotto> results = new Vector<>();
    for (Prodotto p : prodotti) {
      if (cmp.test(p))
        results.add(p);
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
  public Prodotto delete(int codice, Predicate<Prodotto> cmp) {
    for (int i = 0; i < prodotti.size(); i++) {
      Prodotto p = prodotti.get(i);
      if (p.codice == codice) {
        prodotti.remove(i);
        return p;
      }
    }
    return null;
  }

  // Others
  public String toString() {
    String out = "";
    for (Prodotto p : prodotti) {
      out += p.codice + " -> " + p.nome + "\n";
    }
    return out.subSequence(0, out.length() - 1).toString();
  }

}
