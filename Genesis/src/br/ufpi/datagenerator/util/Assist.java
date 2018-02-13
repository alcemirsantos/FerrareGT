/**
 * 
 */
package br.ufpi.datagenerator.util;

import br.ufpi.datagenerator.domain.Relationship;
import br.ufpi.datagenerator.domain.Table;

/**
 * Verifica a existencia de dependencias ciclicas
 * 
 * @author iure
 * 
 */
public class Assist {

	/**
	 * verifica a existencia de dependencias ciclicas em uma tabela
	 * 
	 * @param table
	 * @return true se for ciclica
	 */
	public boolean isCiclic(Table table) {
		if (hasCiclic(table) > 0)
			return true;
		else
			return false;
	}

	public int hasCiclic(Table table) {

		return hasCiclic(table, table.getName(), 0);

	}

	private int hasCiclic(Table table, String name, int value) {
		value++;

		int othervalue = 0;
		for (Relationship relationship : table.getRelationship()) {

			if (relationship.getForeignKey().getName().equals(name)) {
				return value;
			}
			int val = hasCiclic(relationship.getForeignKey(), name, value);

			if (val != 0) {
				othervalue = val;
			}
		}

		return othervalue;

	}

}
