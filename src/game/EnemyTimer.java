package game;

import java.util.Random;

public class EnemyTimer extends Thread { // determines enemy movement's direction and duration
	private Enemy enemy;

	EnemyTimer(Enemy enemy) {
		this.enemy = enemy;
	}

	public void run() {
		Random r = new Random();
		int secs = r.nextInt(3)+1; // secs can be from 1 to 3

		int directionX = 0, directionY = 0;

		while (directionX == 0 && directionY ==0) { // while both x and y direction is 0,
			directionX = r.nextInt(3)-1; // get random number from -1,0,1 (determines dy and dx)
			directionY = r.nextInt(3)-1;
		}

		this.enemy.setDX(directionX); // dir x and y as dx and dy of enemy
		this.enemy.setDY(directionY);

		long startTime = System.currentTimeMillis(); // for timing, determine time now and time after secs*1000
		long endTime = startTime + (secs*1000);

		while (System.currentTimeMillis() <= endTime) { // while current time has not reached end

			this.enemy.changeXY(); // move enemy
			try {
				Thread.sleep(15); // sleep for a bit
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (directionX != 0) this.enemy.setDX(0); // after duration, reset dx and dy to 0
		if (directionY != 0) this.enemy.setDY(0);
		this.enemy.flipMoving(); // signify that enemy has stopped moving
	}
}
