package com.tinkerpop.furnace.console.loaders

import com.tinkerpop.furnace.generators.Distribution
import com.tinkerpop.furnace.generators.NormalDistribution

/**
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
class BaseGeneratorLoader {
    protected static final String SETTING_POWER_LAW = "powerLaw"
    protected static final String SETTING_NORMAL = "normal"

    protected static final int DEFAULT_NUMBER_OF_VERTICES = 100
    protected static final int DEFAULT_NUMBER_OF_EDGES = 1000

    protected static final Distribution DEFAULT_DISTRIBUTION = new NormalDistribution(0)
}
