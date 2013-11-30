package fiuba.taller.actividad;

import java.io.StringReader;
import java.rmi.RemoteException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis2.AxisFault;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

//import com.ws.services.*;

@SuppressWarnings("unused")
public class Actividad {

	protected long id;
	protected String nombre;
	protected String tipo;
	protected String descripcion;
	protected long idAmbitoSuperior;
	protected long idActividadSuperior;
	// - coordinadores : ArrayList<Miembro>
	// - cartelera : Cartelera
	// - foro : Foro
	// - actividadesInternas : ArrayList<Actividad>
	// - chat : Chat (necesario ??)
	// usar desp tipo de dato correcto en fechas
	protected String fechaInicio;
	protected String fechaFin;

	// - muroSuperior : Informable
	public Actividad() {
		id = 10; // finalmente va -1
		idAmbitoSuperior = 22; // finalmente va -1
		idActividadSuperior = 88; // finalmente va -1
		nombre = "pepe"; // finalmente va ""
		fechaFin = "mamam"; // finalmente va ""
		fechaInicio = "hola";
		descripcion = "esto es una descripcion"; // finalmente va ""
		tipo = "bad gay"; // finalmente va ""
	}

	public static boolean esTipoValido(String xml) {
		return true;
	}

	public String pruebaIntegracion() {
		String xml = "<?xml version=\"1.0\"?><WS><Usuario><username>usuario_prueba1</username><password>1234</password><activado>true</activado><habilitado>true</habilitado></Usuario></WS>";
		/*GuardarDatosResponse response = new GuardarDatosResponse();
		 GuardarDatos envio = new GuardarDatos();
		 envio.setXml(xml);
		 IntegracionStub servicio;
		 try {
		 servicio = new IntegracionStub();
		 response = servicio.guardarDatos(envio);
		 } catch (RemoteException e) {
		 System.out.print("Ocurrio un Error en el metodo pruebaIntegracion\n");
		 e.printStackTrace();
		 return "ERROR";
		 }
		*/
		return "Integracion Contesto";// +response.get_return()+"\n";

	}

	// Recibe el xml obtenido de integracion, cullo contenido es los datos de la
	// clase actividad
	// es privado pero por motivo de testing lo pongo publico
	public String descerializar(String xml) {
		// procesar xml y asignar sus datos a los atributos internos de
		// Actividad
		String info = "";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			Document doc = builder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName("Actividad");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					this.nombre = getValue("nombre", element);
					this.fechaInicio = getValue("fechaini", element);
					this.fechaFin = getValue("fechafin", element);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return info;
	}

	// serializa a la actividad
	// devuelve xml
	// este metodo intente ser util tanto para cuando:
	// -> se envian las propiedades de la actividad a presentacion
	// -> se guardan los datos a integracion y se envian los datos de consulta a
	// integracion
	public String serializar() {
		String identif = "";
		String idAmbSupStr = "";
		String idActSupStr = "ninguna";
		if (id >= 0) {
			identif = String.valueOf(id);
		}
		if (idAmbitoSuperior >= 0) {
			idAmbSupStr = String.valueOf(idAmbitoSuperior);
		}
		if (idActividadSuperior >= 0) {
			idActSupStr = String.valueOf(idActividadSuperior);
		}
		String xml = "<?xml version=\"1.0\"?><WS><Actividad>"
				+"<id>" + identif + "</id>"
				+"<idAmbitoSuperior>" + idAmbSupStr + "</idAmbitoSuperior>"
				+ "<idActividadSuperior>" + idActSupStr + "</idActividadSuperior>"
				+ "<nombre>" + nombre + "</nombre>"
				+ "<Tipo>" + tipo + "</Tipo>"
				+"<Descripcion>" + descripcion+"</Descripcion>"
				+"<fechaini>" + fechaInicio + "</fechaini>"
				+"<fechafin>" + fechaFin + "</fechafin>" 
				+ "</Actividad></WS>";
		return xml;
	}

	public void guardarEstado() {
		/* GuardarDatosResponse response = new GuardarDatosResponse();
		 GuardarDatos envio = new GuardarDatos();
		 envio.setXml(serializar());
		 IntegracionStub servicio;
		 try {
		 servicio = new IntegracionStub();
		 response = servicio.guardarDatos(envio);
		 } catch (RemoteException e) {
		 System.out.print("Ocurrio un Error en el metodo setNombre\n");
		 e.printStackTrace();
		 }
		 System.out.print(response.get_return());
		 */
	}

	/*
	 * Con el estado interno actual que tiene la instancia , la serializa y
	 * manda su xml a integracion, integracion devuelve un xml con los datos
	 * completos de los campos vacios enviados
	 */
	public String getXml(long id) {
		// "No implementado todavia :)";
		// devuelve siempre lo mismo
		String xmlDevuelto = "<?xml version=\"1.0\"?><WS><Actividad>"
				+ "<id>" + 45 + "</id>" 
				+ "<idSuperior>" + 88 + "</idSuperior>"
				+ "<nombre>" + "pepe" + "</nombre>"
				+ "<Tipo>"+ "ActividadIndividual" + "</Tipo>" 
				+ "<Descripcion>"+ "esto es una descripcion" + "</Descripcion>" 
				+ "<fechaini>"+ fechaInicio + "</fechaini>" 
				+ "<fechafin>"+ fechaFin + "</fechafin>" 
				+ "</Actividad></WS>";
		return xmlDevuelto;
	}

	/*
	 * metodo para realizar una consulta a integracion que dependa no solamente
	 * del id de la actividad sino del estado interno de la actividad
	 */
	public String getXml() {
		return getXml(-1);
	}

	public static Actividad getActividad(long idActividad) {
		Actividad actividad = new Actividad();
		String xml = actividad.getXml(idActividad);
		actividad.descerializar(xml);
		return actividad;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setIdAmbitoSuperior(long idAmbitoSuperior) {
		this.idAmbitoSuperior = idAmbitoSuperior;
	}

	public void setIdActividadSuperior(long idActividadSuperior) {
		this.idActividadSuperior = idActividadSuperior;
	}

	// esta devuelve un xml
	public String getPropiedades(int IdActividad) {
		// cargo datos de integracion
		// devuelvo la clase serializada(necesario, no combiene directamente
		// enviar xml que devuelve integracion??)
		return serializar();
	}

	// FALTA HACER
	// devuelve xml
	/*
	 * public String getActividades(int id,String tipoAmbito){ if
	 * (tipoAmbito.equalsIgnoreCase("Ambito")){ return
	 * "usted pidio las actividades que contiene un Ambito\n"; }else
	 * if(tipoAmbito.equalsIgnoreCase("Actividad")){ return
	 * "usted pidio las actividades que contiene una actividad\n"; } return
	 * "tipo de ambito no valido : "+tipoAmbito; }
	 */
	
	/* METODOS PRIVADOS AUXILIARES */
	// metodo interno de ayuda para el parseo
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0)
				.getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
}