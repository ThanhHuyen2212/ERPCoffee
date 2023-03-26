package Logic;

import DAL.SizeAccess;
import Entity.Size;

import java.util.ArrayList;

public class SizeManagement {
    private ArrayList<Size> sizes;
    SizeAccess sizeAccess = new SizeAccess();
    public SizeManagement(){
        init();
    }

    private void init(){
        sizes = sizeAccess.retrieve();
    }
    public ArrayList<Size> getSizes(){
        return sizes;
    }
}
