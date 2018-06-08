package server;

public class RunSolver {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Solver Unittest ****");

        char[][] board = {{'s', 'L'}, {'L', 'g'}};
        
        GameBoard gB = new PipeGameBoard(board);
        
        Solver s = new MySolver(new BestFirstSearchSearcher());
        s.solve(gB);
    }
}
