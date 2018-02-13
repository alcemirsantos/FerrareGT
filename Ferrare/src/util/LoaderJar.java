package util;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Classe que lê a classe do arquivo .jar
 * @author Ismayle de Sousa Santos
 */
public class LoaderJar {
	
	//path :: pasta lib ou qualquer outra indicada pelo usuário
	//extractorName :: nome do extractor. OBS: Ele deve estar no pacote extractors
	//Falta padronizar para ter que ficarn o pacote extractor ou através da indicação do usuário
	
	public Class<?> loadJar2(String path, String className){
		java.net.URL location;
		try {
			location = new URL(
					"jar:file:"+path+"!/");

			URL[] allLocations = new URL[1];
			allLocations[0] = location;
			ClassLoader loader = URLClassLoader.newInstance(allLocations, this
					.getClass().getClassLoader());

			Class<?> classe = loader.loadClass(className);
			Method[] metodos = classe.getMethods();
			System.out.println("--");
			for (int i = 0; i < metodos.length; i++)   
			       System.out.println( metodos[i].toString() );			
			return classe;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}

}
