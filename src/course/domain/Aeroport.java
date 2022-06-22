package course.domain;

public class Aeroport extends DomainType {

	private static final long serialVersionUID = -1400278242161089165L;

	private String nazva;
	private String kraina;
	private String micto;
	private String adresa;
	private String telephon;
	private int dovjuna;
	private int shuruna;
	private int aeroID;

	public String getnazva() {
		return nazva;
	}

	public void setnazva(String nazva) {
		this.nazva = nazva;
	}

	public String getkraina() {
		return kraina;
	}

	public void setkraina(String kraina) {
		this.kraina = kraina;
	}

	public String getmicto() {
		return micto;
	}

	public void setmicto(String micto) {
		this.micto = micto;

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

	public int getdovjuna() {
		return dovjuna;
	}

	public void setdovjuna(int dovjuna) {
		this.dovjuna = dovjuna;

	}

	public int getshuruna() {
		return shuruna;
	}

	public void setshuruna(int shuruna) {
		this.shuruna = shuruna;

	}

	public int getAeroID() {
		return aeroID;
	}

	public void setAeroID(int aeroID) {
		this.aeroID = aeroID;
	}
}