import java.util.ArrayList;
import java.util.Scanner;

public class MazeUtils{
	private static Scanner input = new Scanner(System.in);
	
	public static void runMaze(int[][] chosenMaze, int startXPos, int startYPos) {
		int XPos = startXPos;
		int YPos = startYPos;
		char choice2;
		while(true) {
			displayMaze(chosenMaze, XPos, YPos);
			// display maze choices
			System.out.println("Which direction do you want to go? Enter q to quit or s for solution.");
			choice2 = input.next().charAt(0);
			switch (choice2) {
			case 'l':
				if (YPos > 0) {
					if (chosenMaze[XPos][YPos - 1] != 1) YPos--;
				} else {
					System.out.println("You win!!");
					return;
				}
				break;
			case 'r':
				if (YPos < chosenMaze[XPos].length - 1) {
					if (chosenMaze[XPos][YPos + 1] != 1) YPos++;
				} else {
					System.out.println("You win!!");
					return;
				}
				break;
			case 'u':
				if (XPos > 0) {
					if (chosenMaze[XPos - 1][YPos] != 1) XPos--;
				} else {
					System.out.println("You win!!");
					return;
				}
				break;
			case 'd':
				if (XPos < chosenMaze.length - 1) {
					if (chosenMaze[XPos + 1][YPos] != 1) XPos++;
				} else {
					System.out.println("You win!!");
					return;
				}
				break;
			case 'q':
				return;
			case 's':
				char[] solution = solveMaze(chosenMaze,startXPos,startYPos);
				for (char c : solution) {
					System.out.print(c + " ");
				}
				System.out.println();
				return;
			default:
				System.out.println("Invalid Choice!");
				break;
			}
		}
	}
	
	
	public static void main(String[] args) {
		int maze1[][] = { { 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 0, 0, 1 },{ 1, 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 1, 0, 0, 1 },{ 1, 1, 0, 0, 0, 1, 1 }, { 1, 1, 0, 0, 0, 1, 1 },{ 1, 1, 0, 1, 1, 1, 1 } };
		int maze2[][] = { { 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1, 0, 1 },{ 1, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 0, 1 },{ 1, 1, 0, 0, 0, 0, 1 }, { 1, 1, 0, 1, 1, 1, 1 } };
		int maze3[][] = { { 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 0, 0, 1 },{ 1, 0, 0, 0, 1, 0, 1 }, { 1, 0, 1, 1, 0, 0, 1 },{ 1, 0, 1, 1, 1, 0, 1 }, { 1, 0, 1, 1, 1, 0, 1 },{ 1, 1, 1, 1, 1, 0, 1 } };
		
		// code to ask user for a map selection or quit
		char choice1;
		do {
			System.out.println("which map do you choose(1,2,3) or q to quit?");
			choice1 = input.next().charAt(0);
			switch (choice1) {
			case '1':
				runMaze(maze1, 1, 1);
				break;
			case '2':
				runMaze(maze2, 1, 1);
				break;
			case '3':
				runMaze(maze3, 5, 1);
				break;
			case 'q':
				System.exit(0);
				break;
			default: // FIX!!
				System.out.println("Invalid choice!");
				continue;
			}
		} while (choice1 != 'q');
	}

	public static void displayMaze(int[][] maze, int x, int y) {

		for (int row = 0; row < maze.length; row++) {
			for (int column = 0; column < maze[row].length; column++) {

				if (maze[row][column] == 1) {
					System.out.print('x');
				} else {
					if (row == x && column == y) {
						System.out.print("o");
					} else
						System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	public static char[] recursiveDfs(boolean[][] visited, int[][] maze, ArrayList<Character> path, int x, int y) {
		// check if we found the exit, if so copy it to a char array and return it
		if (x < 0 || x > maze.length - 1 || y < 0 || y > maze[0].length) {
			char[] solution = new char[path.size()];
			for (int i = 0; i < solution.length; i++) {
				solution[i] = path.get(i);
			}
			return solution;
		}
		// check if we hit a wall or a spot we already visited, if so, return immediately
		if (visited[x][y] || maze[x][y] == 1) 
			return null;
		// mark current spot as visited
		visited[x][y] = true;
		char[] solution;
		//turn down
		path.add('d');
		solution = recursiveDfs(visited,maze,path,x + 1,y);
		if (solution != null) return solution;
		path.remove(path.size() - 1); 
		// turn left
		path.add('l');
		solution = recursiveDfs(visited,maze,path,x ,y - 1);
		if (solution != null) return solution;
		path.remove(path.size() - 1); 
		// turn right
		path.add('r');
		solution = recursiveDfs(visited,maze,path,x ,y + 1);
		if (solution != null) return solution;
		path.remove(path.size() - 1); 
		//turn up
		path.add('u');
		solution = recursiveDfs(visited,maze,path,x - 1,y);
		if (solution != null) return solution;
		path.remove(path.size() - 1); 
		// nothing worked? maze has no solution!
		return null;
	}

	public static char[] solveMaze(int[][] maze, int x, int y) {
		boolean[][] visited = new boolean[maze.length][maze[0].length];
		return recursiveDfs(visited, maze, new ArrayList<Character>(), x, y);
	}
}
