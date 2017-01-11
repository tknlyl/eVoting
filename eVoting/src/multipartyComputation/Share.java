package multipartyComputation;

import java.math.BigInteger;

/*
 * Author : Leyla
 * Date   : January 05, 2017
 */

public class Share {
	
	private final int id;
	private final BigInteger value;
	
	public Share(final int id, final BigInteger value){
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public BigInteger getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Share [id=" + id + ", value=" + value + "]";
	}
}


