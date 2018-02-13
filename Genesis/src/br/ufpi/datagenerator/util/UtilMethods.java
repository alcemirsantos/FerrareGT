package br.ufpi.datagenerator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import br.ufpi.datagenerator.creator.RequiredTables;

public  class UtilMethods {
	
	public static ArrayList<String> getRequiredFields( RequiredTables requiredTables, HashMap<String, ArrayList<HashMap<String, String>>> model){
		Set<String> requirerTAbleNames = requiredTables.getTables().keySet();

		ArrayList<String> requiredFields = new ArrayList<String>();  // itens do XXXXHeader.txt

		// cria o numero de elementos que ter� o csv
		for (String requiredTableName : requirerTAbleNames) {
			int lineNumbers = model.get(requiredTableName).size();

			ArrayList<String> fields = (ArrayList<String>) requiredTables
					.getTables().get(requiredTableName);

			for (String field : fields) {
				for (int i = 0; i < lineNumbers; i++) {

					requiredFields.add(requiredTableName + "_" + field + i);
				}

			}
		}
		return requiredFields;
	}//fim do método
	
	
}//fim da classe
