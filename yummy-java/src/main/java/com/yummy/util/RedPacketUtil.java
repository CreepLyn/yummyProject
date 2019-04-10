package com.yummy.util;

import com.yummy.modal.Redpacket;

import java.util.ArrayList;
import java.util.List;

public class RedPacketUtil {

    private static Redpacket redpacketFAST = new Redpacket();
    private static Redpacket redpacketFOOD = new Redpacket();
    private static Redpacket redpacketFRUIT = new Redpacket();
    private static Redpacket redpacketDRINK = new Redpacket();
    private static Redpacket redpacketALL = new Redpacket();

    public RedPacketUtil() {
        redpacketFAST.setType(TypeMessage.FAST);
        redpacketFAST.setPrice(35);
        redpacketFAST.setBonus(10);
        redpacketFOOD.setType(TypeMessage.FOOD);
        redpacketFOOD.setPrice(28);
        redpacketFOOD.setBonus(15);
        redpacketFRUIT.setType(TypeMessage.FRUIT);
        redpacketFRUIT.setPrice(20);
        redpacketFRUIT.setBonus(7);
        redpacketDRINK.setType(TypeMessage.DRINK);
        redpacketDRINK.setPrice(24);
        redpacketDRINK.setBonus(8);
        redpacketALL.setType(TypeMessage.ALL);
        redpacketALL.setPrice(45);
        redpacketALL.setBonus(7);
    }

    public List<Redpacket> getElement(int index) {
        List<Redpacket> result = new ArrayList<Redpacket>();
        switch (index) {
            case 1:
                result.add(redpacketFAST);
                break;
            case 2:
                result.add(redpacketFAST);
                result.add(redpacketFOOD);
                break;
            case 3:
                result.add(redpacketFAST);
                result.add(redpacketFOOD);
                result.add(redpacketDRINK);
                break;
            case 4:
                result.add(redpacketFAST);
                result.add(redpacketFOOD);
                result.add(redpacketDRINK);
                result.add(redpacketFRUIT);
                break;
            case 5:
                result.add(redpacketFAST);
                result.add(redpacketFOOD);
                result.add(redpacketDRINK);
                result.add(redpacketFRUIT);
                result.add(redpacketALL);
                break;
        }
        return result;
    }
}
