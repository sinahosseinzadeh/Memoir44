public class Tank extends Troop {

    public Tank(Team owner , int x , int y) {
        super(3 , 1 ,owner.getTankPerGroup(), owner , x , y , 'T');
    }



    @Override
    public boolean haveAttack() {
        return getNumberOfAttacks() != 0;
    }

    @Override
    public boolean haveMove() {
        return getNumberOfMoves() != 0;
    }

}
