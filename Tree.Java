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
        System.out.println(this.rnd.nextDouble()+ "<"+ proba);
        if(this.rnd.nextDouble() < proba){
            this.color = parentColor;
        }else{
            int rndColor = (int)(this.rnd.nextDouble()*4);
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

    public static Tree generateRandomtree(int nbLeaves, double proportionCut, int minDimensionCut, double sameColorProb, int widthLine, Random rnd, int width, int height){
        Tree a = new Tree(width, height, rnd);
        int leaves = 0;
        boolean isPossible = true;

        while(isPossible && leaves < nbLeaves){
            Tree leaf = chooseLeaf(a);
            if(leaf.width >= minDimensionCut || leaf.height >= minDimensionCut){
                Pair<Point, Boolean> division = leaf.chooseDivision(proportionCut);
                System.out.println("division :" + division.first().getx()+":"+division.first().gety());
                if(division.second())System.out.println("division en X");else System.out.println("division en Y");
                leaf.setDivisionAxis(division.second());
                if(division.second()){
                    //Case X division //Arrondir line div 2 en dessous pour left et inverse pour right
                    leaf.left = new Tree(leaf.coord, (division.first().getx() - leaf.coord.getx() - (widthLine/2)), leaf.height, rnd);
                    leaf.right = new Tree(new Point(division.first().getx()+(widthLine/2), division.first().gety()), (leaf.coord.getx()+leaf.width - division.first().getx() - (widthLine/2)), leaf.height, rnd);
                    System.out.println(leaf.left.coord.getx()+":"+leaf.left.coord.gety());
                    System.out.println(leaf.right.coord.getx()+":"+leaf.right.coord.gety());
                }else{
                    //Case Y division
                    leaf.left = new Tree(leaf.coord, leaf.width, (division.first().gety() - leaf.coord.gety() - (widthLine/2)), rnd);
                    leaf.right = new Tree(new Point(division.first().getx(), division.first().gety()+(widthLine/2)), leaf.width, (leaf.coord.gety()+leaf.height - division.first().gety() - (widthLine/2)), rnd);
                    System.out.println(leaf.left.coord.getx()+":"+leaf.left.coord.gety());
                    System.out.println(leaf.right.coord.getx()+":"+leaf.right.coord.gety());
                }
                leaf.left.chooseColor(leaf.color, sameColorProb);
                leaf.right.chooseColor(leaf.color, sameColorProb);
                leaves+=1 ;
            }else{
                isPossible = false;
            }
        }

        return a;
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