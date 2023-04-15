package Logic;

import DAL.CategoryAccess;
import DAL.MemberAccess;
import Entity.Category;
import Entity.Member;

import java.util.ArrayList;

public class MemberManagement {
    MemberAccess memberAccess;
    private ArrayList<Member> members;

    public MemberManagement() {
        memberAccess = new MemberAccess();
        members = memberAccess.retrieve();
    }
    public ArrayList<Member> getMembers() {
        return members;
    }

    public void createMember(Member member) {

        memberAccess.createMember(member);
    }

    public Member findByPhone(String phone) {
        Member member = new Member();
        for (Member mb : members) {
            if (mb.getPhoneNumber().equalsIgnoreCase(phone.trim())) {
                member = mb;
            }
        }
        return member;
    }
    public Member findByName(String name) {
        Member member = new Member();
        for (Member mb : members) {
            if (mb.getFullName().equalsIgnoreCase(name.trim())) {
                member = mb;
            }
        }
        return member;
    }public ArrayList<String> PointOfMember(Member member){
        ArrayList<String> arrayList = new ArrayList<>();
        int countPoint = member.getPoint()/10;
        if(countPoint>0){
            for(int i=1;i<=countPoint;i++){
                arrayList.add(String.valueOf(i*10));
            }
        }
        return arrayList;
    }
    public void updateSubPoint(Member member, Integer point){
        memberAccess.UpdateSubPoint(member,point);
    }
    public void updateMember( Member member){
        memberAccess.updateMember(member);
    }
}
