package me.djc.gruduatedaily.bean;

import me.djc.gruduatedaily.room.entity.Order;

import java.util.List;

/**
 * order 根据类型聚合
 *
 * @author xujichang
 * @date 2019/05/15
 **/
public class Orders {
    private int orderType;
    private List<Order> mList;

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int eOrderType) {
        orderType = eOrderType;
    }

    public List<Order> getList() {
        return mList;
    }

    public void setList(List<Order> eList) {
        mList = eList;
    }
}
