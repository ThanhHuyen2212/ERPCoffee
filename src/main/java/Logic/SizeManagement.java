package Logic;

import DAL.SizeAccess;
import Entity.Size;

import java.util.ArrayList;

public class SizeManagement {
    private ArrayList<Size> sizes;
    public SizeManagement(){
        init();
    }

    private void init(){
        sizes = SizeAccess.retrieve();
    }
    public ArrayList<Size> getSizes(){
        return sizes;
    }
}
