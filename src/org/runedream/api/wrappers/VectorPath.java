package org.runedream.api.wrappers;

/**
 * A set of PolarTiles to be traversed.
 * 
 * @author Static
 * @see VectorTile
 */
public class VectorPath {

    private final VectorTile[] tiles;

    /**
     * Constructs a path of PolarTiles.
     * @param tiles The tiles to construct the path from.
     * @see VectorTile
     */
    public VectorPath(final VectorTile... tiles) {
        this.tiles = tiles;
    }

    /**
     * Gets the first PolarTile in the path.
     * @return The first PolarTile in the path.
     */
    public VectorTile getStart() {
        return tiles[0];
    }

    /**
     * Gets the last PolarTile in the path.
     * @return The last PolarTile in the path.
     */
    public VectorTile getEnd() {
        return tiles[tiles.length - 1];
    }
    
    /**
     * Reverses the PolarPath.
     */
    public void reverse() {
        final VectorTile[] reversed = new VectorTile[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            reversed[i] = tiles[tiles.length - i];    
        }
        for (int i = 0; i < tiles.length; i++) {
        	tiles[i] = reversed[i];
        }
    }
    
    /**
     * Traverses the path, clicking each PolarTile until every one has been clicked, or one fails.
     * @return <tt>true</tt> if traversed; otherwise <tt>false</tt>.
     */
    public boolean traverse() {
        for (final VectorTile tile : tiles) {
            if (!tile.clickOnMap()) {
                return false;
            }
        }
        return true;
    }
    
}