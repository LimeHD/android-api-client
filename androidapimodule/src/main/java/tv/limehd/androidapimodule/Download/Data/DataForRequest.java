package tv.limehd.androidapimodule.Download.Data;

import java.util.HashMap;

public class DataForRequest {
    private HashMap<Class<? extends Component>, Component> components;

    public DataForRequest() {
        components = new HashMap<>();
    }

    public <T extends Component> T getComponent(Class<? extends Component> component) {
        if(components != null) {
            return (T) components.get(component);
        }
        return null;
    }
    public <T extends Component> void addComponent(T component) {
        if(component!= null){
            components.put(component.getClass(), component);
        }
    }

}





