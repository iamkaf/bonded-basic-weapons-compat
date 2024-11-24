package com.iamkaf.bondedbw;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class BondedBW {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        LOGGER.info("Bonded - Basic Weapons Compat Initialized");
    }
}
