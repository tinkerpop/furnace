package com.tinkerpop.furnace.groovy

import com.tinkerpop.furnace.groovy.loaders.CommunityGeneratorLoader
import com.tinkerpop.furnace.groovy.loaders.DistributionGeneratorLoader

/**
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
class Furnace {
    def static load() {
        CommunityGeneratorLoader.load()
        DistributionGeneratorLoader.load()
    }
}
