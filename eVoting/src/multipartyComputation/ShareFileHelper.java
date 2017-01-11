package multipartyComputation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

/*
 * Author : Leyla
 * Date   : January 05, 2017
 */
public class ShareFileHelper {
	
	public ShareFileHelper(){
	}
	
	public static BigInteger readBigIntegerFromFile(File fromFile){
		BigInteger value = null;
		try
        {
			FileInputStream fileInputStream = new FileInputStream(fromFile.getAbsolutePath());
	        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	        value = (BigInteger) objectInputStream.readObject();
	        
	        objectInputStream.close();
	        fileInputStream.close();
	        
        } catch(IOException ioe) {
        	ioe.printStackTrace();
        } catch(ClassNotFoundException c) {
	        System.out.println("Class not found");
	        c.printStackTrace();
        }
		return value;
	}
	
	public static void writeBigIntegerToFile(BigInteger value, File toFile){
		try
        {
			FileOutputStream fileOutputStream = new FileOutputStream(toFile.getAbsolutePath());
			ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
			outputStream.writeObject(value);
			outputStream.close();
			fileOutputStream.close();
        } catch(IOException ioe) {
        	ioe.printStackTrace();
        }
	}
}
