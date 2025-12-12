/**
* Relax Gaming - Java clean code test
*
* For the purposes of this test, the candidate can assume that the code compiles and that references
* to other classes do what you would expect them to.
*
* The objective is for the candidate to list down the things in plain text which can be improved in this class
*
* Good luck!
*
*/

public class account
{
    /**
     * Review: accountNumber should be private
     */
    public String accountNumber;
    
    public account(String accountNumber){
        // Constructor
        /**
         * Review: accountNumber should be checked before assignment (e.g. null or empty or not valid)
         */
        this.accountNumber = accountNumber;
    }
    /*
     * Review: add javadoc for public methods
     */
    public String getAccountNumber(){
        return accountNumber; // return the account number
    }
    
    public ArrayList getTransactions() throws Exception{
        try{
            List dbTransactionList = Db.getTransactions(accountNumber.trim()); //Get the list of transactions
            /**
             * Review: Use List<Transaction> instead of raw ArrayList
             */
            ArrayList transactionList = new ArrayList();
            /**
             * Review: prefer for(DbRow dbRow : dbTransactionList) syntax for better readability
             */
            int i;
            for(i=0; i<dbTransactionList.size(); i++){
                DbRow dbRow = (DbRow) dbTransactionList.get(i);
                Transaction trans = makeTransactionFromDbRow(dbRow);
                /**
                 * Review: createTimestampAndExpiryDate is called twice, store the result in a variable instead
                 * if trans == null, continue to next iteration
                 */
                trans.setTimestamp(createTimestampAndExpiryDate(trans)[0]);
                trans.setExpiryDate(createTimestampAndExpiryDate(trans)[1]);
                transactionList.add(trans);
            }
            return transactionList;
            
        } catch (SQLException ex){
            /**
             * Review: Log the exception message for debugging purposes
             * should not throw a generic exception, create a custom exception or rethrow SQLException
             */
            // There was a database error
            throw new Exception("Can't retrieve transactions from the database");
        }
    }
    
    public Transaction makeTransactionFromDbRow(DbRow row)
    {
        double currencyAmountInPounds = Double.parseDouble(row.getValueForField("amt"));
        /**
         * Review: Use double instead of float for currencyAmountInEuros
         */
        float currencyAmountInEuros = new Float(currencyAmountInPounds * 1.10);
        String description = row.getValueForField("desc");
        /**
         * Review: fixDescription is commented out,  remove it or uncomment it
         */
//        description = fixDescription(description);
        return new Transaction(description, currencyAmountInEuros); // return the new Transaction object
    }

    /**
     * Review: parameter can be removed
     */
    public String[] createTimestampAndExpirydate(Transaction trans) {
        /**
         * Review: The array should be initialized with a size
         * String[] return1 = new String[2];
         */
    	String[] return1 = new String[]{};
    	LocalDateTime now = LocalDateTime.now();
    	return1[0] = now.toString();
        /**
         * Review: use now variable instead of calling LocalDateTime.now() again
         */

    	return1[1] = LocalDateTime.now().plusDays(60).toString();
    	
    	return return1;
    	
    }
    
    public String fixDescription(String desc) {
    	String newDesc = "Transaction [" + desc + "]";
    	return newDesc;
    }

    /**
     * Review: use @Override annotation, change parameter type to Object
     */
    // Override the equals method   
    public boolean equals(Account o) {
        /**
         * Review: accountNumber is a string, should use .equals() for comparison
         */
        return o.getAccountNumber() == getAccountNumber(); // check account numbers are the same 
    }
}       

