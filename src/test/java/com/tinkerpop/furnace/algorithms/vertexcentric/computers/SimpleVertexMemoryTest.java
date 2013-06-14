package com.tinkerpop.furnace.algorithms.vertexcentric.computers;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.furnace.algorithms.vertexcentric.GraphComputer;
import com.tinkerpop.furnace.algorithms.vertexcentric.VertexProgram;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class SimpleVertexMemoryTest extends TestCase {

    public void testConstantVariables() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        Map<String, VertexProgram.KeyType> keys = new HashMap<String, VertexProgram.KeyType>();
        keys.put("name", VertexProgram.KeyType.CONSTANT);
        keys.put("age", VertexProgram.KeyType.VARIABLE);
        SimpleVertexMemory memory = new SimpleVertexMemory(GraphComputer.Isolation.BSP);
        memory.setComputeKeys(keys);
        memory.setProperty(graph.getVertex(1), "name", "marko");
        memory.setProperty(graph.getVertex(1), "age", 29);
        memory.setProperty(graph.getVertex(1), "age", 33);
        try {
            memory.setProperty(graph.getVertex(1), "name", "marko");
            assertFalse(true);
        } catch (Exception e) {
            assertTrue(true);
        }
        assertEquals(memory.getProperty(graph.getVertex(1), "name"), "marko");
    }

}
