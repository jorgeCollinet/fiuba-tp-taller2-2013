package fiuba.taller.actividad;

import java.util.List;

public interface IEvaluable {

	/*
	 * idEvaluado: Puede representar tanto a un participante como a un grupo
	 * nota: Numerica o conceptual
	 * 
	 * Asigna nota al evaluado correspondiente
	 * */
	public void evaluar(long idEvaluado, String nota);
	
	/*
	 * 
	 * */
	public Nota getNota(long idParticipante);
	
	/*
	 * 
	 * */
	public List<Nota> getNotas();
}