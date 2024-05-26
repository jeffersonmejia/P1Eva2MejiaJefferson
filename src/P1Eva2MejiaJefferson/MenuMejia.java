package P1Eva2MejiaJefferson;

import java.util.Scanner;

public class MenuMejia {

	public static void main(String[] args) {
		int opcion = 0, nCompra = 0, OPCION_SALIR = 5;
		Scanner scanner = new Scanner(System.in);
		// INICIALIZACIÓN SISTEMA BIBLIOTECARIO
		SistemaBibliotecaMejia sistema = new SistemaBibliotecaMejia(nCompra);
		do {
			System.out.println("------------------------------");
			System.out.println("BIENVENIDO: SISTEMA BIBLIOTECARIO");
			System.out.println("1. Registrar ");
			System.out.println("2. Pedir ");
			System.out.println("3. Devolver ");
			System.out.println("4. Consultar ");
			;
			System.out.println(OPCION_SALIR + ". Salir");
			System.out.print("Selecciona una opcion (1-" + OPCION_SALIR + "): ");
			opcion = scanner.nextInt();
			// MENÚ DE OPCIONES
			switch (opcion) {
			case 1: {
				sistema.agregarLibro();
				break;
			}
			case 2: {
				sistema.pedirLibro();
				break;
			}
			case 3: {
				sistema.devolverLibro();
				break;
			}
			case 4: {
				sistema.consultarLibro();
				break;
			}
			case 5: {
				sistema.salir();
				break;
			}
			default: {
				System.out.println("Error, opción inválida");
				break;
			}
			}
		} while (opcion != OPCION_SALIR);
		scanner.close();
	}
}
