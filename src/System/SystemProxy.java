package System;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SystemProxy {
    private RizpaSystem sys;

    public SystemProxy() {
        sys = new RizpaSystem();
    }


    public <T> T invoke(String methodName, Object... args) throws Throwable {
        Class[] allParams = new Class[args.length];


        for (int i = 0; i < args.length; i++) {
            String type = args[i].getClass().getTypeName();

            switch (type) {
                case "java.lang.Integer":
                    allParams[i] = int.class;
                    break;
                case "java.lang.Short":
                    allParams[i] = short.class;
                    break;
                case "java.lang.Long":
                    allParams[i] = long.class;
                    break;
                case "java.lang.Byte":
                    allParams[i] = byte.class;
                    break;
                case "java.lang.Char":
                    allParams[i] = char.class;
                    break;
                case "java.lang.Float":
                    allParams[i] = float.class;
                    break;
                case "java.lang.Double":
                    allParams[i] = double.class;
                    break;
                case "java.lang.Boolean":
                    allParams[i] = boolean.class;
                    break;
                case "java.lang.String":
                    allParams[i] = String.class;
                    break;
                default:
                    allParams[i] = int.class;
                    break;
            }
        }
        Method func = RizpaSystem.class.getMethod(methodName, allParams);
        if (!sys.isLoaded() && !methodName.equals("load"))
            throw new FileNotFoundException("No file was loaded to the system.\n");
        try {
            return (T) func.invoke(sys, args);
        }
        catch (InvocationTargetException e) {
             throw new FileNotFoundException( e.getTargetException().getMessage() == null ? "An unknown error occurred..." : e.getTargetException().getMessage());
        }
}

public RizpaSystem getRizpa(){
        return this.sys;
}

public void setRizpa(Object system){
        this.sys = (RizpaSystem) system;
}

}
