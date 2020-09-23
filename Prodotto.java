import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Vector;

public class Prodotto {

  public Reparto reparto;
  public String categoria, scaffale, nome, produttore, unitàMisura, responsabile, telefono;
  public int quantità, codice;
  public double prezzo, sconto;
  public GregorianCalendar scadenza;

  Prodotto() {
    // Defaults for primitive types
    quantità = -1;
    codice = -1;
    prezzo = -1;
    sconto = -1;
  }

  public Vector<String> toVector() {
    Vector<String> out = new Vector<>();

    out.add((reparto != null) ? reparto.toString() : "");

    out.add((categoria != null) ? categoria : "");
    out.add((scaffale != null) ? scaffale : "");
    out.add((nome != null) ? nome : "");
    out.add((produttore != null) ? produttore : "");
    out.add((unitàMisura != null) ? unitàMisura : "");
    out.add((responsabile != null) ? responsabile : "");
    out.add((telefono != null) ? telefono : "");

    out.add((quantità != -1) ? String.valueOf(quantità) : "");
    out.add((codice != -1) ? String.valueOf(codice) : "");

    out.add((prezzo != -1) ? String.valueOf(prezzo) : "");
    out.add((sconto != -1) ? String.valueOf(sconto) : "");

    if (scadenza != null) {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      out.add(formatter.format(scadenza.getTime()));
    } else {
      out.add("");
    }

    return out;
  }

  public static Prodotto fromVector(Vector<String> in) {
    Prodotto p = new Prodotto();

    p.reparto = (in.get(0).equals("")) ? null : Reparto.valueOf(in.get(0));

    p.categoria = (in.get(1).equals("")) ? null : in.get(1);
    p.scaffale = (in.get(2).equals("")) ? null : in.get(2);
    p.nome = (in.get(3).equals("")) ? null : in.get(3);
    p.produttore = (in.get(4).equals("")) ? null : in.get(4);
    p.unitàMisura = (in.get(5).equals("")) ? null : in.get(5);
    p.responsabile = (in.get(6).equals("")) ? null : in.get(6);
    p.telefono = (in.get(7).equals("")) ? null : in.get(7);

    p.quantità = (in.get(8).equals("")) ? -1 : Integer.parseInt(in.get(8));
    p.codice = (in.get(9).equals("")) ? -1 : Integer.parseInt(in.get(9));

    p.prezzo = (in.get(10).equals("")) ? -1 : Double.parseDouble(in.get(10));
    p.sconto = (in.get(11).equals("")) ? -1 : Double.parseDouble(in.get(11));

    if (!in.get(12).equals("")) {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      p.scadenza = new GregorianCalendar();
      try {
        p.scadenza.setTime(formatter.parse(in.get(12)));
      } catch (ParseException e) {
        p.scadenza = null;
      }
    } else {
      p.scadenza = null;
    }

    return p;
  }

}
