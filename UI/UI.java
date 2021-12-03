package UI;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//no generics
public class UI extends Options {

    private boolean running;


    private enum Opt {
        ReadFile(1),
        ShowStocks(2),
        StockInfo(3),
        TradeAction(4),
        ActionsLists(5),
        Save(6),
        Load(7),
        Exit(8);

       private static final Map map = new HashMap<>();
        private final int value;

         Opt(int value){
            this.value = value;
        }

        static {
            for (Opt opt : Opt.values()){
                map.put(opt.value, opt);
            }
        }

        public static Opt valueOf(int val){
            return (Opt) map.get(val);
        }

    }

    public UI(){
        running = true;
    }

    public static void main(String[] args) throws Exception {
        UI ui=  new UI();
        ui.run();
    }

    public void run() throws Exception {


        while(running){

            switch (Opt.valueOf(getOption())) {
                case ReadFile:
                    ReadFile();
                    break;
                case ShowStocks:
                    ShowStocks();
                    break;
                case StockInfo:
                    StockInfo();
                    break;
                case TradeAction:
                    TradeAction();
                    break;
                case ActionsLists:
                    ActionsLists();
                    break;
                case Save:
                    Save();
                    break;
                case Load:
                    Load();
                    break;
                case Exit:
                    Exit();
                    break;
                default:
                    //
            }
        }
    }

    public int getOption() {
        //need exception
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Please choose one of the following options:\n" +
                "Press 1 and then press enter to read a file.\n" +
                "Press 2 and then press enter to show stocks.\n" +
                "Press 3 and then press enter to get a stock info.\n" +
                "Press 4 and then press enter to do a trade action.\n" +
                "Press 5 and then press enter to watch actions lists.\n" +
                "Press 6 and then press enter to save account\n" +
                "Press 7 and then press enter to load an account.\n" +
                "Press 8 and then press enter to exit Rizpa.\n");

        Scanner scanner = new Scanner(System.in);
        int res = scanner.nextInt();
        scanner.nextLine();
        return res;

    }

    public void Exit() {
        System.out.println("Thanks for using Rizpa!\n Have a great day, goodbye. ");
        running = false;
    }



}
