package Logic;

import DAL.SizeAccess;
import Entity.Size;

import java.util.ArrayList;

public class SizeManagement {
    private ArrayList<Size> sizes;
    SizeAccess sizeAccess;
    public SizeManagement(){
        sizeAccess = new SizeAccess();
        sizes = sizeAccess.retrieve();
    }

    public ArrayList<Size> getSizes(){
        return sizes;
    }
}
