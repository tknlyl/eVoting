package controller;

import java.math.BigInteger;

import Ecc.EllipticCurve;
import EccSign.ECDSA;
import EccSign.Point;
import Paillier.CreatingEvoting;
import Paillier.ICreateElection;
import Paillier.IVote;
import Paillier.Voting;
import hash.Hash;
import hash.IHash;
import helper.CertificateReader;
import multipartyComputation.CertificateManager;
import multipartyComputation.DBHelper;
import voting.IVoting;
import voting.VotingModule;

//@generic, emre
public class Controller{
	
	private int selectedCandidateID = -1;
	private String email = null;
	private String regCode = null;

	/*Generic Def*/
	private static Controller instance = null;
	
	/*Arzum Def*/
	
	/*Burcu Def*/
	//public DBHelper db = null;

	ICreateElection paillier = null;
	public IVote vote = null;
	public IHash hash = null;
	public IVoting vm = null;
	public String queriedVote = null;
	
	/*Emre Def*/
	
	/*Guven Def*/
	
	/*Leyla Def*/
	private EllipticCurve curve = null;
	private ECDSA ecDSA = null;
	private BigInteger privateKey = null;
	private Point publicKey = null;
	private CertificateManager certificateManager = null;
	private CertificateReader certificateReader = null;
	private DBHelper dbHelper = null;
	
	/*Pelin Def*/
	
	//@generic, emre
	private Controller() throws Exception{
		/*Generic Def*/
		
		/*Arzum Def*/
		
		/*Burcu Def*/
		//db = new DBHelper();
		paillier = new CreatingEvoting(new BigInteger("10"), 10);
		vote = new Voting(paillier);
		hash = new Hash();
		vm = new VotingModule();
		/*Emre Def*/
		
		/*Guven Def*/
		
		/*Leyla Def*/
		
		
		curve = EllipticCurve.NIST_P_192;
		ecDSA = new ECDSA();
		privateKey = new BigInteger("113030449655396724366806196427178321280749587523943565521585687101690651316050");
		ecDSA.setPu(privateKey);
		publicKey = ecDSA.getQA();
		certificateManager = new CertificateManager(privateKey, publicKey);
		certificateReader = new CertificateReader();
		dbHelper = new DBHelper();
		
		/*Pelin Def*/
		
	}
	
	//@generic, emre
	public static Controller getInstance() throws Exception{
		if(instance == null){
			instance = new Controller();
		}
		return instance;
	}
	
	public int getCandidateID()
	{
		return this.selectedCandidateID;
	};
	
	public void setCandidateID(int id)
	{
		this.selectedCandidateID = id;
	};
	
	public String getEmail()
	{
		return this.email;
	};
	
	public void setEmail(String mail)
	{
		this.email = mail;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	};
	
	public void resetParameters()
	{
		this.selectedCandidateID = -1;
		this.email = null;
		this.regCode = null;
	}

	public EllipticCurve getCurve() {
		return curve;
	}

	public void setCurve(EllipticCurve curve) {
		this.curve = curve;
	}

	public ECDSA getEcDSA() {
		return ecDSA;
	}

	public void setEcDSA(ECDSA ecDSA) {
		this.ecDSA = ecDSA;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(BigInteger privateKey) {
		this.privateKey = privateKey;
	}

	public CertificateManager getCertificateManager() {
		return certificateManager;
	}

	public void setCertificateManager(CertificateManager certificateManager) {
		this.certificateManager = certificateManager;
	}
	
	public CertificateReader getCertificateReader() {
		return certificateReader;
	}

	public void setCertificateReader(CertificateReader certificateReader) {
		this.certificateReader = certificateReader;
	}

	public Point getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(Point publicKey) {
		this.publicKey = publicKey;
	}
	
	public String getQueriedVote() {
		return queriedVote;
	}

	public void setQueriedVote(String queriedVote) {
		this.queriedVote = queriedVote;
	}

	public DBHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
	
	
}