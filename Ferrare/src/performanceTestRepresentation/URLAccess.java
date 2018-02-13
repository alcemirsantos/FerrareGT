package performanceTestRepresentation;

import java.util.LinkedList;
import java.util.List;

import functionalTestRepresentation.Field;

/**
 * Classe que representa os Acessos as p�ginas 
 * @author Ismayle de Sousa Santos
 */
public class URLAccess {
	private String server, path, method, response;
	private int limitTime, port;
	private URLAccess nextAccess;
	private List<Field> fields;
	private int capture = 0; //0 ->normal; 1-> captura de url e campos hidden
    private LinkedList<String> listHidden = new LinkedList<String>(); 

   
 	/**
 	 * Construtor Completo da Classe
 	 * @param url URL base da p�gina
 	 * @param path Path da p�gina
 	 * @param port Porta
 	 * @param method Metodo de envio de dados 
 	 * @param limitTime Tempo m�ximo de resposta
 	 * @param response Resposta esperada 
 	 * @param capture Indica se os campos hidden dessa p�gina devem ser capturados
 	 */
	public URLAccess(String url, String path, int port, String method,
			int limitTime, String response, int capture) {
		super();
		server = url;
		this.path = path;
		this.method = method;
		this.port = port;
		this.limitTime = limitTime;
		this.response = response;
		this.fields = new LinkedList<Field>();
		this.nextAccess = null;
		this.capture = capture;
	}
	
	/**
	 * Construtor da Classe
	 */
	public URLAccess() {
		this("", "", 80, "GET", 0, "",0);
	}
	
	 /**
	  * Retorna a lista de Campos hidden
	 * @return Lista de Campos hidden
	 */
	public LinkedList<String> getListHidden(){
	        return this.listHidden;	
	 }
	     
	 /**
	  * Altera a lista de Campos hidden
	 * @param listHidden Nova lista de Campos hidden
	 */
	public void setListHidden(LinkedList<String> listHidden){
	         this.listHidden = listHidden;	
	 }
	     
	 /**
	  * Retorna  o valor do atributo capture
	 * @return Valor do atributo capture
	 */
	public int getCaptura(){
	     	return this.capture;
	 }
	     
	 /**
	  * Altera o valor do atributo capture
	 * @param captura Novo valor do atributo capture
	 */
	public void setCaptura(int captura){
	     	this.capture = captura;
	 }
	
	/**
	 * Retorna o pr�ximo Acesso
	 * @return Acesso
	 */
	public URLAccess getNextAccess() {
		return nextAccess;
	}

	/**
	 * Altera o pr�ximo Acesso
	 * @param nextAccess Pr�ximo Acesso
	 */
	public void setNextAccess(URLAccess nextAccess) {
		this.nextAccess = nextAccess;
	}

	/**
	 * Retorna o tempo de resposta
	 * @return Tempo de resposta
	 */
	public int getLimitTime() {
		return limitTime;
	}

	/**
	 * Altera o tempo de resposta 
	 * @param limitTime Novo tempo de resposta
	 */
	public void setPageTimeout(int limitTime) {
		this.limitTime = limitTime;
	}

	/**
	 * Retorna o m�todo usado no Acesso
	 * @return M�todo
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Altera o M�todo do Acesso
	 * @param method Novo m�todo
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * Retorna o path do Acesso 
	 * @return Path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Altera o path do Acesso
	 * @param path Novo path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Retorna a porta do Acesso
	 * @return porta
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Altera a porta do Acesso
	 * @param port Nova porta
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Retorna a resposta esperada do Acesso
	 * @return Resposta esperada
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Alterar a resposta esperada do Acesso
	 * @param response nova resposta
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * Retorna a URL base do Acesso
	 * @return URL base
	 */
	public String getServer() {
		return server;
	}

	/**
	 * Altera a URL base do Acesso
	 * @param url Nova URL
	 */
	public void setServer(String url) {
		server = url;
	}

	/**
	 * Retorna a lista de Campos do Acesso
	 * @return Lista de Campos
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * Altera a lista de Campos
	 * @param fields Nova lista de Campos
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	/**
	 * Adiciona um novo Campo a lista de Campos do Acesso
	 * @param field Field a ser adicionado
	 * @return True se o Campo for adicionado e False caso contr�rio
	 */
	public boolean addField(Field field) {
		return (fields.add(field));
	}

	/**
	 * Remove um Campo da lista de Campos do Acesso
	 * @param field Campo a ser removido 
	 * @return True se o Campo for remomvido e False caso contr�rio
	 */
	public boolean removeField(Field field) {
		return (fields.remove(field));
	}
	
	/**
	 * Remove um Campo da lista de Campos do Acesso com base na posi��o desse Campo
	 * @param pos Posi��o do Campo a ser removido
	 */
	public void removeField(int pos) {
		fields.remove(pos);
	}
	
	/**
	 * Remove todos os Campos da lista de Campos
	 */
	public void removeAllField() {
		fields.clear();
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("URLAccess:");

		s.append("\n Server: ");
		s.append(server);

		s.append("\n Path: ");
		s.append(path);

		s.append("\n Method: ");
		s.append(method);

		s.append("\n Port: ");
		s.append(port);

		s.append("\n Expected Response Time: ");
		s.append(limitTime);

		s.append("\n Response Contains: ");
		s.append(response);

		s.append("\n Fields:");
		for (Field f : fields) {
			s.append("\n " + f.toString());
		}
		s.append("\n");

		return s.toString();
	}

}
