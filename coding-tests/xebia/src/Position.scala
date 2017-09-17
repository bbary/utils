/**
  * Created by brahim on 9/17/17.
  */
class Position (var x: Int, var y: Int, var orientation: orientationEnum) {

    def move(move: moveEnum): Position ={
       return this
    }

  override def toString: String = s"$x $y $orientation"
}



