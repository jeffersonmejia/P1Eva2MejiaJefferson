package P1Eva2MejiaJefferson;

public class BibliotecaMejia {
	// AGREGAR 3 ATRIBUTOS
	private String idLibro, nombreLibro, editorialLibro, areaConocimiento, autorLibro;
	private int anioLibrio;
	private double costoAlquilar;

	public BibliotecaMejia(String idLibro, String nombreLibro, String editorialLibro, int anioLibrio,
			String areaConocimiento, String autorLibro, double costoAlquilar) {
		this.idLibro = idLibro;
		this.nombreLibro = nombreLibro;
		this.editorialLibro = editorialLibro;
		this.anioLibrio = anioLibrio;
		this.areaConocimiento = areaConocimiento;
		this.autorLibro = autorLibro;
		this.costoAlquilar = costoAlquilar;
	}

	public String getIdLibro() {
		return idLibro;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public String getEditorialLibro() {
		return editorialLibro;
	}

	public int getAnioLibrio() {
		return anioLibrio;
	}

	public String getAreaConocimiento() {
		return areaConocimiento;
	}

	public String getAutorLibro() {
		return autorLibro;
	}

	public double getCostoAlquilar() {
		return costoAlquilar;
	}

}
