class Main {

    static final int RANDOM_SEED = 0;
    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    private int nbLeaves;
    private double proportionCut;
    private int minDimensionCut;
    private double sameColorProb;
    private int widthLine;
    private int centerX;
    private int centerY;
    private int choosedStrategy;

    public void setnbLeaves(int val) throws Exception{
        if(val < 1){
            throw new Exception("Invalid nbLeaves value")
        }
        this.nbLeaves = val;
    }

    public void setProportionCut(double val) throws Exception{
        if(val < 0 || val > 1){
            throw new Exception("Invalid proportionCut value")
        }
        this.proportionCut = val;
    }


    public void setMinDimensionCut(int val) throws Exception{
        if(val < 0){
            throw new Exception("Invalid minDimensionCut value")
        }
        this.minDimensionCut = val;
    }

    public void setSameColorProb(double val) throws Exception{
        if(val < 0 || val > 1){
            throw new Exception("Invalid sameColorProb value")
        }
        this.sameColorProb = val;
    }

    public void setWidthLine(int val) throws Exception{
        if(val < 0){
            throw new Exception("Invalid widthLine value")
        }
        this.widthLine = val;
    }

    public void setCenterX(int val) throws Exception{
        if(val < 0 || val > WIDTH){
            throw new Exception("Invalid centerX value")
        }
        this.centerX = val;
    }

    public void setCenterY(int val) throws Exception{
        if(val < 0 || val > HEIGHT){
            throw new Exception("Invalid centerY value")
        }
        this.centerY = val;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

        for(String arg : args){
            System.out.println(arg);
        }
        try{
            this.setnbLeaves(Integer.parseInt(args[0]));
            this.setProportionCut(Double.parseDouble(args[1]));
            this.setMinDimensionCut(Integer.parseInt(args[2]));
            this.setSameColorProb(Double.parseDouble(args[3]));
            this.widthLine(Integer.parseInt(args[4]));
            this.setCenterX(Integer.parseInt(args[5]));
            this.setCenterY(Integer.parseInt(args[6]));
            this.choosedStrategy = Integer.parseInt(args[7]);
        }catch(Exception e){
            e.printStackTrace();
        }

        Random rnd = new Random(RANDOM_SEED);
        Tree a = Tree.generateRandomtree(300, 0.5, 0, 0.5, 0, rnd, WIDTH, HEIGHT);
        Image img=a.toImage();
        
        try {
            img.save("img.png");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }
}