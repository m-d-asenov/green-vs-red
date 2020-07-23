/**
* Each cell on a grid is eithr green(1) or red(0).
* The size of the grid, i.e. its height and width, is taken from the first line of the input.
*
* The two integers are separated by a comma and a space, 
* so that the first integer (x) is the width and the second (y) is the height.
*
* The next y lines of the input consist of x 0s and 1s in each line - 
* the initial values of the cells in the grid - Generation Zero.
*
* There are two rules for changing the value of a  cell:
* 1) A red cell turns to green if it is surrounded either by 3 or by 6 green cells.
* 2) A green cell turns to red it is surrounded by 0, 1, 4, 5, 7, or 8 green neighbours.
*
* The last input line consists of three integers separated by a comma and a space.
* The first two numbers represent the cordinates of one of the cells in the grid,
* and again the first integer is the width(which column) and the second one shows the height(which row).
* The third integer shows how many times (generations) the two rules will be executed on the grid.
*
* The task of the programm is to count how many times the value of the coordinates taken from the last input line,
* will be 1 (or green), including the Generation Zero.
*
* @author (Miriyan Asenov)
* @version (2020-07-24)
*/


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GreenRed{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		int[] gridSize = Arrays.stream( sc.nextLine().split(", ") ).mapToInt(Integer::parseInt).toArray();
		
		Grid grid = new Grid(gridSize[1], gridSize[0]);
		
		int[][] gridArr = new int[grid.getHeight()][grid.getWidth()];
		
		for(int i = 0; i < grid.getHeight(); i++){
			
			int[] inputLine = Arrays.stream( sc.nextLine().split("") ).mapToInt(Integer::parseInt).toArray();
			
			for(int j = 0; j < grid.getWidth(); j++){
				gridArr[i][j] = inputLine[j];
			}
		}
		
		int[] lastInputLine = Arrays.stream( sc.nextLine().split(", ") ).mapToInt(Integer::parseInt).toArray();
		
		int row = lastInputLine[1];
		int col = lastInputLine[0];
		int generations = lastInputLine[2];
		ArrayList<Integer> neighbours = new ArrayList<>();
		HashMap<String,ArrayList<Integer>> eachCellNeighbours = new HashMap<>();
		ArrayList<Integer> targetCellStates = new ArrayList<>();
		for(int i = 0; i <= generations; i++){
			eachCellNeighbours = new HashMap<>(); 
			for(int j = 0; j < grid.getHeight(); j++){
				
				for(int k = 0; k < grid.getWidth(); k++){
					neighbours = new ArrayList<>();
					
					if(j > 0 && k > 0) neighbours.add(gridArr[j-1][k-1]);
					if(j > 0) neighbours.add(gridArr[j-1][k]);
					if(j > 0 && k < grid.getWidth() - 1)neighbours.add(gridArr[j-1][k+1]);
					if(k > 0)neighbours.add(gridArr[j][k-1]);
					if(k < grid.getWidth() - 1)neighbours.add(gridArr[j][k+1]);
					if(j < grid.getHeight() - 1 && k > 0)neighbours.add(gridArr[j+1][k-1]);
					if(j < grid.getHeight() - 1) neighbours.add(gridArr[j+1][k]);
					if(j < grid.getHeight() - 1 && k < grid.getWidth() - 1)neighbours.add(gridArr[j+1][k+1]);
					
					String coordinates = j + " " + k; 
					eachCellNeighbours.put(coordinates, neighbours);
				}
				
			}
			
			for(Map.Entry<String,ArrayList<Integer>> entry : eachCellNeighbours.entrySet()){
				int[] coord = Arrays.stream(entry.getKey().split(" ")).mapToInt(Integer::parseInt).toArray();
				
				if(coord[0] == row && coord[1] == col) targetCellStates.add(gridArr[coord[0]][coord[1]]);
				
				int sum = entry.getValue().stream().mapToInt(Integer::intValue).sum();
				
				if(gridArr[coord[0]][coord[1]] == 0 && (sum == 3 || sum == 6)) gridArr[coord[0]][coord[1]] = 1;
				
				if(gridArr[coord[0]][coord[1]] == 1 && (sum == 0 || sum == 1 || 
						   sum == 4 || sum == 5 ||
						   sum == 7 || sum == 8) ) gridArr[coord[0]][coord[1]] = 0;		
			}
			
		}
		System.out.print(targetCellStates.stream().mapToInt(Integer::intValue).sum());
	}
}