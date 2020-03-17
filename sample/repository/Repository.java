package sample.repository;

import javafx.scene.image.ImageView;
import sample.model.Tile;

import java.util.List;

public class Repository {
    private List<Tile> Tiles;

    public Repository(List<Tile> list) {
        this.Tiles = list;
    }

    public List<Tile> getList() {
        return Tiles;
    }

    public void setList(List<Tile> list) {
        this.Tiles = list;
    }

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    public Tile save(Tile entity) throws sample.AlreadyExistsException {
        for (Tile p : Tiles)
            if (p == entity){
                throw new sample.AlreadyExistsException("Course already exists");
            }

        Tiles.add(entity);
        return null;
    }

    /**
     * @param pos the position of a tile in the list
     * @return null- if the tile with that position is not found, otherwise the imageView of the tile
     */
    public ImageView find(Integer pos){
        for(Tile t : Tiles)
            if(t.getPos().compareTo(pos) == 0)
                return t.getIw();
        return null;
    }

}
