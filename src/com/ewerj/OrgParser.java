package com.ewerj;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

/*
Parser for reading user/org files and building representative heirarchy
 */
public class OrgParser {
    public static void main(String[] args) throws IOException {
        String orgFileName = args[0];
        String userFileName = args[1];

        List<User> orphanedUsers = new ArrayList<>();

        //parse out a map of all the orgs
        Map<Integer, Org> unassociatedOrgMap = null;
        try (Stream<String> orgStream = Files.lines(Paths.get(orgFileName))) {
            unassociatedOrgMap = orgStream.map(line -> parseOrg(line))
                    .collect(Collectors.toMap(Org::getId, Function.identity()));
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

        //parse a list of users
        try (Stream<String> userStream = Files.lines(Paths.get(userFileName))) {
            Java8StillHasNoTuple[] users = userStream.map(line -> {
                Integer[] bits = Arrays.stream(line.split(",", -1)).map(x -> parseInt(x)).toArray(Integer[]::new);
                return new Java8StillHasNoTuple(bits[1], new User(bits[0], bits[2], bits[3]));
            }).toArray(Java8StillHasNoTuple[]::new);

            //associate users with orgs
            for (Java8StillHasNoTuple u: users) {
                if (unassociatedOrgMap.containsKey(u.orgId)) {
                    unassociatedOrgMap.get(u.orgId).addUser(u.user);
                } else {
                    orphanedUsers.add(u.user);
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

        //associate relationships between orgs
        List <Org> topLevelOrgs = new ArrayList < > ();
        for (Org org: unassociatedOrgMap.values()) {
            Optional < Integer > parentOrgId = org.getParentOrgId();
            if (parentOrgId.isPresent()) {
                org.setParentOrg(unassociatedOrgMap.get(parentOrgId.get()));
                unassociatedOrgMap.get(parentOrgId.get()).addChildOrg(org);
            } else {
                topLevelOrgs.add(org);
            }
        }

        //print out all orgs
        topLevelOrgs.stream().forEach(System.out::println);

        /*
        just for funsies
        if (orphanedUsers.size() > 0) {
            System.out.println("------");
            System.out.println("The following users were not associated with a valid Org.");
            System.out.println("------");

            orphanedUsers.stream().forEach(System.out::println);
        }*/
    }

    public static Org parseOrg(String line) {
        String[] bits = line.split(",", -1);
        return new Org(parseInt(bits[0]), (bits[1] != null && !bits[1].isEmpty()) ? parseInt(bits[1]) : -1, bits[2]);
    }


    private static class Java8StillHasNoTuple {
        private Integer orgId;
        private User user;

        private Java8StillHasNoTuple(Integer orgId, User user) {
            this.orgId = orgId;
            this.user = user;
        }
    }
}