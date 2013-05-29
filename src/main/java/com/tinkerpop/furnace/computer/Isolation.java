package com.tinkerpop.furnace.computer;

/**
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public enum Isolation {
    /**
     * Computations are carried out in a bulk synchronous manner.
     * The computation is completed on all vertices.
     * All resulting changes are buffered until the changes become visible.
     * Only after all computations are completed is a round complete.
     */
    BSP,
    /**
     * As with BSP but mutations may become visible before the end of the round.
     */
    DIRTY_BSP
}