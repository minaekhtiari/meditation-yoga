package apps.hillavas.com.yoga.classes;

/**
 * Created by A.Mohammadi on 9/12/2017.
 */

public class Home_Menu_Page {

    private int levelId;
    private int categoryId;
    private String titr;
    private boolean hasChild;

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitr() {
        return titr;
    }

    public void setTitr(String titr) {
        this.titr = titr;
    }
}
