package System.Users;

public class Action {
    private enum Type{
        LOAD,BUY,SELL
    }
    Type type;
    private final String date;
    private final String symbol;
    private final float sum;
    private final float previousBalance;
    private final float newBalance;


    public Action(String type , String symbol,String date, float sum, float previousBalance, float newBalance) {
        this.type = Type.valueOf(type);
        this.date = date;
        this.sum = sum;
        this.previousBalance = previousBalance;
        this.newBalance = newBalance;
        this.symbol =symbol;
    }


}
