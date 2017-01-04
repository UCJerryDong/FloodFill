yimport java.util.Stack;

// This is the basic flood algorithm based on a 6*6 maze.
class MicroMouse {

	// Create a great number for the convience of finding md.
	final static int BIG_NUMBER = 500;


	// Define the maze.
	final static int size_maze = 6;
	final static int num_neighbor = 4;
	static int center_maze = size_maze / 2 - 1;
	static int [][] maze = new int[size_maze][size_maze];
	
	// Create four relative arrays to show if there is a wall in that direction;
	// Will be replaced when the actual sensor is equiped
	static int [][] up = {{1,1,1,1,1,1},
						  {0,0,1,1,1,0},
						  {0,0,1,1,0,1},
						  {0,1,0,0,0,1},
						  {1,0,1,1,1,0},
						  {0,0,0,0,0,0}};
	static int [][] right = {{0,0,0,0,0,1},
						     {1,0,0,0,0,1},
						     {1,1,0,0,0,1},
						     {0,1,0,1,0,1},
						     {0,1,0,1,0,1},
						     {1,0,1,0,1,1}};
	static int [][] down = {{0,0,1,1,1,0},
						    {0,0,1,1,0,1},
						    {0,1,0,0,0,1},
						    {1,0,1,1,1,0},
						    {0,0,1,1,1,0},
						    {1,1,1,1,1,1}};
	static int [][] left = {{1,0,0,0,0,0},
						    {1,1,0,0,0,0},
						    {1,1,1,0,0,0},
						    {1,0,1,0,1,0},
						    {1,0,1,0,1,0},
						    {1,1,0,1,0,1}};

	// Print the current maze
	public static void PrintMaze() {
		for (int row = 0; row < size_maze; row++) {
			for (int col = 0; col < size_maze; col++) {
				System.out.print(maze[row][col] + " ");
			}
			System.out.print("\n");
		}
	}

	// Give the initial values for a 6*6 maze
	// +1 is used to identify the four center square
	public static void InitializeMaze() {
		for (int row = 0; row < size_maze; row++) {
			for (int col = 0; col < size_maze; col++) {
				if (row <= center_maze && col <= center_maze)
					maze[row][col] = (center_maze - row) + (center_maze - col);
				else if (row < center_maze + 1 && col > center_maze)
					maze[row][col] =  (center_maze - row) + (col - center_maze) - 1;
				else if (row > center_maze && col < center_maze + 1)
					maze[row][col] = (row - center_maze) + (center_maze - col) - 1;
				else if (row > center_maze + 1 || col > center_maze + 1)
					maze[row][col] = (row - center_maze) + (col - center_maze) - 2;
			}	
		}
	}

	// Get the neighbor of a particular node
	public static int[] getNeighbor (int row, int col) {

		int [] neighbor = {-1, -1, -1, -1};

		// Get the neighbor info of this particular node

		// Consider if top is in array and it does not have an up wall
		// Same for the following
		if (isInArray(row-1, col) && up[row][col] == 0) 
			neighbor[0] = maze[row-1][col];

		if (isInArray(row, col+1) && right[row][col] == 0)
			neighbor[1] = maze[row][col+1];

		if (isInArray(row+1, col) && down[row][col] == 0)
			neighbor[2] = maze[row+1][col];

		if (isInArray(row, col-1) && left[row][col] == 0)
			neighbor[3] = maze[row][col-1];

		return neighbor;
	}

	public static int getMinDistance(int[] neighbor) {
		// Assign a number to minDinstance for further comparison
		int minDinstance = BIG_NUMBER;

		for (int index = 0; index < num_neighbor; index++) {
			if (minDinstance > neighbor[index] && neighbor[index] != -1) 
				minDinstance = neighbor[index];
		}

		// -1 is used to assign the required distance 
		return minDinstance;
	}

	// To determine if this pair refers to a node in the array
	public static boolean isInArray (int row, int col) {
		return row >= 0 && col >=0 && row < size_maze && col < size_maze;
	}
	
	// Update the maze
	public static void updateDistance(int row, int col) {
		int [] neighbor = getNeighbor(row, col);
		int minDinstance = getMinDistance(neighbor);
		for (int index = 0; index < num_neighbor; index++)
			System.out.print(neighbor[index]);

		if (minDinstance != (maze[row][col] - 1)) {
			
			maze[row][col] = minDinstance + 1;

			// Manually check the four direction
			if (neighbor[0] != -1) 
				updateDistance(row-1, col);
			if (neighbor[1] != -1)
				updateDistance(row, col+1);
			if (neighbor[2] != -1)
				updateDistance(row+1, col);
			if (neighbor[3] != -1)
				updateDistance(row, col-1);
		}
	}	
	
	// Mimic the movement of MM
	public static void moveMM (int row, int col) {
		if (maze[row][col] == 0) {
			return;
		}
		if (up[row][col] == 0) {
			moveMM(row-1,col);
		}
		else if (right[row][col] == 0){
			moveMM(row,col+1);
		}
		else if (down[row][col] == 0){
			moveMM(row+1,col);
		}
		else if (left[row][col] == 0){
			moveMM(row,col-1);
		}
		updateDistance(row,col);
	}

	// Launch the flood 
	public static void main (String[] args) {
		InitializeMaze();
		PrintMaze();
		updateDistance(3,1);
		System.out.print("\n");
		PrintMaze();
	}
}
