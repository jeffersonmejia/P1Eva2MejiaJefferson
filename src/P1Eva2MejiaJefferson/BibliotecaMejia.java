package P1Eva2MejiaJefferson;

public class BibliotecaMejia {
	// AGREGAR 3 ATRIBUTOS
	private String nombreLibro, editorialLibro, anioLibrio, areaConocimiento, autorLibro;
	private int nLibro;
	private double costoAlquilar;

	public BibliotecaMejia(String nombreLibro, String editorialLibro, String anioLibrio, String areaConocimiento,
			String autorLibro, int nLibro, double costoAlquilar) {
		this.nombreLibro = nombreLibro;
		this.editorialLibro = editorialLibro;
		this.anioLibrio = anioLibrio;
		this.areaConocimiento = areaConocimiento;
		this.autorLibro = autorLibro;
		this.nLibro = nLibro;
		this.costoAlquilar = costoAlquilar;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}

	public String getEditorialLibro() {
		return editorialLibro;
	}

	public void setEditorialLibro(String editorialLibro) {
		this.editorialLibro = editorialLibro;
	}

	public String getAnioLibrio() {
		return anioLibrio;
	}

	public void setAnioLibrio(String anioLibrio) {
		this.anioLibrio = anioLibrio;
	}

	public String getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public String getAutorLibro() {
		return autorLibro;
	}

	public void setAutorLibro(String autorLibro) {
		this.autorLibro = autorLibro;
	}

	public int getnLibro() {
		return nLibro;
	}

	public double getCostoAlquilar() {
		return costoAlquilar;
	}

}
