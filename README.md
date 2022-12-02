# Exécution du programme

L'exécution du programme passe par l'appel du Main

L'exécution necessite de passer des arguments lors de l'appel de la commande d'exécution

Voici une explication ainsi que des exemples

## Sur linux on compile ainsi

Commande de compilation

> javac Main.java

Ensuite on utilise la commande java Main en ajoutant en parametre
 - nbLeaves : nombre de feuilles à dessiner
 - proportionCut : proportion de découpe
 -  minDimensionCut : taille minimum à découper
 - sameColorProb : probabilité d'avoir la meme couleur que la feuille père
 - widthLine : Taille des lignes séparant les rectangles couleur

> java Main nbLeaves proportionCut minDimensionCut sameColorProb widthLine

Pour exécuter la nouvelle stratégie on ajoutera les paramètres

 - centerX : coordonnées x du point de distorsion
 - centerY : coordonnées y du point de distorsion
 - num : numéro de la nouvelle stratégie (1 ou 2)

 > java Main nbLeaves proportionCut minDimensionCut sameColorProb widthLine centerX centerY num

## Exemples d'exécution

Génération d'un arbre de 400 feuilles

 > java Main 400 0.3 5 0.5 2
 ![400 feuilles](/examples/example1.png)

Génération d'un arbre avec la nouvelle stratégie (1) le point de distorsion étant en (700, 500)

 > java Main 150 0.5 1 0.5 4 700 500 1
 ![stratégie 1](/examples/example2.png)

Génération d'un arbre avec la nouvelle stratégie (2) le point de distorsion étant en (700, 500)

 > java Main 35 0.5 1 0.3 4 700 500 2
 ![stratégie 2](/examples/example3.png)