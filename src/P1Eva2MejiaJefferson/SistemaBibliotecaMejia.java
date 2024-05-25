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
	private boolean existeLibro;
	private String nombreLibro, editorialLibro, areaConocimiento, autorLibro, libroSolicitado, NOMBRE_ARCHIVO;
	private JSONObject libroJSON;
	private JSONArray librosAnteriores;
	private JSONParser parser;
	private JSONObject bibliotecaJSON;
	private JSONArray librosJSON;
	private JSONArray librosNuevos;
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

		// INICIALIZACION ATRIBUTOS PARA LÓGICA
		existeLibro = false;
		// INICIALIZACIÓN ARCHIVOS
		NOMBRE_ARCHIVO = "libros.json";
		// INICIALIZACION JSON
		libroJSON = new JSONObject();
		parser = new JSONParser();
		librosJSON = new JSONArray();
		bibliotecaJSON = new JSONObject();
		librosNuevos = new JSONArray();
		librosAnteriores = new JSONArray();
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

			// CREACIÓN NUEVO OBJETO LIBRO
			libros[i] = new BibliotecaMejia(nombreLibro, editorialLibro, anioLibro, areaConocimiento, autorLibro, i + 1,
					costoAlquilar);
		}
		guardarLibrosJSON();
	}

	public void guardarLibrosJSON() {
		// TRAER DATOS GUARDADOS DEL JSON
		/*
		 * librosAnteriores = leerLibrosJSON(); if (librosAnteriores != null) {
		 * librosNuevos.addAll(librosAnteriores); }
		 */

		// GUARDAR NUEVOS DATOS EN JSON
		for (int i = 0; i < libros.length; i++) {
			libroJSON = new JSONObject();
			libroJSON.put("ID", libros[i].getnLibro());
			libroJSON.put("Nombre", libros[i].getNombreLibro());
			libroJSON.put("Editorial", libros[i].getEditorialLibro());
			libroJSON.put("Año edicion", libros[i].getAnioLibrio());
			libroJSON.put("Area estudio", libros[i].getAreaConocimiento());
			libroJSON.put("Autor", libros[i].getAutorLibro());
			libroJSON.put("Costo alquilado dia (USD)", libros[i].getCostoAlquilar());
			librosNuevos.add(libroJSON);
		}

		// GUARDA EL ARRAY DE LIBROS EN OBJETO JSON
		bibliotecaJSON.put("libros", librosNuevos);
		// CONTROL ERROR ARCHIVOS
		try (FileWriter file = new FileWriter(NOMBRE_ARCHIVO)) {
			// ESCRITURA JSON
			file.write(libroJSON.toJSONString());
			// LIMPIAR BUFFER ARCHIVO
			file.flush();
			leerLibrosJSON();
		} catch (Exception e) {
			// IMPRIME ERRORES SI NO GUARDA EL ARCHIVO
			e.printStackTrace();
			System.out.println("El sistema no ha podido guardar los libros");
		}
	}

	public void leerLibrosJSON() {
		librosJSON = new JSONArray();
		try (FileReader reader = new FileReader(NOMBRE_ARCHIVO)) {
			parser = new JSONParser();
			objectParser = parser.parse(reader);// aqui es el error
			if (objectParser instanceof JSONObject) {
				librosJSON.add((JSONObject) objectParser);
			} else if (objectParser instanceof JSONArray) {
				// CONVERSIÓN JSON A ARRAY
				librosJSON = (JSONArray) objectParser;
			}
			// MOSTRAR UBICACION JSON
			System.out.println("Libros guardados con éxito");
			System.out.println("------------------------------");
			// IMPRIMIR DATOS
			System.out.println("DATOS");
			for (Object libroObj : librosJSON) {
				JSONObject libroJSON = (JSONObject) libroObj;
				System.out.println("ID: " + libroJSON.get("ID"));
				System.out.println("Nombre: " + libroJSON.get("Nombre"));
				System.out.println("Editorial: " + libroJSON.get("Editorial"));
				System.out.println("Año de edición: " + libroJSON.get("Año edicion"));
				System.out.println("Area de estudio: " + libroJSON.get("Area estudio"));
				System.out.println("Autor: " + libroJSON.get("Autor"));
				System.out.println("Costo alquilado dia (USD): " + libroJSON.get("Costo alquilado dia (USD)"));

			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			System.out.println("Archivo no encontrado/vacío: " + NOMBRE_ARCHIVO);
		}
	}

	public void pedirLibro() {
		System.out.println("------------------------------");
		System.out.println("MENÚ > PEDIR");
		consultarLibro();
		if (existeLibro) {
			System.out.println("------------------------------");
			System.out.println("El libro: " + nombreLibro + " ha sido alquilado con éxito");
			System.out.println("Compra#: " + nCompra);
			libroSolicitado = nombreLibro;
			nCompra++;
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
			for (int i = 0; i < libros.length; i++) {
				System.out.print(libros[i].getNombreLibro());
				if (i + 1 != libros.length) {
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
			for (int i = 0; i < libros.length; i++) {
				if (libros[i].getNombreLibro().equals(nombreLibro)) {
					System.out.println("ID: " + libros[i].getnLibro());
					System.out.println("Nombre: " + libros[i].getNombreLibro());
					System.out.println("Editorial: " + libros[i].getEditorialLibro());
					System.out.println("Año edición: " + libros[i].getAnioLibrio());
					System.out.println("Área estudio: " + libros[i].getAreaConocimiento());
					System.out.println("Autor: " + libros[i].getAutorLibro());
					System.out.println("Costo alquilado dia (USD): " + libros[i].getCostoAlquilar());
					existeLibro = true;
					// ROMPE EL CICLO CUANDO ENCUENTRE EL LIBRO
					i = libros.length;
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
