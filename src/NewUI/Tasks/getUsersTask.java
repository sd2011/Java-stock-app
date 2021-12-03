package NewUI.Tasks;

import System.SystemProxy;
import javafx.concurrent.Task;
import NewUI.Data.UserData;
import NewUI.UIAdapter;

public class getUsersTask extends Task<Boolean> {


    private final UIAdapter ui;
    private final SystemProxy prox;

    public getUsersTask(UIAdapter ui, SystemProxy prox){
        this.ui  = ui;
        this.prox = prox;
    }

    @Override
    protected Boolean call() throws Exception {
        try {
            UserData user;
            int size = prox.invoke("getUsersSize");
            for(int i=0; i<size ; i++  ) {
                user = new UserData(prox.invoke("getUserName" ,i ));
                ui.sendData(user);
            }
        } catch (Throwable throwable) {
            ui.makeException(throwable.getMessage());

        }
        return Boolean.TRUE;
    }

    @Override
    protected void cancelled() {
        super.cancelled();
    }
}
