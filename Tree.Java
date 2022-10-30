import java.awt.Color;
import java.lang.Math;

public class Tree {

    private Random rnd;
    private Point coord;
    private Color color;
    private int   width,height;
    private Tree  left,right;

    /**
     * Constructor
     * @param a
     * @param proportion
     */
    Tree(int width, int height, Random rnd){
        this.coord=new Point(0,0);
        this.color=Color.white;
        this.width=width;
        this.height=height;
        this.left=null;
        this.right=null;

        this.rnd = rnd;
    }
    
    /**
     * Method chooseColor
     * @param parentColor
     * @param proba
     * @return
     */
    public Color chooseColor(Color parentColor, double proba){
        if(this.rnd.nextDouble() < proba){
            return parentColor;
        }else{
            int rndColor = (int)(this.rnd.nextDouble()*4);
            switch(rndColor){
                case 0:
                    return Color.white;
                case 1:
                    return Color.red;
                case 2:
                    return Color.yellow;
                case 3:
                    return Color.blue;
                case 4:
                    return Color.black;
                default:
                    return Color.white;
            }
        }
    }

    /**
     * Method chooseDivision
     * @param a
     * @param proportion
     * @return division coord and isDivisionAxisX
     */
    public Pair<Point, Boolean> chooseDivision(Tree a, double proportion){
        double p = (a.width*a.height)/(Math.pow(a.width+a.height, 1.5));
        if(rnd.nextDouble() < p){
            //Division X axis
            int s = a.width;
            int intervalMin = (int)Math.min(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            int intervalMax = (int)Math.max(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            int rndX = (int)(this.rnd.nextDouble()*(a.coord.getx()+intervalMax))+(a.coord.getx()+intervalMin);
            Point coord = new Point(rndX,a.coord.gety());
            return new Pair<Point, Boolean>(coord, true);
        }else{
            //Division Y axis
            int s = a.height;
            int intervalMin = (int)Math.min(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            int intervalMax = (int)Math.max(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            int rndY = (int)(rnd.nextDouble()*(a.coord.gety()+intervalMax))+(a.coord.gety()+intervalMin);
            Point coord = new Point(a.coord.getx(), rndY);
            return new Pair<Point, Boolean>(coord, false);
        }
    }



}