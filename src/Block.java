import java.util.Stack;
import java.security.MessageDigest;
import java.time.LocalDateTime;

public class Block {
	
	//The things that make up a block.
	public String priorBlockHash;
	public int placement;
	public String timeStamp = LocalDateTime.now().toString();
	public Stack<Transaction> blockData;
	public int nonce;
	public String hash;

	/**
	 * Timestamp doesn't matter much in terms of use;
	 * But it has to be different for every block
	 * for the sake of hashing
	 * @param p
	 */
	
	public void setPlacement(int p) {
		placement = p;
	}
	
	/**
	 * Everything else is defined manually, or
	 * automatically like the timestamp. That's why all that's created is the trx stack
	 */
	
	public Block() {
		this.blockData = new Stack<Transaction>();
	}
	
	public Stack<Transaction> getBlockData() {
		return blockData;
	}
	
	public void setPriorBlockHash(String s) {
		this.priorBlockHash = s;
	}
	
	public void addTransaction(Transaction t) {
		blockData.add(t);
	}
	
	//String form of JUST transaction data
	public String dataToString() {
		String result = "";
		
		for (Transaction t : this.getBlockData()) {
			result += t.readable() + "\n";
		}
		
		return result;
	}

	/**
	 * Another way to print a block. This one is formatted to be more readable, while the
	 * previous way holds all the information too but is quicker to hash. They're used in 
	 * different places. 
	 * @return
	 */
	public String polishedPrintEntireBlock() {
		String result = "";
	
		result += "This is block #" + this.placement + "\n";
		result += "Previous hash: " + priorBlockHash + "\n";
		
		if (this.blockData.isEmpty()) {
			result += "No transactions in this block.";
		} else {
			result += "Transactions: \n" + this.dataToString();
		}
			
		return result;
	}
	
	
	/**THS METHOD IS NOT MY CODE!
	 * Copied from online
	 * feels like a standard enough 
	 * thing to just import
	 * 
	 * accidental haiku lol
	 * 
	 * Of course, hashing will be important in the POW method, coming up
	 */
	public String applySha256(String s) {		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			byte[] hash = digest.digest(s.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer(); 
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Hashes all the data in a block. Includes a nonce, but is not a parameter;
	 * nonce value is held elsewhere. 
	 * @return
	 */
	public String hashBlockData() {
		String result = "";
		
		result += this.placement;
		result += this.timeStamp;
		result += this.priorBlockHash;
		result += this.dataToString();
		result += this.nonce;
		
		result = applySha256(result);
		
		return result;
	}
	

	
	
	/**The Proof-of-Work process that makes everything happen. This is the crux of
	 * a blockchain, but here's a super quick description:
	 * 
	 * A random number (the Nonce) is added to the block, then the whole thing
	 * is hashed. The hash returns an essentialy random string, which will be completely
	 * different even if the nonce is altered just a little. The Nonce is randomized
	 * until the hash begins with an arbitrary number of Zeroes, which can only
	 * happen after a random but number of nonces are tried and hashed.  
	 *
	 * @param difficulty
	 * How many zeroes are needed to "solve" the block. At least in this IDE, 4 or less is instant, 5
	 * takes about 10 seconds, and 6 takes 2-3 minutes. Each step higher should roughly x16 the expected time.
	 * @param loud
	 * Whether or not to throw some print statements in there to track progress, used for tests.  
	 */
	public void mine(int difficulty, boolean loud) {
		
		String target = ""; //Construct the string of 0's
		while (difficulty > target.length()) {target += "0";}
		
		if (loud) {
		StdOut.println("Beginning to mine block #" + this.placement
				+ " with " + (this.blockData.size()-1) + " transactions");
		}
		
		//Ignored the miner reward in the transaction number
		//Loop that will change the nonce until the right one is found
		hash = this.hashBlockData();
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = this.hashBlockData();
		}
		
		if (loud) {
		StdOut.println("Mined Block #" + placement 
				+ ", Nonce: "+ nonce + ", Hash: " + hash);
		}
		
	}
	

	}
	
	
	
