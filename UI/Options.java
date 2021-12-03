package UI;


import java.util.*;

import System.SystemProxy;
import UI.options.*;


public  class Options {
    private final SystemProxy prox;
    private final Scanner scanner;
    private final ReadFile readFile;
    private final ActionsLists actionsLists;
    private final ShowStocks showStocks;
    private final StockInfo stockInfo;
    private final TradeAction tradeAction;
    private final Save save;
    private final Load load;

    public Options() {
        this.prox = new SystemProxy();
        this.scanner = new Scanner(System.in);
        this.readFile = new ReadFile();
        this.showStocks = new ShowStocks();
        this.actionsLists = new ActionsLists();
        this.stockInfo = new StockInfo();
        this.tradeAction = new TradeAction();
        this.save = new Save();
        this.load = new Load();
    }
    public void ReadFile(){
        readFile.execute(this);
    }

    public void ActionsLists(){
        actionsLists.execute(this);
    }

    public void ShowStocks(){
        showStocks.execute(this);
    }

    public void StockInfo(){
        stockInfo.execute(this);
    }

    public void TradeAction(){
        tradeAction.execute(this);
    }

    public void Save(){save.execute(this);}
    public void Load() {load.execute(this);}

    public SystemProxy getProx() {
        return this.prox;
    }

    public Scanner getScanner() {
        return this.scanner;
    }
}
