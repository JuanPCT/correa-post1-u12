package co.edu.ufps.reembolsos.procesamiento;
public class SolicitudNoEncontrada extends RuntimeException { public SolicitudNoEncontrada(Long id){super("No existe la solicitud "+id);} }
