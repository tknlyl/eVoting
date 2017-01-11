package multipartyComputation;

/*
 * Author : Leyla
 * Date   : January 05, 2017
 */
public class EncryptedShare{

	private final int id;
	private final byte[] encValueBytes;
	
	public EncryptedShare(final int id, final byte[] encValueBytes){
		this.id = id;
		this.encValueBytes = encValueBytes;
	}

	public int getId() {
		return id;
	}

	public byte[] getEncValueBytes() {
		return encValueBytes;
	}
}

