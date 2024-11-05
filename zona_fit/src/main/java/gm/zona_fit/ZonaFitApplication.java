package gm.zona_fit;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {
	@Autowired
	private IClienteServicio clienteServicio;
	private static final Logger logger=
			LoggerFactory.getLogger(ZonaFitApplication.class);

	String nl =System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion");

		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Aplicacion Finalizado");
	}

	@Override
	public void run(String... args) throws Exception {


		zonaFitApp();

	}
	private void zonaFitApp(){
		logger.info("*** Aplicacion Zona Fit(GYM) ***");
		var salir=false;
		var teclado=new Scanner(System.in);
		while (!salir){
			var opcion=mostrarMenu( teclado);
			salir = ejecutarOpciones(teclado,opcion);
			logger.info(nl);
		}

	}

	private int mostrarMenu(Scanner teclado){
		logger.info("""
				\nAplicacion Zona Fit(GYM)
				1.Listar Clientes
				2.Buscar Clientes
				3.Agregar cliente
				4.Modificar cliente
				5.Eliminar cliente
				6.Salir
				Elige una opcion: """);
		return Integer.parseInt(teclado.nextLine());
	}

	private boolean ejecutarOpciones(Scanner teclado, int opcion){
		var salir=false;
		switch (opcion){
			case 1->{
				logger.info(nl +"-----Listado de clientes----- " + nl);
				List<Cliente>clientes =clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString() +nl));
			}
			case 2->{
				logger.info(nl+"---Buscar cliente por Id----"+ nl);
				logger.info("Id cliente buscar: ");
				var	idCliente= Integer.parseInt(teclado.nextLine());
				Cliente cliente=clienteServicio.buscarCliente(idCliente);
				if (cliente!=null){
					logger.info("Cliente encontrado: " +cliente + nl);
				}else {
					logger.info("Cliente no encontrado: " + cliente + nl);
				}
			}
			case 3->{
				logger.info("---Agregar estudiante---" + nl);
				logger.info("Nombre: ");
				var nombre=teclado.nextLine();
				logger.info("Apellido: ");
				var apellido=teclado.nextLine();
				logger.info("Membresia: ");
				var membresia=Integer.parseInt(teclado.nextLine());
				var cliente=new Cliente();
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMembresia(membresia);
				clienteServicio.guardarCliente(cliente);
				logger.info("Cliente agregado: "+ cliente + nl);
			}
			case 4->{
				logger.info("----Modificar cliente----");
				logger.info("Id Cliente: ");
				var idCliente=Integer.parseInt(teclado.nextLine());
				Cliente cliente=clienteServicio.buscarCliente(idCliente);
				if (cliente!=null){
					logger.info("Nombre: ");
					var nombre=teclado.nextLine();
					logger.info("Apellido: ");
					var apellido=teclado.nextLine();
					logger.info("Membresia: ");
					var membresia=Integer.parseInt(teclado.nextLine());
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);
					clienteServicio.guardarCliente(cliente);
					logger.info("Cliente modificado : " + cliente + nl);

				}
				else {
					logger.info("Cliente no encontrado " +cliente + nl);
				}

			}
			 case 5->{
				logger.info("----Eliminar Cliente----");
				logger.info("Id Cliente: ");
				var idCliente=Integer.parseInt(teclado.nextLine());
				var cliente=clienteServicio.buscarCliente(idCliente);
				if (cliente!=null){
					clienteServicio.elimnarCliente(cliente);
					logger.info("Cliente eliminado " + cliente + nl);
				}
				else {
					logger.info("Cliente no encontrado: " +cliente + nl);
				}

			 }
			 case 6->{
				logger.info("Hasta pronto! " +nl + nl);
				salir=true;
			 }
			default -> logger.info("Opcion no valida : " + opcion + nl);

		}
		return salir;

	}



}
