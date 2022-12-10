<<<<<<< HEAD
import Fonts.*;

import java.util.Objects;

public class FontFactory{
    /**
     * Create different fonts
     * @param product
     * @return font of type product
     */
    public Fonts createFont(String product){
        if(Objects.equals(product, "Arial")) return new Arial();
        if(Objects.equals(product, "Courier")) return new Courier();
        if(Objects.equals(product, "Helvetica")) return new Helvetica();
        if(Objects.equals(product, "Verdana")) return new Verdana();
        return null;
    }
}
=======
import Fonts.*;

import java.util.Objects;

public class FontFactory{

    public Fonts createFont(String product){
        if(Objects.equals(product, "Arial")) return new Arial();
        if(Objects.equals(product, "Courier")) return new Courier();
        if(Objects.equals(product, "Helvetica")) return new Helvetica();
        if(Objects.equals(product, "Verdana")) return new Verdana();
        return null;
    }
}
>>>>>>> e982ada4930cb6220b48fd290636ebe4f74a4d25
