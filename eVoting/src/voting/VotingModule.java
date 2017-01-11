package voting;

import java.math.BigInteger;
import java.util.ArrayList;
import EccSign.Isign;
import EccSign.Sign;
import Paillier.CreatingEvoting;
import Paillier.ICreateElection;
import Paillier.IVote;
import Paillier.Voting;
import hash.Hash;
import hash.IHash;
import multipartyComputation.DBHelper;

//Election bitince isCompleted ý true yap
public class VotingModule implements IVoting{
	
	private boolean isCodeFake;
	private int voterID;
	private String hashOfRegCode;
	private String hashOfFakeRegCode;
	private String encryptedVote;
	private String signedEncryptedVote;
	private int numOfCastedVotes = 0;
	//--private Controller controller;
	private IVote vote;
	private IHash hash;
	private static DBHelper dbHelper;
	
	private int digestSizeBits = 512;
	private String dbHashOfRegCode;
	private String dbHashOfFakeRegCode;
	private int validityFlag;
	private int coercionFlag;

	
	public VotingModule() throws Exception{
		this.isCodeFake = false;
		this.hashOfRegCode = null;
		this.hashOfFakeRegCode = null;
		this.encryptedVote = null;
		this.voterID = -1;
		//--this.controller = Controller.getInstance();
		//--dbHelper = controller.db;
		dbHelper = new DBHelper();
		hash = new Hash(); //controllerdan almadığımız için eklendi.
	}


	private String getCastedVote() throws Exception {
		//GUI aracýlýðýyla Ballottan seçilmiþ olan Candidate Id alýnacak
		//encrypt the vote by calling encrypt() method from paillierInterface
		//set encryptedVote
		//return encrypted vote
		
		//--int id = controller.getCandidateID();//get Candidate id
		int id = 1;
		//vote = controller.vote;
		
		ICreateElection paillier = new CreatingEvoting(new BigInteger("10"), 10);
		vote = new Voting(paillier);
		BigInteger encryptedVote = vote.vote(id);
		
		return encryptedVote.toString();
	} 

	// GUI'den giriþ bilgileri alýnacak

	/*
	public boolean isLoginSuccessful() {
		//GUI'den girilmiþ olan email ve regCode alýnýr.
		//regCode'un hash deðeri hesaplanýr. H(RegCode) diyelim. hashOfRegCode = H(RegCode) yapýlýr.
		//Voter ve RegisteredVoter db tablolarý joinlenip, girilen emaile ait regCode'un ve fakeRegCode'un hash deðerleri çekilir. Ayrýca VoterId çekilip set edilir.
		//H(RegCode) ile bu çekilen deðerler karþýlaþtýrýlýr.
		//HErhangi birine eþitse result = true yapýlýr.
		//Eðer fake code girilmiþse isCodeFake = true yap.
		//Result deðeri döndürülür.
		boolean result;
		String email = controller.getEmail();
		String regCode = controller.getRegCode();
		hash = controller.hash;
		hashOfRegCode = hash.sha3(regCode, digestSizeBits);
		
		//DBden regCode ve hashRegCode çekilir set edilir.
		
		if(hashOfRegCode.equals(this.dbHashOfRegCode))
		{
			result = true;
		}
		else if(hashOfRegCode.equals(this.dbHashOfFakeRegCode))
		{
			this.isCodeFake = true;
			this.hashOfFakeRegCode = hashOfRegCode; // control if it successfully assign
			hashOfRegCode = null;
			result = true;
			
		}
		else
		{
			result = false;
		}
		
		return result;
	} 
	*/
	
	public boolean isLoginSuccessful() {
		//GUI'den girilmiþ olan email ve regCode alýnýr.
		//regCode'un hash deðeri hesaplanýr. H(RegCode) diyelim. hashOfRegCode = H(RegCode) yapýlýr.
		//Voter ve RegisteredVoter db tablolarý joinlenip, girilen emaile ait regCode'un ve fakeRegCode'un hash deðerleri çekilir. Ayrýca VoterId çekilip set edilir.
		//H(RegCode) ile bu çekilen deðerler karþýlaþtýrýlýr.
		//HErhangi birine eþitse result = true yapýlýr.
		//Eðer fake code girilmiþse isCodeFake = true yap.
		//Result deðeri döndürülür.
		boolean result;
		//--String email = controller.getEmail();
		//--String regCode = controller.getRegCode();
		
		String email = "leylatekin@iyte.edu.tr";
		
		this.voterID = dbHelper.getVoterID(email);
		
		//DBden regCode ve hashRegCode çekilir set edilir.
		ArrayList<String> regCodes = dbHelper.getRegCodeAndFakeRegCodeOfVoter(email);
		dbHashOfRegCode = regCodes.get(0);
		dbHashOfFakeRegCode = regCodes.get(1);

		
		
		//--hashOfRegCode = hash.sha3(regCode, digestSizeBits);
		
		hashOfRegCode = "ab2016";
		
		if(hashOfRegCode.equals(dbHashOfRegCode))
		{
			result = true;
			System.out.println("hashOfRegCode.equals(dbHashOfRegCode)");
		}
		else if(hashOfRegCode.equals(dbHashOfFakeRegCode))
		{
			this.isCodeFake = true;
			this.hashOfFakeRegCode = hashOfRegCode; // control if it successfully assign
			hashOfRegCode = null;
			result = true;
			System.out.println("hashOfRegCode.equals(dbHashOfFakeRegCode)");
			
		}
		else
		{
			result = false;
		}
		
		return result;
	} 

	private boolean checkCastedVoteValidity()
	{
		//First, check validity flag
		//If it is zero, check isCodeFake.
		//If isCodeFake is true, then check coercion flag. 
		       //If the coercion flag is 1, then vote cannot be accepted. 
		       //If the coercion flag is 0, set coercionFlag = 1 (Þu iþlem saveVote'da yapýlacak: and save the vote. Check if it is successfully saved, if not change the flag.)
		//If isCodeFake is false, then set the validityFlag = 1 (Þu iþlem saveVote'da yapýlacak: and save the vote.)
		//(Þu iþlem saveVote'da yapýlacak: Check if it is successfully saved, if not change the validity flag.)
		
		this.validityFlag = -1;
		this.coercionFlag = -1;
		ArrayList<Integer> flags = dbHelper.getRegisteredVoterFlags(voterID); 
		int dbValidityFlag = flags.get(0);
		int dbCoercionFlag = flags.get(1);
		
		if(dbValidityFlag == 0){
			System.out.println("dbValidityFlag == 0");
			if(isCodeFake == true){
				System.out.println("isCodeFake == true");
				if(dbCoercionFlag == 0){
					System.out.println("dbCoercionFlag == 0");
					coercionFlag = 1;
					return true;
				}
			}
			else{	//isCodeFake==false
				System.out.println("hahahah !! :)");
				validityFlag = 1;
				return true;
			}
		}
		
		return false;
	}
	
	private void signEncryptedVote() throws Exception
	{
		//VoterID'ye bakarak VoterCredentials db tablosundan private key çekilir.
		//encryptedVote bu key ile imzalanýr. (Elliptic curve'ün interfacei kullanýlýr)
		//signedEncryptedVote set edilip döndürülür.
		
		encryptedVote = getCastedVote();
		BigInteger voterPr = dbHelper.getVoterPrivateKey(voterID);
		System.out.println("voterPr: " + voterPr);
		Isign signer = new Sign(voterPr);
		System.out.println("encryptedVote: " + encryptedVote);
		signedEncryptedVote = signer.signingMessage(encryptedVote);
	}
	
	public boolean saveVote() throws Exception
	{
		//check vote checkCastedVoteValidity
		//If it returns true, then call signEncryptedVote
		//Calculate hash value of signedEncryptedVote. H(signedEncryptedVote) diyelim.
		//DB'deki Vote tablosuna þu bilgileri ekle (save) : voterID, signedEncryptedVote, H(EV || Signature || HashOfRegCode or HashOfFakeRegCode).
		//If successfully saved: modify the flags, whose values are not equal to -1, of registered voters as parameters validityFlag and coercionFlag. 
		//Kaydedildiyse, numOfCastedVotes deðerini 1 arttýr ve true döndür.
		//Kaydedilmediyse false döndür ve checkCastedVoteValidity fonksiyonunda set edilen flagleri deðiþtir:


		if(isLoginSuccessful()){
			if(checkCastedVoteValidity()){
				signEncryptedVote();
				
				String hashOfCode = hashOfRegCode;
				if(hashOfCode == null){
					hashOfCode = hashOfFakeRegCode;
				}
				
				System.out.println("hashOfCode: " + hashOfCode);
				//String seHregCode = encryptedVote.concat(signedEncryptedVote).concat(hashOfCode);
				String seHregCode = hashOfCode.concat(signedEncryptedVote).concat(encryptedVote);
				System.out.println("seHregCode :" + seHregCode);
				String hash_seHregCode = hash.sha3(seHregCode, digestSizeBits);
				
	
				if(dbHelper.registerVote(hashOfCode, voterID, signedEncryptedVote, hash_seHregCode)){
					if(validityFlag == 1){
						if(dbHelper.updateValidityFlag(voterID, 1)){
							numOfCastedVotes++;
							return true;
						}
					}
					else if(coercionFlag == 1){
						if(dbHelper.updateCoercionFlag(voterID, 1)){
							numOfCastedVotes++;
							return true;
						}
					}
				}
				
				//return false;
			}	
		}
		
		return false;
	}
	
	
	public int getNumberOfCastedVotes()
	{
		return numOfCastedVotes;
	}

	public String getHashOfRegCode()
	{
		return this.hashOfRegCode;
	}
	
	public String getHashOfFakeRegCode()
	{
		return this.hashOfFakeRegCode;
	}
}