package UI.options;

import System.SystemProxy;
import UI.Options;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ReadFile  {

    public void execute( Options options)  {
        SystemProxy prox =  options.getProx();
        Scanner scanner = options.getScanner();

        System.out.println("Please write a full path to an XML file to be loaded to the system:\n ");
        try {
            prox.invoke("load",scanner.nextLine());
            System.out.println("File loaded successfully!\n ");

        }catch(InputMismatchException e){
            System.out.println("Error! Input does not match specified argument\n\n");
        } catch( Throwable e){
            System.out.println("Error: "+e.getMessage());
        }


    }


}
