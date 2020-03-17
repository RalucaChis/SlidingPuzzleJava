package sample.model;

import javafx.scene.image.ImageView;

public class Tile {
    private int x, y;
    Integer pos;
    ImageView iw;

    public Tile(int x, int y, Integer pos) {
        this.x = x;
        this.y = y;
        this.pos = pos;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public ImageView getIw() {
        return iw;
    }

    public void setIw(ImageView iw) {
        this.iw = iw;
    }
}
