package io.github.wdpm.dao;

import io.github.wdpm.model.Flavor;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;

/**
 * @author evan
 * @date 2020/5/20
 */
@Repository
public class FlavorDAOImpl implements FlavorDAO {
    @Override
    public EnumSet<?> getFlavors() {
        return EnumSet.allOf(Flavor.class);
    }
}
