package P1Eva2MejiaJefferson;

import java.io.BufferedReader;//LECTOR BUFFER 
import java.io.BufferedWriter;//ESCRITOR BUFFER
import java.io.File;//CREACION DE ARCHIVOS
import java.io.FileReader;//LECTURA ARCHIVOS
import java.io.FileWriter;//ESCRITURA ARCHIVOS
import java.io.IOException;//MANEJO EXCEPCIONES ENTRADA/SALIDA
import java.util.Scanner;//PEDIDO DE DATOS POR TECLADO

import org.json.simple.JSONArray;//CREAR ARRAY JSON
import org.json.simple.JSONObject;//CREAR OBJETO JSON
import org.json.simple.parser.JSONParser;//CONVIERTE JSON-ARRAY/OBJETO
import org.json.simple.parser.ParseException;//MANEJO ERRORES JSON

public class SistemaBibliotecaMejia {
	// MÍNIMO AGREGAR UN ATRIBUTO
	private BibliotecaMejia libros[];
	String[] datosCsv;
	private Scanner scanner;
	private int nLibros, nCompra, anioLibro, nCompraBuscar;
	private double costoAlquilar;
	private boolean existeLibro, esConsultaNombre, libroEliminado, primeraLineaCsv;
	private String nombreLibro, editorialLibro, areaConocimiento, autorLibro, libroSolicitado, NOMBRE_ARCHIVO,
			NOMBRE_ARCHIVO_CSV, NOMBRE_ARCHIVO_TXT, idLibro, lineaLecturaCsv;
	private JSONObject libroJSON;
	private JSONArray librosAnteriores;
	private JSONParser parser;
	private JSONArray bibliotecaJSON;
	private JSONArray librosJSON;
	private BufferedWriter escritorCsv;
	private StringBuilder contenidoCsv;
	private FileWriter escritorTxt;
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
		esConsultaNombre = false;
		existeLibro = false;
		// INICIALIZACIÓN RUTA ARCHIVOS
		NOMBRE_ARCHIVO = "libros.json";
		NOMBRE_ARCHIVO_CSV = "compras.csv";
		NOMBRE_ARCHIVO_TXT = "devoluciones.txt";
		// INICIALIZACION .JSON
		libroJSON = new JSONObject();
		parser = new JSONParser();
		librosJSON = new JSONArray();
		bibliotecaJSON = new JSONArray();
		librosAnteriores = new JSONArray();
		libroEliminado = false;
		// INICIALIZACION .CSV
		contenidoCsv = new StringBuilder();
		primeraLineaCsv = true;
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

	public void consultarLibro() {
		librosJSON = new JSONArray();
		scanner = new Scanner(System.in);
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
			System.out.println("DATOS");
			if (esConsultaNombre) {
				System.out.println("Libros disponibles: ");
			}
			// IMPRIMIR DATOS
			for (Object libroObj : librosJSON) {
				libroJSON = (JSONObject) libroObj;
				if (esConsultaNombre) {
					System.out.println("- " + libroJSON.get("Nombre"));
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
			}
		} catch (IOException | ParseException e) {
			// IMPRIME ERROR SI NO EXISTE ARCHIVO
			System.out.println("No hay datos anteriores en " + NOMBRE_ARCHIVO);
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
		// MUESTRA LIBROS DISPONIBLES
		consultarLibro();
		existeLibro = buscarLibro();
		if (existeLibro) {
			try {
				// CREA ESCRITOR .CSV
				escritorCsv = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO_CSV, true));
				if (new File(NOMBRE_ARCHIVO_CSV).length() == 0) {
					// CREA .CSV POR PRIMERA VEZ
					escritorCsv.write("nCompra,nombreLibro");
					escritorCsv.newLine();
				}
				// CREA NUEVO ID COMPRA SI YA EXISTE
				do {
					nCompra++;
				} while (buscarNCompra(nCompra));
				// AGREGA DATOS AL CSV
				escritorCsv.write(nCompra + ", " + nombreLibro);
				escritorCsv.newLine();
				// CIERRA CSV
				escritorCsv.close();
				// AVISA AL USUARIO QUE SE GUARDÓ LA COMPRA
				System.out.println("Libro solicitado con éxito - compra#" + nCompra);
				libroSolicitado = nombreLibro;
			} catch (Exception e) {
				System.out.println("No se ha guardado la compra en " + NOMBRE_ARCHIVO_CSV);
			}
		} else {
			System.out.println("El libro no existe");
		}
		esConsultaNombre = false;
	}

	public void verCompras() {
		System.out.println("------------------------------");
		try (BufferedReader lectorCsv = new BufferedReader(new FileReader(NOMBRE_ARCHIVO_CSV))) {
			System.out.println("PEDIDOS");
			System.out.println("------------------------------");
			// LEE .CSV LINEA A LINEA
			while ((lineaLecturaCsv = lectorCsv.readLine()) != null) {
				System.out.println(lineaLecturaCsv);

			}
			// MANEJO ERRORES
		} catch (IOException e) {
			System.out.println("No se ha podido leer el archivo " + NOMBRE_ARCHIVO_CSV);
			e.printStackTrace();
		}
		System.out.println("------------------------------");
	}

	public boolean buscarNCompra(int nCompra) {
		// BUSCA LIBRO POR NUMERO DE COMPRA
		try (BufferedReader lectorCsv = new BufferedReader(new FileReader(NOMBRE_ARCHIVO_CSV))) {
			while ((lineaLecturaCsv = lectorCsv.readLine()) != null) {
				datosCsv = lineaLecturaCsv.split(",");
				if (datosCsv.length > 0 && datosCsv[0].trim().equals(String.valueOf(nCompra))) {
					return true;
				}
			}
		} catch (IOException e) {
			System.out.println("No se ha podido leer el archivo " + NOMBRE_ARCHIVO_CSV);
			e.printStackTrace();
		}
		return false;
	}

	public boolean buscarLibro() {
		// PIDE NOMBRE LIBRO
		System.out.print("Ingresa el nombre del libro: ");
		nombreLibro = scanner.nextLine();
		librosJSON = obtenerLibrosAnteriores();
		// VERIFICA EXISTENCIA LIBRO
		for (Object libroObj : librosJSON) {
			JSONObject libroJSON = (JSONObject) libroObj;
			// SI EXISTE MUESTRA DATOS
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

	public boolean buscarLibroCompra(String nombreLibro) {
		// BUSCA LIBRO POR ID COMPRA
		try (BufferedReader lectorCsv = new BufferedReader(new FileReader(NOMBRE_ARCHIVO_CSV))) {
			// LEE .CSV
			while ((lineaLecturaCsv = lectorCsv.readLine()) != null) {
				datosCsv = lineaLecturaCsv.split(",");
				if (datosCsv.length > 1 && datosCsv[1].trim().equalsIgnoreCase(nombreLibro)) {
					return true;
				}
			}
			// MANEJO ERROR SI NO EXISTE .CSV
		} catch (IOException e) {
			System.out.println("No se ha podido leer el archivo " + NOMBRE_ARCHIVO_CSV);
			e.printStackTrace();
		}
		return false;
	}

	public void devolverLibro() {
		scanner = new Scanner(System.in);
		System.out.println("------------------------------");
		System.out.println("MENÚ > DEVOLVER");
		verCompras();
		// CONSULTA POR TECLADO
		System.out.print("Ingresa el numero de compra: ");
		nCompraBuscar = scanner.nextInt();
		scanner = new Scanner(System.in);
		// MUESTRA EL LIBRO SOLICITADO POR EL USUARIO
		if (buscarNCompra(nCompraBuscar)) {
			eliminarCompra(nCompraBuscar);
		} else {
			// EL LIBRO NO EXISTE, NO PUEDE SER DEVUELTO
			System.out.println("El ID " + nCompraBuscar + " de la compra no existe");
		}
	}

	public void eliminarCompra(int nCompraEliminar) {
		libroEliminado = false;
		try (BufferedReader lectorCsv = new BufferedReader(new FileReader(NOMBRE_ARCHIVO_CSV))) {
			contenidoCsv = new StringBuilder();

			// IDENTIFICA PRIMERA LINEA .CSV
			primeraLineaCsv = true;
			while ((lineaLecturaCsv = lectorCsv.readLine()) != null) {
				if (primeraLineaCsv) {
					// IGNORA PRIMERA LINEA (CABEZERA)
					contenidoCsv.append(lineaLecturaCsv).append("\n");
					primeraLineaCsv = false;
					continue;
				}
				// SEPARA DATOS .CSV
				datosCsv = lineaLecturaCsv.split(",");
				if (datosCsv.length > 0 && Integer.parseInt(datosCsv[0].trim()) != nCompraEliminar) {
					contenidoCsv.append(lineaLecturaCsv).append("\n");
					nombreLibro = datosCsv[1];
				} else {
					libroEliminado = true;
				}
			}
			// ACTUALIZA .CSV, ELIMINA COMPRA
			if (libroEliminado) {
				try (BufferedWriter escritorCsv = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO_CSV))) {
					escritorCsv.write(contenidoCsv.toString());
					// GUARDADO DEVOLUCIONES .TXT
					try {
						escritorTxt = new FileWriter(NOMBRE_ARCHIVO_TXT, true);
						escritorTxt.write("------------------------------\n");
						escritorTxt.write("ID: " + nCompraEliminar + "\n");
						escritorTxt.write("Libro: " + nombreLibro + "\n");
						escritorTxt.close();
						System.out.println("Libro " + nombreLibro + " - ID " + nCompraEliminar + " ha sido devuelto sin recargos");
						// MANEJO ERROR .TXT
					} catch (IOException e) {
						System.out.println("Error: no se ha guardado el registro de devolución ");
					}
					// MANEJO ERROR COMPRA ELIMINADA .CSV
				} catch (IOException e) {
					System.out.println("Error al escribir en el archivo " + NOMBRE_ARCHIVO_CSV);
				}
				// COMPRA NO EXISTE
			} else {
				System.out.println("No se encontró la compra con el número " + nCompraEliminar);
			}
			// EL ARCHIVO NO EXISTE
		} catch (IOException e) {
			System.out.println("Error al leer el archivo " + NOMBRE_ARCHIVO_CSV);
		}
	}

	public void salir() {
		// SALIR DEL MENÚ
		System.out.println("Saliste del sistema, vuelve pronto");
	}
}
