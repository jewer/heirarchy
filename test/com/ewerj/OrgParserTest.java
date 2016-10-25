package com.ewerj;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrgParserTest {
    @Test
    public void OrgParser_should_read_line_and_parse_org() {
        String goodInput = "1,5,6,7";
        Org actualOrg = OrgParser.parseOrg(goodInput);

        assertEquals(1, actualOrg.getId());
        assertEquals(0, actualOrg.getTotalNumFiles());
        assertEquals(0, actualOrg.getTotalNumBytes());
        assertEquals(0, actualOrg.getTotalNumUsers());
    }

}