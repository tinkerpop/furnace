package com.tinkerpop.furnace.groovy

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
        groovy.execute("import com.tinkerpop.furnace.groovy.Furnace")

        groovy.execute("com.tinkerpop.furnace.groovy.Furnace.load()")
    }
}
