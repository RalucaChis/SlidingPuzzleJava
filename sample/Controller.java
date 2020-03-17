package sample;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import sample.repository.Repository;
import sample.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Repository repo;

    public Controller() {
    }

    public Controller(Repository repo) {
        this.repo = repo;
    }

    public Repository getRepo() {
        return repo;
    }

    public void setRepo(Repository repo) {
        this.repo = repo;
    }

    /**
     * shuffles the puzzle
     * @param n the size of the puzzle (nxn is the size of the list)
     * @return a list of shuffled tiles
     */
    public List<Tile> shuffle(Integer n) {
        List<Tile> list = repo.getList();
        Integer a[][] = new Integer[21][21];
        for (Tile t : list) {
            a[t.getX()][t.getY()] = t.getPos();
        }

        Integer x = n - 1, y = n - 1;
        int moves = 20, dir;

        while (moves > 0) {
            dir = (int) (Math.random() * 4);
            if (dir == 0 && x - 1 >= 0) { //x-1
                Integer nw = a[x - 1][y];
                a[x - 1][y] = a[x][y];
                a[x][y] = nw;
                x--;
            } else if (dir == 1 && y - 1 >= 0) { //y-1
                Integer nw = a[x][y - 1];
                a[x][y - 1] = a[x][y];
                a[x][y] = nw;
                y--;
            } else if (dir == 2 && x + 1 <= n-1) { //x+1
                Integer nw = a[x + 1][y];
                a[x + 1][y] = a[x][y];
                a[x][y] = nw;
                x++;
            } else if (dir == 3 && y + 1 <= n-1) {//y+1
                Integer nw = a[x][y + 1];
                a[x][y + 1] = a[x][y];
                a[x][y] = nw;
                y++;
            }
            moves--;
        }
        List<Tile> newList = new ArrayList<Tile>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                newList.add(new Tile(i, j, a[i][j]));

        return newList;
    }

    /**
     * checks if the buttons are in the right order
     * @param buttons a list of buttons
     * @return true - if the buttons are in the right order, false - otherwise
     */
    public boolean check(List<Button> buttons) {
        for (Integer i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getId().compareTo(i.toString()) != 0)
                return false;
        }
        return true;
    }

    /**
     * finds the imageView of a tile with a specific position
     * @param pos the position of a tile in the tileList
     * @return an imageView or null if the position given is not valid
     */
    public ImageView find(Integer pos) {
        return repo.find(pos);
    }

}
