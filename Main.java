import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
  static boolean[][] wall;
  static boolean[][] visited;
  int ROWS;
  int COLS;
  static ArrayList<Integer> roomSize = new ArrayList<Integer>();
  int currentRoom;
  int flooring;


  public Main(int ROWS, int COLS, int flooring, Scanner sc) {
    this.ROWS = ROWS;
    this.COLS = COLS;
    this.flooring = flooring;
    wall = new boolean[ROWS][COLS]; 
    visited = new boolean[ROWS][COLS];
    sc.nextLine();

    for (int i = 0; i < ROWS; i++) {
      String row = sc.nextLine();
   
      for (int j = 0; j < COLS; j++) {
        if (row.charAt(j) == 'I') {
          wall[i][j] = true;
        }
      }
    }
  }


  private boolean valid(int row, int col) {
      boolean c1 = row >= 0;
      boolean c2 = row < ROWS;
      boolean c3 = col >= 0;
      boolean c4 = col < COLS;
      return c1 && c2 && c3 && c4;

  }

  private boolean isCorner(int i, int j) {

    return !(i == 0 || j == 0); 
    
  }

  public void searchRoom(int row, int col) {
    visited[row][col] = true;
    roomSize.set(currentRoom, roomSize.get(currentRoom)+1);



    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
       
        if (valid(row+i,col+j) && !wall[row+i][col+j] && !visited[row+i][col+j] && !isCorner(i,j)) {
          // Move to next cell in same room
          searchRoom(row+i,col+j);
        }
      }
    }
  }

  public void findFloor() {
    for (int i = 0; i < ROWS; i++) {
      
      for (int j = 0; j < COLS; j++) {
        if (wall[i][j] == false && visited[i][j] == false) {
          roomSize.add(0);
          currentRoom = roomSize.size() - 1;
          searchRoom(i,j);
        }
      }   
    }
    
    Collections.sort(roomSize, Collections.reverseOrder());
    
  }

  public void applyFlooring() {
    int currRoom = 0;
    
    while (this.flooring > 0 && currRoom < roomSize.size()) {
      if (this.flooring - roomSize.get(currRoom)>=0)  {
        this.flooring -= roomSize.get(currRoom);
        currRoom += 1;
      } else {
        break;
      }
    }
    
    String s1;
    String s2;

    if (currRoom == 1){
      s1 = "1 room, ";
    } else {
      //String.valueOf(number)
      s1 = String.valueOf(currRoom) + " rooms, "; 
    }

    s2 = String.valueOf(this.flooring) + " square metre(s) left over";

    System.out.println(s1 + s2);

  }

  //4 rooms, 1 square metre(s) left over

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int flooring = in.nextInt();
    int ROWS = in.nextInt();
    int COLS = in.nextInt();
    Main m = new Main(ROWS, COLS, flooring, in);
    m.findFloor();
    m.applyFlooring();
  }

}