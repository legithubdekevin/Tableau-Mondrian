class Main {
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        Tree a = Tree.generateRandomtree(400, 0.3, 5, 0.5, 2, new Random(), 1400, 1000);
        Image img=a.toImage();
        
        try {
            img.save("img.png");
        } catch (Exception e) {
            // TODO: handle exception
        }


    }
}