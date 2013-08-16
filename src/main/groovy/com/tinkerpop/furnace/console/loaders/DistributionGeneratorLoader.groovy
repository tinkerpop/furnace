package com.tinkerpop.furnace.console.loaders

import com.tinkerpop.blueprints.Graph
import com.tinkerpop.furnace.generators.Distribution
import com.tinkerpop.furnace.generators.DistributionGenerator
import com.tinkerpop.furnace.generators.NormalDistribution
import com.tinkerpop.furnace.generators.PowerLawDistribution

/**
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
class DistributionGeneratorLoader extends BaseGeneratorLoader {

    def static load() {
        DistributionGenerator.metaClass.generateInto = { final Graph g,
                                                         final int vertices = DEFAULT_NUMBER_OF_VERTICES,
                                                         final int edges = DEFAULT_NUMBER_OF_EDGES,
                                                         final inDistribution = DEFAULT_DISTRIBUTION,
                                                         final outDistribution = DEFAULT_DISTRIBUTION ->
            generateInto((DistributionGenerator) delegate, g, vertices, edges, inDistribution, outDistribution)
        }

        DistributionGenerator.metaClass.generateInto = { final Map opts = [:], final Graph g ->
            generateInto((DistributionGenerator) delegate, g,
                    opts.vertices == null ? DEFAULT_NUMBER_OF_VERTICES : opts.vertices,
                    opts.edges == null ? DEFAULT_NUMBER_OF_EDGES : opts.edges,
                    opts.inDistribution == null ? DEFAULT_DISTRIBUTION : opts.inDistribution,
                    opts.outDistribution == null ? DEFAULT_DISTRIBUTION : opts.outDistribution)
        }
    }

    private static def generateInto(final DistributionGenerator generator,
                              final Graph g,
                              final int vertices = DEFAULT_NUMBER_OF_VERTICES,
                              final int edges = DEFAULT_NUMBER_OF_EDGES,
                              final inDistribution = DEFAULT_DISTRIBUTION,
                              final outDistribution = DEFAULT_DISTRIBUTION) {

        if (inDistribution != null) {
            if (inDistribution instanceof Distribution) {
                generator.setInDistribution((Distribution) inDistribution)
            } else if (inDistribution instanceof Map) {
                def m = (Map) inDistribution
                if (m.containsKey(SETTING_POWER_LAW)) {
                    generator.setInDistribution(new PowerLawDistribution(m.powerLaw))
                } else if (m.containsKey(SETTING_NORMAL)) {
                    generator.setInDistribution(new NormalDistribution(m.normal))
                }
            } else {
                generator.setInDistribution(new NormalDistribution(inDistribution))
            }
        }

        if (outDistribution != null) {
            if (outDistribution instanceof Distribution) {
                generator.setOutDistribution((Distribution) outDistribution)
            } else if (outDistribution instanceof Map) {
                def m = (Map) outDistribution
                if (m.containsKey(SETTING_POWER_LAW)) {
                    generator.setOutDistribution(new PowerLawDistribution(m.powerLaw))
                } else if (m.containsKey(SETTING_NORMAL)) {
                    generator.setOutDistribution(new NormalDistribution(m.normal))
                }
            } else {
                generator.setOutDistribution(new NormalDistribution(outDistribution))
            }
        }

        (0..<vertices).each{g.addVertex(it)}
        generator.generate(g, edges)
    }
}
