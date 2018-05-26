
package classes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryWithParentChild {

    @SerializedName("CategoryId")
    @Expose
    private int categoryId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ParentId")
    @Expose
    private int parentId;
    @SerializedName("LevelId")
    @Expose
    private int levelId;
    @SerializedName("Depth")
    @Expose
    private int depth;
    @SerializedName("HasChild")
    @Expose
    private boolean hasChild;

    private boolean fromTitr;

    private int indexFromList;

    private int indexForColor;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getIndexForColor() {
        return indexForColor;
    }

    public void setIndexForColor(int indexForColor) {
        this.indexForColor = indexForColor;
    }

    public int getIndexFromList() {
        return indexFromList;
    }

    public void setIndexFromList(int indexFromList) {
        this.indexFromList = indexFromList;
    }

    public boolean isFromTitr() {
        return fromTitr;
    }

    public void setFromTitr(boolean fromTitr) {
        this.fromTitr = fromTitr;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

}
