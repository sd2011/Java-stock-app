package NewUI.Tasks;

import NewUI.UIAdapter;
import System.SystemProxy;
import javafx.concurrent.Task;

import java.util.concurrent.TimeUnit;

public class LoadTask extends Task<Boolean> {
    private final UIAdapter ui;
    private final SystemProxy prox;
    private final String file;

    public  LoadTask(UIAdapter ui, SystemProxy prox, String file) {
        this.ui =ui;
        this.prox = prox;
        this.file =file;
        this.ui.sendData(this);
    }
    @Override
    protected Boolean call() throws Exception {
        try {

            updateMessage("Loading file...");
            updateProgress(0,3);
            TimeUnit.SECONDS.sleep(2);
            this.prox.invoke("load", file);
            updateMessage("Updating stocks...");
            updateProgress(1,3);
            TimeUnit.SECONDS.sleep(1);
            updateMessage("Updating users...");
            updateProgress(2,3);
            TimeUnit.SECONDS.sleep(1);
            updateMessage("Done!");
            updateProgress(3,3);
            TimeUnit.MILLISECONDS.sleep(50);
            ui.sendData(null);
        } catch (Throwable e) {
            this.ui.makeException(e.getMessage());
        }
        return Boolean.TRUE;
    }
}
