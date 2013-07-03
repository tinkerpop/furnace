package com.tinkerpop.furnace.wrappers;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.furnace.util.VertexQueryBuilder;
import com.tinkerpop.pipes.AbstractPipe;
import com.tinkerpop.pipes.util.PipeHelper;
import com.tinkerpop.pipes.util.Pipeline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class DerivedAdjacency implements Iterable<Vertex> {

    private final List<VertexQueryBuilder> queries = new ArrayList<VertexQueryBuilder>();

    public DerivedAdjacency add(final VertexQueryBuilder query) {
        this.queries.add(query);
        return this;
    }

    public VertexQueryBuilder nextQuery(final int iteration) {
        return this.queries.get(this.queries.size() % iteration);
    }

    public int size() {
        return this.queries.size();
    }

    public Iterator<Vertex> iterator() {
        final Pipeline<Vertex, Vertex> pipeline = new Pipeline<Vertex, Vertex>();
        for (VertexQueryBuilder query : this.queries) {
            pipeline.addPipe(new VertexQueryBuilderPipe(query));
        }
        return pipeline;
    }

    class VertexQueryBuilderPipe extends AbstractPipe<Vertex, Vertex> {

        private final VertexQueryBuilder builder;
        private Iterator<Vertex> tempIterator = PipeHelper.emptyIterator();

        public VertexQueryBuilderPipe(VertexQueryBuilder builder) {
            this.builder = builder;
        }

        public Vertex processNextStart() {
            while (true) {
                if (this.tempIterator.hasNext())
                    return this.tempIterator.next();
                else {
                    final Vertex vertex = this.starts.next();
                    this.tempIterator = this.builder.build(vertex).vertices().iterator();
                }
            }
        }
    }
}
