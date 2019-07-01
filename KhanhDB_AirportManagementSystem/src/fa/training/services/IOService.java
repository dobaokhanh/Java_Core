package fa.training.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;


public class IOService {
	
	public void save(Object obj, String fileName) throws Exception {
		ObjectOutputStream fileOut = null;
		try {
			fileOut = new ObjectOutputStream(new FileOutputStream(fileName));
			fileOut.writeObject(obj);
		} catch (IOException e) {
			throw new IOException();
		} finally {
			if (fileOut != null)
				fileOut.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object getAll(String fileName) throws Exception {
		Object obj;
		ObjectInputStream fileIn = null;
		try {
			fileIn = new ObjectInputStream(new FileInputStream(fileName));
			obj = (Set<Object>) fileIn.readObject();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			if (fileIn != null)
				fileIn.close();
		}
		return obj;
	}
}
