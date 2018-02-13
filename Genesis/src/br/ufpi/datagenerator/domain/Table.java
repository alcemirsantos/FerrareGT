package br.ufpi.datagenerator.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Tem as informaçoes de uma tabela, o seu nome, o listas de atributos e
 * relacionamentos
 * 
 * @author iure
 * 
 */
public class Table {

	/**
	 * Nome da tabela
	 */
	private String name;

	/**
	 * as colunas não chave estrangeira
	 */
	private Collection<PrimitiveAttributes> primitiveAtributes;

	/**
	 * colunas que são chave estrangeira
	 */
	private Collection<Relationship> relationship;

	public Table() {
		super();
	}

	public Table(String name,
			Collection<PrimitiveAttributes> primitiveAtributes,
			Collection<Relationship> relationship) {
		super();
		this.name = name;
		this.primitiveAtributes = primitiveAtributes;
		this.relationship = relationship;
	}

	public Collection<PrimitiveAttributes> getPrimitiveAtributes() {

		return primitiveAtributes;
	}

	public void setPrimitiveAtributes(
			Collection<PrimitiveAttributes> primitiveAtributes) {

		this.primitiveAtributes = primitiveAtributes;
	}

	public Collection<Relationship> getRelationship() {

		return relationship;
	}

	public void setRelationship(Collection<Relationship> relationship) {
		this.relationship = relationship;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// TODO verificar solução para dependencias ciclicas
	@Override
	public boolean equals(Object obj) {

		// verificaçãp de associaçoes reflexivas
		if (relationship != null) {
			ArrayList<String> names = new ArrayList<String>();
			for (Relationship relations : relationship) {
				names.add(relations.getForeignKey().getName());
			}
			if (names.contains(name))
				return true;
		}

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;

		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (primitiveAtributes == null) {
			if (other.primitiveAtributes != null)
				return false;
		} else if (!primitiveAtributes.equals(other.primitiveAtributes))
			return false;

		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		return true;
	}
}
