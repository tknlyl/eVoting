package multipartyComputation;

import java.io.File;
import java.math.BigInteger;

/*
 * Author : Leyla
 * Date   : January 05, 2017
 */
public class TestAuthorityManager {
	
	public static void main(String[] args) throws Exception{
		
		String secretPath = "D:\\evoting\\PrivateKey.txt";
		File secretFile = new File(secretPath);
		
		ShareFileHelper.writeBigIntegerToFile(new BigInteger("1234"), secretFile);
		
		AuthorityManager authorityManager = new AuthorityManager(secretPath, 1);
		authorityManager.shareSecret();
		authorityManager.reconstructSecret();
	}
}



