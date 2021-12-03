package System.WebApi;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private List<Massage> massageList;


    private class Massage {
        private String name;
        private String text;

        public Massage(String name,String text){
            this.name = name;
            this.text = text;

        }
    }


    public ChatManager(){
        this.massageList=new ArrayList<>();
    }

    public void addMassage(String name, String text) {
        this.massageList.add((new Massage(name,text)));
    }

    public List<?> getMassageList(int fromIndex) {
        return this.massageList.subList(fromIndex,this.massageList.size());
    }

    public int getLastIndex() {
    return this.massageList.size();
    }
}
