package teste;

import javafx.scene.image.ImageView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sample.repository.Repository;
import sample.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class RepoTest {
    List<Tile> tileList = new ArrayList<Tile>();
    Tile t1, t2, t3;
    Repository repo;

    @Before
    public void setUp() {
        t1 = new Tile(1,2,3);
        t2 = new Tile(0,1,2);
        t3 = new Tile(0,0,1);
        tileList.add(t1);
        tileList.add(t2);
        repo = new Repository(tileList);
    }

    @Test
    public void saveTest() {
        Tile t = repo.save(t3);
        Assert.assertEquals(t,null);
    }

    @Test
    public void findTest(){
        ImageView iw = repo.find(2);
        Assert.assertEquals(iw,null);
    }
}
