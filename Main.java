class Main {
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        Tree a = Tree.generateBetterRandomtree2(300, 0.5, 0, 0.5, 0, new Random(3), 1000, 1000, new Point(500, 500));
        Image img=a.toImage();
        
        try {
            img.save("img.png");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }
}