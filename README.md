# TD5_S6_Projet_JAVA
GitHub pour le projet Java de S6 à l'ENSEA, en binôme avec Laure WOLFF


## 🏃‍♂️ Projet Jeu Java : Héros en Action

Ce projet Java présente un jeu simple et divertissant où le joueur incarne un héros qui peut courir, marcher ou s'arrêter tout en évitant différents obstacles pour arriver à la fin de chaque niveau.

## 🎮 Contrôles du jeu

- **Courir** : Appuyer sur la touche `F`
- **Marcher** : Appuyer sur la touche `S`
- **S'arrêter** : Appuyer sur la touche `Espace`

## 🌳 Obstacles et pièges

Tout au long de son parcours, le héros devra éviter trois types d'obstacles :
- 🪨 **Roches** : Impossibles à traverser
- 🌲 **Arbres** : Bloquent le chemin
- ⚠️ **Pièges** : Marcher dessus déclenche immédiatement un Game Over !

## 🌎 Fonctionnalités

### Niveau 1 :
Le premier niveau est fixe et permet au joueur de s'habituer aux mécaniques du jeu.

### Niveau 2 (et suivants) : Génération aléatoire
À partir du niveau 2, chaque carte est générée aléatoirement tout en garantissant toujours au moins un chemin valide grâce à un algorithme de génération de labyrinthe.

Voici un aperçu simplifié de l'algorithme utilisé pour générer automatiquement les niveaux :

```java
Stack<int[]> stack = new Stack<>();
stack.push(new int[]{startY, 1});

while (!stack.isEmpty()) {
    int[] current = stack.peek();
    // Trouver voisins possibles et creuser un chemin valide
    // ...
}
```

Chaque génération du niveau est sauvegardée dans un fichier texte `level2.txt`.

## 📷 Captures d'écran

Voici à quoi ressemble l'interface du jeu :

- **Écran d'accueil** :
![Écran d'accueil](Projet_Java_S6_CAPODAGLI_WOLFF/img/EcranTitle.png)

- **Écran Game Over** :
![Game Over](Projet_Java_S6_CAPODAGLI_WOLFF/img/gameover.png)


---

**Bon jeu à tous !** 🎲🏅
