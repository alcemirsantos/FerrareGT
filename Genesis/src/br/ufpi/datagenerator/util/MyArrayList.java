package br.ufpi.datagenerator.util;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class MyArrayList extends ArrayList {

	private static final long serialVersionUID = 1L;

	public MyArrayList(Object o[]) {
		super(o.length);
		for (int i = 0; i < o.length; i++)
			super.add(o[i]);
	}
}