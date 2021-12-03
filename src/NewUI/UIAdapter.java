package NewUI;

import NewUI.Tasks.*;
import System.SystemProxy;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.function.Consumer;


public class  UIAdapter <T> {
    private final Consumer<T> func;
    private final SystemProxy prox;
    private final Consumer<String> problem;


    public UIAdapter(Consumer<T> func, SystemProxy prox ,Consumer<String> problem){
        this.func =func;
        this.prox  = prox;
        this.problem = problem;
    }


    public void sendData(T data) {
        Platform.runLater(
                () -> func.accept(data)
        );
    }
    public Task<Boolean> loadFile(String file) {
        Task<Boolean> task = new LoadTask(this,prox,file);
        new Thread(task).start();
        return task;
    }
    public Task <Boolean> getUsers(){
        Task<Boolean> task = new getUsersTask(this,prox);
        new Thread(task).start();
        return task;
    }

    public Task<Boolean> getHoldings(String name) {

        Task<Boolean> task = new getHoldingsTask(this,prox,name);
        new Thread(task).start();
        return task;
    }

    public Task<Boolean> getFilterdStocks(String value, boolean isSell, String name) {
        Task<Boolean> task = new getFilterdStocksSTask(this,prox,value,isSell,name);
        new Thread(task).start();
        return task;

    }

    public Task<Boolean> makeOrder(Boolean isSell, Boolean isMkt, String symbol, int amount, float limit, String name) {
        Task<Boolean> task = new MakeOrderTask(this,prox,isSell,isMkt,symbol,amount,limit,name);
        new Thread(task).start();
        return task;
    }

    public Task<Boolean> getStockInfo(String symbol, String type) {
        Task<Boolean> task = new getStockInfoTask(this,prox,symbol,type);
        new Thread(task).start();
        return task;
    }


    public void makeException(String message) {
        Platform.runLater(
                ()->this.problem.accept(message)
        );
    }
}
