package teste;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sample.Controller;
import sample.repository.Repository;
import sample.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class ControllerTest {
    Controller contr;
    Repository repo;
    List<Tile> tileList = new ArrayList<Tile>();
    Tile t1, t2, t3;

    @Before
    public void setUp() {
        t1 = new Tile(1, 2, 0);
        t2 = new Tile(0, 1, 1);
        t3 = new Tile(0, 0, 2);
        tileList.add(t1);
        tileList.add(t2);
        tileList.add(t2);
        tileList.add(t2);
        tileList.add(t2);
        tileList.add(t2);
        tileList.add(t2);
        tileList.add(t2);
        tileList.add(t2);
        repo = new Repository(tileList);
        contr = new Controller(repo);
    }

    @Test
    public void findTest() {
        ImageView iw = contr.find(2);
        Assert.assertEquals(iw,null);
    }

    @Test
    public void shuffleTest() {
        List<Tile> l = contr.shuffle(3);
        Assert.assertEquals(l.size(), tileList.size());
    }

}
