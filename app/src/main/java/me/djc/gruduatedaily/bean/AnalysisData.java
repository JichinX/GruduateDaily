package me.djc.gruduatedaily.bean;

/**
 * @author xujichang
 * @date 2019/05/15
 **/
public class AnalysisData {
    private int mType;
    private String mTitle;

    public AnalysisData(String title, int type) {
        mTitle = title;
        mType = type;
    }

    public int getType() {

        return mType;
    }

    public void setType(int eType) {
        mType = eType;
    }

    public String getTitle() {

        return mTitle;
    }

    public void setTitle(String eTitle) {
        mTitle = eTitle;
    }
}
