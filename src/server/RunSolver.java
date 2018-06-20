package server;

public class RunSolver {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Solver Unittest ****");

        char[][] board = {{'s', ' '}, {'-', 'g'}};
        
        GameBoard gB = new PipeGameBoard(board);
        
        Solver solver = new MySolver(new BestFirstSearchSearcher());
        Solution sol = solver.solve(gB);
    }
}
