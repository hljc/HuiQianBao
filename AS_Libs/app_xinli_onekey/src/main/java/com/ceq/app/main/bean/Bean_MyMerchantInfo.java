package com.ceq.app.main.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class Bean_MyMerchantInfo {
    int falsenum,truenum;
    List<Bean_ProductionPeopleInfo> thirdLevelDistribution;

    public int getFalsenum() {
        return falsenum;
    }

    public void setFalsenum(int falsenum) {
        this.falsenum = falsenum;
    }

    public int getTruenum() {
        return truenum;
    }

    public void setTruenum(int truenum) {
        this.truenum = truenum;
    }

    public List<Bean_ProductionPeopleInfo> getThirdLevelDistribution() {
        return thirdLevelDistribution;
    }

    public void setThirdLevelDistribution(List<Bean_ProductionPeopleInfo> thirdLevelDistribution) {
        this.thirdLevelDistribution = thirdLevelDistribution;
    }
}
