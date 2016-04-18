package com.lyonsys.cstest.mandrill;

import java.util.List;

/**
 * Created by yong on 16/04/2016.
 */
public interface LiveOrderBoard {
    List<Order> ordersFor(String currency);
}
