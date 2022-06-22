package course.domain;

public class Rozklad extends DomainType {

	private static final long serialVersionUID = -1400278242161089165L;

	private int RozkladAerokompaniiID;
	private String poch_pynkt;
	private String kinc_pynkt;
	private String reis;
	private String dni;
	private String vidprav;
	private String prubyttya;

	public int getRozkladAerokompaniiID() {
		return RozkladAerokompaniiID;
	}

	public void setRozkladAerokompaniiID(int RozkladAerokompaniiID) {
		this.RozkladAerokompaniiID = RozkladAerokompaniiID;
	}

	public String getpoch_pynkt() {
		return poch_pynkt;
	}

	public void setpoch_pynkt(String poch_pynkt) {
		this.poch_pynkt = poch_pynkt;
	}

	public String getkinc_pynkt() {
		return kinc_pynkt;
	}

	public void setkinc_pynkt(String kinc_pynkt) {
		this.kinc_pynkt = kinc_pynkt;

	}

	public String getreis() {
		return reis;
	}

	public void setreis(String reis) {
		this.reis = reis;

	}

	public String getdni() {
		return dni;
	}

	public void setdni(String dni) {
		this.dni = dni;

	}

	public String getvidprav() {
		return vidprav;
	}

	public void setvidprav(String vidprav) {
		this.vidprav = vidprav;

	}

	public String getprubyttya() {
		return prubyttya;
	}

	public void setprubyttya(String prubyttya) {
		this.prubyttya = prubyttya;

	}
}
