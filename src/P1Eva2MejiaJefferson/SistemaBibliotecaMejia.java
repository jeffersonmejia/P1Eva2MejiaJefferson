package P1Eva2MejiaJefferson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;//PEDIDO DE DATOS POR TECLADO

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SistemaBibliotecaMejia {
	// MÍNIMO AGREGAR UN ATRIBUTO
	private BibliotecaMejia libros[];
	private Scanner scanner;
	private int nLibros, nCompra, anioLibro;
	private double costoAlquilar;
	private boolean existeLibro, esJsonVacio, esConsultaNombre;
	private String nombreLibro, editorialLibro, areaConocimiento, autorLibro, libroSolicitado, NOMBRE_ARCHIVO, idLibro;
	private JSONObject libroJSON;
	private JSONArray librosAnteriores;
	private JSONParser parser;
	private JSONArray bibliotecaJSON;
	private JSONArray librosJSON;
	private JSONArray librosActuales;

	private Object objectParser;

	public SistemaBibliotecaMejia(int nCompra) {
		// INICIALIZACIÓN ATRIBUTOS POR PARAMTERO
		this.nCompra = nCompra;
		// INICIALIZACION SCANNER
		scanner = new Scanner(System.in);
		// INICIALIZACION ATRIBUTOS
		nombreLibro = "";
		editorialLibro = "";
		anioLibro = 0;
		areaConocimiento = "";
		autorLibro = "";
		libroSolicitado = "";
		nLibros = 0;
		costoAlquilar = 0;
		esJsonVacio = true;
		esConsultaNombre = false;

		// INICIALIZACION ATRIBUTOS PARA LÓGICA
		existeLibro = false;
		// INICIALIZACIÓN ARCHIVOS
		NOMBRE_ARCHIVO = "libros.json";
		// INICIALIZACION JSON
		libroJSON = new JSONObject();
		parser = new JSONParser();
		librosJSON = new JSONArray();
		bibliotecaJSON = new JSONArray();
		librosAnteriores = new JSONArray();
		librosActuales = new JSONArray();
		objectParser = null;

	}

	public void agregarLibro() {
		System.out.println("------------------------------");
		System.out.println("MENÚ > AGREGAR");
		do {
			System.out.print("Ingresa la cantidad de libros a ingresar (1-3): ");
			nLibros = scanner.nextInt();
		} while (nLibros < 1 || nLibros > 3);

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
				System.out.print("Ingresa el año de edición (2000-2024): ");
				anioLibro = scanner.nextInt();
			} while (anioLibro < 2000 || anioLibro > 2024);

			scanner = new Scanner(System.in);
			System.out.print("Ingresa el área de estudio: ");
			areaConocimiento = scanner.nextLine();

			System.out.print("Ingresa el autor: ");
			autorLibro = scanner.nextLine();

			// CONTROL ALQULADO 10-50
			do {
				System.out.print("Ingresa el costo de alquilado por día $(10-50): ");
				costoAlquilar = scanner.nextDouble();
			} while (costoAlquilar < 10 || costoAlquilar > 50);

			idLibro = (i + 1) + "_" + editorialLibro.toUpperCase();
			// CREACIÓN NUEVO OBJETO LIBRO
			libros[i] = new BibliotecaMejia(idLibro, nombreLibro, editorialLibro, anioLibro, areaConocimiento, autorLibro,
					costoAlquilar);
		}
		guardarLibrosJSON();
	}

	public void guardarLibrosJSON() {
		// TRAER DATOS GUARDADOS DEL JSON
		librosAnteriores = obtenerLibrosAnteriores();

		if (librosAnteriores != null) {
			bibliotecaJSON.addAll(librosAnteriores);
		}

		// GUARDAR NUEVOS DATOS EN JSON
		for (int i = 0; i < libros.length; i++) {
			libroJSON = new JSONObject();
			libroJSON.put("ID", libros[i].getIdLibro());
			libroJSON.put("Nombre", libros[i].getNombreLibro());
			libroJSON.put("Editorial", libros[i].getEditorialLibro());
			libroJSON.put("Año edicion", libros[i].getAnioLibrio());
			libroJSON.put("Area estudio", libros[i].getAreaConocimiento());
			libroJSON.put("Autor", libros[i].getAutorLibro());
			libroJSON.put("Costo alquilado dia (USD)", libros[i].getCostoAlquilar());
			// GUARDA EL ARRAY DE LIBROS EN ARRAYS JSON
			bibliotecaJSON.add(libroJSON);
		}
		// CONTROL ARCHIVOS
		try (FileWriter file = new FileWriter(NOMBRE_ARCHIVO)) {
			// ESCRITURA JSON
			file.write(bibliotecaJSON.toJSONString());
			// LIMPIAR BUFFER ARCHIVO
			file.flush();
			// MOSTRAR UBICACION JSON
			System.out.println(nLibros + " libro/s se guardó con éxito");
		} catch (Exception e) { //
			// IMPRIME ERRORES SI NO GUARDA EL ARCHIVO
			System.out.println("El archivo no existe, se creara uno nuevo");
		}
		bibliotecaJSON = new JSONArray();
	}

	public void leerLibrosJSON() {
		librosJSON = new JSONArray();
		// MANEJO DE ERRORES SI NO ENCUENTRA .JSON
		try (FileReader reader = new FileReader(NOMBRE_ARCHIVO)) {
			parser = new JSONParser();
			objectParser = parser.parse(reader);
			if (objectParser instanceof JSONObject) {

				librosJSON.add((JSONObject) objectParser);
			} else if (objectParser instanceof JSONArray) {
				// CONVERSIÓN JSON A ARRAY
				librosJSON = (JSONArray) objectParser;
			}
			System.out.println("------------------------------");
			// IMPRIMIR DATOS
			System.out.println("DATOS");
			if (esConsultaNombre) {
				System.out.print("Libros disponibles: ");
			}
			for (Object libroObj : librosJSON) {
				JSONObject libroJSON = (JSONObject) libroObj;
				if (esConsultaNombre) {
					System.out.print(libroJSON.get("Nombre") + ", ");
				} else {
					System.out.println("ID: " + libroJSON.get("ID"));
					System.out.println("Nombre: " + libroJSON.get("Nombre"));
					System.out.println("Editorial: " + libroJSON.get("Editorial"));
					System.out.println("Año de edición: " + libroJSON.get("Año edicion"));
					System.out.println("Area de estudio: " + libroJSON.get("Area estudio"));
					System.out.println("Autor: " + libroJSON.get("Autor"));
					System.out.println("Costo alquilado dia (USD): " + libroJSON.get("Costo alquilado dia (USD)"));
					System.out.println("------------------------------");
				}

				esJsonVacio = false;
			}
			if (esConsultaNombre) {
				System.out.println();
			}
		} catch (IOException | ParseException e) {
			// IMPRIME ERROR SI NO EXISTE ARCHIVO
			System.out.println("No hay datos anteriores en " + NOMBRE_ARCHIVO);
			esJsonVacio = true;
		}
	}

	public JSONArray obtenerLibrosAnteriores() {
		librosJSON = new JSONArray();
		// MANEJO DE ERRORES SI NO ENCUENTRA .JSON
		try (FileReader reader = new FileReader(NOMBRE_ARCHIVO)) {
			parser = new JSONParser();
			objectParser = parser.parse(reader);
			if (objectParser instanceof JSONObject) {
				// CONVERSIÓN JSON A JSON ARRAY
				librosJSON.add((JSONObject) objectParser);
			} else if (objectParser instanceof JSONArray) {
				// ASIGNACIÓN DIRECTA
				librosJSON = (JSONArray) objectParser;
			}

		} catch (IOException | ParseException e) {
			// IMPRIME ERROR SI NO EXISTE ARCHIVO
			System.out.println("No hay datos anteriores en " + NOMBRE_ARCHIVO + ", no se realizarán actualizaciones");
		}
		return librosJSON;
	}

	public void pedirLibro() {
		System.out.println("------------------------------");
		System.out.println("MENÚ > PEDIR");
		esConsultaNombre = true;
		leerLibrosJSON();
		existeLibro = buscarLibro();
		if (existeLibro) {
			System.out.println("Libro solicitado con éxito");
			libroSolicitado = nombreLibro;
		} else {
			System.out.println("El libro no existe");
		}
		esConsultaNombre = false;
	}

	public boolean buscarLibro() {
		System.out.print("Ingresa el nombre del libro: ");
		nombreLibro = scanner.nextLine();
		librosJSON = obtenerLibrosAnteriores();
		for (Object libroObj : librosJSON) {
			JSONObject libroJSON = (JSONObject) libroObj;
			if (libroJSON.get("Nombre").equals(nombreLibro)) {
				System.out.println("------------------------------");
				System.out.println("ID: " + libroJSON.get("ID"));
				System.out.println("Nombre: " + libroJSON.get("Nombre"));
				System.out.println("Editorial: " + libroJSON.get("Editorial"));
				System.out.println("Año de edición: " + libroJSON.get("Año edicion"));
				System.out.println("Area de estudio: " + libroJSON.get("Area estudio"));
				System.out.println("Autor: " + libroJSON.get("Autor"));
				System.out.println("Costo alquilado dia (USD): " + libroJSON.get("Costo alquilado dia (USD)"));
				System.out.println("------------------------------");
				return true;
			}
		}
		return false;
	}

	public void devolverLibro() {
		System.out.println("------------------------------");
		System.out.println("MENÚ > DEVOLVER");
		scanner = new Scanner(System.in);
		// CONSULTA POR TECLADO
		System.out.print("Ingresa el nombre del libro: ");
		nombreLibro = scanner.nextLine();
		// MUESTRA EL LIBRO SOLICITADO POR EL USUARIO
		if (libroSolicitado.equals(nombreLibro)) {
			System.out.println("El libro: " + nombreLibro + " devuelto con éxito, sin recargos");
			libroSolicitado = "";
			// EL LIBRO NO EXISTE, NO PUEDE SER DEVUELTO
		} else {
			System.out.println("El libro: " + nombreLibro + " no ha sido pedido por el usuario");
		}
	}

	public void consultarLibro() {
		leerLibrosJSON();

	}

	// SALIR DEL MENÚ
	public void salir() {
		System.out.println("Saliste del sistema, vuelve pronto");
	}
}
