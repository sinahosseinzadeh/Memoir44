public class Cannon extends Troop {

    public Cannon(Team owner , int x , int y) {
        super(1 , 1,owner.getCannonPerGroup(), owner , x , y , 'C');
    }



    @Override
    public boolean haveAttack() {
        return getNumberOfAttacks() == 1 && getNumberOfMoves() == 1;
    }

    @Override
    public boolean haveMove() {
        return getNumberOfAttacks() == 1 && getNumberOfMoves() == 1;
    }


}
