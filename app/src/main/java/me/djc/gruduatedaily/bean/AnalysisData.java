package me.djc.gruduatedaily.bean;

import me.djc.gruduatedaily.room.entity.Label;
import me.djc.gruduatedaily.room.entity.Plan;

import java.util.List;

/**
 * @author xujichang
 * @date 2019/05/15
 **/
public class AnalysisData {
    private Label mLabel;
    private List<Plan> mPlans;

    public AnalysisData(Label eLabel, List<Plan> ePlans) {
        mLabel = eLabel;
        mPlans = ePlans;
    }

    public AnalysisData() {
    }

    public Label getLabel() {
        return mLabel;
    }

    public void setLabel(Label eLabel) {
        mLabel = eLabel;
    }

    public List<Plan> getPlans() {
        return mPlans;
    }

    public void setPlans(List<Plan> ePlans) {
        mPlans = ePlans;
    }
}
