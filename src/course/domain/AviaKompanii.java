package course.domain;

public class AviaKompanii extends DomainType {

	private static final long serialVersionUID = -1400278242161089165L;

	private String nazva;
	private String misto;
	private String adresa;
	private String telephon;
	private String fax;
	private int rik_zasnyvanna;

	public String getnazva() {
		return nazva;
	}

	public void setnazva(String nazva) {
		this.nazva = nazva;
	}

	public String getmisto() {
		return misto;
	}

	public void setmisto(String misto) {
		this.misto = misto;

	}

	public String getadresa() {
		return adresa;
	}

	public void setadresa(String adresa) {
		this.adresa = adresa;

	}

	public String gettelephon() {
		return telephon;
	}

	public void settelephon(String telephon) {
		this.telephon = telephon;

	}

	public String getfax() {
		return fax;
	}

	public void setfax(String fax) {
		this.fax = fax;

	}

	public int getrik_zasnyvanna() {
		return rik_zasnyvanna;
	}

	public void setrik_zasnyvanna(int rik_zasnyvanna) {
		this.rik_zasnyvanna = rik_zasnyvanna;

	}
}
