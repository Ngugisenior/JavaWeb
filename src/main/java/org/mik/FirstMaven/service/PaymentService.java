package org.mik.FirstMaven.service;


import org.apache.log4j.Logger;
import org.mik.FirstMaven.entity.AbstractEntity;

public class PaymentService {
    private static final Logger LOG = Logger.getLogger(PaymentService.class.getName());

    public void pay(AbstractEntity from, AbstractEntity to, Long amount){
        if(from.getAmount()>amount){
            LOG.info(String.format("$s receives money (%d) from %s", to,amount,from));
            to.setAmount(to.getAmount()+amount);
            from.setAmount(from.getAmount()-amount);
        }
        LOG.info(String.format("%s doesn't have enough money for %d", from, amount));
    }
}
