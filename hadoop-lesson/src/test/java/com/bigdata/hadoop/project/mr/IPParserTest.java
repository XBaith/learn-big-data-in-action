package com.bigdata.hadoop.project.mr;

import com.bigdata.project.utils.IPParser;
import org.junit.Test;

public class IPParserTest {
    @Test
    public void testIP(){
        IPParser.RegionInfo regionInfo = IPParser.getInstance().analyseIp("124.79.172.232");

        System.out.println(regionInfo.getCountry() + ":" + regionInfo.getProvince() + ":" + regionInfo.getCity());
    }
}
