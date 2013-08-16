package com.tinkerpop.furnace.console.loaders

import com.tinkerpop.blueprints.Graph
import com.tinkerpop.furnace.generators.CommunityGenerator
import com.tinkerpop.furnace.generators.Distribution
import com.tinkerpop.furnace.generators.NormalDistribution
import com.tinkerpop.furnace.generators.PowerLawDistribution

/**
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
class CommunityGeneratorLoader extends BaseGeneratorLoader {

    private static final double DEFAULT_CROSS_COMMUNITY_PERCENT = 0.1d
    private static final int DEFAULT_NUMBER_OF_COMMUNITIES = 10

    def static load() {
        CommunityGenerator.metaClass.generateInto = { final Graph g,
                                                      final int vertices = DEFAULT_NUMBER_OF_VERTICES,
                                                      final int edges = DEFAULT_NUMBER_OF_EDGES,
                                                      final int communities = DEFAULT_NUMBER_OF_COMMUNITIES,
                                                      final communityDistribution = DEFAULT_DISTRIBUTION,
                                                      final degreeDistribution = DEFAULT_DISTRIBUTION,
                                                      final double crossCommunityPercent = DEFAULT_CROSS_COMMUNITY_PERCENT ->
            generateInto((CommunityGenerator) delegate, g, vertices, edges, communities, communityDistribution,
                    degreeDistribution, crossCommunityPercent)
        }

        CommunityGenerator.metaClass.generateInto = { final Map opts = [:], final Graph g ->
            generateInto((CommunityGenerator) delegate, g,
                    opts.vertices == null ? DEFAULT_NUMBER_OF_VERTICES : opts.vertices,
                    opts.edges == null ? DEFAULT_NUMBER_OF_EDGES : opts.edges,
                    opts.communities == null ? DEFAULT_NUMBER_OF_COMMUNITIES : opts.communities,
                    opts.communityDistribution == null ? DEFAULT_DISTRIBUTION : opts.communityDistribution,
                    opts.degreeDistribution == null ? DEFAULT_DISTRIBUTION : opts.degreeDistribution,
                    opts.crossCommunityPercent == null ? DEFAULT_CROSS_COMMUNITY_PERCENT : opts.crossCommunityPercent)
        }
    }

    private static def generateInto(final CommunityGenerator generator,
                                    final Graph g,
                                    final int vertices = DEFAULT_NUMBER_OF_VERTICES,
                                    final int edges = DEFAULT_NUMBER_OF_EDGES,
                                    final int communities = DEFAULT_NUMBER_OF_COMMUNITIES,
                                    final communityDistribution = DEFAULT_DISTRIBUTION,
                                    final degreeDistribution = DEFAULT_DISTRIBUTION,
                                    final double crossCommunityPercent = DEFAULT_CROSS_COMMUNITY_PERCENT) {

        if (communityDistribution != null) {
            if (communityDistribution instanceof Distribution) {
                generator.setCommunityDistribution((Distribution) communityDistribution)
            } else if (communityDistribution instanceof Map) {
                def m = (Map) communityDistribution
                if (m.containsKey(SETTING_POWER_LAW)) {
                    generator.setCommunityDistribution(new PowerLawDistribution(m.powerLaw))
                } else if (m.containsKey(SETTING_NORMAL)) {
                    generator.setCommunityDistribution(new NormalDistribution(m.normal))
                }
            } else {
                generator.setCommunityDistribution(new NormalDistribution(communityDistribution))
            }
        }

        if (degreeDistribution != null) {
            if (degreeDistribution instanceof Distribution) {
                generator.setDegreeDistribution((Distribution) degreeDistribution)
            } else if (degreeDistribution instanceof Map) {
                def m = (Map) degreeDistribution
                if (m.containsKey(SETTING_POWER_LAW)) {
                    generator.setDegreeDistribution(new PowerLawDistribution(m.powerLaw))
                } else if (m.containsKey(SETTING_NORMAL)) {
                    generator.setDegreeDistribution(new NormalDistribution(m.normal))
                }
            } else {
                generator.setDegreeDistribution(new NormalDistribution(degreeDistribution))
            }
        }

        generator.setCrossCommunityPercentage(crossCommunityPercent)

        (0..<vertices).each { g.addVertex(it) }
        generator.generate(g, communities, edges)
    }
}
