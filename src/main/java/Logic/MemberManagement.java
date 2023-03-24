package Logic;

import DAL.CategoryAccess;
import DAL.MemberAccess;
import Entity.Category;
import Entity.Member;

import java.util.ArrayList;

public class MemberManagement {
    private ArrayList<Member> members;
    public MemberManagement(){
        init();
    }

    private void init(){
        members = MemberAccess.retrieve();
    }
    public ArrayList<Member> getMembers(){
        return members;
    }
}
