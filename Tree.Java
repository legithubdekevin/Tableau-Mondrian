import java.awt.Color;

public class Tree {

    private Point point;
    private Color color;
    private int   width,height;
    private Tree  left,right;

    /*
     * Constructor
     */
    Tree(int width, int height){
        this.point=new Point(0,0);
        this.color=Color.white;
        this.width=width;
        this.height=height;
        this.left=null;
        this.right=null;

    }
    /*
     * Methodes
     */
    public static void chooseColor(Color parentColor, double proba){
        if()
    }

}