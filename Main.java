class Main {
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        Tree a = Tree.generateRandomtree(10000, 0.50, 5, 0.01, 2, new Random(), 1000, 1000);
        Image img=a.toImage();
        
        try {
            img.save("img.png");
        } catch (Exception e) {
            // TODO: handle exception
        }


    }
}