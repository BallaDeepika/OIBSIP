package oasis;
import java.util.*;
public class task2 {
	public static void main(String [] args) {
		Scanner sc=new Scanner(System.in);
		int maxattempts=5;
		int attempts=0;
		int crctscore=1000; //if prediction is correct score=+1000
		int scoreminus=100;  //-100 for wrong predictions
		int gamescore=0;
		int randomno=(int)(Math.random()*99+1);
		System.out.println("\n**Welcome to NUMBER GUESSING GAME**\n\nTry to guess the correct number and Win!!\n"
				+ "You have 5 attempts");
		boolean flag=true;
		
	while(flag) {	
	  while(true) {
			System.out.print("\nEnter a number between 1 and 100:");
			int a=sc.nextInt();
			gamescore-=scoreminus;
			if(a==randomno) {
				gamescore=+crctscore;
				System.out.println("Yayy!!! you won the game \n Your score is "+gamescore);
				break;
			}
			else if(a<randomno) {
				System.out.println("The number you guessed is smaller!!");
			}
			else if(a>randomno){
				System.out.println("The number you guessed is greater!!");
			}
			else {
				System.out.println("please enter valid number");
			}
			attempts++;
			if(attempts==maxattempts) {
				System.out.println("\nSorry no. of attempts exceeded!!");
				System.out.println("The number is "+randomno);
				System.out.println("Your score is "+gamescore);
				break;
			}
			
		}
	  System.out.println("\nPress 0 to play again and 1 to exit :");
	  int b=sc.nextInt();
	  if(b==0) {
		  flag=true;
		  attempts=0;
		  gamescore=0;
	  }
	  else if(b==1) {
		  flag=false;
	  }
	}
	  System.out.println("Thank you See You Again!!");
	  
		}
}

