package com.tinkerpop.furnace.console

import com.tinkerpop.blueprints.Graph
import com.tinkerpop.furnace.generators.Distribution
import com.tinkerpop.furnace.generators.DistributionGenerator
import com.tinkerpop.furnace.generators.NormalDistribution
import com.tinkerpop.furnace.generators.PowerLawDistribution
import com.tinkerpop.gremlin.groovy.console.ConsoleGroovy
import com.tinkerpop.gremlin.groovy.console.ConsoleIO
import com.tinkerpop.gremlin.groovy.console.ConsolePlugin

/**
 * Furnace plugin to Gremlin Console.
 *
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
class FurnaceConsolePlugin implements ConsolePlugin {
    @Override
    public String getName() {
        return "furnace"
    }

    @Override
    public void pluginTo(final ConsoleGroovy groovy, final ConsoleIO io) {
        groovy.execute("import com.tinkerpop.furnace.algorithms.vertexcentric.*")
        groovy.execute("import com.tinkerpop.furnace.algorithms.vertexcentric.computers.*")
        groovy.execute("import com.tinkerpop.furnace.algorithms.vertexcentric.programs.*")
        groovy.execute("import com.tinkerpop.furnace.algorithms.vertexcentric.programs.clustering.*")
        groovy.execute("import com.tinkerpop.furnace.algorithms.vertexcentric.programs.neural.*")
        groovy.execute("import com.tinkerpop.furnace.algorithms.vertexcentric.programs.paths.*")
        groovy.execute("import com.tinkerpop.furnace.algorithms.vertexcentric.programs.ranking.*")
        groovy.execute("import com.tinkerpop.furnace.algorithms.vertexcentric.programs.swarm.*")
        groovy.execute("import com.tinkerpop.furnace.generators.*")
        groovy.execute("import com.tinkerpop.furnace.util.*")
        groovy.execute("import com.tinkerpop.furnace.wrappers.derived.*")

        groovy.execute("com.tinkerpop.furnace.console.FurnaceConsolePlugin.load()")
    }

    public static void load() {

        DistributionGenerator.metaClass.generateInto = { final Graph g,
            final int vertices = 10,
            final int edges = 100,
            final inDistribution = new NormalDistribution(0),
            final outDistribution = new NormalDistribution(0) ->

            doWork((DistributionGenerator) delegate, g, vertices, edges, inDistribution, outDistribution)
        }

        DistributionGenerator.metaClass.generateInto = { final Map opts = [:], final Graph g ->

            doWork((DistributionGenerator) delegate, g,
                opts.vertices == null ? 10 : opts.vertices,
                opts.edges == null ? 100 : opts.edges,
                opts.inDistribution == null ? new NormalDistribution(0) : opts.inDistribution,
                opts.outDistribution == null ? new NormalDistribution(0) : opts.outDistribution)
        }
    }

    private static def doWork(final DistributionGenerator generator,
                              final Graph g,
                              final int vertices = 10,
                              final int edges = 100,
                              final inDistribution = new NormalDistribution(0),
                              final outDistribution = new NormalDistribution(0)) {

        if (inDistribution != null) {
            if (inDistribution instanceof Distribution) {
                generator.setInDistribution((Distribution) inDistribution)
            } else if (inDistribution instanceof Map) {
                def m = (Map) inDistribution
                if (m.containsKey("powerLaw")) {
                    generator.setInDistribution(new PowerLawDistribution(m.powerLaw))
                } else if (m.containsKey("normal")) {
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
                if (m.containsKey("powerLaw")) {
                    generator.setOutDistribution(new PowerLawDistribution(m.powerLaw))
                } else if (m.containsKey("normal")) {
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
