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
     * Method to Image
     * @return Image
     */
    public Image toImage(){
        Image img = new Image(this.width, this.height);
        drawImage(this, img);
        return img;
    }
    
    /**
     * Method chooseColor
     * @param parentColor
     * @param proba
     * @return
     */
    public void chooseColor(Color parentColor, double proba){
        if(this.rnd.nextDouble() < proba){
            this.color = parentColor;
        }else{
            int rndColor = (int)(this.rnd.nextDouble()*4);
            switch(rndColor){
                case 0:
                    this.color = Color.white;
                case 1:
                    this.color = Color.red;
                case 2:
                    this.color = Color.yellow;
                case 3:
                    this.color = Color.blue;
                case 4:
                    this.color = Color.black;
                default:
                    this.color = Color.white;
            }
        }
    }

    /**
     * Method chooseDivision
     * @param a
     * @param proportion
     * @return division coord and isDivisionAxisX
     */
    public Pair<Point, Boolean> chooseDivision(double proportion){
        double p = (this.width*this.height)/(Math.pow(this.width+this.height, 1.5));
        if(rnd.nextDouble() < p){
            //Division X axis
            int s = this.width;
            int intervalMin = (int)Math.min(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            int intervalMax = (int)Math.max(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            int rndX = (int)(this.rnd.nextDouble()*(this.coord.getx()+intervalMax))+(this.coord.getx()+intervalMin);
            Point coord = new Point(rndX,this.coord.gety());
            return new Pair<Point, Boolean>(coord, true);
        }else{
            //Division Y axis
            int s = this.height;
            int intervalMin = (int)Math.min(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            int intervalMax = (int)Math.max(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            int rndY = (int)(rnd.nextDouble()*(this.coord.gety()+intervalMax))+(this.coord.gety()+intervalMin);
            Point coord = new Point(this.coord.getx(), rndY);
            return new Pair<Point, Boolean>(coord, false);
        }
    }

    /**
     * Recursive function to choose leaf
     * @param a Tree
     * @return the leaf with the highest weight
     */
    public static Tree chooseLeaf(Tree a){
        if(a.left == null && a.right == null){
            //Case it's a leaf
            return a;
        }else{
            Tree childLeft = chooseLeaf(a.left);
            Tree childRight = chooseLeaf(a.right);
            if(weight(childLeft) >= weight(childRight)){
                return childLeft;
            }else{
                return childRight;
            }
        }
    }

    /**
     * Calculate the weight of a rectangle
     * @param a
     * @return the weight
     */
    public static double weight(Tree a){
        return (a.width*a.height)/(Math.pow(a.width+a.height, 1.5));
    }

    /**
     * Draw Image recursively
     * ** The line are not drawn
     * @param a
     * @param img
     */
    public static void drawImage(Tree a, Image img){
        if(a.left == null && a.right == null){
            //Case it's a leaf
            img.setRectangle(a.coord.getx(), a.coord.getx()+a.width, a.coord.gety(), a.coord.gety()+a.height, a.color);
        }else{
            drawImage(a.left, img);
            // ** here depends the division
            drawImage(a.right, img);
        }
    }

}