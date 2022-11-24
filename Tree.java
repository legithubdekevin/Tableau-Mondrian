import java.awt.Color;
import java.lang.Math;

public class Tree {

    private Random  rnd;
    private Point   coord;
    private Color   color;
    private int     width,height;
    private Tree    left,right;
    private boolean isDivisionAxisX;

    /**
     * Constructor
     * @param a
     * @param proportion
     * complexity constant
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
    public Point getCoord() {
        return coord;
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
     * complexity constant
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
     * complexity linear on tree size
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

    public static Tree chooseLeaf(AVL weights){
        return AVL.max(weights);
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
     * 
     * @param nbLeaves
     * @param proportionCut
     * @param minDimensionCut
     * @param sameColorProb
     * @param widthLine
     * @param rnd
     * @param width
     * @param height
     * @return
     * complexity quadratic on nbLeaves
     */
    public static Tree generateRandomtree(int nbLeaves, double proportionCut, int minDimensionCut, double sameColorProb, int widthLine, Random rnd, int width, int height){
        Tree a = new Tree(width, height, rnd);
        int leaves = 1;
        boolean isPossible = true;
        AVL weights = new AVL(a, null, null);

        while(isPossible && leaves < nbLeaves){
            //Choose a leaf
            //Tree leaf = chooseLeaf(a);
            Tree leaf = chooseLeaf(weights);
            Pair<Point, Boolean> division = leaf.chooseDivision(proportionCut);
            if(isDivisionPossible(leaf, minDimensionCut, division.second())){
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
                
                //Create child
                leaf.left = new Tree(leftCoord, leftWidth, leftHeight, rnd);
                leaf.left.chooseColor(leaf.color, sameColorProb);
                leaf.right = new Tree(rightCoord, rightWidth, rightHeight, rnd);
                leaf.right.chooseColor(leaf.color, sameColorProb);
                //Delete max weigth and insert child in AVL
                weights = AVL.deleteMax(weights);
                weights= AVL.add(weights, leaf.left).first();
                weights = AVL.add(weights, leaf.right).first();
                
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
    public static boolean isDivisionPossible(Tree leaf, int minDimensionCut, boolean isDivisionAxisX){
        if(isDivisionAxisX){
            return leaf.width >= minDimensionCut;
        }else{
            return leaf.height >= minDimensionCut;
        }
    }

    /**
     * Draw Image recursively
     * @param a
     * @param img
     * complexity nbLeaves * heightLeaf * widthLeaf
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


    /**
     * New Strategy
     * @param nbLeaves
     * @param proportionCut
     * @param minDimensionCut
     * @param sameColorProb
     * @param widthLine
     * @param rnd
     * @param width
     * @param height
     * @param center
     * @return
     */
    public static Tree generateBetterRandomtree(int nbLeaves, double proportionCut, int minDimensionCut, double sameColorProb, int widthLine, Random rnd, int width, int height, Point center){
        Tree a = new Tree(width, height, rnd);
        QuadTree pp = new QuadTree(a);
        Image imgp = new Image(width, height);
        imgp.setRectangle(0, width, 0, height, Color.white);
        imgp.setRectangle(center.getx()-3, center.getx()+3, center.gety()-3, center.gety()+3, Color.blue);
        int leaves = 1;
        boolean isPossible = true;
        while(leaves < nbLeaves && isPossible){
            //Choose division Axis
            boolean isDivisionAxisX = rnd.nextDouble() < proportionCut;
            //Generate random point interpolate
            Point rndPoint;
            if(isDivisionAxisX){
                //Find max interval to divide
                int startX = (center.getx()) < (width - center.getx()) ? width : 0;
                //Find interpol point
                int interpolateX = startX+(int)((center.getx() - startX)*((double)leaves/(double)nbLeaves));
                //Generate random y
                int randomY = (int)(rnd.nextDouble() * height);
                rndPoint = new Point(interpolateX, randomY);
            }else{
                //Find max interval to divide
                int startY = (center.gety()) < (height - center.gety()) ? height : 0;
                //Find interpol point
                int interpolateY = startY+(int)((center.gety() - startY)*((double)leaves/(double)nbLeaves));
                //Generate random x
                int randomX = (int)(rnd.nextDouble() * width);
                rndPoint = new Point(randomX, interpolateY);
            }
            //System.out.println(rndPoint.getx()+", "+rndPoint.gety());
            //Trouver la feuille contenant le point
            Tree leaf = QuadTree.getTreeContained(rndPoint, pp);
            if(isDivisionPossible(leaf, minDimensionCut, isDivisionAxisX)){
                System.out.println("LEAF "+leaf.coord.getx()+", "+leaf.coord.gety()+" h: "+leaf.height+" w: "+leaf.width);
                leaf.setDivisionAxis(isDivisionAxisX);

                

                Point leftCoord;
                Point rightCoord;
                int leftWidth;
                int rightWidth;
                int leftHeight;
                int rightHeight;

                if(isDivisionAxisX){
                    //Case X division
                    leftCoord   = leaf.coord;
                    rightCoord  = new Point(rndPoint.getx()+(widthLine/2), leaf.coord.gety());
                    //Case the width line is impair we slice left part
                    leftWidth   = rndPoint.getx() - leftCoord.getx() - (int)Math.ceil((double)widthLine/2);
                    rightWidth  = leaf.coord.getx()+leaf.width - rndPoint.getx() - (int)Math.floor((double)widthLine/2);
                    leftHeight  = leaf.height;
                    rightHeight = leaf.height;
                    System.out.println("startL : "+leftCoord.getx()+", "+leftCoord.gety());
                    System.out.println("endL : "+(leftCoord.getx()+leftWidth)+", "+(leftCoord.gety()+leftHeight));
                    System.out.println("startR : "+rightCoord.getx()+", "+rightCoord.gety());
                    System.out.println("endR : "+(rightCoord.getx()+rightWidth)+", "+(rightCoord.gety()+rightHeight));

                }else{
                    //Case Y division
                    leftCoord   = leaf.coord;
                    rightCoord  = new Point(leaf.coord.getx(), rndPoint.gety()+(widthLine/2));
                    leftWidth   = leaf.width;
                    rightWidth  = leaf.width;
                    //Case the width line is impair we slice left part 
                    leftHeight  = rndPoint.gety() - leaf.coord.gety() - (int)Math.ceil((double)widthLine/2);
                    rightHeight = leaf.coord.gety()+leaf.height - rndPoint.gety() - (int)Math.floor((double)widthLine/2);
                    System.out.println("startL : "+leftCoord.getx()+", "+leftCoord.gety());
                    System.out.println("endL : "+(leftCoord.getx()+leftWidth)+", "+(leftCoord.gety()+leftHeight));
                    System.out.println("startR : "+rightCoord.getx()+", "+rightCoord.gety());
                    System.out.println("endR : "+(rightCoord.getx()+rightWidth)+", "+(rightCoord.gety()+rightHeight));
                }
                
                //Create child
                leaf.left = new Tree(leftCoord, leftWidth, leftHeight, rnd);
                leaf.left.chooseColor(leaf.color, sameColorProb);
                leaf.right = new Tree(rightCoord, rightWidth, rightHeight, rnd);
                leaf.right.chooseColor(leaf.color, sameColorProb);
                //insert child in QuadTree
                pp = QuadTree.addPoint(leaf.right, pp);
                
                leaves+=1 ;
                try{
                    Image img = a.toImage();
                    img.setRectangle(rndPoint.getx()-3, rndPoint.getx()+3, rndPoint.gety()-3, rndPoint.gety()+3, Color.yellow);
                    img.save("img"+leaves+".png");
                    
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }else{
                isPossible = false;
            }
        }

        return a;
    }

    public static Tree generateBetterRandomtree2(int nbLeaves, double proportionCut, int minDimensionCut, double sameColorProb, int widthLine, Random rnd, int width, int height, Point center){
        Tree a = new Tree(width, height, rnd);
        QuadTree pp = new QuadTree(a);
        Image imgp = new Image(width, height);
        imgp.setRectangle(0, width, 0, height, Color.white);
        imgp.setRectangle(center.getx()-3, center.getx()+3, center.gety()-3, center.gety()+3, Color.blue);
        int leaves = 1;
        boolean isPossible = true;
        //Get max radius
        int maxRadius = Math.min(Math.min(center.getx(), width-center.getx()), Math.min(center.gety(), height-center.gety()))-minDimensionCut; 
        while(leaves < nbLeaves && isPossible){
            //Choose division Axis
            boolean isDivisionAxisX = rnd.nextDouble() < proportionCut;

            //Interpolate radius
            int interpolateRadius = maxRadius-(int)(maxRadius*((double)leaves/(double)nbLeaves));
            //Random polar coord
            int randomTheta = (int)(rnd.nextDouble()*360);


            Point rndPoint = new Point(center.getx()+(int)(interpolateRadius*Math.cos(Math.toRadians(randomTheta))), center.gety()+(int)(interpolateRadius*Math.sin(Math.toRadians(randomTheta))));
            //Trouver la feuille contenant le point
            Tree leaf = QuadTree.getTreeContained(rndPoint, pp);
            if(isDivisionPossible(leaf, minDimensionCut, isDivisionAxisX)){
                System.out.println("LEAF "+leaf.coord.getx()+", "+leaf.coord.gety()+" h: "+leaf.height+" w: "+leaf.width);
                leaf.setDivisionAxis(isDivisionAxisX);

                

                Point leftCoord;
                Point rightCoord;
                int leftWidth;
                int rightWidth;
                int leftHeight;
                int rightHeight;

                if(isDivisionAxisX){
                    //Case X division
                    leftCoord   = leaf.coord;
                    rightCoord  = new Point(rndPoint.getx()+(widthLine/2), leaf.coord.gety());
                    //Case the width line is impair we slice left part
                    leftWidth   = rndPoint.getx() - leftCoord.getx() - (int)Math.ceil((double)widthLine/2);
                    rightWidth  = leaf.coord.getx()+leaf.width - rndPoint.getx() - (int)Math.floor((double)widthLine/2);
                    leftHeight  = leaf.height;
                    rightHeight = leaf.height;
                    System.out.println("startL : "+leftCoord.getx()+", "+leftCoord.gety());
                    System.out.println("endL : "+(leftCoord.getx()+leftWidth)+", "+(leftCoord.gety()+leftHeight));
                    System.out.println("startR : "+rightCoord.getx()+", "+rightCoord.gety());
                    System.out.println("endR : "+(rightCoord.getx()+rightWidth)+", "+(rightCoord.gety()+rightHeight));

                }else{
                    //Case Y division
                    leftCoord   = leaf.coord;
                    rightCoord  = new Point(leaf.coord.getx(), rndPoint.gety()+(widthLine/2));
                    leftWidth   = leaf.width;
                    rightWidth  = leaf.width;
                    //Case the width line is impair we slice left part 
                    leftHeight  = rndPoint.gety() - leaf.coord.gety() - (int)Math.ceil((double)widthLine/2);
                    rightHeight = leaf.coord.gety()+leaf.height - rndPoint.gety() - (int)Math.floor((double)widthLine/2);
                    System.out.println("startL : "+leftCoord.getx()+", "+leftCoord.gety());
                    System.out.println("endL : "+(leftCoord.getx()+leftWidth)+", "+(leftCoord.gety()+leftHeight));
                    System.out.println("startR : "+rightCoord.getx()+", "+rightCoord.gety());
                    System.out.println("endR : "+(rightCoord.getx()+rightWidth)+", "+(rightCoord.gety()+rightHeight));
                }
                
                //Create child
                leaf.left = new Tree(leftCoord, leftWidth, leftHeight, rnd);
                leaf.left.chooseColor(leaf.color, sameColorProb);
                leaf.right = new Tree(rightCoord, rightWidth, rightHeight, rnd);
                leaf.right.chooseColor(leaf.color, sameColorProb);
                //insert child in QuadTree
                pp = QuadTree.addPoint(leaf.right, pp);
                
                leaves+=1 ;
                try{
                    Image img = a.toImage();
                    img.setRectangle(rndPoint.getx()-3, rndPoint.getx()+3, rndPoint.gety()-3, rndPoint.gety()+3, Color.yellow);
                    img.setRectangle(center.getx()-3, center.getx()+3, center.gety()-3, center.gety()+3, Color.blue);

                    img.save("img"+leaves+".png");
                    
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }else{
                isPossible = false;
            }
        }

        return a;
    }


}