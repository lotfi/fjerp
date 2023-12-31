package org.fjerp.modele;

import java.io.Serializable;

public class Data implements Serializable
{
	private static final long serialVersionUID = 8161109705040081891L;

	private String[] names = {};
	private Object[] values = {};
	private int length = 0;

	public Data(int length)
	{
		this.length = length;

		if (
			length > 0
		)
		{
			names = new String[length];
			values = new Object[length];
		}
	}

	public Data(String[] names)
	{
		this.length = names.length;

		this.names = names;
		values = new Object[length];
	}

	public int getIndex(String name) {
		for (
				int i = 0; i < length; i++
		)
		{
			if (
				names[i].equals(name)
			)
				return i;
		}

		return -1;
	}

	public Object getValue(String name) {
		for (
				int i = 0; i < length; i++
		)
		{
			if (
				names[i].equals(name)
			)
				return values[i];
		}

		return null;
	}

	public Object getValue(int index) {
		if (
			index >= 0 && index <= length - 1
		)
		{
			return values[index];
		}

		return null;
	}

	public void setValue(String name, Object value) {
		int index = getIndex(name);

		if (
			index != -1
		)
			values[index] = value;
	}

	public void setValue(int i, String name, Object value) {
		if (
			i >= 0 && i <= length - 1
		)
		{
			names[i] = name;
			values[i] = value;
		}
	}

	public int getLength() {
		return length;
	}

	public String[] getNames() {
		return this.names;
	}

}
