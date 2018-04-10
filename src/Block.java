import java.util.Stack;

public class Block {

	//It is a chain, after all
	public Block previousBlock;
	
	//This is where the proof of work will happen later
	public String nonce;
	
	//the main attraction, a list of transactions
	public Stack<Transaction> transactions;
	
	
}
