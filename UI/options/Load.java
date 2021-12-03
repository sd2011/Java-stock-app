package UI.options;

import UI.Options;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import System.SystemProxy;

public class Load {

    public void execute(Options options) {
        SystemProxy prox = options.getProx();
        Scanner scanner = options.getScanner();

        try {

            System.out.println("Please write the name of the file you wish to load\n");
            String fileName = scanner.nextLine();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));

            prox.setRizpa(in.readObject());
            System.out.println("File as been successfully loaded!");


        } catch (FileNotFoundException e) {
            System.out.println("Error! File not found!\n");
        } catch (IOException e) {
            System.out.println("Error! Unable to load file\n");
        } catch (
                InputMismatchException e) {
            System.out.println("Error! Input does not match specified argument\nPlease make sure to enter input according to the system requests \n");
        } catch (Throwable e) {
            System.out.println("Error! " + e.getMessage() + "\n");
        }
    }
}
