package UI.options;

import UI.Options;
import System.SystemProxy;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Save {

    public void execute(Options options) {
        SystemProxy prox = options.getProx();
        Scanner scanner = options.getScanner();

        try{
            System.out.println("Please write a name for the saved file\n");
           String fileName = scanner.nextLine();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));

            out.writeObject(prox.getRizpa());
            out.flush();
            System.out.println("File as been successfully saved!");

        } catch (FileNotFoundException e) {
            System.out.println("Error! File not found!\n");
        } catch (IOException e) {
            System.out.println("Error! Unable to save file\n");
        } catch(
                InputMismatchException e){
            System.out.println("Error! Input does not match specified argument\nPlease make sure to enter input according to the system requests \n");
        } catch (Throwable e){
            System.out.println("Error! "+e.getMessage()+"\n");
        }


    }
}
