class Main {
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        Tree a = Tree.generateBetterRandomtree2(300, 0.5, 0, 0.5, 0, new Random(3), 1000, 1000, new Point(500, 500));
        Image img=a.toImage();
        
        // AVL Arbre = new AVL(0, null, null);
        // System.out.println(Arbre);    
        // Arbre = AVL.add(Arbre,-1).first();
        // Arbre = AVL.add(Arbre,3).first();
        // Arbre = AVL.add(Arbre,2).first();
        // Arbre = AVL.add(Arbre,1).first();
        // Arbre = AVL.add(Arbre,5).first();
        // AVL.PrintAVL(Arbre);




        try {
            img.save("img.png");
        } catch (Exception e) {
            // TODO: handle exception
        }


    }
}