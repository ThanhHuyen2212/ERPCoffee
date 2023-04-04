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
    public void saveSize(Size s){
        sizeAccess.saveSize(s);
    }
    public void editSize(String oldSize, Size s){
        sizeAccess.editSize(oldSize,s);
    }
}
