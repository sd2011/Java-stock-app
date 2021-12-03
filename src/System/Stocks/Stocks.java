package System.Stocks;

import System.RseStock;
import System.RseStocks;
import System.Users.User;

import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

public class Stocks implements Serializable
{
    private final Map<String,Stock> stocks;

    public Stocks(RseStocks stocksToLoad){
        stocks = new HashMap<>();
        List<RseStock> RseList = stocksToLoad.getRseStock();
        for (RseStock stock: RseList)
        {
        stocks.put(stock.getRseSymbol(),new Stock(stock));
        }
    }

    public Stocks(Stock stock){
        this.stocks = new HashMap<>();
        this.stocks.put(stock.getSymbol(),stock);
    }

    public void addStocks(RseStocks stocksToLoad) {
        List<RseStock> RseList = stocksToLoad.getRseStock();
        for (RseStock stock: RseList)
        {
            if(!stocks.containsKey(stock.getRseSymbol()))
            stocks.put(stock.getRseSymbol(),new Stock(stock));
        }
    }

    public void addStock(Stock newStock) {
        for (Stock stock : this.stocks.values()){
            if (stock.getSymbol().equals(newStock.getSymbol()) || stock.getCompanyName().equals(newStock.getCompanyName()))
                throw new InputMismatchException("Stock already exists in the system");
        }
        this.stocks.put(newStock.getSymbol(), newStock);

    }


    public Stock getStock(int i){
       try {
           return stocks.get(stocks.keySet().toArray()[i]);
       }
       catch(NullPointerException e){
           throw new NullPointerException("Symbol does not match any of the symbols in system\n");
       }

    }
    public Stock getStock(String symbol){
        Stock stock = stocks.get(symbol.toUpperCase());
        if(stock == null)
            throw new NullPointerException("Stock symbol does not match any of the stock symbols in system\n");
        return stock;


    }


    public int getstocksSize() { return this.stocks.size(); }

    public boolean addRequest(boolean buyOrSell, String symbol, int amount, float gate, String type, User user) throws Exception {
        if(amount < 1){
            throw new Exception("Amount must be a positive whole number\n");
        }
        if(gate < 0){
            throw new Exception("Limit must be a none negative number\n");
        }
        if (!stocks.containsKey(symbol.toUpperCase())) {
            throw new NoSuchObjectException("Symbol doesn't match any of the stocks in system!");
        }
        if(!buyOrSell && !user.containsItem(symbol)){
           throw new NoSuchObjectException("User can't sell stocks of an item he dont hold position of");
        }

        if(!buyOrSell && (user.getItemQuantity(symbol) - user.getItemAmountOnHold(symbol) < amount)){
            throw new Exception("User doesn't have enough stocks available of this item to make such a sell");
        }
        else {
            return getStock(symbol).addRequest(buyOrSell, amount, gate, type,user);

        }

    }



}
