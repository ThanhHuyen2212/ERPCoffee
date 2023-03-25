package Logic;

import DAL.CategoryAccess;
import DAL.MemberAccess;
import Entity.Category;
import Entity.Member;

import java.util.ArrayList;

public class MemberManagement {
    MemberAccess memberAccess = new MemberAccess();
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
    public void createMember(Member member){
        memberAccess.createMember(member);
    }
}
