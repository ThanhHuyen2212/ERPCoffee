package App.Size.Model;

import Entity.Size;
import Logic.SizeManagement;

import java.util.ArrayList;

public class SizeModel {
    private ArrayList<Size> sizes;
    private SizeManagement sizeManagement;
    public SizeModel(){
        sizeManagement = new SizeManagement();
    }

    public ArrayList<Size> getSizes() {
        if(sizes==null) sizes = sizeManagement.getSizes();
        return sizes;
    }

    public void setSizes(ArrayList<Size> sizes) {
        this.sizes = sizes;
    }

    public void insertSize(Size s){
        sizeManagement.saveSize(s);
        sizes.add(s);
    }
    public void editSize(Size oldsize, Size s){
        sizes.remove(oldsize);
        sizes.add(s);
        sizeManagement.editSize(oldsize.getSign(),s);
    }
}
