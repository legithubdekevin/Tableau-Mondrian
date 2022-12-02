class Main {

    static final int RANDOM_SEED = 0;
    static final int WIDTH = 1400;
    static final int HEIGHT = 1000;
    static int nbLeaves;
    static double proportionCut;
    static int minDimensionCut;
    static double sameColorProb;
    static int widthLine;
    static int centerX;
    static int centerY;
    static int choosedStrategy;

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        if(args.length == 0){
            AVL test = new AVL(null, null, null)
        }else{
            try{
                if(args.length < 5) throw new Exception("Missing parameters");
                setnbLeaves(Integer.parseInt(args[0]));
                setProportionCut(Double.parseDouble(args[1]));
                setMinDimensionCut(Integer.parseInt(args[2]));
                setSameColorProb(Double.parseDouble(args[3]));
                setWidthLine(Integer.parseInt(args[4]));
                if(args.length > 6) {setCenterX(Integer.parseInt(args[5]));}
                if(args.length > 7) {setCenterY(Integer.parseInt(args[6]));}
                if(args.length >= 8) {setChoosedStrategy(args[7]);}else{setChoosedStrategy(null);}
            }catch(Exception e){
                e.printStackTrace();
            }
    
            Random rnd = new Random(RANDOM_SEED);
            Tree a;
            Image img;
    
            if(choosedStrategy == 0){
                a = Tree.generateRandomtree(nbLeaves, proportionCut, minDimensionCut, sameColorProb, widthLine, rnd, WIDTH, HEIGHT);
                img=a.toImage();
    
                try {
                    img.save("img.png");
                } catch (Exception e) {
                    // handle exception
                    e.printStackTrace();
                }
            }else if(choosedStrategy == 1){
                System.out.println("test");
                a = Tree.generateBetterRandomtree(nbLeaves, proportionCut, minDimensionCut, sameColorProb, widthLine, rnd, WIDTH, HEIGHT, new Point(centerX, centerY));
                img=a.toImage();
    
                try {
                    img.save("img1.png");
                } catch (Exception e) {
                    // handle exception
                    e.printStackTrace();
                }
            }else if(choosedStrategy == 2){
    
                a = Tree.generateBetterRandomtree2(nbLeaves, proportionCut, minDimensionCut, sameColorProb, widthLine, rnd, WIDTH, HEIGHT, new Point(centerX, centerY));
                img=a.toImage();
    
                try {
                    img.save("img2.png");
                } catch (Exception e) {
                    // handle exception
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setnbLeaves(int val) throws Exception{
        if(val < 1){
            throw new Exception("Invalid nbLeaves value");
        }
        nbLeaves = val;
    }

    public static void setProportionCut(double val) throws Exception{
        if(val < 0 || val > 1){
            throw new Exception("Invalid proportionCut value");
        }
        proportionCut = val;
    }


    public static void setMinDimensionCut(int val) throws Exception{
        if(val < 0){
            throw new Exception("Invalid minDimensionCut value");
        }
        minDimensionCut = val;
    }

    public static void setSameColorProb(double val) throws Exception{
        if(val < 0 || val > 1){
            throw new Exception("Invalid sameColorProb value");
        }
        sameColorProb = val;
    }

    public static void setWidthLine(int val) throws Exception{
        if(val < 0){
            throw new Exception("Invalid widthLine value");
        }
        widthLine = val;
    }

    public static void setCenterX(int val) throws Exception{
        if(val < 0 || val > WIDTH){
            throw new Exception("Invalid centerX value");
        }
        centerX = val;
    }

    public static void setCenterY(int val) throws Exception{
        if(val < 0 || val > HEIGHT){
            throw new Exception("Invalid centerY value");
        }
        centerY = val;
    }

    public static void setChoosedStrategy(String val) throws Exception{
        if(val == null){
            choosedStrategy = 0;
        }else{
            choosedStrategy = Integer.parseInt(val);
        }
    }
}