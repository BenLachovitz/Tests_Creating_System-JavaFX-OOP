package Quiz_Model;

import java.io.Serializable;
import java.util.Arrays;

public class Set <T> implements Serializable, Cloneable
{
	private T[] data;
	private int numOfData;

	@SuppressWarnings("unchecked")
	public Set()
	{
		data=(T[])new Object[10];
		numOfData=0;
	}

	public boolean add(T variable)
	{
		if (data.length==numOfData)
			data=Arrays.copyOf(data, numOfData*2);
		if (contains(variable))
			return false;
		data[numOfData++]=variable;
		return true;
	}

	public String SetToString()
	{
		String temp="";
		for(int i=0;i<numOfData;i++)
		{
			temp+=data[i];
		}
		return temp;
	}
	
	public Set<T> clone() throws CloneNotSupportedException
	{
		return (Set<T>)super.clone();
	}
	
	public void clear()
	{
		for (int i=0;i<numOfData;i++)
		{
			data[i]=null;
		}
		numOfData=0;
	}

	public boolean contains(T variable)
	{
		for (int i=0;i<numOfData;i++)
		{
			if (data[i].equals(variable))
				return true;
		}
		return false;
	}

	public void set(int index, T variable) throws ArrayIndexOutOfBoundsException
	{
			data[index]=variable;
	}

	public void remove(int index)
	{
		if (data[index]==null)
			throw new NullPointerException();
		data[index]=data[numOfData-1];
		data[numOfData--]=null;
	}

	public int size()
	{
		return numOfData;
	}

	public T get(int index) throws ArrayIndexOutOfBoundsException
	{
			return data[index];
	}

	public boolean equals(Object other)
	{
		if (!(other instanceof Set<?>))
			return false;
		@SuppressWarnings("unchecked")
		Set<T> temp = (Set<T>)other;
		if (numOfData!=temp.numOfData)
			return false;
		for (int i=0;i<numOfData;i++)
		{
			if (!data[i].equals(temp.data[i]))
				return false;
		}
		return true;
	}

	public String toString()
	{
		return Arrays.toString(data);
	}
}
