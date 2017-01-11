package systemCreation;

import java.util.Date;

public class Election {

	private int electionID;
	private Date regStartTime;
	private Date regEndTime;
	private Date electionStartTime;
	private Date electionEndTime;
	private boolean isCompleted;
	private String hashOfPrKey;
	
	public Election(int electionID, boolean status, Date regStartTime, Date regEndTime, Date electionStartTime, Date electionEndTime) {
		this.electionID = electionID;
		this.isCompleted = status;
		this.regStartTime = regStartTime;
		this.regEndTime = regEndTime;
		this.electionStartTime = electionStartTime;
		this.electionEndTime = electionEndTime;
	}
	
	public Election(int electionID, boolean status, Date regStartTime, Date regEndTime, Date electionStartTime, Date electionEndTime, String hashOfPrKey) {
		this.electionID = electionID;
		this.regStartTime = regStartTime;
		this.regEndTime = regEndTime;
		this.electionStartTime = electionStartTime;
		this.electionEndTime = electionEndTime;
		this.isCompleted = status;
		this.hashOfPrKey = hashOfPrKey;
	}
	
	public Election(Date regStartTime, Date regEndTime, Date electionStartTime, Date electionEndTime, boolean status) {
		this.regStartTime = regStartTime;
		this.regEndTime = regEndTime;
		this.electionStartTime = electionStartTime;
		this.electionEndTime = electionEndTime;
		this.isCompleted = status;
	}
	
	public Date getRegStartTime() {
		return regStartTime;
	}
	public void setRegStartTime(Date regStartTime) {
		this.regStartTime = regStartTime;
	}
	public Date getRegEndTime() {
		return regEndTime;
	}
	public void setRegEndTime(Date regEndTime) {
		this.regEndTime = regEndTime;
	}
	public Date getElectionStartTime() {
		return electionStartTime;
	}
	public void setElectionStartTime(Date electionStartTime) {
		this.electionStartTime = electionStartTime;
	}
	public Date getElectionEndTime() {
		return electionEndTime;
	}
	public void setElectionEndTime(Date electionEndTime) {
		this.electionEndTime = electionEndTime;
	}
	public boolean getStatus() {
		return isCompleted;
	}
	public void setStatus(boolean status) {
		this.isCompleted = status;
	}

	public int getElectionID() {
		return electionID;
	}

	public void setElectionID(int electionID) {
		this.electionID = electionID;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getHashOfPrKey() {
		return hashOfPrKey;
	}

	public void setHashOfPrKey(String hashOfPrKey) {
		this.hashOfPrKey = hashOfPrKey;
	}
}
