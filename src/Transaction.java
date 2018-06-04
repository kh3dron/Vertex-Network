import java.time.LocalDateTime;

public class Transaction {

	public String timeStamp;
	public Account from;
	public long amount;	
	public Account to;

	
	public void setFrom(Account from) 		{this.from = from;}
	public void setAmount(long amount) 		{this.amount = amount;}
	public void setTo(Account to) 			{this.to = to;}
	
	public String getTimeStamp() 			{return timeStamp;}
	public Account getFrom() 				{return from;}
	public long getAmount() 				{return amount;}
	public Account getTo() 					{return to;}

	
	/**A fairly simple object. Just holds the details
	 * that define a transaction.
	 * Note that a timestamp is included - this means that
	 * two otherwise identical transactions wil hash differently, good for security
	 */
	public Transaction(Account from, long amount, Account to) {
		this.timeStamp = LocalDateTime.now().toString();
		this.from = from;
		this.amount = amount;
		this.to = to;
	}
	
	public Transaction() {
		this.timeStamp = LocalDateTime.now().toString();
	}
	
	public String readable() {
		
		String result = "At " + String.valueOf(timeStamp)
		+ " " + from.getName() + " sent "
		+ String.valueOf(amount) + " coins to " + to.getName();			
		return result;
	}		
}
	

