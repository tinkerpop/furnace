package com.tinkerpop.furnace.util;

import com.tinkerpop.blueprints.Compare;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class VertexQueryBuilderTest extends TestCase {

    public void testVertexQueryBuilder() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        VertexQueryBuilder builder = new VertexQueryBuilder().labels("knows");
        assertEquals(builder.build(graph.getVertex(1)).count(), 2);
        builder = new VertexQueryBuilder().labels("knows").has("weight", Compare.GREATER_THAN, 0.5f);
        assertEquals(graph.getVertex(1).query().has("weight", Compare.GREATER_THAN, 0.5f).labels("knows").count(), 1);
        assertEquals(builder.build(graph.getVertex(1)).count(), 1);
    }
}
