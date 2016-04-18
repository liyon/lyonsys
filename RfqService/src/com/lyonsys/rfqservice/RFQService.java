package com.lyonsys.rfqservice;

import java.util.Optional;

/**
 * Created by yong on 16/04/2016.
 */
public interface RFQService {
    Optional<Quote> quoteFor(String currency, int amount);
}
