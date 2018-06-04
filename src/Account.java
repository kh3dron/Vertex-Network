public class Account {

	/**
	 * Again, a super simple object that just holds a handful of values. 
	 * No real methods, like in transaction. 
	 */
	
	public String name;
	public long balance; 
	public long genesisBalance;
	
	public String getName() {return name;}
	public long getBalance() {return balance;}

	public Account(String name, int balance) {
		this.name = name;
		this.balance = balance;
		this.genesisBalance = balance;
	}

	public void deductCoins(long i) {
		balance = balance - i;
	}
	
	public void gainCoins(long i) {
		balance = balance + i;
	}
	
}
