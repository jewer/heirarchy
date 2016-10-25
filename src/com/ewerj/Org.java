package com.ewerj;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
Entity for holding users and organizational heirarchy
 */
public class Org {
    private int orgId;
    private int parentOrgId;
    private String name;
    private Org parent;
    private List<User> users;
    private List<Org> childOrgs;

    public Org(int orgId, int parentOrgId, String name) {
        this.orgId = orgId;
        this.name = name;
        this.parentOrgId = parentOrgId;
        this.childOrgs = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void setParentOrg(Org o){
        this.parent = o;
    }

    public void addUser(User u){
        this.users.add(u);
    }

    public void addChildOrg(Org o){
        this.childOrgs.add(o);
    }

    public List<Org> getChildOrgs(){
        return this.childOrgs;
    }

    public int getId(){
        return this.orgId;
    }

    public Optional<Integer> getParentOrgId(){
        if (this.parentOrgId >= 0) {
            return Optional.of(this.parentOrgId);
        } else {
            return Optional.empty();
        }
    }

    public int getTotalNumUsers(){
        Integer users = this.users.size();
        Integer childUsers = this.getChildOrgs().stream().map(o -> o.getTotalNumUsers()).reduce((x, y) -> x + y).orElse(0);
        return users + childUsers;
        //return users;
    }
    public int getTotalNumFiles(){
        Integer files = this.users.stream().map(u -> u.getNumFiles()).reduce((x, y) -> x + y).orElse(0);
        Integer childFiles = this.getChildOrgs().stream().map(o -> o.getTotalNumFiles()).reduce((x, y) -> x + y).orElse(0);
        return files + childFiles;
    }
    public int getTotalNumBytes(){
        Integer bytes = this.users.stream().map(u -> u.getNumBytes()).reduce((x, y) -> x + y).orElse(0);
        Integer childBytes = this.getChildOrgs().stream().map(o -> o.getTotalNumBytes()).reduce((x, y) -> x + y).orElse(0);
        return bytes + childBytes;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        List<String> orgStrings = new ArrayList<>();
        orgStrings.add(String.format("%s %s %s %s %s",
                this.getParentOrgId().isPresent() ? "  " : "",
                this.orgId,
                this.getTotalNumUsers(),
                this.getTotalNumFiles(),
                this.getTotalNumBytes()
        ));

        orgStrings.addAll(this.getChildOrgs().stream().map(Org::toString).collect(Collectors.toList()));
        return orgStrings.stream().collect(Collectors.joining("\n"));
    }
}
