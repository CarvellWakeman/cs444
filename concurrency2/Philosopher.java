public class Philosopher implements Runnable{

  private final Object rightFork;
  private final Object leftFork;

  Philosopher(Object left, Object right){
    this.leftFork = left;
    this.rightFork = right;
  }

  private void doMove(String move) throws InterruptedException{
    System.out.println(Thread.currentThread.getName() + " " + move);
    Thread.sleep(((int)(Math.random() * 100)));
  }

  @Override
  public void run(){
    try{
      while(true){
        doMove(System.nanoTime() + ": Thinking");
        synchronized (leftFork){
          doMove(System.nanoTime() +": Picked up left fork");
          synchronized(rightFork){
            doMove(System.nanoTime() + ": Picked up right fork - eating");
            doMove(System.nanoTime() + ": Put down right fork");
          }
          doMove(System.nanoTime() + ": Put down left fork. Back to thinking");
        }
      }
    } catch(InterruptedException e){
      Thread.currentThread().interrupt();
    }
  }

}
