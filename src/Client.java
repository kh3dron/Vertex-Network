import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * This is where the main program is run. Honestly, never ran it outside of an IDE, but it worked there. 
 * @author James
 *
 */

public class Client {
	/**This program is not networked. It is a blockchain, 
	 * but it runs locally. These accounts can send and 
	 * recieve transactions, and be logged in to, but 
	 * are all hardcoded and local.
	 */
	Account Ahmed = 	new Account("Ahmed",	500);
	Account Iddo = 		new Account("Iddo",		499);		//get wrecked
	Account Ben = 		new Account("Ben", 		500);
	Account Rishi = 	new Account("Rishi", 	500);
	Account Roshan = 	new Account("Roshan", 	500);
	Account Pimmy = 	new Account("Pimmy", 	500);
	Account Shannon = 	new Account("Shannon", 	500);	
	Account Casey = 	new Account("Casey", 	500);
	Account Nick = 		new Account("Nick", 	500);
	Account Gabe = 		new Account("Gabe", 	500);
	Account Tristan = 	new Account("Tristan", 	500);
	Account Martinez = 	new Account("Martinez", 500);

	/**
	 * When a user mines a block, they get paid from here.
	 * 
	 * In a more robust version, the miner payout would be a method in block, but
	 * this is simpler (while less secure, but it's local, come on) and cooperates
	 * with other methods better, because it's just a transaction
	 * 
	 */
	
	Account Odin = 		new Account("Mining Rewarder", 2147483647);

	public Block currentBlock = new Block();
	
	public Account signedInUser = Tristan;
	
	//This is where the blockchain is 
	public Stack<Block> blockchain = new Stack<Block>();

	//probably a better way to do this
	public ArrayList<Account> allAccounts = new ArrayList<Account>(Arrays.asList(
			Ahmed, Iddo, Ben, Rishi, Roshan, Pimmy,
			Shannon, Casey, Nick, Gabe, Tristan, Martinez
			));
	
	//Constructs a transaction from user inputs
	public Transaction buildTransaction() {
		
		Transaction r = new Transaction();
		r.setFrom(signedInUser);
		r.setTo(chooseUserFromList());
		StdOut.print("How many coins would you like to send?\n> ");
		int amount = StdIn.readInt();
		r.setAmount(amount);
		StdOut.println("Transaction built. That transaction will be in Block #"
		+ currentBlock.placement + ".");
		this.currentBlock.addTransaction(r);
		return r;
	}
	
	//Builds the special transaction for Mining a block
	public Transaction minerReward(Account a) {
		return new Transaction(Odin, 5, a);
	}
	
	//All user input is ints, to simplify things
	public Account chooseUserFromList() {
		StdOut.println("Choose a user from the list! Enter number below.");
		int tick = 0;
		for (Account a : allAccounts) {
			StdOut.println(tick + " " + a.getName());
			tick++;
		}
		StdOut.print("> ");
		int name = StdIn.readInt();
		
		while (name >= allAccounts.size() || name < 0) {
			StdOut.println("That didn't work. Try again?");
			StdOut.print("> ");
			name = StdIn.readInt();
		}
		
		return allAccounts.get(name);
		
	}
	
	public void signInUser(Account a) {
		this.signedInUser = a;
	}

	public void addBlockToChain(Block b) {
		blockchain.add(b);
	}
	
	/**
	 * Reads every transaction in a block and updates balances. Here's why that Odin solution to miner
	 * payouts is used, it fits in to this cleanly
	 * @param b
	 */
	
	public void updateAccountBalancesByBlock(Block b) {
		for (Transaction t : b.getBlockData()) {
			t.getFrom().deductCoins(t.getAmount());
			t.getTo().gainCoins(t.getAmount());
			}
		}
	
	//main loop
	public int centralAsk() {
		StdOut.println("\nWhat do you want to do?"
				+ "\n[0] View your transaction history"
				+ "\n[1] Send some coins to a friend"
				+ "\n[2] Read the Blockchain"
				+ "\n[3] Read another user's TX history"
				+ "\n[4] Read the contents of the current block"
				+ "\n[5] Mine the current block"
				+ "\n[6] Sign in as a differnt user"
				+ "\n[7] Help Menu"
				);
		StdOut.print("> ");
		return StdIn.readInt();
	}
	
	//Shows every transaction an account is a part of
	public void showTransactionHistory(Account a) {
		
		StdOut.println("The account " + a.getName() + " currently hodls " + a.balance + " coins.");
		StdOut.println("The genesis balance was " + a.genesisBalance + " Coins."
						+ " Transactions made to or from " + a.getName() + ":");
		boolean found = false;
		for (Block b : blockchain) {
			for (Transaction t : b.getBlockData()) {
					if ((t.getFrom() == a) || t.getTo() == a) {
						found = true;
						if (t.getFrom() == Odin) {
							StdOut.println("At " + t.getTimeStamp() + " " + a.getName()
									+ " mined block #" + b.placement
									+ " and earned " + t.getAmount() + " coins");
						} else {
							StdOut.println(t.readable());
						}
					}
				}
			}
		if (!found) {
			StdOut.println(a.getName() + " has never made a transaction on the network.");
		}
	}
	
	/**
	 * Shows contents of the current, unmined block. 
	 * Not actually very useful, but a good way to show of the datastructure of 
	 * what a blockchain is. This is for a class, after all
	 */
	public void currentBlockStatus() {	
			StdOut.println(currentBlock.polishedPrintEntireBlock());
		}
	
	/**
	 * Adds the current block to chain and creates new current. 
	 * Assumes the current block has been mined/solved. 
	 */
	public void generateNewCurrentBlock() {
		this.blockchain.add(currentBlock);
		String passTheHashBro = currentBlock.hashBlockData();
		currentBlock = new Block();
		currentBlock.setPlacement(this.blockchain.size());
		currentBlock.setPriorBlockHash(passTheHashBro);
	}
	
	public void signInAsOtherUser() {
		this.signedInUser = chooseUserFromList();
	}
	
	//Prints all data from all blocks
	public void readBlockchain() {
		
		if (blockchain.size() == 0) {
			StdOut.println("There's no blocks yet! Mine one to get things rolling.");
		} else {
			for (Block b : this.blockchain) {
				StdOut.println(b.polishedPrintEntireBlock());
			}
		}
	}
		
	//Adds miner reward transaction and does POW. genereates new current block. 
	public void mineCurrentBlock(int difficulty, boolean loud) {
		currentBlock.addTransaction(minerReward(signedInUser));
		this.currentBlock.mine(difficulty, loud);
		updateAccountBalancesByBlock(currentBlock);
		generateNewCurrentBlock();	
	}
	
	
	public void printHelpMenu(int difficulty) {
		StdOut.println(
				  "Vertex Network Version 2.4.1"
				+ "\nNetwork Hashing Difficulty: " + difficulty
				+ "\nVertex Network* has an asterisk because"
				+ "\nnothing about this program is networked."
				+ "\n"
				+ "\nVertex Coins are not legal tender."
				+ "\nAs few animals as possible were harmed in the"
				+ "\nmaking of this program. Any references to actual"
				+ "\npersons, events, establishments, or"
				+ "\nlocations are purely coincidental.");
	
	}
	
	public void printLogo() {
		StdOut.println(
				" __   __  _______  ______    _______  _______  __   __            \r\n" + 
				"|  | |  ||       ||    _ |  |       ||       ||  |_|  |           \r\n" + 
				"|  |_|  ||    ___||   | ||  |_     _||    ___||       |           \r\n" + 
				"|       ||   |___ |   |_||_   |   |  |   |___ |       |           \r\n" + 
				"|       ||    ___||    __  |  |   |  |    ___| |     |            \r\n" + 
				" |     | |   |___ |   |  | |  |   |  |   |___ |   _   |           \r\n" + 
				"  |___|  |_______||___|  |_|  |___|  |_______||__| |__|           \r\n" + 
				" __    _  _______  _______  _     _  _______  ______    ___   _ * \r\n" + 
				"|  |  | ||       ||       || | _ | ||       ||    _ |  |   | | |  \r\n" + 
				"|   |_| ||    ___||_     _|| || || ||   _   ||   | ||  |   |_| |  \r\n" + 
				"|       ||   |___   |   |  |       ||  | |  ||   |_||_ |      _|  \r\n" + 
				"|  _    ||    ___|  |   |  |       ||  |_|  ||    __  ||     |_   \r\n" + 
				"| | |   ||   |___   |   |  |   _   ||       ||   |  | ||    _  |  \r\n" + 
				"|_|  |__||_______|  |___|  |__| |__||_______||___|  |_||___| |_|  \n"		
		);	
	}
	
	//RUNS THE PROGRAM
	public void run() {		
		
		printLogo();
		StdOut.println("Welcome to the Vertex Network*!");
		StdOut.println("You're logged in as " + signedInUser.name);
		
		int difficulty = 5; //4 is instant, 5 is ~10 secs, 6 is ~2 minutes
		
		while (true) {
			
			int process = centralAsk();
			StdOut.println();
			
			if (process == 0) {
				showTransactionHistory(this.signedInUser);
			}
			if (process == 1) {
				buildTransaction();
			}
			if (process == 2) {
				readBlockchain();
			}
			if (process == 3) {
				showTransactionHistory(chooseUserFromList());
			}
			if (process == 4) {
				currentBlockStatus();
			}
			if (process == 5) {
				mineCurrentBlock(difficulty, true);
				}	
			if (process == 6) {
				signInAsOtherUser();
				StdOut.println("\nWelcome to the Vertex Network*!");
				StdOut.println("You're logged in as " + signedInUser.name);	
			}
			if (process == 7) {
				printHelpMenu(difficulty);
				
			}	
		}
	}
		
	public static void main(String[] args) {
		/**
		 * honestly, this run() way of doing things seems like a bit of a redundant step, 
		 * but that's how the prof does all of her programs so whatevs
		 */
		new Client().run();
	}	
}

