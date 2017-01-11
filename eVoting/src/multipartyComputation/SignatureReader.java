package multipartyComputation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Author : Leyla
 * Date   : January 08, 2017
 */
public class SignatureReader {

	private String signatureFolderPath;
	
	public SignatureReader(String signatureFolderPath){
		this.signatureFolderPath = signatureFolderPath;
	}
	
	public String readSignature(String signatureName){
	    Scanner scan;
	    String signature ="";
		try {
			scan = new Scanner(new File(signatureFolderPath+signatureName));
			signature = scan.useDelimiter("\\Z").next().trim();
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return signature;
	}
}
