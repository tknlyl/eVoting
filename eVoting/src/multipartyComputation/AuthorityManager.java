package multipartyComputation;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;

/*
 * Author : Leyla
 * Date   : January 05, 2017
 */
public class AuthorityManager {

	private File secretFile;
	private int electionID;
	private ArrayList<Authority> authorityList;
	private int k;
	private int n;
	private Shamir shamir;
	private boolean isShared;
	private DBHelper dbHelper;
		
	public AuthorityManager(String secretPath, int electionID) throws Exception{
		secretFile = new File(secretPath);
		this.electionID = electionID;
		authorityList = new ArrayList<>();
		dbHelper = new DBHelper();
		initialize();
	}
	
	private void initialize() throws Exception{
		setAuthorityList(dbHelper.getAuthorities(electionID));
		n = authorityList.size();
		k = (int) Math.ceil((double) n/2);
		System.out.println("number of authorities: " + n);
		System.out.println("threshold: " + k);
		shamir = new Shamir(k, n);
		
		for(Authority authority : authorityList){
			authority.setAuthorityManager(this);
		}
		
		isShared = false;
	}
	
	public void shareSecret(){
		BigInteger secret = ShareFileHelper.readBigIntegerFromFile(secretFile);
		System.out.println("Secret read from private key file:" + secret);
		
		if(secret != null){
			Share[] shares = shamir.distribute(secret);
			for(int i=0;i<n;i++){
				Authority authority = authorityList.get(i);
				authority.setShare(shares[i]);
				System.out.println("Authority Code:" + authority.getAuthorityCode() + " and Share:" + shares[i]);
			}	
			secretFile.delete();
			isShared = true;
		}
	}


	public void reconstructSecret() throws Exception{
		if(isShared == true){
			for(Authority authority : authorityList){
				authority.calculateSecret();
			}
		}
	}
	
	public ArrayList<Authority> getAuthorityList() {
		return authorityList;
	}
	
	public void setAuthorityList(ArrayList<Authority> authorities){
		for(Authority authority : authorities){
			authorityList.add(authority);
		}
	}

	public Shamir getShamir() {
		return shamir;
	}
}
