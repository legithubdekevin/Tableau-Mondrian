import java.awt.Color;
import java.lang.Math;

public class Tree {

    private Random rnd;

    private Point coord;
    private Color color;
    private int   width,height;
    private Tree  left,right;
    private boolean isDivisionAxisX;

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

    Tree(Point coord, int width, int height, Random rnd){
        this.rnd = rnd;

        this.coord = coord;
        this.color = Color.white;
        this.width = width;
        this.height = height;
        this.left = null;
        this.right = null;        
    }

    /**
     * Setter division axis
     * @param isX
     */
    public void setDivisionAxis(boolean isX){
        this.isDivisionAxisX = isX;
    }
    
    
    public boolean getDivisionAxis(){
        return this.isDivisionAxisX ;
    }
    public int getwidth(){
        return this.width;
    }
    public int getheight(){
        return this.height;
    }
    /**
     * Method to Image
     * @return Image
     */
    public Image toImage(){
        Image img = new Image(this.width, this.height);
        img.setRectangle(0, width, 0, height, Color.gray);
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
            int rndColor = (int)(this.rnd.nextDouble()*5);
            switch(rndColor){
                case 0:
                    this.color = Color.white;
                    break;
                case 1:
                    this.color = Color.red;
                    break;
                case 2:
                    this.color = Color.yellow;
                    break;
                case 3:
                    this.color = Color.blue;
                    break;
                case 4:
                    this.color = Color.black;
                    break;
                default:
                    this.color = Color.white;
                    break;
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
        double p = ((double)(this.getwidth()) / (double)(this.getwidth()+this.getheight()));
        double randnum = this.rnd.nextDouble();
        if(randnum < p){
            //Division X axis
            int s = this.width;
            int intervalMin = (int)Math.min(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            int intervalMax = (int)Math.max(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            //Generate random pos in interval
            int rndX = (int)(this.rnd.nextDouble()*(intervalMax-intervalMin))+intervalMin+this.coord.getx();
            Point coord = new Point(rndX,this.coord.gety());

            return new Pair<Point, Boolean>(coord, true);
        }else{
            //Division Y axis
            int s = this.height;
            int intervalMin = (int)Math.min(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            int intervalMax = (int)Math.max(Math.ceil(s*(1-proportion)), Math.floor(s*proportion));
            //Generate random pos in interval
            int rndY = (int)(rnd.nextDouble()*(intervalMax-intervalMin))+intervalMin+this.coord.gety();
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

    public static Tree generateRandomtree(int nbLeaves, double proportionCut, int minDimensionCut, double sameColorProb, int widthLine, Random rnd, int width, int height){
        Tree a = new Tree(width, height, rnd);
        int leaves = 1;
        boolean isPossible = true;

        while(isPossible && leaves < nbLeaves){
            Tree leaf = chooseLeaf(a);
            if(isDivisionPossible(leaf, minDimensionCut)){
                Pair<Point, Boolean> division = leaf.chooseDivision(proportionCut);
                leaf.setDivisionAxis(division.second());

                Point leftCoord;
                Point rightCoord;
                int leftWidth;
                int rightWidth;
                int leftHeight;
                int rightHeight;

                if(division.second()){
                    //Case X division
                    leftCoord   = leaf.coord;
                    rightCoord  = new Point(division.first().getx()+(widthLine/2), division.first().gety());
                    //Case the width line is impair we slice left part
                    leftWidth   = division.first().getx() - leftCoord.getx() - (int)Math.ceil((double)widthLine/2);
                    rightWidth  = leaf.coord.getx()+leaf.width - division.first().getx() - (int)Math.floor((double)widthLine/2);
                    leftHeight  = leaf.height;
                    rightHeight = leaf.height;

                }else{
                    //Case Y division
                    leftCoord   = leaf.coord;
                    rightCoord  = new Point(division.first().getx(), division.first().gety()+(widthLine/2));
                    leftWidth   = leaf.width;
                    rightWidth  = leaf.width;
                    //Case the width line is impair we slice left part 
                    leftHeight  = division.first().gety() - leaf.coord.gety() - (int)Math.ceil((double)widthLine/2);
                    rightHeight = leaf.coord.gety()+leaf.height - division.first().gety() - (int)Math.floor((double)widthLine/2);
                }
                
                leaf.left = new Tree(leftCoord, leftWidth, leftHeight, rnd);
                leaf.left.chooseColor(leaf.color, sameColorProb);
                leaf.right = new Tree(rightCoord, rightWidth, rightHeight, rnd);
                leaf.right.chooseColor(leaf.color, sameColorProb);
                leaves+=1 ;
            }else{
                isPossible = false;
            }
        }

        return a;
    }

    /**
     * Method is a leaf divisable
     * @param leaf
     * @param minDimensionCut
     * @return
     */
    public static boolean isDivisionPossible(Tree leaf, int minDimensionCut){
        return leaf.width >= minDimensionCut || leaf.height >= minDimensionCut;
    }

    /**
     * Draw Image recursively
     * @param a
     * @param img
     */
    public static void drawImage(Tree a, Image img){
        if(a.left == null && a.right == null){
            //Case it's a leaf
            img.setRectangle(a.coord.getx(), a.coord.getx()+a.width, a.coord.gety(), a.coord.gety()+a.height, a.color);
        }else{
            drawImage(a.left, img);                           
            drawImage(a.right, img);
        }
    }

}