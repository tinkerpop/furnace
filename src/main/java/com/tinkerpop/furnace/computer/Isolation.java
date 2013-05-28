package com.tinkerpop.furnace.computer;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public enum Isolation {
    /**
     * Computations are carried out in Bulk synchronous: The computation is completed on all
     * vertices and all resulting changes are buffered until such completion before the changes
     * become visible. Only after all computations are completed is a round complete.
     */
    BSP,
    /**
     * As with BSP but mutations may become visible before the end of the round.
     */
    DIRTY_BSP,
    /**
     * Asynchronous parallel computing
     */
    ASP
}