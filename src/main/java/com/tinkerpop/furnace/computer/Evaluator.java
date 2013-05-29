package com.tinkerpop.furnace.computer;

/**
 * The Evaluator is responsible for the execution of the GraphComputer against a particular graph instance.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public interface Evaluator {

    public void execute(GraphComputer computer);
}
