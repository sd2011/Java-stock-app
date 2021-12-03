package System.Users;

import java.util.ArrayList;
import java.util.List;

public class Actions {
    private final List<Action> actions;


    public Actions() {
        this.actions = new ArrayList<>();
    }

    public void addAction(String type, String symbol, String date, float sum, float preBalance, float newBalance) {
        actions.add(0,new Action(type,symbol,date,sum,preBalance,newBalance));
    }
}
