package multipartyComputation;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/*
 * Author : Leyla
 * Date   : January 05, 2017
 */

public class Shamir {

	private int k;	// threshold
	private int n;	// number of shares
	private BigInteger p;
	private Random random;
	
	public Shamir(int k, int n){
		setK(k);
		setN(n);
		this.random = new SecureRandom();
	}

	public Share[] distribute(final BigInteger secret)
	{
		p = BigInteger.probablePrime(secret.bitLength()+1, random);
		
		//System.out.println("Random p:" + p);
		
		final BigInteger[] coef = new BigInteger[k-1];
		for(int i=0; i<k-1; i++){
			coef[i] = randomFieldFp();
		}	
				
		final Share[] shares = new Share[n];
		for(int i=1; i<=n; i++){
			BigInteger sum = secret;
			
			for(int j=1; j<k; j++){
				BigInteger temp = BigInteger.valueOf(i).modPow(BigInteger.valueOf(k-j), p);
				BigInteger temp2 = coef[j-1].multiply(temp).mod(p);
				
				sum = sum.add(temp2).mod(p);
			}
			shares[i-1] = new Share(i, sum);
		}
		return shares;
	}
	
	public BigInteger reconstruct(Share[] shares)
	{
		BigInteger sum = BigInteger.ZERO;
        for (int i=0; i<k; i++) {
        	final BigInteger value = shares[i].getValue(); 
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;
           
            for (int j=0; j<k; j++) {
                if (i != j) {
                    numerator = numerator.multiply(BigInteger.valueOf(-shares[j].getId())).mod(p);
                    denominator = denominator.multiply(BigInteger.valueOf(shares[i].getId()-shares[j].getId())).mod(p);
                }
            }

            BigInteger invOfDenominator = denominator.modInverse(p);
            BigInteger product = value.multiply(numerator).multiply(invOfDenominator).mod(p);
            sum = sum.add(p).add(product).mod(p);
        }
        return sum;
	}
	
	public BigInteger randomFieldFp(){
		BigInteger c = null;
		do {
		    c = new BigInteger(p.bitLength(), random);
		} while(c.compareTo(BigInteger.ZERO) > 0 && c.compareTo(p) >= 0);
		
		return c;
	}
	
	public void checkForPositive(int number) {
	    if (number < 1) {
	      throw new IllegalArgumentException(number + " is less than 1");
	    }
	}
	
	public int getK() {
		return k;
	}
	
	public void setK(int k){
		checkForPositive(k);
		this.k = k;
	}
	
	public int getN() {
		return n;
	}
	
	public void setN(int n){
		checkForPositive(n);
		this.n = n;
	}

	public BigInteger getP(){
		return p;
	}	
}
