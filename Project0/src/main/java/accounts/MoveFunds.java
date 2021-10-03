package accounts;

import exceptions.InvalidConsoleResponse;

import java.nio.charset.StandardCharsets;

public class MoveFunds {
    String fundMovement;

    public MoveFunds(String typeOfFundMovement) {
        fundMovement = typeOfFundMovement;
    }

    public String moveFunds(){
        if(fundMovement.equals("Deposit")){
            //Calls the deposit method


            //I added this to keep this class from being red while I worked on other classes.
            //DELETE WHEN READY TO FINISH THIS METHOD
            String depositAmount = "0";




            System.out.println("Your deposit of " + depositAmount + "is complete! Thank you for your business!");
        } else if(fundMovement.equals("Withdrawal")){
            //Calls the withdrawal method
        } else if(fundMovement.equals("Transfer")){
            //Calls the withdrawal method on the first account and the deposit method on the second account.
        }
        return "Transaction successful!";
    }

    private boolean numOnly(String userInput, String message) throws InvalidConsoleResponse {
        byte[] bytes = userInput.getBytes(StandardCharsets.US_ASCII);
        int numberOfPeriods = 0;
        for(int i = 0; i < userInput.length(); i++) {
            if(bytes[i] <= 48 && bytes[i] >= 57 || bytes[i] != 46) {
                throw new InvalidConsoleResponse("Please only input numbers.\n" + message);

            }
            if(bytes[i] == 46){
                numberOfPeriods++;
            }
        }
        if(numberOfPeriods > 1){
            throw new InvalidConsoleResponse("Please only input one decimal point.\n" + message);
        }
        return true;
    }
}
