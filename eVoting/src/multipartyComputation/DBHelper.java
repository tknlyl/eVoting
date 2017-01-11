package multipartyComputation;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import registration.RegisteredVoter;
import registration.Voter;
import systemCreation.Election;

/*
 * Author : Leyla
 * Date   : January 08, 2017
 */
public class DBHelper {
	
	private static final String SELECT_ALL_AUTHORITY_BY_ELECTIONID = "select * from LEYLA.Authority where electionID=?";
	private static final String SELECT_PRKEY_BY_AUTHORITY_CODE = "select authorityPr from LEYLA.AuthorityCredentials where authorityCode=?";
	private static final String SELECT_ELECTION_BY_ID = "select * from LEYLA.Election where electionID=?";
	private static final String SELECT_ACTIVE_ELECTION = "select * from LEYLA.Election where isCompleted=?"; 
	private static final String SELECT_PRKEY_BY_VOTER_ID = "select voterPr from LEYLA.VoterCredentials where voterID=?";
	private static final String SELECT_VALIDITY_COERCION_FLAG_BY_VOTER_ID = "select validityFlag, coercionFlag from LEYLA.Voter, LEYLA.RegisteredVoter where Voter.voterID=RegisteredVoter.voterID and Voter.voterID=?";
	private static final String INSERT_VOTE = "insert into LEYLA.Vote(voteID,voterID,signedEV,hash_seHregCode) values (?,?,?,?)";		
	private static final String INSERT_AUTHORITY_CREDENTIALS = "insert into LEYLA.AuthorityCredentials(authorityCode,authorityPr) values (?,?)";
	private static final String INSERT_VOTER_CREDENTIALS = "insert into LEYLA.VoterCredentials(voterID,voterPr) values (?,?)";
	private static final String SELECT_HASHREGCODE_HASHFAKEREGCODE_BY_VOTER_EMAIL = "select hash_regcode, hash_fakeregcode from LEYLA.Voter, LEYLA.RegisteredVoter where Voter.voterID=RegisteredVoter.voterID and Voter.email=?";
	private static final String SELECT_VOTER_ID_BY_VOTER_EMAIL = "select voterID from LEYLA.Voter where email =?";
	
	
	private Connection dbConnection;
	public DBHelper() {
		dbConnection = openConnection();
	}

	public ArrayList<Authority> getAuthorities(int electionID) throws Exception{
		ArrayList<Authority> authorityList = new ArrayList<Authority>();
		Authority authority = null;
   	 
		PreparedStatement prep = null;
		ResultSet rs = null;
		try {
		    prep = dbConnection.prepareStatement(SELECT_ALL_AUTHORITY_BY_ELECTIONID);
		    prep.setInt(1, electionID);
	    	rs = prep.executeQuery();
		    
	    	int id=1;
			while(rs.next())
	        {
				authority = new Authority(id, rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				authorityList.add(authority);
				id++;
	        }
		 } catch(SQLException ex) {
			 Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
	     }
	      			
   	 	return authorityList;
    }

	public BigInteger getAuthorityPrivateKey(int authorityCode){
		BigInteger authorityPr = null;
		
		PreparedStatement prep = null;
		ResultSet rs = null;
		try { 
			prep = dbConnection.prepareStatement(SELECT_PRKEY_BY_AUTHORITY_CODE);
		    prep.setInt(1, authorityCode);
	    	rs = prep.executeQuery();
			 
			while(rs.next())
			{
				/*
				BigDecimal bigDecimal = rs.getBigDecimal("authorityPr");
				authorityPr = (bigDecimal != null ? bigDecimal.toBigInteger() : null);
				*/

				authorityPr = new BigInteger(rs.getString(1));
			}
		} catch (SQLException ex) {
			 Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
	    }
	      			
		return authorityPr;
	}
	
	public boolean registerAuthorityCredentials(int authorityCode, BigInteger authorityPr)
	{
		boolean result = false;
		
		PreparedStatement prep = null;
	    try {
	    	prep = dbConnection.prepareStatement(INSERT_AUTHORITY_CREDENTIALS);
	    	prep.setInt(1, authorityCode);
	    	prep.setString(2, authorityPr.toString());
	    	prep.executeUpdate();
	    	result = true;
	    } catch (SQLException ex) {
	    	Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
		} 
	    
	    return result;
	}
	
	public boolean registerVoterCredentials(int voterID, BigInteger voterPr)
	{
		boolean result = false;
		
		PreparedStatement prep = null;
	    try {
	    	prep = dbConnection.prepareStatement(INSERT_VOTER_CREDENTIALS);
	    	prep.setInt(1, voterID);
	    	prep.setString(2, voterPr.toString());
	    	prep.executeUpdate();
	    	result = true;
	    } catch (SQLException ex) {
	    	Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
		} 
	    
	    return result;
	}
	
	public Election getElection(int electionID){
		Election election = null;
		
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		try { 
			prep = dbConnection.prepareStatement(SELECT_ELECTION_BY_ID);
		    prep.setInt(1, electionID);
	    	rs = prep.executeQuery();
			 
			while(rs.next())
			{
				election = new Election(rs.getInt(1), rs.getBoolean(2), rs.getDate(3), rs.getDate(4), rs.getDate(5), rs.getDate(6), rs.getString(7));
			}
		} catch (SQLException ex) {
			 Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
			try {
				if(prep != null) {
					prep.clearParameters();
					prep.close();
				}
				if(conn != null)
					conn.close();
		    } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	      			
		return election;
	 }
	
	public Election getActiveElection(){
		Election election = null;
		
		PreparedStatement prep = null;
		ResultSet rs = null;
		try { 
			prep = dbConnection.prepareStatement(SELECT_ACTIVE_ELECTION);
			prep.setBoolean(1, false);
	    	rs = prep.executeQuery();
			 
			while(rs.next())
			{
				election = new Election(rs.getInt(1), rs.getBoolean(2), rs.getDate(3), rs.getDate(4), rs.getDate(5), rs.getDate(6), rs.getString(7));
			}
		} catch (SQLException ex) {
			 Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
	    }
	      			
		return election;
	}
	
	public BigInteger getVoterPrivateKey(int voterID){
		BigInteger voterPr = null;
		
		PreparedStatement prep = null;
		ResultSet rs = null;
		try { 
			prep = dbConnection.prepareStatement(SELECT_PRKEY_BY_VOTER_ID);
		    prep.setInt(1, voterID);
	    	rs = prep.executeQuery();
			 
			while(rs.next())
			{
				voterPr = new BigInteger(rs.getString(1));
			}
		} catch (SQLException ex) {
			 Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
	    }
	      			
		return voterPr;
	}
	
	public int getVoterID(String email){
		int voterID = -1;
		
		PreparedStatement prep = null;
		ResultSet rs = null;
		try { 
			prep = dbConnection.prepareStatement(SELECT_VOTER_ID_BY_VOTER_EMAIL);
		    prep.setString(1, email);
	    	rs = prep.executeQuery();
			 
			while(rs.next())
			{
				voterID = rs.getInt(1);
			}
		} catch (SQLException ex) {
			 Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
	    }
	      			
		return voterID;
	}
	
	public ArrayList<Integer> getRegisteredVoterFlags(int voterID){
		ArrayList<Integer> flags = new ArrayList<>();
		
		PreparedStatement prep = null;
		ResultSet rs = null;
		try { 
			prep = dbConnection.prepareStatement(SELECT_VALIDITY_COERCION_FLAG_BY_VOTER_ID);
			prep.setInt(1, voterID);
	    	rs = prep.executeQuery();
			 
			while(rs.next())
			{
				System.out.println("ValidityFlag:"+ rs.getInt(1) + " CoercionFlag:"+ rs.getInt(2));
				flags.add(rs.getInt(1));
				flags.add(rs.getInt(2));
			}
		} catch (SQLException ex) {
			 Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
	    }
	      			
		return flags;
	}
	
	public boolean registerVote(String voteID, int voterID, String signedEV, String hash_seHregCode)
	{
		boolean result = false;
		
		PreparedStatement prep = null;
	    try {
	    	prep = dbConnection.prepareStatement(INSERT_VOTE);
	    	prep.setString(1, voteID);
	    	prep.setInt(2, voterID);
	    	prep.setString(3, signedEV);
	    	prep.setString(4, hash_seHregCode);
	    	prep.executeUpdate();
	    	result = true;
	    } catch (SQLException ex) {
	    	Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
		} 
	    
	    return result;
	}
	
	public boolean updateValidityFlag(int voterID, int validityFlag)
	{
		boolean result = false;
		String UPDATE_VALIDITY_FLAG = "update LEYLA.RegisteredVoter set validityFlag=" + validityFlag + " where voterID=?";
		
		PreparedStatement prep = null;
	    try {
	    	prep = dbConnection.prepareStatement(UPDATE_VALIDITY_FLAG);
	    	prep.setInt(1, voterID);
	    	prep.executeUpdate();
	    	result = true;
	    } catch (SQLException ex) {
	    	Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
		} 
	   
	    return result;
	}
	
	public boolean updateCoercionFlag(int voterID, int coercionFlag)
	{
		boolean result = false;
		String UPDATE_COERCION_FLAG = "update LEYLA.RegisteredVoter set coercionFlag=" + coercionFlag + " where voterID=?";
		
		PreparedStatement prep = null;
	    try {
	    	prep = dbConnection.prepareStatement(UPDATE_COERCION_FLAG);
	    	prep.setInt(1, voterID);
	    	prep.executeUpdate();
	    	result = true;
	    } catch (SQLException ex) {
	    	Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
		} 
	   
	    return result;
	}
	
	//--- Arzum-Burcu DBHelper
	private static Connection openConnection() {
        Connection connection = ConnectionManager.getInstance().getConnection();
        return connection;
	}

	
	public int openConnection(String userName, String password) throws SQLException
    {
        openConnection();
        return 0;
    }
	
	public ResultSet getResultOfQuery( String query){
	    ResultSet rs=null;
	    try {
	      
	        PreparedStatement ps = dbConnection.prepareStatement(query);/*a Statement object that carries your SQL language query to the database*/
	        rs = ps.executeQuery();/*instantiates a ResultSet object that retrieves the results of your query, and executes a simple while loop, which retrieves and displays those results.*/
	                                    /*The java.sql.ResultSet interface represents the result set of a database query. A ResultSet object maintains a cursor that points to the current row in the result set. 
	                                    The term "result set" refers to the row and column data contained in a ResultSet object.*/
	    } catch (SQLException ex) {
	        Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
	        /*public static final Level SEVERE
	        SEVERE is a message level indicating a serious failure.*/
	    }
	    return rs;
    }

	public Voter getVoter(String email){
		ResultSet rs=null;
		Voter voter = null;
		try {
	    
			String query = "SELECT * FROM voter where email = '"+ email+"'";
			//System.out.println(query);
			PreparedStatement ps = dbConnection.prepareStatement(query);
	   
			rs = ps.executeQuery(query);//returns result set of the query, used for only select queries

	        while(rs.next())
	        {
	        	// System.out.println("voterid: "+rs.getInt(1)+" name:"+rs.getString(2)+" email:"+rs.getString(3)+" birthdate: "+rs.getDate(4)+" certificateName:"+rs.getString(5));
	            voter = new Voter(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getString(5));
	        }
		} catch (SQLException ex) {
		    Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return voter;
	}

 	public boolean insertRegisteredVoter(RegisteredVoter regVoter )
    {
        try {
        	System.out.println("inside insert register method");
        	
            String query="INSERT INTO registeredvoter(voterid, hash_regcode, hash_fakeregcode, validityflag, coercionflag)"
                    + "values(?,?,?,?,?)";
            PreparedStatement ps = dbConnection.prepareStatement(query);/*An object that represents a precompiled SQL statement.
                                                                          A SQL statement is precompiled and stored in a PreparedStatement object.
                                                                        This object can then be used to efficiently execute this statement multiple times. Prepare statement is an interface extends from Statement class*/


            ps.setInt(1,regVoter.getVid());
            ps.setString(2, regVoter.getHashRegistrationCode());
            ps.setString(3, regVoter.getHashCoercionCode());
            ps.setBoolean(4, false);
            ps.setBoolean(5, false);
            ps.executeUpdate();
            
            System.out.println("executed");
            
            dbConnection.commit();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
             return false;
        }
    }

 	public int countRegisteredVoters(){
 		ResultSet rs=null;
 		int countOfRegisteredVoters=-1;
 		try {
        
 			String query = "SELECT COUNT(*) FROM registeredvoter";
 			PreparedStatement ps = dbConnection.prepareStatement(query);
 			rs = ps.executeQuery(query);//returns result set of the query, used for only select queries
 
	        while(rs.next())
	        {
	             System.out.println("Number of Eligible(Registered) Voters"+rs.getInt(1));
	             countOfRegisteredVoters = rs.getInt(1);
	        }
       
 		} catch (SQLException ex) {
 			Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
 		}
 		return countOfRegisteredVoters;
 	}

 	
 	public int getVotersSecret(int voterID){
 		int vSecret=-1;
 		try { 
 			String query = "SELECT * FROM votercredentials where voterid="+voterID;
 			ResultSet rs= getResultOfQuery(query);
		 
			 while(rs.next())
	         {
				 System.out.println("voterid: "+rs.getInt(1)+" secret:"+rs.getInt(2));
	             rs.getInt(1);
	             vSecret = rs.getInt(2);
	         }
	 	} catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
      	return vSecret;
 	}
 
 	
 	public String getHashOfSavedVoteForQuery(String hashOfRegCode)
 	{
 		System.out.println("inside get hash of casted vote for query");
 
 		String vSecret= null;
 		try 
 		{ 
	 		String query = "select HASH_SEHREGCODE from LEYLA.Vote where VOTEID="+hashOfRegCode;
	 		ResultSet rs= getResultOfQuery(query);
 			 
	 		while(rs.next())
	 	    {
	 			System.out.println("queriedVote:"+rs.getString(1));
	 	        vSecret = rs.getString(1);
	 	    }
 		} catch (SQLException ex) 
	 	{
	 		Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
	 	}
 	      			
 		return vSecret;
 	}
 	
 	public ArrayList<String> getRegCodeAndFakeRegCodeOfVoter(String email){
 		ArrayList<String> results = new ArrayList<>();
		
		PreparedStatement prep = null;
		ResultSet rs = null;
		try { 
			prep = dbConnection.prepareStatement(SELECT_HASHREGCODE_HASHFAKEREGCODE_BY_VOTER_EMAIL);
			prep.setString(1, email);
	    	rs = prep.executeQuery();
			 
			while(rs.next())
			{
	 			System.out.println("RegCode: "+rs.getString(1)+" FakeRegCode:"+rs.getString(2));
	 			results.add(rs.getString(1));
	 			results.add(rs.getString(2));
			}
		} catch (SQLException ex) {
			 Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
	    }
	      			
		return results;
	}
}


