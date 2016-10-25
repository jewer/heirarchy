package com.ewerj;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrgTest {
    @Test
    public void getTotalUsers_should_count_users(){
        Org o = new Org(1, -1, "name");
        o.addUser(new User(1, 1, 1));

        assertEquals(1, o.getTotalNumUsers());
    }

    @Test
    public void getTotalUsers_should_count_users_including_child_orgs(){
        Org o = new Org(1, -1, "name");
        o.addUser(new User(1, 1, 1));

        Org child = new Org(2, 1, "child");
        o.addUser(new User(2, 1, 1));

        o.addChildOrg(child);

        assertEquals(2, o.getTotalNumUsers());
    }


    @Test
    public void should_include_child_orgs_in_calculations(){
        Org o = new Org(1, -1, "name");
        o.addUser(new User(1, 100, 200));

        Org child = new Org(2, 1, "child");
        o.addUser(new User(2, 200, 300));

        o.addChildOrg(child);

        assertEquals(300, o.getTotalNumFiles());
        assertEquals(500, o.getTotalNumBytes());
    }

}
