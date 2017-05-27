package com.tfr.microbrew.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tfr.microbrew.model.FixedCost;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 *
 * Created by Erik Hage on 5/26/2017.
 */
@Configuration
public class CostsConfig {

    @Bean(name = "FixedCosts")
    public List<FixedCost> fixedCosts() {
        return Lists.newArrayList(
                new FixedCost("Rent", 5000.0, Sets.newHashSet(1)),
                new FixedCost("Brewery License", 105.0, Sets.newHashSet(1)),
                new FixedCost("Insurance", 333.00, Sets.newHashSet(1)),
                new FixedCost("Internet/Phone/TV", 100.0, Sets.newHashSet(15)),
                new FixedCost("Gas/Electric", 1200.0, Sets.newHashSet(15)),
                new FixedCost("Logistics", 200.0, Sets.newHashSet(15)),
                new FixedCost("Water/Sewer", 200.0, Sets.newHashSet(15)),
                new FixedCost("Accountant", 250.0, Sets.newHashSet(1)),
                new FixedCost("Loan/Credit Payment", 1200.0, Sets.newHashSet(22))
        );
    }

}
