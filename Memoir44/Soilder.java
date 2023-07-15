public class Soilder extends Troop {

    public Soilder(Team owner , int x , int y) {
        super(2 , 1,owner.getSoldierPerGroup(), owner , x , y , 'S');
    }



    @Override
    public boolean haveAttack() {
        if(getNumberOfMoves() == 0)return false;
        return getNumberOfAttacks() != 0;
    }

    @Override
    public boolean haveMove() {
        return getNumberOfMoves() != 0;
    }


}
