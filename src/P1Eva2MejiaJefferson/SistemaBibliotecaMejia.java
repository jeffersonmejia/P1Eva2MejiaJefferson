package P1Eva2MejiaJefferson;

import java.util.Scanner;//PEDIDO DE DATOS POR TECLADO

public class SistemaBibliotecaMejia {
	// MÍNIMO UN ATRIBUTO
	private BibliotecaMejia libros[];
	private Scanner scanner;
	private int nLibros, totalLibros, nCompra;
	private double costoAlquilar;
	private String nombreLibro, editorialLibro, anioLibro, areaConocimiento, autorLibro, libroSolicitado;
	boolean existeLibro;

	public SistemaBibliotecaMejia(int nCompra) {
		// INICIALIZACIÓN ATRIBUTOS POR PARAMTERO
		this.nCompra = nCompra;
		// INICIALIZACION SCANNER
		scanner = new Scanner(System.in);
		// INICIALIZACION ATRIBUTOS
		nombreLibro = "";
		editorialLibro = "";
		anioLibro = "";
		areaConocimiento = "";
		autorLibro = "";
		libroSolicitado = "";
		nLibros = 0;
		costoAlquilar = 0;

		// INICIALIZACION ATRIBUTOS PARA LÓGICA
		existeLibro = false;

	}

	public void agregarLibro() {
		System.out.println("------------------------------");
		System.out.println("MENÚ > AGREGAR");
		do {
			System.out.print("Ingresa la cantidad de libros a ingresar (1-3): ");
			nLibros = scanner.nextInt();
		} while (nLibros < 1 || nLibros > 3);
		totalLibros += nLibros;
		libros = new BibliotecaMejia[nLibros];

		// PEDIDO DATOS POR TECLADO
		for (int i = 0; i < nLibros; i++) {
			scanner = new Scanner(System.in);
			if (i > 0) {
				System.out.println("------------------------------");
			}
			System.out.print("Ingresa el nombre: ");
			nombreLibro = scanner.nextLine();

			System.out.print("Ingresa el editorial: ");
			editorialLibro = scanner.nextLine();

			// CONTROL AÑO 4 DIGITOS
			do {
				System.out.print("Ingresa el año de edición (4 dígitos): ");
				anioLibro = scanner.nextLine();
			} while (anioLibro.length() != 4);

			System.out.print("Ingresa el área de estudio: ");
			areaConocimiento = scanner.nextLine();

			System.out.print("Ingresa el autor: ");
			autorLibro = scanner.nextLine();

			// CONTROL ALQULADO 10-50
			do {
				System.out.print("Ingresa el costo de alquilado por día $(10-50): ");
				costoAlquilar = scanner.nextDouble();
			} while (costoAlquilar < 10 || costoAlquilar > 50);

			// CREACIÓN NUEVO OBJETO LIBRO
			libros[i] = new BibliotecaMejia(nombreLibro, editorialLibro, anioLibro, areaConocimiento, autorLibro, i + 1,
					costoAlquilar);
		}
		System.out.println("------------------------------");
		if (nLibros == 1) {
			System.out.print(nLibros + " libro ha sido ingresado con éxito: ");
		} else if (nLibros > 0) {
			System.out.print(nLibros + " libros han sido ingresados con éxito: ");
		}
		for (int i = 0; i < nLibros; i++) {
			System.out.print(libros[i].getNombreLibro());
			if (i + 1 != nLibros) {
				System.out.print(", ");
			}
		}
		System.out.println("");
	}

	public void pedirLibro() {
		System.out.println("------------------------------");
		System.out.println("MENÚ > PEDIR");
		consultarLibro();
		if (existeLibro) {
			System.out.println("------------------------------");
			System.out.println("El libro: " + nombreLibro + " alquilado con éxito");
			libroSolicitado = nombreLibro;
		}
	}

	public void devolverLibro() {
		System.out.println("------------------------------");
		System.out.println("MENÚ > DEVOLVER");
		scanner = new Scanner(System.in);
		// CONSULTA POR TECLADO
		System.out.print("Ingresa el nombre del libro: ");
		nombreLibro = scanner.nextLine();
		if (libroSolicitado.equals(nombreLibro)) {
			System.out.println("El libro: " + nombreLibro + " devuelto con éxito, sin recargos");
			libroSolicitado = "";
		} else {
			System.out.println("El libro: " + nombreLibro + " no ha sido pedido por el usuario");
		}
	}

	public void consultarLibro() {
		if (libros != null) {
			// LISTA LIBROS DISPONIBLES
			System.out.print("Libros disponibles: ");
			for (int i = 0; i < totalLibros; i++) {
				System.out.print(libros[i].getNombreLibro());
				if (i + 1 != totalLibros) {
					System.out.print(", ");
				}
			}
			System.out.println("");

			scanner = new Scanner(System.in);
			// CONSULTA POR TECLADO
			System.out.print("Ingresa el nombre del libro: ");
			nombreLibro = scanner.nextLine();
			// BUSQUEDA INFORMACION LIBRO
			System.out.println("------------------------------");
			for (int i = 0; i < totalLibros; i++) {
				if (libros[i].getNombreLibro().equals(nombreLibro)) {
					System.out.println("ID: " + libros[i].getnLibro());
					System.out.println("Nombre: " + libros[i].getNombreLibro());
					System.out.println("Editorial: " + libros[i].getEditorialLibro());
					System.out.println("Año edición: " + libros[i].getAnioLibrio());
					System.out.println("Área estudio: " + libros[i].getAreaConocimiento());
					System.out.println("Autor: " + libros[i].getAutorLibro());
					System.out.println("Costo alquilado hora (USD): " + libros[i].getCostoAlquilar());
					existeLibro = true;
					// ROMPE EL CICLO CUANDO ENCUENTRE EL LIBRO
					i = totalLibros;
				} else {
					// NO EXISTE EL LIBRO
					existeLibro = false;
				}
			}
			if (!existeLibro) {
				// AVISO QUE N EXITE LIBRO
				System.out.println("El libro ingresado no existe");
			}
			// NO EXISTEN LIBROS
		} else {
			System.out.println("------------------------------");
			System.out.println("No hay libros disponibles");
		}
	}

	// SALIR DEL MENÚ
	public void salir() {
		System.out.println("Saliste del sistema, vuelve pronto");
	}
}
